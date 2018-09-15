package io.wonder.soft.retail.application.workflow.service

import io.wonder.soft.retail.domain.workflow.entity._

trait WorkflowTransactionService  {

  def openTransaction(userId: String, workflowId: Int): Either[Exception, WorkflowTransactionEntity]

  def findDefinitionByStepId(workflowId: Int, stepId: Int): Option[WorkflowDetailEntity]

  def proceedState(transactionId: String, transition: WorkflowTransitionEntity): Either[Exception, WorkflowCurrentStateEntity]

  def listTransition(workflowId: Int, transactionId: String): List[WorkflowUserTransitionEntity]

}
