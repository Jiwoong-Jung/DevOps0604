<template>
  <div>
    <h2 id="page-heading" data-cy="AttachGroupHeading">
      <span v-text="t$('mytestApp.attachGroup.home.title')" id="attach-group-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('mytestApp.attachGroup.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'AttachGroupCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-attach-group"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('mytestApp.attachGroup.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && attachGroups && attachGroups.length === 0">
      <span v-text="t$('mytestApp.attachGroup.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="attachGroups && attachGroups.length > 0">
      <table class="table table-striped" aria-describedby="attachGroups">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="t$('mytestApp.attachGroup.createdAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="t$('mytestApp.attachGroup.createdBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('post.id')">
              <span v-text="t$('mytestApp.attachGroup.post')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'post.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="attachGroup in attachGroups" :key="attachGroup.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attachGroup.id } }">{{ attachGroup.id }}</router-link>
            </td>
            <td>{{ formatDateShort(attachGroup.createdAt) || '' }}</td>
            <td>{{ attachGroup.createdBy }}</td>
            <td>
              <div v-if="attachGroup.post">
                <router-link :to="{ name: 'PostView', params: { postId: attachGroup.post.id } }">{{ attachGroup.post.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attachGroup.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AttachGroupEdit', params: { attachGroupId: attachGroup.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(attachGroup)"
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
        <span id="mytestApp.attachGroup.delete.question" data-cy="attachGroupDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-attachGroup-heading" v-text="t$('mytestApp.attachGroup.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-attachGroup"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeAttachGroup()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="attachGroups && attachGroups.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attach-group.component.ts"></script>
