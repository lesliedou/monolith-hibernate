<script lang="tsx">
import { useRoute, useRouter } from 'vue-router';
import { getCurrentInstance, reactive, computed, defineComponent, h, ref, resolveComponent, Component } from 'vue';
import config from './config/edit-config';
import { Dictionary, IDictionary } from '@/models/settings/dictionary.model';

import ServerProvider from '@/api-service/index';
import { BasicForm } from '@/components/Form';
import { PageWrapper } from '@/components/Page';
import Icon from '@/components/Icon/Icon.vue';
import { getButtonConfig } from '@/components/Button/button-config';
import { isBoolean, isFunction } from '@/utils/is';
import { message } from 'ant-design-vue';
import { useGo } from '@/hooks/web/usePage';
import { useMultipleTabStore } from '@/store/modules/multipleTab';

export default defineComponent({
  // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
  name: 'SystemDictionaryEdit',
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
      items: apiService.settings.commonFieldDataService.retrieve,
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
    let dictionaryId = ref('');
    if (props.containerType === 'router') {
      dictionaryId.value = route.params?.entityId as string;
    } else {
      dictionaryId.value = props.entityId as string;
    }
    const dictionary = ref<IDictionary>(new Dictionary());
    dictionary.value = Object.assign(dictionary.value, props.baseData);
    if (dictionaryId.value) {
      const data = await apiService.settings.dictionaryService.find(Number(dictionaryId.value));
      if (data) {
        dictionary.value = Object.assign(dictionary.value, data);
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
    const submitButtonTitlePrefix = dictionaryId.value ? '更新' : '保存';
    const saveOrUpdateApi = dictionaryId.value
      ? apiService.settings.dictionaryService.update
      : apiService.settings.dictionaryService.create;
    const saveOrUpdate = () => {
      validate()
        .then(result => {
          if (result) {
            dictionary.value = Object.assign(dictionary.value, result);
            saveOrUpdateApi(dictionary.value)
              .then(res => {
                dictionary.value = Object.assign(dictionary.value, res);
                dictionaryId.value = dictionary.value.id + '';
                message.success(submitButtonTitlePrefix + '成功！');
                if (props.containerType === 'router') {
                  const { fullPath } = route; //获取当前路径
                  tabStore.closeTabByKey(fullPath, router).then(() => {
                    go('/settings/dictionary/' + dictionaryId.value + '/edit', true);
                  });
                } else {
                  if (!props.savedOpen) {
                    ctx?.$emit('refresh', { update: true, containerType: props.containerType });
                  }
                  (ctx?.$refs['BASE_ENTITY'] as any).setFieldsValue(dictionary.value);
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
            return !!dictionary.value.id;
          },
          type: 'primary',
          theme: 'save',
          click: saveOrUpdate,
        },
        {
          hide: () => {
            return !dictionary.value.id;
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
        title: props.containerType === 'router' ? '数据字典' : null,
        operation: [],
        component: {
          name: 'a-form',
          props: {
            modelName: 'BASE_ENTITY',
            model: dictionary.value,
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
      {
        title: '关联表',
        operation: [],
        component: [
          {
            name: 'vxe-grid',
            title: '字典项列表列表',
            props: {
              modelName: 'items',
              data: dictionary.items,
              columns: config.itemsColumns,
              border: true,
              showOverflow: true,
              editConfig: {
                trigger: 'click',
                mode: 'row',
                activeMethod({ row, rowIndex, column, columnIndex }) {
                  console.log('activeMethod', row, rowIndex, column, columnIndex);
                  return true;
                },
              },
              rowConfig: {
                useKey: true,
              },
              onEditClosed: ({ $table, row, column }) => {
                console.log('$table', $table);
                if ($table.isUpdateByRow(row)) {
                  row.loading = true;
                  apiService.settings.dictionaryService
                    .update({ id: row.id, [column.field]: row[column.field] }, [row.id], [column.field])
                    .then(data => {
                      $table.reloadRow(row, data, column.field);
                      message.success('保存成功！');
                    })
                    .finally(() => {
                      row.loading = false;
                    });
                }
              },
              fieldMapToTime: [],
              compact: true,
              size: 'default',
              disabled: !isEdit.value,
              toolbarConfig: {
                buttons: [
                  { code: 'insert_actived', name: '新增', icon: 'fa fa-plus' },
                  { code: 'remove', name: '删除', icon: 'fa fa-trash-o' },
                ],
                // 表格右上角自定义按钮
                tools: [
                  // { code: 'myPrint', name: '自定义打印' }
                ],
                import: false,
                export: false,
                print: false,
                custom: false,
              },
            },
            slots: {
              dragBtn: () => {
                return h('span', { class: 'drag-btn' }, h('i', { class: 'vxe-icon-edit' }, []));
              },
            },
          },
        ],
      },
    ]);
    let sortable1: any;

    const rowDrop = () => {
      const $table = xTable1.value;
      sortable1 = Sortable.create($table.$el.querySelector('.body--wrapper>.vxe-table--body tbody'), {
        handle: '.drag-btn',
        onEnd: sortableEvent => {
          const newIndex = sortableEvent.newIndex as number;
          const oldIndex = sortableEvent.oldIndex as number;
          const currRow = demo1.tableData.splice(oldIndex, 1)[0];
          demo1.tableData.splice(newIndex, 0, currRow);
        },
      });
    };
    let initTime: any;
    nextTick(() => {
      // 加载完成之后在绑定拖动事件
      initTime = setTimeout(() => {
        rowDrop();
      }, 500);
    });
    onUnmounted(() => {
      clearTimeout(initTime);
      if (sortable1) {
        sortable1.destroy();
      }
    });
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
                      : [
                          h(
                            childComponent,
                            { ...child.props, ref: child.props.modelName },
                            child?.name === 'vxe-grid' ? getXGridSlots(child) : child.slots || {},
                          ),
                        ],
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
                  title: props.containerType === 'router' ? (dictionaryId.value ? '编辑数据字典' : '新增数据字典') : null,
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
      dictionaryId,
      saveOrUpdate,
      formGroup,
      pageConfig,
      slots,
      rowDrop,
      dictionary,
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
