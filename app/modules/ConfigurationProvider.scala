package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{ Configuration, Environment }
import repos.{ IPriceRepo, PriceBookRepo, PriceRepo }
import services.{ IPriceService, PriceService }

class ConfigurationProvider(
    environment:   Environment,
    configuration: Configuration
) extends AbstractModule {
  def configure() = {
    bind(classOf[IPriceRepo]).annotatedWith(Names.named("legacyPriceRepo")).to(classOf[PriceRepo])
    bind(classOf[IPriceRepo]).annotatedWith(Names.named("priceBookRepo")).to(classOf[PriceBookRepo])
    bind(classOf[IPriceService]).to(classOf[PriceService])
  }
}