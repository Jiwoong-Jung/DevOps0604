<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mytestApp.comment.home.createOrEditLabel"
          data-cy="CommentCreateUpdateHeading"
          v-text="t$('mytestApp.comment.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="comment.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="comment.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.depth')" for="comment-depth"></label>
            <input
              type="number"
              class="form-control"
              name="depth"
              id="comment-depth"
              data-cy="depth"
              :class="{ valid: !v$.depth.$invalid, invalid: v$.depth.$invalid }"
              v-model.number="v$.depth.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.comment')" for="comment-comment"></label>
            <input
              type="text"
              class="form-control"
              name="comment"
              id="comment-comment"
              data-cy="comment"
              :class="{ valid: !v$.comment.$invalid, invalid: v$.comment.$invalid }"
              v-model="v$.comment.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.readCnt')" for="comment-readCnt"></label>
            <input
              type="number"
              class="form-control"
              name="readCnt"
              id="comment-readCnt"
              data-cy="readCnt"
              :class="{ valid: !v$.readCnt.$invalid, invalid: v$.readCnt.$invalid }"
              v-model.number="v$.readCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.goodCnt')" for="comment-goodCnt"></label>
            <input
              type="number"
              class="form-control"
              name="goodCnt"
              id="comment-goodCnt"
              data-cy="goodCnt"
              :class="{ valid: !v$.goodCnt.$invalid, invalid: v$.goodCnt.$invalid }"
              v-model.number="v$.goodCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.badCnt')" for="comment-badCnt"></label>
            <input
              type="number"
              class="form-control"
              name="badCnt"
              id="comment-badCnt"
              data-cy="badCnt"
              :class="{ valid: !v$.badCnt.$invalid, invalid: v$.badCnt.$invalid }"
              v-model.number="v$.badCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.createdAt')" for="comment-createdAt"></label>
            <div class="d-flex">
              <input
                id="comment-createdAt"
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
            <label class="form-control-label" v-text="t$('mytestApp.comment.createdBy')" for="comment-createdBy"></label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="comment-createdBy"
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
            <label class="form-control-label" v-text="t$('mytestApp.comment.modifiedAt')" for="comment-modifiedAt"></label>
            <div class="d-flex">
              <input
                id="comment-modifiedAt"
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
            <label class="form-control-label" v-text="t$('mytestApp.comment.modifiedBy')" for="comment-modifiedBy"></label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="comment-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !v$.modifiedBy.$invalid, invalid: v$.modifiedBy.$invalid }"
              v-model.number="v$.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.post')" for="comment-post"></label>
            <select class="form-control" id="comment-post" data-cy="post" name="post" v-model="comment.post">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="comment.post && postOption.id === comment.post.id ? comment.post : postOption"
                v-for="postOption in posts"
                :key="postOption.id"
              >
                {{ postOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.comment.parent')" for="comment-parent"></label>
            <select class="form-control" id="comment-parent" data-cy="parent" name="parent" v-model="comment.parent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="comment.parent && commentOption.id === comment.parent.id ? comment.parent : commentOption"
                v-for="commentOption in comments"
                :key="commentOption.id"
              >
                {{ commentOption.id }}
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
<script lang="ts" src="./comment-update.component.ts"></script>
