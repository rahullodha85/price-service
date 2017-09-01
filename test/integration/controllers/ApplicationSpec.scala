package integration.controllers

import org.scalatest.{ Matchers, WordSpec }
import play.api.test.Helpers._
import play.api.test._

import scala.language.postfixOps

class ApplicationSpec extends WordSpec
    with Matchers {

  import utils.TestUtils._

  val app = application()

  "Application Controller" should {
    "send 404 on a bad request" in withPlay() {
      val result = route(app, FakeRequest(GET, "/boom")).get
      status(result) shouldBe NOT_FOUND
    }

    "render the index page" in withPlay() {
      val index = route(app, FakeRequest(GET, "/admin/status")).get

      status(index) shouldBe OK
      contentType(index).get == "application/json" shouldBe true
      (contentAsJson(index) \ "response" \ "results").as[String] == "price-service is up and running!" shouldBe true
    }

    "get Swagger spec" in withPlay() {
      val index = route(app, FakeRequest(GET, "/admin/api-docs")).get

      status(index) shouldBe OK
      contentType(index).get == "application/json" shouldBe true
      (contentAsJson(index) \ "swagger").as[String] == "2.0" shouldBe true
    }

    "change the log Level" in withPlay() {
      val changeLog = route(app, FakeRequest(PUT, "/admin/logLevel/WARN")).get
      status(changeLog) shouldBe OK
      contentType(changeLog).get == "application/json" shouldBe true
      (contentAsJson(changeLog) \ "response" \ "results").as[String] == "Log level changed to WARN" shouldBe true
    }

    "not process incorrect log Level" in withPlay() {
      val result = route(app, FakeRequest(GET, "/admin/logLevel/WARN2")).get
      status(result) shouldBe NOT_FOUND
    }
  }
}
