<template>
  <div>
    <app-header></app-header>
    <app-footer></app-footer>
  </div>
</template>

<script>
import ApiClient from './utils/ApiClient'
import AppHeader from './utils/AppHeader'
import AppFooter from './utils/AppFooter'

export default {
  name: 'Workflow',
  components: { AppHeader, AppFooter },
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  created: function () {
    let targetPath = '/api/workflow/definitions?workflow-id=1'

    ApiClient.search(targetPath, (response) => {
      console.log(response)
    }, (error) => {
      console.log(error)
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
