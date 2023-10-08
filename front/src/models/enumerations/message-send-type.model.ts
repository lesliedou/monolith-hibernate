export enum MessageSendType {
  /**
   * EMAIL
   */

  EMAIL = 'EMAIL',

  /**
   * SMS
   */

  SMS = 'SMS',

  /**
   * WECHAT
   */

  WECHAT = 'WECHAT',
}
export const MessageSendTypeDict = [
  {
    value: MessageSendType.EMAIL,
    label: 'EMAIL',
  },

  {
    value: MessageSendType.SMS,
    label: 'SMS',
  },

  {
    value: MessageSendType.WECHAT,
    label: 'WECHAT',
  },
];
