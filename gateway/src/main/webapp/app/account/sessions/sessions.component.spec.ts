import { shallowMount } from '@vue/test-utils';
import axios from 'axios';
import sinon from 'sinon';

import { createTestingPinia } from '@pinia/testing';
import Sessions from './sessions.vue';
import { useStore } from '@/store';

type SessionsComponentType = InstanceType<typeof Sessions>;

const pinia = createTestingPinia({ stubActions: false });

const store = useStore();

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Sessions Component', () => {
  let sessions: SessionsComponentType;

  beforeEach(() => {
    axiosStub.get.reset();
    axiosStub.get.resolves({ data: [] });
    axiosStub.delete.reset();

    store.setAuthentication({
      login: 'username',
    });

    const wrapper = shallowMount(Sessions, {
      global: {
        plugins: [pinia],
      },
    });
    sessions = wrapper.vm;
  });

  it('should call remote service on init', () => {
    expect(axiosStub.get.callCount).toEqual(1);
  });

  it('should have good username', () => {
    expect(sessions.username).toEqual('username');
  });

  it('should invalidate a session', async () => {
    // Given
    axiosStub.get.reset();
    axiosStub.get.resolves({ data: [] });
    axiosStub.delete.resolves();

    // When
    await sessions.invalidate('session');
    await sessions.$nextTick();

    // Then
    expect(sessions.error).toBeNull();
    expect(sessions.success).toEqual('OK');
    expect(axiosStub.get.callCount).toEqual(1);
  });

  it('should fail to invalidate session', async () => {
    // Given
    axiosStub.get.reset();
    axiosStub.get.resolves({ data: [] });
    axiosStub.delete.rejects();

    // When
    await sessions.invalidate('session');
    await sessions.$nextTick();

    // Then
    expect(sessions.success).toBeNull();
    expect(sessions.error).toEqual('ERROR');
    expect(axiosStub.get.callCount).toEqual(0);
  });
});
