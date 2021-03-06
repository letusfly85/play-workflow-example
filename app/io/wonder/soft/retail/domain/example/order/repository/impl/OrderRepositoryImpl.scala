package io.wonder.soft.retail.domain.example.order.repository.impl

import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.model.Orders
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository

import scala.util.{Failure, Success, Try}

class OrderRepositoryImpl extends OrderRepository {
  val oc = Orders.column

  import OrderEntity._

  override def find(id: Int): Option[OrderEntity] =
    Orders.find(id).flatMap(orders => Some(orders))

  override def create(entity: OrderEntity): Either[Exception, OrderEntity] = {
    Try {
      /*
      DB localTx {implicit session =>
        withSQL {
          insert.into(Orders).namedValues(
            oc.name -> entity.name
          )
        }.update().apply()
      }
      */

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }

    ???
  }

  override def update(entity: OrderEntity): Either[RuntimeException, OrderEntity] = {
    Try {
      Orders.find(entity.id) match {
        case Some(orders) =>
          orders.copy(
            transactionId = entity.transactionId,
            statusId = entity.statusId,
            statusName = entity.statusName,
            updatedAt = Some(new org.joda.time.DateTime())
          ).save()
          Right(entity)

        case None =>
          Left(new RuntimeException("")) //TODO
      }

    } match {
      case Success(result) => result
      case Failure(e) => Left(new RuntimeException(e))
    }
  }

  override def destroy(id: Int): Option[OrderEntity] = None
}

