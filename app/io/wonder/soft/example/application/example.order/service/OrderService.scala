package io.wonder.soft.example.application.example.order.service

import entity.OrderEntity
import io.wonder.soft.example.application.ApplicationService

class OrderService extends ApplicationService {

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
