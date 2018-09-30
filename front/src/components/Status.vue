<template>
  <div>
    <workflow-header></workflow-header>
    <div class="card border-light mb-4 card-status-list">
      <div class="card-body ">
        <table class="table table-hover">
          <thead>
          <tr align="left">
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Edit</th>
            <th scope="col">Destroy</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(row, index) in statuses" v-bind:key="row.id">
            <td align="left">{{ row.id}}</td>
            <td align="left">{{ row.name }}</td>
            <td>
              <button class="btn-primary" v-on:click="edit(index)">Edit</button>
            </td>
            <td>
              <button class="btn-danger" v-on:click="destroy(index)">Destroy</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppFooter from './utils/AppFooter'
import WorkflowHeader from './workflow/WorkflowHeader'

export default {
  name: 'Status',
  data () {
    return {
      statuses: []
    }
  },
  components: { WorkflowHeader, AppFooter },
  methods: {
    edit: function (index) {},
    destroy: function (index) {}
  },
  created: function () {
    let targetPath = '/api/workflow-statuses'

    const self = this
    ApiClient.search(targetPath, (response) => {
      self.logger.info(response.data)
      self.statuses = response.data
    }, (error) => {
      self.logger.error(error)
    })
  }
}
</script>

<style>
.card-status-list {
  width: 60%;
  margin-top: 5px;
  margin-left: 20%;
  border: transparent 1px solid;
}
</style>
