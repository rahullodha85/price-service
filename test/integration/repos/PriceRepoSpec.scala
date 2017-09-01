package integration.repos

import com.mongodb.DBObject
import com.mongodb.util.JSON
import constants.Banner
import metrics.NoOpStatsDClient
import models.PriceModel
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mock.MockitoSugar
import play.api.Play
import repos.{ DBConnectorFactoryLike, DbConnector, PriceRepo }
import utils.{ BannerAgnostic, MongoTestUtils }
import utils.TestUtils.application

class PriceRepoSpec extends WordSpec
    with BeforeAndAfterAll
    with MockitoSugar
    with BannerAgnostic { self =>

  var priceRepo: PriceRepo = _
  val app = application()
  val dbConnector = app.injector.instanceOf[DbConnector]
  var dbConnectorFactory: DBConnectorFactoryLike = mock[DBConnectorFactoryLike]

  override def beforeAll() {
    Play.start(app)
    MongoTestUtils.initTest

    dbConnector.pricesCollection.insert(JSON.parse(MongoTestUtils.getPricesCollectionJson).asInstanceOf[DBObject])
    when(dbConnectorFactory.getDb(any(classOf[Banner.Value]))).thenReturn(dbConnector)

    priceRepo = new PriceRepo(NoOpStatsDClient, dbConnectorFactory)
  }

  override def afterAll() {
    dbConnector.pricesCollection.drop()
    Play.stop(app)
    MongoTestUtils.cleanUp
  }

  "When querying Price Repo" should {

    "test price endpoint with valid productCode" in {
      val result = priceRepo.getPriceByProductCode("123")

      assert(result.nonEmpty)
      assert(result.head == PriceModel("123", "999", "M", 21000, None, 52500, None, Some("2015-09-16 14:15:23"), 21000, None, 10500, None, 52500, None, "", "Y", None))
    }

    "test price endpoint with invalid productCode" in {
      val result = priceRepo.getPriceByProductCode("456")

      assert(result.isEmpty)
    }
  }

}
