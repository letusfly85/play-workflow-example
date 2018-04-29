package io.wonder.soft.retail.domain.example.craft.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class CraftLineActions(
  id: Int,
  name: String,
  description: Option[String] = None,
  serviceId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = CraftLineActions.autoSession): CraftLineActions = CraftLineActions.save(this)(session)

  def destroy()(implicit session: DBSession = CraftLineActions.autoSession): Int = CraftLineActions.destroy(this)(session)

}


object CraftLineActions extends SQLSyntaxSupport[CraftLineActions] {

  override val tableName = "craft_line_actions"

  override val columns = Seq("id", "name", "description", "service_id", "created_at", "updated_at")

  def apply(cla: SyntaxProvider[CraftLineActions])(rs: WrappedResultSet): CraftLineActions = apply(cla.resultName)(rs)
  def apply(cla: ResultName[CraftLineActions])(rs: WrappedResultSet): CraftLineActions = new CraftLineActions(
    id = rs.get(cla.id),
    name = rs.get(cla.name),
    description = rs.get(cla.description),
    serviceId = rs.get(cla.serviceId),
    createdAt = rs.get(cla.createdAt),
    updatedAt = rs.get(cla.updatedAt)
  )

  val cla = CraftLineActions.syntax("cla")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CraftLineActions] = {
    withSQL {
      select.from(CraftLineActions as cla).where.eq(cla.id, id)
    }.map(CraftLineActions(cla.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CraftLineActions] = {
    withSQL(select.from(CraftLineActions as cla)).map(CraftLineActions(cla.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CraftLineActions as cla)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CraftLineActions] = {
    withSQL {
      select.from(CraftLineActions as cla).where.append(where)
    }.map(CraftLineActions(cla.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CraftLineActions] = {
    withSQL {
      select.from(CraftLineActions as cla).where.append(where)
    }.map(CraftLineActions(cla.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CraftLineActions as cla).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: Option[String] = None,
    serviceId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): CraftLineActions = {
    val generatedKey = withSQL {
      insert.into(CraftLineActions).namedValues(
        column.name -> name,
        column.description -> description,
        column.serviceId -> serviceId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    CraftLineActions(
      id = generatedKey.toInt,
      name = name,
      description = description,
      serviceId = serviceId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[CraftLineActions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'description -> entity.description,
        'serviceId -> entity.serviceId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into craft_line_actions(
      name,
      description,
      service_id,
      created_at,
      updated_at
    ) values (
      {name},
      {description},
      {serviceId},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CraftLineActions)(implicit session: DBSession = autoSession): CraftLineActions = {
    withSQL {
      update(CraftLineActions).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.description -> entity.description,
        column.serviceId -> entity.serviceId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CraftLineActions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CraftLineActions).where.eq(column.id, entity.id) }.update.apply()
  }

}
