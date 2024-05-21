<template>
  <div>
    <h2 id="page-heading" data-cy="PostHeading">
      <span v-text="t$('mytestApp.post.home.title')" id="post-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('mytestApp.post.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PostCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-post">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('mytestApp.post.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && posts && posts.length === 0">
      <span v-text="t$('mytestApp.post.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="posts && posts.length > 0">
      <table class="table table-striped" aria-describedby="posts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="t$('mytestApp.post.status')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="t$('mytestApp.post.title')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contents')">
              <span v-text="t$('mytestApp.post.contents')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contents'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readCnt')">
              <span v-text="t$('mytestApp.post.readCnt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('goodCnt')">
              <span v-text="t$('mytestApp.post.goodCnt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goodCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('badCnt')">
              <span v-text="t$('mytestApp.post.badCnt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'badCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="t$('mytestApp.post.createdAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="t$('mytestApp.post.createdBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="t$('mytestApp.post.modifiedAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="t$('mytestApp.post.modifiedBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('board.id')">
              <span v-text="t$('mytestApp.post.board')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'board.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in posts" :key="post.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PostView', params: { postId: post.id } }">{{ post.id }}</router-link>
            </td>
            <td>{{ post.status }}</td>
            <td>{{ post.title }}</td>
            <td>
              <a v-if="post.contents" v-on:click="openFile(post.contentsContentType, post.contents)" v-text="t$('entity.action.open')"></a>
              <span v-if="post.contents">{{ post.contentsContentType }}, {{ byteSize(post.contents) }}</span>
            </td>
            <td>{{ post.readCnt }}</td>
            <td>{{ post.goodCnt }}</td>
            <td>{{ post.badCnt }}</td>
            <td>{{ formatDateShort(post.createdAt) || '' }}</td>
            <td>{{ post.createdBy }}</td>
            <td>{{ formatDateShort(post.modifiedAt) || '' }}</td>
            <td>{{ post.modifiedBy }}</td>
            <td>
              <div v-if="post.board">
                <router-link :to="{ name: 'BoardView', params: { boardId: post.board.id } }">{{ post.board.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PostView', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PostEdit', params: { postId: post.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(post)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="mytestApp.post.delete.question" data-cy="postDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-post-heading" v-text="t$('mytestApp.post.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-post"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removePost()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="posts && posts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./post.component.ts"></script>
