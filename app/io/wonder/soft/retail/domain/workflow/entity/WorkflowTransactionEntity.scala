package io.wonder.soft.retail.domain.workflow.entity

import io.wonder.soft.retail.domain.Entity
import io.wonder.soft.retail.domain.workflow.model.WorkflowTransactions
import io.wonder.soft.retail.domain.Entity
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowTransactionEntity (
  id: Int,
  workflowId: Int,
  transactionId: String,
  userId: Option[String],
  stepId: Int,
  fromTransitionId: Option[Int],
  isInit: Boolean,
  isCompleted: Boolean
) extends Entity

object WorkflowTransactionEntity {
  implicit def workflowTransactionsReads: Reads[WorkflowTransactionEntity] = (
    (JsPath \ "id").read[Int] and
    (JsPath \ "workflow_id").read[Int] and
    (JsPath \ "transaction_id").read[String] and
    (JsPath \ "user_id").readNullable[String] and
    (JsPath \ "step_id").read[Int] and
    (JsPath \ "from_transition_id").readNullable[Int] and
    (JsPath \ "is_init").read[Boolean] and
    (JsPath \ "is_completed").read[Boolean]
  )(WorkflowTransactionEntity.apply _)

  implicit def workflowTransactionsWrites: Writes[WorkflowTransactionEntity] = (
    (JsPath \ "id").write[Int] and
    (JsPath \ "workflow_id").write[Int] and
    (JsPath \ "transaction_id").write[String] and
    (JsPath \ "user_id").writeNullable[String] and
    (JsPath \ "step_id").write[Int] and
    (JsPath \ "from_transition_id").writeNullable[Int] and
    (JsPath \ "is_init").write[Boolean] and
    (JsPath \ "is_completed").write[Boolean]
  )(unlift(WorkflowTransactionEntity.unapply))

  implicit def convertFromModel(model: WorkflowTransactions): WorkflowTransactionEntity = {
    WorkflowTransactionEntity (
      model.id,
      model.workflowId,
      model.transactionId,
      None,
      model.stepId,
      None,
      model.isInit,
      model.isCompleted
    )
  }

  implicit def convertFromModels(models: List[WorkflowTransactions]): List[WorkflowTransactionEntity] =
    models.map(convertFromModel)
}
