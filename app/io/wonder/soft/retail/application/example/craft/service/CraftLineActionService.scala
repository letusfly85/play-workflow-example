package io.wonder.soft.retail.application.example.craft.service

import io.wonder.soft.retail.application.ApplicationService
import io.wonder.soft.retail.domain.example.craft.query.{CraftLineActionQueryProcessor, CraftLineQueryProcessor}
import javax.inject.Inject

class CraftLineActionService @Inject()
  (actionQuery: CraftLineActionQueryProcessor
  ) extends ApplicationService {


  def listActions = actionQuery.searchByServiceId

}
