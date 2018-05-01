package io.wonder.soft.retail.application.example.craft.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.retail.domain.example.craft.entity.CraftLineEntity
import io.wonder.soft.retail.domain.example.craft.query.CraftLineQueryProcessor
import io.wonder.soft.retail.domain.example.craft.repository.CraftLineRepository
import javax.inject.Inject
import play.api.Logger

class CraftLineService @Inject()
  (transactionService: WorkflowTransactionService,
   craftLinesRepository: CraftLineRepository,
   craftLineQuery: CraftLineQueryProcessor
  ) extends ApplicationService {

  val craftExampleWorkflowId = 3

  def listCraftLine: List[CraftLineEntity] = craftLineQuery.listCraftLine

  def openCraftLine(craftLineId: String): Either[Exception, CraftLineEntity] = {
    craftLinesRepository.find(craftLineId.toInt) match {
      case Some(craftLine) if craftLine.transactionId.getOrElse("") == "" =>
        Logger.info(s"find not yet initialized craft entity ${craftLine.toString}")
        transactionService.initialize(userId = "1", workflowId = craftExampleWorkflowId) match {
          case Right(transaction) =>
            val define = transactionService.showDefine(workflowId = craftExampleWorkflowId, transaction.stepId).get
            val newCraftLine = craftLine.copy(
              transactionId = Some(transaction.transactionId),
              statusId = define.stepId.toString,
              statusName = Some(define.stepLabel)
            )
            craftLinesRepository.update(newCraftLine)

          case Left(exception) =>
            Left(new Exception(exception))
        }

      case Some(craftLine) =>
        Logger.info(s"find craft entity ${craftLine.toString}")
        Right(craftLine)

      case None =>
        Left(new RuntimeException(""))
    }
  }

}
