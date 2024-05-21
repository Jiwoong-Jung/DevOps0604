/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import AttachGroup from './attach-group.vue';
import AttachGroupService from './attach-group.service';
import AlertService from '@/shared/alert/alert.service';

type AttachGroupComponentType = InstanceType<typeof AttachGroup>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('AttachGroup Management Component', () => {
    let attachGroupServiceStub: SinonStubbedInstance<AttachGroupService>;
    let mountOptions: MountingOptions<AttachGroupComponentType>['global'];

    beforeEach(() => {
      attachGroupServiceStub = sinon.createStubInstance<AttachGroupService>(AttachGroupService);
      attachGroupServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          jhiItemCount: true,
          bPagination: true,
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'jhi-sort-indicator': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          attachGroupService: () => attachGroupServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        attachGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(AttachGroup, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(attachGroupServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.attachGroups[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for an id', async () => {
        // WHEN
        const wrapper = shallowMount(AttachGroup, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(attachGroupServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['id,asc'],
        });
      });
    });
    describe('Handles', () => {
      let comp: AttachGroupComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(AttachGroup, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        attachGroupServiceStub.retrieve.reset();
        attachGroupServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('should load a page', async () => {
        // GIVEN
        attachGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.page = 2;
        await comp.$nextTick();

        // THEN
        expect(attachGroupServiceStub.retrieve.called).toBeTruthy();
        expect(comp.attachGroups[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should not load a page if the page is the same as the previous page', () => {
        // WHEN
        comp.page = 1;

        // THEN
        expect(attachGroupServiceStub.retrieve.called).toBeFalsy();
      });

      it('should re-initialize the page', async () => {
        // GIVEN
        comp.page = 2;
        await comp.$nextTick();
        attachGroupServiceStub.retrieve.reset();
        attachGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        comp.clear();
        await comp.$nextTick();

        // THEN
        expect(comp.page).toEqual(1);
        expect(attachGroupServiceStub.retrieve.callCount).toEqual(1);
        expect(comp.attachGroups[0]).toEqual(expect.objectContaining({ id: 123 }));
      });

      it('should calculate the sort attribute for a non-id attribute', async () => {
        // WHEN
        comp.propOrder = 'name';
        await comp.$nextTick();

        // THEN
        expect(attachGroupServiceStub.retrieve.lastCall.firstArg).toMatchObject({
          sort: ['name,asc', 'id'],
        });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        attachGroupServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeAttachGroup();
        await comp.$nextTick(); // clear components

        // THEN
        expect(attachGroupServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(attachGroupServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
