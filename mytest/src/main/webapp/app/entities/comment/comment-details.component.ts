import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CommentService from './comment.service';
import { useDateFormat } from '@/shared/composables';
import { type IComment } from '@/shared/model/comment.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CommentDetails',
  setup() {
    const dateFormat = useDateFormat();
    const commentService = inject('commentService', () => new CommentService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const comment: Ref<IComment> = ref({});

    const retrieveComment = async commentId => {
      try {
        const res = await commentService().find(commentId);
        comment.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.commentId) {
      retrieveComment(route.params.commentId);
    }

    return {
      ...dateFormat,
      alertService,
      comment,

      previousState,
      t$: useI18n().t,
    };
  },
});
