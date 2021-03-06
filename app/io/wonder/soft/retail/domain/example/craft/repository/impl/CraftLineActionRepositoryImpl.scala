package io.wonder.soft.retail.domain.example.craft.repository.impl

import io.wonder.soft.retail.domain.example.craft.entity.CraftLineActionEntity
import io.wonder.soft.retail.domain.example.craft.model.CraftLineActions
import io.wonder.soft.retail.domain.example.craft.repository.CraftLineActionRepository
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class CraftLineActionRepositoryImpl extends CraftLineActionRepository {
  val clac = CraftLineActions.column

  import CraftLineActionEntity._

  override def find(id: Int): Option[CraftLineActionEntity] =
    CraftLineActions.find(id).flatMap(actions => Some(actions))

  override def create(entity: CraftLineActionEntity): Either[Exception, CraftLineActionEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(CraftLineActions).namedValues(
            clac.name -> entity.name,
            clac.description -> entity.description,
            clac.serviceId -> entity.serviceId
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }

    ???
  }

  override def update(entity: CraftLineActionEntity): Either[RuntimeException, CraftLineActionEntity] = {
    Try {
      CraftLineActions.find(entity.id) match {
        case Some(orders) =>
          orders.copy(
            name = entity.name,
            serviceId = entity.serviceId,
            description = entity.description
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

  override def destroy(id: Int): Option[CraftLineActionEntity] = None
}

