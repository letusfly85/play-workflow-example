package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}

case class WorkflowTransactions(
  id: Int,
  workflowId: Int,
  transactionId: String,
  userId: Option[String] = None,
  stepId: Int,
  fromTransitionId: Option[Int] = None,
  isInit: Boolean,
  isCompleted: Boolean,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = WorkflowTransactions.autoSession): WorkflowTransactions = WorkflowTransactions.save(this)(session)

  def destroy()(implicit session: DBSession = WorkflowTransactions.autoSession): Int = WorkflowTransactions.destroy(this)(session)

}


object WorkflowTransactions extends SQLSyntaxSupport[WorkflowTransactions] {

  override val tableName = "workflow_transactions"

  override val columns = Seq("id", "workflow_id", "transaction_id", "user_id", "step_id", "from_transition_id", "is_init", "is_completed", "created_at", "updated_at")

  def apply(wt: SyntaxProvider[WorkflowTransactions])(rs: WrappedResultSet): WorkflowTransactions = apply(wt.resultName)(rs)
  def apply(wt: ResultName[WorkflowTransactions])(rs: WrappedResultSet): WorkflowTransactions = new WorkflowTransactions(
    id = rs.get(wt.id),
    workflowId = rs.get(wt.workflowId),
    transactionId = rs.get(wt.transactionId),
    userId = rs.get(wt.userId),
    stepId = rs.get(wt.stepId),
    fromTransitionId = rs.get(wt.fromTransitionId),
    isInit = rs.get(wt.isInit),
    isCompleted = rs.get(wt.isCompleted),
    createdAt = rs.get(wt.createdAt),
    updatedAt = rs.get(wt.updatedAt)
  )

  val wt = WorkflowTransactions.syntax("wt")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[WorkflowTransactions] = {
    withSQL {
      select.from(WorkflowTransactions as wt).where.eq(wt.id, id)
    }.map(WorkflowTransactions(wt.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[WorkflowTransactions] = {
    withSQL(select.from(WorkflowTransactions as wt)).map(WorkflowTransactions(wt.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(WorkflowTransactions as wt)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[WorkflowTransactions] = {
    withSQL {
      select.from(WorkflowTransactions as wt).where.append(where)
    }.map(WorkflowTransactions(wt.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[WorkflowTransactions] = {
    withSQL {
      select.from(WorkflowTransactions as wt).where.append(where)
    }.map(WorkflowTransactions(wt.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(WorkflowTransactions as wt).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    workflowId: Int,
    transactionId: String,
    userId: Option[String] = None,
    stepId: Int,
    fromTransitionId: Option[Int] = None,
    isInit: Boolean,
    isCompleted: Boolean,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): WorkflowTransactions = {
    val generatedKey = withSQL {
      insert.into(WorkflowTransactions).namedValues(
        column.workflowId -> workflowId,
        column.transactionId -> transactionId,
        column.userId -> userId,
        column.stepId -> stepId,
        column.fromTransitionId -> fromTransitionId,
        column.isInit -> isInit,
        column.isCompleted -> isCompleted,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    WorkflowTransactions(
      id = generatedKey.toInt,
      workflowId = workflowId,
      transactionId = transactionId,
      userId = userId,
      stepId = stepId,
      fromTransitionId = fromTransitionId,
      isInit = isInit,
      isCompleted = isCompleted,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[WorkflowTransactions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'workflowId -> entity.workflowId,
        'transactionId -> entity.transactionId,
        'userId -> entity.userId,
        'stepId -> entity.stepId,
        'fromTransitionId -> entity.fromTransitionId,
        'isInit -> entity.isInit,
        'isCompleted -> entity.isCompleted,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into workflow_transactions(
      workflow_id,
      transaction_id,
      user_id,
      step_id,
      from_transition_id,
      is_init,
      is_completed,
      created_at,
      updated_at
    ) values (
      {workflowId},
      {transactionId},
      {userId},
      {stepId},
      {fromTransitionId},
      {isInit},
      {isCompleted},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: WorkflowTransactions)(implicit session: DBSession = autoSession): WorkflowTransactions = {
    withSQL {
      update(WorkflowTransactions).set(
        column.id -> entity.id,
        column.workflowId -> entity.workflowId,
        column.transactionId -> entity.transactionId,
        column.userId -> entity.userId,
        column.stepId -> entity.stepId,
        column.fromTransitionId -> entity.fromTransitionId,
        column.isInit -> entity.isInit,
        column.isCompleted -> entity.isCompleted,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: WorkflowTransactions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(WorkflowTransactions).where.eq(column.id, entity.id) }.update.apply()
  }

}
