import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import BoardService from './board.service';
import { useDateFormat } from '@/shared/composables';
import { type IBoard } from '@/shared/model/board.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'BoardDetails',
  setup() {
    const dateFormat = useDateFormat();
    const boardService = inject('boardService', () => new BoardService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const board: Ref<IBoard> = ref({});

    const retrieveBoard = async boardId => {
      try {
        const res = await boardService().find(boardId);
        board.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.boardId) {
      retrieveBoard(route.params.boardId);
    }

    return {
      ...dateFormat,
      alertService,
      board,

      previousState,
      t$: useI18n().t,
    };
  },
});
