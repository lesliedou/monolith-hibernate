<template>
  <ConfigProvider :locale="antdLocale">
    <AppProvider>
      <RouterView />
    </AppProvider>
  </ConfigProvider>
</template>

<script lang="ts" setup>
import { ConfigProvider } from 'ant-design-vue';
import { AppProvider } from '@/components/Application';
import { useTitle } from '@/hooks/web/useTitle';
import antdLocale from 'ant-design-vue/es/locale/zh_CN';

import 'dayjs/locale/zh-cn';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

// Listening to page changes and dynamically changing site titles
useTitle();
</script>
