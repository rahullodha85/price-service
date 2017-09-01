package services

import com.google.inject.name.Named
import com.google.inject.{ ImplementedBy, Inject, Singleton }
import constants.Banner
import helpers.ConfigurationFactory
import play.api.libs.ws.WSClient

@ImplementedBy(classOf[ToggleClientFactory])
trait ToggleClientFactoryLike {

  def getClient(implicit banner: Banner.Value): TogglesClient

}

@Singleton
class ToggleClientFactory @Inject() (configFactory: ConfigurationFactory, @Named("CorrelationID") correlationIdWSClient: WSClient) extends ToggleClientFactoryLike {

  private val bannerSpecificToggleClients: Map[Banner.Value, TogglesClient] = {
    Banner.values.toList.
      map { banner =>
        banner -> new TogglesClient(configFactory.getConfig(banner), correlationIdWSClient)
      }.
      toMap
  }

  def getClient(implicit banner: Banner.Value): TogglesClient = {
    bannerSpecificToggleClients.get(banner).get
  }

}