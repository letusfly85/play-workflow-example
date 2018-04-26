package io.wonder.soft.retail.domain.example.order.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.example.order.model.OrderActions
import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.example.order.model.OrderActions
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class OrderActionEntity(
    id: Int,
    name: String,
    description: Option[String],
    serviceId: Int,
) extends Entity

object OrderActionEntity {
  implicit def orderActionsReads: Reads[OrderActionEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "name").read[String] and
        (JsPath \ "description").readNullable[String] and
        (JsPath \ "service_id").read[Int]
    )(OrderActionEntity.apply _)

  implicit def orderActionsWrites: Writes[OrderActionEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "name").write[String] and
        (JsPath \ "description").writeNullable[String] and
        (JsPath \ "service_id").write[Int]
    )(unlift(OrderActionEntity.unapply))

  implicit def convertFromModel(model: OrderActions): OrderActionEntity = {
    OrderActionEntity(
      model.id,
      model.name,
      None,
      model.serviceId,
    )
  }

  implicit def convertFromModels(
      models: List[OrderActions]): List[OrderActionEntity] =
    models.map(convertFromModel)
}
