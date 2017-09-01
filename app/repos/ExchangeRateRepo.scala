package repos

import com.google.inject.Inject
import com.mongodb.casbah.commons.MongoDBObject
import constants.Constants
import metrics.StatsDClientLike
import models.InternationalRates
import utils.BannerAgnostic

class ExchangeRateRepo @Inject() (
    statsDClient:       StatsDClientLike,
    dbConnectorFactory: DBConnectorFactoryLike
) extends BannerAgnostic {

  def getByCurrencyCode(currencyCode: String): InternationalRates = {
    statsDClient.time("getInternationalRateByCurrencyCode") {
      if (!currencyCode.equals(Constants.DEFAULT_CURRENCY_CODE)) {
        val rate = dbConnectorFactory.getDb.intlRatesCollection.findOne(MongoDBObject(Constants.CURRENCY_CODE -> currencyCode))

        rate match {
          case Some(r) =>
            val currencyCode = rate.get.get(Constants.CURRENCY_CODE).toString
            val currencyName = rate.get.get(Constants.CURRENCY_NAME).toString
            val currencySymbol = rate.get.get(Constants.CURRENCY_SYMBOL).toString
            val quoteId = rate.get.get(Constants.QUOTE_ID).asInstanceOf[Int]
            val exchangeRate = rate.get.get(Constants.EXCHANGE_RATE).asInstanceOf[Number].doubleValue
            val merchantCurrency = rate.get.get(Constants.MERCHANT_CURRENCY).toString
            val shopperCurrency = rate.get.get(Constants.SHOPPER_CURRENCY).toString
            val roundMethod = rate.get.get(Constants.ROUND_METHOD).asInstanceOf[Int]
            val landingCost = rate.get.get(Constants.LANDING_COST).asInstanceOf[Number].doubleValue

            InternationalRates(currencyCode, currencyName, currencySymbol, quoteId, exchangeRate, merchantCurrency,
              shopperCurrency, roundMethod, landingCost)
          case None =>
            Constants.DEFAULT_INTERNATIONAL_RATES

        }
      } else
        Constants.DEFAULT_INTERNATIONAL_RATES
    }
  }

  def getByCountryCode(countryCode: String): InternationalRates = {
    statsDClient.time("getInternationalRateByCountryCode") {
      if (!countryCode.equals(Constants.DEFAULT_COUNTRY_CODE)) {
        val rate = dbConnectorFactory.getDb.intlRatesCollection.findOne(MongoDBObject(Constants.COUNTRY_CODE -> countryCode))

        rate match {
          case Some(r) =>
            val currencyCode = rate.get.get(Constants.CURRENCY_CODE).toString
            val currencyName = rate.get.get(Constants.CURRENCY_NAME).toString
            val currencySymbol = rate.get.get(Constants.CURRENCY_SYMBOL).toString
            val quoteId = rate.get.get(Constants.QUOTE_ID).asInstanceOf[Int]
            val exchangeRate = rate.get.get(Constants.EXCHANGE_RATE).asInstanceOf[Number].doubleValue
            val merchantCurrency = rate.get.get(Constants.MERCHANT_CURRENCY).toString
            val shopperCurrency = rate.get.get(Constants.SHOPPER_CURRENCY).toString
            val roundMethod = rate.get.get(Constants.ROUND_METHOD).asInstanceOf[Int]
            val landingCost = rate.get.get(Constants.LANDING_COST).asInstanceOf[Number].doubleValue

            InternationalRates(currencyCode, currencyName, currencySymbol, quoteId, exchangeRate, merchantCurrency,
              shopperCurrency, roundMethod, landingCost)
          case None =>
            Constants.DEFAULT_INTERNATIONAL_RATES
        }
      } else
        Constants.DEFAULT_INTERNATIONAL_RATES
    }
  }
}

