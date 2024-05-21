import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PostService from './post.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import BoardService from '@/entities/board/board.service';
import { type IBoard } from '@/shared/model/board.model';
import { type IPost, Post } from '@/shared/model/post.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PostUpdate',
  setup() {
    const postService = inject('postService', () => new PostService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const post: Ref<IPost> = ref(new Post());

    const boardService = inject('boardService', () => new BoardService());

    const boards: Ref<IBoard[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePost = async postId => {
      try {
        const res = await postService().find(postId);
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        post.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.postId) {
      retrievePost(route.params.postId);
    }

    const initRelationships = () => {
      boardService()
        .retrieve()
        .then(res => {
          boards.value = res.data;
        });
    };

    initRelationships();

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      status: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      title: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      contents: {},
      readCnt: {},
      goodCnt: {},
      badCnt: {},
      createdAt: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      createdBy: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      modifiedAt: {},
      modifiedBy: {},
      board: {},
    };
    const v$ = useVuelidate(validationRules, post as any);
    v$.value.$validate();

    return {
      postService,
      alertService,
      post,
      previousState,
      isSaving,
      currentLanguage,
      boards,
      ...dataUtils,
      v$,
      ...useDateFormat({ entityRef: post }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.post.id) {
        this.postService()
          .update(this.post)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mytestApp.post.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.postService()
          .create(this.post)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mytestApp.post.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
