package io.wonder.soft.retail.application.helper

import play.api.libs.json.{JsError, JsResult, JsSuccess}

trait JsResultHelper {

  implicit def transJsResultToEither[T](jsResult: JsResult[T]): Either[Exception, T] = jsResult match {
    case JsSuccess(t, _) => Right(t)

    case JsError(errors) =>
      //refs: https://discuss.lightbend.com/t/exception-handling-in-play-framework-parsing-json/625
      Left(new Exception(JsError.toJson(errors).toString()))
  }

}
