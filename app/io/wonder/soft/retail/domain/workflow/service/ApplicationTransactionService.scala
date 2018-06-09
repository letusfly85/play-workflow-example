package io.wonder.soft.retail.domain.workflow.service

import io.wonder.soft.retail.domain.example.craft.entity.CraftLineEntity
import io.wonder.soft.retail.domain.example.craft.query.CraftLineQueryProcessor
import io.wonder.soft.retail.domain.example.craft.repository.CraftLineRepository
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import io.wonder.soft.retail.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.retail.domain.example.order.repository.OrderRepository
import io.wonder.soft.retail.domain.workflow.entity.{WorkflowCurrentStateEntity, WorkflowDefinitionEntity}
import javax.inject.Inject
import play.api.Logger

class ApplicationTransactionService @Inject()
  (orderRepository: OrderRepository,
   orderQueryProcessor: OrderQueryProcessor,
   craftLinesRepository: CraftLineRepository,
   craftLineQueryProcessor: CraftLineQueryProcessor
  )
{
  val orderServiceId: Int = 0
  val craftServiceId: Int = 3
  val serviceMap = Map(orderServiceId -> 'order, craftServiceId -> 'craft)

  def updateAppTransaction(define: WorkflowDefinitionEntity, currentStateEntity: WorkflowCurrentStateEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    serviceMap.get(currentStateEntity.serviceId) match {
      case Some('order) =>
        updateOrderRepository(define, currentStateEntity) match {
          case Right(_) => Right(currentStateEntity)
          case Left(exception) => Left(new Exception(exception))
        }

      case Some('craft) =>
        updateCraftLineRepository(define, currentStateEntity) match {
          case Right(_) => Right(currentStateEntity)
          case Left(exception) => Left(new Exception(exception))
        }

      case _ =>
        Left(new RuntimeException(s"there is no service for ${currentStateEntity.toString}"))
    }
  }

  private def updateOrderRepository(define: WorkflowDefinitionEntity, currentStateEntity: WorkflowCurrentStateEntity): Either[Exception, OrderEntity] = {
    orderQueryProcessor.findByTransactionId(currentStateEntity.transactionId) match {
      case Some(orderEntity) =>
        val nextOrderEntity =
          orderEntity.copy(
            statusId = Some(currentStateEntity.currentStepId.toString), statusName = Some(define.stepLabel)
          )
        orderRepository.update(nextOrderEntity)

      case None =>
        Left(new RuntimeException(s"there is no order for ${currentStateEntity.toString}"))
    }
  }

  private def updateCraftLineRepository(define: WorkflowDefinitionEntity, currentStateEntity: WorkflowCurrentStateEntity): Either[Exception, CraftLineEntity] = {
    Logger.info(s"update craftLine for ${currentStateEntity.toString}")
    craftLineQueryProcessor.findByTransactionId(currentStateEntity.transactionId) match {
      case Some(craftLineEntity) =>
        val nextCraftLineEntity =
          craftLineEntity.copy(
            statusId = currentStateEntity.currentStepId.toString,
            statusName = Some(define.stepLabel)
          )
        craftLinesRepository.update(nextCraftLineEntity)

      case None =>
        Left(new RuntimeException(s"there is no order for ${currentStateEntity.toString}"))
    }
  }

}
