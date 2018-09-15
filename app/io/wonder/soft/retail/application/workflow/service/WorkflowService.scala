package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDetailEntity, WorkflowEntity => DefinitionSummaryEntity, WorkflowStatusEntity}

trait WorkflowService {

  def listStatus: List[WorkflowStatusEntity]

  def createStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def updateStatus(workflowStatusEntity: WorkflowStatusEntity): Either[Exception, WorkflowStatusEntity]

  def listDefinition(workflowId: Int): List[WorkflowDetailEntity]

  def listSummary: List[DefinitionSummaryEntity]

  def createSummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity]

  def destroySummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity]

  def createDefinition(schemeEntity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity]


}
