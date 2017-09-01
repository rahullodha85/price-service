package monitoring

import java.util.UUID

import akka.stream.Materializer
import com.google.inject.Inject
import monitoring.Constants._
import org.slf4j.MDC
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.Future

class IdentifyRequestFilter @Inject() (val mat: Materializer) extends Filter {
  def apply(next: RequestHeader => Future[Result])(req: RequestHeader): Future[Result] = {
    val correlationID = req.headers.get(CORRELATION_ID) getOrElse UUID.randomUUID().toString
    MDC.put(CORRELATION_ID, correlationID)
    MDC.put(X_FORWARDED_FOR, req.headers.get(X_FORWARDED_FOR).getOrElse("?"))
    MDC.put(USER_AGENT, req.headers.get(USER_AGENT).getOrElse("?"))
    MDC.put(REMOTE_ADDRESS, req.remoteAddress)
    next(req)
  }
}

