package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class WorkflowConditions(
  id: Int,
  name: Option[String] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowConditions.autoSession): WorkflowConditions = WorkflowConditions.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowConditions.autoSession): Int = WorkflowConditions.destroy(this)(session)

}


object WorkflowConditions extends SQLSyntaxSupport[WorkflowConditions] {

  override val tableName = "workflow_conditions"

  override val columns = Seq("id", "name", "created_at", "updated_at")

  def apply(wc: SyntaxProvider[WorkflowConditions])(rs: WrappedResultSet): WorkflowConditions = apply(wc.resultName)(rs)
  def apply(wc: ResultName[WorkflowConditions])(rs: WrappedResultSet): WorkflowConditions = new WorkflowConditions(
    id = rs.get(wc.id),
    name = rs.get(wc.name),
    createdAt = rs.get(wc.createdAt),
    updatedAt = rs.get(wc.updatedAt)
  )

  val wc = WorkflowConditions.syntax("wc")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowConditions] = {
    withSQL {
      select.from(WorkflowConditions as wc).where.eq(wc.id, id)
    }.map(WorkflowConditions(wc.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowConditions] = {
    withSQL(select.from(WorkflowConditions as wc)).map(WorkflowConditions(wc.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowConditions as wc)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowConditions] = {
    withSQL {
      select.from(WorkflowConditions as wc).where.append(where)
    }.map(WorkflowConditions(wc.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowConditions] = {
    withSQL {
      select.from(WorkflowConditions as wc).where.append(where)
    }.map(WorkflowConditions(wc.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowConditions as wc).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: Option[String] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowConditions = {
    val generatedKey = withSQL {
      insert.into(WorkflowConditions).namedValues(
        column.name -> name,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowConditions(
      id = generatedKey.toInt,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowConditions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_conditions(
      name,
      created_at,
      updated_at
    ) values (
      {name},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowConditions)(implicit session: DBSession = autoSession): WorkflowConditions = {
    withSQL {
      update(WorkflowConditions).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowConditions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowConditions).where.eq(column.id, entity.id) }.update.apply()
  }

}
