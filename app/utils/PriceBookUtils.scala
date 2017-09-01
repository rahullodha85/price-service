package utils

object PriceBookUtils {
  val countriesWithLocalizedPrices = Set("CA")

  def areLocalizedPricesAvailableFor(countryCode: String): Boolean = {
    countriesWithLocalizedPrices.contains(countryCode)
  }
}
