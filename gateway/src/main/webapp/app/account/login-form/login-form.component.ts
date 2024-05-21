import axios from 'axios';
import { defineComponent, inject, ref, type Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useI18n } from 'vue-i18n';
import type AccountService from '../account.service';
import type LoginService from '@/account/login.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  setup() {
    const authenticationError: Ref<boolean> = ref(false);
    const login: Ref<string> = ref(null);
    const password: Ref<string> = ref(null);
    const rememberMe: Ref<boolean> = ref(false);
    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const accountService = inject<AccountService>('accountService');
    const loginService = inject<LoginService>('loginService');

    const doLogin = async () => {
      const data = `username=${encodeURIComponent(login.value)}&password=${encodeURIComponent(password.value)}&remember-me=${rememberMe.value}&submit=Login`;
      try {
        await axios.post('api/authentication', data, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
        });

        authenticationError.value = false;
        loginService.hideLogin();
        await accountService.retrieveAccount();
        if (route.path === '/forbidden') {
          previousState();
        }
      } catch (_error) {
        authenticationError.value = true;
      }
    };
    return {
      authenticationError,
      login,
      password,
      rememberMe,
      accountService,
      doLogin,
      t$: useI18n().t,
    };
  },
});
