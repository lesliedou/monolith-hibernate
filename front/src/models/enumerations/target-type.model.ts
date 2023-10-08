export enum TargetType {
  /**
   * 新窗口
   */
  BLANK = 'BLANK',

  /**
   * 当前窗口
   */
  SELF = 'SELF',

  /**
   * 父窗口
   */
  PARENT = 'PARENT',

  /**
   * 顶层窗口
   */
  TOP = 'TOP',
}
export const TargetTypeDict = [
  {
    value: TargetType.BLANK,
    label: 'BLANK',
  },

  {
    value: TargetType.SELF,
    label: 'SELF',
  },

  {
    value: TargetType.PARENT,
    label: 'PARENT',
  },

  {
    value: TargetType.TOP,
    label: 'TOP',
  },
];
