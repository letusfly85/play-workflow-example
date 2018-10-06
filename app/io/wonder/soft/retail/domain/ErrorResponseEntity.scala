package io.wonder.soft.retail.domain

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class ErrorResponseEntity(
  requestId: String,
  path: String = "",
  method: String = "",
  message: String = "",
  createdAt: DateTime
) extends Entity

object ErrorResponseEntity {
  import Entity._
  
  implicit def workflowReads: Reads[ErrorResponseEntity] = (
    (JsPath \ "request_id").read[String] and
      (JsPath \ "path").read[String] and
      (JsPath \ "method").read[String] and
      (JsPath \ "message").read[String] and
      (JsPath \ "created_at").read[DateTime]
    ) (ErrorResponseEntity.apply _)

  implicit def workflowWrites: Writes[ErrorResponseEntity] = (
    (JsPath \ "request_id").write[String] and
      (JsPath \ "path").write[String] and
      (JsPath \ "method").write[String] and
      (JsPath \ "message").write[String] and
      (JsPath \ "created_at").write[DateTime]
    ) (unlift(ErrorResponseEntity.unapply))
}
