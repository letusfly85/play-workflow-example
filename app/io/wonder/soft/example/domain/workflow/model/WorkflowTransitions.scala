package io.wonder.soft.example.domain.workflow.model

import org.joda.time.DateTime
import scalikejdbc._

case class WorkflowTransitions(
  id: Int,
  workflowId: Int,
  name: String,
  fromStepId: Int,
  toStepId: Int,
  conditionSuiteId: Option[Int] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None,
  isDefined: Boolean) {

  def save()(implicit session: DBSession = WorkflowTransitions.autoSession): WorkflowTransitions = WorkflowTransitions.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowTransitions.autoSession): Int = WorkflowTransitions.destroy(this)(session)

}


object WorkflowTransitions extends SQLSyntaxSupport[WorkflowTransitions] {

  override val tableName = "workflow_transitions"

  override val columns = Seq("id", "workflow_id", "name", "from_step_id", "to_step_id", "condition_suite_id", "created_at", "updated_at", "is_defined")

  def apply(wt: SyntaxProvider[WorkflowTransitions])(rs: WrappedResultSet): WorkflowTransitions = apply(wt.resultName)(rs)
  def apply(wt: ResultName[WorkflowTransitions])(rs: WrappedResultSet): WorkflowTransitions = new WorkflowTransitions(
    id = rs.get(wt.id),
    workflowId = rs.get(wt.workflowId),
    name = rs.get(wt.name),
    fromStepId = rs.get(wt.fromStepId),
    toStepId = rs.get(wt.toStepId),
    conditionSuiteId = rs.get(wt.conditionSuiteId),
    createdAt = rs.get(wt.createdAt),
    updatedAt = rs.get(wt.updatedAt),
    isDefined = rs.get(wt.isDefined)
  )

  val wt = WorkflowTransitions.syntax("wt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowTransitions] = {
    withSQL {
      select.from(WorkflowTransitions as wt).where.eq(wt.id, id)
    }.map(WorkflowTransitions(wt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowTransitions] = {
    withSQL(select.from(WorkflowTransitions as wt)).map(WorkflowTransitions(wt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowTransitions as wt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowTransitions] = {
    withSQL {
      select.from(WorkflowTransitions as wt).where.append(where)
    }.map(WorkflowTransitions(wt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowTransitions] = {
    withSQL {
      select.from(WorkflowTransitions as wt).where.append(where)
    }.map(WorkflowTransitions(wt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowTransitions as wt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    name: String,
    fromStepId: Int,
    toStepId: Int,
    conditionSuiteId: Option[Int] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None,
    isDefined: Boolean)(implicit session: DBSession = autoSession): WorkflowTransitions = {
    val generatedKey = withSQL {
      insert.into(WorkflowTransitions).namedValues(
        column.workflowId -> workflowId,
        column.name -> name,
        column.fromStepId -> fromStepId,
        column.toStepId -> toStepId,
        column.conditionSuiteId -> conditionSuiteId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt,
        column.isDefined -> isDefined
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowTransitions(
      id = generatedKey.toInt,
      workflowId = workflowId,
      name = name,
      fromStepId = fromStepId,
      toStepId = toStepId,
      conditionSuiteId = conditionSuiteId,
      createdAt = createdAt,
      updatedAt = updatedAt,
      isDefined = isDefined)
  }

  def batchInsert(entities: Seq[WorkflowTransitions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'name -> entity.name,
        'fromStepId -> entity.fromStepId,
        'toStepId -> entity.toStepId,
        'conditionSuiteId -> entity.conditionSuiteId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt,
        'isDefined -> entity.isDefined))
    SQL("""insert into workflow_transitions(
      workflow_id,
      name,
      from_step_id,
      to_step_id,
      condition_suite_id,
      created_at,
      updated_at,
      is_defined
    ) values (
      {workflowId},
      {name},
      {fromStepId},
      {toStepId},
      {conditionSuiteId},
      {createdAt},
      {updatedAt},
      {isDefined}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowTransitions)(implicit session: DBSession = autoSession): WorkflowTransitions = {
    withSQL {
      update(WorkflowTransitions).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.name -> entity.name,
        column.fromStepId -> entity.fromStepId,
        column.toStepId -> entity.toStepId,
        column.conditionSuiteId -> entity.conditionSuiteId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.isDefined -> entity.isDefined
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowTransitions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowTransitions).where.eq(column.id, entity.id) }.update.apply()
  }

}
