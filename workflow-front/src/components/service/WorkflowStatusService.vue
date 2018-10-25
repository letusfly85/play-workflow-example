<script>
import ApiClient from '../utils/ApiClient'

export default {
  name: 'WorkflowStatusService',
  list: function (callback, errorHandler) {
    let targetPath = '/api/workflow-statuses/'
    ApiClient.search(targetPath, (response) => {
      callback(response)
    }, (error) => {
      errorHandler(error)
    })
  }
}
</script>
