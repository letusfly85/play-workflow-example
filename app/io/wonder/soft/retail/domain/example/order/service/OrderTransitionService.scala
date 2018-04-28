package io.wonder.soft.retail.domain.example.order.service

import io.wonder.soft.retail.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.retail.domain.workflow.entity.WorkflowTransitionEntity
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository
import io.wonder.soft.retail.domain.example.order.service.orderActions._
import javax.inject.Inject

class OrderTransitionService @Inject()
   (transactionService: WorkflowTransactionService,
    orderQuery: OrderQueryProcessor,
    orderRepository: OrderRepository
   )
  {

  /**
    * TODO implement
    */
  def executeTransition(orderAction: OrderAction) = {

    orderAction match {
      case AssignMemberAction =>
      case SetShipmentDayAction  =>
      case FixPaymentAction =>
      case ShipItemAction =>
    }

  }

  def proceedState(transactionId: String, transition: WorkflowTransitionEntity, userId: String = "1", workflowId: Int = 2): Either[Exception, OrderEntity] = {
    transactionService.proceedState(transactionId, transition) match {
      case Right(nextState) =>
        val nextDefine = transactionService.showDefine(workflowId, nextState.currentStepId)
        orderQuery.findByTransactionId(transactionId) match {
          case Some(orderEntity) =>
            val newOrder = orderEntity.copy(
              statusId = Some(nextDefine.get.stepId.toString),
              statusName = Some(nextDefine.get.stepLabel))

            orderRepository.update(newOrder)

          case None => Left(new Exception(s"no transaction Id for userId: ${userId}, transactionId: ${transactionId}"))
        }

      case Left(exception) =>
        Left(new Exception(exception))
    }
  }

}
