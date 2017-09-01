package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import monitoring.CorrelationIdWSClient
import play.api.libs.ws.WSClient
import play.api.{ Configuration, Environment }

class CorrelationIDWSClientProvider(
    environment:   Environment,
    configuration: Configuration
) extends AbstractModule {
  def configure() = {
    bind(classOf[WSClient]).annotatedWith(Names.named("CorrelationID")).to(classOf[CorrelationIdWSClient])
  }
}