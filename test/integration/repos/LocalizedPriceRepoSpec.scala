package integration.repos

import com.mongodb.DBObject
import com.mongodb.util.JSON
import constants.Banner
import metrics.NoOpStatsDClient
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import play.api.Play
import repos.{ DBConnectorFactoryLike, DbConnector, PriceBookRepo }
import utils.{ BannerAgnostic, MongoTestUtils }
import utils.TestUtils.application
import org.mockito.ArgumentMatchers.any

class LocalizedPriceRepoSpec extends WordSpec
    with ShouldMatchers
    with BeforeAndAfterAll
    with MockitoSugar
    with Matchers
    with BannerAgnostic {

  var priceBookRepo: PriceBookRepo = _
  val app = application()
  val dbConnector = app.injector.instanceOf[DbConnector]
  var dbConnectorFactory: DBConnectorFactoryLike = mock[DBConnectorFactoryLike]

  override def beforeAll() {
    Play.start(app)
    MongoTestUtils.initTest
    dbConnector.pricesCollection.insert(JSON.parse(MongoTestUtils.getPricesCollectionJson).asInstanceOf[DBObject])
    dbConnector.intlRatesCollection.insert(JSON.parse(MongoTestUtils.getCanadaInternationalRateJson).asInstanceOf[DBObject])
    dbConnector.intlRatesCollection.insert(JSON.parse(MongoTestUtils.getGreatBritainInternationalRateJson).asInstanceOf[DBObject])
    when(dbConnectorFactory.getDb(any(classOf[Banner.Value]))).thenReturn(dbConnector)

    priceBookRepo = new PriceBookRepo(NoOpStatsDClient, dbConnectorFactory)
  }

  override def afterAll() {
    dbConnector.pricesCollection.drop()
    dbConnector.intlRatesCollection.drop()
    dbConnector.localizedPricesCollection.drop()
    Play.stop(app)
    MongoTestUtils.cleanUp
  }

  "When querying Price Repo" should {

    "test price endpoint with valid productCode" in {
      dbConnector.localizedPricesCollection.insert(JSON.parse(MongoTestUtils.getLocalizedPricesCollectionJson(list_price = 7500, sale_price = 3000, privatesale_price = 7500)).asInstanceOf[DBObject])

      val priceModels = priceBookRepo.getPriceByProductCode("1000")

      assert(priceModels.size == 1)
      val price = priceModels.head
      assert(price.product_code == "1000")
      assert(price.upc_code == "2000")
      assert(price.markdown_price == 3000)
      assert(price.current_price == 3000)
      assert(price.list_price == 7500)
      assert(price.msrp == 7500)
      assert(price.final_sale_status == "N")
      assert(price.privatesale_price == 7500)
      assert(price.price_status == "M")
    }

    "test bulk price endpoint with valid productCodes" in {
      dbConnector.localizedPricesCollection.insert(JSON.parse(MongoTestUtils.getLocalizedPricesCollectionJson(product_code = "1001", upc_code = "2001", list_price = 7501, sale_price = 3001, privatesale_price = 7501)).asInstanceOf[DBObject])
      dbConnector.localizedPricesCollection.insert(JSON.parse(MongoTestUtils.getLocalizedPricesCollectionJson(product_code = "1002", upc_code = "2002", list_price = 7502, sale_price = 3002, privatesale_price = 7502)).asInstanceOf[DBObject])
      dbConnector.localizedPricesCollection.insert(JSON.parse(MongoTestUtils.getLocalizedPricesCollectionJson(product_code = "1003", upc_code = "2003", country = "CA", list_price = 7503, sale_price = 3003, privatesale_price = 7503)).asInstanceOf[DBObject])

      val priceModels = priceBookRepo.getPriceByProductCodes(Seq("1001", "1002", "1003"))

      assert(priceModels.size == 2)
      val price1 = priceModels(0)
      assert(price1.product_code == "1001")
      assert(price1.upc_code == "2001")
      assert(price1.markdown_price == 3001)
      assert(price1.current_price == 3001)
      assert(price1.list_price == 7501)
      assert(price1.msrp == 7501)
      assert(price1.final_sale_status == "N")
      assert(price1.privatesale_price == 7501)
      assert(price1.price_status == "M")
      val price2 = priceModels(1)
      assert(price2.product_code == "1002")
      assert(price2.upc_code == "2002")
      assert(price2.markdown_price == 3002)
      assert(price2.current_price == 3002)
      assert(price2.list_price == 7502)
      assert(price2.msrp == 7502)
      assert(price2.final_sale_status == "N")
      assert(price2.privatesale_price == 7502)
      assert(price2.price_status == "M")
    }

    "test price endpoint with invalid productCode" in {
      val result = priceBookRepo.getPriceByProductCode("DOES_NOT_EXIST")
      assert(result.isEmpty)
    }

    "test price endpoint with Canadian country code" in {
      dbConnector.localizedPricesCollection.insert(JSON.parse(MongoTestUtils.getLocalizedPricesCollectionJson(list_price = 7500, sale_price = 3000, privatesale_price = 7500)).asInstanceOf[DBObject])
      dbConnector.localizedPricesCollection.insert(JSON.parse(MongoTestUtils.getLocalizedPricesCollectionJson(country = "CA", list_price = 10000, sale_price = 9000, privatesale_price = 7500)).asInstanceOf[DBObject])

      val priceModel = priceBookRepo.getPriceByProductCode("1000", "CA")

      val price = priceModel.head
      assert(price.product_code == "1000")
      assert(price.upc_code == "2000")
      assert(price.markdown_price == 9000)
      assert(price.current_price == 9000)
      assert(price.list_price == 10000)
      assert(price.msrp == 10000)
      assert(price.final_sale_status == "N")
      assert(price.privatesale_price == 7500)
      assert(price.price_status == "M")
    }
  }
}
