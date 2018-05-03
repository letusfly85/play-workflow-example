package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.ActionTransactions
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class ActionTransactionEntity(
    id: Int,
    actionId: Int,
    workflowId: Int,
    transactionId: String,
    stepId: Int,
    isReverted: Boolean,
    isFinished: Boolean = false
) extends Entity

object ActionTransactionEntity {
  implicit def actionTransactionsReads: Reads[ActionTransactionEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "action_id").read[Int] and
        (JsPath \ "workflow_id").read[Int] and
        (JsPath \ "transaction_id").read[String] and
        (JsPath \ "step_id").read[Int] and
        (JsPath \ "is_reverted").read[Boolean] and
        (JsPath \ "is_finished").read[Boolean]
    )(ActionTransactionEntity.apply _)

  implicit def actionTransactionsWrites: Writes[ActionTransactionEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "action_id").write[Int] and
        (JsPath \ "workflow_id").write[Int] and
        (JsPath \ "transaction_id").write[String] and
        (JsPath \ "step_id").write[Int] and
        (JsPath \ "is_reverted").write[Boolean] and
        (JsPath \ "is_finished").write[Boolean]
    )(unlift(ActionTransactionEntity.unapply))

  implicit def convertFromModel(
      model: ActionTransactions): ActionTransactionEntity = {
    ActionTransactionEntity(
      model.id,
      model.actionId,
      model.workflowId,
      model.transactionId,
      model.stepId,
      model.isReverted
    )
  }

  implicit def convertFromModels(
      models: List[ActionTransactions]): List[ActionTransactionEntity] =
    models.map(convertFromModel)
}
