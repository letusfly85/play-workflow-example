<template>
  <div id="app-header">
  <b-navbar toggleable="md" class="navbar-workflow">
    <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>
    <b-navbar-brand href="#">Workflows</b-navbar-brand>
    <b-collapse is-nav id="nav_collapse">

      <b-navbar-nav>
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
          <b-dropdown-item-button v-on:click="signOut">Sign Out</b-dropdown-item-button>
        </b-nav-item-dropdown>

      </b-navbar-nav>

    </b-collapse>
  </b-navbar>
  </div>
</template>

<script>
export default {
  name: 'AppHeader',
  created: function () {
    console.log('init')
  }
}
</script>

<style>
  .navbar-workflow {
    background-color: beige;
    opacity: 1.0;
    color: black;
    z-index: 10;
  }
</style>
