package io.wonder.soft.retail.application.example.order.controller

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import io.wonder.soft.retail.application.example.order.service.OrderService
import io.wonder.soft.retail.application.helper.JsResultHelper
import io.wonder.soft.retail.domain.example.order.entity.OrderEntity
import play.api.Logging

import scala.util.{Failure, Success, Try}

@Singleton
class OrderController @Inject()
(service: OrderService, cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper with Logging {

  def listOrder = Action {
    Ok(Json.toJson(service.listOrder))
  }

  def findOrder(workflowId: String, orderId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        orderEntity <- Json.fromJson[OrderEntity](json).right
        entity <- service.openOrder(workflowId, orderEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(exception) =>
          logger.info(exception.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(exception) =>
        exception.printStackTrace()
        InternalServerError(JsObject.empty)
    }
  }

}
