<script>
var data = {
  orderExampleWorkflowId: 2,
  craftExampleWorkflowId: 3
}
export default {
  name: 'AppConst.vue',
  data: function () {
    return data
  },
  created: function () {
    console.log(this.data)
  }
}
</script>
