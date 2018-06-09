package io.wonder.soft.retail.application.workflow

import io.wonder.soft.retail.application.workflow.service.{WorkflowConditionService, WorkflowService, WorkflowTransactionService}
import io.wonder.soft.retail.domain.example.craft.query.{CraftLineActionQueryProcessor, CraftLineQueryProcessor}
import io.wonder.soft.retail.domain.example.craft.repository.CraftLineRepository
import io.wonder.soft.retail.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository
import io.wonder.soft.retail.domain.workflow.query.{ActionTransactionQueryProcessor, WorkflowQueryProcessor, WorkflowTransactionQueryProcessor}
import io.wonder.soft.retail.domain.workflow.repository._
import io.wonder.soft.retail.domain.workflow.service.UserTransaction
import repository.WorkflowDefinitionSummaryRepository

trait WorkflowServicesModule {

  import com.softwaremill.macwire._

  lazy val summaryRepository = wire[WorkflowDefinitionSummaryRepository]
  lazy val workflowDefinitionRepository = wire[WorkflowDefinitionRepository]
  lazy val workflowStatusRepository = wire[WorkflowStatusRepository]
  lazy val workflowTransitionRepository = wire[WorkflowTransitionRepository]
  lazy val queryProcessor = wire[WorkflowQueryProcessor]

  lazy val workflowService = wire[WorkflowService]

  lazy val actionQuery = wire[ActionTransactionQueryProcessor]
  lazy val actionRepository = wire[WorkflowActionConditionRepository]

  lazy val workflowConditionService = wire[WorkflowConditionService]

  lazy val userTransaction = wire[UserTransaction]
  lazy val transactionQuery = wire[WorkflowTransactionQueryProcessor]
  lazy val transactionRepository = wire[WorkflowTransactionRepository]
  lazy val currentStateRepository = wire[WorkflowCurrentStateRepository]

  lazy val workflowTransactionService = wire[WorkflowTransactionService]

  lazy val orderRepository = wire[OrderRepository]
  lazy val orderQueryProcessor = wire[OrderQueryProcessor]
  lazy val craftLinesRepository = wire[CraftLineRepository]
  lazy val craftLineQueryProcessor = wire[CraftLineQueryProcessor]
  lazy val craftLineActionQueryProcessor = wire[CraftLineActionQueryProcessor]

}
