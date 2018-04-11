package io.wonder.soft.example.domain.example.order.service

import io.wonder.soft.example.domain.example.order.OrderAction
import io.wonder.soft.example.domain.example.order.orderActions.{AssignMember, FixPayment, SetShipmentDay, ShipItem}

/**
  * TODO implement
  */
class OrderTransitionService {

  def executeTransition(orderAction: OrderAction) = {

    orderAction match {
      case AssignMember =>
      case SetShipmentDay  =>
      case FixPayment =>
      case ShipItem =>
    }

  }

}
