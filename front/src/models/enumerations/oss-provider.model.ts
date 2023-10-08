export enum OssProvider {
  /**
   * 本地
   */
  LOCAL = 'LOCAL',

  /**
   * MINIO
   */
  MINIO = 'MINIO',

  /**
   * 七牛云
   */
  QINIU = 'QINIU',

  /**
   * 阿里云
   */
  ALI = 'ALI',

  /**
   * 腾讯云
   */
  TENCENT = 'TENCENT',
}
export const OssProviderDict = [
  {
    value: OssProvider.LOCAL,
    label: 'LOCAL',
  },

  {
    value: OssProvider.MINIO,
    label: 'MINIO',
  },

  {
    value: OssProvider.QINIU,
    label: 'QINIU',
  },

  {
    value: OssProvider.ALI,
    label: 'ALI',
  },

  {
    value: OssProvider.TENCENT,
    label: 'TENCENT',
  },
];
