package io.wonder.soft.example.application.example.order.service

import io.wonder.soft.example.application.ApplicationService
import io.wonder.soft.example.domain.example.order.entity.OrderEntity

class OrderService extends ApplicationService {
  import OrderEntity._

  def listOrder: List[OrderEntity] = ???

  //todo hook workflow
  def assignMember: Either[Exception, OrderEntity] = ???

  //todo hook workflow
  def setShipmentDay: Either[Exception, OrderEntity] = ???

  //todo hook workflow
  def fixPayment: Either[Exception, OrderEntity] = ???

  //todo hook workflow
  def shipItem: Either[Exception, OrderEntity] = ???

}
