package constants

import com.typesafe.config.{ Config, ConfigFactory }
import play.api.mvc.PathBindable

import scala.util.Try

object Banner extends Enumeration {
  val Off5th = Value("o5a")
  val Lat = Value("lat")
  val Saks = Value("s5a")

  def apply(bannerOption: Option[String] = None, config: Config = ConfigFactory.load()): Banner.Value = {
    val bannerName: Option[String] = bannerOption orElse
      Try(Some(config.getString("hbc.banner"))).getOrElse(None)

    bannerName match {
      case Some(name) if isValid(name) => Banner.withName(name)
      case _                           => throw UnknownBannerException()
    }
  }

  private def isValid(value: String): Boolean = {
    Banner.values.toList.map(_.toString).contains(value)
  }

  implicit object bindable extends PathBindable.Parsing[Banner.Value](
    withName(_), _.toString, (k: String, e: Exception) => "Cannot parse %s as Banner.Value: %s".format(k, e.getMessage())
  )

}

case class UnknownBannerException() extends IllegalArgumentException