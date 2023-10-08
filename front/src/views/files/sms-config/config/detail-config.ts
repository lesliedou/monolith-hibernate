import { h } from 'vue';
import { Switch } from 'ant-design-vue';
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
    label: '提供商',
    field: 'provider',
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('SmsProvider').find(item => item.value === value) || value;
    },
  },
  {
    label: '资源编号',
    field: 'smsCode',
  },
  {
    label: '模板ID',
    field: 'templateId',
  },
  {
    label: 'accessKey',
    field: 'accessKey',
  },
  {
    label: 'secretKey',
    field: 'secretKey',
  },
  {
    label: 'regionId',
    field: 'regionId',
  },
  {
    label: '短信签名',
    field: 'signName',
  },
  {
    label: '备注',
    field: 'remark',
  },
  {
    label: '启用',
    field: 'enabled',
    render: (value, data) =>
      h(Switch, {
        disabled: true,
        checked: value,
        onChange: checked => {
          data.enabled = checked;
        },
      }),
  },
];

export default {
  fields,
};
