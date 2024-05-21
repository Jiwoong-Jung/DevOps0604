/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import AttachDetails from './attach-details.vue';
import AttachService from './attach.service';
import AlertService from '@/shared/alert/alert.service';

type AttachDetailsComponentType = InstanceType<typeof AttachDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const attachSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Attach Management Detail Component', () => {
    let attachServiceStub: SinonStubbedInstance<AttachService>;
    let mountOptions: MountingOptions<AttachDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      attachServiceStub = sinon.createStubInstance<AttachService>(AttachService);

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
          attachService: () => attachServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        attachServiceStub.find.resolves(attachSample);
        route = {
          params: {
            attachId: '' + 123,
          },
        };
        const wrapper = shallowMount(AttachDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.attach).toMatchObject(attachSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        attachServiceStub.find.resolves(attachSample);
        const wrapper = shallowMount(AttachDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
