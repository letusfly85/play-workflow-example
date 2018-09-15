package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.Workflows
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowEntity(
    id: Int,
    workflowId: Int,
    name: String,
    description: Option[String],
    serviceId: Int,
) extends Entity

object WorkflowEntity {
  implicit def workflowReads: Reads[WorkflowEntity] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "workflow_id").read[Int] and
    (JsPath \ "name").read[String] and
    (JsPath \ "description").readNullable[String] and
    (JsPath \ "service_id").read[Int]
   )(WorkflowEntity.apply _)

  implicit def workflowWrites: Writes[WorkflowEntity] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "workflow_id").write[Int] and
    (JsPath \ "name").write[String] and
    (JsPath \ "description").writeNullable[String] and
    (JsPath \ "service_id").write[Int]
  )(unlift(WorkflowEntity.unapply))

  implicit def convertFromModel(model: Workflows): WorkflowEntity = {
    WorkflowEntity(
      model.id,
      model.workflowId,
      model.name,
      model.description,
      model.serviceId,
    )
  }

  implicit def convertFromModels(models: List[Workflows])
    : List[WorkflowEntity] =
    models.map(convertFromModel)
}
