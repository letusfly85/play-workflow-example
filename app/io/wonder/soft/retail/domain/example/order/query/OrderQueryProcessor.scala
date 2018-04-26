package io.wonder.soft.retail.domain.example.order.query

import io.wonder.soft.retail.domain.example.order.model.Orders
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.model.Orders
import scalikejdbc._

import scala.util.{Success, Try}

class OrderQueryProcessor {
  import OrderEntity._

  val o = Orders.syntax("o")

  def listOrder: List[OrderEntity] = Orders.findAll().map(o => o)

  def findByTransactionId(transactionId: String): Option[OrderEntity] =
    Orders.findBy(sqls.eq(o.transactionId, transactionId)).map(o => o)

}
