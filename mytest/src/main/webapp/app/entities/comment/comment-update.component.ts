import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CommentService from './comment.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PostService from '@/entities/post/post.service';
import { type IPost } from '@/shared/model/post.model';
import { type IComment, Comment } from '@/shared/model/comment.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CommentUpdate',
  setup() {
    const commentService = inject('commentService', () => new CommentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const comment: Ref<IComment> = ref(new Comment());

    const comments: Ref<IComment[]> = ref([]);

    const postService = inject('postService', () => new PostService());

    const posts: Ref<IPost[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveComment = async commentId => {
      try {
        const res = await commentService().find(commentId);
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        comment.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.commentId) {
      retrieveComment(route.params.commentId);
    }

    const initRelationships = () => {
      commentService()
        .retrieve()
        .then(res => {
          comments.value = res.data;
        });
      postService()
        .retrieve()
        .then(res => {
          posts.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      depth: {},
      comment: {},
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
      post: {},
      parent: {},
    };
    const v$ = useVuelidate(validationRules, comment as any);
    v$.value.$validate();

    return {
      commentService,
      alertService,
      comment,
      previousState,
      isSaving,
      currentLanguage,
      comments,
      posts,
      v$,
      ...useDateFormat({ entityRef: comment }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.comment.id) {
        this.commentService()
          .update(this.comment)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mytestApp.comment.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.commentService()
          .create(this.comment)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mytestApp.comment.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
