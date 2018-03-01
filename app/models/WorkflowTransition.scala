package models

import scalikejdbc._
import org.joda.time.{DateTime}

case class WorkflowTransition(
  id: Int,
  workflowId: Int,
  name: String,
  statusId: String,
  fromStepId: Int,
  toStepId: Int,
  conditionType: String,
  conditionKey: Option[String] = None,
  conditionValue: Option[String] = None,
  conditionExpr: Option[String] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowTransition.autoSession): WorkflowTransition = WorkflowTransition.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowTransition.autoSession): Int = WorkflowTransition.destroy(this)(session)

}


object WorkflowTransition extends SQLSyntaxSupport[WorkflowTransition] {

  override val schemaName = Some("simple_workflow")

  override val tableName = "workflow_transition"

  override val columns = Seq("id", "workflow_id", "name", "status_id", "from_step_id", "to_step_id", "condition_type", "condition_key", "condition_value", "condition_expr", "created_at", "updated_at")

  def apply(wt: SyntaxProvider[WorkflowTransition])(rs: WrappedResultSet): WorkflowTransition = apply(wt.resultName)(rs)
  def apply(wt: ResultName[WorkflowTransition])(rs: WrappedResultSet): WorkflowTransition = new WorkflowTransition(
    id = rs.get(wt.id),
    workflowId = rs.get(wt.workflowId),
    name = rs.get(wt.name),
    statusId = rs.get(wt.statusId),
    fromStepId = rs.get(wt.fromStepId),
    toStepId = rs.get(wt.toStepId),
    conditionType = rs.get(wt.conditionType),
    conditionKey = rs.get(wt.conditionKey),
    conditionValue = rs.get(wt.conditionValue),
    conditionExpr = rs.get(wt.conditionExpr),
    createdAt = rs.get(wt.createdAt),
    updatedAt = rs.get(wt.updatedAt)
  )

  val wt = WorkflowTransition.syntax("wt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowTransition] = {
    withSQL {
      select.from(WorkflowTransition as wt).where.eq(wt.id, id)
    }.map(WorkflowTransition(wt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowTransition] = {
    withSQL(select.from(WorkflowTransition as wt)).map(WorkflowTransition(wt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowTransition as wt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowTransition] = {
    withSQL {
      select.from(WorkflowTransition as wt).where.append(where)
    }.map(WorkflowTransition(wt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowTransition] = {
    withSQL {
      select.from(WorkflowTransition as wt).where.append(where)
    }.map(WorkflowTransition(wt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowTransition as wt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    name: String,
    statusId: String,
    fromStepId: Int,
    toStepId: Int,
    conditionType: String,
    conditionKey: Option[String] = None,
    conditionValue: Option[String] = None,
    conditionExpr: Option[String] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowTransition = {
    val generatedKey = withSQL {
      insert.into(WorkflowTransition).namedValues(
        column.workflowId -> workflowId,
        column.name -> name,
        column.statusId -> statusId,
        column.fromStepId -> fromStepId,
        column.toStepId -> toStepId,
        column.conditionType -> conditionType,
        column.conditionKey -> conditionKey,
        column.conditionValue -> conditionValue,
        column.conditionExpr -> conditionExpr,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowTransition(
      id = generatedKey.toInt,
      workflowId = workflowId,
      name = name,
      statusId = statusId,
      fromStepId = fromStepId,
      toStepId = toStepId,
      conditionType = conditionType,
      conditionKey = conditionKey,
      conditionValue = conditionValue,
      conditionExpr = conditionExpr,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowTransition])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'name -> entity.name,
        'statusId -> entity.statusId,
        'fromStepId -> entity.fromStepId,
        'toStepId -> entity.toStepId,
        'conditionType -> entity.conditionType,
        'conditionKey -> entity.conditionKey,
        'conditionValue -> entity.conditionValue,
        'conditionExpr -> entity.conditionExpr,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_transition(
      workflow_id,
      name,
      status_id,
      from_step_id,
      to_step_id,
      condition_type,
      condition_key,
      condition_value,
      condition_expr,
      created_at,
      updated_at
    ) values (
      {workflowId},
      {name},
      {statusId},
      {fromStepId},
      {toStepId},
      {conditionType},
      {conditionKey},
      {conditionValue},
      {conditionExpr},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowTransition)(implicit session: DBSession = autoSession): WorkflowTransition = {
    withSQL {
      update(WorkflowTransition).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.name -> entity.name,
        column.statusId -> entity.statusId,
        column.fromStepId -> entity.fromStepId,
        column.toStepId -> entity.toStepId,
        column.conditionType -> entity.conditionType,
        column.conditionKey -> entity.conditionKey,
        column.conditionValue -> entity.conditionValue,
        column.conditionExpr -> entity.conditionExpr,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowTransition)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowTransition).where.eq(column.id, entity.id) }.update.apply()
  }

}
