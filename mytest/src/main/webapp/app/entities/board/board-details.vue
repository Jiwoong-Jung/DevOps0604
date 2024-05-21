<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="board">
        <h2 class="jh-entity-heading" data-cy="boardDetailsHeading">
          <span v-text="t$('mytestApp.board.detail.title')"></span> {{ board.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mytestApp.board.title')"></span>
          </dt>
          <dd>
            <span>{{ board.title }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.board.category')"></span>
          </dt>
          <dd>
            <span>{{ board.category }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.board.createdAt')"></span>
          </dt>
          <dd>
            <span v-if="board.createdAt">{{ formatDateLong(board.createdAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.board.createdBy')"></span>
          </dt>
          <dd>
            <span>{{ board.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.board.modifiedAt')"></span>
          </dt>
          <dd>
            <span v-if="board.modifiedAt">{{ formatDateLong(board.modifiedAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.board.modifiedBy')"></span>
          </dt>
          <dd>
            <span>{{ board.modifiedBy }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="board.id" :to="{ name: 'BoardEdit', params: { boardId: board.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./board-details.component.ts"></script>
