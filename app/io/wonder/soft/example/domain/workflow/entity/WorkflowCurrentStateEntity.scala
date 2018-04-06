package entity

import io.wonder.soft.example.domain.Entity
import io.wonder.soft.example.domain.workflow.model.WorkflowCurrentStates
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

final case class WorkflowCurrentStateEntity(
    id: Int,
    workflowId: Int,
    transactionId: String,
    userId: Option[String],
    currentStepId: Int,
    schemeId: Int,
    serviceId: Int,
) extends Entity

object WorkflowCurrentStateEntity {
  implicit def workflowCurrentStatesReads: Reads[WorkflowCurrentStateEntity] =
    (
      (JsPath \ "id").read[Int] and
        (JsPath \ "workflow_id").read[Int] and
        (JsPath \ "transaction_id").read[String] and
        (JsPath \ "user_id").readNullable[String] and
        (JsPath \ "current_step_id").read[Int] and
        (JsPath \ "scheme_id").read[Int] and
        (JsPath \ "service_id").read[Int]
    )(WorkflowCurrentStateEntity.apply _)

  implicit def workflowCurrentStatesWrites: Writes[WorkflowCurrentStateEntity] =
    (
      (JsPath \ "id").write[Int] and
        (JsPath \ "workflow_id").write[Int] and
        (JsPath \ "transaction_id").write[String] and
        (JsPath \ "user_id").writeNullable[String] and
        (JsPath \ "current_step_id").write[Int] and
        (JsPath \ "scheme_id").write[Int] and
        (JsPath \ "service_id").write[Int]
    )(unlift(WorkflowCurrentStateEntity.unapply))

  implicit def convertFromModel(
      model: WorkflowCurrentStates): WorkflowCurrentStateEntity = {
    WorkflowCurrentStateEntity(
      model.id,
      model.workflowId,
      model.transactionId,
      None,
      model.currentStepId,
      model.schemeId,
      model.serviceId,
    )
  }

  implicit def convertFromModels(
      models: List[WorkflowCurrentStates]): List[WorkflowCurrentStateEntity] =
    models.map(convertFromModel)
}
