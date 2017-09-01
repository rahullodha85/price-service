package unit.helpers

import com.typesafe.config.Config
import constants.Banner
import helpers.BannerAwareConfig
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar

class BannerAwareConfigSpec extends WordSpec with MockitoSugar {

  ".getString" when {
    "given a banner" when {
      "there is config override for that banner" should {
        "return the banner-override config value" in {
          val config = mock[Config]
          val bannerAwareConfig = new BannerAwareConfig(Banner.Saks, config)

          mockProperty(config, "hbc.banner", "lat")
          mockProperty(config, "toggleServiceUrl", "not banner override")
          mockProperty(config, "s5a.toggleServiceUrl", "banner override")

          assert(bannerAwareConfig.getString("toggleServiceUrl").get.equals("banner override"))
        }
      }

      "there is no config override for that banner" should {
        "return the banner-agnostic config value" in {
          val config = mock[Config]
          val bannerAwareConfig = new BannerAwareConfig(Banner.Saks, config)

          mockProperty(config, "hbc.banner", "lat") // default banner
          mockProperty(config, "toggleServiceUrl", "NOT banner override")

          assert(bannerAwareConfig.getString("toggleServiceUrl").get.equals("NOT banner override"))
        }
      }

      "there is no config override for that banner and no banner-agnostic config value" should {
        "return the banner-agnostic config value" in {
          val config = mock[Config]
          val bannerAwareConfig = new BannerAwareConfig(Banner.Saks, config)

          mockProperty(config, "hbc.banner", "lat") // default banner

          assert(bannerAwareConfig.getString("toggleServiceUrl").isEmpty)
        }
      }
    }
  }

  private def mockProperty(config: Config, k: String, v: String) = {
    when(config.getString(k)).thenReturn(v)
    when(config.hasPathOrNull(k)).thenReturn(true)
  }

  private def mockProperty(config: Config, k: String, v: Int) = {
    when(config.getInt(k)).thenReturn(v)
    when(config.hasPathOrNull(k)).thenReturn(true)
  }

  ".getInt" when {
    "given a banner" when {
      "there is config override for that banner" should {
        "return the banner-override config value" in {
          val config = mock[Config]
          val bannerAwareConfig = new BannerAwareConfig(Banner.Saks, config)

          val BANNER_AGNOSTIC_FALLBACK = 100
          val BANNER_OVERRIDE = 200
          mockProperty(config, "hbc.banner", "lat") // default banner
          mockProperty(config, "timeout", BANNER_AGNOSTIC_FALLBACK)
          mockProperty(config, "s5a.timeout", BANNER_OVERRIDE)

          assert(bannerAwareConfig.getInt("timeout").get.equals(BANNER_OVERRIDE))
        }
      }

      "there is no config override for that banner" should {
        "return the banner-agnostic config value" in {
          val config = mock[Config]
          val bannerAwareConfig = new BannerAwareConfig(Banner.Saks, config)

          val BANNER_AGNOSTIC_FALLBACK = 100
          mockProperty(config, "hbc.banner", "lat") // default banner
          mockProperty(config, "timeout", BANNER_AGNOSTIC_FALLBACK)

          assert(bannerAwareConfig.getInt("timeout").get.equals(BANNER_AGNOSTIC_FALLBACK))
        }
      }

      "there is no config override for that banner and no banner-agnostic config value" should {
        "return the banner-agnostic config value" in {
          val config = mock[Config]
          val bannerAwareConfig = new BannerAwareConfig(Banner.Saks, config)

          mockProperty(config, "hbc.banner", "lat") // default banner

          assert(bannerAwareConfig.getInt("timeout").isEmpty)
        }
      }
    }
  }

}
