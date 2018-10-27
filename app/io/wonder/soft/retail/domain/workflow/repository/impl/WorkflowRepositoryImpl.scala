package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowEntity
import io.wonder.soft.retail.domain.workflow.model.{WorkflowSteps, Workflows}
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowRepositoryImpl extends WorkflowRepository {
  import WorkflowEntity._

  val w = Workflows.syntax("w")
  val wd = WorkflowSteps.syntax("wd")

  val wc = Workflows.column
  val wdc = WorkflowSteps.column

  override def find(id: Int): Option[WorkflowEntity] = {
    Workflows.find(id) match {
      case Some(model) => Some(model)
      case None => None
    }
  }

  override def save(entity: WorkflowEntity): Either[Exception, WorkflowEntity] = {
    Try {
      DB localTx { implicit session =>
        Workflows.find(entity.id) match {
          case Some(_) =>
            if (entity.steps.nonEmpty) {
              entity.steps.foreach { detail =>
                WorkflowSteps.findBy(
                  sqls.eq(WorkflowSteps.column.workflowId, detail.workflowId)
                ).map(detail => detail.destroy())
              }

              entity.steps.foreach { detail =>
                withSQL {
                  insert.into(WorkflowSteps).namedValues(
                    wdc.workflowId -> entity.workflowId,
                    wdc.name -> entity.name,
                    wdc.statusId -> detail.status.map(s => s.id).getOrElse(0),
                    wdc.stepId -> detail.stepId,
                    wdc.stepLabel -> detail.stepLabel,
                    wdc.isFirstStep -> detail.isFirstStep,
                    wdc.isLastStep -> detail.isLastStep
                  )
                }.update().apply()
              }
            }
            val id = this.createWorkflow(entity)
            Workflows.find(id).get

          case None =>
            val id = this.createWorkflow(entity)
            Workflows.find(id).get
        }
      }
    } match {
      case Success(result) => Right(result)
      case Failure(ex) => Left(new Exception(ex))
    }
  }

  private def createWorkflow(entity: WorkflowEntity)(implicit session: DBSession): Int = {
    withSQL {
      insert.into(Workflows).namedValues(
        wc.workflowId -> entity.workflowId,
        wc.name -> entity.name,
        wc.description -> entity.description,
        wc.serviceId -> entity.serviceId
      )
    }.update().apply()
  }

  override def destroy(id: Int): Option[WorkflowEntity] = {
    Workflows.find(id) match {
      case Some(model) =>
        model.destroy()
        Some(model)

      case None => None
    }
  }

}
