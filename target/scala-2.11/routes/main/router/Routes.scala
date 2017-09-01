
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/461967/dev/price-service/conf/routes
// @DATE:Wed Jun 21 17:10:22 EDT 2017

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.common.Binder._

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:18
  Application_2: controllers.Application,
  // @LINE:27
  Assets_1: controllers.Assets,
  // @LINE:55
  Admin_3: controllers.Admin,
  // @LINE:157
  Price_0: controllers.Price,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:18
    Application_2: controllers.Application,
    // @LINE:27
    Assets_1: controllers.Assets,
    // @LINE:55
    Admin_3: controllers.Admin,
    // @LINE:157
    Price_0: controllers.Price
  ) = this(errorHandler, Application_2, Assets_1, Admin_3, Price_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, Application_2, Assets_1, Admin_3, Price_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/api-docs""", """controllers.Application.getApiDocs"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/api-docs/ui/""" + "$" + """file<.+>""", """controllers.Assets.at(path:String = "/public/lib/swagger-ui", file:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/api-docs/ui""", """controllers.Application.renderSwaggerUi"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/status""", """controllers.Application.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/ping""", """controllers.Admin.ping"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/jvmstats""", """controllers.Admin.jvmstats"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/dispatcherSettings""", """controllers.Admin.dispatcherSettings"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/logLevel/""" + "$" + """level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>""", """controllers.Application.changeLogLevel(level:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/clear_toggles""", """controllers.Application.clearToggles(name:Option[String], banner:Option[constants.Banner.Value])"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """banner<[^/]+>/admin/clear_toggles""", """controllers.Application.clearToggles(name:Option[String], banner:Option[constants.Banner.Value])"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/toggles""", """controllers.Application.toggles(name:Option[String] ?= None)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """price-service/product/""" + "$" + """productCode<[0-9]+>""", """controllers.Price.getPrice(productCode:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """v1/price-service/product/""" + "$" + """productCode<[0-9]+>""", """controllers.Price.getPrice(productCode:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """price-service/products""", """controllers.Price.getPriceForProducts(currency_code:Option[String], country_code:Option[String], localized_price:Option[Boolean])"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """v1/price-service/products""", """controllers.Price.getPriceForProducts(currency_code:Option[String], country_code:Option[String], localized_price:Option[Boolean])"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """price-service/upc/""" + "$" + """upcCode<[0-9]+>""", """controllers.Price.getPriceByUpc(upcCode:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """v1/price-service/upc/""" + "$" + """upcCode<[0-9]+>""", """controllers.Price.getPriceByUpc(upcCode:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """price-service/intl-rate/currency/""" + "$" + """currencyCode<[a-zA-z]+>""", """controllers.Price.getInternationalRateByCurrencyCode(currencyCode:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """v1/price-service/intl-rate/currency/""" + "$" + """currencyCode<[a-zA-z]+>""", """controllers.Price.getInternationalRateByCurrencyCode(currencyCode:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """price-service/intl-rate/country/""" + "$" + """countryCode<[a-zA-z]+>""", """controllers.Price.getInternationalRateByCountryCode(countryCode:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """v1/price-service/intl-rate/country/""" + "$" + """countryCode<[a-zA-z]+>""", """controllers.Price.getInternationalRateByCountryCode(countryCode:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:18
  private[this] lazy val controllers_Application_getApiDocs0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/api-docs")))
  )
  private[this] lazy val controllers_Application_getApiDocs0_invoker = createInvoker(
    Application_2.getApiDocs,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "getApiDocs",
      Nil,
      "GET",
      """##
 summary: Get service api documentation
 description:  Returns the service api documentation. Returns a json.

 tags:
   - admin
##""",
      this.prefix + """admin/api-docs"""
    )
  )

  // @LINE:27
  private[this] lazy val controllers_Assets_at1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/api-docs/ui/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_at1_invoker = createInvoker(
    Assets_1.at(fakeValue[String], fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "at",
      Seq(classOf[String], classOf[String]),
      "GET",
      """##
 summary: Get service api documentation UI static resources
 description:  Returns service api documentation UI static resources.

 tags:
   - admin
##""",
      this.prefix + """admin/api-docs/ui/""" + "$" + """file<.+>"""
    )
  )

  // @LINE:36
  private[this] lazy val controllers_Application_renderSwaggerUi2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/api-docs/ui")))
  )
  private[this] lazy val controllers_Application_renderSwaggerUi2_invoker = createInvoker(
    Application_2.renderSwaggerUi,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "renderSwaggerUi",
      Nil,
      "GET",
      """##
 summary: Renders swagger UI
 description:  Returns the Swagger UI for the api documentation.

 tags:
   - admin
##""",
      this.prefix + """admin/api-docs/ui"""
    )
  )

  // @LINE:46
  private[this] lazy val controllers_Application_index3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/status")))
  )
  private[this] lazy val controllers_Application_index3_invoker = createInvoker(
    Application_2.index,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "index",
      Nil,
      "GET",
      """##
 summary: General Health Check
 description:  Basic health check. Returns a json.

 tags:
   - admin
##""",
      this.prefix + """admin/status"""
    )
  )

  // @LINE:55
  private[this] lazy val controllers_Admin_ping4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/ping")))
  )
  private[this] lazy val controllers_Admin_ping4_invoker = createInvoker(
    Admin_3.ping,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Admin",
      "ping",
      Nil,
      "GET",
      """##
 summary: Health Check
 description:  Basic health check. Returns a json.

 tags:
   - admin
##""",
      this.prefix + """admin/ping"""
    )
  )

  // @LINE:69
  private[this] lazy val controllers_Admin_jvmstats5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/jvmstats")))
  )
  private[this] lazy val controllers_Admin_jvmstats5_invoker = createInvoker(
    Admin_3.jvmstats,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Admin",
      "jvmstats",
      Nil,
      "GET",
      """##
 summary: Get JVM Stats
 description:  Returns JVM Stats.

 tags:
   - admin
 responses:
   200:
     description: success
     schema:
       """ + "$" + """ref: '#/definitions/scala.collection.mutable.Map'
##""",
      this.prefix + """admin/jvmstats"""
    )
  )

  // @LINE:78
  private[this] lazy val controllers_Admin_dispatcherSettings6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/dispatcherSettings")))
  )
  private[this] lazy val controllers_Admin_dispatcherSettings6_invoker = createInvoker(
    Admin_3.dispatcherSettings,
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Admin",
      "dispatcherSettings",
      Nil,
      "GET",
      """##
 summary: Get Akka Actor config
 description:  Returns Akka Actor config. Returns a json.

 tags:
   - admin
##""",
      this.prefix + """admin/dispatcherSettings"""
    )
  )

  // @LINE:92
  private[this] lazy val controllers_Application_changeLogLevel7_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/logLevel/"), DynamicPart("level", """(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)""",false)))
  )
  private[this] lazy val controllers_Application_changeLogLevel7_invoker = createInvoker(
    Application_2.changeLogLevel(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "changeLogLevel",
      Seq(classOf[String]),
      "PUT",
      """##
 summary: Change Log Level
 description:  Change the log level of this service. Returns a string.

 tags:
   - admin
 parameters:
   - name: level
     type: string
     enum: ["ALL", "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF"]
     description: Log level to set.
