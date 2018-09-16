package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class Workflows(
  id: Int,
  workflowId: Int,
  name: String,
  details: Seq[WorkflowDetails] = Nil,
  serviceId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None,
  description: Option[String] = None) {

  def save()(implicit session: DBSession = Workflows.autoSession): Workflows = Workflows.save(this)(session)

  def destroy()(implicit session: DBSession = Workflows.autoSession): Int = Workflows.destroy(this)(session)

}


object Workflows extends SQLSyntaxSupport[Workflows] {

  override val tableName = "workflows"

  override val columns = Seq("id", "workflow_id", "name", "service_id", "created_at", "updated_at", "description")

  def apply(w: SyntaxProvider[Workflows])(rs: WrappedResultSet): Workflows = apply(w.resultName)(rs)
  def apply(w: ResultName[Workflows])(rs: WrappedResultSet): Workflows = new Workflows(
    id = rs.get(w.id),
    workflowId = rs.get(w.workflowId),
    name = rs.get(w.name),
    serviceId = rs.get(w.serviceId),
    createdAt = rs.get(w.createdAt),
    updatedAt = rs.get(w.updatedAt),
    description = rs.get(w.description)
  )

  val w = Workflows.syntax("w")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Workflows] = {
    withSQL {
      select.from(Workflows as w).where.eq(w.id, id)
    }.map(Workflows(w.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Workflows] = {
    withSQL(select.from(Workflows as w)).map(Workflows(w.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Workflows as w)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Workflows] = {
    withSQL {
      select.from(Workflows as w).where.append(where)
    }.map(Workflows(w.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Workflows] = {
    withSQL {
      select.from(Workflows as w).where.append(where)
    }.map(Workflows(w.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Workflows as w).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    name: String,
    serviceId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None,
    description: Option[String] = None)(implicit session: DBSession = autoSession): Workflows = {
    val generatedKey = withSQL {
      insert.into(Workflows).namedValues(
        column.workflowId -> workflowId,
        column.name -> name,
        column.serviceId -> serviceId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt,
        column.description -> description
      )
    }.updateAndReturnGeneratedKey.apply()

    Workflows(
      id = generatedKey.toInt,
      workflowId = workflowId,
      name = name,
      serviceId = serviceId,
      createdAt = createdAt,
      updatedAt = updatedAt,
      description = description)
  }

  def batchInsert(entities: Seq[Workflows])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'name -> entity.name,
        'serviceId -> entity.serviceId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt,
        'description -> entity.description))
    SQL("""insert into workflows(
      workflow_id,
      name,
      service_id,
      created_at,
      updated_at,
      description
    ) values (
      {workflowId},
      {name},
      {serviceId},
      {createdAt},
      {updatedAt},
      {description}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Workflows)(implicit session: DBSession = autoSession): Workflows = {
    withSQL {
      update(Workflows).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.name -> entity.name,
        column.serviceId -> entity.serviceId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.description -> entity.description
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Workflows)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Workflows).where.eq(column.id, entity.id) }.update.apply()
  }

}
