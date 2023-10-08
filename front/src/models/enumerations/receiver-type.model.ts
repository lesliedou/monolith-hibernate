export enum ReceiverType {
  /**
   * USER
   */

  USER = 'USER',

  /**
   * ALL
   */

  ALL = 'ALL',

  /**
   * DEPARTMENT
   */

  DEPARTMENT = 'DEPARTMENT',

  /**
   * AUTHORITY
   */

  AUTHORITY = 'AUTHORITY',

  /**
   * POSITION
   */

  POSITION = 'POSITION',
}
export const ReceiverTypeDict = [
  {
    value: ReceiverType.USER,
    label: 'USER',
  },

  {
    value: ReceiverType.ALL,
    label: 'ALL',
  },

  {
    value: ReceiverType.DEPARTMENT,
    label: 'DEPARTMENT',
  },

  {
    value: ReceiverType.AUTHORITY,
    label: 'AUTHORITY',
  },

  {
    value: ReceiverType.POSITION,
    label: 'POSITION',
  },
];
