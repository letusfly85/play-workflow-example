<script>
import ApiClient from '../utils/ApiClient'

export default {
  name: 'WorkflowService',
  list: function (callback, errorHandler) {
    let targetPath = '/api/workflows/'
    ApiClient.search(targetPath, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  find: function (id, callback, errorHandler) {
    let targetPath = '/api/workflows/'
    ApiClient.find(targetPath, id, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  },
  create: function (id, params, callback, errorHandler) {
    let targetPath = `/api/workflows/${id}`
    ApiClient.create(targetPath, params, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  }
}
</script>
