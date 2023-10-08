export enum ResetFrequency {
  /**
   * 不重置
   */

  NONE = 'NONE',

  /**
   * 每天重置
   */

  DAILY = 'DAILY',

  /**
   * 每周重置
   */

  WEEKLY = 'WEEKLY',

  /**
   * 每月重置
   */

  MONTHLY = 'MONTHLY',

  /**
   * 每年重置
   */

  YEARLY = 'YEARLY',

  /**
   * 自定义重置
   */

  CUSTOM = 'CUSTOM',
}
export const ResetFrequencyDict = [
  {
    value: ResetFrequency.NONE,
    label: '不重置',
  },

  {
    value: ResetFrequency.DAILY,
    label: '每天重置',
  },

  {
    value: ResetFrequency.WEEKLY,
    label: '每周重置',
  },

  {
    value: ResetFrequency.MONTHLY,
    label: '每月重置',
  },

  {
    value: ResetFrequency.YEARLY,
    label: '每年重置',
  },

  {
    value: ResetFrequency.CUSTOM,
    label: '自定义重置',
  },
];
