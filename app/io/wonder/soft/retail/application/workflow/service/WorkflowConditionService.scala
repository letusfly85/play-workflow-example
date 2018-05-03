package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowActionConditionEntity, WorkflowActionTransitionEntity, WorkflowTransitionEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory
import io.wonder.soft.retail.domain.workflow.query.ActionTransactionQueryProcessor
import io.wonder.soft.retail.domain.workflow.repository.WorkflowActionConditionRepository
import javax.inject.Inject

class WorkflowConditionService @Inject()
  (actionQuery: ActionTransactionQueryProcessor,
   actionRepository: WorkflowActionConditionRepository)
  extends ApplicationService {

  def createActionCondition(actionTransitionEntity: WorkflowActionTransitionEntity): Either[Exception, WorkflowActionConditionEntity] = {
    val condition = WorkflowFactory.buildConditionEntity(actionTransitionEntity)
    actionRepository.create(condition)
  }

  def deleteActionCondition(id: Int) = {
    actionRepository.destroy(id)
  }

  def searchCraftLineActions(workflowId: Int, transitionId: Int): List[WorkflowActionConditionEntity] = {
    actionQuery.searchCraftLineActions(workflowId, transitionId)
  }

  def saveActionConditions(actionConditions: List[WorkflowActionConditionEntity]): Either[Exception, List[WorkflowActionConditionEntity]] = {
    val result = actionConditions.foldLeft(List.empty[WorkflowActionConditionEntity]) { case (acc, actionCondition) =>
      actionQuery.findByTransitionId(actionCondition.actionId, actionCondition.transitionId) match {
        case None if actionCondition.isActivate =>
          actionRepository.create(actionCondition) match {
            case Right(entity) => entity :: acc
            case Left(exception) => acc
          }

        case Some(_) if actionCondition.isActivate =>
          actionRepository.update(actionCondition) match {
            case Right(entity) => entity :: acc
            case Left(exception) => acc
          }

        case Some(entity) =>
          actionRepository.destroy(entity.id)
          acc

        case _ =>
          acc
      }
    }

    Right(result)
  }

}
