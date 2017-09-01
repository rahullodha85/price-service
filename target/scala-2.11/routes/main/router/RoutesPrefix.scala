
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/461967/dev/price-service/conf/routes
// @DATE:Wed Jun 21 17:10:22 EDT 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
