package utils

import com.github.simplyscala.{ MongoEmbedDatabase, MongodProps }

object MongoTestUtils extends MongoEmbedDatabase {

  var mongoProps: MongodProps = _

  def initTest = {
    mongoProps = mongoStart()
  }

  def cleanUp = {
    mongoStop(mongoProps)
  }

  def getPricesCollectionJson = {
    val pricesCollectionJson =
      s"""
                {
                  "product_code": "123",
                  "upc_code": "999",
                  "current_price": 21000,
                  "msrp": 52500,
                  "price_status": "M",
                  "markdown_price": 21000,
                  "privatesale_price": 10500,
                  "list_price": 52500,
                  "date_field": "",
                  "modified_on": "2015-09-16 14:15:23",
                  "final_sale_status": "Y"
                }
              """

    pricesCollectionJson
  }

  def getLocalizedPricesCollectionJson(product_code: String = "1000", upc_code: String = "2000",
                                       country: String = "US", list_price: Integer, sale_price: Integer,
                                       privatesale_price: Integer) = {
    val localizedPricesCollectionJson =
      s"""
         {
         	"product_code" : "$product_code",
         	"upc_code" : "$upc_code",
         	"country" : "$country",
         	"list_price" : $list_price,
         	"sale_price" : $sale_price,
         	"privatesale_price" : $privatesale_price,
         	"is_deleted" : "N",
         	"final_sale_status" : "N",
         	"modified_on" : "2015-09-16 14:15:23",
         	"price_status" : "M"
         }
      """
    localizedPricesCollectionJson
  }

  def getCanadaInternationalRateJson = {
    val canadianExchangeRate = 1.5
    val canadaInternationalRateJson =
      s"""
         {
           "country_code": "CA",
           "country_name": "Canada",
           "currency_code": "CAD",
           "currency_name": "Canadian Dollar",
           "currency_symbol": "CAD",
           "quote_id": 43250074,
           "exchange_rate": $canadianExchangeRate,
           "merchant_currency": "USD",
           "shopper_currency": "CAD",
           "round_method": 2,
           "landing_cost": 0
         }
       """
    canadaInternationalRateJson
  }

  def getGreatBritainInternationalRateJson = {
    val britishExchangeRate = .5
    val greatBritainInternationalRateJson =
      s"""
         {
           "country_code": "GB",
           "country_name": "United Kingdom",
           "currency_code": "GBP",
           "currency_name": "British Pound",
           "currency_symbol": "GBP",
           "quote_id": 43250086,
           "exchange_rate": $britishExchangeRate,
           "merchant_currency": "USD",
           "shopper_currency": "GBP",
           "round_method": 2,
           "landing_cost": 0
         }
       """
    greatBritainInternationalRateJson
  }

}
