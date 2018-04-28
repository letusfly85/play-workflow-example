package io.wonder.soft.retail.application.example.order.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository
import io.wonder.soft.retail.domain.example.order.service.OrderTransitionService
import io.wonder.soft.retail.domain.example.order.service.orderActions._
import javax.inject.Inject
import play.Logger

class OrderService @Inject()
  (transactionService: WorkflowTransactionService,
   transitionService: OrderTransitionService,
   orderRepository: OrderRepository,
   orderQuery: OrderQueryProcessor
  ) extends ApplicationService {

  val orderExampleWorkflowId = 2

  def listOrder: List[OrderEntity] = orderQuery.listOrder

  def openOrder(orderEntity: OrderEntity): Either[Exception, OrderEntity] = {
    orderRepository.find(orderEntity.id) match {
      case Some(order) if order.transactionId.getOrElse("") == "" =>
        Logger.info(s"find not yet initialized order entity ${orderEntity.toString}")
        transactionService.initialize(userId = "1", workflowId = orderExampleWorkflowId) match {
          case Right(transaction) =>
            val define = transactionService.showDefine(workflowId = orderExampleWorkflowId, transaction.stepId).get
            val newOrder = order.copy(
              transactionId = Some(transaction.transactionId),
              statusId = Some(define.stepId.toString),
              statusName = Some(define.stepLabel)
            )
            orderRepository.update(newOrder)

          case Left(exception) =>
            Left(new Exception(exception))
        }

      case Some(craftLine) =>
        Logger.info(s"find order entity ${craftLine.toString}")
        Right(craftLine)

      case None =>
        Left(new RuntimeException(""))
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
