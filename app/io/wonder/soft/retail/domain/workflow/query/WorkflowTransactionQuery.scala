package io.wonder.soft.retail.domain.workflow.query

import io.wonder.soft.retail.domain.workflow.entity.{WorkflowCurrentStateEntity, WorkflowTransactionEntity}
import io.wonder.soft.retail.domain.workflow.model.{WorkflowCurrentStates, WorkflowTransactions}
import scalikejdbc._

class WorkflowTransactionQuery {

  val wcsc = WorkflowCurrentStates.column
  val wtc = WorkflowTransactions.column

  def findCurrentStateByTransactionId(transactionId: String): Option[WorkflowCurrentStateEntity] =
    WorkflowCurrentStates
      .findBy(sqls.eq(wcsc.transactionId, transactionId))
      .map(t => t)

  def findFinishedTransaction(transactionId: String): Option[WorkflowTransactionEntity] =
    WorkflowTransactions
      .findBy(
        sqls.eq(wtc.transactionId, transactionId).and.eq(wtc.isCompleted, true)
      ).map(t => t)
}
