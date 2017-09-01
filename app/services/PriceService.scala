package services

import com.google.inject.Inject
import com.google.inject.name.Named
import constants.{ Banner, Constants }
import mappers.PriceViewModelMapper
import models.{ InternationalRates, PriceBookToggles, PriceViewModel }
import repos.{ ExchangeRateRepo, IPriceRepo }
import utils.PriceBookUtils
import play.api.libs.concurrent.Execution.Implicits._
import play.api.Logger

import scala.concurrent.Future

trait IPriceService {
  def getProductPrice(productCode: String, currencyCode: String = Constants.DEFAULT_CURRENCY_CODE, countryCode: String = Constants.DEFAULT_COUNTRY_CODE, useLocalizedPrice: Boolean = true)(implicit banner: Banner.Value): Future[Seq[PriceViewModel]]
  def getPriceByProductCodes(productCodes: Seq[String], currencyCode: String, countryCode: String, useLocalizedPrice: Boolean)(implicit banner: Banner.Value): Future[Seq[PriceViewModel]]
  def getPriceByUpc(upcCode: String, currencyCode: String = Constants.DEFAULT_CURRENCY_CODE, countryCode: String = Constants.DEFAULT_COUNTRY_CODE, darkLaunch: Boolean = false)(implicit banner: Banner.Value): Future[PriceViewModel]
  def getInternationalRateByCurrencyCode(currencyCode: String): InternationalRates
  def getInternationalRateByCountryCode(countryCode: String): InternationalRates
}

class PriceService @Inject() (
    exchangeRateRepo:                          ExchangeRateRepo,
    @Named("priceBookRepo") priceBookRepo:     IPriceRepo,
    @Named("legacyPriceRepo") legacyPriceRepo: IPriceRepo,
    toggleService:                             ToggleService
) extends IPriceService {

  val DARK_LAUNCH_NOT_SUPPORTED: Boolean = false

  def getProductPrice(productCode: String, currencyCode: String, countryCode: String, useLocalizedPrice: Boolean)(implicit banner: Banner.Value): Future[Seq[PriceViewModel]] = {
    toggleService.getPriceBookToggles(DARK_LAUNCH_NOT_SUPPORTED).map(toggles => {

      lazy val priceRepo: IPriceRepo = if (usePriceBookCollection(toggles, countryCode, useLocalizedPrice)) priceBookRepo else legacyPriceRepo
      val internationalRateResults = exchangeRateRepo.getByCurrencyCode(currencyCode)

      if (toggles.canadaPriceBook && useLocalizedPrice && PriceBookUtils.areLocalizedPricesAvailableFor(countryCode)) {
        Logger.debug("Starting query for local price")
        val localizedPriceModels = priceRepo.getPriceByProductCode(productCode, countryCode)
        Logger.debug("Done query for local price")
        PriceViewModelMapper.fromLocalizedPriceList(localizedPriceModels, internationalRateResults)
      } else {
        Logger.debug("Starting query for us price")
        val usPriceModels = priceRepo.getPriceByProductCode(productCode, Constants.DEFAULT_COUNTRY_CODE)
        Logger.debug("Done query for us price")
        PriceViewModelMapper.fromPriceList(usPriceModels, internationalRateResults)
      }
    })
  }

  def getPriceByProductCodes(productCodes: Seq[String], currencyCode: String, countryCode: String, useLocalizedPrice: Boolean)(implicit banner: Banner.Value): Future[Seq[PriceViewModel]] = {
    toggleService.getPriceBookToggles(DARK_LAUNCH_NOT_SUPPORTED).map(toggles => {
      lazy val priceRepo: IPriceRepo = if (usePriceBookCollection(toggles, countryCode, useLocalizedPrice)) priceBookRepo else legacyPriceRepo

      val internationalRateResults = exchangeRateRepo.getByCurrencyCode(currencyCode)

      if (toggles.canadaPriceBook && useLocalizedPrice && PriceBookUtils.areLocalizedPricesAvailableFor(countryCode)) {
        Logger.debug("Starting query for local price")
        val localizedPriceModels = priceRepo.getPriceByProductCodes(productCodes, countryCode)
        Logger.debug("Done query for local price")
        PriceViewModelMapper.fromLocalizedPriceList(localizedPriceModels, internationalRateResults)
      } else {
        Logger.debug("Starting query for us price")
        val usPriceModels = priceRepo.getPriceByProductCodes(productCodes, Constants.DEFAULT_COUNTRY_CODE)
        Logger.debug("Done query for us price")
        PriceViewModelMapper.fromPriceList(usPriceModels, internationalRateResults)
      }
    })
  }

  def getPriceByUpc(upcCode: String, currencyCode: String, countryCode: String, darkLaunch: Boolean)(implicit banner: Banner.Value): Future[PriceViewModel] = {
    toggleService.getPriceBookToggles(darkLaunch).map(toggles => {
      val priceRepo: IPriceRepo = if (usePriceBookCollection(toggles, countryCode)) priceBookRepo else legacyPriceRepo

      val internationalRateResults = exchangeRateRepo.getByCurrencyCode(currencyCode)

      if (toggles.canadaPriceBook && PriceBookUtils.areLocalizedPricesAvailableFor(countryCode)) {
        val localizedPriceModel = priceRepo.getPriceByUpc(upcCode, countryCode)
        PriceViewModelMapper.fromLocalizedAndUsPrice(localizedPriceModel.get, internationalRateResults)
      } else {
        val usPriceModel = priceRepo.getPriceByUpc(upcCode, Constants.DEFAULT_COUNTRY_CODE)
        PriceViewModelMapper.fromPrice(usPriceModel.get, internationalRateResults)
      }
    })

  }

  def usePriceBookCollection(toggles: PriceBookToggles, countryCode: String, useLocalizedPrice: Boolean = true): Boolean = {
    toggles.useNewCollection || (toggles.canadaPriceBook && useLocalizedPrice && PriceBookUtils.areLocalizedPricesAvailableFor(countryCode))
  }

  def getInternationalRateByCurrencyCode(currencyCode: String): InternationalRates = {
    exchangeRateRepo.getByCurrencyCode(currencyCode)
  }

  def getInternationalRateByCountryCode(countryCode: String): InternationalRates = {
    exchangeRateRepo.getByCountryCode(countryCode)
  }
}
