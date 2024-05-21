import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import axios from 'axios';
import sinon from 'sinon';
import { type RouteLocation } from 'vue-router';
import { createTestingPinia } from '@pinia/testing';

import LoginService from '../login.service';
import AccountService from '../account.service';
import LoginForm from './login-form.vue';
import { useStore } from '@/store';

type LoginFormComponentType = InstanceType<typeof LoginForm>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();
vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const pinia = createTestingPinia();

const store = useStore();

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
};

describe('LoginForm Component', () => {
  let loginForm: LoginFormComponentType;

  beforeEach(() => {
    route = {};
    axiosStub.get.resolves({});
    axiosStub.post.reset();

    const loginService = new LoginService({ emit: vitest.fn() });

    const globalOptions: MountingOptions<LoginFormComponentType>['global'] = {
      stubs: {
        'b-alert': true,
        'b-button': true,
        'b-form': true,
        'b-form-input': true,
        'b-form-group': true,
        'b-form-checkbox': true,
        'b-link': true,
      },
      plugins: [pinia],
      provide: {
        loginService,
        accountService: new AccountService(store),
      },
    };
    const wrapper = shallowMount(LoginForm, { global: globalOptions });

    loginForm = wrapper.vm;
  });

  it('should authentication be KO', async () => {
    // GIVEN
    loginForm.login = 'login';
    loginForm.password = 'pwd';
    loginForm.rememberMe = true;
    axiosStub.post.rejects();

    // WHEN
    loginForm.doLogin();
    await loginForm.$nextTick();

    // THEN
    expect(
      axiosStub.post.calledWith('api/authentication', 'username=login&password=pwd&remember-me=true&submit=Login', {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      }),
    ).toBeTruthy();
    await loginForm.$nextTick();
    expect(loginForm.authenticationError).toBeTruthy();
  });

  it('should authentication be OK', async () => {
    // GIVEN
    loginForm.login = 'login';
    loginForm.password = 'pwd';
    loginForm.rememberMe = true;
    axiosStub.post.resolves({});

    // WHEN
    loginForm.doLogin();
    await loginForm.$nextTick();

    // THEN
    expect(
      axiosStub.post.calledWith('api/authentication', 'username=login&password=pwd&remember-me=true&submit=Login', {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      }),
    ).toBeTruthy();

    expect(loginForm.authenticationError).toBeFalsy();
  });
});
