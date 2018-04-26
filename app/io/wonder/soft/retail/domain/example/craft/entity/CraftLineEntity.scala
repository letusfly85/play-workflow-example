package io.wonder.soft.retail.domain.example.craft.entity

import io.wonder.soft.retail.domain.example.craft.model.CraftLines
import io.wonder.soft.retail.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class CraftLineEntity(
    id: Int,
    transactionId: Option[String],
    statusId: String,
    statusName: Option[String],
    productName: Option[String],
    assignedMemberName: Option[String],
    serviceId: Int,
) extends Entity

object CraftLineEntity {
  implicit def craftLinesReads: Reads[CraftLineEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "transaction_id").readNullable[String] and
        (JsPath \ "status_id").read[String] and
        (JsPath \ "status_name").readNullable[String] and
        (JsPath \ "product_name").readNullable[String] and
        (JsPath \ "assigned_member_name").readNullable[String] and
        (JsPath \ "service_id").read[Int]
    )(CraftLineEntity.apply _)

  implicit def craftLinesWrites: Writes[CraftLineEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "transaction_id").writeNullable[String] and
        (JsPath \ "status_id").write[String] and
        (JsPath \ "status_name").writeNullable[String] and
        (JsPath \ "product_name").writeNullable[String] and
        (JsPath \ "assigned_member_name").writeNullable[String] and
        (JsPath \ "service_id").write[Int]
    )(unlift(CraftLineEntity.unapply))

  implicit def convertFromModel(model: CraftLines): CraftLineEntity = {
    CraftLineEntity(
      model.id,
      None,
      model.statusId,
      None,
      None,
      None,
      model.serviceId,
    )
  }

  implicit def convertFromModels(
      models: List[CraftLines]): List[CraftLineEntity] =
    models.map(convertFromModel)
}
