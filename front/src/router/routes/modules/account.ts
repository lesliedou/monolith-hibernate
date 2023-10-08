import type { AppRouteModule } from '@/router/types';

import { LAYOUT } from '@/router/constant';

const account: AppRouteModule = {
  path: '/account',
  name: 'Account',
  component: LAYOUT,
  redirect: '/account/settings',
  meta: {
    hideChildrenInMenu: true,
    icon: 'simple-icons:about-dot-me',
    title: '个人中心',
    orderNo: 100000,
    hideMenu: true,
  },
  children: [
    {
      path: 'settings',
      name: 'AboutPage',
      component: () => import('@/views/account/setting/index.vue'),
      meta: {
        title: '个人中心',
        icon: 'simple-icons:about-dot-me',
        hideMenu: true,
      },
    },
  ],
};

export default account;
