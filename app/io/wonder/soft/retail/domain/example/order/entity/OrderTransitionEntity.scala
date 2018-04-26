package io.wonder.soft.retail.domain.example.order.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.entity.WorkflowTransitionEntity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class OrderTransitionEntity(
    transactionId: String,
    transition: WorkflowTransitionEntity,
) extends Entity

object OrderTransitionEntity {
  implicit def orderTransitionReads: Reads[OrderTransitionEntity] =
    (
      (JsPath \ "transaction_id").read[String] and
        (JsPath \ "transition").read[WorkflowTransitionEntity]
    )(OrderTransitionEntity.apply _)

  implicit def orderTransitionWrites: Writes[OrderTransitionEntity] =
    (
      (JsPath \ "transaction_id").write[String] and
        (JsPath \ "transition").write[WorkflowTransitionEntity]
    )(unlift(OrderTransitionEntity.unapply))
}
