import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AttachService from './attach.service';
import { useDateFormat } from '@/shared/composables';
import { type IAttach } from '@/shared/model/attach.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AttachDetails',
  setup() {
    const dateFormat = useDateFormat();
    const attachService = inject('attachService', () => new AttachService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const attach: Ref<IAttach> = ref({});

    const retrieveAttach = async attachId => {
      try {
        const res = await attachService().find(attachId);
        attach.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.attachId) {
      retrieveAttach(route.params.attachId);
    }

    return {
      ...dateFormat,
      alertService,
      attach,

      previousState,
      t$: useI18n().t,
    };
  },
});
