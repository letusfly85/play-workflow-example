package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.WorkflowActionConditions
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowActionConditionEntity(
    id: Int,
    workflowId: Int,
    transitionId: Int,
    actionId: Int,
    serviceId: Int,
    name: Option[String],
) extends Entity

object WorkflowActionConditionEntity {
  implicit def workflowActionConditionsReads
    : Reads[WorkflowActionConditionEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "workflow_id").read[Int] and
        (JsPath \ "transition_id").read[Int] and
        (JsPath \ "action_id").read[Int] and
        (JsPath \ "service_id").read[Int] and
        (JsPath \ "name").readNullable[String]
    )(WorkflowActionConditionEntity.apply _)

  implicit def workflowActionConditionsWrites
    : Writes[WorkflowActionConditionEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "workflow_id").write[Int] and
        (JsPath \ "transition_id").write[Int] and
        (JsPath \ "action_id").write[Int] and
        (JsPath \ "service_id").write[Int] and
        (JsPath \ "name").writeNullable[String]
    )(unlift(WorkflowActionConditionEntity.unapply))

  implicit def convertFromModel(
      model: WorkflowActionConditions): WorkflowActionConditionEntity = {
    WorkflowActionConditionEntity(
      model.id,
      model.workflowId,
      model.transitionId,
      model.actionId,
      model.serviceId,
      model.name
    )
  }

  implicit def convertFromModels(models: List[WorkflowActionConditions])
    : List[WorkflowActionConditionEntity] =
    models.map(convertFromModel)
}
