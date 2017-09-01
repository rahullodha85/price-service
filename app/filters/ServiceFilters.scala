package filters

import javax.inject.Inject

import monitoring.IdentifyRequestFilter
import play.api.http.HttpFilters

// common logging and metrics for all requests
class ServiceFilters @Inject() (
    identifyRequest: IdentifyRequestFilter,
    timing:          TimingFilter,
    increment:       IncrementFilter,
    exception:       ExceptionFilter
) extends HttpFilters {
  val filters = Seq(identifyRequest, timing, increment, exception)
}