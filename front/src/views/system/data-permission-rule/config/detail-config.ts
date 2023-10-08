import { h } from 'vue';
import { Switch } from 'ant-design-vue';
import dayjs from 'dayjs';
import { DescItem } from '@/components/Description';

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
    label: '菜单ID',
    field: 'permissionId',
  },
  {
    label: '规则名称',
    field: 'name',
  },
  {
    label: '字段',
    field: 'column',
  },
  {
    label: '条件',
    field: 'conditions',
  },
  {
    label: '规则值',
    field: 'value',
  },
  {
    label: '禁用状态',
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
    label: '创建者Id',
    field: 'createdBy',
  },
  {
    label: '创建时间',
    field: 'createdDate',
    format: (value, _data) => {
      return value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '';
    },
  },
  {
    label: '修改者Id',
    field: 'lastModifiedBy',
  },
  {
    label: '修改时间',
    field: 'lastModifiedDate',
    format: (value, _data) => {
      return value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '';
    },
  },
];

export default {
  fields,
};
