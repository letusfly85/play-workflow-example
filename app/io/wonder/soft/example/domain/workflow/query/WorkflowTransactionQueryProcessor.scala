package io.wonder.soft.example.domain.workflow.query

import entity.WorkflowCurrentStateEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowCurrentStates

import scalikejdbc._

class WorkflowTransactionQueryProcessor {

  val wcsc = WorkflowCurrentStates.column

  def findCurrentStateByTransactionId(transactionId: String): Option[WorkflowCurrentStateEntity] =
    WorkflowCurrentStates.findBy(sqls.eq(wcsc.transactionId, transactionId)).map(t => t)

}
