export enum SmsProvider {
  /**
   * 阿里云
   */
  ALIBABA = 'ALIBABA',

  /**
   * 华为云
   */
  HUAWEI = 'HUAWEI',

  /**
   * 云片
   */
  YUNPIAN = 'YUNPIAN',

  /**
   * 腾讯云
   */
  TENCENT = 'TENCENT',

  /**
   * 合一
   */
  UNI_SMS = 'UNI_SMS',

  /**
   * 京东云
   */
  JD_CLOUD = 'JD_CLOUD',

  /**
   * 容联云
   */
  CLOOPEN = 'CLOOPEN',

  /**
   * 亿美软通
   */
  EMAY = 'EMAY',
}
export const SmsProviderDict = [
  {
    value: SmsProvider.ALIBABA,
    label: 'ALIBABA',
  },

  {
    value: SmsProvider.HUAWEI,
    label: 'HUAWEI',
  },

  {
    value: SmsProvider.YUNPIAN,
    label: 'YUNPIAN',
  },

  {
    value: SmsProvider.TENCENT,
    label: 'TENCENT',
  },

  {
    value: SmsProvider.UNI_SMS,
    label: 'UNI_SMS',
  },

  {
    value: SmsProvider.JD_CLOUD,
    label: 'JD_CLOUD',
  },

  {
    value: SmsProvider.CLOOPEN,
    label: 'CLOOPEN',
  },

  {
    value: SmsProvider.EMAY,
    label: 'EMAY',
  },
];
