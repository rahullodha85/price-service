package services

import com.google.inject.Inject
import constants.Banner
import models.{ PriceBookToggles, Toggle }
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class ToggleService @Inject() (toggleClientFactory: ToggleClientFactoryLike) {
  val NEW_PRICE_COLLECTION_TOGGLE = "USE_NEW_PRICE_COLLECTION"
  val CANADA_PRICE_BOOK_TOGGLE = "CANADA_PRICE_BOOK"
  val CANADA_PRICE_BOOK_DARK_LAUNCH = "CANADA_PRICE_BOOK_DARK_LAUNCH"

  def getToggleState(toggle: Option[Toggle]): Boolean = {
    toggle match {
      case Some(t) => t.toggle_state
      case None    => false
    }
  }

  def getPriceBookToggles(darkLaunch: Boolean)(implicit banner: Banner.Value): Future[PriceBookToggles] = {
    val togglesClient: TogglesClient = toggleClientFactory.getClient(banner)
    for {
      useNewCollectionToggle <- togglesClient.getToggle(NEW_PRICE_COLLECTION_TOGGLE)
      canadaPriceBookDarkLaunchToggle <- togglesClient.getToggle(CANADA_PRICE_BOOK_DARK_LAUNCH)
      canadaPriceBookToggle <- togglesClient.getToggle(CANADA_PRICE_BOOK_TOGGLE)
    } yield {
      val canadaPriceBookToggleOn: Boolean = getToggleState(canadaPriceBookToggle) || (darkLaunch && getToggleState(canadaPriceBookDarkLaunchToggle))
      PriceBookToggles(getToggleState(useNewCollectionToggle), canadaPriceBookToggleOn)
    }

  }

}
