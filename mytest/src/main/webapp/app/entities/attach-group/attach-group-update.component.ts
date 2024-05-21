import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import AttachGroupService from './attach-group.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PostService from '@/entities/post/post.service';
import { type IPost } from '@/shared/model/post.model';
import { type IAttachGroup, AttachGroup } from '@/shared/model/attach-group.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'AttachGroupUpdate',
  setup() {
    const attachGroupService = inject('attachGroupService', () => new AttachGroupService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const attachGroup: Ref<IAttachGroup> = ref(new AttachGroup());

    const postService = inject('postService', () => new PostService());

    const posts: Ref<IPost[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'ko'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveAttachGroup = async attachGroupId => {
      try {
        const res = await attachGroupService().find(attachGroupId);
        res.createdAt = new Date(res.createdAt);
        attachGroup.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.attachGroupId) {
      retrieveAttachGroup(route.params.attachGroupId);
    }

    const initRelationships = () => {
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
      createdAt: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      createdBy: {
        required: validations.required(t$('entity.validation.required').toString()),
        integer: validations.integer(t$('entity.validation.number').toString()),
      },
      post: {},
    };
    const v$ = useVuelidate(validationRules, attachGroup as any);
    v$.value.$validate();

    return {
      attachGroupService,
      alertService,
      attachGroup,
      previousState,
      isSaving,
      currentLanguage,
      posts,
      v$,
      ...useDateFormat({ entityRef: attachGroup }),
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.attachGroup.id) {
        this.attachGroupService()
          .update(this.attachGroup)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('mytestApp.attachGroup.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.attachGroupService()
          .create(this.attachGroup)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('mytestApp.attachGroup.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
