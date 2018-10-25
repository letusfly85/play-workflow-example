<template>
  <div>
    <workflow-header></workflow-header>
    <svg id="graph" :width="svgArea.width" :height="svgArea.height" style="border: 1px">
    </svg>
    <div class="card border-light mb-4 card-workflow-list">
      <div class="card-body ">
        <div v-for="transition in transitions" v-bind:key="transition.id">
          <div style="margin-bottom: 3px; height: 5rem;">
            <div class="card border-light col-2" style="float: left;">
              <div class="card-body ">
                {{ transition.from_step.step_label }}
              </div>
            </div>
            <div class="card border-light col-2" style="float: left;">
              <div class="card-body ">
                -->
              </div>
            </div>
            <div class="card border-light col-2" style="float: left;">
              <div class="card-body ">
                {{ transition.to_step.step_label }}
              </div>
            </div>
            <div class="card border-light col-6">
              <div class="card-body ">
                {{ transition.name }}
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
      <select v-model="form.from_step">
        <option v-for="option in step_id_list" v-bind:key="option.id" v-bind:value="option.value">
          {{ option.text }}
        </option>
      </select>
      <select  v-model="form.to_step">
        <option v-for="option in step_id_list" v-bind:key="option.id" v-bind:value="option.value">
          {{ option.text }}
        </option>
      </select>
      <input type="text" value="" v-model="form.transition_name" class="form-control">
      <button class="btn-primary" v-on:click="createTransition">Save Transition</button>
    </div>
    <app-footer></app-footer>
  </div>
</template>

<script>
import AppFooter from './utils/AppFooter'
// https://bl.ocks.org/heybignick/3faf257bbbbc7743bb72310d03b86ee8
import * as d3 from 'd3v4'
import WorkflowHeader from './workflow/WorkflowHeader'
import WorkflowTransitionService from './service/WorkflowTransitionService'
import WorkflowService from './service/WorkflowService'

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
    toggleChange: function (toggle) {
      if (toggle) this.addToggle = false
      else this.addToggle = true
    },
    createTransition: function () {
      let workflowId = Number(this.$store.state.workflowId)
      let param = {
        id: 0,
        workflow_id: workflowId,
        name: this.form.transition_name,
        from_step: this.form.from_step,
        to_step: this.form.to_step
      }
      console.log(param)

      const self = this
      WorkflowTransitionService.create(workflowId, param, (response) => {
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
    WorkflowTransitionService.list(this.$store.state.workflowId, (response) => {
      console.log(response)
      self.transitions = response.data.map(function (data) {
        data.conditions = []
        return data
      })
    }, (error) => {
      console.log(error)
    })

    WorkflowService.find(this.$store.state.workflowId, (response) => {
      console.log(response)
      self.workflows = response.data
      self.step_id_list = self.workflows.details.map(function (record) {
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
