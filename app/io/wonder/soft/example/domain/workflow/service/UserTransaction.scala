package io.wonder.soft.example.domain.workflow.service

import entity.WorkflowCurrentStateEntity
import io.wonder.soft.example.application.workflow.service.WorkflowTransactionService
import io.wonder.soft.example.domain.example.order.entity.OrderEntity
import io.wonder.soft.example.domain.example.order.query.OrderQueryProcessor
import io.wonder.soft.example.domain.example.order.repository.OrderRepository
import javax.inject.Inject

class UserTransaction @Inject()
  (transactionService: WorkflowTransactionService,
   orderRepository: OrderRepository,
   orderQueryProcessor: OrderQueryProcessor)
{

  def updateUserRepository(currentStateEntity: WorkflowCurrentStateEntity): Either[Exception, WorkflowCurrentStateEntity] = {
    currentStateEntity.serviceId match {
      case 0 =>
        updateOrderRepository(currentStateEntity) match {
          case Right(_) => Right(currentStateEntity)
          case Left(exception) => Left(new Exception(exception))
        }

      case _ =>
        Left(new RuntimeException(s"there is no service for ${currentStateEntity.toString}"))
    }
  }

  private def updateOrderRepository(currentStateEntity: WorkflowCurrentStateEntity): Either[Exception, OrderEntity] = {
    orderQueryProcessor.findByTransactionId(currentStateEntity.transactionId) match {
      case Some(orderEntity) =>
        val define = transactionService.showDefine(currentStateEntity.workflowId, currentStateEntity.currentStepId).get
        val nextOrderEntity =
          orderEntity.copy(
            statusId = Some(currentStateEntity.currentStepId.toString), statusName = Some(define.stepLabel)
          )
        orderRepository.update(nextOrderEntity)

      case None =>
        Left(new RuntimeException(s"there is no order for ${currentStateEntity.toString}"))
    }
  }

}
