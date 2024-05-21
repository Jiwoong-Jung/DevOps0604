<template>
  <div>
    <h2 v-if="authenticated"><span v-text="t$('sessions.title')"></span></h2>

    <div class="alert alert-success" v-if="success" v-html="t$('sessions.messages.success')"></div>
    <div class="alert alert-danger" v-if="error" v-html="t$('sessions.messages.error')"></div>

    <div class="table-responsive">
      <table class="table table-striped">
        <thead>
          <tr>
            <th v-text="t$('sessions.table.ipaddress')"></th>
            <th v-text="t$('sessions.table.useragent')"></th>
            <th v-text="t$('sessions.table.date')"></th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="session in sessions" :key="session.ipAddress">
            <td>{{ session.ipAddress }}</td>
            <td>{{ session.userAgent }}</td>
            <td>{{ session.tokenDate }}</td>
            <td>
              <button
                type="submit"
                class="btn btn-primary"
                v-on:click="invalidate(session.series)"
                v-text="t$('sessions.table.button')"
              ></button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts" src="./sessions.component.ts"></script>
