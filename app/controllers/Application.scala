package controllers

import javax.inject._

import ch.qos.logback.classic.{ Level, LoggerContext }
import com.iheart.playSwagger.SwaggerSpecGenerator
import constants.Banner
import helpers.ControllerPayloadLike._
import no.samordnaopptak.json.J
import org.slf4j.LoggerFactory
import play.api._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.JsObject
import play.api.mvc._
import services.ToggleClientFactoryLike

import scala.concurrent.Future

@Singleton
class Application @Inject() (
    timeoutHelper:       helpers.ControllerTimeout,
    toggleClientFactory: ToggleClientFactoryLike
) extends Controller {

  import timeoutHelper._

  def index = Action.async {
    implicit request =>
      timeout {
        val response = "price-service is up and running!"
        writeResponseGet(response)
      }
  }

  def changeLogLevelGet(levelString: String) = changeLogLevel(levelString)

  def changeLogLevel(levelString: String) = Action.async {
    implicit request =>
      timeout {
        Logger.debug("price-service change log level called")

        val level = Level.toLevel(levelString)
        val loggerCtx = LoggerFactory.getILoggerFactory.asInstanceOf[LoggerContext]

        loggerCtx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(level)
        loggerCtx.getLogger("application").setLevel(level)
        loggerCtx.getLogger("play").setLevel(level)

        val response = s"Log level changed to $level"
        writeResponseGet(response)
      }
  }

  def clearToggles(name: Option[String], banner: Banner.Value) = Action.async {
    implicit request =>
      implicit val bannerValue = banner

      timeout {
        toggleClientFactory.getClient.clearCache(name)
        writeResponseGet("done!")
      }
  }

  def toggles(name: Option[String], banner: Banner.Value) = Action.async {
    implicit request =>
      implicit val bannerValue = banner

      withTimeout {
        name.map(toggleName => toggleClientFactory.getClient.getToggle(toggleName).map(toggle => Seq(toggle.toList).flatten))
          .getOrElse(toggleClientFactory.getClient.getToggles)
          .map(response => writeResponseGet(response))
      }
  }

  val swaggerInfoObject = J.obj(
    "info" -> J.obj(
      "title" -> "Generated Swagger API",
      "version" -> "1.0"
    )
  )

  def apiDoc = Action.async { implicit request =>
    try {
      val generatedSwaggerDocs = no.samordnaopptak.apidoc.ApiDocUtil.getSwaggerDocs("/")
      val json = generatedSwaggerDocs ++ swaggerInfoObject
      Future(Ok(json.asJsValue))
    } catch {
      case e: Exception =>
        println(s"ERROR: $e")
        throw e
    }
  }

  implicit val cl = getClass.getClassLoader
  val domainPackage = Seq("models")
  private lazy val generator = SwaggerSpecGenerator(domainPackage: _*)

  def specs = {
    Action.async { _ =>
      Future.fromTry(generator.generate())
        .map { newSwagger =>

          val keys = List("definitions", "paths")

          val oldSwagger = no.samordnaopptak.apidoc.ApiDocUtil.getSwaggerDocs("/").asJsValue

          val newSwaggerJsonKeyValMap = newSwagger.value
          val oldSwaggerJsonKeyValMap = oldSwagger.value

          val combinedPathDefs = for (key <- keys) yield {
            (
              key,
              newSwaggerJsonKeyValMap.get(key).map(_.as[JsObject])
              .foldLeft(oldSwaggerJsonKeyValMap.get(key).map(_.as[JsObject]).get)(_ ++ _)
            )
          }

          val unorganizedResult = newSwagger ++ JsObject(combinedPathDefs)
          val organizedKeys =
            List(
              "swagger",
              "info",
              "tags",
              "consumes",
              "produces",
              "paths",
              "definitions"
            )

          val organizedPairs = (for (key <- organizedKeys) yield {
            unorganizedResult.value.get(key) map (value => (key, value))
          }).flatten

          Ok(JsObject(organizedPairs))
        }
    }
  }

  def renderSwaggerUi = Action(Redirect("/admin/api-docs/ui/index.html?url=/admin/api-docs"))

  def getApiDocs() = Action.async { implicit request =>
    Future.fromTry(generator.generate()).map(Ok(_))
  }
}