##""",
      this.prefix + """admin/logLevel/""" + "$" + """level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>"""
    )
  )

  // @LINE:101
  private[this] lazy val controllers_Application_clearToggles8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/clear_toggles")))
  )
  private[this] lazy val controllers_Application_clearToggles8_invoker = createInvoker(
    Application_2.clearToggles(fakeValue[Option[String]], fakeValue[Option[constants.Banner.Value]]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "clearToggles",
      Seq(classOf[Option[String]], classOf[Option[constants.Banner.Value]]),
      "GET",
      """##
 summary: Clear Toggles
 description: Clear the toggles cache, if you pass a toggle name under ?name=toggle_name it will clear that toggle, otherwise clear everything. Returns a string.

 tags:
   - admin
##""",
      this.prefix + """admin/clear_toggles"""
    )
  )

  // @LINE:103
  private[this] lazy val controllers_Application_clearToggles9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("banner", """[^/]+""",true), StaticPart("/admin/clear_toggles")))
  )
  private[this] lazy val controllers_Application_clearToggles9_invoker = createInvoker(
    Application_2.clearToggles(fakeValue[Option[String]], fakeValue[Option[constants.Banner.Value]]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "clearToggles",
      Seq(classOf[Option[String]], classOf[Option[constants.Banner.Value]]),
      "GET",
      """GET     /admin/:name/clear_toggles             controllers.Application.clearToggles(name: Option[String])""",
      this.prefix + """""" + "$" + """banner<[^/]+>/admin/clear_toggles"""
    )
  )

  // @LINE:119
  private[this] lazy val controllers_Application_toggles10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/toggles")))
  )
  private[this] lazy val controllers_Application_toggles10_invoker = createInvoker(
    Application_2.toggles(fakeValue[Option[String]]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Application",
      "toggles",
      Seq(classOf[Option[String]]),
      "GET",
      """##
 summary: Get Toggles
 description: See what toggles our service has, if you pass a toggle name under ?name=toggle_name it will fetch that toggle, otherwise fetch everything. Returns a string.

 tags:
   - admin
 responses:
    200:
      description: success
      schema:
        type: array
        items:
          """ + "$" + """ref: '#/definitions/models.Toggle'
##""",
      this.prefix + """admin/toggles"""
    )
  )

  // @LINE:157
  private[this] lazy val controllers_Price_getPrice11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("price-service/product/"), DynamicPart("productCode", """[0-9]+""",false)))
  )
  private[this] lazy val controllers_Price_getPrice11_invoker = createInvoker(
    Price_0.getPrice(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getPrice",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get price info for a given product code
 description: Returns price information for a given product code
 parameters:
   - in: path
     name: productCode
     type: string
     description: Unique ID of the product Example - 0459475706564
     required: true
 responses:
   200:
       description: success
       schema:
           required:
               - 'request'
           properties:
               request:
                   """ + "$" + """ref: '#/definitions/models.ApiRequestModel'
               response:
                   required:
                       - 'results'
                   properties:
                       results:
                           type: 'object'
                           """ + "$" + """ref: '#/definitions/models.PriceViewModel'
               errors:
                   type: array
                   items:
                       """ + "$" + """ref: '#/definitions/models.ApiErrorModel'
##""",
      this.prefix + """price-service/product/""" + "$" + """productCode<[0-9]+>"""
    )
  )

  // @LINE:164
  private[this] lazy val controllers_Price_getPrice12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("v1/price-service/product/"), DynamicPart("productCode", """[0-9]+""",false)))
  )
  private[this] lazy val controllers_Price_getPrice12_invoker = createInvoker(
    Price_0.getPrice(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getPrice",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get price info for a given product code
 description: Added for backward compatibility. We no longer use version context in our API.
 deprecated: true
##""",
      this.prefix + """v1/price-service/product/""" + "$" + """productCode<[0-9]+>"""
    )
  )

  // @LINE:207
  private[this] lazy val controllers_Price_getPriceForProducts13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("price-service/products")))
  )
  private[this] lazy val controllers_Price_getPriceForProducts13_invoker = createInvoker(
    Price_0.getPriceForProducts(fakeValue[Option[String]], fakeValue[Option[String]], fakeValue[Option[Boolean]]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getPriceForProducts",
      Seq(classOf[Option[String]], classOf[Option[String]], classOf[Option[Boolean]]),
      "POST",
      """##
 summary: Get price info for a given list of product codes
 description: Returns price information for a given list of product codes
 consumes:
   - "application/json"
 parameters:
   - in: body
     name: productCode
     description: Payload with list of product codes
     required: true
     schema:
       type: object
       required:
           - product_codes
       properties:
           product_codes:
               type: array
               items:
                   type: string
 responses:
   200:
       description: success
       schema:
           required:
               - 'request'
           properties:
               request:
                   """ + "$" + """ref: '#/definitions/models.ApiRequestModel'
               response:
                   required:
                       - 'results'
                   properties:
                       results:
                           type: array
                           items:
                               """ + "$" + """ref: '#/definitions/models.PriceViewModel'
               errors:
                   type: array
                   items:
                       """ + "$" + """ref: '#/definitions/models.ApiErrorModel'
##""",
      this.prefix + """price-service/products"""
    )
  )

  // @LINE:214
  private[this] lazy val controllers_Price_getPriceForProducts14_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("v1/price-service/products")))
  )
  private[this] lazy val controllers_Price_getPriceForProducts14_invoker = createInvoker(
    Price_0.getPriceForProducts(fakeValue[Option[String]], fakeValue[Option[String]], fakeValue[Option[Boolean]]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getPriceForProducts",
      Seq(classOf[Option[String]], classOf[Option[String]], classOf[Option[Boolean]]),
      "POST",
      """##
 summary: Get price info for a given list of product codes
 description: Added for backward compatibility. We no longer use version context in our API.
 deprecated: true
##""",
      this.prefix + """v1/price-service/products"""
    )
  )

  // @LINE:246
  private[this] lazy val controllers_Price_getPriceByUpc15_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("price-service/upc/"), DynamicPart("upcCode", """[0-9]+""",false)))
  )
  private[this] lazy val controllers_Price_getPriceByUpc15_invoker = createInvoker(
    Price_0.getPriceByUpc(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getPriceByUpc",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get price info for a given upc code
 description: Returns price information for a given upc code
 parameters:
   - in: path
     name: upcCode
     type: string
     description: Unique upc code Example -
     required: true
 responses:
   200:
       description: success
       schema:
           required:
               - 'request'
           properties:
               request:
                   """ + "$" + """ref: '#/definitions/models.ApiRequestModel'
               response:
                   required:
                       - 'results'
                   properties:
                       results:
                           type: 'object'
                           """ + "$" + """ref: '#/definitions/models.PriceViewModel'
               errors:
                   type: array
                   items:
                       """ + "$" + """ref: '#/definitions/models.ApiErrorModel'
##""",
      this.prefix + """price-service/upc/""" + "$" + """upcCode<[0-9]+>"""
    )
  )

  // @LINE:253
  private[this] lazy val controllers_Price_getPriceByUpc16_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("v1/price-service/upc/"), DynamicPart("upcCode", """[0-9]+""",false)))
  )
  private[this] lazy val controllers_Price_getPriceByUpc16_invoker = createInvoker(
    Price_0.getPriceByUpc(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getPriceByUpc",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get price info for a given upc code
 description: Added for backward compatibility. We no longer use version context in our API.
 deprecated: true
##""",
      this.prefix + """v1/price-service/upc/""" + "$" + """upcCode<[0-9]+>"""
    )
  )

  // @LINE:285
  private[this] lazy val controllers_Price_getInternationalRateByCurrencyCode17_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("price-service/intl-rate/currency/"), DynamicPart("currencyCode", """[a-zA-z]+""",false)))
  )
  private[this] lazy val controllers_Price_getInternationalRateByCurrencyCode17_invoker = createInvoker(
    Price_0.getInternationalRateByCurrencyCode(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getInternationalRateByCurrencyCode",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get international rate for a given currency code
 description: Returns international rate information for a given currency code
 parameters:
   - in: path
     name: currencyCode
     type: string
     description: Currency code Example - CAD/GBP
     required: true
 responses:
   200:
       description: success
       schema:
           required:
               - 'request'
           properties:
               request:
                   """ + "$" + """ref: '#/definitions/models.ApiRequestModel'
               response:
                   required:
                       - 'results'
                   properties:
                       results:
                           type: 'object'
                           """ + "$" + """ref: '#/definitions/models.InternationalRates'
               errors:
                   type: array
                   items:
                       """ + "$" + """ref: '#/definitions/models.ApiErrorModel'
##""",
      this.prefix + """price-service/intl-rate/currency/""" + "$" + """currencyCode<[a-zA-z]+>"""
    )
  )

  // @LINE:292
  private[this] lazy val controllers_Price_getInternationalRateByCurrencyCode18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("v1/price-service/intl-rate/currency/"), DynamicPart("currencyCode", """[a-zA-z]+""",false)))
  )
  private[this] lazy val controllers_Price_getInternationalRateByCurrencyCode18_invoker = createInvoker(
    Price_0.getInternationalRateByCurrencyCode(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getInternationalRateByCurrencyCode",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get international rate for a given currency code
 description: Added for backward compatibility. We no longer use version context in our API.
 deprecated: true
##""",
      this.prefix + """v1/price-service/intl-rate/currency/""" + "$" + """currencyCode<[a-zA-z]+>"""
    )
  )

  // @LINE:324
  private[this] lazy val controllers_Price_getInternationalRateByCountryCode19_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("price-service/intl-rate/country/"), DynamicPart("countryCode", """[a-zA-z]+""",false)))
  )
  private[this] lazy val controllers_Price_getInternationalRateByCountryCode19_invoker = createInvoker(
    Price_0.getInternationalRateByCountryCode(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getInternationalRateByCountryCode",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get international rate for a given country code
 description: Returns international rate information for a given country code
 parameters:
   - in: path
     name: countryCode
     type: string
     description: Country code Example - CA/UK
     required: true
 responses:
   200:
       description: success
       schema:
           required:
               - 'request'
           properties:
               request:
                   """ + "$" + """ref: '#/definitions/models.ApiRequestModel'
               response:
                   required:
                       - 'results'
                   properties:
                       results:
                           type: 'object'
                           """ + "$" + """ref: '#/definitions/models.InternationalRates'
               errors:
                   type: array
                   items:
                       """ + "$" + """ref: '#/definitions/models.ApiErrorModel'
##""",
      this.prefix + """price-service/intl-rate/country/""" + "$" + """countryCode<[a-zA-z]+>"""
    )
  )

  // @LINE:331
  private[this] lazy val controllers_Price_getInternationalRateByCountryCode20_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("v1/price-service/intl-rate/country/"), DynamicPart("countryCode", """[a-zA-z]+""",false)))
  )
  private[this] lazy val controllers_Price_getInternationalRateByCountryCode20_invoker = createInvoker(
    Price_0.getInternationalRateByCountryCode(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Price",
      "getInternationalRateByCountryCode",
      Seq(classOf[String]),
      "GET",
      """##
 summary: Get international rate for a given country code
 description: Added for backward compatibility. We no longer use version context in our API.
 deprecated: true
##""",
      this.prefix + """v1/price-service/intl-rate/country/""" + "$" + """countryCode<[a-zA-z]+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:18
    case controllers_Application_getApiDocs0_route(params) =>
      call { 
        controllers_Application_getApiDocs0_invoker.call(Application_2.getApiDocs)
      }
  
    // @LINE:27
    case controllers_Assets_at1_route(params) =>
      call(Param[String]("path", Right("/public/lib/swagger-ui")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at1_invoker.call(Assets_1.at(path, file))
      }
  
    // @LINE:36
    case controllers_Application_renderSwaggerUi2_route(params) =>
      call { 
        controllers_Application_renderSwaggerUi2_invoker.call(Application_2.renderSwaggerUi)
      }
  
    // @LINE:46
    case controllers_Application_index3_route(params) =>
      call { 
        controllers_Application_index3_invoker.call(Application_2.index)
      }
  
    // @LINE:55
    case controllers_Admin_ping4_route(params) =>
      call { 
        controllers_Admin_ping4_invoker.call(Admin_3.ping)
      }
  
    // @LINE:69
    case controllers_Admin_jvmstats5_route(params) =>
      call { 
        controllers_Admin_jvmstats5_invoker.call(Admin_3.jvmstats)
      }
  
    // @LINE:78
    case controllers_Admin_dispatcherSettings6_route(params) =>
      call { 
        controllers_Admin_dispatcherSettings6_invoker.call(Admin_3.dispatcherSettings)
      }
  
    // @LINE:92
    case controllers_Application_changeLogLevel7_route(params) =>
      call(params.fromPath[String]("level", None)) { (level) =>
        controllers_Application_changeLogLevel7_invoker.call(Application_2.changeLogLevel(level))
      }
  
    // @LINE:101
    case controllers_Application_clearToggles8_route(params) =>
      call(params.fromQuery[Option[String]]("name", None), params.fromQuery[Option[constants.Banner.Value]]("banner", None)) { (name, banner) =>
        controllers_Application_clearToggles8_invoker.call(Application_2.clearToggles(name, banner))
      }
  
    // @LINE:103
    case controllers_Application_clearToggles9_route(params) =>
      call(params.fromQuery[Option[String]]("name", None), params.fromPath[Option[constants.Banner.Value]]("banner", None)) { (name, banner) =>
        controllers_Application_clearToggles9_invoker.call(Application_2.clearToggles(name, banner))
      }
  
    // @LINE:119
    case controllers_Application_toggles10_route(params) =>
      call(params.fromQuery[Option[String]]("name", Some(None))) { (name) =>
        controllers_Application_toggles10_invoker.call(Application_2.toggles(name))
      }
  
    // @LINE:157
    case controllers_Price_getPrice11_route(params) =>
      call(params.fromPath[String]("productCode", None)) { (productCode) =>
        controllers_Price_getPrice11_invoker.call(Price_0.getPrice(productCode))
      }
  
    // @LINE:164
    case controllers_Price_getPrice12_route(params) =>
      call(params.fromPath[String]("productCode", None)) { (productCode) =>
        controllers_Price_getPrice12_invoker.call(Price_0.getPrice(productCode))
      }
  
    // @LINE:207
    case controllers_Price_getPriceForProducts13_route(params) =>
      call(params.fromQuery[Option[String]]("currency_code", None), params.fromQuery[Option[String]]("country_code", None), params.fromQuery[Option[Boolean]]("localized_price", None)) { (currency_code, country_code, localized_price) =>
        controllers_Price_getPriceForProducts13_invoker.call(Price_0.getPriceForProducts(currency_code, country_code, localized_price))
      }
  
    // @LINE:214
    case controllers_Price_getPriceForProducts14_route(params) =>
      call(params.fromQuery[Option[String]]("currency_code", None), params.fromQuery[Option[String]]("country_code", None), params.fromQuery[Option[Boolean]]("localized_price", None)) { (currency_code, country_code, localized_price) =>
        controllers_Price_getPriceForProducts14_invoker.call(Price_0.getPriceForProducts(currency_code, country_code, localized_price))
      }
  
    // @LINE:246
    case controllers_Price_getPriceByUpc15_route(params) =>
      call(params.fromPath[String]("upcCode", None)) { (upcCode) =>
        controllers_Price_getPriceByUpc15_invoker.call(Price_0.getPriceByUpc(upcCode))
      }
  
    // @LINE:253
    case controllers_Price_getPriceByUpc16_route(params) =>
      call(params.fromPath[String]("upcCode", None)) { (upcCode) =>
        controllers_Price_getPriceByUpc16_invoker.call(Price_0.getPriceByUpc(upcCode))
      }
  
    // @LINE:285
    case controllers_Price_getInternationalRateByCurrencyCode17_route(params) =>
      call(params.fromPath[String]("currencyCode", None)) { (currencyCode) =>
        controllers_Price_getInternationalRateByCurrencyCode17_invoker.call(Price_0.getInternationalRateByCurrencyCode(currencyCode))
      }
  
    // @LINE:292
    case controllers_Price_getInternationalRateByCurrencyCode18_route(params) =>
      call(params.fromPath[String]("currencyCode", None)) { (currencyCode) =>
        controllers_Price_getInternationalRateByCurrencyCode18_invoker.call(Price_0.getInternationalRateByCurrencyCode(currencyCode))
      }
  
    // @LINE:324
    case controllers_Price_getInternationalRateByCountryCode19_route(params) =>
      call(params.fromPath[String]("countryCode", None)) { (countryCode) =>
        controllers_Price_getInternationalRateByCountryCode19_invoker.call(Price_0.getInternationalRateByCountryCode(countryCode))
      }
  
    // @LINE:331
    case controllers_Price_getInternationalRateByCountryCode20_route(params) =>
      call(params.fromPath[String]("countryCode", None)) { (countryCode) =>
        controllers_Price_getInternationalRateByCountryCode20_invoker.call(Price_0.getInternationalRateByCountryCode(countryCode))
      }
  }
}
