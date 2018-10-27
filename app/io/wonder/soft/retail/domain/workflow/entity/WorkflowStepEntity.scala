package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.WorkflowSteps
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowStepEntity(
  workflowId: Int,
  name: String,
  status: Option[WorkflowStatusEntity] = None,
  stepId: Int,
  stepLabel: String,
  isFirstStep: Boolean = false,
  isLastStep: Boolean = false
) extends Entity

object WorkflowStepEntity {
  implicit def workflowDetailReads: Reads[WorkflowStepEntity] =
    (
      (JsPath \ "workflow_id").read[Int] and
      (JsPath \ "name").read[String] and
      (JsPath \ "status").readNullable[WorkflowStatusEntity] and
      (JsPath \ "step_id").read[Int] and
      (JsPath \ "step_label").read[String] and
      (JsPath \ "is_first_step").read[Boolean] and
      (JsPath \ "is_last_step").read[Boolean]
    )(WorkflowStepEntity.apply _)

  implicit def workflowDetailWrites: Writes[WorkflowStepEntity] =
    (
      (JsPath \ "workflow_id").write[Int] and
      (JsPath \ "name").write[String] and
      (JsPath \ "status").writeNullable[WorkflowStatusEntity] and
      (JsPath \ "step_id").write[Int] and
      (JsPath \ "step_label").write[String] and
      (JsPath \ "is_first_step").write[Boolean] and
      (JsPath \ "is_last_step").write[Boolean]
    )(unlift(WorkflowStepEntity.unapply))

  implicit def convertFromModel(
    model: WorkflowSteps): WorkflowStepEntity = {
    WorkflowStepEntity(
      model.workflowId,
      model.name,
      model.status.map(s => s),
      model.stepId,
      model.stepLabel,
      model.isFirstStep,
      model.isLastStep
    )
  }

  implicit def convertFromModels(models: List[WorkflowSteps]): List[WorkflowStepEntity] = {
    models.map(convertFromModel)
  }
}
