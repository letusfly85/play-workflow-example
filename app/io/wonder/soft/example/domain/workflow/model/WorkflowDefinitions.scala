package io.wonder.soft.example.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}

case class WorkflowDefinitions(
  id: Int,
  workflowId: Int,
  name: String,
  statusId: Int,
  stepId: Int,
  stepLabel: String,
  isFirstStep: Boolean,
  isLastStep: Boolean,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowDefinitions.autoSession): WorkflowDefinitions = WorkflowDefinitions.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowDefinitions.autoSession): Int = WorkflowDefinitions.destroy(this)(session)

}


object WorkflowDefinitions extends SQLSyntaxSupport[WorkflowDefinitions] {

  override val tableName = "workflow_definitions"

  override val columns = Seq("id", "workflow_id", "name", "status_id", "step_id", "step_label", "is_first_step", "is_last_step", "created_at", "updated_at")

  def apply(wd: SyntaxProvider[WorkflowDefinitions])(rs: WrappedResultSet): WorkflowDefinitions = apply(wd.resultName)(rs)
  def apply(wd: ResultName[WorkflowDefinitions])(rs: WrappedResultSet): WorkflowDefinitions = new WorkflowDefinitions(
    id = rs.get(wd.id),
    workflowId = rs.get(wd.workflowId),
    name = rs.get(wd.name),
    statusId = rs.get(wd.statusId),
    stepId = rs.get(wd.stepId),
    stepLabel = rs.get(wd.stepLabel),
    isFirstStep = rs.get(wd.isFirstStep),
    isLastStep = rs.get(wd.isLastStep),
    createdAt = rs.get(wd.createdAt),
    updatedAt = rs.get(wd.updatedAt)
  )

  val wd = WorkflowDefinitions.syntax("wd")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowDefinitions] = {
    withSQL {
      select.from(WorkflowDefinitions as wd).where.eq(wd.id, id)
    }.map(WorkflowDefinitions(wd.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowDefinitions] = {
    withSQL(select.from(WorkflowDefinitions as wd)).map(WorkflowDefinitions(wd.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowDefinitions as wd)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowDefinitions] = {
    withSQL {
      select.from(WorkflowDefinitions as wd).where.append(where)
    }.map(WorkflowDefinitions(wd.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowDefinitions] = {
    withSQL {
      select.from(WorkflowDefinitions as wd).where.append(where)
    }.map(WorkflowDefinitions(wd.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowDefinitions as wd).where.append(where)
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
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowDefinitions = {
    val generatedKey = withSQL {
      insert.into(WorkflowDefinitions).namedValues(
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

    WorkflowDefinitions(
      id = generatedKey.toInt,
      workflowId = workflowId,
      name = name,
      statusId = statusId,
      stepId = stepId,
      stepLabel = stepLabel,
      isFirstStep = isFirstStep,
      isLastStep = isLastStep,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowDefinitions])(implicit session: DBSession = autoSession): List[Int] = {
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
    SQL("""insert into workflow_definitions(
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

  def save(entity: WorkflowDefinitions)(implicit session: DBSession = autoSession): WorkflowDefinitions = {
    withSQL {
      update(WorkflowDefinitions).set(
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

  def destroy(entity: WorkflowDefinitions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowDefinitions).where.eq(column.id, entity.id) }.update.apply()
  }

}
