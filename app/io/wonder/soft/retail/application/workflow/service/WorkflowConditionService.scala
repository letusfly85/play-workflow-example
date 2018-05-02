package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowActionConditionEntity, WorkflowActionTransitionEntity, WorkflowTransitionEntity}
import io.wonder.soft.retail.domain.workflow.factory.WorkflowFactory
import io.wonder.soft.retail.domain.workflow.repository.WorkflowActionConditionRepository
import javax.inject.Inject

class WorkflowConditionService @Inject()
  (actionConditionRepository: WorkflowActionConditionRepository)
  extends ApplicationService {

  def createActionCondition(actionTransitionEntity: WorkflowActionTransitionEntity): Either[Exception, WorkflowActionConditionEntity] = {
    val condition = WorkflowFactory.buildConditionEntity(actionTransitionEntity)
    actionConditionRepository.create(condition)
  }

  def deleteActionCondition(id: Int) = {
    actionConditionRepository.destroy(id)
  }

}
