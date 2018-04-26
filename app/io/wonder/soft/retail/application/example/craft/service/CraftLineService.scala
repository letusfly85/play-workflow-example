package io.wonder.soft.retail.application.example.craft.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.retail.domain.example.craft.entity.CraftLineEntity
import io.wonder.soft.retail.domain.example.craft.query.CraftLineQueryProcessor
import javax.inject.Inject

class CraftLineService @Inject()
  (transactionService: WorkflowTransactionService,
   craftLineQuery: CraftLineQueryProcessor
  ) extends ApplicationService {

  def listCraftLine: List[CraftLineEntity] = craftLineQuery.listCraftLine

  def openCraftLine(craftLine: CraftLineEntity): Either[Exception, CraftLineEntity] = {
    transactionService.initialize(userId = "1", workflowId = 3) match {
      case Right(transaction) =>
        Right(craftLine.copy(
          transactionId = Some(transaction.transactionId))
        )

      case Left(exception) =>
        Left(new Exception(exception))
    }
  }

}
