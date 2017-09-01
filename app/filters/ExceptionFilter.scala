package filters

import akka.stream.Materializer
import com.google.inject.Inject
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ExceptionFilter @Inject() (val mat: Materializer) extends Filter {
  override def apply(next: RequestHeader => Future[Result])(req: RequestHeader): Future[Result] =
    next(req) recover (helpers.ControllerPayloadLike.defaultExceptionHandler(req))
}
