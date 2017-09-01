package unit.utils

import constants.Constants
import models.{ InternationalRates, PriceModel, PriceViewModel }
import org.scalatest.WordSpec
import mappers.PriceViewModelMapper

class PriceViewModelMapperSpec extends WordSpec {

  "convertToInternationalPrice" should {
    "return Price Model with US prices when currency code is USD" in {
      val priceModel = new PriceModelBuilder(current_price = 150, list_price = 300).build
      val convertedPriceModel = PriceViewModelMapper.fromPrice(priceModel, Constants.DEFAULT_INTERNATIONAL_RATES)

      assert(convertedPriceModel.current_price == 150)
      assert(convertedPriceModel.list_price == 300)
    }

    "return price model with Japanese exchange rate prices when currency code is JPY" in {
      val exchange_rate: Double = 1.8
      val JAPANESE_RATES = InternationalRates("JPY", "Japanese Yen", "JPY", 0, exchange_rate, "JPY", "JPY", 0, 0L)
      val priceModel = new PriceModelBuilder(current_price = 100, list_price = 200).build

      val convertedPriceModel: PriceViewModel = PriceViewModelMapper.fromPrice(priceModel, JAPANESE_RATES)

      assert(convertedPriceModel.current_price == 180)
      assert(convertedPriceModel.list_price == 360)
      assert(convertedPriceModel.usd_current_price == Some(100))
    }
  }
}

case class PriceModelBuilder(
    product_code:          String         = "1234",
    upc_code:              String         = "1234",
    price_status:          String         = "N",
    current_price:         Int            = 100,
    usd_current_price:     Option[Int]    = Some(100),
    msrp:                  Int            = 100,
    usd_msrp:              Option[Int]    = Some(100),
    modified_on:           Option[String] = None,
    markdown_price:        Int            = 100,
    usd_markdown_price:    Option[Int]    = Some(100),
    privatesale_price:     Int            = 100,
    usd_privatesale_price: Option[Int]    = Some(100),
    list_price:            Int            = 100,
    usd_list_price:        Option[Int]    = Some(100),
    date_field:            String         = "",
    final_sale_status:     String         = "",
    percent_off:           Option[String] = None
) {
  def build: PriceModel = {
    PriceModel(
      product_code,
      upc_code,
      price_status,
      current_price,
      usd_current_price,
      msrp,
      usd_msrp,
      modified_on,
      markdown_price,
      usd_markdown_price,
      privatesale_price,
      usd_privatesale_price,
      list_price,
      usd_list_price,
      date_field,
      final_sale_status,
      percent_off
    )
  }
}
