package io.wonder.soft.example.domain.workflow.model

import org.joda.time.DateTime
import scalikejdbc._

case class WorkflowStatuses(
  id: Int,
  name: String,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowStatuses.autoSession): WorkflowStatuses = WorkflowStatuses.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowStatuses.autoSession): Int = WorkflowStatuses.destroy(this)(session)

}


object WorkflowStatuses extends SQLSyntaxSupport[WorkflowStatuses] {

  override val schemaName = None // Some("simple_workflow")

  override val tableName = "workflow_statuses"

  override val columns = Seq("id", "name", "created_at", "updated_at")

  def apply(ws: SyntaxProvider[WorkflowStatuses])(rs: WrappedResultSet): WorkflowStatuses = apply(ws.resultName)(rs)
  def apply(ws: ResultName[WorkflowStatuses])(rs: WrappedResultSet): WorkflowStatuses = new WorkflowStatuses(
    id = rs.get(ws.id),
    name = rs.get(ws.name),
    createdAt = rs.get(ws.createdAt),
    updatedAt = rs.get(ws.updatedAt)
  )

  val ws = WorkflowStatuses.syntax("ws")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowStatuses] = {
    withSQL {
      select.from(WorkflowStatuses as ws).where.eq(ws.id, id)
    }.map(WorkflowStatuses(ws.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowStatuses] = {
    withSQL(select.from(WorkflowStatuses as ws)).map(WorkflowStatuses(ws.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowStatuses as ws)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowStatuses] = {
    withSQL {
      select.from(WorkflowStatuses as ws).where.append(where)
    }.map(WorkflowStatuses(ws.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowStatuses] = {
    withSQL {
      select.from(WorkflowStatuses as ws).where.append(where)
    }.map(WorkflowStatuses(ws.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowStatuses as ws).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowStatuses = {
    val generatedKey = withSQL {
      insert.into(WorkflowStatuses).namedValues(
        column.name -> name,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowStatuses(
      id = generatedKey.toInt,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowStatuses])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_statuses(
      name,
      created_at,
      updated_at
    ) values (
      {name},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowStatuses)(implicit session: DBSession = autoSession): WorkflowStatuses = {
    withSQL {
      update(WorkflowStatuses).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowStatuses)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowStatuses).where.eq(column.id, entity.id) }.update.apply()
  }

}
