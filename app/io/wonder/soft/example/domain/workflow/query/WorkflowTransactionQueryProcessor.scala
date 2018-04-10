package io.wonder.soft.example.domain.workflow.query

import entity.WorkflowCurrentStateEntity
import io.wonder.soft.example.domain.workflow.entity.WorkflowTransactionEntity
import io.wonder.soft.example.domain.workflow.model.{
  WorkflowCurrentStates,
  WorkflowTransactions
}
import scalikejdbc._

class WorkflowTransactionQueryProcessor {

  val wcsc = WorkflowCurrentStates.column
  val wtc = WorkflowTransactions.column

  def findCurrentStateByTransactionId(
      transactionId: String): Option[WorkflowCurrentStateEntity] =
    WorkflowCurrentStates
      .findBy(sqls.eq(wcsc.transactionId, transactionId))
      .map(t => t)

  def findFinishedTransaction(
      transactionId: String): Option[WorkflowTransactionEntity] =
    WorkflowTransactions
      .findBy(
        sqls.eq(wtc.transactionId, transactionId).and.eq(wtc.isCompleted, true))
      .map(t => t)

}
