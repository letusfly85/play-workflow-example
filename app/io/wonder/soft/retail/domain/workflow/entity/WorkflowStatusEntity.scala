package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.WorkflowStatuses
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowStatusEntity(id: Int, name: String) extends Entity

object WorkflowStatusEntity {
  implicit def workflowStatusReads: Reads[WorkflowStatusEntity] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "name").read[String]
  )(WorkflowStatusEntity.apply _)

  implicit def workflowStatusWrites: Writes[WorkflowStatusEntity] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "name").write[String]
  )(unlift(WorkflowStatusEntity.unapply))

  implicit def convertFromModel(model: WorkflowStatuses): WorkflowStatusEntity = {
    WorkflowStatusEntity(model.id, model.name)
  }

  implicit def convertFromModels(models: List[WorkflowStatuses]): List[WorkflowStatusEntity] = {
    models.map(convertFromModel)
  }
}
