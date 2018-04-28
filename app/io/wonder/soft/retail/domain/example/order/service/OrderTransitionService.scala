package io.wonder.soft.retail.domain.example.order.service

import io.wonder.soft.retail.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository
import io.wonder.soft.retail.domain.example.order.service.orderActions._
import javax.inject.Inject

class OrderTransitionService @Inject()
   (orderQuery: OrderQueryProcessor,
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

}
