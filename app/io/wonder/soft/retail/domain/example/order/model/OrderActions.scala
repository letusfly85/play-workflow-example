package io.wonder.soft.retail.domain.example.order.model

import scalikejdbc._
import org.joda.time.{DateTime}

case class OrderActions(
  id: Int,
  name: String,
  description: Option[String] = None,
  serviceId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = OrderActions.autoSession): OrderActions = OrderActions.save(this)(session)

  def destroy()(implicit session: DBSession = OrderActions.autoSession): Int = OrderActions.destroy(this)(session)

}


object OrderActions extends SQLSyntaxSupport[OrderActions] {

  override val tableName = "order_actions"

  override val columns = Seq("id", "name", "description", "service_id", "created_at", "updated_at")

  def apply(oa: SyntaxProvider[OrderActions])(rs: WrappedResultSet): OrderActions = apply(oa.resultName)(rs)
  def apply(oa: ResultName[OrderActions])(rs: WrappedResultSet): OrderActions = new OrderActions(
    id = rs.get(oa.id),
    name = rs.get(oa.name),
    description = rs.get(oa.description),
    serviceId = rs.get(oa.serviceId),
    createdAt = rs.get(oa.createdAt),
    updatedAt = rs.get(oa.updatedAt)
  )

  val oa = OrderActions.syntax("oa")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[OrderActions] = {
    withSQL {
      select.from(OrderActions as oa).where.eq(oa.id, id)
    }.map(OrderActions(oa.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[OrderActions] = {
    withSQL(select.from(OrderActions as oa)).map(OrderActions(oa.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(OrderActions as oa)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[OrderActions] = {
    withSQL {
      select.from(OrderActions as oa).where.append(where)
    }.map(OrderActions(oa.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[OrderActions] = {
    withSQL {
      select.from(OrderActions as oa).where.append(where)
    }.map(OrderActions(oa.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(OrderActions as oa).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: Option[String] = None,
    serviceId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): OrderActions = {
    val generatedKey = withSQL {
      insert.into(OrderActions).namedValues(
        column.name -> name,
        column.description -> description,
        column.serviceId -> serviceId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    OrderActions(
      id = generatedKey.toInt,
      name = name,
      description = description,
      serviceId = serviceId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[OrderActions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'description -> entity.description,
        'serviceId -> entity.serviceId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into order_actions(
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

  def save(entity: OrderActions)(implicit session: DBSession = autoSession): OrderActions = {
    withSQL {
      update(OrderActions).set(
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

  def destroy(entity: OrderActions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(OrderActions).where.eq(column.id, entity.id) }.update.apply()
  }

}
