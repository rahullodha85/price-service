
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/461967/dev/price-service/conf/routes
// @DATE:Wed Jun 21 17:10:22 EDT 2017

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.common.Binder._

// @LINE:18
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:55
  class ReverseAdmin(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:55
    def ping: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Admin.ping",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/ping"})
        }
      """
    )
  
    // @LINE:78
    def dispatcherSettings: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Admin.dispatcherSettings",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/dispatcherSettings"})
        }
      """
    )
  
    // @LINE:69
    def jvmstats: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Admin.jvmstats",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/jvmstats"})
        }
      """
    )
  
  }

  // @LINE:157
  class ReversePrice(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:207
    def getPriceForProducts: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Price.getPriceForProducts",
      """
        function(currency_code0,country_code1,localized_price2) {
        
          if (true) {
            return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "price-service/products" + _qS([(""" + implicitly[QueryStringBindable[Option[String]]].javascriptUnbind + """)("currency_code", currency_code0), (""" + implicitly[QueryStringBindable[Option[String]]].javascriptUnbind + """)("country_code", country_code1), (""" + implicitly[QueryStringBindable[Option[Boolean]]].javascriptUnbind + """)("localized_price", localized_price2)])})
          }
        
        }
      """
    )
  
    // @LINE:324
    def getInternationalRateByCountryCode: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Price.getInternationalRateByCountryCode",
      """
        function(countryCode0) {
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "price-service/intl-rate/country/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("countryCode", countryCode0)})
          }
        
        }
      """
    )
  
    // @LINE:246
    def getPriceByUpc: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Price.getPriceByUpc",
      """
        function(upcCode0) {
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "price-service/upc/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("upcCode", upcCode0)})
          }
        
        }
      """
    )
  
    // @LINE:285
    def getInternationalRateByCurrencyCode: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Price.getInternationalRateByCurrencyCode",
      """
        function(currencyCode0) {
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "price-service/intl-rate/currency/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("currencyCode", currencyCode0)})
          }
        
        }
      """
    )
  
    // @LINE:157
    def getPrice: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Price.getPrice",
      """
        function(productCode0) {
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "price-service/product/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("productCode", productCode0)})
          }
        
        }
      """
    )
  
  }

  // @LINE:27
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:27
    def at: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.at",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/api-docs/ui/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:18
  class ReverseApplication(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:101
    def clearToggles: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.clearToggles",
      """
        function(name0,banner1) {
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/clear_toggles" + _qS([(""" + implicitly[QueryStringBindable[Option[String]]].javascriptUnbind + """)("name", name0), (""" + implicitly[QueryStringBindable[Option[constants.Banner.Value]]].javascriptUnbind + """)("banner", banner1)])})
          }
        
        }
      """
    )
  
    // @LINE:36
    def renderSwaggerUi: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.renderSwaggerUi",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/api-docs/ui"})
        }
      """
    )
  
    // @LINE:92
    def changeLogLevel: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.changeLogLevel",
      """
        function(level0) {
          return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/logLevel/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("level", level0)})
        }
      """
    )
  
    // @LINE:119
    def toggles: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.toggles",
      """
        function(name0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/toggles" + _qS([(name0 == null ? null : (""" + implicitly[QueryStringBindable[Option[String]]].javascriptUnbind + """)("name", name0))])})
        }
      """
    )
  
    // @LINE:18
    def getApiDocs: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.getApiDocs",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/api-docs"})
        }
      """
    )
  
    // @LINE:46
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/status"})
        }
      """
    )
  
  }


}
