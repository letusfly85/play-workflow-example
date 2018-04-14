package io.wonder.soft.example.application.example.order.service

import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.example.order.entity.OrderEntity
import io.wonder.soft.example.domain.example.order.orderActions.{AssignMemberAction, FixPaymentAction, SetShipmentDayAction, ShipItemAction}
import io.wonder.soft.example.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.example.domain.example.order.service.OrderTransitionService
import javax.inject.Inject

class OrderService @Inject()
  (transitionService: OrderTransitionService,
   orderQuery: OrderQueryProcessor
  ) extends ApplicationService {

  def listOrder: List[OrderEntity] = orderQuery.listOrder

  def openOrder(orderEntity: OrderEntity): Either[Exception, OrderEntity] = {
    transitionService.initializeTransaction(orderEntity)
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
