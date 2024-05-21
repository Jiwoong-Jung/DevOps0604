import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import AttachGroupService from './attach-group.service';
import { useDateFormat } from '@/shared/composables';
import { type IAttachGroup } from '@/shared/model/attach-group.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AttachGroupDetails',
  setup() {
    const dateFormat = useDateFormat();
    const attachGroupService = inject('attachGroupService', () => new AttachGroupService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const attachGroup: Ref<IAttachGroup> = ref({});

    const retrieveAttachGroup = async attachGroupId => {
      try {
        const res = await attachGroupService().find(attachGroupId);
        attachGroup.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.attachGroupId) {
      retrieveAttachGroup(route.params.attachGroupId);
    }

    return {
      ...dateFormat,
      alertService,
      attachGroup,

      previousState,
      t$: useI18n().t,
    };
  },
});
