<template>
  <div id="add-toggle-button">
    <button v-on:click="toggleChange(toggleValue)" class="btn btn-info">
      {{ childToggle ? 'Cancel' : ''}} Create new {{ buttonTitle }}
    </button>
  </div>
</template>

<script>
export default {
  name: 'AddToggleButton',
  props: [ 'buttonTitle', 'toggleValue' ],
  data () {
    return {
      childToggle: false
    }
  },
  methods: {
    toggleChange: function (toggle) {
      if (toggle) this.childToggle = false
      else this.childToggle = true
      this.$emit('child-event', this.childToggle)
    }
  }
}
</script>

<style scoped>

</style>
