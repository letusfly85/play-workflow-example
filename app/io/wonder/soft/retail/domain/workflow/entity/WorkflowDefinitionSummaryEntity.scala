package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.WorkflowDefinitionSummaries
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowDefinitionSummaryEntity(
    id: Int,
    workflowId: Int,
    name: String,
    serviceId: Int,
) extends Entity

object WorkflowDefinitionSummaryEntity {
  implicit def workflowDefinitionSummariesReads
    : Reads[WorkflowDefinitionSummaryEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "workflow_id").read[Int] and
        (JsPath \ "name").read[String] and
        (JsPath \ "service_id").read[Int]
    )(WorkflowDefinitionSummaryEntity.apply _)

  implicit def workflowDefinitionSummariesWrites
    : Writes[WorkflowDefinitionSummaryEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "workflow_id").write[Int] and
        (JsPath \ "name").write[String] and
        (JsPath \ "service_id").write[Int]
    )(unlift(WorkflowDefinitionSummaryEntity.unapply))

  implicit def convertFromModel(
      model: WorkflowDefinitionSummaries): WorkflowDefinitionSummaryEntity = {
    WorkflowDefinitionSummaryEntity(
      model.id,
      model.workflowId,
      model.name,
      model.serviceId,
    )
  }

  implicit def convertFromModels(models: List[WorkflowDefinitionSummaries])
    : List[WorkflowDefinitionSummaryEntity] =
    models.map(convertFromModel)
}
