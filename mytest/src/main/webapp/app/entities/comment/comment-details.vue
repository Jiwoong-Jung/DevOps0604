<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="comment">
        <h2 class="jh-entity-heading" data-cy="commentDetailsHeading">
          <span v-text="t$('mytestApp.comment.detail.title')"></span> {{ comment.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mytestApp.comment.depth')"></span>
          </dt>
          <dd>
            <span>{{ comment.depth }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.comment')"></span>
          </dt>
          <dd>
            <span>{{ comment.comment }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.readCnt')"></span>
          </dt>
          <dd>
            <span>{{ comment.readCnt }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.goodCnt')"></span>
          </dt>
          <dd>
            <span>{{ comment.goodCnt }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.badCnt')"></span>
          </dt>
          <dd>
            <span>{{ comment.badCnt }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.createdAt')"></span>
          </dt>
          <dd>
            <span v-if="comment.createdAt">{{ formatDateLong(comment.createdAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.createdBy')"></span>
          </dt>
          <dd>
            <span>{{ comment.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.modifiedAt')"></span>
          </dt>
          <dd>
            <span v-if="comment.modifiedAt">{{ formatDateLong(comment.modifiedAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.modifiedBy')"></span>
          </dt>
          <dd>
            <span>{{ comment.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.post')"></span>
          </dt>
          <dd>
            <div v-if="comment.post">
              <router-link :to="{ name: 'PostView', params: { postId: comment.post.id } }">{{ comment.post.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.comment.parent')"></span>
          </dt>
          <dd>
            <div v-if="comment.parent">
              <router-link :to="{ name: 'CommentView', params: { commentId: comment.parent.id } }">{{ comment.parent.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="comment.id" :to="{ name: 'CommentEdit', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./comment-details.component.ts"></script>
