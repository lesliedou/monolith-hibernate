import type { AppRouteModule } from '@/router/types';

import { LAYOUT } from '@/router/constant';
const IFrame = () => import('@/views/sys/iframe/FrameBlank.vue');

const dev: AppRouteModule = {
  path: '/dev',
  name: 'Dev',
  component: LAYOUT,
  redirect: '/dev/doc',
  meta: {
    orderNo: 1000,
    icon: 'ion:tv-outline',
    title: '开发参考',
  },

  children: [
    {
      path: 'doc',
      name: 'Doc',
      component: IFrame,
      meta: {
        frameSrc: '/swagger-ui/index.html',
        title: 'API文档',
      },
    },
    {
      path: 'icon',
      name: 'IconDemo',
      component: () => import('@/views/dev/feat/icon/index.vue'),
      meta: {
        title: 'ICON选择',
      },
    },
  ],
};

export default dev;
