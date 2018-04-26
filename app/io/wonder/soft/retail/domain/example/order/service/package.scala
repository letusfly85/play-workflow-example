package io.wonder.soft.retail.domain.example.order.service

trait OrderAction

package object orderActions {

  case object AssignMemberAction extends OrderAction

  case object SetShipmentDayAction extends OrderAction

  case object FixPaymentAction extends OrderAction

  case object ShipItemAction extends OrderAction

}
