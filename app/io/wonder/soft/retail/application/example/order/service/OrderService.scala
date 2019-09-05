package io.wonder.soft.retail.application.example.order.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository
import io.wonder.soft.retail.domain.example.order.service.OrderTransitionService
import io.wonder.soft.retail.domain.example.order.service.orderActions._
import javax.inject.Inject
import play.api.Logging

class OrderService @Inject()
  (transactionService: WorkflowTransactionService,
   transitionService: OrderTransitionService,
   orderRepository: OrderRepository,
   orderQuery: OrderQueryProcessor
  ) extends ApplicationService with Logging {

  def listOrder: List[OrderEntity] = orderQuery.listOrder

  def openOrder(workflowId: String, orderEntity: OrderEntity): Either[Exception, OrderEntity] = {
    orderRepository.find(orderEntity.id) match {
      case Some(order) if order.transactionId.getOrElse("") == "" =>
        logger.info(s"find not yet initialized order entity ${orderEntity.toString}")
        transactionService.openTransaction(userId = "1", workflowId = workflowId.toInt) match {
          case Right(transaction) =>
            val workflowStep = transactionService.findStep(workflowId.toInt, transaction.stepId).get
            val newOrder = order.copy(
              transactionId = Some(transaction.transactionId),
              statusId = Some(workflowStep.stepId.toString),
              statusName = Some(workflowStep.stepLabel)
            )
            orderRepository.update(newOrder)

          case Left(exception) =>
            Left(new Exception(exception))
        }

      case Some(order) =>
        logger.info(s"find order entity ${order.toString}")
        Right(order)

      case None =>
        Left(new RuntimeException(s"NO_ORDER_FOUND ${workflowId} ${orderEntity.id}"))
    }
  }

  //todo hook workflow
  def assignMember: Either[Exception, OrderEntity] = {
    transitionService.executeTransition(AssignMemberAction)
    ???
  }

  //todo hook workflow
  def setShipmentDay: Either[Exception, OrderEntity] = {
    transitionService.executeTransition(SetShipmentDayAction)
    ???
  }

  //todo hook workflow
  def fixPayment: Either[Exception, OrderEntity] = {
    transitionService.executeTransition(FixPaymentAction)
    ???
  }

  //todo hook workflow
  def shipItem: Either[Exception, OrderEntity] = {
    transitionService.executeTransition(ShipItemAction)
    ???
  }

}
