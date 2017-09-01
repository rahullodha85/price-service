package models

import play.api.libs.json._

case class PriceViewModel(
  product_code:          String,
  upc_code:              String,
  price_status:          String,
  current_price:         Int,
  usd_current_price:     Option[Int],
  msrp:                  Int,
  usd_msrp:              Option[Int],
  modified_on:           Option[String],
  markdown_price:        Int,
  usd_markdown_price:    Option[Int],
  privatesale_price:     Int,
  usd_privatesale_price: Option[Int],
  list_price:            Int,
  usd_list_price:        Option[Int],
  date_field:            String,
  final_sale_status:     String,
  percent_off:           String
)

object PriceViewModel {
  implicit val priceFormat = Json.format[PriceViewModel]
}
