package io.wonder.soft.example.domain.example.order.service

import io.wonder.soft.example.domain.example.order.OrderAction
import io.wonder.soft.example.domain.example.order.orderActions.{AssignMemberAction, FixPaymentAction, SetShipmentDayAction, ShipItemAction}

/**
  * TODO implement
  */
class OrderTransitionService {

  def executeTransition(orderAction: OrderAction) = {

    orderAction match {
      case AssignMemberAction =>
      case SetShipmentDayAction  =>
      case FixPaymentAction =>
      case ShipItemAction =>
    }

  }

}
