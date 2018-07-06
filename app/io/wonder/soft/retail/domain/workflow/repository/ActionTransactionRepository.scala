package io.wonder.soft.retail.domain.workflow.repository

import io.wonder.soft.retail.domain.workflow.entity.ActionTransactionEntity

trait ActionTransactionRepository {

  def find(id: Int): Option[ActionTransactionEntity]

  def create(entity: ActionTransactionEntity): Either[Exception, ActionTransactionEntity]

  def update(entity: ActionTransactionEntity): Either[RuntimeException, ActionTransactionEntity]

  def destroy(id: Int): Option[ActionTransactionEntity]

}
