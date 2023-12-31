<script lang="tsx">
import { useRoute, useRouter } from 'vue-router';
import { getCurrentInstance, reactive, computed, defineComponent, h, ref, resolveComponent, Component } from 'vue';
import config from './config/edit-config';
import { ResourceCategory, IResourceCategory } from '@/models/files/resource-category.model';

import ServerProvider from '@/api-service/index';
import { BasicForm } from '@/components/Form';
import { PageWrapper } from '@/components/Page';
import Icon from '@/components/Icon/Icon.vue';
import { isBoolean, isFunction } from '@/utils/is';
import { message } from 'ant-design-vue';
import { useGo } from '@/hooks/web/usePage';
import { useMultipleTabStore } from '@/store/modules/multipleTab';

export default defineComponent({
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
  name: 'OssResourceCategoryEdit',
  props: {
    entityId: {
      type: [String, Number] as PropType<string | number>,
      default: '',
    },
    containerType: {
      type: String,
      default: 'router',
    },
    baseData: {
      type: Object,
      default: () => ({}),
    },
    savedOpen: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    BasicForm,
  },
  async setup(props) {
    const ctx = getCurrentInstance()?.proxy;
    const apiService = ctx?.$apiService as typeof ServerProvider;
    const relationshipApis: any = {
      files: apiService.files.uploadFileService.retrieve,
      children: apiService.files.resourceCategoryService.tree,
      images: apiService.files.uploadImageService.retrieve,
      parent: apiService.files.resourceCategoryService.tree,
    };
    const route = useRoute();
    const router = useRouter();
    const go = useGo();
    const tabStore = useMultipleTabStore();
    const activeNames = ref<any[]>([]);
    const handleChange = (val: any[]) => {
      activeNames.value = val;
      ctx?.$emit('change', activeNames.value);
    };
    let resourceCategoryId = ref('');
    if (props.containerType === 'router') {
      resourceCategoryId.value = route.params?.entityId as string;
    } else {
      resourceCategoryId.value = props.entityId as string;
    }
    const resourceCategory = ref<IResourceCategory>(new ResourceCategory());
    resourceCategory.value = Object.assign(resourceCategory.value, props.baseData);
    if (resourceCategoryId.value) {
      const data = await apiService.files.resourceCategoryService.find(Number(resourceCategoryId.value));
      if (data) {
        resourceCategory.value = Object.assign(resourceCategory.value, data);
      }
    }
    const formItemsConfig = config.fields();
    if (formItemsConfig && formItemsConfig.length && Object.keys(relationshipApis).length) {
      formItemsConfig
        .filter(item => item.field && Object.keys(relationshipApis).includes(item.field))
        .forEach(item => {
          item.componentProps = {
            ...item.componentProps,
            api: relationshipApis[item.field],
          };
        });
    }
    const submitButtonTitlePrefix = resourceCategoryId.value ? '更新' : '保存';
    const saveOrUpdateApi = resourceCategoryId.value
      ? apiService.files.resourceCategoryService.update
      : apiService.files.resourceCategoryService.create;
    const saveOrUpdate = () => {
      validate()
        .then(result => {
          if (result) {
            resourceCategory.value = Object.assign(resourceCategory.value, result);
            saveOrUpdateApi(resourceCategory.value)
              .then(res => {
                resourceCategory.value = Object.assign(resourceCategory.value, res);
                resourceCategoryId.value = resourceCategory.value.id + '';
                message.success(submitButtonTitlePrefix + '成功！');
                if (props.containerType === 'router') {
                  const { fullPath } = route; //获取当前路径
                  tabStore.closeTabByKey(fullPath, router).then(() => {
                    go('/files/resource-category/' + resourceCategoryId.value + '/edit', true);
                  });
                } else {
                  if (!props.savedOpen) {
                    ctx?.$emit('refresh', { update: true, containerType: props.containerType });
                  }
                  (ctx?.$refs['BASE_ENTITY'] as any).setFieldsValue(resourceCategory.value);
                }
              })
              .catch(error => {
                console.log('error', error);
                message.error(submitButtonTitlePrefix + '失败！');
              });
          } else {
            message.error('数据验证失败！');
          }
        })
        .catch(error => {
          console.log('error', error);
          message.error('数据验证失败！');
        });
    };
    //获得关联表属性。
    const pageConfig = reactive<any>({
      active: '0',
      operations: [
        {
          title: '关闭',
          type: 'default',
          theme: 'close',
          skipValidate: true,
          click: async () => {
            if (props.containerType === 'router') {
              const { fullPath } = route; //获取当前路径
              await tabStore.closeTabByKey(fullPath, router);
            } else {
              ctx?.$emit('cancel', { containerType: props.containerType, update: false });
            }
          },
        },
        {
          hide: () => {
            return !!resourceCategory.value.id;
          },
          type: 'primary',
          theme: 'save',
          click: saveOrUpdate,
        },
        {
          hide: () => {
            return !resourceCategory.value.id;
          },
          theme: 'update',
          type: 'primary',
          click: saveOrUpdate,
        },
      ],
    });
    const isEdit = computed(() => {
      return true;
    });
    const validate = async () => {
      let isValid = true;
      let result = {};
      var refKeys = Object.keys(ctx?.$refs as object);
      for (const refKey of refKeys) {
        const component: any = ctx?.$refs[refKey];
        if (['BASE_ENTITY', 'FormList'].includes(refKey)) {
          if (component && component.validate) {
            const validateResult = await component.validate();
            if (!validateResult) {
              isValid = false;
              break;
            } else {
              result = { ...result, ...validateResult };
            }
          }
        } else {
          if (component && component.validate) {
            const validateResult = await component.validate(true);
            if (!validateResult) {
              const { fullData } = component.getTableData();
              fullData.forEach(row => {
                if (typeof row.id === 'string' && row.id.startsWith('row_')) {
                  row.id = null;
                }
              });
              result[refKey] = fullData;
            } else {
              isValid = false;
            }
          }
        }
      }
      if (!isValid) {
        return false;
      } else {
        return result;
      }
    };
    const formGroup = computed(() => [
      {
        title: props.containerType === 'router' ? '资源分类' : null,
        operation: [],
        component: {
          name: 'a-form',
          props: {
            modelName: 'BASE_ENTITY',
            model: resourceCategory.value,
            labelWidth: '120px',
            fieldMapToTime: [],
            compact: true,
            alwaysShowLines: 1,
            schemas: formItemsConfig,
            // formItemsRender,
            size: 'default',
            disabled: false,
            showAdvancedButton: false,
            showResetButton: false,
            showSubmitButton: false,
            showActionButtonGroup: false,
            resetButtonOptions: {
              type: 'default',
              size: 'default',
              text: '关闭',
              preIcon: null,
            },
            actionColOptions: {
              span: 18,
            },
            submitButtonOptions: {
              type: 'primary',
              size: 'default',
              text: submitButtonTitlePrefix,
              preIcon: null,
            },
            resetFunc: () => {
              ctx?.$emit('cancel', { update: false, containerType: props.containerType });
            },
            submitFunc: saveOrUpdate,
          },
          on: {},
        },
      },
    ]);
    const formSlots = () => [];
    const renderChild = () => {
      const wrapperPros: any = {};
      if (!pageConfig?.canExpand) {
        wrapperPros.bordered = false;
        wrapperPros.size = 'small';
      }
      return formGroup.value.map(item => {
        var componentRef = item.component;
        if (componentRef && !(componentRef instanceof Array)) {
          if (componentRef.name === 'a-form') {
            if (pageConfig?.canExpand) {
              // @ts-ignore
              return h('a-collapse-panel', {}, h(BasicForm, { ...componentRef.props, ref: componentRef.props.modelName }, formSlots));
            } else {
              // @ts-ignore
              return h(BasicForm, { ...componentRef.props, ref: componentRef.props.modelName }, formSlots);
            }
          } else {
            const component = resolveComponent(componentRef.name);
            return h(
              resolveComponent(pageConfig?.canExpand ? 'a-collapse-panel' : 'a-card'),
              { ...wrapperPros },
              h(component, { ...componentRef.props, ref: componentRef.props.modelName }, () => []),
            );
          }
        } else if (componentRef && componentRef instanceof Array) {
          return h(resolveComponent(pageConfig?.canExpand ? 'a-collapse-panel' : 'a-card'), { ...wrapperPros }, () =>
            h(resolveComponent('a-tabs'), {}, () =>
              componentRef.map((child, index) => {
                const childComponent: Component = resolveComponent(child.name) as Component;
                return h(
                  resolveComponent('a-tab-pane'),
                  { tab: child.title || index, key: index, disabled: child.disabled && child.disabled() },
                  () =>
                    child.disabled && child.disabled()
                      ? []
                      : [h(childComponent, { ...child.props, ref: child.props.modelName }, () => child.slots || {})],
                );
              }),
            ),
          );
        } else {
          return <div>无内容</div>;
        }
      });
    };
    const slots: any = {
      rightFooter: () => (
        <div>
          <a-space>
            {pageConfig.operations.map((operation: any) => {
              const buttonSlots: any = {};
              if (operation.icon) {
                buttonSlots.icon = () => <Icon icon={operation.icon} />;
              }
              if (operation.text) {
                buttonSlots.default = () => operation.text;
              }
              const hideButton = isBoolean(operation.hide) ? operation.hide : isFunction(operation.hide) ? operation.hide() : false;
              switch (operation.theme) {
                case 'save':
                  if (!buttonSlots.icon) {
                    buttonSlots.icon = () => <Icon icon={'ant-design:save-outlined'} />;
                  }
                  if (!buttonSlots.default) {
                    buttonSlots.default = () => '保存';
                  }
                  return hideButton ? (
                    <span />
                  ) : (
                    <a-button
                      {...{
                        type: operation.type || 'default',
                        onClick: () => {
                          validate().then(result => {
                            operation.click(result);
                          });
                        },
                      }}
                      v-slots={buttonSlots}
                    ></a-button>
                  );
                case 'update':
                  if (!buttonSlots.icon) {
                    buttonSlots.icon = () => <Icon icon={'ant-design:check-outlined'} />;
                  }
                  if (!buttonSlots.default) {
                    buttonSlots.default = () => '更新';
                  }
                  return hideButton ? (
                    <span />
                  ) : (
                    <a-button
                      {...{
                        type: operation.type || 'default',
                        onClick: () => {
                          validate().then(result => {
                            operation.click(result);
                          });
                        },
                      }}
                      v-slots={buttonSlots}
                    ></a-button>
                  );
                default:
                  return hideButton ? (
                    <span />
                  ) : (
                    <a-button
                      {...{
                        type: operation.type || 'default',
                        onClick: () => {
                          if (operation.skipValidate) {
                            operation.click();
                          } else {
                            validate().then(result => {
                              operation.click(result);
                            });
                          }
                        },
                      }}
                    >
                      {operation.title}
                    </a-button>
                  );
              }
            })}
          </a-space>
        </div>
      ),
      default: () => {
        if (pageConfig?.canExpand) {
          return (
            <div>
              <a-collapse value={activeNames} onchange={handleChange} v-slots={{ default: () => renderChild() }} />
            </div>
          );
        } else {
          return (
            <div>
              <a-card
                {...{
                  props: {
                    shadow: 'never',
                  },
                  title: props.containerType === 'router' ? (resourceCategoryId.value ? '编辑资源分类' : '新增资源分类') : null,
                  bordered: false,
                  size: 'small',
                }}
                v-slots={{ default: () => renderChild() }}
              ></a-card>
            </div>
          );
        }
      },
    };
    return {
      // pageControl,
      resourceCategoryId,
      saveOrUpdate,
      formGroup,
      pageConfig,
      slots,
      resourceCategory,
    };
  },
  methods: {},
  render() {
    if (this.containerType === 'modal' || this.containerType === 'drawer') {
      // this.slots.actions = this.slots.rightFooter;
      delete this.slots.rightFooter;
      return <a-card {...this.pageConfig} v-slots={this.slots} />;
    } else {
      return (
        <PageWrapper
          {...{
            props: {
              title: this.pageConfig?.title || '编辑',
            },
          }}
          v-slots={this.slots}
        />
      );
    }
  },
});
</script>
