package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowActionConditionEntity
import io.wonder.soft.retail.domain.workflow.model.WorkflowActionConditions
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowActionConditionRepositoryImpl extends WorkflowActionConditionRepository {

  val wacc = WorkflowActionConditions.column

  override def find(id: Int): Option[WorkflowActionConditionEntity] = ???

  override def create(entity: WorkflowActionConditionEntity): Either[Exception, WorkflowActionConditionEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowActionConditions).namedValues(
            wacc.actionId -> entity.actionId,
            wacc.workflowId -> entity.workflowId,
            wacc.transitionId -> entity.transitionId,
            wacc.serviceId -> entity.serviceId,
            wacc.name -> entity.name
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: WorkflowActionConditionEntity): Either[RuntimeException, WorkflowActionConditionEntity] = {
    Try {
      WorkflowActionConditions.find(entity.id) match {
        case Some(transitions) =>
          transitions.copy(
            actionId = entity.actionId,
            workflowId = entity.workflowId,
            transitionId = entity.transitionId,
            serviceId = entity.serviceId,
            name = entity.name
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

  override def destroy(id: Int): Option[WorkflowActionConditionEntity] = {
    WorkflowActionConditions.find(id) match {
      case Some(condition) =>
        condition.destroy()
        Some(condition)

      case None => None
    }
  }

}
