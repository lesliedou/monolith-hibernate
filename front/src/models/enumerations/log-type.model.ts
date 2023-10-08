export enum LogType {
  /**
   * 登录日志
   */
  LOGIN = 'LOGIN',

  /**
   * 操作日志
   */
  OPERATE = 'OPERATE',
}
export const LogTypeDict = [
  {
    value: LogType.LOGIN,
    label: 'LOGIN',
  },

  {
    value: LogType.OPERATE,
    label: 'OPERATE',
  },
];
