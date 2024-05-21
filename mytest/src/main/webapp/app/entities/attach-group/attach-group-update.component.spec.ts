/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import AttachGroupUpdate from './attach-group-update.vue';
import AttachGroupService from './attach-group.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import PostService from '@/entities/post/post.service';

type AttachGroupUpdateComponentType = InstanceType<typeof AttachGroupUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const attachGroupSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AttachGroupUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('AttachGroup Management Update Component', () => {
    let comp: AttachGroupUpdateComponentType;
    let attachGroupServiceStub: SinonStubbedInstance<AttachGroupService>;

    beforeEach(() => {
      route = {};
      attachGroupServiceStub = sinon.createStubInstance<AttachGroupService>(AttachGroupService);
      attachGroupServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          attachGroupService: () => attachGroupServiceStub,
          postService: () =>
            sinon.createStubInstance<PostService>(PostService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(AttachGroupUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(AttachGroupUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.attachGroup = attachGroupSample;
        attachGroupServiceStub.update.resolves(attachGroupSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachGroupServiceStub.update.calledWith(attachGroupSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        attachGroupServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AttachGroupUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.attachGroup = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachGroupServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        attachGroupServiceStub.find.resolves(attachGroupSample);
        attachGroupServiceStub.retrieve.resolves([attachGroupSample]);

        // WHEN
        route = {
          params: {
            attachGroupId: '' + attachGroupSample.id,
          },
        };
        const wrapper = shallowMount(AttachGroupUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.attachGroup).toMatchObject(attachGroupSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        attachGroupServiceStub.find.resolves(attachGroupSample);
        const wrapper = shallowMount(AttachGroupUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
