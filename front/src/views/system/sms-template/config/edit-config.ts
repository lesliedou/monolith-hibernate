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
    label: '模板标题',
    field: 'name',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入模板标题', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '模板CODE',
    field: 'code',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入模板CODE', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '模板类型',
    field: 'type',
    component: 'Select',
    componentProps: { placeholder: '请选择模板类型', options: allEnumsDict.MessageSendType, style: 'width: 100%' },
    rules: [],
  },
  {
    label: '模板内容',
    field: 'content',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入模板内容', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '模板测试json',
    field: 'testJson',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入模板测试json', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '创建者Id',
    field: 'createdBy',
    show: false,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入创建者Id', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '创建时间',
    field: 'createdDate',
    show: false,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择创建时间', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '修改者Id',
    field: 'lastModifiedBy',
    show: false,
    component: 'InputNumber',
    componentProps: { placeholder: '请输入修改者Id', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '修改时间',
    field: 'lastModifiedDate',
    show: false,
    component: 'DatePicker',
    componentProps: { valueFormat: 'YYYY-MM-DD hh:mm:ss', placeholder: '请选择修改时间', style: 'width: 100%' },
    rules: [],
  },
];
export default {
  fields,
};
