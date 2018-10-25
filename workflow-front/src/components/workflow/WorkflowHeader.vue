<template>
  <div id="workflow-header">
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      <a class="navbar-brand" href="#">Workflow Admin</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarColor01">
        <ul class="navbar-nav mr-auto">
          <li :class="navItemClass.workflow">
            <a class="nav-link" href="#/workflows">workflows</a>
          </li>
          <li :class="navItemClass.status">
            <a class="nav-link" href="#/workflow-statuses">statuses</a>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <input class="form-control mr-sm-2" type="text" placeholder="Search">
          <button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
        </form>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'WorkflowHeader',
  data () {
    return {
      navItemClass: {
        workflow: 'nav-link',
        status: 'nav-link'
      }
    }
  },
  created: function () {
    if (this.$route.path.startsWith('/workflows')) {
      this.navItemClass.workflow = 'nav-item active'
      this.navItemClass.status = 'nav-item'
    } else if (this.$route.path === '/workflow-statuses') {
      this.navItemClass.workflow = 'nav-item'
      this.navItemClass.status = 'nav-item active'
    } else {
      this.navItemClass.workflow = 'nav-item'
      this.navItemClass.status = 'nav-item'
    }
  }
}
</script>
