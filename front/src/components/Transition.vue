<template>
  <div>
    <app-header></app-header>
    <svg id="graph"  :width="svgArea.width" :height="svgArea.height" style="border: 1px">
    </svg>
    <b-card class="card-workflow-list">
      <div v-for="(transition, index) in transitions" v-bind:key="transition.id">
        <div style="margin-bottom: 3px; height: 5rem;">
          <b-card style="float: left; width:  14%;  font-size:   12px; height: 100%; margin-right: 2px;">{{ transition.from_step.step_label }}</b-card>
          <b-card style="float: left; height: 100%; margin-right: 2px; border: transparent 1px solid;"> ---> </b-card>
          <b-card style="float: left; width:  14%;  font-size:   12px; height: 100%; margin-right: 10px;">{{ transition.to_step.step_label }}</b-card>
          <b-card style="float: left; width:  40%;  height: 100%;">{{ transition.name }}</b-card>
          <b-btn variant="outline-info" size="sm" style="margin-top: 2rem; border-radius: 25px;" @click="addCondition(transition, index)" > Conditionを編集する </b-btn>
          <b-modal :ref="'transitionRef'+transition.id" title="Conditionを追加する" size="lg">
            <div class="modal-content">
              <div v-for="condition in transition.conditions" v-bind:key="condition.name">
                {{ index.toString() + '_' + condition.action_id.toString() }}
                <b-form-checkbox :id="index.toString() + '_' + condition.action_id.toString()"
                                 v-model="condition.checked" value=true unchecked-value=false>
                  {{ condition.name }}
                </b-form-checkbox>
              </div>
              <b-btn @click="saveCondition(transition, index)">コンディションを保存する</b-btn>
            </div>
          </b-modal>
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
// import AppConst from './utils/AppConst'
import * as d3 from 'd3v4'

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
  components: { AppHeader, AppFooter },
  methods: {
    searchConditions: function (tran, index) {
      let self = this
      let targetPath = '/api/workflow/craft-conditions/' + this.$store.state.workflowId + '/' + tran.id
      ApiClient.search(targetPath, (response) => {
        console.log(response)
        self.transitions[index].conditions = response.data.map(function (record) {
          record.checked = false
          return record
        })
      }, (error) => {
        console.log(error)
      })
    },
    addCondition: function (tran, index) {
      let modal = this.$refs['transitionRef' + tran.id][0]
      /*
      if (this.$store.state.workflowId === AppConst.data().craftExampleWorkflowId) {
        modal.$emit('reloadModal', this.transitions[index].conditions)
      }
      */
      modal.show()
    },
    saveCondition: function (tran, index) {
      // todo implement post action condition record
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
    let targetPath = '/api/workflow/transitions?workflow-id=' + this.$store.state.workflowId

    const self = this
    ApiClient.search(targetPath, (response) => {
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

    targetPath = '/api/workflow/definitions?workflow-id=' + this.$store.state.workflowId
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
