package io.wonder.soft.example.domain.example.order.service

import io.wonder.soft.example.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.example.domain.example.order.OrderAction
import io.wonder.soft.example.domain.example.order.entity.OrderEntity
import io.wonder.soft.example.domain.example.order.orderActions.{AssignMemberAction, FixPaymentAction, SetShipmentDayAction, ShipItemAction}
import io.wonder.soft.example.domain.example.order.repository.OrderRepository
import io.wonder.soft.example.domain.workflow.entity.WorkflowTransitionEntity
import javax.inject.Inject
import play.api.Logger

/**
  * TODO implement
  */
class OrderTransitionService @Inject()
   (transactionService: WorkflowTransactionService,
    orderRepository: OrderRepository
   )
  {

  def executeTransition(orderAction: OrderAction) = {

    orderAction match {
      case AssignMemberAction =>
      case SetShipmentDayAction  =>
      case FixPaymentAction =>
      case ShipItemAction =>
    }

  }

  def initializeTransaction(orderEntity: OrderEntity, userId: String = "1", workflowId: Int = 2): Either[Exception, OrderEntity] = {
    orderEntity.transactionId match {
      case Some(tId) if tId != "" =>
        Logger.info(s"transaction_id: ${tId} is already assigned")
        Right(orderEntity)

      case _ =>
        transactionService.initialize(userId, workflowId) match {
          case Right(transactionEntity) =>
            // todo change use `get`
            val define = transactionService.showDefine(transactionEntity.workflowId, transactionEntity.stepId).get

            val newOrderEntity =
              orderEntity.copy(
                transactionId = Some(transactionEntity.transactionId),
                statusId = Some(transactionEntity.stepId.toString),
                statusName = Some(define.stepLabel)
              )
            orderRepository.update(newOrderEntity)

            Right(newOrderEntity)

          case Left(exception) =>
            Left(new Exception(exception))
        }
    }
  }

  def proceedState(orderEntity: OrderEntity, transition: WorkflowTransitionEntity, userId: String = "1", workflowId: Int = 2): Either[Exception, OrderEntity] = {
    orderEntity.transactionId match {
      case Some(tId) =>
        transactionService.proceedState(tId, transition) match {
          case Right(nextState) =>
            val nextDefine = transactionService.showDefine(workflowId, nextState.currentStepId)
            val newOrder = orderEntity.copy(
              statusId = Some(nextDefine.get.stepId.toString),
              statusName = Some(nextDefine.get.stepLabel))
            orderRepository.update(newOrder)

            Right(orderEntity)

          case Left(exception) =>
            Left(new Exception(exception))
        }

      case None => Left(new Exception(s"no transaction Id for ${userId}"))
    }
  }

}
