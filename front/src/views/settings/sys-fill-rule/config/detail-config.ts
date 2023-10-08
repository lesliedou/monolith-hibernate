import { h, resolveComponent } from 'vue';
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
    label: '规则名称',
    field: 'name',
  },
  {
    label: '规则Code',
    field: 'code',
  },
  {
    label: '规则描述',
    field: 'desc',
  },
  {
    label: '是否启用',
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
  {
    label: '重置频率',
    field: 'resetFrequency',
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('ResetFrequency').find(item => item.value === value) || value;
    },
  },
  {
    label: '序列值',
    field: 'seqValue',
  },
  {
    label: '生成值',
    field: 'fillValue',
  },
  {
    label: '规则实现类',
    field: 'implClass',
  },
  {
    label: '规则参数',
    field: 'params',
  },
  {
    label: '重置开始日期',
    field: 'resetStartTime',
    format: (value, _data) => {
      return value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '';
    },
  },
  {
    label: '重置结束日期',
    field: 'resetEndTime',
    format: (value, _data) => {
      return value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '';
    },
  },
  {
    label: '重置时间',
    field: 'resetTime',
    format: (value, _data) => {
      return value ? dayjs(value).format('YYYY-MM-DD HH:mm:ss') : '';
    },
  },
  {
    label: '配置项列表',
    field: 'ruleItems',
    render: (value, _data) => h(resolveComponent('vxe-grid'), { disabled: true, data: value, columns: {} }),
  },
];
const ruleItemsColumns = [
  {
    label: 'ID',
    field: 'id',
    show: values => {
      return values && values.id;
    },
  },
  {
    label: '排序值',
    field: 'sortValue',
  },
  {
    label: '字段参数类型',
    field: 'fieldParamType',
    format: (value, _data) => {
      const { getEnumDict } = useI18n();
      return getEnumDict('FieldParamType').find(item => item.value === value) || value;
    },
  },
  {
    label: '字段参数值',
    field: 'fieldParamValue',
  },
  {
    label: '日期格式',
    field: 'datePattern',
  },
  {
    label: '序列长度',
    field: 'seqLength',
  },
  {
    label: '序列增量',
    field: 'seqIncrement',
  },
  {
    label: '序列起始值',
    field: 'seqStartValue',
  },
];

export default {
  fields,
  ruleItemsColumns,
};
