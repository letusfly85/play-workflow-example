<script>
var data = {
  workflowId: 2
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
