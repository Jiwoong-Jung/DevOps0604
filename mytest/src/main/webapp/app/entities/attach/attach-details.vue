<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="attach">
        <h2 class="jh-entity-heading" data-cy="attachDetailsHeading">
          <span v-text="t$('mytestApp.attach.detail.title')"></span> {{ attach.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('mytestApp.attach.ord')"></span>
          </dt>
          <dd>
            <span>{{ attach.ord }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.name')"></span>
          </dt>
          <dd>
            <span>{{ attach.name }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.origName')"></span>
          </dt>
          <dd>
            <span>{{ attach.origName }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.ext')"></span>
          </dt>
          <dd>
            <span>{{ attach.ext }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.contentType')"></span>
          </dt>
          <dd>
            <span>{{ attach.contentType }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.path')"></span>
          </dt>
          <dd>
            <span>{{ attach.path }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.fileSize')"></span>
          </dt>
          <dd>
            <span>{{ attach.fileSize }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.createdAt')"></span>
          </dt>
          <dd>
            <span v-if="attach.createdAt">{{ formatDateLong(attach.createdAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.createdBy')"></span>
          </dt>
          <dd>
            <span>{{ attach.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.modifiedAt')"></span>
          </dt>
          <dd>
            <span v-if="attach.modifiedAt">{{ formatDateLong(attach.modifiedAt) }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.modifiedBy')"></span>
          </dt>
          <dd>
            <span>{{ attach.modifiedBy }}</span>
          </dd>
          <dt>
            <span v-text="t$('mytestApp.attach.attachGroup')"></span>
          </dt>
          <dd>
            <div v-if="attach.attachGroup">
              <router-link :to="{ name: 'AttachGroupView', params: { attachGroupId: attach.attachGroup.id } }">{{
                attach.attachGroup.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="attach.id" :to="{ name: 'AttachEdit', params: { attachId: attach.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./attach-details.component.ts"></script>
