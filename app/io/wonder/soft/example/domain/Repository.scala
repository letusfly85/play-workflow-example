package io.wonder.soft.example.domain

trait Repository {

  def find(id: Int): Option[Entity]

  def create(entity: Entity): Either[Exception, Entity]

  def destroy(id: Int): Option[Entity]

  def update(entity: Entity): Either[Exception, Entity]

}
