package controllers

import com.google.inject.{ Inject, Singleton }
import helpers.AdminHelper._
import helpers.ControllerPayload
import play.api.Play.current
import play.api._
import play.api.libs.json._
import play.api.mvc._

@Singleton
class Admin @Inject() (
    timeoutHelper: helpers.ControllerTimeout
) extends ControllerPayload {

  import timeoutHelper._

  def ping = Action.async {
    implicit request =>
      timeout {
        Logger.debug("ping")
        writeResponseGet("pong")
      }
  }

  def jvmstats = Action.async {
    implicit request =>
      timeout {
        Logger.debug("jvmstats")
        writeResponseGet(Json.toJson(extractJvmStats()))
      }
  }

  def dispatcherSettings = Action.async {
    implicit request =>
      timeout {
        Logger.debug("default dispatcher settings")
        val settings = current.configuration.underlying.getAnyRef("play.akka.actor").toString
        writeResponseGet(settings)
      }
  }
}