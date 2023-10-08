import { FormSchema } from '@/components/Form';
import { allEnumsDict } from '@/models/enumerations/all-enums';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！

const fields = (): FormSchema[] => [
  {
    label: 'ID',
    field: 'id',
    show: ({ values }) => {
      return values && values.id;
    },
    dynamicDisabled: true,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入ID', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '排序值',
    field: 'sortValue',
    component: 'InputNumber',
    componentProps: { placeholder: '请输入排序值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段参数类型',
    field: 'fieldParamType',
    component: 'Select',
    componentProps: { placeholder: '请选择字段参数类型', options: allEnumsDict.FieldParamType, style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段参数值',
    field: 'fieldParamValue',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入字段参数值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '日期格式',
    field: 'datePattern',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入日期格式', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '序列长度',
    field: 'seqLength',
    component: 'InputNumber',
    componentProps: { placeholder: '请输入序列长度', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '序列增量',
    field: 'seqIncrement',
    component: 'InputNumber',
    componentProps: { placeholder: '请输入序列增量', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '序列起始值',
    field: 'seqStartValue',
    component: 'InputNumber',
    componentProps: { placeholder: '请输入序列起始值', style: 'width: 100%' },
    rules: [],
  },
];
export default {
  fields,
};
