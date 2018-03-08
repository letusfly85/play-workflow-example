package io.wonder.soft.example.domain.workflow.entity

import io.wonder.soft.example.domain.Entity
import io.wonder.soft.example.domain.workflow.model.Tasks
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class TaskEntity(id: Int, name: String) extends Entity

object TaskEntity {
  implicit def workflowStatusReads: Reads[TaskEntity] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "name").read[String]
    )(TaskEntity.apply _)

  implicit def workflowStatusWrites: Writes[TaskEntity] = (
    (JsPath \ "id").write[Int] and
      (JsPath \ "name").write[String]
    )(unlift(TaskEntity.unapply))

  implicit def convertFromModel(model: Tasks): TaskEntity = {
    TaskEntity(model.id, model.name)
  }

  implicit def convertFromModels(models: List[Tasks]): List[TaskEntity] = {
    models.map(convertFromModel)
  }
}
