/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import AttachUpdate from './attach-update.vue';
import AttachService from './attach.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import AttachGroupService from '@/entities/attach-group/attach-group.service';

type AttachUpdateComponentType = InstanceType<typeof AttachUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const attachSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<AttachUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Attach Management Update Component', () => {
    let comp: AttachUpdateComponentType;
    let attachServiceStub: SinonStubbedInstance<AttachService>;

    beforeEach(() => {
      route = {};
      attachServiceStub = sinon.createStubInstance<AttachService>(AttachService);
      attachServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          attachService: () => attachServiceStub,
          attachGroupService: () =>
            sinon.createStubInstance<AttachGroupService>(AttachGroupService, {
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
        const wrapper = shallowMount(AttachUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(AttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.attach = attachSample;
        attachServiceStub.update.resolves(attachSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachServiceStub.update.calledWith(attachSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        attachServiceStub.create.resolves(entity);
        const wrapper = shallowMount(AttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.attach = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(attachServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        attachServiceStub.find.resolves(attachSample);
        attachServiceStub.retrieve.resolves([attachSample]);

        // WHEN
        route = {
          params: {
            attachId: '' + attachSample.id,
          },
        };
        const wrapper = shallowMount(AttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.attach).toMatchObject(attachSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        attachServiceStub.find.resolves(attachSample);
        const wrapper = shallowMount(AttachUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
