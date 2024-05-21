<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mytestApp.attach.home.createOrEditLabel"
          data-cy="AttachCreateUpdateHeading"
          v-text="t$('mytestApp.attach.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="attach.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="attach.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.ord')" for="attach-ord"></label>
            <input
              type="number"
              class="form-control"
              name="ord"
              id="attach-ord"
              data-cy="ord"
              :class="{ valid: !v$.ord.$invalid, invalid: v$.ord.$invalid }"
              v-model.number="v$.ord.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.name')" for="attach-name"></label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="attach-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.origName')" for="attach-origName"></label>
            <input
              type="text"
              class="form-control"
              name="origName"
              id="attach-origName"
              data-cy="origName"
              :class="{ valid: !v$.origName.$invalid, invalid: v$.origName.$invalid }"
              v-model="v$.origName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.ext')" for="attach-ext"></label>
            <input
              type="text"
              class="form-control"
              name="ext"
              id="attach-ext"
              data-cy="ext"
              :class="{ valid: !v$.ext.$invalid, invalid: v$.ext.$invalid }"
              v-model="v$.ext.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.contentType')" for="attach-contentType"></label>
            <input
              type="text"
              class="form-control"
              name="contentType"
              id="attach-contentType"
              data-cy="contentType"
              :class="{ valid: !v$.contentType.$invalid, invalid: v$.contentType.$invalid }"
              v-model="v$.contentType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.path')" for="attach-path"></label>
            <input
              type="text"
              class="form-control"
              name="path"
              id="attach-path"
              data-cy="path"
              :class="{ valid: !v$.path.$invalid, invalid: v$.path.$invalid }"
              v-model="v$.path.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.fileSize')" for="attach-fileSize"></label>
            <input
              type="number"
              class="form-control"
              name="fileSize"
              id="attach-fileSize"
              data-cy="fileSize"
              :class="{ valid: !v$.fileSize.$invalid, invalid: v$.fileSize.$invalid }"
              v-model.number="v$.fileSize.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.createdAt')" for="attach-createdAt"></label>
            <div class="d-flex">
              <input
                id="attach-createdAt"
                data-cy="createdAt"
                type="datetime-local"
                class="form-control"
                name="createdAt"
                :class="{ valid: !v$.createdAt.$invalid, invalid: v$.createdAt.$invalid }"
                required
                :value="convertDateTimeFromServer(v$.createdAt.$model)"
                @change="updateZonedDateTimeField('createdAt', $event)"
              />
            </div>
            <div v-if="v$.createdAt.$anyDirty && v$.createdAt.$invalid">
              <small class="form-text text-danger" v-for="error of v$.createdAt.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.createdBy')" for="attach-createdBy"></label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="attach-createdBy"
              data-cy="createdBy"
              :class="{ valid: !v$.createdBy.$invalid, invalid: v$.createdBy.$invalid }"
              v-model.number="v$.createdBy.$model"
              required
            />
            <div v-if="v$.createdBy.$anyDirty && v$.createdBy.$invalid">
              <small class="form-text text-danger" v-for="error of v$.createdBy.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.modifiedAt')" for="attach-modifiedAt"></label>
            <div class="d-flex">
              <input
                id="attach-modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                class="form-control"
                name="modifiedAt"
                :class="{ valid: !v$.modifiedAt.$invalid, invalid: v$.modifiedAt.$invalid }"
                :value="convertDateTimeFromServer(v$.modifiedAt.$model)"
                @change="updateZonedDateTimeField('modifiedAt', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.modifiedBy')" for="attach-modifiedBy"></label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="attach-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !v$.modifiedBy.$invalid, invalid: v$.modifiedBy.$invalid }"
              v-model.number="v$.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attach.attachGroup')" for="attach-attachGroup"></label>
            <select class="form-control" id="attach-attachGroup" data-cy="attachGroup" name="attachGroup" v-model="attach.attachGroup">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="attach.attachGroup && attachGroupOption.id === attach.attachGroup.id ? attach.attachGroup : attachGroupOption"
                v-for="attachGroupOption in attachGroups"
                :key="attachGroupOption.id"
              >
                {{ attachGroupOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./attach-update.component.ts"></script>
