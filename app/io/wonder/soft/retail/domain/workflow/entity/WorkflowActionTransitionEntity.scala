package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

/**
  * this is implemented by user, not implemented by some auto processing
  * @param actionId
  * @param serviceId
  * @param transitionEntity
  */
final case class WorkflowActionTransitionEntity(
  actionId: Int,
  serviceId: Int,
  transitionEntity: WorkflowTransitionEntity
) extends Entity

object WorkflowActionTransitionEntity {
  implicit def workflowTransitionReads: Reads[WorkflowActionTransitionEntity] =
    (
      (JsPath \ "action_id").read[Int] and
        (JsPath \ "service_id").read[Int] and
        (JsPath \ "transition").read[WorkflowTransitionEntity]
      )(WorkflowActionTransitionEntity.apply _)

  implicit def workflowTransitionWrites: Writes[WorkflowActionTransitionEntity] =
    (
      (JsPath \ "action_id").write[Int] and
        (JsPath \ "service_id").write[Int] and
        (JsPath \ "transition").write[WorkflowTransitionEntity]
      )(unlift(WorkflowActionTransitionEntity.unapply))
}


