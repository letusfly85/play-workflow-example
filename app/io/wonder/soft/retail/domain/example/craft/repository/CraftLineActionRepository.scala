package io.wonder.soft.retail.domain.example.craft.repository

import io.wonder.soft.retail.domain.example.craft.entity.CraftLineActionEntity

trait CraftLineActionRepository {

  def find(id: Int): Option[CraftLineActionEntity]

  def create(entity: CraftLineActionEntity): Either[Exception, CraftLineActionEntity]

  def update(entity: CraftLineActionEntity): Either[RuntimeException, CraftLineActionEntity]

  def destroy(id: Int): Option[CraftLineActionEntity]
}

