package models

import scalikejdbc._
import org.joda.time.{DateTime}

case class WorkflowStatus(
  id: Int,
  name: String,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowStatus.autoSession): WorkflowStatus = WorkflowStatus.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowStatus.autoSession): Int = WorkflowStatus.destroy(this)(session)

}


object WorkflowStatus extends SQLSyntaxSupport[WorkflowStatus] {

  override val schemaName = Some("simple_workflow")

  override val tableName = "workflow_status"

  override val columns = Seq("id", "name", "created_at", "updated_at")

  def apply(ws: SyntaxProvider[WorkflowStatus])(rs: WrappedResultSet): WorkflowStatus = apply(ws.resultName)(rs)
  def apply(ws: ResultName[WorkflowStatus])(rs: WrappedResultSet): WorkflowStatus = new WorkflowStatus(
    id = rs.get(ws.id),
    name = rs.get(ws.name),
    createdAt = rs.get(ws.createdAt),
    updatedAt = rs.get(ws.updatedAt)
  )

  val ws = WorkflowStatus.syntax("ws")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowStatus] = {
    withSQL {
      select.from(WorkflowStatus as ws).where.eq(ws.id, id)
    }.map(WorkflowStatus(ws.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowStatus] = {
    withSQL(select.from(WorkflowStatus as ws)).map(WorkflowStatus(ws.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowStatus as ws)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowStatus] = {
    withSQL {
      select.from(WorkflowStatus as ws).where.append(where)
    }.map(WorkflowStatus(ws.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowStatus] = {
    withSQL {
      select.from(WorkflowStatus as ws).where.append(where)
    }.map(WorkflowStatus(ws.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowStatus as ws).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowStatus = {
    val generatedKey = withSQL {
      insert.into(WorkflowStatus).namedValues(
        column.name -> name,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowStatus(
      id = generatedKey.toInt,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowStatus])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_status(
      name,
      created_at,
      updated_at
    ) values (
      {name},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowStatus)(implicit session: DBSession = autoSession): WorkflowStatus = {
    withSQL {
      update(WorkflowStatus).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowStatus)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowStatus).where.eq(column.id, entity.id) }.update.apply()
  }

}
