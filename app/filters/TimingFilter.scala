package filters

import javax.inject.Inject

import akka.stream.Materializer
import metrics.StatsDClientLike
import play.api.mvc.{ Filter, RequestHeader, Result }

import scala.concurrent.Future

class TimingFilter @Inject() (recorder: StatsDClientLike, val mat: Materializer) extends Filter {
  override def apply(next: RequestHeader => Future[Result])(req: RequestHeader): Future[Result] = {
    recorder.time("", req) {
      next(req)
    }
  }
}
