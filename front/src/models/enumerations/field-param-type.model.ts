export enum FieldParamType {
  /**
   * 日期时间
   */

  DATETIME = 'DATETIME',

  /**
   * 数字序列
   */

  NUMBER = 'NUMBER',

  /**
   * 固定字符
   */

  FIXED_CHAR = 'FIXED_CHAR',

  /**
   * 参数
   */

  PARAM = 'PARAM',
}
export const FieldParamTypeDict = [
  {
    value: FieldParamType.DATETIME,
    label: '日期时间',
  },

  {
    value: FieldParamType.NUMBER,
    label: '数字序列',
  },

  {
    value: FieldParamType.FIXED_CHAR,
    label: '固定字符',
  },

  {
    value: FieldParamType.PARAM,
    label: '参数',
  },
];
