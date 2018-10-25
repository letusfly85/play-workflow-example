package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class Issues(
  id: Int,
  issueId: String,
  transactionId: Option[String] = None,
  statusId: String,
  statusName: Option[String] = None,
  serviceId: Option[String] = None,
  projectId: Option[String] = None,
  userId: Option[String] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = Issues.autoSession): Issues = Issues.save(this)(session)

  def destroy()(implicit session: DBSession = Issues.autoSession): Int = Issues.destroy(this)(session)

}


object Issues extends SQLSyntaxSupport[Issues] {

  override val tableName = "issues"

  override val columns = Seq("id", "issue_id", "transaction_id", "status_id", "status_name", "service_id", "project_id", "user_id", "created_at", "updated_at")

  def apply(i: SyntaxProvider[Issues])(rs: WrappedResultSet): Issues = apply(i.resultName)(rs)
  def apply(i: ResultName[Issues])(rs: WrappedResultSet): Issues = new Issues(
    id = rs.get(i.id),
    issueId = rs.get(i.issueId),
    transactionId = rs.get(i.transactionId),
    statusId = rs.get(i.statusId),
    statusName = rs.get(i.statusName),
    serviceId = rs.get(i.serviceId),
    projectId = rs.get(i.projectId),
    userId = rs.get(i.userId),
    createdAt = rs.get(i.createdAt),
    updatedAt = rs.get(i.updatedAt)
  )

  val i = Issues.syntax("i")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Issues] = {
    withSQL {
      select.from(Issues as i).where.eq(i.id, id)
    }.map(Issues(i.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Issues] = {
    withSQL(select.from(Issues as i)).map(Issues(i.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Issues as i)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Issues] = {
    withSQL {
      select.from(Issues as i).where.append(where)
    }.map(Issues(i.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Issues] = {
    withSQL {
      select.from(Issues as i).where.append(where)
    }.map(Issues(i.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Issues as i).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    issueId: String,
    transactionId: Option[String] = None,
    statusId: String,
    statusName: Option[String] = None,
    serviceId: Option[String] = None,
    projectId: Option[String] = None,
    userId: Option[String] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): Issues = {
    val generatedKey = withSQL {
      insert.into(Issues).namedValues(
        column.issueId -> issueId,
        column.transactionId -> transactionId,
        column.statusId -> statusId,
        column.statusName -> statusName,
        column.serviceId -> serviceId,
        column.projectId -> projectId,
        column.userId -> userId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Issues(
      id = generatedKey.toInt,
      issueId = issueId,
      transactionId = transactionId,
      statusId = statusId,
      statusName = statusName,
      serviceId = serviceId,
      projectId = projectId,
      userId = userId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[Issues])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'issueId -> entity.issueId,
        'transactionId -> entity.transactionId,
        'statusId -> entity.statusId,
        'statusName -> entity.statusName,
        'serviceId -> entity.serviceId,
        'projectId -> entity.projectId,
        'userId -> entity.userId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into issues(
      issue_id,
      transaction_id,
      status_id,
      status_name,
      service_id,
      project_id,
      user_id,
      created_at,
      updated_at
    ) values (
      {issueId},
      {transactionId},
      {statusId},
      {statusName},
      {serviceId},
      {projectId},
      {userId},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Issues)(implicit session: DBSession = autoSession): Issues = {
    withSQL {
      update(Issues).set(
        column.id -> entity.id,
        column.issueId -> entity.issueId,
        column.transactionId -> entity.transactionId,
        column.statusId -> entity.statusId,
        column.statusName -> entity.statusName,
        column.serviceId -> entity.serviceId,
        column.projectId -> entity.projectId,
        column.userId -> entity.userId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Issues)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Issues).where.eq(column.id, entity.id) }.update.apply()
  }

}
