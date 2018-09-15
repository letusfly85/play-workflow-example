package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowDetailEntity, WorkflowEntity => DefinitionSummaryEntity, WorkflowStatusEntity}

trait WorkflowService {

  def listDefinition(workflowId: Int): List[WorkflowDetailEntity]

  def listSummary: List[DefinitionSummaryEntity]

  def createSummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity]

  def destroySummary(entity: DefinitionSummaryEntity): Either[Exception, DefinitionSummaryEntity]

  def createDefinition(schemeEntity: WorkflowDetailEntity): Either[Exception, WorkflowDetailEntity]


}
