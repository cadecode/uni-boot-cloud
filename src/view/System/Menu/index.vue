<template>
  <div class="app-container  menu-management-container">
    <div class="menu-management-filter">
      <el-form
        ref="menusFilterForm"
        size="small"
        inline
        :model="menusFilterForm.data"
        :rules="menusFilterForm.rules"
      >
        <el-form-item label="路由名" prop="routeName">
          <el-input v-model="menusFilterForm.data.routeName" />
        </el-form-item>
        <el-form-item label="菜单名" prop="menuName">
          <el-input v-model="menusFilterForm.data.menuName" />
        </el-form-item>
        <el-form-item label="角色" prop="roleIdList">
          <el-select
            v-model="menusFilterForm.data.roleIdList"
            collapse-tags
            multiple
            filterable
            placeholder="请选择"
          >
            <el-option
              v-for="item in roleTree.data"
              :key="item.id"
              :label="item.code"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="启用" prop="enableFlag">
          <el-radio-group v-model="menusFilterForm.data.enableFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="pageMenus(1)">搜索</el-button>
          <el-button @click="() => this.$refs.menusFilterForm.resetFields()">重置</el-button>
          <el-button type="info" @click="addMenu">添加菜单/路由</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="15">
      <el-col :xs="24" :sm="24" :md="24" :lg="21" :xl="21">
        <el-tabs v-model="listTableTabName" type="border-card" class="menu-management-menu" @tab-click="handleListTableTabClick">
          <el-tab-pane name="menuListTab" label="侧边菜单列表">
            <el-table
              ref="menuListTable"
              height="calc(100vh - 221px)"
              :data="menuListTable.data"
              highlight-current-row
              row-key="id"
              default-expand-all
              :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
              @row-click="listTableClick"
            >
              <el-table-column property="orderNum" label="NO." width="200px" fixed />
              <el-table-column property="id" label="ID" width="170px" fixed />
              <el-table-column property="routeName" label="路由名" width="170px" fixed />
              <el-table-column property="menuName" label="菜单名" width="170px" fixed />
              <el-table-column property="enableFlag" label="启用" width="60px">
                <template v-slot="scope">
                  <el-switch
                    v-model="scope.row.enableFlag"
                    @change="flag => updateEnable(flag, scope.$index, scope.row)"
                  />
                </template>
              </el-table-column>
              <el-table-column property="updateTime" label="更新时间" width="150px" />
              <el-table-column property="updateUser" label="更新人" width="160px" />
              <el-table-column property="createTime" label="创建时间" width="150px" />
              <el-table-column label="操作" width="180px">
                <template v-slot="scope">
                  <el-button size="mini" @click="updateMenu(scope.$index, scope.row)">
                    <el-icon class="el-icon-edit" />
                  </el-button>
                  <el-button size="mini" type="danger" @click="deleteMenu(scope.$index, scope.row)">
                    <el-icon class="el-icon-delete" />
                  </el-button>
                  <el-button
                    v-if="!scope.row.leafFlag"
                    size="mini"
                    type="info"
                    @click="addMenu(scope.$index, scope.row)"
                  >
                    <el-icon class="el-icon-plus" />
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column property="parentId" label="父级ID" width="170px" />
              <el-table-column property="routePath" label="路由路径" width="300px" show-overflow-tooltip />
              <el-table-column property="componentPath" label="组件路径" width="300px" show-overflow-tooltip />
              <el-table-column property="leafFlag" label="是否页面" :formatter="(row, col, cell) => cell?'是':'否'" />
              <el-table-column property="cacheFlag" label="是否缓存" :formatter="(row, col, cell) => cell?'是':'否'" />
              <el-table-column property="icon" label="图标" width="150px" />
            </el-table>
          </el-tab-pane>
          <el-tab-pane name="hiddenRouteListTab" label="内部路由列表">
            <el-table
              ref="hiddenRouteListTable"
              height="calc(100vh - 221px)"
              :data="hiddenRouteListTable.data"
              highlight-current-row
              row-key="id"
              @row-click="listTableClick"
            >
              <el-table-column property="id" label="ID" width="170px" fixed />
              <el-table-column property="routeName" label="路由名" width="170px" fixed />
              <el-table-column property="menuName" label="菜单名" width="170px" fixed />
              <el-table-column property="enableFlag" label="启用" width="60px">
                <template v-slot="scope">
                  <el-switch
                    v-model="scope.row.enableFlag"
                    @change="flag => updateEnable(flag, scope.$index, scope.row)"
                  />
                </template>
              </el-table-column>
              <el-table-column property="updateTime" label="更新时间" width="150px" />
              <el-table-column property="updateUser" label="更新人" width="160px" />
              <el-table-column property="createTime" label="创建时间" width="150px" />
              <el-table-column label="操作" width="180px">
                <template v-slot="scope">
                  <el-button size="mini" @click="updateMenu(scope.$index, scope.row)">
                    <el-icon class="el-icon-edit" />
                  </el-button>
                  <el-button size="mini" type="danger" @click="deleteMenu(scope.$index, scope.row)">
                    <el-icon class="el-icon-delete" />
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column property="routePath" label="路由路径" width="300px" show-overflow-tooltip />
              <el-table-column property="componentPath" label="组件路径" width="300px" show-overflow-tooltip />
              <el-table-column property="cacheFlag" label="是否缓存" :formatter="(row, col, cell) => cell?'是':'否'" />
            </el-table>
            <el-pagination
              layout="prev, pager, next"
              :page-size="hiddenRouteListTable.page.pageSize"
              :total="hiddenRouteListTable.page.total"
              :current-page.sync="hiddenRouteListTable.page.pageNumber"
              @current-change="pageMenus"
            />
          </el-tab-pane>
        </el-tabs>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="3" :xl="3">
        <el-tabs type="border-card" class="menu-management-role">
          <el-tab-pane label="角色绑定">
            <el-tree
              v-if="listTableCurrClick"
              ref="roleTree"
              :data="roleTree.data"
              :props="roleTree.props"
              node-key="code"
              show-checkbox
              @check="roleCheck"
            />
            <el-empty v-else description="请先点击表格选择一行数据" />
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
    <el-dialog
      :title="isHiddenRouteClicked() ? '更新内部路由' : '更新侧边菜单'"
      :visible.sync="updateMenuForm.showDialog"
      width="30%"
    >
      <el-form
        ref="updateMenuForm"
        label-width="100px"
        size="small"
        :model="updateMenuForm.data"
        :rules="updateMenuForm.rule"
      >
        <el-form-item label="路由名称" prop="routeName">
          <el-input v-model="updateMenuForm.data.routeName" />
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="updateMenuForm.data.menuName" />
        </el-form-item>
        <el-form-item label="路由路径" prop="routePath">
          <el-input v-model="updateMenuForm.data.routePath" />
        </el-form-item>
        <el-form-item label="组件路径" prop="componentPath">
          <el-input v-model="updateMenuForm.data.componentPath" />
        </el-form-item>
        <el-form-item label="是否缓存" prop="cacheFlag">
          <el-radio-group v-model="updateMenuForm.data.cacheFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <template v-if="!isHiddenRouteClicked()">
          <el-form-item label="图标" prop="icon">
            <el-input v-model="updateMenuForm.data.icon" />
          </el-form-item>
          <el-form-item label="排序" prop="orderNum">
            <el-input v-model="updateMenuForm.data.orderNum" type="number" />
          </el-form-item>
        </template>
        <el-form-item>
          <el-button @click="() => this.$refs.updateMenuForm.resetFields()">重置</el-button>
          <el-button @click="updateMenuForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateMenuCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog
      :title="isHiddenRouteClicked() ? '添加内部路由' : '添加侧边菜单'"
      :visible.sync="addMenuForm.showDialog"
      width="30%"
    >
      <el-form
        ref="addMenuForm"
        label-width="100px"
        size="small"
        :model="addMenuForm.data"
        :rules="addMenuForm.rule"
      >
        <el-form-item label="路由名称" prop="routeName">
          <el-input v-model="addMenuForm.data.routeName" />
        </el-form-item>
        <el-form-item label="父级 ID" prop="parentId">
          <el-autocomplete
            v-model="addMenuForm.data.parentId"
            :fetch-suggestions="listParentMenuSuggest"
            placeholder="不填写时为顶级菜单"
            @select="handleParentMenuSelect"
          />
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="addMenuForm.data.menuName" />
        </el-form-item>
        <el-form-item label="路由路径" prop="routePath">
          <el-input v-model="addMenuForm.data.routePath" />
        </el-form-item>
        <el-form-item label="组件路径" prop="componentPath">
          <el-input v-model="addMenuForm.data.componentPath" placeholder="不填写时跟随路由路径，外链时置空" />
        </el-form-item>
        <el-form-item label="是否页面" prop="leafFlag">
          <el-radio-group v-model="addMenuForm.data.leafFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false" :disabled="isHiddenRouteClicked()">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否缓存" prop="cacheFlag">
          <el-radio-group v-model="addMenuForm.data.cacheFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <template v-if="!isHiddenRouteClicked()">
          <el-form-item label="图标" prop="icon">
            <el-input v-model="addMenuForm.data.icon" />
          </el-form-item>
          <el-form-item label="排序" prop="orderNum">
            <el-input v-model="addMenuForm.data.orderNum" type="number" />
          </el-form-item>
        </template>
        <el-form-item>
          <el-button @click="() => this.$refs.addMenuForm.resetFields()">重置</el-button>
          <el-button @click="addMenuForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="addMenuCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  addMenu,
  addRoleMenu,
  deleteMenu,
  listMenuRolesVoByMenuIds,
  listRole,
  listMenuParentSuggest,
  pageMenuRolesVo,
  removeRoleMenu,
  updateMenu,
  updateMenuEnable
} from '@/api/system';
import {isExternalUrl} from '@/util/common';

