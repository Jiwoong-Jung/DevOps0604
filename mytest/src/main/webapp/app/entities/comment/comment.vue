<template>
  <div>
    <h2 id="page-heading" data-cy="CommentHeading">
      <span v-text="t$('mytestApp.comment.home.title')" id="comment-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('mytestApp.comment.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CommentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-comment"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('mytestApp.comment.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && comments && comments.length === 0">
      <span v-text="t$('mytestApp.comment.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="comments && comments.length > 0">
      <table class="table table-striped" aria-describedby="comments">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('depth')">
              <span v-text="t$('mytestApp.comment.depth')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'depth'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('comment')">
              <span v-text="t$('mytestApp.comment.comment')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'comment'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('readCnt')">
              <span v-text="t$('mytestApp.comment.readCnt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'readCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('goodCnt')">
              <span v-text="t$('mytestApp.comment.goodCnt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goodCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('badCnt')">
              <span v-text="t$('mytestApp.comment.badCnt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'badCnt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="t$('mytestApp.comment.createdAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="t$('mytestApp.comment.createdBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="t$('mytestApp.comment.modifiedAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="t$('mytestApp.comment.modifiedBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('post.id')">
              <span v-text="t$('mytestApp.comment.post')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'post.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('parent.id')">
              <span v-text="t$('mytestApp.comment.parent')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parent.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="comment in comments" :key="comment.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CommentView', params: { commentId: comment.id } }">{{ comment.id }}</router-link>
            </td>
            <td>{{ comment.depth }}</td>
            <td>{{ comment.comment }}</td>
            <td>{{ comment.readCnt }}</td>
            <td>{{ comment.goodCnt }}</td>
            <td>{{ comment.badCnt }}</td>
            <td>{{ formatDateShort(comment.createdAt) || '' }}</td>
            <td>{{ comment.createdBy }}</td>
            <td>{{ formatDateShort(comment.modifiedAt) || '' }}</td>
            <td>{{ comment.modifiedBy }}</td>
            <td>
              <div v-if="comment.post">
                <router-link :to="{ name: 'PostView', params: { postId: comment.post.id } }">{{ comment.post.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="comment.parent">
                <router-link :to="{ name: 'CommentView', params: { commentId: comment.parent.id } }">{{ comment.parent.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CommentView', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CommentEdit', params: { commentId: comment.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(comment)"
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
        <span id="mytestApp.comment.delete.question" data-cy="commentDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-comment-heading" v-text="t$('mytestApp.comment.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-comment"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeComment()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="comments && comments.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./comment.component.ts"></script>
