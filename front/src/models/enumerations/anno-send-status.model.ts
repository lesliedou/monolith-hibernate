export enum AnnoSendStatus {
  /**
   * NOT_RELEASE
   */

  NOT_RELEASE = 'NOT_RELEASE',

  /**
   * RELEASED
   */

  RELEASED = 'RELEASED',

  /**
   * CANCELED
   */

  CANCELED = 'CANCELED',
}
export const AnnoSendStatusDict = [
  {
    value: AnnoSendStatus.NOT_RELEASE,
    label: 'NOT_RELEASE',
  },

  {
    value: AnnoSendStatus.RELEASED,
    label: 'RELEASED',
  },

  {
    value: AnnoSendStatus.CANCELED,
    label: 'CANCELED',
  },
];
