package io.wonder.soft.example.domain.workflow

import io.wonder.soft.example.domain.workflow.entity.WorkflowSchemeEntity
import io.wonder.soft.example.domain.workflow.model.WorkflowSchemes
import io.wonder.soft.example.domain.Repository
import scalikejdbc._

import scala.util.{Failure, Success, Try}

object WorkflowSchemeRepository extends Repository[WorkflowSchemeEntity] {
  import WorkflowSchemeEntity._

  val wsc = WorkflowSchemes.column

  override def find(id: Int): Option[WorkflowSchemeEntity] = {
    val maybeSchemes = WorkflowSchemes.find(id)
    maybeSchemes.flatMap[WorkflowSchemeEntity](schemes => Some(schemes))
  }

  def findStatusId(workflowId: Int): Option[Int] = {
    WorkflowSchemes.findBy(
      sqls.eq(WorkflowSchemes.column.workflowId, workflowId)).map(scheme => scheme.statusId
    )
  }

  override def create(entity: WorkflowSchemeEntity): Either[Exception, WorkflowSchemeEntity] = {
    Try {
      DB localTx {implicit session =>
        withSQL {
          insert.into(WorkflowSchemes).namedValues(
            wsc.workflowId -> entity.workflowId,
            wsc.name -> entity.name,
            wsc.statusId -> entity.status.get.id,
            wsc.schemeStepId -> entity.schemeStepId,
            wsc.schemeStepLabel -> entity.schemeStepLabel,
            wsc.isFirstStep -> entity.isFirstStep,
            wsc.isLastStep -> entity.isLastStep
          )
        }.update().apply()
      }

    } match {
      case Success(_) => Right(entity)
      case Failure(e) => Left(new Exception(e))
    }
  }

  //TODO
  override def destroy(id: Int): Option[WorkflowSchemeEntity] = None

  //TODO
  override def update(entity: WorkflowSchemeEntity): Either[Exception, WorkflowSchemeEntity] = ???

}
