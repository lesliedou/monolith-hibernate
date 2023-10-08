export enum SendStatus {
  /**
   * 未推送
   */
  WAITING = 'WAITING',

  /**
   * 推送成功
   */
  SUCCESS = 'SUCCESS',

  /**
   * 推送失败
   */
  FAILURE = 'FAILURE',

  /**
   * 失败不再发送
   */
  NOT_TRY = 'NOT_TRY',
}
export const SendStatusDict = [
  {
    value: SendStatus.WAITING,
    label: 'WAITING',
  },

  {
    value: SendStatus.SUCCESS,
    label: 'SUCCESS',
  },

  {
    value: SendStatus.FAILURE,
    label: 'FAILURE',
  },

  {
    value: SendStatus.NOT_TRY,
    label: 'NOT_TRY',
  },
];
