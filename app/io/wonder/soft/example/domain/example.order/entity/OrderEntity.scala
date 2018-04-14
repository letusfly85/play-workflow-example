package io.wonder.soft.example.domain.example.order.entity

import io.wonder.soft.example.domain.Entity
import io.wonder.soft.example.domain.example.order.model.Orders
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class OrderEntity(
    id: Int,
    orderId: String,
    transactionId: Option[String],
    statusId: String,
    statusName: Option[String],
    customerName: Option[String],
    assignedMemberName: Option[String],
    serviceId: Int,
) extends Entity

object OrderEntity {
  implicit def ordersReads: Reads[OrderEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "order_id").read[String] and
        (JsPath \ "transaction_id").readNullable[String] and
        (JsPath \ "status_id").read[String] and
        (JsPath \ "status_name").readNullable[String] and
        (JsPath \ "customer_name").readNullable[String] and
        (JsPath \ "assigned_member_name").readNullable[String] and
        (JsPath \ "service_id").read[Int]
    )(OrderEntity.apply _)

  implicit def ordersWrites: Writes[OrderEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "order_id").write[String] and
        (JsPath \ "transaction_id").writeNullable[String] and
        (JsPath \ "status_id").write[String] and
        (JsPath \ "status_name").writeNullable[String] and
        (JsPath \ "customer_name").writeNullable[String] and
        (JsPath \ "assigned_member_name").writeNullable[String] and
        (JsPath \ "service_id").write[Int]
    )(unlift(OrderEntity.unapply))

  implicit def convertFromModel(model: Orders): OrderEntity = {
    OrderEntity(
      model.id,
      model.orderId,
      model.transactionId,
      model.statusId,
      model.statusName,
      model.customerName,
      model.assignedMemberName,
      model.serviceId
    )
  }

  implicit def convertFromModels(models: List[Orders]): List[OrderEntity] =
    models.map(convertFromModel)
}
