package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

/**
  * this is implemented by user, not implemented by some auto processing
  * @param isActive
  * @param transitionEntity
  */
final case class WorkflowUserTransitionEntity(
    isActive: Boolean = false,
    transitionEntity: WorkflowTransitionEntity
) extends Entity

object WorkflowUserTransitionEntity {
  implicit def workflowTransitionReads: Reads[WorkflowUserTransitionEntity] =
    (
      (JsPath \ "is_active").read[Boolean] and
        (JsPath \ "transition").read[WorkflowTransitionEntity]
    )(WorkflowUserTransitionEntity.apply _)

  implicit def workflowTransitionWrites: Writes[WorkflowUserTransitionEntity] =
    (
      (JsPath \ "is_active").write[Boolean] and
        (JsPath \ "transition").write[WorkflowTransitionEntity]
    )(unlift(WorkflowUserTransitionEntity.unapply))
}
