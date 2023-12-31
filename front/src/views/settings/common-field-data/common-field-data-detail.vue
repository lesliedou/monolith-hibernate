<script lang="tsx">
import { getCurrentInstance, ref, reactive, h, resolveComponent, toRef, Component } from 'vue';
import { useRoute } from 'vue-router';
import config from './config/detail-config';
import { defineComponent } from 'vue';
import { ICommonFieldData } from '@/models/settings/common-field-data.model';

import ServerProvider from '@/api-service/index';
import { PageWrapper } from '@/components/Page';
import Icon from '@/components/Icon/Icon.vue';
import { getButtonConfig } from '@/components/Button/button-config';
import { Description } from '@/components/Description';
import DescList from '@/components/DescList/src/DescList.vue';

export default defineComponent({
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
  name: 'SystemCommonFieldDataDetail',
  props: {
    entityId: {
      type: Number || String,
      default: 0,
    },
    containerType: {
      type: String,
      default: 'router',
    },
  },
  components: {
    DescList,
  },
  async setup(props) {
    const ctx = getCurrentInstance()?.proxy;
    const route = useRoute();
    // const pageControl = control(ctx);
    // const formConfig = pageControl.formRender();
    const activeNames = ref<any[]>([]);
    const handleChange = val => {
      activeNames.value = val;
      ctx?.$emit('change', activeNames.value);
    };
    const apiService = ctx?.$apiService as typeof ServerProvider;
    const commonFieldData = reactive<ICommonFieldData>({});
    let entityId: any = '';
    if (props.containerType === 'router') {
      entityId = route.params?.entityId;
    } else {
      entityId = props.entityId;
    }
    if (entityId) {
      const data = await apiService.settings.commonFieldDataService.find(Number(entityId));
      if (data) {
        Object.assign(commonFieldData, data);
      }
    }
    const formItemsConfig = reactive(config.fields);
    //获得关联表属性。
    const pageConfig = reactive({
      active: '0',
      operations: [
        {
          theme: 'close',
          click: () => {
            console.log('close');
          },
        },
      ],
      canExpand: false,
      title: '详情',
    });
    const formGroup = reactive([
      {
        title: '通用字段数据详情',
        operation: [],
        component: {
          name: 'a-desc',
          props: {
            schema: formItemsConfig,
            isEdit: () => false,
            // formConfig,
            labelWidth: '120px',
            data: commonFieldData,
            column: 1,
          },
        },
      },
    ]);
    const getXGridSlots = component => {
      if (component.recordActions) {
        const buttons = reactive(component.recordActions.map(action => getButtonConfig(action)));
        buttons.filter(button => button.icon).forEach(button => (button.slots = { default: () => <Icon icon={button.icon} /> }));
        buttons.filter(button => !button.icon).forEach(button => (button.slots = { default: () => button.text }));
        return {
          recordAction: row => (
            <div>
              <a-space>
                {buttons.map(button => {
                  return (
                    <a-button
                      {...{
                        type: button.type || 'primary',
                        shape: button.shape || 'circle',
                        title: button.text || button.name,
                        onClick: () => {
                          if (button.onClick) {
                            button.onClick(row.row, button.name);
                          } else {
                            console.log(button.name, 'onClick事件未定义！');
                          }
                        },
                      }}
                      v-slots={button.slots}
                    ></a-button>
                  );
                })}
              </a-space>
            </div>
          ),
        };
      } else {
        return {
          recordAction: () => <div />,
        };
      }
    };
    const renderChild = () => {
      const wrapperPros: any = {};
      if (!pageConfig?.canExpand) {
        wrapperPros.bordered = false;
        wrapperPros.size = 'small';
      }

      return formGroup.map(item => {
        var componentRef = toRef(item, 'component').value;
        if (componentRef && !(componentRef instanceof Array)) {
          if (componentRef.name === 'a-desc') {
            if (pageConfig?.canExpand) {
              return (
                <a-collapse-panel>
                  <Description {...componentRef.props} />
                </a-collapse-panel>
              );
            } else {
              return <Description {...componentRef.props} />;
            }
          } else {
            const component = resolveComponent(componentRef.name);
            return h(
              resolveComponent(pageConfig?.canExpand ? 'a-collapse-panel' : 'a-card'),
              { ...wrapperPros },
              h(component, componentRef.props),
            );
          }
        } else if (componentRef && componentRef instanceof Array) {
          return h(resolveComponent(pageConfig?.canExpand ? 'a-collapse-panel' : 'a-card'), { ...wrapperPros }, () =>
            h(resolveComponent('a-tabs'), {}, () =>
              componentRef.map((child, index) => {
                return h(resolveComponent('a-tab-pane'), { tab: child.title || index, key: index }, () =>
                  h(resolveComponent(child?.name) as Component, child.props, child?.name === 'vxe-grid' ? getXGridSlots(child) : {}),
                );
              }),
            ),
          );
        } else {
          return <div>无内容</div>;
        }
      });
    };
    const slots = {
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
              switch (operation.theme) {
                case 'save':
                  if (!buttonSlots.icon) {
                    buttonSlots.icon = () => <Icon icon={'ant-design:save-outlined'} />;
                  }
                  if (!buttonSlots.default) {
                    buttonSlots.default = () => '保存';
                  }
                  return (
                    <a-button
                      {...{
                        type: 'primary',
                        onClick: operation.click,
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
                  return (
                    <a-button
                      {...{
                        type: 'primary',
                        onClick: operation.click,
                      }}
                      v-slots={buttonSlots}
                    ></a-button>
                  );
                default:
                  return (
                    <a-button
                      {...{
                        type: 'primary',
                        onClick: operation.click,
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
            <a-card
              {...{
                props: {
                  shadow: 'never',
                },
                bordered: false,
                size: 'small',
              }}
              v-slots={{ default: () => renderChild() }}
            ></a-card>
          );
        }
      },
    };
    return {
      // formConfig,
      pageConfig,
      // pageControl,
      formGroup,
      slots,
    };
  },
  methods: {},
  render() {
    if (this.containerType === 'modal' || this.containerType === 'drawer') {
      return <a-card {...this.pageConfig} v-slots={this.slots} />;
    } else {
      return (
        <PageWrapper
          {...{
            props: {
              title: this.pageConfig?.title || '详情',
            },
          }}
          v-slots={this.slots}
        />
      );
    }
  },
});
</script>
