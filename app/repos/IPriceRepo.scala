package repos

import constants.Constants
import models.PriceModel
import constants.Banner

trait IPriceRepo {
  def getPriceByProductCode(productCode: String, countryCode: String = Constants.DEFAULT_COUNTRY_CODE)(implicit banner: Banner.Value): Seq[PriceModel]
  def getPriceByProductCodes(productCodes: Seq[String], countryCode: String = Constants.DEFAULT_COUNTRY_CODE)(implicit banner: Banner.Value): Seq[PriceModel]
  def getPriceByUpc(upcCode: String, countryCode: String = Constants.DEFAULT_COUNTRY_CODE)(implicit banner: Banner.Value): Option[PriceModel]
}
