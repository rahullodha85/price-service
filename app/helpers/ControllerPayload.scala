package helpers

import models._
import play.api.libs.json.{ JsResultException, JsSuccess, _ }
import play.api.mvc.{ Result, _ }

import scala.concurrent._
import scala.util.control.NonFatal

trait ControllerPayload extends Controller {

  ////////////////////////
  //      RESPONSE      //
  ////////////////////////

  def writeResponseStore[T: Format](result: T)(implicit request: Request[_]): Result =
    writeResponseSuccess(result, Created)

  def writeResponseStores[T: Format](results: Seq[T])(implicit request: Request[_]): Result =
    writeResponses(results, Created)

  def writeResponseGet[T: Format](response: T, errors: Seq[ApiErrorModel] = Seq())(implicit request: Request[_]): Result =
    writeResponseSuccess(response, Ok, errors)

  def writeResponseUpdate[T: Format](result: T)(implicit request: Request[_]): Result =
    writeResponseSuccess(result, Ok)

  def writeResponseUpdates[T: Format](results: Seq[T])(implicit request: Request[_]): Result =
    writeResponses(results, Ok)

  def writeResponseRemove[T: Format](result: T)(implicit request: Request[_]): Result =
    writeResponseSuccess(result, Ok)

  def writeResponseSuccess[T: Format](result: T, status: Status, errors: Seq[ApiErrorModel] = Seq())(implicit request: RequestHeader): Result =
    writeResponse(status, constructResponseModel(result, errors))

  def writeResponseError(errors: Seq[ApiErrorModel], status: Status)(implicit request: RequestHeader): Result =
    formatResponse(constructErrorResponseModel(errors), status)

  def writeResponse(responseStatus: Status, body: ApiModel): Result =
    responseStatus.apply(Json.prettyPrint(Json.toJson(body))).as(JSON)

  def constructResultModel[T: Format](result: T): ApiResultModel = ApiResultModel(Json.toJson(result))

  def constructResponseModel[T: Format](
    result: T,
    errs:   Seq[ApiErrorModel] = Seq()
  )(implicit request: RequestHeader): ApiModel =
    ApiModel.apply(
      ApiRequestModel.fromReq(request),
      constructResultModel(result),
      errs
    )

  def constructErrorResponseModel(errs: Seq[ApiErrorModel])(implicit request: RequestHeader): ApiModel =
    ApiModel.apply(
      ApiRequestModel.fromReq(request),
      EmptyApiResultModel,
      errs
    )

  private def formatResponse(responseModel: ApiModel, response: Status): Result =
    response.apply(Json.prettyPrint(Json.toJson(responseModel))).as(JSON)

  private def writeResponses[T: Format](
    results: Seq[T],
    status:  Status
  )(implicit request: Request[_]): Result =
    formatResponse(constructResponseModel(results), status)

  ////////////////////////
  //     GET ITEMS      //
  ////////////////////////

  def getRequestItem[T: Format](implicit request: Request[AnyContent]): T = {
    val readJsonObject: Format[JsValue] = (__).format[JsValue]
    getRequestBodyAsJson(request).validate(readJsonObject) match {
      case JsError(e) => throw new JsResultException(e)
      case JsSuccess(hbcStatusObject, _) =>
        //Validate the hbcStatus object
        hbcStatusObject.validate[T] match {
          case JsSuccess(hbcStatus, _) => hbcStatus
          case JsError(e)              => throw new JsResultException(e)
        }
    }
  }

  ////////////////////////
  //      HELPERS       //
  ////////////////////////

  private def getRequestBodyAsJson(implicit request: Request[AnyContent]): JsValue =
    request.body.asJson.fold(throw new IllegalArgumentException("no json found"))(x => x)

  def findResponseStatus(implicit request: RequestHeader): PartialFunction[Throwable, (Status, ApiErrorModel)] = {
    case e: NoSuchElementException =>
      (NotFound, ApiErrorModel.fromExceptionAndMessage(
        "hbcStatus '" + e.getMessage + "' does not exist.", e
      ))
    case e: VerifyError =>
      (PreconditionFailed, ApiErrorModel.fromException(e))
    case e: ClassCastException =>
      (UnsupportedMediaType, ApiErrorModel.fromException(e))
    case e: IllegalArgumentException =>
      (BadRequest, ApiErrorModel.fromException(e))
    case e: JsResultException =>
      (BadRequest, ApiErrorModel.fromException(e))
    case e: TimeoutException =>
      (ServiceUnavailable, ApiErrorModel.fromException(e))
    case NonFatal(e) =>
      (InternalServerError, ApiErrorModel.fromExceptionAndMessage(
        "Yikes! An error has occurred: " + e.getMessage, e
      ))
  }

  def handlerForRequest(implicit req: RequestHeader): (Status, ApiErrorModel) => Result = {
    (status, err) =>
      writeResponse(
        status,
        constructErrorResponseModel(Seq(err))
      )
  }

  def defaultExceptionHandler(implicit req: RequestHeader): PartialFunction[Throwable, Result] =
    findResponseStatus andThen handlerForRequest(req).tupled
}

object ControllerPayloadLike extends ControllerPayload
