package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.WorkflowEntity
import io.wonder.soft.retail.domain.workflow.model.{WorkflowDetails, Workflows}
import scalikejdbc._

import scala.util.{Failure, Success, Try}

class WorkflowRepositoryImpl extends WorkflowRepository {
  import WorkflowEntity._

  val w = Workflows.syntax("w")
  val wd = WorkflowDetails.syntax("wd")

  val wc = Workflows.column
  val wdc = WorkflowDetails.column

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
          case Some(workflow) =>
            if (entity.details.nonEmpty) {
              entity.details.foreach { detail =>
                WorkflowDetails.findBy(
                  sqls.eq(WorkflowDetails.column.workflowId, detail.workflowId)
                ).map(detail => detail.destroy())
              }

              entity.details.foreach { detail =>
                withSQL {
                  insert.into(WorkflowDetails).namedValues(
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
            val id = withSQL {
              update(Workflows).set(
                Workflows.column.name -> entity.name,
                Workflows.column.description -> entity.description,
                Workflows.column.serviceId -> entity.serviceId
              ).where.eq(Workflows.column.id, workflow.id)
            }.update.apply()

            Workflows.find(id).get

          case None =>
            val id = withSQL {
              insert.into(Workflows).namedValues(
                wc.workflowId -> entity.workflowId,
                wc.name -> entity.name,
                wc.description -> entity.description,
                wc.serviceId -> entity.serviceId
              )
            }.update().apply()

            Workflows.find(id).get
        }

      }
    } match {
      case Success(result) => Right(result)
      case Failure(ex) => Left(new Exception(ex))
    }
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
