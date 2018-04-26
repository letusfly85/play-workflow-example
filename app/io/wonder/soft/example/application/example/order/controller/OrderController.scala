package io.wonder.soft.example.application.example.order.controller

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import io.wonder.soft.example.application.example.order.service.OrderService
import io.wonder.soft.example.application.helper.JsResultHelper
import io.wonder.soft.example.domain.example.order.entity.{OrderEntity, OrderTransitionEntity}
import play.api.Logger

import scala.util.{Failure, Success, Try}

@Singleton
class OrderController @Inject()
(service: OrderService, cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

  def listOrder = Action {
    Ok(Json.toJson(service.listOrder))
  }

  def findOrder(orderId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        orderEntity <- Json.fromJson[OrderEntity](json).right
        entity <- service.openOrder(orderEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(exception) =>
          Logger.info(exception.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(exception) =>
        exception.printStackTrace()
        InternalServerError(JsObject.empty)
    }
  }

  def proceedState = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        orderEntity <- Json.fromJson[OrderTransitionEntity](json).right
        entity <- service.proceedState(orderEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(exception) =>
          Logger.info(exception.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(exception) =>
        exception.printStackTrace()
        InternalServerError(JsObject.empty)
    }
  }

  /**
    *
    * @return
    */
  @deprecated("this should not be used", "2.0.4")
  def proceedStateWithOrder = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        orderEntity <- Json.fromJson[OrderTransitionEntity](json).right
        entity <- service.proceedState(orderEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(statusEntity) => Created(Json.toJson(statusEntity))
        case Left(exception) =>
          Logger.info(exception.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(exception) =>
        exception.printStackTrace()
        InternalServerError(JsObject.empty)
    }
  }

}
