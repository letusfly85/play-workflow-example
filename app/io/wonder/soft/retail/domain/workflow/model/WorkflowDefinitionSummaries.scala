package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class WorkflowDefinitionSummaries(
  id: Int,
  workflowId: Int,
  name: String,
  serviceId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowDefinitionSummaries.autoSession): WorkflowDefinitionSummaries = WorkflowDefinitionSummaries.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowDefinitionSummaries.autoSession): Int = WorkflowDefinitionSummaries.destroy(this)(session)

}


object WorkflowDefinitionSummaries extends SQLSyntaxSupport[WorkflowDefinitionSummaries] {

  override val tableName = "workflow_definition_summaries"

  override val columns = Seq("id", "workflow_id", "name", "service_id", "created_at", "updated_at")

  def apply(wds: SyntaxProvider[WorkflowDefinitionSummaries])(rs: WrappedResultSet): WorkflowDefinitionSummaries = apply(wds.resultName)(rs)
  def apply(wds: ResultName[WorkflowDefinitionSummaries])(rs: WrappedResultSet): WorkflowDefinitionSummaries = new WorkflowDefinitionSummaries(
    id = rs.get(wds.id),
    workflowId = rs.get(wds.workflowId),
    name = rs.get(wds.name),
    serviceId = rs.get(wds.serviceId),
    createdAt = rs.get(wds.createdAt),
    updatedAt = rs.get(wds.updatedAt)
  )

  val wds = WorkflowDefinitionSummaries.syntax("wds")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowDefinitionSummaries] = {
    withSQL {
      select.from(WorkflowDefinitionSummaries as wds).where.eq(wds.id, id)
    }.map(WorkflowDefinitionSummaries(wds.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowDefinitionSummaries] = {
    withSQL(select.from(WorkflowDefinitionSummaries as wds)).map(WorkflowDefinitionSummaries(wds.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowDefinitionSummaries as wds)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowDefinitionSummaries] = {
    withSQL {
      select.from(WorkflowDefinitionSummaries as wds).where.append(where)
    }.map(WorkflowDefinitionSummaries(wds.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowDefinitionSummaries] = {
    withSQL {
      select.from(WorkflowDefinitionSummaries as wds).where.append(where)
    }.map(WorkflowDefinitionSummaries(wds.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowDefinitionSummaries as wds).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    name: String,
    serviceId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowDefinitionSummaries = {
    val generatedKey = withSQL {
      insert.into(WorkflowDefinitionSummaries).namedValues(
        column.workflowId -> workflowId,
        column.name -> name,
        column.serviceId -> serviceId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowDefinitionSummaries(
      id = generatedKey.toInt,
      workflowId = workflowId,
      name = name,
      serviceId = serviceId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowDefinitionSummaries])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'name -> entity.name,
        'serviceId -> entity.serviceId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_definition_summaries(
      workflow_id,
      name,
      service_id,
      created_at,
      updated_at
    ) values (
      {workflowId},
      {name},
      {serviceId},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowDefinitionSummaries)(implicit session: DBSession = autoSession): WorkflowDefinitionSummaries = {
    withSQL {
      update(WorkflowDefinitionSummaries).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.name -> entity.name,
        column.serviceId -> entity.serviceId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowDefinitionSummaries)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowDefinitionSummaries).where.eq(column.id, entity.id) }.update.apply()
  }

}
