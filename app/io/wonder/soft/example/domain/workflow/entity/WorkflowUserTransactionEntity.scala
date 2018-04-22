package io.wonder.soft.example.domain.workflow.entity

import io.wonder.soft.example.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowUserTransactionEntity(
    transactionId: String,
    transition: WorkflowTransitionEntity,
) extends Entity

object WorkflowUserTransactionEntity {
  implicit def orderTransitionReads: Reads[WorkflowUserTransactionEntity] =
    (
      (JsPath \ "transaction_id").read[String] and
        (JsPath \ "transition").read[WorkflowTransitionEntity]
    )(WorkflowUserTransactionEntity.apply _)

  implicit def orderTransitionWrites: Writes[WorkflowUserTransactionEntity] =
    (
      (JsPath \ "transaction_id").write[String] and
        (JsPath \ "transition").write[WorkflowTransitionEntity]
    )(unlift(WorkflowUserTransactionEntity.unapply))
}
