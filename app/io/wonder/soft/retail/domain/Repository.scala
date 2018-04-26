package io.wonder.soft.retail.domain

trait Repository[E <: Entity] {

  def find(id: Int): Option[E]

  def create(entity: E): Either[Exception, E]

  def destroy(id: Int): Option[E]

  def update(entity: E): Either[Exception, E]

}
