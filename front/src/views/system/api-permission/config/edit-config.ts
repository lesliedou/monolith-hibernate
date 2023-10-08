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
    label: '服务名称',
    field: 'serviceName',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入服务名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '权限名称',
    field: 'name',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入权限名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: 'Code',
    field: 'code',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入Code', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '权限描述',
    field: 'description',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入权限描述', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '类型',
    field: 'type',
    component: 'Select',
    componentProps: { placeholder: '请选择类型', options: allEnumsDict.ApiPermissionType, style: 'width: 100%' },
    rules: [],
  },
  {
    label: '请求类型',
    field: 'method',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入请求类型', style: 'width: 100%' },
    rules: [],
  },
  {
    label: 'url 地址',
    field: 'url',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入url 地址', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '状态',
    field: 'status',
    component: 'Select',
    componentProps: { placeholder: '请选择状态', options: allEnumsDict.ApiPermissionState, style: 'width: 100%' },
    rules: [],
  },
  {
    label: '上级',
    field: 'parent',
    component: 'ApiTreeSelect',
    componentProps: {
      api: null,
      style: 'width: 100%',
      labelInValue: true,
      numberToString: true,
      fieldNames: { children: 'children', value: 'id', label: 'name' },
      resultField: 'records',
      placeholder: '请选择上级',
    },
    rules: [],
  },
];
export default {
  fields,
};
