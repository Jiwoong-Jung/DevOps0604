/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AttachGroupDetails from './attach-group-details.vue';
import AttachGroupService from './attach-group.service';
import AlertService from '@/shared/alert/alert.service';

type AttachGroupDetailsComponentType = InstanceType<typeof AttachGroupDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const attachGroupSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('AttachGroup Management Detail Component', () => {
    let attachGroupServiceStub: SinonStubbedInstance<AttachGroupService>;
    let mountOptions: MountingOptions<AttachGroupDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      attachGroupServiceStub = sinon.createStubInstance<AttachGroupService>(AttachGroupService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          attachGroupService: () => attachGroupServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        attachGroupServiceStub.find.resolves(attachGroupSample);
        route = {
          params: {
            attachGroupId: '' + 123,
          },
        };
        const wrapper = shallowMount(AttachGroupDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.attachGroup).toMatchObject(attachGroupSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        attachGroupServiceStub.find.resolves(attachGroupSample);
        const wrapper = shallowMount(AttachGroupDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
