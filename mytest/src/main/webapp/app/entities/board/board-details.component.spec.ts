/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import BoardDetails from './board-details.vue';
import BoardService from './board.service';
import AlertService from '@/shared/alert/alert.service';

type BoardDetailsComponentType = InstanceType<typeof BoardDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const boardSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Board Management Detail Component', () => {
    let boardServiceStub: SinonStubbedInstance<BoardService>;
    let mountOptions: MountingOptions<BoardDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      boardServiceStub = sinon.createStubInstance<BoardService>(BoardService);

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
          boardService: () => boardServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        boardServiceStub.find.resolves(boardSample);
        route = {
          params: {
            boardId: '' + 123,
          },
        };
        const wrapper = shallowMount(BoardDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.board).toMatchObject(boardSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        boardServiceStub.find.resolves(boardSample);
        const wrapper = shallowMount(BoardDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
