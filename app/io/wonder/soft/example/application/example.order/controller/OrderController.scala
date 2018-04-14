package io.wonder.soft.example.application.example.order.controller

import javax.inject._

import play.api.mvc._
import play.api.libs.json._

import io.wonder.soft.example.application.example.order.service.OrderService
import io.wonder.soft.example.application.helper.JsResultHelper

@Singleton
class OrderController @Inject()
(service: OrderService, cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

  def listOrder = Action {
    Ok(Json.toJson(service.listOrder))
  }

}
