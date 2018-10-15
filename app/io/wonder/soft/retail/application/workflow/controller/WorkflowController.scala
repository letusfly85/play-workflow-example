package io.wonder.soft.retail.application.workflow.controller

import io.wonder.soft.retail.application.example.craft.service.CraftLineActionService
import javax.inject._
import io.wonder.soft.retail.application.workflow.service.{WorkflowConditionService, WorkflowService}
import io.wonder.soft.retail.domain.workflow.entity._
import io.wonder.soft.retail.application.helper.JsResultHelper
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

  def list = Action {
    Ok(Json.toJson(service.list))
  }

  def find(workflowId: String) = Action { implicit request =>
    Ok(Json.toJson(service.find(workflowId.toInt)))
  }

  def create = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        summaryEntity <- Json.fromJson[WorkflowEntity](json).right
        entity <- service.create(summaryEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(summaryEntity) => Created(Json.toJson(summaryEntity))
        case Left(e) => errorHandler(e)
      }
      case Failure(e) => errorHandler(new Exception(e))
    }
  }

  //todo use cats
  //import cats.implicits._
  def update(workflowId: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        workflowEntity <- Json.fromJson[WorkflowEntity](json).right
        entity <- service.update(workflowEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(workflowEntity) => Created(Json.toJson(workflowEntity))
        case Left(e) => errorHandler(e)
      }
      case Failure(e) => errorHandler(new Exception(e))
    }
  }

  def destroy(id: String) = Action { implicit request =>
    Try {
      for {
        json <- request.body.asJson.toRight(new Exception("")).right
        summaryEntity <- Json.fromJson[WorkflowEntity](json).right
        entity <- service.destroy(summaryEntity).right
      } yield entity

    } match {
      case Success(either) => either match {
        case Right(summaryEntity) => Ok(Json.toJson(summaryEntity))
        case Left(e) => errorHandler(e)
      }
      case Failure(e) => errorHandler(new Exception(e))
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

}
