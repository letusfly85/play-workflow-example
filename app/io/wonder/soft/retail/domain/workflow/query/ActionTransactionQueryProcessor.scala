package io.wonder.soft.retail.domain.workflow.query

import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity
import io.wonder.soft.retail.domain.workflow.model.ActionTransactions
import scalikejdbc._

class ActionTransactionQueryProcessor {

  val atc = ActionTransactions.column

  def findAllByTransactionId(transactionId: String, actionId: String): List[ActionTransactionEntity] =
    ActionTransactions
      .findAllBy(
        sqls.eq(atc.transactionId, transactionId)
          .and.eq(atc.actionId, actionId)
          .and.ne(atc.isReverted, true))
      .map(t => t)

}