export default {
  name: 'MenuManagement',
  data() {
    return {
      menusFilterForm: {
        data: {
          routeName: null,
          menuName: null,
          roleIdList: [],
          enableFlag: null
        },
        rules: {}
      },
      // 选中 tab name
      listTableTabName: 'menuListTab',
      // 表格选中的行
      listTableCurrClick: null,
      // 侧边栏列表
      menuListTable: {
        data: []
      },
      // 内部路由列表
      hiddenRouteListTable: {
        data: [],
        page: {
          total: 0,
          pageNumber: 1,
          pageSize: 12
        }
      },
      roleTree: {
        data: [],
        props: {
          label: 'code',
          children: 'children'
        }
      },
      updateMenuForm: {
        // 元数据引用
        row: null,
        data: {
          // 元数据id
          id: null,
          parentId: null,
          routeName: null,
          routePath: null,
          componentPath: null,
          menuName: null,
          icon: null,
          orderNum: null,
          cacheFlag: null
        },
        showDialog: false,
        rule: {
          routeName: [{required: true, message: '请输入路由名', trigger: 'blur'}],
          routePath: [{required: true, message: '请输入路由路径', trigger: 'blur'}],
          componentPath: [{
            validator: (rule, value, callback) => {
              const routePath = this.updateMenuForm.data.routePath;
              // 外链时置空
              if (isExternalUrl(routePath)) {
                this.updateMenuForm.data.componentPath = null;
                callback();
                return;
              }
              if (this.updateMenuForm.data.componentPath) {
                callback();
                return;
              }
              if (routePath) {
                this.updateMenuForm.data.componentPath = routePath;
                callback();
                return;
              }
              callback(new Error('请输入组件路径或路由路径'));
            }, trigger: 'blur'
          }],
          menuName: [{required: true, message: '请输入菜单名', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}],
          cacheFlag: [{required: true, message: '请选择是否缓存', trigger: 'blur'}]
        }
      },
      addMenuForm: {
        // 元数据引用
        row: null,
        data: {
          parentId: null,
          routeName: null,
          routePath: null,
          componentPath: null,
          menuName: null,
          leafFlag: null,
          icon: null,
          orderNum: null,
          enableFlag: true,
          cacheFlag: null
        },
        showDialog: false,
        rule: {
          routeName: [{required: true, message: '请输入路由名', trigger: 'blur'}],
          routePath: [{required: true, message: '请输入路由路径', trigger: 'blur'}],
          componentPath: [{
            validator: (rule, value, callback) => {
              const routePath = this.addMenuForm.data.routePath;
              // 外链时置空
              if (isExternalUrl(routePath)) {
                this.addMenuForm.data.componentPath = null;
                callback();
                return;
              }
              if (this.addMenuForm.data.componentPath) {
                callback();
                return;
              }
              if (routePath) {
                this.addMenuForm.data.componentPath = routePath;
                callback();
                return;
              }
              callback(new Error('请输入组件路径或路由路径'));
            }, trigger: 'blur'
          }],
          menuName: [{required: true, message: '请输入菜单名', trigger: 'blur'}],
          leafFlag: [{required: true, message: '请选择是否页面', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}],
          cacheFlag: [{required: true, message: '请选择是否缓存', trigger: 'blur'}]
        },
        parentMenuSuggestList: null
      }
    };
  },
  created() {
    this.pageMenus(1);
    this.loadRoleTree();
  },
  methods: {
    isHiddenRouteClicked() {
      return this.listTableTabName === 'hiddenRouteListTab';
    },
    pageMenus(currPage) {
      const hiddenFlag = this.isHiddenRouteClicked();
      // 分页插件回调传递当前页号
      // hiddenFlag false 时，侧边栏菜单数据不需要分页，pageSize 传 0
      const data = {
        ...this.menusFilterForm.data,
        hiddenFlag,
        pageNumber: currPage,
        pageSize: hiddenFlag ? this.hiddenRouteListTable.page.pageSize : 0
      };
      // 查询用户列表
      pageMenuRolesVo(data).then(res => {
        // 内部路由菜单数据
        if (hiddenFlag) {
          this.hiddenRouteListTable.data = res.data.records;
          this.hiddenRouteListTable.page.total = res.data.total;
          if (currPage === 1) {
            this.hiddenRouteListTable.page.pageNumber = 1;
          }
          return;
        }

        // 侧边栏菜单数据
        this.menuListTable.data = res.data.records;
      });
    },
    refreshMenu(row) {
      return listMenuRolesVoByMenuIds([row.id]).then(res => {
        Object.keys(res.data[0]).forEach(k => {
          row[k] = res.data[0][k];
        });
      });
    },
    updateEnable(flag, index, row) {
      updateMenuEnable({id: row.id, enableFlag: flag})
        .then(res => {
          if (res.data) {
            this.refreshMenu(row);
            return;
          }
          // 没修改成功则刷回原值
          row.enableFlag = !flag;
        })
        .catch(() => {
          row.enableFlag = !flag;
        });
    },
    updateMenu(index, row) {
      this.updateMenuForm.showDialog = true;
      // 存放一个引用，用于修改后刷新内容
      this.updateMenuForm.row = row;
      // 保证表单初始值是data中的初始值
      this.$nextTick(() => {
        Object.keys(this.updateMenuForm.data).forEach(k => {
          this.updateMenuForm.data[k] = row[k];
        });
      });
    },
    updateMenuCommit() {
      this.$refs.updateMenuForm.validate((valid) => {
        if (!valid) {
          return;
        }
        updateMenu(this.updateMenuForm.data).then(res => {
          if (res.data) {
            this.refreshMenu(this.updateMenuForm.row);
            this.addMenuForm.parentMenuSuggestList = null;
            this.updateMenuForm.showDialog = false;
          }
        });
      });
    },
    addMenu(index, row) {
      // 清理 parentId 以便添加根菜单
      this.addMenuForm.data.parentId = null;
      // 存放当前行的引用，添加根菜单时为 undefined
      this.addMenuForm.row = row;
      this.addMenuForm.showDialog = true;
      this.$nextTick(() => {
        // 若是添加子菜单，注入当前行的 id 作为新菜单的父级ID
        if (row) {
          this.addMenuForm.data.parentId = row.id;
        }
      });
    },
    addMenuCommit() {
      this.$refs.addMenuForm.validate((valid) => {
        if (!valid) {
          return;
        }
        addMenu({...this.addMenuForm.data, hiddenFlag: this.isHiddenRouteClicked()}).then(res => {
          if (res.data) {
            this.pageMenus(1);
            this.addMenuForm.showDialog = false;
            this.addMenuForm.parentMenuSuggestList = null;
          }
        });
      });
    },
    deleteMenu(index, row) {
      const del = () => {
        const hiddenFlag = this.isHiddenRouteClicked();
        deleteMenu([row.id]).then(res => {
          if (res.data) {
            // 内部路由数据
            if (hiddenFlag) {
              const findIndex = this.hiddenRouteListTable.data.findIndex(o => o.id === row.id);
              if (findIndex !== -1) {
                this.hiddenRouteListTable.data.splice(findIndex, 1);
              }
              this.listTableCurrClick = null;
              return;
            }

            // 侧边菜单数据
            this.pageMenus(1);
            this.listTableCurrClick = null;
            this.addMenuForm.parentMenuSuggestList = null;
          }
        });
      };
      // 非页面需要删除确认
      if (!row.leafFlag) {
        this.$messageBox.confirm('删除菜单会删除其全部子菜单，是否继续？', '删除确认', {
          type: 'warning',
          confirmButtonText: '确认',
          cancelButtonText: '取消'
        }).then(() => {
          del();
        });
        return;
      }
      del();
    },
    loadRoleTree() {
      // 查询role列表
      listRole().then(res => {
        this.roleTree.data = res.data;
      });
    },
    listTableClick(curr, old) {
      this.listTableCurrClick = curr;
      if (curr) {
        this.$nextTick(() => {
          this.$refs.roleTree.setCheckedKeys(curr.roles);
        });
      }
    },
    roleCheck(obj, state) {
      const checked = state.checkedNodes.includes(obj);
      // 启用该角色
      if (checked) {
        addRoleMenu([{id: this.listTableCurrClick.id, roleId: obj.id}]).then(res => {
          if (res.data) {
            // 添加到对象中
            this.listTableCurrClick.roles.push(obj.code);
          }
        });
        return;
      }
      removeRoleMenu([{id: this.listTableCurrClick.id, roleId: obj.id}]).then(res => {
        if (res.data) {
          // 删除该角色
          const index = this.listTableCurrClick.roles.indexOf(obj.code);
          this.listTableCurrClick.roles.splice(index, 1);
        }
      });
    },
    handleListTableTabClick() {
      // 清空选中
      this.listTableCurrClick = null;
      this.$refs.menuListTable.setCurrentRow();
      this.$refs.hiddenRouteListTable.setCurrentRow();

      // 当内部路由表格为空时，切换 tab 时加载一次
      if (this.isHiddenRouteClicked() && this.hiddenRouteListTable.data.length === 0) {
        this.pageMenus(1);
      }
    },
    listParentMenuSuggest(queryString, cb) {
      if (this.addMenuForm.parentMenuSuggestList) {
        cb(this.addMenuForm.parentMenuSuggestList.filter(o => !queryString || o.value.includes(queryString)));
        return;
      }
      listMenuParentSuggest().then(res => {
        res.data.forEach(o => { o.value = `${o.id} | ${o.menuName} | ${o.routeName}`; });
        this.addMenuForm.parentMenuSuggestList = res.data;
        cb(this.addMenuForm.parentMenuSuggestList);
      });
    },
    handleParentMenuSelect(item) {
      this.addMenuForm.data.parentId = item.id;
    }
  }
};
</script>
<style lang="scss" scoped>
.menu-management-container {

  .menu-management-filter {
    min-height: 51px;
    overflow: auto;
  }

  .menu-management-menu {
    height: calc(100vh - 131px);
  }

  @media only screen and (max-width: 1199px) {
    .menu-management-menu {
      margin-bottom: 20px;
    }
  }

  .menu-management-role {
    height: calc(100vh - 131px);

    .el-tab-pane {
      height: calc(100vh - 200px);
      overflow: auto;
    }

    ::v-deep .el-tree-node__expand-icon {
      display: none;
    }
  }

  ::v-deep .el-autocomplete .el-input {
    width: 436px !important;
  }
}
</style>
