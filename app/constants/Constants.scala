package constants

import models.InternationalRates

object Constants {
  val TIMEOUT_MSG = "request timed out"
  val ZULU_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
  val ERROR_MESSAGE = "error"

  val MONGO_CONFIG = "mongo"
  val MONGO_URI = "mongoUri"
  val DB_NAME = "dbName"
  val DB_COLLECTION = "dbCollection"
  val LOCALIZED_PRICES_DB_COLLECTION = "dbLocalizedPricesCollection"
  val CONNECTIONS_PER_HOST = "connectionsPerHost"
  val CONNECT_TIMEOUT = "connectTimeout"
  val MAX_WAIT_TIME = "maxWaitTime"
  val THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER = "threadsAllowedToBlockForConnectionMultiplier"
  val READ_PREFERENCE = "readPreference"

  val DB_INTL_RATES_COLLECTION = "dbIntlRatesCollection"

  val PRODUCT_CODE = "product_code"
  val PRODUCT_CODES = "product_codes"
  val UPC_CODE = "upc_code"
  val COUNTRY = "country"

  val DEFAULT_COUNTRY_CODE = "US"
  val DEFAULT_COUNTRY_NAME = "United States of America"
  val DEFAULT_CURRENCY_CODE = "USD"
  val DEFAULT_CURRENCY_NAME = "US Dollar"
  val DEFAULT_CURRENCY_SYMBOL = "USD"
  val DEFAULT_QUOTE_ID = 0
  val DEFAULT_EXCHANGE_RATE = 1L
  val DEFAULT_MERCHANT_CURRENCY = "USD"
  val DEFAULT_SHOPPER_CURRENCY = "USD"
  val DEFAULT_ROUND_METHOD = 0
  val DEFAULT_LANDING_COST = 0L

  val COUNTRY_CODE = "country_code"
  val COUNTRY_NAME = "country_name"
  val CURRENCY_CODE = "currency_code"
  val CURRENCY_NAME = "currency_name"
  val LOCALIZED_PRICE = "localized_price"
  val CURRENCY_SYMBOL = "currency_symbol"
  val QUOTE_ID = "quote_id"
  val EXCHANGE_RATE = "exchange_rate"
  val MERCHANT_CURRENCY = "merchant_currency"
  val SHOPPER_CURRENCY = "shopper_currency"
  val ROUND_METHOD = "round_method"
  val LANDING_COST = "landing_cost"

  val DEFAULT_INTERNATIONAL_RATES = InternationalRates(
    Constants.DEFAULT_CURRENCY_CODE,
    Constants.DEFAULT_CURRENCY_NAME,
    Constants.DEFAULT_CURRENCY_SYMBOL,
    Constants.DEFAULT_QUOTE_ID,
    Constants.DEFAULT_EXCHANGE_RATE,
    Constants.DEFAULT_MERCHANT_CURRENCY,
    Constants.DEFAULT_SHOPPER_CURRENCY,
    Constants.DEFAULT_ROUND_METHOD,
    Constants.DEFAULT_LANDING_COST
  )
}
