package io.wonder.soft.retail.application.example.craft.controller

import io.wonder.soft.retail.application.example.craft.service.CraftLineService
import io.wonder.soft.retail.application.helper.JsResultHelper
import io.wonder.soft.retail.domain.example.craft.entity.CraftLineEntity
import javax.inject._
import play.api.Logging
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class CraftLineController @Inject()
(service: CraftLineService, cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper with Logging {

  def listCraftLine = Action {
    Ok(Json.toJson(service.listCraftLine))
  }

  def findCraftLine(craftLineId: String) = Action { implicit request =>
    Try {
      for {
        entity <- service.openCraftLine(craftLineId).right
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
