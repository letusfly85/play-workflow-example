package io.wonder.soft.retail.domain.example.order.repository

import io.wonder.soft.retail.domain.example.order.entity.OrderEntity

trait OrderRepository {

  def find(id: Int): Option[OrderEntity]

  def create(entity: OrderEntity): Either[Exception, OrderEntity]

  def update(entity: OrderEntity): Either[RuntimeException, OrderEntity]

  def destroy(id: Int): Option[OrderEntity]
}

