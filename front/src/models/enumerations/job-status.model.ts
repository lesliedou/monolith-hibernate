export enum JobStatus {
  /**
   * 正常
   */
  NORMAL = 'NORMAL',

  /**
   * 暂停
   */
  PAUSED = 'PAUSED',

  /**
   * 完成
   */
  COMPLETE = 'COMPLETE',

  /**
   * 错误
   */
  ERROR = 'ERROR',

  /**
   * 阻塞
   */
  BLOCKED = 'BLOCKED',
}
export const JobStatusDict = [
  {
    value: JobStatus.NORMAL,
    label: 'NORMAL',
  },

  {
    value: JobStatus.PAUSED,
    label: 'PAUSED',
  },

  {
    value: JobStatus.COMPLETE,
    label: 'COMPLETE',
  },

  {
    value: JobStatus.ERROR,
    label: 'ERROR',
  },

  {
    value: JobStatus.BLOCKED,
    label: 'BLOCKED',
  },
];
