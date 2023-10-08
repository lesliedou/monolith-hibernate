export enum CommonFieldType {
  /**
   * 整数
   */
  INTEGER = 'INTEGER',

  /**
   * 长整数
   */
  LONG = 'LONG',

  /**
   * 布尔
   */
  BOOLEAN = 'BOOLEAN',

  /**
   * 字符串
   */
  STRING = 'STRING',

  /**
   * 单精度
   */
  FLOAT = 'FLOAT',

  /**
   * 双精度
   */
  DOUBLE = 'DOUBLE',

  /**
   * 日期时间
   */
  ZONED_DATE_TIME = 'ZONED_DATE_TIME',

  /**
   * 本地日期
   */
  LOCATE_DATE = 'LOCATE_DATE',

  /**
   * 大数字
   */
  BIG_DECIMAL = 'BIG_DECIMAL',

  /**
   * 大文本
   */
  TEXTBLOB = 'TEXTBLOB',

  /**
   * 大图片
   */
  IMAGEBLOB = 'IMAGEBLOB',

  /**
   * 数组
   */
  ARRAY = 'ARRAY',

  /**
   * 枚举
   */
  ENUM = 'ENUM',

  /**
   * 上传图片
   */
  UPLOAD_IMAGE = 'UPLOAD_IMAGE',

  /**
   * 上传文件
   */
  UPLOAD_FILE = 'UPLOAD_FILE',

  /**
   * 实体
   */
  ENTITY = 'ENTITY',

  /**
   * 单选
   */
  RADIO = 'RADIO',

  /**
   * 多选
   */
  MULTI_SELECT = 'MULTI_SELECT',

  /**
   * 数据字典
   */
  DATA_DICTIONARY = 'DATA_DICTIONARY',

  /**
   * UUID
   */
  UUID = 'UUID',

  /**
   * 时间戳
   */
  INSTANT = 'INSTANT',
}
export const CommonFieldTypeDict = [
  {
    value: CommonFieldType.INTEGER,
    label: 'INTEGER',
  },

  {
    value: CommonFieldType.LONG,
    label: 'LONG',
  },

  {
    value: CommonFieldType.BOOLEAN,
    label: 'BOOLEAN',
  },

  {
    value: CommonFieldType.STRING,
    label: 'STRING',
  },

  {
    value: CommonFieldType.FLOAT,
    label: 'FLOAT',
  },

  {
    value: CommonFieldType.DOUBLE,
    label: 'DOUBLE',
  },

  {
    value: CommonFieldType.ZONED_DATE_TIME,
    label: 'ZONED_DATE_TIME',
  },

  {
    value: CommonFieldType.LOCATE_DATE,
    label: 'LOCATE_DATE',
  },

  {
    value: CommonFieldType.BIG_DECIMAL,
    label: 'BIG_DECIMAL',
  },

  {
    value: CommonFieldType.TEXTBLOB,
    label: 'TEXTBLOB',
  },

  {
    value: CommonFieldType.IMAGEBLOB,
    label: 'IMAGEBLOB',
  },

  {
    value: CommonFieldType.ARRAY,
    label: 'ARRAY',
  },

  {
    value: CommonFieldType.ENUM,
    label: 'ENUM',
  },

  {
    value: CommonFieldType.UPLOAD_IMAGE,
    label: 'UPLOAD_IMAGE',
  },

  {
    value: CommonFieldType.UPLOAD_FILE,
    label: 'UPLOAD_FILE',
  },

  {
    value: CommonFieldType.ENTITY,
    label: 'ENTITY',
  },

  {
    value: CommonFieldType.RADIO,
    label: 'RADIO',
  },

  {
    value: CommonFieldType.MULTI_SELECT,
    label: 'MULTI_SELECT',
  },

  {
    value: CommonFieldType.DATA_DICTIONARY,
    label: 'DATA_DICTIONARY',
  },

  {
    value: CommonFieldType.UUID,
    label: 'UUID',
  },

  {
    value: CommonFieldType.INSTANT,
    label: 'INSTANT',
  },
];
