<template>
  <div>
    <b-dropdown-item to="/board">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.board')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/post">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.post')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/attach-group">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.attachGroup')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/attach">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.attach')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/comment">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.comment')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
