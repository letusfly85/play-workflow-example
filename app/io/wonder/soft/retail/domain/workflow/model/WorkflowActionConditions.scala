package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class WorkflowActionConditions(
  id: Int,
  workflowId: Int,
  transitionId: Int,
  actionId: Int,
  serviceId: Int,
  name: Option[String] = None,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowActionConditions.autoSession): WorkflowActionConditions = WorkflowActionConditions.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowActionConditions.autoSession): Int = WorkflowActionConditions.destroy(this)(session)

}


object WorkflowActionConditions extends SQLSyntaxSupport[WorkflowActionConditions] {

  override val tableName = "workflow_action_conditions"

  override val columns = Seq("id", "workflow_id", "transition_id", "action_id", "service_id", "name", "created_at", "updated_at")

  def apply(wac: SyntaxProvider[WorkflowActionConditions])(rs: WrappedResultSet): WorkflowActionConditions = apply(wac.resultName)(rs)
  def apply(wac: ResultName[WorkflowActionConditions])(rs: WrappedResultSet): WorkflowActionConditions = new WorkflowActionConditions(
    id = rs.get(wac.id),
    workflowId = rs.get(wac.workflowId),
    transitionId = rs.get(wac.transitionId),
    actionId = rs.get(wac.actionId),
    serviceId = rs.get(wac.serviceId),
    name = rs.get(wac.name),
    createdAt = rs.get(wac.createdAt),
    updatedAt = rs.get(wac.updatedAt)
  )

  val wac = WorkflowActionConditions.syntax("wac")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowActionConditions] = {
    withSQL {
      select.from(WorkflowActionConditions as wac).where.eq(wac.id, id)
    }.map(WorkflowActionConditions(wac.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowActionConditions] = {
    withSQL(select.from(WorkflowActionConditions as wac)).map(WorkflowActionConditions(wac.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowActionConditions as wac)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowActionConditions] = {
    withSQL {
      select.from(WorkflowActionConditions as wac).where.append(where)
    }.map(WorkflowActionConditions(wac.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowActionConditions] = {
    withSQL {
      select.from(WorkflowActionConditions as wac).where.append(where)
    }.map(WorkflowActionConditions(wac.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowActionConditions as wac).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    transitionId: Int,
    actionId: Int,
    serviceId: Int,
    name: Option[String] = None,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowActionConditions = {
    val generatedKey = withSQL {
      insert.into(WorkflowActionConditions).namedValues(
        column.workflowId -> workflowId,
        column.transitionId -> transitionId,
        column.actionId -> actionId,
        column.serviceId -> serviceId,
        column.name -> name,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowActionConditions(
      id = generatedKey.toInt,
      workflowId = workflowId,
      transitionId = transitionId,
      actionId = actionId,
      serviceId = serviceId,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowActionConditions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'transitionId -> entity.transitionId,
        'actionId -> entity.actionId,
        'serviceId -> entity.serviceId,
        'name -> entity.name,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_action_conditions(
      workflow_id,
      transition_id,
      action_id,
      service_id,
      name,
      created_at,
      updated_at
    ) values (
      {workflowId},
      {transitionId},
      {actionId},
      {serviceId},
      {name},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowActionConditions)(implicit session: DBSession = autoSession): WorkflowActionConditions = {
    withSQL {
      update(WorkflowActionConditions).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.transitionId -> entity.transitionId,
        column.actionId -> entity.actionId,
        column.serviceId -> entity.serviceId,
        column.name -> entity.name,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowActionConditions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowActionConditions).where.eq(column.id, entity.id) }.update.apply()
  }

}
