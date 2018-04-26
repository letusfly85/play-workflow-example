package io.wonder.soft.retail.domain.example.craft.repository

import io.wonder.soft.retail.domain.Repository
import io.wonder.soft.retail.domain.example.craft.entity.CraftLineEntity
import io.wonder.soft.retail.domain.example.craft.model.CraftLines

import scala.util.{Failure, Success, Try}

class CraftLinesRepository extends Repository[CraftLineEntity] {
  val clc = CraftLines.column

  import CraftLineEntity._

  override def find(id: Int): Option[CraftLineEntity] =
    CraftLines.find(id).flatMap(orders => Some(orders))

  override def create(entity: CraftLineEntity): Either[Exception, CraftLineEntity] = {
    Try {
      /*
      DB localTx {implicit session =>
        withSQL {
          insert.into(CraftLines).namedValues(
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

  override def update(entity: CraftLineEntity): Either[RuntimeException, CraftLineEntity] = {
    Try {
      CraftLines.find(entity.id) match {
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

  override def destroy(id: Int): Option[CraftLineEntity] = None
}

