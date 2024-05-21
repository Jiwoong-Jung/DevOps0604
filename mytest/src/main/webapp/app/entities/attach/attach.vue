<template>
  <div>
    <h2 id="page-heading" data-cy="AttachHeading">
      <span v-text="t$('mytestApp.attach.home.title')" id="attach-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('mytestApp.attach.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'AttachCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-attach"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('mytestApp.attach.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && attaches && attaches.length === 0">
      <span v-text="t$('mytestApp.attach.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="attaches && attaches.length > 0">
      <table class="table table-striped" aria-describedby="attaches">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ord')">
              <span v-text="t$('mytestApp.attach.ord')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ord'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="t$('mytestApp.attach.name')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('origName')">
              <span v-text="t$('mytestApp.attach.origName')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'origName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ext')">
              <span v-text="t$('mytestApp.attach.ext')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ext'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contentType')">
              <span v-text="t$('mytestApp.attach.contentType')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contentType'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('path')">
              <span v-text="t$('mytestApp.attach.path')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'path'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fileSize')">
              <span v-text="t$('mytestApp.attach.fileSize')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fileSize'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdAt')">
              <span v-text="t$('mytestApp.attach.createdAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="t$('mytestApp.attach.createdBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedAt')">
              <span v-text="t$('mytestApp.attach.modifiedAt')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedAt'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('modifiedBy')">
              <span v-text="t$('mytestApp.attach.modifiedBy')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'modifiedBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('attachGroup.id')">
              <span v-text="t$('mytestApp.attach.attachGroup')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'attachGroup.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="attach in attaches" :key="attach.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AttachView', params: { attachId: attach.id } }">{{ attach.id }}</router-link>
            </td>
            <td>{{ attach.ord }}</td>
            <td>{{ attach.name }}</td>
            <td>{{ attach.origName }}</td>
            <td>{{ attach.ext }}</td>
            <td>{{ attach.contentType }}</td>
            <td>{{ attach.path }}</td>
            <td>{{ attach.fileSize }}</td>
            <td>{{ formatDateShort(attach.createdAt) || '' }}</td>
            <td>{{ attach.createdBy }}</td>
            <td>{{ formatDateShort(attach.modifiedAt) || '' }}</td>
            <td>{{ attach.modifiedBy }}</td>
            <td>
              <div v-if="attach.attachGroup">
                <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attach.attachGroup.id } }">{{
                  attach.attachGroup.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AttachView', params: { attachId: attach.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AttachEdit', params: { attachId: attach.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(attach)"
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
        <span id="mytestApp.attach.delete.question" data-cy="attachDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-attach-heading" v-text="t$('mytestApp.attach.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-attach"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeAttach()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="attaches && attaches.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attach.component.ts"></script>
