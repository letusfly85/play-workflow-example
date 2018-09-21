<script>
import ApiClient from '../utils/ApiClient'

export default {
  name: 'WorkflowTransitionService',
  list: function (workflowId, callback, errorHandler) {
    let targetPath = `/api/workflows/${workflowId}/transitions`
    ApiClient.search(targetPath, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  find: function (id, workflowId, callback, errorHandler) {
    let targetPath = `/api/workflows/${workflowId}/transitions/${id}`
    ApiClient.find(targetPath, id, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  create: function (workflowId, params, callback, errorHandler) {
    let targetPath = `/api/workflows/${workflowId}/transitions`
    ApiClient.create(targetPath, params, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  destroy: function (id, workflowId, params, callback, errorHandler) {
    let targetPath = `/api/workflows/${workflowId}/transitions/${id}`
    ApiClient.delete(targetPath, params, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  }
}
</script>
