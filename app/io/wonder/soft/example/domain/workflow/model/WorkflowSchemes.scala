package io.wonder.soft.example.domain.workflow.model

import org.joda.time.DateTime
import scalikejdbc._

case class WorkflowSchemes(
  id: Int,
  workflowId: Int,
  name: String,
  statusId: String,
  schemeStepId: Int,
  schemeStepLabel: String,
  isFirstStep: Boolean,
  isLastStep: Boolean,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowSchemes.autoSession): WorkflowSchemes = WorkflowSchemes.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowSchemes.autoSession): Int = WorkflowSchemes.destroy(this)(session)

}


object WorkflowSchemes extends SQLSyntaxSupport[WorkflowSchemes] {

  override val schemaName = None // Some("simple_workflow")

  override val tableName = "workflow_schemes"

  override val columns = Seq("id", "workflow_id", "name", "status_id", "scheme_step_id", "scheme_step_label", "is_first_step", "is_last_step", "created_at", "updated_at")

  def apply(ws: SyntaxProvider[WorkflowSchemes])(rs: WrappedResultSet): WorkflowSchemes = apply(ws.resultName)(rs)
  def apply(ws: ResultName[WorkflowSchemes])(rs: WrappedResultSet): WorkflowSchemes = new WorkflowSchemes(
    id = rs.get(ws.id),
    workflowId = rs.get(ws.workflowId),
    name = rs.get(ws.name),
    statusId = rs.get(ws.statusId),
    schemeStepId = rs.get(ws.schemeStepId),
    schemeStepLabel = rs.get(ws.schemeStepLabel),
    isFirstStep = rs.get(ws.isFirstStep),
    isLastStep = rs.get(ws.isLastStep),
    createdAt = rs.get(ws.createdAt),
    updatedAt = rs.get(ws.updatedAt)
  )

  val ws = WorkflowSchemes.syntax("ws")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowSchemes] = {
    withSQL {
      select.from(WorkflowSchemes as ws).where.eq(ws.id, id)
    }.map(WorkflowSchemes(ws.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowSchemes] = {
    withSQL(select.from(WorkflowSchemes as ws)).map(WorkflowSchemes(ws.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowSchemes as ws)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowSchemes] = {
    withSQL {
      select.from(WorkflowSchemes as ws).where.append(where)
    }.map(WorkflowSchemes(ws.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowSchemes] = {
    withSQL {
      select.from(WorkflowSchemes as ws).where.append(where)
    }.map(WorkflowSchemes(ws.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowSchemes as ws).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    name: String,
    statusId: String,
    schemeStepId: Int,
    schemeStepLabel: String,
    isFirstStep: Boolean,
    isLastStep: Boolean,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowSchemes = {
    val generatedKey = withSQL {
      insert.into(WorkflowSchemes).namedValues(
        column.workflowId -> workflowId,
        column.name -> name,
        column.statusId -> statusId,
        column.schemeStepId -> schemeStepId,
        column.schemeStepLabel -> schemeStepLabel,
        column.isFirstStep -> isFirstStep,
        column.isLastStep -> isLastStep,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowSchemes(
      id = generatedKey.toInt,
      workflowId = workflowId,
      name = name,
      statusId = statusId,
      schemeStepId = schemeStepId,
      schemeStepLabel = schemeStepLabel,
      isFirstStep = isFirstStep,
      isLastStep = isLastStep,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowSchemes])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'name -> entity.name,
        'statusId -> entity.statusId,
        'schemeStepId -> entity.schemeStepId,
        'schemeStepLabel -> entity.schemeStepLabel,
        'isFirstStep -> entity.isFirstStep,
        'isLastStep -> entity.isLastStep,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_schemes(
      workflow_id,
      name,
      status_id,
      scheme_step_id,
      scheme_step_label,
      is_first_step,
      is_last_step,
      created_at,
      updated_at
    ) values (
      {workflowId},
      {name},
      {statusId},
      {schemeStepId},
      {schemeStepLabel},
      {isFirstStep},
      {isLastStep},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowSchemes)(implicit session: DBSession = autoSession): WorkflowSchemes = {
    withSQL {
      update(WorkflowSchemes).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.name -> entity.name,
        column.statusId -> entity.statusId,
        column.schemeStepId -> entity.schemeStepId,
        column.schemeStepLabel -> entity.schemeStepLabel,
        column.isFirstStep -> entity.isFirstStep,
        column.isLastStep -> entity.isLastStep,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowSchemes)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowSchemes).where.eq(column.id, entity.id) }.update.apply()
  }

}
