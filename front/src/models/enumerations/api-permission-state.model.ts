export enum ApiPermissionState {
  /**
   * 可配置
   */
  CONFIGURABLE = 'CONFIGURABLE',

  /**
   * 允许所有
   */
  PERMIT_ALL = 'PERMIT_ALL',

  /**
   * 不可达
   */
  UNREACHABLE = 'UNREACHABLE',

  /**
   * 认证
   */
  AUTHENTICATE = 'AUTHENTICATE',
}
export const ApiPermissionStateDict = [
  {
    value: ApiPermissionState.CONFIGURABLE,
    label: 'CONFIGURABLE',
  },

  {
    value: ApiPermissionState.PERMIT_ALL,
    label: 'PERMIT_ALL',
  },

  {
    value: ApiPermissionState.UNREACHABLE,
    label: 'UNREACHABLE',
  },

  {
    value: ApiPermissionState.AUTHENTICATE,
    label: 'AUTHENTICATE',
  },
];
