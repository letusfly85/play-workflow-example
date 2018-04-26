package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.WorkflowTransitions
import io.wonder.soft.retail.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowTransitionEntity(
                   workflowId: Int,
                   name: String,
                   fromStep: WorkflowStepEntity,
                   toStep: WorkflowStepEntity,
                   conditionSuiteId: Option[Int], //TODO implement and use entity
                   isDefined: Option[Boolean] = Some(false)
                 ) extends Entity


object WorkflowTransitionEntity {
  implicit def workflowTransitionReads: Reads[WorkflowTransitionEntity] = (
    (JsPath \ "workflow_id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "from_step").read[WorkflowStepEntity] and
    (JsPath \ "to_step").read[WorkflowStepEntity] and
    (JsPath \ "condition_suite_id").readNullable[Int] and
    (JsPath \ "is_defined").readNullable[Boolean]
  )(WorkflowTransitionEntity.apply _)

  implicit def workflowTransitionWrites: Writes[WorkflowTransitionEntity] = (
    (JsPath \ "workflow_id").write[Int] and
    (JsPath \ "name").write[String] and
    (JsPath \ "from_step").write[WorkflowStepEntity] and
    (JsPath \ "to_step").write[WorkflowStepEntity] and
    (JsPath \ "condition_suite_id").writeNullable[Int] and
    (JsPath \ "is_defined").writeNullable[Boolean]
  )(unlift(WorkflowTransitionEntity.unapply))

  implicit def convertFromModel(model: WorkflowTransitions): WorkflowTransitionEntity = {
    WorkflowTransitionEntity(
      model.workflowId,
      model.name,
      WorkflowStepEntity(),
      WorkflowStepEntity(),
      model.conditionSuiteId,
      Some(model.isDefined)
    )
  }

  implicit def convertFromModels(models: List[WorkflowTransitions]): List[WorkflowTransitionEntity] = {
    models.map(convertFromModel)
  }
}
