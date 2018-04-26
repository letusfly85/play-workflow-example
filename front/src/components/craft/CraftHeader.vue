<template>
  <div id="order-header">
    <b-navbar toggleable="md" class="navbar-order">
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
      <b-navbar-brand href="#">Craft</b-navbar-brand>
      <b-collapse is-nav id="nav_collapse">

        <b-navbar-nav>
          <b-nav-item href="#/craft-lines">Orders</b-nav-item>
          <b-nav-item href="#/workflows">Workflows</b-nav-item>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">

          <b-nav-item-dropdown right>
            <!-- Using button-content slot -->
            <template slot="button-content">
              <em>User</em>
            </template>
            <b-dropdown-item href="#">Profile</b-dropdown-item>
            <b-dropdown-item href="#/password/change">Password Change</b-dropdown-item>
            <b-dropdown-item-button>Sign Out</b-dropdown-item-button>
          </b-nav-item-dropdown>

        </b-navbar-nav>

      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: 'CraftHeader',
  created: function () {
  }
}
</script>

<style>
  .navbar-order {
    background-color: mistyrose;
    opacity: 1.0;
    color: black;
    z-index: 10;
  }
</style>
