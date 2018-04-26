package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.Repository
import io.wonder.soft.retail.domain.workflow.entity.WorkflowTransitionEntity
import io.wonder.soft.retail.domain.workflow.model.WorkflowTransitions
import io.wonder.soft.retail.domain.Repository
import io.wonder.soft.retail.domain.workflow.entity.WorkflowTransitionEntity
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowTransitionRepository extends Repository[WorkflowTransitionEntity] {

  val wtc = WorkflowTransitions.column

  override def find(id: Int): Option[WorkflowTransitionEntity] = ???

  override def create(entity: WorkflowTransitionEntity): Either[Exception, WorkflowTransitionEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowTransitions).namedValues(
            wtc.workflowId -> entity.workflowId,
            wtc.name -> entity.name,
            wtc.fromStepId -> entity.fromStep.stepId,
            wtc.toStepId -> entity.toStep.stepId
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  override def update(entity: WorkflowTransitionEntity): Either[RuntimeException, WorkflowTransitionEntity] = {
    Try {
      WorkflowTransitions.findBy(sqls
          .eq(wtc.workflowId, entity.workflowId)
          .eq(wtc.fromStepId, entity.fromStep.stepId)
          .eq(wtc.toStepId, entity.toStep.stepId)) match {
        case Some(transitions) =>
          transitions.copy(name = entity.name).save()
          Right(entity)

        case None =>
          Left(new RuntimeException("")) //TODO
      }

    } match {
      case Success(result) => result
      case Failure(e) => Left(new RuntimeException(e))
    }
  }

  override def destroy(id: Int): Option[WorkflowTransitionEntity] = None

}
