package io.wonder.soft.example.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}

case class WorkflowCurrentStates(
  id: Int,
  workflowId: Int,
  transactionId: String,
  userId: Option[String] = None,
  currentStepId: Int,
  schemeId: Int,
  serviceId: Int,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowCurrentStates.autoSession): WorkflowCurrentStates = WorkflowCurrentStates.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowCurrentStates.autoSession): Int = WorkflowCurrentStates.destroy(this)(session)

}


object WorkflowCurrentStates extends SQLSyntaxSupport[WorkflowCurrentStates] {

  override val tableName = "workflow_current_states"

  override val columns = Seq("id", "workflow_id", "transaction_id", "user_id", "current_step_id", "scheme_id", "service_id", "created_at", "updated_at")

  def apply(wcs: SyntaxProvider[WorkflowCurrentStates])(rs: WrappedResultSet): WorkflowCurrentStates = apply(wcs.resultName)(rs)
  def apply(wcs: ResultName[WorkflowCurrentStates])(rs: WrappedResultSet): WorkflowCurrentStates = new WorkflowCurrentStates(
    id = rs.get(wcs.id),
    workflowId = rs.get(wcs.workflowId),
    transactionId = rs.get(wcs.transactionId),
    userId = rs.get(wcs.userId),
    currentStepId = rs.get(wcs.currentStepId),
    schemeId = rs.get(wcs.schemeId),
    serviceId = rs.get(wcs.serviceId),
    createdAt = rs.get(wcs.createdAt),
    updatedAt = rs.get(wcs.updatedAt)
  )

  val wcs = WorkflowCurrentStates.syntax("wcs")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowCurrentStates] = {
    withSQL {
      select.from(WorkflowCurrentStates as wcs).where.eq(wcs.id, id)
    }.map(WorkflowCurrentStates(wcs.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowCurrentStates] = {
    withSQL(select.from(WorkflowCurrentStates as wcs)).map(WorkflowCurrentStates(wcs.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowCurrentStates as wcs)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowCurrentStates] = {
    withSQL {
      select.from(WorkflowCurrentStates as wcs).where.append(where)
    }.map(WorkflowCurrentStates(wcs.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowCurrentStates] = {
    withSQL {
      select.from(WorkflowCurrentStates as wcs).where.append(where)
    }.map(WorkflowCurrentStates(wcs.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowCurrentStates as wcs).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    transactionId: String,
    userId: Option[String] = None,
    currentStepId: Int,
    schemeId: Int,
    serviceId: Int,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowCurrentStates = {
    val generatedKey = withSQL {
      insert.into(WorkflowCurrentStates).namedValues(
        column.workflowId -> workflowId,
        column.transactionId -> transactionId,
        column.userId -> userId,
        column.currentStepId -> currentStepId,
        column.schemeId -> schemeId,
        column.serviceId -> serviceId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowCurrentStates(
      id = generatedKey.toInt,
      workflowId = workflowId,
      transactionId = transactionId,
      userId = userId,
      currentStepId = currentStepId,
      schemeId = schemeId,
      serviceId = serviceId,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowCurrentStates])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'transactionId -> entity.transactionId,
        'userId -> entity.userId,
        'currentStepId -> entity.currentStepId,
        'schemeId -> entity.schemeId,
        'serviceId -> entity.serviceId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_current_states(
      workflow_id,
      transaction_id,
      user_id,
      current_step_id,
      scheme_id,
      service_id,
      created_at,
      updated_at
    ) values (
      {workflowId},
      {transactionId},
      {userId},
      {currentStepId},
      {schemeId},
      {serviceId},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowCurrentStates)(implicit session: DBSession = autoSession): WorkflowCurrentStates = {
    withSQL {
      update(WorkflowCurrentStates).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.transactionId -> entity.transactionId,
        column.userId -> entity.userId,
        column.currentStepId -> entity.currentStepId,
        column.schemeId -> entity.schemeId,
        column.serviceId -> entity.serviceId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowCurrentStates)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowCurrentStates).where.eq(column.id, entity.id) }.update.apply()
  }

}
