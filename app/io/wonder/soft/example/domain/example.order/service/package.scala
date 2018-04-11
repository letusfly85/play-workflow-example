package io.wonder.soft.example.domain.example.order

trait OrderAction

package object orderActions {

  case object AssignMember extends OrderAction

  case object SetShipmentDay extends OrderAction

  case object FixPayment extends OrderAction

  case object ShipItem extends OrderAction

}
