package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.workflow.model.WorkflowTransitions
import io.wonder.soft.retail.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowTransitionEntity(
  id: Int = 0,
  workflowId: Int,
  name: String,
  fromStep: Option[WorkflowStepEntity] = None,
  toStep: Option[WorkflowStepEntity] = None,
  isDefined: Option[Boolean] = Some(false)
) extends Entity


object WorkflowTransitionEntity {
  implicit def workflowTransitionReads: Reads[WorkflowTransitionEntity] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "workflow_id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "from_step").readNullable[WorkflowStepEntity] and
    (JsPath \ "to_step").readNullable[WorkflowStepEntity] and
    (JsPath \ "is_defined").readNullable[Boolean]
  )(WorkflowTransitionEntity.apply _)

  implicit def workflowTransitionWrites: Writes[WorkflowTransitionEntity] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "workflow_id").write[Int] and
    (JsPath \ "name").write[String] and
    (JsPath \ "from_step").writeNullable[WorkflowStepEntity] and
    (JsPath \ "to_step").writeNullable[WorkflowStepEntity] and
    (JsPath \ "is_defined").writeNullable[Boolean]
  )(unlift(WorkflowTransitionEntity.unapply))

  implicit def convertFromModel(model: WorkflowTransitions): WorkflowTransitionEntity = {
    WorkflowTransitionEntity(
      model.id,
      model.workflowId,
      model.name,
      None,
      None,
      Some(model.isDefined)
    )
  }

  implicit def convertFromModels(models: List[WorkflowTransitions]): List[WorkflowTransitionEntity] = {
    models.map(convertFromModel)
  }
}
