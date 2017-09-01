package controllers

import com.google.inject.Inject
import constants.{ Banner, Constants }
import constants.Constants.PRODUCT_CODES
import helpers.ControllerPayload
import models.PriceViewModel
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{ JsError, JsSuccess, Json }
import play.api.mvc.{ Action, AnyContent, Request }
import play.mvc.Controller
import services.IPriceService

import scala.concurrent.Future

class Price @Inject() (
  timeoutHelper: helpers.ControllerTimeout,
  priceService:  IPriceService
) extends Controller
    with ControllerPayload {

  private def findByProductCode(banner: Banner.Value, productCode: String): Future[Seq[PriceViewModel]] = {
    priceService.getProductPrice(productCode, Constants.DEFAULT_COUNTRY_CODE, Constants.DEFAULT_CURRENCY_CODE, true)(banner)
  }

  private def findByUpcCode(banner: Banner.Value, upcCode: String): Future[PriceViewModel] = {
    priceService.getPriceByUpc(upcCode, Constants.DEFAULT_CURRENCY_CODE, Constants.DEFAULT_COUNTRY_CODE, false)(banner)
  }

  private def findByProductCodes(banner: Banner.Value, currency_code: Option[String], country_code: Option[String], localized_price: Option[Boolean], request: Request[AnyContent]) = {
    val payload = request.body.asJson.getOrElse(Json.obj())
    val productCodes = (payload \ PRODUCT_CODES).validate[Seq[String]] match {
      case JsSuccess(prodCodes, _) => prodCodes
      case JsError(_)              => Seq("")
    }
    priceService.getPriceByProductCodes(
      productCodes,
      currency_code.getOrElse(Constants.DEFAULT_CURRENCY_CODE),
      country_code.getOrElse(Constants.DEFAULT_COUNTRY_CODE),
      localized_price.getOrElse(false)
    )(banner)
  }

  @deprecated("use pricesByProductCode (multitenant endpoint)")
  def getPrice(productCode: String) = Action.async({ implicit request =>
    findByProductCode(Banner(), productCode).map(model => writeResponseGet(model))
  })

  @deprecated("use pricesByProductCodes (multitenant endpoint)")
  def getPriceForProducts(currency_code: Option[String], country_code: Option[String], localized_price: Option[Boolean]) = Action.async({ implicit request =>
    findByProductCodes(Banner(), currency_code, country_code, localized_price, request).map(model => writeResponseGet(model))
  })

  @deprecated("use priceByUpcCode (multitenant endpoint)")
  def getPriceByUpc(upcCode: String) = Action.async({ implicit request =>
    findByUpcCode(Banner(), upcCode).map(model => writeResponseGet(model))
  })

  def pricesByProductCode(banner: Banner.Value, productCode: String) = Action.async({ implicit request =>
    findByProductCode(banner, productCode).map(model => writeResponseGet(model))
  })

  def pricesByProductCodes(currency_code: Option[String], country_code: Option[String], localized_price: Option[Boolean], banner: Banner.Value) = Action.async({ implicit request =>
    findByProductCodes(banner, currency_code, country_code, localized_price, request).map(model => writeResponseGet(model))
  })

  def priceByUpcCode(banner: Banner.Value, upcCode: String) = Action.async({ implicit request =>
    findByUpcCode(banner, upcCode).map(model => writeResponseGet(model))
  })

  def getInternationalRateByCurrencyCode(currencyCode: String) = Action({ implicit request =>
    writeResponseGet(priceService.getInternationalRateByCurrencyCode(currencyCode))
  })

  def getInternationalRateByCountryCode(currencyCode: String) = Action({ implicit request =>
    writeResponseGet(priceService.getInternationalRateByCountryCode(currencyCode))
  })
}
