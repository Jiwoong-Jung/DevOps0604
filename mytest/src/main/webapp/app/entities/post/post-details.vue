<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="post">
        <h2 class="jh-entity-heading" data-cy="postDetailsHeading">
          <span v-text="t$('mytestApp.post.detail.title')"></span> {{ post.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mytestApp.post.status')"></span>
          </dt>
          <dd>
            <span>{{ post.status }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.title')"></span>
          </dt>
          <dd>
            <span>{{ post.title }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.contents')"></span>
          </dt>
          <dd>
            <div v-if="post.contents">
              <a v-on:click="openFile(post.contentsContentType, post.contents)" v-text="t$('entity.action.open')"></a>
              {{ post.contentsContentType }}, {{ byteSize(post.contents) }}
            </div>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.readCnt')"></span>
          </dt>
          <dd>
            <span>{{ post.readCnt }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.goodCnt')"></span>
          </dt>
          <dd>
            <span>{{ post.goodCnt }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.badCnt')"></span>
          </dt>
          <dd>
            <span>{{ post.badCnt }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.createdAt')"></span>
          </dt>
          <dd>
            <span v-if="post.createdAt">{{ formatDateLong(post.createdAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.createdBy')"></span>
          </dt>
          <dd>
            <span>{{ post.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.modifiedAt')"></span>
          </dt>
          <dd>
            <span v-if="post.modifiedAt">{{ formatDateLong(post.modifiedAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.modifiedBy')"></span>
          </dt>
          <dd>
            <span>{{ post.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.post.board')"></span>
          </dt>
          <dd>
            <div v-if="post.board">
              <router-link :to="{ name: 'BoardView', params: { boardId: post.board.id } }">{{ post.board.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="post.id" :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post-details.component.ts"></script>
