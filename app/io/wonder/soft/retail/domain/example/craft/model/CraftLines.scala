package io.wonder.soft.retail.domain.example.craft.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class CraftLines(
  id: Int,
  transactionId: Option[String] = None,
  statusId: String,
  statusName: Option[String] = None,
  productName: Option[String] = None,
  assignedMemberName: Option[String] = None,
  serviceId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = CraftLines.autoSession): CraftLines = CraftLines.save(this)(session)

  def destroy()(implicit session: DBSession = CraftLines.autoSession): Int = CraftLines.destroy(this)(session)

}


object CraftLines extends SQLSyntaxSupport[CraftLines] {

  override val tableName = "craft_lines"

  override val columns = Seq("id", "transaction_id", "status_id", "status_name", "product_name", "assigned_member_name", "service_id", "created_at", "updated_at")

  def apply(cl: SyntaxProvider[CraftLines])(rs: WrappedResultSet): CraftLines = apply(cl.resultName)(rs)
  def apply(cl: ResultName[CraftLines])(rs: WrappedResultSet): CraftLines = new CraftLines(
    id = rs.get(cl.id),
    transactionId = rs.get(cl.transactionId),
    statusId = rs.get(cl.statusId),
    statusName = rs.get(cl.statusName),
    productName = rs.get(cl.productName),
    assignedMemberName = rs.get(cl.assignedMemberName),
    serviceId = rs.get(cl.serviceId),
    createdAt = rs.get(cl.createdAt),
    updatedAt = rs.get(cl.updatedAt)
  )

  val cl = CraftLines.syntax("cl")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CraftLines] = {
    withSQL {
      select.from(CraftLines as cl).where.eq(cl.id, id)
    }.map(CraftLines(cl.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CraftLines] = {
    withSQL(select.from(CraftLines as cl)).map(CraftLines(cl.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CraftLines as cl)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CraftLines] = {
    withSQL {
      select.from(CraftLines as cl).where.append(where)
    }.map(CraftLines(cl.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CraftLines] = {
    withSQL {
      select.from(CraftLines as cl).where.append(where)
    }.map(CraftLines(cl.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CraftLines as cl).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    transactionId: Option[String] = None,
    statusId: String,
    statusName: Option[String] = None,
    productName: Option[String] = None,
    assignedMemberName: Option[String] = None,
    serviceId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): CraftLines = {
    val generatedKey = withSQL {
      insert.into(CraftLines).namedValues(
        column.transactionId -> transactionId,
        column.statusId -> statusId,
        column.statusName -> statusName,
        column.productName -> productName,
        column.assignedMemberName -> assignedMemberName,
        column.serviceId -> serviceId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    CraftLines(
      id = generatedKey.toInt,
      transactionId = transactionId,
      statusId = statusId,
      statusName = statusName,
      productName = productName,
      assignedMemberName = assignedMemberName,
      serviceId = serviceId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[CraftLines])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'transactionId -> entity.transactionId,
        'statusId -> entity.statusId,
        'statusName -> entity.statusName,
        'productName -> entity.productName,
        'assignedMemberName -> entity.assignedMemberName,
        'serviceId -> entity.serviceId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into craft_lines(
      transaction_id,
      status_id,
      status_name,
      product_name,
      assigned_member_name,
      service_id,
      created_at,
      updated_at
    ) values (
      {transactionId},
      {statusId},
      {statusName},
      {productName},
      {assignedMemberName},
      {serviceId},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: CraftLines)(implicit session: DBSession = autoSession): CraftLines = {
    withSQL {
      update(CraftLines).set(
        column.id -> entity.id,
        column.transactionId -> entity.transactionId,
        column.statusId -> entity.statusId,
        column.statusName -> entity.statusName,
        column.productName -> entity.productName,
        column.assignedMemberName -> entity.assignedMemberName,
        column.serviceId -> entity.serviceId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CraftLines)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(CraftLines).where.eq(column.id, entity.id) }.update.apply()
  }

}
