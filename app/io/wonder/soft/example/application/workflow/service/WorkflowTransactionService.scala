package io.wonder.soft.example.application.workflow.service

import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.workflow.entity.WorkflowTransactionEntity
import io.wonder.soft.example.domain.workflow.factory.WorkflowTransactionFactory
import io.wonder.soft.example.domain.workflow.query.WorkflowQueryProcessor
import io.wonder.soft.example.domain.workflow.repository.{WorkflowDefinitionRepository, WorkflowTransactionRepository}
import javax.inject.Inject

import scala.util.{Failure, Success, Try}

class WorkflowTransactionService @Inject()(
    definitionRepository: WorkflowDefinitionRepository,
    defineQuery: WorkflowQueryProcessor,
    transactionRepository: WorkflowTransactionRepository,
    transactionService: WorkflowTransactionService)
    extends ApplicationService {

  def initialize(
      userId: String,
      workflowId: Int): Either[Exception, WorkflowTransactionEntity] = {
    Try {
      //todo check exists transaction

      //find workflow definition
      val defines = defineQuery.searchDefinitions(workflowId)

      val maybeInitialDefine = defines.filter(d => d.isFirstStep).headOption

      maybeInitialDefine match {
        case Some(define) =>
          //generate transaction id
          val transactionId = java.util.UUID.randomUUID().toString

          val transaction = WorkflowTransactionFactory.build(userId, transactionId, define)
          val initialTransaction = transaction.copy(isInit = true)

          transactionRepository.create(initialTransaction)

        case None =>
          throw new RuntimeException(s"there is no initial step for workflowId: ${workflowId}")
      }

    } match {
      case Success(result)    => result
      case Failure(exception) => Left(new Exception(exception))
    }
  }

}
