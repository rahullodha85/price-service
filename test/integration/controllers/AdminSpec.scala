package integration.controllers

import play.api.test.Helpers._
import play.api.test._
import play.api.Play
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpec }
import utils.TestUtils.application

class AdminSpec extends WordSpec
    with Matchers
    with BeforeAndAfterAll {

  val app = application()

  override def beforeAll() = {
    Play.start(app)
  }

  override def afterAll() = {
    Play.stop(app)
  }

  "Admin controller" should {
    "show **pong** when /admin/ping endpoint is called" in {
      val ping = route(app, FakeRequest(GET, "/admin/ping")).get
      status(ping) shouldBe OK
      contentAsString(ping).contains("pong") shouldBe true
    }

    "show **JVM Stats** when /admin/jvmstats endpoint is called" in {
      val jvmstats = route(app, FakeRequest(GET, "/admin/jvmstats")).get
      status(jvmstats) shouldBe OK
      contentAsString(jvmstats).contains("jvm_num_cpus") shouldBe true
    }
  }
}