/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import CommentUpdate from './comment-update.vue';
import CommentService from './comment.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import PostService from '@/entities/post/post.service';

type CommentUpdateComponentType = InstanceType<typeof CommentUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const commentSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CommentUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Comment Management Update Component', () => {
    let comp: CommentUpdateComponentType;
    let commentServiceStub: SinonStubbedInstance<CommentService>;

    beforeEach(() => {
      route = {};
      commentServiceStub = sinon.createStubInstance<CommentService>(CommentService);
      commentServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          commentService: () => commentServiceStub,
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
        const wrapper = shallowMount(CommentUpdate, { global: mountOptions });
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
        const wrapper = shallowMount(CommentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.comment = commentSample;
        commentServiceStub.update.resolves(commentSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commentServiceStub.update.calledWith(commentSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        commentServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CommentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.comment = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        commentServiceStub.find.resolves(commentSample);
        commentServiceStub.retrieve.resolves([commentSample]);

        // WHEN
        route = {
          params: {
            commentId: '' + commentSample.id,
          },
        };
        const wrapper = shallowMount(CommentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.comment).toMatchObject(commentSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        commentServiceStub.find.resolves(commentSample);
        const wrapper = shallowMount(CommentUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
