<template>
  <div :class="getClass">
    <template v-if="canFullscreen">
      <Tooltip v-if="fullScreen" title="还原" placement="bottom">
        <FullscreenExitOutlined role="full" @click="handleFullScreen" />
      </Tooltip>
      <Tooltip v-else title="最大化" placement="bottom">
        <FullscreenOutlined role="close" @click="handleFullScreen" />
      </Tooltip>
    </template>
    <Tooltip title="关闭" placement="bottom">
      <CloseOutlined @click="handleCancel" />
    </Tooltip>
  </div>
</template>
<script lang="ts">
import { defineComponent, computed } from 'vue';
import { FullscreenExitOutlined, FullscreenOutlined, CloseOutlined } from '@ant-design/icons-vue';
import { useDesign } from '@/hooks/web/useDesign';
import { Tooltip } from 'ant-design-vue';

export default defineComponent({
  name: 'ModalClose',
  components: { Tooltip, FullscreenExitOutlined, FullscreenOutlined, CloseOutlined },
  props: {
    canFullscreen: { type: Boolean, default: true },
    fullScreen: { type: Boolean },
  },
  emits: ['cancel', 'fullscreen'],
  setup(props, { emit }) {
    const { prefixCls } = useDesign('basic-modal-close');

    const getClass = computed(() => {
      return [
        prefixCls,
        `${prefixCls}--custom`,
        {
          [`${prefixCls}--can-full`]: props.canFullscreen,
        },
      ];
    });

    function handleCancel(e: Event) {
      emit('cancel', e);
    }

    function handleFullScreen(e: Event) {
      e?.stopPropagation();
      e?.preventDefault();
      emit('fullscreen');
    }

    return {
      getClass,
      prefixCls,
      handleCancel,
      handleFullScreen,
    };
  },
});
</script>
<style lang="less">
@prefix-cls: ~'@{namespace}-basic-modal-close';
.@{prefix-cls} {
  display: flex;
  height: 95%;
  align-items: center;

  > span {
    margin-left: 48px;
    font-size: 16px;
  }

  &--can-full {
    > span {
      margin-left: 12px;
    }
  }

  &:not(&--can-full) {
    > span:nth-child(1) {
      &:hover {
        font-weight: 700;
      }
    }
  }

  & span:nth-child(1) {
    display: inline-block;
    padding: 10px;

    &:hover {
      color: @primary-color;
    }
  }

  & span:last-child {
    padding: 10px 10px 10px 0;
    &:hover {
      color: @error-color;
    }
  }
}
</style>
