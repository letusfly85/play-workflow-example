<template>
  <div>
    <app-header></app-header>
    <b-card class="card-workflow-list">
      <div v-for="transition in transitions" v-bind:key="transition.id">
        <div style="margin-bottom: 3px; height: 5rem;">
          <b-card style="float: left; width: 14%; font-size: 12px; height: 100%; margin-right: 2px;">{{ transition.from_step.step_label }}</b-card>
          <b-card style="float: left; height: 100%; margin-right: 2px; border: transparent 1px solid;"> ---> </b-card>
          <b-card style="float: left; width: 14%; font-size: 12px; height: 100%; margin-right: 10px;">{{ transition.to_step.step_label }}</b-card>
          <b-card style="width: 55%; height: 100%;">{{ transition.name }}</b-card>
        </div>
      </div>
    </b-card>
    <br/><br/>
    <button v-on:click="toggleChange(addToggle)" class="btn-outline-success">
      {{ addToggle ? 'Cancel' : ''}} Add Transition
    </button>
    <br/>
    <br/>
    <b-form @submit="createTransition">
      <div v-if="addToggle" class="form-add-transition">
        <b-form-select v-model="form.from_step" :options="step_id_list" class="mb-3"></b-form-select>
        <b-form-select v-model="form.to_step" :options="step_id_list" class="mb-3"></b-form-select>
        <b-form-input value="" v-model="form.transition_name" class="form-control"></b-form-input>
        <b-button type="submit" class="btn-success">Save Transition</b-button>
      </div>
    </b-form>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppHeader from './utils/AppHeader'
import AppFooter from './utils/AppFooter'
import AppConst from './utils/AppConst'
import * as d3 from 'd3v4'

export default {
  name: 'Transition',
  data () {
    return {
      transitions: [],
      workflows: [],
      addToggle: false,
      form: {
        from_step: null,
        to_step: null,
        transition_name: null
      },
      step_id_list: [],
      transition_nodes: {
        nodes: [
          { id: 'sample1', group: 1 },
          { id: 'sample2', group: 2 }
        ],
        'links': [
          { source: 'edge1', target: 'sample1', value: 2 },
          { source: 'edge2', target: 'sample2', value: 3 }
        ]
      }
    }
  },
  components: { AppHeader, AppFooter, AppConst },
  methods: {
    toggleChange: function (toggle) {
      if (toggle === true) {
        this.addToggle = false
      } else {
        this.addToggle = true
      }
    },
    createTransition: function () {
      let param = {
        workflow_id: AppConst.data().orderExampleWorkflowId,
        name: this.form.transition_name,
        from_step: this.form.from_step,
        to_step: this.form.to_step
      }
      console.log(param)

      const self = this
      let targetPath = '/api/workflow/transitions'
      ApiClient.create(targetPath, param, (response) => {
        console.log(response)
        self.transitions.push(param)
      }, (error) => {
        console.log(error)
      })
    }
  },
  created: function () {
    var targetPath = '/api/workflow/transitions?workflow-id=' + AppConst.data().orderExampleWorkflowId

    const self = this
    ApiClient.search(targetPath, (response) => {
      console.log(response)
      self.transitions = response.data
    }, (error) => {
      console.log(error)
    })

    targetPath = '/api/workflow/definitions?workflow-id=' + AppConst.data().orderExampleWorkflowId
    ApiClient.search(targetPath, (response) => {
      console.log(response)
      self.workflows = response.data

      self.step_id_list = self.workflows.map(function (record) {
        record.value = {
          step_id: record.step_id,
          step_label: record.step_label,
          is_first_step: record.is_first_step,
          is_last_step: record.is_last_step }
        record.text = record.step_label

        return record
      })
    }, (error) => {
      console.log(error)
    })
  }
}
</script>

<style scoped>
  .card-workflow-list {
    width: 80%;
    margin-top: 5px;
    margin-left: 10%;
    border: transparent 1px solid;
  }

  .form-add-transition {
    width: 70%;
    margin-left: 15%;
    border: transparent 1px solid;
  }

  .links line {
    stroke: #999;
    stroke-opacity: 0.6;
  }

  .nodes circle {
    stroke: #fff;
    stroke-width: 1.5px;
  }

  text {
    font-family: sans-serif;
    font-size: 10px;
  }
</style>
