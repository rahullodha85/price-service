package mappers

import constants.Constants
import models.{ InternationalRates, PriceModel, PriceViewModel }

object PriceViewModelMapper {
  def apply(priceObj: PriceModel): PriceViewModel = {
    PriceViewModel(
      priceObj.product_code,
      priceObj.upc_code,
      priceObj.price_status,
      priceObj.current_price,
      Option(priceObj.current_price),
      priceObj.msrp,
      Option(priceObj.msrp),
      priceObj.modified_on,
      priceObj.markdown_price,
      Option(priceObj.markdown_price),
      priceObj.privatesale_price,
      Option(priceObj.privatesale_price),
      priceObj.list_price,
      Option(priceObj.list_price),
      priceObj.date_field,
      priceObj.final_sale_status,
      priceObj.percent_off.getOrElse("")
    )
  }

  def fromLocalizedAndUsPrice(localizedPriceModel: PriceModel, localExchangeRate: InternationalRates) = {

    PriceViewModel(
      product_code = localizedPriceModel.product_code,
      upc_code = localizedPriceModel.upc_code,
      price_status = localizedPriceModel.price_status,
      msrp = localizedPriceModel.msrp,
      list_price = localizedPriceModel.list_price,
      current_price = localizedPriceModel.current_price,
      markdown_price = localizedPriceModel.markdown_price,
      privatesale_price = localizedPriceModel.privatesale_price,
      modified_on = localizedPriceModel.modified_on,
      final_sale_status = localizedPriceModel.final_sale_status,
      usd_privatesale_price = Some(calculateUsPriceFromLocalizedPrice(localizedPriceModel.privatesale_price, localExchangeRate.exchange_rate)),
      usd_current_price = Some(calculateUsPriceFromLocalizedPrice(localizedPriceModel.current_price, localExchangeRate.exchange_rate)),
      usd_msrp = Some(calculateUsPriceFromLocalizedPrice(localizedPriceModel.msrp, localExchangeRate.exchange_rate)),
      usd_list_price = Some(calculateUsPriceFromLocalizedPrice(localizedPriceModel.list_price, localExchangeRate.exchange_rate)),
      usd_markdown_price = Some(calculateUsPriceFromLocalizedPrice(localizedPriceModel.markdown_price, localExchangeRate.exchange_rate)),
      date_field = localizedPriceModel.date_field,
      percent_off = localizedPriceModel.percent_off.getOrElse("")
    )
  }

  def fromLocalizedPriceList(localizedPriceModels: Seq[PriceModel], localExchangeRate: InternationalRates): Seq[PriceViewModel] = {
    for (localizedPriceModel <- localizedPriceModels) yield {
      fromLocalizedAndUsPrice(localizedPriceModel, localExchangeRate)
    }
  }

  def fromPriceList(priceObjList: Seq[PriceModel], internationalRate: InternationalRates): Seq[PriceViewModel] = {
    priceObjList.map(price => fromPrice(price, internationalRate))
  }

  def fromPrice(priceObj: PriceModel, internationalRate: InternationalRates): PriceViewModel = {
    if (internationalRate.currency_code.equals(Constants.DEFAULT_CURRENCY_CODE)) {
      apply(priceObj)
    } else {
      val current_price = calculateFinalPrice(priceObj.current_price, internationalRate.exchange_rate, internationalRate.landing_cost)
      val usd_current_price = priceObj.current_price

      val msrp = calculateFinalPrice(priceObj.msrp, internationalRate.exchange_rate, internationalRate.landing_cost)
      val usd_msrp = priceObj.msrp

      val markdown_price = calculateFinalPrice(priceObj.markdown_price, internationalRate.exchange_rate, internationalRate.landing_cost)
      val usd_markdown_price = priceObj.markdown_price

      val privatesale_price = calculateFinalPrice(priceObj.privatesale_price, internationalRate.exchange_rate, internationalRate.landing_cost)
      val usd_privatesale_price = priceObj.privatesale_price

      val list_price = calculateFinalPrice(priceObj.list_price, internationalRate.exchange_rate, internationalRate.landing_cost)
      val usd_list_price = priceObj.list_price

      PriceViewModel(
        priceObj.product_code,
        priceObj.upc_code,
        priceObj.price_status,
        current_price,
        Option(usd_current_price),
        msrp,
        Option(usd_msrp),
        priceObj.modified_on,
        markdown_price,
        Option(usd_markdown_price),
        privatesale_price,
        Option(usd_privatesale_price),
        list_price,
        Option(usd_list_price),
        priceObj.date_field,
        priceObj.final_sale_status,
        priceObj.percent_off.getOrElse("")
      )
    }
  }

  def calculateFinalPrice(price: Int, exchangeRate: Double, landingCost: Double): Int = {
    val convertedPrice = price * exchangeRate
    if (landingCost > 0) {
      val priceAndLandingCost = convertedPrice * landingCost
      Math.round(priceAndLandingCost).toInt
    } else {
      Math.round(convertedPrice).toInt
    }
  }

  def calculateUsPriceFromLocalizedPrice(price: Int, exchangeRate: Double): Int = {
    val convertedPrice = price / exchangeRate
    Math.round(convertedPrice).toInt
  }
}
