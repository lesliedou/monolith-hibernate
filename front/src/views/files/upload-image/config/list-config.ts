import { VxeGridProps } from 'vxe-table';
import { VxeGridPropTypes } from 'vxe-table/types/grid';
import dayjs from 'dayjs';

// begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！-->

const searchForm = (): any[] => [
  {
    title: 'ID',
    field: 'id',
    componentType: 'Text',
    value: '',
    type: 'Long',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '完整文件名',
    field: 'fullName',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '文件名',
    field: 'name',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '扩展名',
    field: 'ext',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '文件类型',
    field: 'type',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: 'Web Url地址',
    field: 'url',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '本地路径',
    field: 'path',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '本地存储目录',
    field: 'folder',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '使用实体名称',
    field: 'ownerEntityName',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '使用实体ID',
    field: 'ownerEntityId',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '业务标题',
    field: 'businessTitle',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '业务自定义描述内容',
    field: 'businessDesc',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '业务状态',
    field: 'businessStatus',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '创建时间',
    field: 'createAt',
    componentType: 'DateTime',
    operator: '',
    span: 8,
    type: 'ZonedDateTime',
    componentProps: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss', style: 'width: 100%' },
  },
  {
    title: '文件大小',
    field: 'fileSize',
    componentType: 'Text',
    value: '',
    type: 'Long',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '小图Url',
    field: 'smartUrl',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '中等图Url',
    field: 'mediumUrl',
    componentType: 'Text',
    value: '',
    type: 'String',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '文件被引用次数',
    field: 'referenceCount',
    componentType: 'Text',
    value: '',
    type: 'Long',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '创建者Id',
    field: 'createdBy',
    componentType: 'Text',
    value: '',
    type: 'Long',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '创建时间',
    field: 'createdDate',
    componentType: 'DateTime',
    operator: '',
    span: 8,
    type: 'ZonedDateTime',
    componentProps: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss', style: 'width: 100%' },
  },
  {
    title: '修改者Id',
    field: 'lastModifiedBy',
    componentType: 'Text',
    value: '',
    type: 'Long',
    operator: '',
    span: 8,
    componentProps: {},
  },
  {
    title: '修改时间',
    field: 'lastModifiedDate',
    componentType: 'DateTime',
    operator: '',
    span: 8,
    type: 'ZonedDateTime',
    componentProps: { type: 'date', format: 'YYYY-MM-DD hh:mm:ss', style: 'width: 100%' },
  },
];

const columns = (): VxeGridPropTypes.Columns => [
  {
    fixed: 'left',
    type: 'checkbox',
    width: 60,
  },
  {
    title: 'ID',
    field: 'id',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '完整文件名',
    field: 'fullName',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '文件名',
    field: 'name',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '扩展名',
    field: 'ext',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '文件类型',
    field: 'type',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: 'Web Url地址',
    field: 'url',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '本地路径',
    field: 'path',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '本地存储目录',
    field: 'folder',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '使用实体名称',
    field: 'ownerEntityName',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '使用实体ID',
    field: 'ownerEntityId',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '业务标题',
    field: 'businessTitle',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '业务自定义描述内容',
    field: 'businessDesc',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '业务状态',
    field: 'businessStatus',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '创建时间',
    field: 'createAt',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    formatter: ({ cellValue }) => (cellValue ? dayjs(cellValue).format('YYYY-MM-DD hh:mm:ss') : ''),
  },
  {
    title: '文件大小',
    field: 'fileSize',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '小图Url',
    field: 'smartUrl',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '中等图Url',
    field: 'mediumUrl',
    minWidth: 160,
    visible: true,
    treeNode: false,
    params: { type: 'STRING' },
    editRender: { name: 'AInput', enabled: false },
  },
  {
    title: '文件被引用次数',
    field: 'referenceCount',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '创建者Id',
    field: 'createdBy',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '创建时间',
    field: 'createdDate',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    formatter: ({ cellValue }) => (cellValue ? dayjs(cellValue).format('YYYY-MM-DD hh:mm:ss') : ''),
  },
  {
    title: '修改者Id',
    field: 'lastModifiedBy',
    minWidth: 80,
    visible: true,
    treeNode: false,
    params: { type: 'LONG' },
    editRender: { name: 'AInputNumber', enabled: false },
  },
  {
    title: '修改时间',
    field: 'lastModifiedDate',
    minWidth: 140,
    visible: true,
    treeNode: false,
    params: { type: 'ZONED_DATE_TIME' },
    formatter: ({ cellValue }) => (cellValue ? dayjs(cellValue).format('YYYY-MM-DD hh:mm:ss') : ''),
  },
  {
    title: '操作',
    field: 'operation',
    fixed: 'right',
    width: 140,
    slots: { default: 'recordAction' },
  },
];

const baseGridOptions = (): VxeGridProps => {
  return {
    rowConfig: {
      keyField: 'id',
      isHover: true,
    },
    border: true,
    showHeaderOverflow: true,
    showOverflow: true,
    keepSource: true,
    id: 'full_edit_1',
    height: 600,
    printConfig: {
      columns: [
        // { field: 'name' },
      ],
    },
    filterConfig: {
      remote: true,
    },
    columnConfig: {
      resizable: true,
    },
    sortConfig: {
      trigger: 'cell',
      remote: true,
      orders: ['asc', 'desc', null],
      multiple: true,
      defaultSort: {
        field: 'id',
        order: 'desc',
      },
    },
    pagerConfig: {
      layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
      pageSize: 15,
      pageSizes: [5, 10, 15, 20, 30, 50],
      total: 0,
      pagerCount: 5,
      currentPage: 1,
    },
    importConfig: {},
    exportConfig: {},
    checkboxConfig: {
      // labelField: 'id',
      reserve: true,
      highlight: true,
    },
    editRules: {},
    editConfig: {
      trigger: 'click',
      mode: 'row',
      showStatus: true,
    },
  };
};

export default {
  searchForm,
  columns,
  baseGridOptions,
};
