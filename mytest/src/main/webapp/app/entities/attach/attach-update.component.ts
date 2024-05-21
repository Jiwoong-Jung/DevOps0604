import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AttachService from './attach.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import AttachGroupService from '@/entities/attach-group/attach-group.service';
import { type IAttachGroup } from '@/shared/model/attach-group.model';
import { type IAttach, Attach } from '@/shared/model/attach.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AttachUpdate',
  setup() {
    const attachService = inject('attachService', () => new AttachService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const attach: Ref<IAttach> = ref(new Attach());

    const attachGroupService = inject('attachGroupService', () => new AttachGroupService());

    const attachGroups: Ref<IAttachGroup[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAttach = async attachId => {
      try {
        const res = await attachService().find(attachId);
        res.createdAt = new Date(res.createdAt);
        res.modifiedAt = new Date(res.modifiedAt);
        attach.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.attachId) {
      retrieveAttach(route.params.attachId);
    }

    const initRelationships = () => {
      attachGroupService()
        .retrieve()
        .then(res => {
          attachGroups.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      ord: {},
      name: {},
      origName: {},
      ext: {},
      contentType: {},
      path: {},
      fileSize: {},
      createdAt: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      createdBy: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      modifiedAt: {},
      modifiedBy: {},
      attachGroup: {},
    };
    const v$ = useVuelidate(validationRules, attach as any);
    v$.value.$validate();

    return {
      attachService,
      alertService,
      attach,
      previousState,
      isSaving,
      currentLanguage,
      attachGroups,
      v$,
      ...useDateFormat({ entityRef: attach }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.attach.id) {
        this.attachService()
          .update(this.attach)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mytestApp.attach.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.attachService()
          .create(this.attach)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mytestApp.attach.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
