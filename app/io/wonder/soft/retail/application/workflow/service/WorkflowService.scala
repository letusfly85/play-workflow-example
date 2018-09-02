package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDefinitionEntity, WorkflowDefinitionSummaryEntity => DefinitionSummaryEntity, WorkflowStatusEntity}

trait WorkflowService {

  def listStatus: List[WorkflowStatusEntity]

  def createStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def updateStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def listDefinition(workflowId: Int): List[WorkflowDefinitionEntity]

  def listSummary: List[DefinitionSummaryEntity]

  def createSummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity]

  def destroySummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity]

  def findDefinition(id: Int): Either[Exception, WorkflowDefinitionEntity]

  def createDefinition(schemeEntity: WorkflowDefinitionEntity): Either[Exception, WorkflowDefinitionEntity]


}
