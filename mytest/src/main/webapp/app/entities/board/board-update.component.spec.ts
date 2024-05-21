/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import BoardUpdate from './board-update.vue';
import BoardService from './board.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type BoardUpdateComponentType = InstanceType<typeof BoardUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const boardSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<BoardUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Board Management Update Component', () => {
    let comp: BoardUpdateComponentType;
    let boardServiceStub: SinonStubbedInstance<BoardService>;

    beforeEach(() => {
      route = {};
      boardServiceStub = sinon.createStubInstance<BoardService>(BoardService);
      boardServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          boardService: () => boardServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(BoardUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(BoardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.board = boardSample;
        boardServiceStub.update.resolves(boardSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(boardServiceStub.update.calledWith(boardSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        boardServiceStub.create.resolves(entity);
        const wrapper = shallowMount(BoardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.board = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(boardServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        boardServiceStub.find.resolves(boardSample);
        boardServiceStub.retrieve.resolves([boardSample]);

        // WHEN
        route = {
          params: {
            boardId: '' + boardSample.id,
          },
        };
        const wrapper = shallowMount(BoardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.board).toMatchObject(boardSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        boardServiceStub.find.resolves(boardSample);
        const wrapper = shallowMount(BoardUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
