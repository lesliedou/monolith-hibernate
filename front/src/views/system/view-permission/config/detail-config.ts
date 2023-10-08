import { h } from 'vue';
import { Switch } from 'ant-design-vue';
import { DescItem } from '@/components/Description';
import Icon from '@/components/Icon/Icon.vue';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const fields: DescItem[] = [
  {
    label: 'ID',
    field: 'id',
    show: values => {
      return values && values.id;
    },
  },
  {
    label: '权限名称',
    field: 'text',
  },
  {
    label: 'i18n主键',
    field: 'i18n',
  },
  {
    label: '显示分组名',
    field: 'group',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.group = checked;
        },
      }),
  },
  {
    label: '路由',
    field: 'link',
  },
  {
    label: '外部链接',
    field: 'externalLink',
  },
  {
    label: '链接目标',
    field: 'target',
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('TargetType').find(item => item.value === value) || value;
    },
  },
  {
    label: '图标',
    field: 'icon',
    render: (value, _data) => h(Icon, { class: value, style: 'font-size: 20px;' }),
  },
  {
    label: '禁用菜单',
    field: 'disabled',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.disabled = checked;
        },
      }),
  },
  {
    label: '隐藏菜单',
    field: 'hide',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.hide = checked;
        },
      }),
  },
  {
    label: '隐藏面包屑',
    field: 'hideInBreadcrumb',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.hideInBreadcrumb = checked;
        },
      }),
  },
  {
    label: '快捷菜单项',
    field: 'shortcut',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.shortcut = checked;
        },
      }),
  },
  {
    label: '菜单根节点',
    field: 'shortcutRoot',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.shortcutRoot = checked;
        },
      }),
  },
  {
    label: '允许复用',
    field: 'reuse',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.reuse = checked;
        },
      }),
  },
  {
    label: '权限代码',
    field: 'code',
  },
  {
    label: '权限描述',
    field: 'description',
  },
  {
    label: '权限类型',
    field: 'type',
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('ViewPermissionType').find(item => item.value === value) || value;
    },
  },
  {
    label: '排序',
    field: 'order',
  },
  {
    label: 'api权限标识串',
    field: 'apiPermissionCodes',
  },
  {
    label: '组件名称',
    field: 'componentFile',
  },
  {
    label: '重定向路径',
    field: 'redirect',
  },
  {
    label: '上级',
    field: 'parentText',
  },
];

export default {
  fields,
};
