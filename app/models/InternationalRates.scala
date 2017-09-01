package models

import play.api.libs.json._

case class InternationalRates(
  currency_code:     String,
  currency_name:     String,
  currency_symbol:   String,
  quote_id:          Int,
  exchange_rate:     Double,
  merchant_currency: String,
  shopper_currency:  String,
  round_method:      Int,
  landing_cost:      Double
)

object InternationalRates {
  implicit val resultFormat: Format[InternationalRates] = Json.format[InternationalRates]
}
