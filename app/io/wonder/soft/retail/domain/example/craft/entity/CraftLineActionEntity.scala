package io.wonder.soft.retail.domain.example.craft.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.example.craft.model.CraftLineActions
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class CraftLineActionEntity(
    id: Int,
    name: String,
    description: Option[String],
    serviceId: Int = 1,
) extends Entity

object CraftLineActionEntity {

  final val craftLineServiceId = 1

  implicit def craftLineActionsReads: Reads[CraftLineActionEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "name").read[String] and
        (JsPath \ "description").readNullable[String] and
        (JsPath \ "service_id").read[Int]
    )(CraftLineActionEntity.apply _)

  implicit def craftLineActionsWrites: Writes[CraftLineActionEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "name").write[String] and
        (JsPath \ "description").writeNullable[String] and
        (JsPath \ "service_id").write[Int]
    )(unlift(CraftLineActionEntity.unapply))

  implicit def convertFromModel(
      model: CraftLineActions): CraftLineActionEntity = {
    CraftLineActionEntity(
      model.id,
      model.name,
      model.description,
      craftLineServiceId
    )
  }

  implicit def convertFromModels(
      models: List[CraftLineActions]): List[CraftLineActionEntity] =
    models.map(convertFromModel)
}
