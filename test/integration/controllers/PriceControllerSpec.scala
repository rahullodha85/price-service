package integration.controllers

import org.scalatest._
import play.api.test.{ FakeHeaders, FakeRequest }
import play.api.test.Helpers._
import play.api.Play
import utils.MongoTestUtils
import utils.TestUtils.application

class PriceControllerSpec extends WordSpec
    with ShouldMatchers
    with BeforeAndAfterAll {

  val app = application()

  override def beforeAll() {
    Play.start(app)
    MongoTestUtils.initTest
  }

  override def afterAll() {
    Play.stop(app)
    MongoTestUtils.cleanUp
  }

  "When running Price Controller" should {
    "test price endpoint with acceptable product code" in {
      val result = route(app, FakeRequest(GET, "/price-service/s5a/prices?product_code=123"))

      result match {
        case Some(r) => status(r) shouldBe 200
        case _       => assert(false)
      }
    }

    "test price *deprecated* endpoint with acceptable productCode" in {
      val result = route(app, FakeRequest(GET, "/v1/price-service/product/123"))

      result match {
        case Some(r) => status(r) shouldBe 200
        case _       => assert(false)
      }
    }

    "test price *deprecated* endpoint with bad/irregular product code" in {
      val result = route(app, FakeRequest(GET, "/v1/price-service/product/abc"))

      result match {
        case Some(r) => status(r) shouldBe 404
        case _       => assert(true)
      }
    }

    "test price endpoint with acceptable currencyCode" in {
      val result = route(app, FakeRequest(GET, "/price-service/intl-rate/currency/abc"))

      result match {
        case Some(r) => status(r) shouldBe 200
        case _       => assert(false)
      }
    }

    "test price endpoint with bad/irregular currencyCode" in {
      val result = route(app, FakeRequest(GET, "/price-service/intl-rate/currency/123"))

      result match {
        case Some(r) => status(r) shouldBe 404
        case _       => assert(true)
      }
    }

    "test price endpoint with acceptable countryCode" in {
      val result = route(app, FakeRequest(GET, "/price-service/intl-rate/country/abc"))

      result match {
        case Some(r) => status(r) shouldBe 200
        case _       => assert(false)
      }
    }

    "test price endpoint with bad/irregular countryCode" in {
      val result = route(app, FakeRequest(GET, "/price-service/intl-rate/country/123"))

      result match {
        case Some(r) => status(r) shouldBe 404
        case _       => assert(true)
      }
    }
  }
}
