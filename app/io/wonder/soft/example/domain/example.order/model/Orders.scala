package io.wonder.soft.example.domain.example.order.model

import scalikejdbc._
import org.joda.time.{DateTime}

case class Orders(
  id: Int,
  orderId: String,
  transactionId: Option[String] = None,
  statusId: String,
  statusName: Option[String] = None,
  customerName: Option[String] = None,
  assignedMemberName: Option[String] = None,
  serviceId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = Orders.autoSession): Orders = Orders.save(this)(session)

  def destroy()(implicit session: DBSession = Orders.autoSession): Int = Orders.destroy(this)(session)

}


object Orders extends SQLSyntaxSupport[Orders] {

  override val tableName = "orders"

  override val columns = Seq("id", "order_id", "transaction_id", "status_id", "status_name", "customer_name", "assigned_member_name", "service_id", "created_at", "updated_at")

  def apply(o: SyntaxProvider[Orders])(rs: WrappedResultSet): Orders = apply(o.resultName)(rs)
  def apply(o: ResultName[Orders])(rs: WrappedResultSet): Orders = new Orders(
    id = rs.get(o.id),
    orderId = rs.get(o.orderId),
    transactionId = rs.get(o.transactionId),
    statusId = rs.get(o.statusId),
    statusName = rs.get(o.statusName),
    customerName = rs.get(o.customerName),
    assignedMemberName = rs.get(o.assignedMemberName),
    serviceId = rs.get(o.serviceId),
    createdAt = rs.get(o.createdAt),
    updatedAt = rs.get(o.updatedAt)
  )

  val o = Orders.syntax("o")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Orders] = {
    withSQL {
      select.from(Orders as o).where.eq(o.id, id)
    }.map(Orders(o.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Orders] = {
    withSQL(select.from(Orders as o)).map(Orders(o.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Orders as o)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Orders] = {
    withSQL {
      select.from(Orders as o).where.append(where)
    }.map(Orders(o.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Orders] = {
    withSQL {
      select.from(Orders as o).where.append(where)
    }.map(Orders(o.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Orders as o).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    orderId: String,
    transactionId: Option[String] = None,
    statusId: String,
    statusName: Option[String] = None,
    customerName: Option[String] = None,
    assignedMemberName: Option[String] = None,
    serviceId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): Orders = {
    val generatedKey = withSQL {
      insert.into(Orders).namedValues(
        column.orderId -> orderId,
        column.transactionId -> transactionId,
        column.statusId -> statusId,
        column.statusName -> statusName,
        column.customerName -> customerName,
        column.assignedMemberName -> assignedMemberName,
        column.serviceId -> serviceId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Orders(
      id = generatedKey.toInt,
      orderId = orderId,
      transactionId = transactionId,
      statusId = statusId,
      statusName = statusName,
      customerName = customerName,
      assignedMemberName = assignedMemberName,
      serviceId = serviceId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[Orders])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'orderId -> entity.orderId,
        'transactionId -> entity.transactionId,
        'statusId -> entity.statusId,
        'statusName -> entity.statusName,
        'customerName -> entity.customerName,
        'assignedMemberName -> entity.assignedMemberName,
        'serviceId -> entity.serviceId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into orders(
      order_id,
      transaction_id,
      status_id,
      status_name,
      customer_name,
      assigned_member_name,
      service_id,
      created_at,
      updated_at
    ) values (
      {orderId},
      {transactionId},
      {statusId},
      {statusName},
      {customerName},
      {assignedMemberName},
      {serviceId},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Orders)(implicit session: DBSession = autoSession): Orders = {
    withSQL {
      update(Orders).set(
        column.id -> entity.id,
        column.orderId -> entity.orderId,
        column.transactionId -> entity.transactionId,
        column.statusId -> entity.statusId,
        column.statusName -> entity.statusName,
        column.customerName -> entity.customerName,
        column.assignedMemberName -> entity.assignedMemberName,
        column.serviceId -> entity.serviceId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Orders)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Orders).where.eq(column.id, entity.id) }.update.apply()
  }

}
