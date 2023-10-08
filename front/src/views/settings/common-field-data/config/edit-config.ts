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
    label: '名称',
    field: 'name',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段值',
    field: 'value',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入字段值', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段标题',
    field: 'label',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入字段标题', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '字段类型',
    field: 'valueType',
    component: 'Select',
    componentProps: { placeholder: '请选择字段类型', options: allEnumsDict.CommonFieldType, style: 'width: 100%' },
    rules: [],
  },
  {
    label: '说明',
    field: 'remark',
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入说明', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '排序',
    field: 'sortValue',
    component: 'InputNumber',
    componentProps: { placeholder: '请输入排序', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '是否禁用',
    field: 'disabled',
    component: 'Switch',
    componentProps: { placeholder: '请选择是否禁用' },
    rules: [],
  },
  {
    label: '实体名称',
    field: 'ownerEntityName',
    show: false,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入实体名称', style: 'width: 100%' },
    rules: [],
  },
  {
    label: '使用实体ID',
    field: 'ownerEntityId',
    show: false,
    component: 'Input',
    componentProps: { type: 'text', clearable: true, placeholder: '请输入使用实体ID', style: 'width: 100%' },
    rules: [],
  },
];
export default {
  fields,
};
