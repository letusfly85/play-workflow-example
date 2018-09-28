package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class WorkflowDetails(
  id: Int,
  workflowId: Int,
  name: String,
  statusId: Int,
  status: Option[WorkflowStatuses] = None,
  stepId: Int,
  stepLabel: String,
  isFirstStep: Boolean,
  isLastStep: Boolean,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowDetails.autoSession): WorkflowDetails = WorkflowDetails.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowDetails.autoSession): Int = WorkflowDetails.destroy(this)(session)

}


object WorkflowDetails extends SQLSyntaxSupport[WorkflowDetails] {

  override val tableName = "workflow_details"

  override val columns = Seq("id", "workflow_id", "name", "status_id", "step_id", "step_label", "is_first_step", "is_last_step", "created_at", "updated_at")

  def apply(wd: SyntaxProvider[WorkflowDetails])(rs: WrappedResultSet): WorkflowDetails = apply(wd.resultName)(rs)
  def apply(wd: ResultName[WorkflowDetails])(rs: WrappedResultSet): WorkflowDetails = {
    val statusId: Int = rs.int(wd.statusId)
    new WorkflowDetails(
      id = rs.get(wd.id),
      workflowId = rs.get(wd.workflowId),
      name = rs.get(wd.name),
      statusId = rs.get(wd.statusId),
      status = WorkflowStatuses.find(statusId),
      stepId = rs.get(wd.stepId),
      stepLabel = rs.get(wd.stepLabel),
      isFirstStep = rs.get(wd.isFirstStep),
      isLastStep = rs.get(wd.isLastStep),
      createdAt = rs.get(wd.createdAt),
      updatedAt = rs.get(wd.updatedAt)
    )
  }

  def opt(m: SyntaxProvider[WorkflowDetails])(rs: WrappedResultSet): Option[WorkflowDetails] =
    rs.longOpt(m.resultName.id).map(_ => WorkflowDetails(m)(rs))

  val wd = WorkflowDetails.syntax("wd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowDetails] = {
    withSQL {
      select.from(WorkflowDetails as wd).where.eq(wd.id, id)
    }.map(WorkflowDetails(wd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowDetails] = {
    withSQL(select.from(WorkflowDetails as wd)).map(WorkflowDetails(wd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowDetails as wd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowDetails] = {
    withSQL {
      select.from(WorkflowDetails as wd).where.append(where)
    }.map(WorkflowDetails(wd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowDetails] = {
    withSQL {
      select.from(WorkflowDetails as wd).where.append(where)
    }.map(WorkflowDetails(wd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowDetails as wd).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    name: String,
    statusId: Int,
    stepId: Int,
    stepLabel: String,
    isFirstStep: Boolean,
    isLastStep: Boolean,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowDetails = {
    val generatedKey = withSQL {
      insert.into(WorkflowDetails).namedValues(
        column.workflowId -> workflowId,
        column.name -> name,
        column.statusId -> statusId,
        column.stepId -> stepId,
        column.stepLabel -> stepLabel,
        column.isFirstStep -> isFirstStep,
        column.isLastStep -> isLastStep,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowDetails(
      id = generatedKey.toInt,
      workflowId = workflowId,
      name = name,
      statusId = statusId,
      status = WorkflowStatuses.find(statusId),
      stepId = stepId,
      stepLabel = stepLabel,
      isFirstStep = isFirstStep,
      isLastStep = isLastStep,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowDetails])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'name -> entity.name,
        'statusId -> entity.statusId,
        'stepId -> entity.stepId,
        'stepLabel -> entity.stepLabel,
        'isFirstStep -> entity.isFirstStep,
        'isLastStep -> entity.isLastStep,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_details(
      workflow_id,
      name,
      status_id,
      step_id,
      step_label,
      is_first_step,
      is_last_step,
      created_at,
      updated_at
    ) values (
      {workflowId},
      {name},
      {statusId},
      {stepId},
      {stepLabel},
      {isFirstStep},
      {isLastStep},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowDetails)(implicit session: DBSession = autoSession): WorkflowDetails = {
    withSQL {
      update(WorkflowDetails).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.name -> entity.name,
        column.statusId -> entity.statusId,
        column.stepId -> entity.stepId,
        column.stepLabel -> entity.stepLabel,
        column.isFirstStep -> entity.isFirstStep,
        column.isLastStep -> entity.isLastStep,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowDetails)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowDetails).where.eq(column.id, entity.id) }.update.apply()
  }

}
