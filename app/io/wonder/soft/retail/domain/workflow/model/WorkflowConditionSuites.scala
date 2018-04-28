package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class WorkflowConditionSuites(
  id: Int,
  suiteId: Int,
  workflowConditionId: Int,
  conditionGroupCode: String,
  conditionsExpr: String,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowConditionSuites.autoSession): WorkflowConditionSuites = WorkflowConditionSuites.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowConditionSuites.autoSession): Int = WorkflowConditionSuites.destroy(this)(session)

}


object WorkflowConditionSuites extends SQLSyntaxSupport[WorkflowConditionSuites] {

  override val tableName = "workflow_condition_suites"

  override val columns = Seq("id", "suite_id", "workflow_condition_id", "condition_group_code", "conditions_expr", "created_at", "updated_at")

  def apply(wcs: SyntaxProvider[WorkflowConditionSuites])(rs: WrappedResultSet): WorkflowConditionSuites = apply(wcs.resultName)(rs)
  def apply(wcs: ResultName[WorkflowConditionSuites])(rs: WrappedResultSet): WorkflowConditionSuites = new WorkflowConditionSuites(
    id = rs.get(wcs.id),
    suiteId = rs.get(wcs.suiteId),
    workflowConditionId = rs.get(wcs.workflowConditionId),
    conditionGroupCode = rs.get(wcs.conditionGroupCode),
    conditionsExpr = rs.get(wcs.conditionsExpr),
    createdAt = rs.get(wcs.createdAt),
    updatedAt = rs.get(wcs.updatedAt)
  )

  val wcs = WorkflowConditionSuites.syntax("wcs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowConditionSuites] = {
    withSQL {
      select.from(WorkflowConditionSuites as wcs).where.eq(wcs.id, id)
    }.map(WorkflowConditionSuites(wcs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowConditionSuites] = {
    withSQL(select.from(WorkflowConditionSuites as wcs)).map(WorkflowConditionSuites(wcs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowConditionSuites as wcs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowConditionSuites] = {
    withSQL {
      select.from(WorkflowConditionSuites as wcs).where.append(where)
    }.map(WorkflowConditionSuites(wcs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowConditionSuites] = {
    withSQL {
      select.from(WorkflowConditionSuites as wcs).where.append(where)
    }.map(WorkflowConditionSuites(wcs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowConditionSuites as wcs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    suiteId: Int,
    workflowConditionId: Int,
    conditionGroupCode: String,
    conditionsExpr: String,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowConditionSuites = {
    val generatedKey = withSQL {
      insert.into(WorkflowConditionSuites).namedValues(
        column.suiteId -> suiteId,
        column.workflowConditionId -> workflowConditionId,
        column.conditionGroupCode -> conditionGroupCode,
        column.conditionsExpr -> conditionsExpr,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowConditionSuites(
      id = generatedKey.toInt,
      suiteId = suiteId,
      workflowConditionId = workflowConditionId,
      conditionGroupCode = conditionGroupCode,
      conditionsExpr = conditionsExpr,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowConditionSuites])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'suiteId -> entity.suiteId,
        'workflowConditionId -> entity.workflowConditionId,
        'conditionGroupCode -> entity.conditionGroupCode,
        'conditionsExpr -> entity.conditionsExpr,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_condition_suites(
      suite_id,
      workflow_condition_id,
      condition_group_code,
      conditions_expr,
      created_at,
      updated_at
    ) values (
      {suiteId},
      {workflowConditionId},
      {conditionGroupCode},
      {conditionsExpr},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowConditionSuites)(implicit session: DBSession = autoSession): WorkflowConditionSuites = {
    withSQL {
      update(WorkflowConditionSuites).set(
        column.id -> entity.id,
        column.suiteId -> entity.suiteId,
        column.workflowConditionId -> entity.workflowConditionId,
        column.conditionGroupCode -> entity.conditionGroupCode,
        column.conditionsExpr -> entity.conditionsExpr,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowConditionSuites)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowConditionSuites).where.eq(column.id, entity.id) }.update.apply()
  }

}
