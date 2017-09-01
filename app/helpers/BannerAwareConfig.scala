package helpers

import com.google.inject.{ ImplementedBy, Inject, Singleton }
import com.typesafe.config.{ Config, ConfigFactory }
import constants.Banner
import play.api.Configuration

class BannerAwareConfig(val banner: Banner.Value = Banner(), override val underlying: Config) extends Configuration(underlying) {

  private def bannerPrefixed(param: String) = s"${banner.toString}.$param"

  override def getString(path: String, validValues: Option[Set[String]]): Option[String] = {
    super.getString(bannerPrefixed(path), validValues)
      .orElse(super.getString(path, validValues))
  }

  override def getInt(path: String): Option[Int] = {
    super.getInt(bannerPrefixed(path))
      .orElse(super.getInt(path))
  }

  override def getConfig(path: String): Option[Configuration] = {
    super.getConfig(bannerPrefixed(path))
      .orElse(super.getConfig(path))
  }

}

@ImplementedBy(classOf[ConfigurationFactory])
trait ConfigurationFactoryLike {

  def getConfig(banner: Banner.Value): Configuration

}

@Singleton
class ConfigurationFactory @Inject() (configuration: Configuration) extends ConfigurationFactoryLike {

  private val bannerSpecificConfigs: Map[Banner.Value, Configuration] = {
    Banner.values.toList.
      map { banner =>
        banner -> new BannerAwareConfig(banner, configuration.underlying)
      }.
      toMap
  }

  def getConfig(banner: Banner.Value): Configuration = {
    bannerSpecificConfigs(banner)
  }

}