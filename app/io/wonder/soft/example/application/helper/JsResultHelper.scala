package io.wonder.soft.example.application.helper

import play.api.libs.json.{JsError, JsResult, JsSuccess}

trait JsResultHelper {

  implicit def transJsResultToEither[T](jsResult: JsResult[T]): Either[JsError, T] = jsResult match {
    case JsSuccess(t, _) => Right(t)
    case JsError(errors) => Left(JsError(errors))
  }

}
