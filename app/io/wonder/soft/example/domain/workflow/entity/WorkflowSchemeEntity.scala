package io.wonder.soft.example.domain.workflow.entity

import io.wonder.soft.example.domain.Entity
import io.wonder.soft.example.domain.workflow.model.WorkflowSchemes
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowSchemeEntity(
                   workflowId: Int,
                   name: String,
                   status: Option[WorkflowStatusEntity],
                   schemeStepId: Int,
                   schemeStepLabel: String,
                   isFirstStep: Boolean = false,
                   isLastStep: Boolean = false
                 ) extends Entity


object WorkflowSchemeEntity {
  implicit def workflowSchemeReads: Reads[WorkflowSchemeEntity] = (
    (JsPath \ "workflow_id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "status").readNullable[WorkflowStatusEntity] and
    (JsPath \ "step_id").read[Int] and
    (JsPath \ "step_label").read[String] and
    (JsPath \ "is_first_step").read[Boolean] and
    (JsPath \ "is_last_step").read[Boolean]
  )(WorkflowSchemeEntity.apply _)

  implicit def workflowSchemeWrites: Writes[WorkflowSchemeEntity] = (
    (JsPath \ "workflow_id").write[Int] and
    (JsPath \ "name").write[String] and
    (JsPath \ "status").writeNullable[WorkflowStatusEntity] and
    (JsPath \ "step_id").write[Int] and
    (JsPath \ "step_label").write[String] and
    (JsPath \ "is_first_step").write[Boolean] and
    (JsPath \ "is_last_step").write[Boolean]
  )(unlift(WorkflowSchemeEntity.unapply))

  implicit def convertFromModel(model: WorkflowSchemes): WorkflowSchemeEntity = {
    WorkflowSchemeEntity(
      model.workflowId,
      model.name,
      None,
      model.schemeStepId,
      model.schemeStepLabel,
      model.isFirstStep,
      model.isLastStep
    )
  }
}
