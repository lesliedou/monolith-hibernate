<template>
  <!-- begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！-->
  <div>
    <a-card v-if="searchFormConfig.toggleSearchStatus && !searchFormConfig.disabled" title="高级搜索" class="bc-list-search-form-card">
      <template #extra>
        <a-space>
          <a-button
            type="default"
            @click="showSearchFormSetting"
            preIcon="ant-design:setting-outlined"
            shape="circle"
            size="small"
          ></a-button>
        </a-space>
      </template>
      <search-form :config="searchFormConfig" @formSearch="formSearch" @close="handleToggleSearch" />
    </a-card>
    <a-card :bordered="false" class="bc-list-result-card" :bodyStyle="{ 'padding-top': '1px' }">
      <a-row :gutter="16">
        <a-col :span="filterTreeConfig.filterTreeSpan" v-if="filterTreeConfig.filterTreeSpan > 0">
          <a-tree
            style="border: #bbcedd 1px solid; height: 100%"
            v-model="filterTreeConfig.checkedKeys"
            :expandedKeys="filterTreeConfig.expandedKeys"
            :autoExpandParent="filterTreeConfig.autoExpandParent"
            :selectedKeys="filterTreeConfig.selectedKeys"
            :treeData="filterTreeConfig.treeFilterData"
            @select="onSelect"
            @expand="onExpand"
          />
        </a-col>
        <a-col :span="24 - filterTreeConfig.filterTreeSpan">
          <card-list ref="cardList" v-bind="cardListOptions" @getMethod="cardListFetchMethod">
            <template #header_left>
              <a-row class="toolbar_buttons_xgrid" :gutter="16">
                <a-col :lg="2" :md="2" :sm="4" v-if="filterTreeConfig.treeFilterData.length > 0">
                  <span class="table-page-search-submitButtons">
                    <a-button
                      type="primary"
                      :icon="filterTreeConfig.filterTreeSpan > 0 ? 'pic-center' : 'pic-right'"
                      @click="switchFilterTree"
                    ></a-button>
                  </span>
                </a-col>
                <a-col v-if="!searchFormConfig.toggleSearchStatus && !searchFormConfig.disabled">
                  <a-input-search
                    placeholder="请输入关键字"
                    v-model:value="searchValue"
                    allow-clear
                    @search="formSearch"
                    enterButton
                    ref="searchInput"
                  >
                    <template #addonAfter v-if="searchFormConfig.allowSwitch">
                      <a-button type="primary" @click="handleToggleSearch" style="margin-left: -2px">
                        <UpOutlined />
                      </a-button>
                    </template>
                  </a-input-search>
                </a-col>
              </a-row>
            </template>
            <template #loadMore>
              <div v-if="loading" style="text-align: center; margin-top: 12px; height: 32px; line-height: 32px">
                <a-spin />
              </div>
              <div v-else style="text-align: center; margin-top: 12px; height: 32px; line-height: 32px">
                <!--              <a-button @click="handleLoadMore">加载更多</a-button>-->
              </div>
            </template>
          </card-list>
        </a-col>
      </a-row>
      <BasicModal v-bind="modalConfig" @register="registerModal" @cancel="closeModal" @ok="okModal">
        <component
          :is="modalConfig.componentName"
          @cancel="closeModalOrDrawer"
          @refresh="formSearch"
          v-bind="modalConfig"
          ref="modalComponentRef"
        />
      </BasicModal>
      <BasicDrawer v-bind="drawerConfig" @register="registerDrawer" @cancel="closeDrawer" @ok="okDrawer">
        <component
          :is="drawerConfig.componentName"
          @cancel="closeModalOrDrawer"
          @refresh="formSearch"
          v-bind="drawerConfig"
          ref="drawerComponentRef"
        />
      </BasicDrawer>
    </a-card>
  </div>
</template>

<script lang="ts" src="./u-report-file-list.component.ts"></script>
<style>
.toolbar_buttons_xgrid {
  margin-left: 5px !important;
}
.table-page-search-submitButtons {
  display: inline-block !important;
}
.vxe-tools--wrapper {
  padding-right: 12px;
}
</style>
