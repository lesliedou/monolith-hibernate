export enum ApiPermissionType {
  /**
   * 业务
   */
  BUSINESS = 'BUSINESS',

  /**
   * API接口
   */
  API = 'API',

  /**
   * 实体
   */
  ENTITY = 'ENTITY',

  /**
   * 微服务
   */
  MICRO_SERVICE = 'MICRO_SERVICE',
}
export const ApiPermissionTypeDict = [
  {
    value: ApiPermissionType.BUSINESS,
    label: 'BUSINESS',
  },

  {
    value: ApiPermissionType.API,
    label: 'API',
  },

  {
    value: ApiPermissionType.ENTITY,
    label: 'ENTITY',
  },

  {
    value: ApiPermissionType.MICRO_SERVICE,
    label: 'MICRO_SERVICE',
  },
];
