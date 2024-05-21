<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mytestApp.board.home.createOrEditLabel"
          data-cy="BoardCreateUpdateHeading"
          v-text="t$('mytestApp.board.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="board.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="board.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.board.title')" for="board-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="board-title"
              data-cy="title"
              :class="{ valid: !v$.title.$invalid, invalid: v$.title.$invalid }"
              v-model="v$.title.$model"
              required
            />
            <div v-if="v$.title.$anyDirty && v$.title.$invalid">
              <small class="form-text text-danger" v-for="error of v$.title.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.board.category')" for="board-category"></label>
            <input
              type="text"
              class="form-control"
              name="category"
              id="board-category"
              data-cy="category"
              :class="{ valid: !v$.category.$invalid, invalid: v$.category.$invalid }"
              v-model="v$.category.$model"
              required
            />
            <div v-if="v$.category.$anyDirty && v$.category.$invalid">
              <small class="form-text text-danger" v-for="error of v$.category.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.board.createdAt')" for="board-createdAt"></label>
            <div class="d-flex">
              <input
                id="board-createdAt"
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
            <label class="form-control-label" v-text="t$('mytestApp.board.createdBy')" for="board-createdBy"></label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="board-createdBy"
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
            <label class="form-control-label" v-text="t$('mytestApp.board.modifiedAt')" for="board-modifiedAt"></label>
            <div class="d-flex">
              <input
                id="board-modifiedAt"
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
            <label class="form-control-label" v-text="t$('mytestApp.board.modifiedBy')" for="board-modifiedBy"></label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="board-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !v$.modifiedBy.$invalid, invalid: v$.modifiedBy.$invalid }"
              v-model.number="v$.modifiedBy.$model"
            />
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
<script lang="ts" src="./board-update.component.ts"></script>
