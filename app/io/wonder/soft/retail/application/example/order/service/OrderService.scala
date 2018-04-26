package io.wonder.soft.retail.application.example.order.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.retail.domain.example.order.entity.OrderTransitionEntity
import io.wonder.soft.retail.domain.example.order.service.OrderTransitionService
import io.wonder.soft.retail.domain.example.order.service.orderActions._
import javax.inject.Inject

class OrderService @Inject()
  (transitionService: OrderTransitionService,
   orderQuery: OrderQueryProcessor
  ) extends ApplicationService {

  def listOrder: List[OrderEntity] = orderQuery.listOrder

  def openOrder(orderEntity: OrderEntity): Either[Exception, OrderEntity] = {
    transitionService.initializeTransaction(orderEntity)
  }

  def proceedState(orderTransition: OrderTransitionEntity): Either[Exception, OrderEntity] = {
    transitionService.proceedState(orderTransition.transactionId, orderTransition.transition)
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
