<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="mytestApp.post.home.createOrEditLabel"
          data-cy="PostCreateUpdateHeading"
          v-text="t$('mytestApp.post.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="post.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="post.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.post.status')" for="post-status"></label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="post-status"
              data-cy="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
              required
            />
            <div v-if="v$.status.$anyDirty && v$.status.$invalid">
              <small class="form-text text-danger" v-for="error of v$.status.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.post.title')" for="post-title"></label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="post-title"
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
            <label class="form-control-label" v-text="t$('mytestApp.post.contents')" for="post-contents"></label>
            <div>
              <div v-if="post.contents" class="form-text text-danger clearfix">
                <a class="pull-left" v-on:click="openFile(post.contentsContentType, post.contents)" v-text="t$('entity.action.open')"></a
                ><br />
                <span class="pull-left">{{ post.contentsContentType }}, {{ byteSize(post.contents) }}</span>
                <button
                  type="button"
                  v-on:click="
                    post.contents = null;
                    post.contentsContentType = null;
                  "
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <label for="file_contents" v-text="t$('entity.action.addblob')" class="btn btn-primary pull-right"></label>
              <input
                type="file"
                ref="file_contents"
                id="file_contents"
                style="display: none"
                data-cy="contents"
                v-on:change="setFileData($event, post, 'contents', false)"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="contents"
              id="post-contents"
              data-cy="contents"
              :class="{ valid: !v$.contents.$invalid, invalid: v$.contents.$invalid }"
              v-model="v$.contents.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="contentsContentType"
              id="post-contentsContentType"
              v-model="post.contentsContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.post.readCnt')" for="post-readCnt"></label>
            <input
              type="number"
              class="form-control"
              name="readCnt"
              id="post-readCnt"
              data-cy="readCnt"
              :class="{ valid: !v$.readCnt.$invalid, invalid: v$.readCnt.$invalid }"
              v-model.number="v$.readCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.post.goodCnt')" for="post-goodCnt"></label>
            <input
              type="number"
              class="form-control"
              name="goodCnt"
              id="post-goodCnt"
              data-cy="goodCnt"
              :class="{ valid: !v$.goodCnt.$invalid, invalid: v$.goodCnt.$invalid }"
              v-model.number="v$.goodCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.post.badCnt')" for="post-badCnt"></label>
            <input
              type="number"
              class="form-control"
              name="badCnt"
              id="post-badCnt"
              data-cy="badCnt"
              :class="{ valid: !v$.badCnt.$invalid, invalid: v$.badCnt.$invalid }"
              v-model.number="v$.badCnt.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.post.createdAt')" for="post-createdAt"></label>
            <div class="d-flex">
              <input
                id="post-createdAt"
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
            <label class="form-control-label" v-text="t$('mytestApp.post.createdBy')" for="post-createdBy"></label>
            <input
              type="number"
              class="form-control"
              name="createdBy"
              id="post-createdBy"
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
            <label class="form-control-label" v-text="t$('mytestApp.post.modifiedAt')" for="post-modifiedAt"></label>
            <div class="d-flex">
              <input
                id="post-modifiedAt"
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
            <label class="form-control-label" v-text="t$('mytestApp.post.modifiedBy')" for="post-modifiedBy"></label>
            <input
              type="number"
              class="form-control"
              name="modifiedBy"
              id="post-modifiedBy"
              data-cy="modifiedBy"
              :class="{ valid: !v$.modifiedBy.$invalid, invalid: v$.modifiedBy.$invalid }"
              v-model.number="v$.modifiedBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('mytestApp.post.board')" for="post-board"></label>
            <select class="form-control" id="post-board" data-cy="board" name="board" v-model="post.board">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="post.board && boardOption.id === post.board.id ? post.board : boardOption"
                v-for="boardOption in boards"
                :key="boardOption.id"
              >
                {{ boardOption.id }}
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
<script lang="ts" src="./post-update.component.ts"></script>
