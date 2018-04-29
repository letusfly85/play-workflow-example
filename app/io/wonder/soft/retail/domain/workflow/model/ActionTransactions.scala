package io.wonder.soft.retail.domain.workflow.model

import scalikejdbc._
import org.joda.time.{DateTime}
import scalikejdbc.jodatime.JodaParameterBinderFactory._
import scalikejdbc.jodatime.JodaTypeBinder._

case class ActionTransactions(
  id: Int,
  actionId: Int,
  workflowId: Int,
  transactionId: String,
  stepId: Int,
  isReverted: Boolean,
  createdAt: Option[DateTime] = None,
  updatedAt: Option[DateTime] = None) {

  def save()(implicit session: DBSession = ActionTransactions.autoSession): ActionTransactions = ActionTransactions.save(this)(session)

  def destroy()(implicit session: DBSession = ActionTransactions.autoSession): Int = ActionTransactions.destroy(this)(session)

}


object ActionTransactions extends SQLSyntaxSupport[ActionTransactions] {

  override val tableName = "action_transactions"

  override val columns = Seq("id", "action_id", "workflow_id", "transaction_id", "step_id", "is_reverted", "created_at", "updated_at")

  def apply(at: SyntaxProvider[ActionTransactions])(rs: WrappedResultSet): ActionTransactions = apply(at.resultName)(rs)
  def apply(at: ResultName[ActionTransactions])(rs: WrappedResultSet): ActionTransactions = new ActionTransactions(
    id = rs.get(at.id),
    actionId = rs.get(at.actionId),
    workflowId = rs.get(at.workflowId),
    transactionId = rs.get(at.transactionId),
    stepId = rs.get(at.stepId),
    isReverted = rs.get(at.isReverted),
    createdAt = rs.get(at.createdAt),
    updatedAt = rs.get(at.updatedAt)
  )

  val at = ActionTransactions.syntax("at")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[ActionTransactions] = {
    withSQL {
      select.from(ActionTransactions as at).where.eq(at.id, id)
    }.map(ActionTransactions(at.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ActionTransactions] = {
    withSQL(select.from(ActionTransactions as at)).map(ActionTransactions(at.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ActionTransactions as at)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ActionTransactions] = {
    withSQL {
      select.from(ActionTransactions as at).where.append(where)
    }.map(ActionTransactions(at.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ActionTransactions] = {
    withSQL {
      select.from(ActionTransactions as at).where.append(where)
    }.map(ActionTransactions(at.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ActionTransactions as at).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    actionId: Int,
    workflowId: Int,
    transactionId: String,
    stepId: Int,
    isReverted: Boolean,
    createdAt: Option[DateTime] = None,
    updatedAt: Option[DateTime] = None)(implicit session: DBSession = autoSession): ActionTransactions = {
    val generatedKey = withSQL {
      insert.into(ActionTransactions).namedValues(
        column.actionId -> actionId,
        column.workflowId -> workflowId,
        column.transactionId -> transactionId,
        column.stepId -> stepId,
        column.isReverted -> isReverted,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    ActionTransactions(
      id = generatedKey.toInt,
      actionId = actionId,
      workflowId = workflowId,
      transactionId = transactionId,
      stepId = stepId,
      isReverted = isReverted,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[ActionTransactions])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'actionId -> entity.actionId,
        'workflowId -> entity.workflowId,
        'transactionId -> entity.transactionId,
        'stepId -> entity.stepId,
        'isReverted -> entity.isReverted,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into action_transactions(
      action_id,
      workflow_id,
      transaction_id,
      step_id,
      is_reverted,
      created_at,
      updated_at
    ) values (
      {actionId},
      {workflowId},
      {transactionId},
      {stepId},
      {isReverted},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: ActionTransactions)(implicit session: DBSession = autoSession): ActionTransactions = {
    withSQL {
      update(ActionTransactions).set(
        column.id -> entity.id,
        column.actionId -> entity.actionId,
        column.workflowId -> entity.workflowId,
        column.transactionId -> entity.transactionId,
        column.stepId -> entity.stepId,
        column.isReverted -> entity.isReverted,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: ActionTransactions)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(ActionTransactions).where.eq(column.id, entity.id) }.update.apply()
  }

}
