package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowDefinitionSummaryEntity, WorkflowStatusEntity, WorkflowTransitionEntity => TransitionEntity}

trait WorkflowService {

  def listStatus: List[WorkflowStatusEntity]

  def createStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def updateStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def listDefinition(workflowId: Int): List[WorkflowDefinitionEntity]

  def listSummary: List[WorkflowDefinitionSummaryEntity]

  def createSummary(entity: WorkflowDefinitionSummaryEntity): Either[Exception, WorkflowDefinitionSummaryEntity]

  def findDefinition(id: Int): Either[Exception, WorkflowDefinitionEntity]

  def createDefinition(schemeEntity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity]


}
