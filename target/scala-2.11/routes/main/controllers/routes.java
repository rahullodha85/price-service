
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/461967/dev/price-service/conf/routes
// @DATE:Wed Jun 21 17:10:22 EDT 2017

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseAdmin Admin = new controllers.ReverseAdmin(RoutesPrefix.byNamePrefix());
  public static final controllers.ReversePrice Price = new controllers.ReversePrice(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseAssets Assets = new controllers.ReverseAssets(RoutesPrefix.byNamePrefix());
  public static final controllers.ReverseApplication Application = new controllers.ReverseApplication(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseAdmin Admin = new controllers.javascript.ReverseAdmin(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReversePrice Price = new controllers.javascript.ReversePrice(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseAssets Assets = new controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix());
    public static final controllers.javascript.ReverseApplication Application = new controllers.javascript.ReverseApplication(RoutesPrefix.byNamePrefix());
  }

}
