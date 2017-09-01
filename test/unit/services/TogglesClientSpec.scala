package unit.services

import play.api.Configuration
import constants.Banner
import helpers.ConfigurationFactory
import monitoring.CorrelationIdWSClient
import org.scalatest.mock.MockitoSugar
import org.scalatest.{ Matchers, WordSpec }
import services.ToggleClientFactory
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers.any

class TogglesClientSpec extends WordSpec with Matchers with MockitoSugar {

  ".apply" when {

    // banner-specificity (one instance per banner)

    "called with the same banner" should {
      "always return the same instance (the same in-memory cache)" in {
        val factory = mock[ConfigurationFactory]
        val mockConfig = mock[Configuration]
        when(factory.getConfig(any(classOf[Banner.Value]))).thenReturn(mockConfig)
        when(mockConfig.getString("webservices.toggles.url")).thenReturn(Option("dummyURL"))

        val toggleClientFactory = new ToggleClientFactory(factory, mock[CorrelationIdWSClient])

        assert(toggleClientFactory.getClient(Banner.Saks) == toggleClientFactory.getClient(Banner.Saks))
      }
    }

    "called with different banners" should {
      "return different instances" in {
        val factory = mock[ConfigurationFactory]
        val mockConfig = mock[Configuration]
        when(factory.getConfig(any(classOf[Banner.Value]))).thenReturn(mockConfig)
        when(mockConfig.getString("webservices.toggles.url")).thenReturn(Option("dummyURL"))

        val toggleClientFactory = new ToggleClientFactory(factory, mock[CorrelationIdWSClient])

        assert(toggleClientFactory.getClient(Banner.Saks) != toggleClientFactory.getClient(Banner.Lat))
      }
    }

  }
}
