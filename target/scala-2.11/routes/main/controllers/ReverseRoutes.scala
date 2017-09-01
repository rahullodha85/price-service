
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/461967/dev/price-service/conf/routes
// @DATE:Wed Jun 21 17:10:22 EDT 2017

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset
import _root_.common.Binder._

// @LINE:18
package controllers {

  // @LINE:55
  class ReverseAdmin(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:55
    def ping(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "admin/ping")
    }
  
    // @LINE:78
    def dispatcherSettings(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "admin/dispatcherSettings")
    }
  
    // @LINE:69
    def jvmstats(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "admin/jvmstats")
    }
  
  }

  // @LINE:157
  class ReversePrice(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:207
    def getPriceForProducts(currency_code:Option[String], country_code:Option[String], localized_price:Option[Boolean]): Call = {
    
      (currency_code: @unchecked, country_code: @unchecked, localized_price: @unchecked) match {
      
        // @LINE:207
        case (currency_code, country_code, localized_price)  =>
          import ReverseRouteContext.empty
          Call("POST", _prefix + { _defaultPrefix } + "price-service/products" + queryString(List(Some(implicitly[QueryStringBindable[Option[String]]].unbind("currency_code", currency_code)), Some(implicitly[QueryStringBindable[Option[String]]].unbind("country_code", country_code)), Some(implicitly[QueryStringBindable[Option[Boolean]]].unbind("localized_price", localized_price)))))
      
      }
    
    }
  
    // @LINE:324
    def getInternationalRateByCountryCode(countryCode:String): Call = {
    
      (countryCode: @unchecked) match {
      
        // @LINE:324
        case (countryCode)  =>
          import ReverseRouteContext.empty
          Call("GET", _prefix + { _defaultPrefix } + "price-service/intl-rate/country/" + implicitly[PathBindable[String]].unbind("countryCode", countryCode))
      
      }
    
    }
  
    // @LINE:246
    def getPriceByUpc(upcCode:String): Call = {
    
      (upcCode: @unchecked) match {
      
        // @LINE:246
        case (upcCode)  =>
          import ReverseRouteContext.empty
          Call("GET", _prefix + { _defaultPrefix } + "price-service/upc/" + implicitly[PathBindable[String]].unbind("upcCode", upcCode))
      
      }
    
    }
  
    // @LINE:285
    def getInternationalRateByCurrencyCode(currencyCode:String): Call = {
    
      (currencyCode: @unchecked) match {
      
        // @LINE:285
        case (currencyCode)  =>
          import ReverseRouteContext.empty
          Call("GET", _prefix + { _defaultPrefix } + "price-service/intl-rate/currency/" + implicitly[PathBindable[String]].unbind("currencyCode", currencyCode))
      
      }
    
    }
  
    // @LINE:157
    def getPrice(productCode:String): Call = {
    
      (productCode: @unchecked) match {
      
        // @LINE:157
        case (productCode)  =>
          import ReverseRouteContext.empty
          Call("GET", _prefix + { _defaultPrefix } + "price-service/product/" + implicitly[PathBindable[String]].unbind("productCode", productCode))
      
      }
    
    }
  
  }

  // @LINE:27
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:27
    def at(file:String): Call = {
      implicit val _rrc = new ReverseRouteContext(Map(("path", "/public/lib/swagger-ui")))
      Call("GET", _prefix + { _defaultPrefix } + "admin/api-docs/ui/" + implicitly[PathBindable[String]].unbind("file", file))
    }
  
  }

  // @LINE:18
  class ReverseApplication(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:101
    def clearToggles(name:Option[String], banner:Option[constants.Banner.Value]): Call = {
    
      (name: @unchecked, banner: @unchecked) match {
      
        // @LINE:101
        case (name, banner)  =>
          import ReverseRouteContext.empty
          Call("GET", _prefix + { _defaultPrefix } + "admin/clear_toggles" + queryString(List(Some(implicitly[QueryStringBindable[Option[String]]].unbind("name", name)), Some(implicitly[QueryStringBindable[Option[constants.Banner.Value]]].unbind("banner", banner)))))
      
      }
    
    }
  
    // @LINE:36
    def renderSwaggerUi(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "admin/api-docs/ui")
    }
  
    // @LINE:92
    def changeLogLevel(level:String): Call = {
      import ReverseRouteContext.empty
      Call("PUT", _prefix + { _defaultPrefix } + "admin/logLevel/" + implicitly[PathBindable[String]].unbind("level", level))
    }
  
    // @LINE:119
    def toggles(name:Option[String] = None): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "admin/toggles" + queryString(List(if(name == None) None else Some(implicitly[QueryStringBindable[Option[String]]].unbind("name", name)))))
    }
  
    // @LINE:18
    def getApiDocs(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "admin/api-docs")
    }
  
    // @LINE:46
    def index(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix + { _defaultPrefix } + "admin/status")
    }
  
  }


}
