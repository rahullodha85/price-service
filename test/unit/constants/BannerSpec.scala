package unit.constants

import com.typesafe.config.{ Config, ConfigException }
import constants.{ Banner, UnknownBannerException }
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{ Matchers, WordSpec }

class BannerSpec extends WordSpec with Matchers with MockitoSugar {
  val configName: String = "hbc.banner"
  val config = mock[Config]

  ".apply" when {

    "given an unknown value" should {
      "throw exception" in {
        intercept[UnknownBannerException] {
          Banner(Some(""), config)
        }
      }
    }

    "given a banner input is missing and config hbc.banner is valid" should {
      "return banner as string" in {

        when(config.getString(configName)).thenReturn("s5a")
        assert(Banner(None, config).equals(Banner.Saks))
      }
    }

    "given a banner input is missing and config hbc.banner is missing" should {
      "throw exception" in {

        when(config.getString(configName)).thenThrow(new ConfigException.Missing(configName))

        intercept[UnknownBannerException] {
          Banner(None, config)
        }
      }
    }

    "given a banner input is missing and config hbc.banner is unknown" should {
      "throw exception" in {

        val config = mock[Config]

        when(config.getString(configName)).thenReturn("UnknownBanner")

        intercept[UnknownBannerException] {
          Banner(None, config)
        }
      }
    }

    "given a known banner" should {
      "return banner as string" in {
        assert(Banner(Some("s5a")).equals(Banner.Saks))
      }
    }

  }
}
