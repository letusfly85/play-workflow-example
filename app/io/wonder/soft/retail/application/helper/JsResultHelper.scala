package io.wonder.soft.retail.application.helper

import io.wonder.soft.retail.domain.ErrorResponseEntity
import org.joda.time.DateTime
import play.api.Logging
import play.api.libs.json.{JsError, JsResult, JsSuccess, Json}
import play.api.mvc.Result
import play.api.mvc.Results._

trait JsResultHelper extends Logging {

  implicit def transJsResultToEither[T](jsResult: JsResult[T]): Either[Exception, T] = jsResult match {
    case JsSuccess(t, _) => Right(t)

    case JsError(errors) =>
      //refs: https://discuss.lightbend.com/t/exception-handling-in-play-framework-parsing-json/625
      Left(new Exception(JsError.toJson(errors).toString()))
  }

  def errorHandler(e: Exception): Result  = {
    logger.info(e.getMessage)
    val errorEntity = ErrorResponseEntity(
      message = e.getMessage,
      createdAt = new DateTime()
    )
    InternalServerError(Json.toJson(errorEntity))
  }

}
