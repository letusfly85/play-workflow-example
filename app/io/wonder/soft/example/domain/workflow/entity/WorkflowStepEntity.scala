package io.wonder.soft.example.domain.workflow.entity

import io.wonder.soft.example.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowStepEntity(
    stepId: Int = 0,
    stepLabel: String = "",
    isFirstStep: Boolean = false,
    isLastStep: Boolean = false
) extends Entity

object WorkflowStepEntity {
  implicit def workflowStepReads: Reads[WorkflowStepEntity] =
    (
      (JsPath \ "step_id").read[Int] and
        (JsPath \ "step_label").read[String] and
        (JsPath \ "is_first_step").read[Boolean] and
        (JsPath \ "is_last_step").read[Boolean]
    )(WorkflowStepEntity.apply _)

  implicit def workflowStepWrites: Writes[WorkflowStepEntity] =
    (
      (JsPath \ "step_id").write[Int] and
        (JsPath \ "step_label").write[String] and
        (JsPath \ "is_first_step").write[Boolean] and
        (JsPath \ "is_last_step").write[Boolean]
    )(unlift(WorkflowStepEntity.unapply))
}
