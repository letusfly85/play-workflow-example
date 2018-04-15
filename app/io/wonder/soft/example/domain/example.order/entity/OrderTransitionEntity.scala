package io.wonder.soft.example.domain.example.order.entity

import io.wonder.soft.example.domain.Entity
import io.wonder.soft.example.domain.workflow.entity.WorkflowTransitionEntity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class OrderTransitionEntity(
    order: OrderEntity,
    transition: WorkflowTransitionEntity,
) extends Entity

object OrderTransitionEntity {
  implicit def orderTransitionReads: Reads[OrderTransitionEntity] =
    (
      (JsPath \ "order").read[OrderEntity] and
        (JsPath \ "transition").read[WorkflowTransitionEntity]
    )(OrderTransitionEntity.apply _)

  implicit def orderTransitionWrites: Writes[OrderTransitionEntity] =
    (
      (JsPath \ "order").write[OrderEntity] and
        (JsPath \ "transition").write[WorkflowTransitionEntity]
    )(unlift(OrderTransitionEntity.unapply))
}
