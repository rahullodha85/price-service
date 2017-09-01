package utils

import constants.Banner

trait BannerAgnostic {
  implicit val irrelevantBanner = Banner()
}
