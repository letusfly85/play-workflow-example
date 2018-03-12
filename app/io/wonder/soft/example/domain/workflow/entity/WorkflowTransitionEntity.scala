package io.wonder.soft.example.domain.workflow.entity

import io.wonder.soft.example.domain.Entity
import io.wonder.soft.example.domain.workflow.model.WorkflowTransitions
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowTransitionEntity(
                   workflowId: Int,
                   name: String,
                   fromStepId: Int,
                   toStepId: Int,
                   conditionSuiteId: Option[Int], //TODO implement and use entity
                   isDefined: Boolean = false
                 ) extends Entity


object WorkflowTransitionEntity {
  implicit def workflowTransitionReads: Reads[WorkflowTransitionEntity] = (
    (JsPath \ "workflow_id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "from_step_id").read[Int] and
    (JsPath \ "to_step_id").read[Int] and
    (JsPath \ "condition_suite_id").readNullable[Int] and
    (JsPath \ "is_defined").read[Boolean]
  )(WorkflowTransitionEntity.apply _)

  implicit def workflowTransitionWrites: Writes[WorkflowTransitionEntity] = (
    (JsPath \ "workflow_id").write[Int] and
    (JsPath \ "name").write[String] and
    (JsPath \ "from_step_id").write[Int] and
    (JsPath \ "to_step_id").write[Int] and
    (JsPath \ "condition_suite_id").writeNullable[Int] and
    (JsPath \ "is_defined").write[Boolean]
  )(unlift(WorkflowTransitionEntity.unapply))

  implicit def convertFromModel(model: WorkflowTransitions): WorkflowTransitionEntity = {
    WorkflowTransitionEntity(
      model.workflowId,
      model.name,
      model.fromStepId,
      model.toStepId,
      model.conditionSuiteId,
      model.isDefined
    )
  }

  implicit def convertFromModels(models: List[WorkflowTransitions]): List[WorkflowTransitionEntity] = {
    models.map(convertFromModel)
  }
}
