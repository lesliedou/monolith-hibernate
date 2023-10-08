<template>
  <Tooltip title="锁定屏幕" placement="bottom" :mouseEnterDelay="0.5" @click="handleLock">
    <LockOutlined />
  </Tooltip>
  <LockModal @register="register" />
</template>
<script lang="ts">
import { defineComponent } from 'vue';
import { createAsyncComponent } from '@/utils/factory/createAsyncComponent';
import { Tooltip } from 'ant-design-vue';
import { LockOutlined } from '@ant-design/icons-vue';
import Icon from '@/components/Icon/Icon.vue';
import { useModal } from '@/components/Modal';

export default defineComponent({
  name: 'LockScreen',
  inheritAttrs: false,
  components: {
    Icon,
    Tooltip,
    LockOutlined,
    LockModal: createAsyncComponent(() => import('./lock/LockModal.vue')),
  },
  setup() {
    const [register, { openModal }] = useModal();

    function handleLock() {
      openModal(true);
    }

    return {
      register,
      handleLock,
    };
  },
});
</script>
