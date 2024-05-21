import axios from 'axios';
import sinon from 'sinon';
import LoginService from './login.service';

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
};

describe('Login Service test suite', () => {
  let loginService: LoginService;

  beforeEach(() => {
    loginService = new LoginService({ emit: vitest.fn() });
  });

  it('should call global logout when asked to', () => {
    loginService.logout();

    expect(axiosStub.post.calledWith('api/logout')).toBeTruthy();
  });
});
