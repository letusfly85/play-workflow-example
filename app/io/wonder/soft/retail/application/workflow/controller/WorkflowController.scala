package io.wonder.soft.retail.application.workflow.controller

import io.wonder.soft.retail.application.example.craft.service.CraftLineActionService
import javax.inject._
import io.wonder.soft.retail.application.workflow.service.{WorkflowConditionService, WorkflowService}
import io.wonder.soft.retail.domain.workflow.entity._
import io.wonder.soft.retail.application.helper.JsResultHelper
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._

import scala.util.{Failure, Success, Try}

@Singleton
class WorkflowController @Inject()
  (service: WorkflowService,
   conditionService: WorkflowConditionService,
   craftLineActionService: CraftLineActionService,
   cc: ControllerComponents)
  extends AbstractController(cc) with JsResultHelper {

  def listSummary = Action {
    Ok(Json.toJson(service.listSummary))
  }

  def createSummary = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        summaryEntity <- Json.fromJson[WorkflowEntity](json).right
        entity <- service.createSummary(summaryEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(summaryEntity) => Created(Json.toJson(summaryEntity))
        case Left(e) =>
          Logger.info(e.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(e) =>
        Logger.info(e.getMessage)
        InternalServerError(JsObject.empty)
    }
  }

  def destroySummary = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        summaryEntity <- Json.fromJson[WorkflowEntity](json).right
        entity <- service.destroySummary(summaryEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(summaryEntity) => Ok(Json.toJson(summaryEntity))
        case Left(e) =>
          Logger.info(e.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(e) =>
        Logger.info(e.getMessage)
        InternalServerError(JsObject.empty)
    }
  }

  def listDefinition(id: String) = Action { implicit request =>
    Ok(Json.toJson(service.listDefinition(id.toInt)))
  }

  //todo use cats
  //import cats.implicits._
  def createDefinition(workflowId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        schemeEntity <- Json.fromJson[WorkflowDetailEntity](json).right
        entity <- service.createDefinition(schemeEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(definitionEntity) => Created(Json.toJson(definitionEntity))
        case Left(e) =>
          Logger.info(e.toString)
          InternalServerError(JsObject.empty)
      }
      case Failure(e) =>
        Logger.info(e.getMessage)
        InternalServerError(JsObject.empty)
    }
  }

  def listCraftLineActions = Action { implicit request =>
    Ok(Json.toJson(craftLineActionService.listActions))
  }

  def listCraftLineConditions(workflowId: String, transitionId: String) = Action { implicit request =>
    val result = conditionService.searchCraftLineActions(workflowId.toInt, transitionId.toInt)
    Ok(Json.toJson(result))
  }

  def saveActionConditions(workflowId: String, transitionId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("json parameter is wrong")).right
        conditionEntities <- Json.fromJson[List[WorkflowActionConditionEntity]](json).right
        entity <- conditionService.saveActionConditions(conditionEntities).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(transitionEntity) => Created(Json.toJson(transitionEntity))
        case Left(e) => InternalServerError(JsObject.empty)
      }
      case Failure(_) => InternalServerError(JsObject.empty)
    }
  }

  // TODO
  def listOrderActions = ???

  def createActionCondition = ???

  def deleteActionCondition = ???

}
