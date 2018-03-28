package io.wonder.soft.example.domain.workflow.entity

import io.wonder.soft.example.domain.Entity
import io.wonder.soft.example.domain.workflow.model.WorkflowDefinitions
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowDefinitionEntity(
                                           workflowId: Int,
                                           name: String,
                                           status: Option[WorkflowStatusEntity],
                                           stepId: Int,
                                           stepLabel: String,
                                           isFirstStep: Boolean = false,
                                           isLastStep: Boolean = false
                 ) extends Entity


object WorkflowDefinitionEntity {
  implicit def workflowDefinitionReads: Reads[WorkflowDefinitionEntity] = (
    (JsPath \ "workflow_id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "status").readNullable[WorkflowStatusEntity] and
    (JsPath \ "step_id").read[Int] and
    (JsPath \ "step_label").read[String] and
    (JsPath \ "is_first_step").read[Boolean] and
    (JsPath \ "is_last_step").read[Boolean]
  )(WorkflowDefinitionEntity.apply _)

  implicit def workflowDefinitionWrites: Writes[WorkflowDefinitionEntity] = (
    (JsPath \ "workflow_id").write[Int] and
    (JsPath \ "name").write[String] and
    (JsPath \ "status").writeNullable[WorkflowStatusEntity] and
    (JsPath \ "step_id").write[Int] and
    (JsPath \ "step_label").write[String] and
    (JsPath \ "is_first_step").write[Boolean] and
    (JsPath \ "is_last_step").write[Boolean]
  )(unlift(WorkflowDefinitionEntity.unapply))

  implicit def convertFromModel(model: WorkflowDefinitions): WorkflowDefinitionEntity = {
    WorkflowDefinitionEntity(
      model.workflowId,
      model.name,
      None,
      model.stepId,
      model.stepLabel,
      model.isFirstStep,
      model.isLastStep
    )
  }

  implicit def convertFromModels(models: List[WorkflowDefinitions]): List[WorkflowDefinitionEntity] = {
    models.map(convertFromModel)
  }
}
