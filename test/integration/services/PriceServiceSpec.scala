package integration.services

import com.mongodb.DBObject
import com.mongodb.util.JSON
import constants.Banner
import metrics.NoOpStatsDClient
import models.{ PriceBookToggles, PriceViewModel }
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito
import org.mockito.Mockito._
import services.{ PriceService, ToggleService }

import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import play.api.Play
import repos._
import utils.TestUtils.application
import utils.{ BannerAgnostic, MongoTestUtils }

class PriceServiceSpec extends WordSpec
    with ShouldMatchers
    with BeforeAndAfterAll
    with MockitoSugar
    with BannerAgnostic {

  val app = application()
  val dbConnector = app.injector.instanceOf[DbConnector]
  val dbConnectorFactory = mock[DBConnectorFactoryLike]
  var exchangeRateRepo: ExchangeRateRepo = new ExchangeRateRepo(NoOpStatsDClient, dbConnectorFactory)
  var priceBookRepo: PriceBookRepo = new PriceBookRepo(NoOpStatsDClient, dbConnectorFactory)
  var priceRepo: PriceRepo = new PriceRepo(NoOpStatsDClient, dbConnectorFactory)
  var toggleService: ToggleService = mock[ToggleService]
  var priceService: PriceService = new PriceService(exchangeRateRepo, priceBookRepo, priceRepo, toggleService)
  var canadianExchangeRate = 1.5
  var britishExchangeRate = .5
  var msrp_usd, list_price_usd = 5000
  var current_price_usd, sale_price_usd, markdown_price_usd = 4000
  var privatesale_price_usd = 3000
  var msrp_cad, list_price_cad = 10000
  var current_price_cad, sale_price_cad, markdown_price_cad = 8000
  var privatesale_price_cad = 6000
  var DARK_LAUNCH_OFF = false
  var DARK_LAUNCH_ON = true
  val WAIT_TIME: FiniteDuration = 10.second

  override def beforeAll() {
    Play.start(app)
    MongoTestUtils.initTest

    dbConnector.pricesCollection.insert(JSON.parse(MongoTestUtils.getPricesCollectionJson).asInstanceOf[DBObject])
    dbConnector.intlRatesCollection.insert(JSON.parse(MongoTestUtils.getCanadaInternationalRateJson).asInstanceOf[DBObject])
    dbConnector.intlRatesCollection.insert(JSON.parse(MongoTestUtils.getGreatBritainInternationalRateJson).asInstanceOf[DBObject])
    when(dbConnectorFactory.getDb(any(classOf[Banner.Value]))).thenReturn(dbConnector)

  }

  override def afterAll() {
    dbConnector.pricesCollection.drop()
    dbConnector.intlRatesCollection.drop()
    dbConnector.localizedPricesCollection.drop()
    Play.stop(app)
    MongoTestUtils.cleanUp
  }

  "The Price Service when searching by product code and price book toggles are ON" should {

    "return the United States price from LocalizedPriceRepo when using US codes" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_OFF)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = true, canadaPriceBook = true)))
      insertPriceToLocalizedPriceRepo()

      val priceViewModel = getProductPrice("1000").head

      assert(priceViewModel.upc_code == "2000")
      assert(priceViewModel.current_price == sale_price_usd)
      assert(priceViewModel.usd_current_price.get == sale_price_usd)
      assert(priceViewModel.msrp == list_price_usd)
      assert(priceViewModel.usd_msrp.get == list_price_usd)
      assert(priceViewModel.markdown_price == sale_price_usd)
      assert(priceViewModel.usd_markdown_price.get == sale_price_usd)
      assert(priceViewModel.privatesale_price == privatesale_price_usd)
      assert(priceViewModel.usd_privatesale_price.get == privatesale_price_usd)
      assert(priceViewModel.list_price == list_price_usd)
      assert(priceViewModel.usd_list_price.get == list_price_usd)
    }

    "return a localized price (Canadian) from LocalizedPriceRepo  when using Canadian codes" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_OFF)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = true, canadaPriceBook = true)))
      insertPriceToLocalizedPriceRepo()
      insertPriceToLocalizedPriceRepo(country = "CA", list_price = list_price_cad, sale_price = sale_price_cad, privatesale_price = privatesale_price_cad)

      val priceViewModel = getProductPrice("1000", "CAD", "CA").head

      assert(priceViewModel.upc_code == "2000")
      assert(priceViewModel.current_price == current_price_cad)
      assert(priceViewModel.usd_current_price.get == 5333) // current_price_cad/canadianExchangeRate
      assert(priceViewModel.msrp == msrp_cad)
      assert(priceViewModel.usd_msrp.get == 6667)
      assert(priceViewModel.markdown_price == markdown_price_cad)
      assert(priceViewModel.usd_markdown_price.get == 5333)
      assert(priceViewModel.privatesale_price == privatesale_price_cad)
      assert(priceViewModel.usd_privatesale_price.get == 4000)
      assert(priceViewModel.list_price == list_price_cad)
      assert(priceViewModel.usd_list_price.get == 6667)
    }

    "return a non-localized price (Great Britain) from LocalizedPriceRepo when using GB codes" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_OFF)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = true, canadaPriceBook = true)))
      insertPriceToLocalizedPriceRepo()

      val priceViewModel = getProductPrice("1000", "GBP", "UK").head

      assert(priceViewModel.upc_code == "2000")
      assert(priceViewModel.current_price == sale_price_usd * britishExchangeRate)
      assert(priceViewModel.usd_current_price.get == sale_price_usd)
      assert(priceViewModel.msrp == list_price_usd * britishExchangeRate)
      assert(priceViewModel.usd_msrp.get == list_price_usd)
      assert(priceViewModel.markdown_price == sale_price_usd * britishExchangeRate)
      assert(priceViewModel.usd_markdown_price.get == sale_price_usd)
      assert(priceViewModel.privatesale_price == privatesale_price_usd * britishExchangeRate)
      assert(priceViewModel.usd_privatesale_price.get == privatesale_price_usd)
      assert(priceViewModel.list_price == list_price_usd * britishExchangeRate)
      assert(priceViewModel.usd_list_price.get == list_price_usd)
    }
  }

  "The Price Service when searching by product code and price book toggles are OFF" should {

    "return the United States price from PriceRepo when using US codes" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_OFF)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = false, canadaPriceBook = false)))
      insertPriceToPriceRepo()

      val priceViewModel = getProductPrice("1000").head

      assert(priceViewModel.upc_code == "2000")
      assert(priceViewModel.current_price == current_price_usd)
      assert(priceViewModel.usd_current_price.get == current_price_usd)
      assert(priceViewModel.msrp == msrp_usd)
      assert(priceViewModel.usd_msrp.get == msrp_usd)
      assert(priceViewModel.markdown_price == markdown_price_usd)
      assert(priceViewModel.usd_markdown_price.get == markdown_price_usd)
      assert(priceViewModel.privatesale_price == privatesale_price_usd)
      assert(priceViewModel.usd_privatesale_price.get == privatesale_price_usd)
      assert(priceViewModel.list_price == list_price_usd)
      assert(priceViewModel.usd_list_price.get == list_price_usd)
    }

    "return an exchange rate based price from PriceRepo when using Canadian codes" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_OFF)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = false, canadaPriceBook = false)))
      insertPriceToPriceRepo(current_price = current_price_usd, msrp = msrp_usd, markdown_price = markdown_price_usd, list_price = list_price_usd, sale_price = sale_price_usd, privatesale_price = privatesale_price_usd)

      val priceViewModel = getProductPrice("1000", "CAD", "CA").head

      assert(priceViewModel.upc_code == "2000")
      assert(priceViewModel.current_price == current_price_usd * canadianExchangeRate)
      assert(priceViewModel.usd_current_price.get == current_price_usd)
      assert(priceViewModel.msrp == msrp_usd * canadianExchangeRate)
      assert(priceViewModel.usd_msrp.get == msrp_usd)
      assert(priceViewModel.markdown_price == markdown_price_usd * canadianExchangeRate)
      assert(priceViewModel.usd_markdown_price.get == markdown_price_usd)
      assert(priceViewModel.privatesale_price == privatesale_price_usd * canadianExchangeRate)
      assert(priceViewModel.usd_privatesale_price.get == privatesale_price_usd)
      assert(priceViewModel.list_price == list_price_usd * canadianExchangeRate)
      assert(priceViewModel.usd_list_price.get == list_price_usd)
    }
  }

  "Price Service get price by product code for mobile app(useLocalizedPrice will be set to false)" should {

    "return an exchange rate based price for Canada from PriceRepo when use new collection toggle is OFF" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_OFF)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = false, canadaPriceBook = true)))
      insertPriceToPriceRepo(product_code = "3000", sale_price = 5500, current_price = 5500, markdown_price = 5500)

      val priceViewModel = getProductPrice("3000", "CAD", "CA", useLocalizedPrices = false).head

      assert(priceViewModel.product_code == "3000")
      assert(priceViewModel.current_price == 5500 * canadianExchangeRate)
    }

    "return an exchange rate based price for Canada from LocalizedPriceRepo when useNewCollection toggle is ON" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_OFF)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = true, canadaPriceBook = true)))
      insertPriceToLocalizedPriceRepo(product_code = "3000", sale_price = 5000)

      val priceViewModel = getProductPrice("3000", "CAD", "CA", useLocalizedPrices = false).head

      assert(priceViewModel.product_code == "3000")
      assert(priceViewModel.current_price == 5000 * canadianExchangeRate)
    }

  }

  "The Price Service when searching by product code and price book toggles are OFF" should {

    "return a localized price (Canadian) when dark launch is enabled" in {
      Mockito.when(toggleService.getPriceBookToggles(DARK_LAUNCH_ON)).thenReturn(Future.successful(PriceBookToggles(useNewCollection = false, canadaPriceBook = true)))
      insertPriceToLocalizedPriceRepo()
      insertPriceToLocalizedPriceRepo(country = "CA", list_price = list_price_cad, sale_price = sale_price_cad, privatesale_price = privatesale_price_cad)

      val priceViewModel = getProductPrice("1000", "CAD", "CA").head

      assert(priceViewModel.upc_code == "2000")
      assert(priceViewModel.current_price == current_price_cad)
      assert(priceViewModel.msrp == msrp_cad)
      assert(priceViewModel.markdown_price == markdown_price_cad)
      assert(priceViewModel.privatesale_price == privatesale_price_cad)
      assert(priceViewModel.list_price == list_price_cad)
    }

  }

  def getProductPrice(product_code: String, currency: String = "USD", country: String = "US", useLocalizedPrices: Boolean = true): Seq[PriceViewModel] = {
    val future = priceService.getProductPrice(product_code, currency, country, useLocalizedPrices)
    Await.result(future, WAIT_TIME)
  }

  def insertPriceToPriceRepo(product_code: String = "1000", upc_code: String = "2000",
                             current_price: Integer = current_price_usd, msrp: Integer = msrp_usd,
                             markdown_price: Integer = markdown_price_usd, list_price: Integer = list_price_usd,
                             sale_price: Integer = sale_price_usd, privatesale_price: Integer = privatesale_price_usd): Any = {
    val productPriceJson =
      s"""
         {
         	"product_code" : "$product_code",
         	"upc_code" : "$upc_code",
          "msrp": $msrp,
          "current_price" : $current_price,
          "markdown_price" : $markdown_price,
          "date_field" : "Sept. 30, 1993",
         	"list_price" : $list_price,
         	"sale_price" : $sale_price,
         	"privatesale_price" : $privatesale_price,
         	"is_deleted" : "N",
         	"final_sale_status" : "N",
         	"modified_on" : "2015-09-16 14:15:23",
         	"price_status" : "M"
         }
      """
    dbConnector.pricesCollection.insert(JSON.parse(productPriceJson).asInstanceOf[DBObject])
  }

  def insertPriceToLocalizedPriceRepo(product_code: String = "1000", upc_code: String = "2000", country: String = "US",
                                      list_price: Integer = list_price_usd, sale_price: Integer = sale_price_usd,
                                      privatesale_price: Integer = privatesale_price_usd) {
    dbConnector.localizedPricesCollection.insert(JSON.parse(MongoTestUtils.getLocalizedPricesCollectionJson(product_code, upc_code, country, list_price, sale_price, privatesale_price)).asInstanceOf[DBObject])
  }

  def insertInternationalRates = {
    dbConnector.intlRatesCollection.insert(JSON.parse(MongoTestUtils.getCanadaInternationalRateJson).asInstanceOf[DBObject])
    dbConnector.intlRatesCollection.insert(JSON.parse(MongoTestUtils.getGreatBritainInternationalRateJson).asInstanceOf[DBObject])
  }

}
