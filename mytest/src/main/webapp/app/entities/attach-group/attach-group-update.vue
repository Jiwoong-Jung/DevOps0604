<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mytestApp.attachGroup.home.createOrEditLabel"
          data-cy="AttachGroupCreateUpdateHeading"
          v-text="t$('mytestApp.attachGroup.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="attachGroup.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="attachGroup.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.attachGroup.createdAt')" for="attach-group-createdAt"></label>
            <div class="d-flex">
              <input
                id="attach-group-createdAt"
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
            <label class="form-control-label" v-text="t$('mytestApp.attachGroup.createdBy')" for="attach-group-createdBy"></label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="attach-group-createdBy"
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
            <label class="form-control-label" v-text="t$('mytestApp.attachGroup.post')" for="attach-group-post"></label>
            <select class="form-control" id="attach-group-post" data-cy="post" name="post" v-model="attachGroup.post">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="attachGroup.post && postOption.id === attachGroup.post.id ? attachGroup.post : postOption"
                v-for="postOption in posts"
                :key="postOption.id"
              >
                {{ postOption.id }}
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
<script lang="ts" src="./attach-group-update.component.ts"></script>
