<template>
  <div>
    <workflow-header></workflow-header>
    <svg id="graph" :width="svgArea.width" :height="svgArea.height" style="border: 1px">
    </svg>
    <div class="card border-light mb-4 card-workflow-list">
      <div class="card-body ">
        <div v-for="(transition, index) in transitions" v-bind:key="transition.id">
          <div style="margin-bottom: 3px; height: 5rem;">
            <div class="card border-light mb-4" style="float: left;">
              <div class="card-body ">
                {{ transition.from_step.step_label }}
              </div>
            </div>
            <div class="card border-light mb-4" style="float: left;">
              <div class="card-body ">
                ----->
              </div>
            </div>
            <div class="card border-light mb-4" style="float: left;">
              <div class="card-body ">
                {{ transition.to_step.step_label }}
              </div>
            </div>
            <div class="card border-light mb-4" style="float: left;">
              <div class="card-body ">
                {{ transition.name }}
              </div>
            </div>
            <button class="btn-sm btn-outline-info col-2" data-toggle="modal" :data-target="'#'+'transitionRef'+transition.id">コンディションを編集する</button>
            <div class="modal" :id="'transitionRef'+transition.id" >
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title">Conditionを追加する</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                  </div>
                  <div class="modal-body">
                    <div v-for="condition in transition.conditions" v-bind:key="condition.name">
                      {{ index.toString() + '_' + condition.action_id.toString() }}
                      {{ condition.name }}
                      <input type="checkbox" :id="index.toString() + '_' + condition.action_id.toString()" v-model="condition.is_activate">
                    </div>
                    <button v-on:click="saveCondition(transition, index)" class="btn-sm btn-info col-6">コンディションを保存する</button>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-primary">Save changes</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <br/>
        </div>
      </div>
    </div>
    <br/><br/>
    <button v-on:click="toggleChange(addToggle)" class="btn-outline-success">
      {{ addToggle ? 'Cancel' : ''}} Add Transition
    </button>
    <br/>
    <br/>
      <div v-if="addToggle" class="form-add-transition">
        <input type="select" v-model="form.from_step" :options="step_id_list" class="mb-3">
        <input type="select" v-model="form.to_step" :options="step_id_list" class="mb-3">
        <input type="text" value="" v-model="form.transition_name" class="form-control">
        <button class="btn-primary" v-on:click="createTransition">Save Transition</button>
      </div>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppFooter from './utils/AppFooter'
// https://bl.ocks.org/heybignick/3faf257bbbbc7743bb72310d03b86ee8
import * as d3 from 'd3v4'
import WorkflowHeader from './workflow/WorkflowHeader'
import WorkflowTransisionService from './service/WorkflowTransitionService'

export default {
  name: 'Transition',
  data () {
    return {
      svgArea: {
        width: 960,
        height: 200
      },
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
          { id: 'sample1', group: 1, fx: 255, fy: 100 },
          { id: 'sample2', group: 3, fx: 455, fy: 100 }
        ],
        links: [
          { source: 'sample1', target: 'sample2', value: 256 }
        ]
      }
    }
  },
  components: { WorkflowHeader, AppFooter },
  methods: {
    searchConditions: function (tran, index) {
      let self = this
      let targetPath = '/api/workflow/craft-conditions/' + this.$store.state.workflowId + '/' + tran.id
      ApiClient.search(targetPath, (response) => {
        self.transitions[index].conditions = response.data.map(function (record) {
          return record
        })
      }, (error) => {
        console.log(error)
      })
    },
    addCondition: function (tran, index) {
      let modal = this.$refs['transitionRef' + tran.id][0]
      console.log(modal)
      modal.show()
    },
    saveCondition: function (tran, index) {
      let targetPath = '/api/workflow/craft-conditions/' + this.$store.state.workflowId + '/' + tran.id
      let params = tran.conditions.map(function (data) {
        data.is_activate = Boolean(data.is_activate)
        return data
      })
      console.log(params)
      ApiClient.create(targetPath, params, (response) => {
        console.log(response)
      }, (error) => {
        console.log(error)
      })
      console.log(tran)
    },
    toggleChange: function (toggle) {
      if (toggle) this.addToggle = false
      else this.addToggle = true
    },
    createTransition: function () {
      let param = {
        workflow_id: this.$store.state.workflowId,
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
    },
    ticked: function (d, link, node) {
      link
        .attr('x1', function (d) { return d.source.x })
        .attr('y1', function (d) { return d.source.y })
        .attr('x2', function (d) { return d.target.x })
        .attr('y2', function (d) { return d.target.y })

      node
        .attr('transform', function (d) {
          return 'translate(' + d.x + ',' + d.y + ')'
        })
    },
    dragstarted: function (d, simulation) {
      if (!d3.event.active) simulation.alphaTarget(0.3).restart()
      d.fx = d.x
      d.fy = d.y
    },
    dragged: function (d, simulation) {
      d.fx = d3.event.x
      d.fy = d3.event.y
    },
    dragended: function (d, simulation) {
      if (!d3.event.active) simulation.alphaTarget(0)
      d.fx = d3.event.x
      d.fy = d3.event.y
    }
  },
  created: function () {
    const self = this
    WorkflowTransisionService.list(this.$store.state.workflowId, (response) => {
      console.log(response)
      self.transitions = response.data.map(function (data) {
        data.conditions = []
        return data
      })
      self.transitions.forEach(function (data, index) {
        self.searchConditions(data, index)
        console.log(self.transitions[index])
      })
    }, (error) => {
      console.log(error)
    })

    let targetPath = '/api/workflow/definitions?workflow-id=' + this.$store.state.workflowId
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
  },
  mounted: function () {
    const svg = d3.selectAll('#graph')
    let color = d3.scaleOrdinal(d3.schemeCategory20)
    let simulation = d3.forceSimulation()
      .force('link', d3.forceLink().id(function (d) {
        return d.id
      }))
      .force('charge', d3.forceManyBody())
      .force('center', d3.forceCenter(this.svgArea.width / 2, this.svgArea.height / 2))

    let graphData = this.transition_nodes
    let link = svg.append('g')
      .attr('class', 'links')
      .selectAll('line')
      .data(graphData.links)
      .enter().append('line')
      .attr('stroke', 'lightblue')
      .attr('stroke-width', 3)
      .attr('opacity', 0.3)

    let node = svg.append('g')
      .attr('class', 'nodes')
      .selectAll('g')
      .data(graphData.nodes)
      .enter().append('g')

    let self = this
    node.append('circle')
      .attr('r', 25)
      .attr('fill', function (d) { return color(d.group) })
      .call(d3.drag()
        .on('start', function (d) { return self.dragstarted(d, simulation) })
        .on('drag', function (d) { return self.dragged(d, simulation) })
        .on('end', function (d) { return self.dragended(d, simulation) })
      )

    node.append('text')
      .text(function (d) {
        return d.id
      })
      .attr('x', 12)
      .attr('y', 0)
    node.append('title')
      .text(function (d) { return d.id })

    simulation
      .nodes(graphData.nodes)
      .on('tick', function (d) { return self.ticked(d, link, node) })

    simulation.force('link')
      .links(graphData.links)
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
</style>
