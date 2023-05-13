<template>
  <div class="menu-management-container">
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
          <el-button type="primary" @click="listMenus">搜索</el-button>
          <el-button @click="() => this.$refs.menusFilterForm.resetFields()">重置</el-button>
          <el-button type="info" @click="addMenu">添加根菜单</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="menu-management-menu">
      <el-tab-pane label="菜单列表">
        <el-table
          ref="menuListTable"
          height="calc(100vh - 221px)"
          :data="menuListTable.data"
          highlight-current-row
          row-key="id"
          lazy
          :load="loadMenuChildren"
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          @current-change="menuListTableClick"
        >
          <el-table-column property="orderNum" label="NO." width="200px" fixed />
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="routeName" label="路由名" width="160px" fixed />
          <el-table-column property="menuName" label="菜单名" width="160px" fixed />
          <el-table-column property="enableFlag" label="启用" width="60px">
            <template slot-scope="scope">
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
            <template slot-scope="scope">
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
          <el-table-column property="routePath" label="路由路径" width="300px" />
          <el-table-column property="componentPath" label="组件路径" width="300px" />
          <el-table-column property="leafFlag" label="是否页面" />
          <el-table-column property="icon" label="图标" width="150px" />
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          :page-size="menuListTable.page.pageSize"
          :total="menuListTable.page.total"
          :current-page.sync="menuListTable.page.pageNumber"
          @current-change="pageMenus"
        />
      </el-tab-pane>
    </el-tabs>
    <el-tabs type="border-card" class="menu-management-role">
      <el-tab-pane label="角色绑定">
        <el-tree
          v-if="menuListTable.currClick"
          ref="roleTree"
          :data="roleTree.data"
          :props="roleTree.props"
          node-key="code"
          show-checkbox
          @check="roleCheck"
        />
        <span v-else class="no-curr-text">请先点击表格选择一行数据</span>
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="更新菜单" :visible.sync="updateMenuForm.showDialog" width="30%">
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
        <el-form-item label="图标" prop="icon">
          <el-input v-model="updateMenuForm.data.icon" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="updateMenuForm.data.orderNum" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.updateMenuForm.resetFields()">重置</el-button>
          <el-button @click="updateMenuForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateMenuCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="添加菜单" :visible.sync="addMenuForm.showDialog" width="30%">
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
        <el-form-item v-if="addMenuForm.row" label="父级ID" prop="parentId">
          <el-input v-model="addMenuForm.data.parentId" />
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
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="addMenuForm.data.icon" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="addMenuForm.data.orderNum" />
        </el-form-item>
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
      menuListTable: {
        currClick: null,
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
          orderNum: null
        },
        showDialog: false,
        rule: {
          routeName: [{required: true, message: '请输入路由名', trigger: 'blur'}],
          routePath: [{required: true, message: '请输入路由路径', trigger: 'blur'}],
          componentPath: [{validator: (rule, value, callback) => {
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
          }, trigger: 'blur'}],
          menuName: [{required: true, message: '请输入菜单名', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}]
        }
      },
      addMenuForm: {
        // 元数据引用
        row: null,
        // 映射 id 和 children tree
        treeMap: {},
        data: {
          parentId: null,
          routeName: null,
          routePath: null,
          componentPath: null,
          menuName: null,
          leafFlag: null,
          icon: null,
          orderNum: null,
          enableFlag: true
        },
        showDialog: false,
        rule: {
          parentId: [{required: true, message: '请输入父级ID', trigger: 'blur'}],
          routeName: [{required: true, message: '请输入路由名', trigger: 'blur'}],
          routePath: [{required: true, message: '请输入路由路径', trigger: 'blur'}],
          componentPath: [{validator: (rule, value, callback) => {
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
          }, trigger: 'blur'}],
          menuName: [{required: true, message: '请输入菜单名', trigger: 'blur'}],
          leafFlag: [{required: true, message: '请选择是否页面', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}]
        }
      }
    };
  },
  created() {
    this.listMenus();
    this.loadRoleTree();
  },
  methods: {
    listMenus() {
      this.pageMenus(1).then(() => {
        this.menuListTable.page.pageNumber = 1;
        // 重置时清空展开内容
        this.$refs.menuListTable.layout.store.states.lazyTreeNodeMap = {};
        this.$refs.menuListTable.layout.store.states.treeData = {};
      });
    },
    pageMenus(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.menusFilterForm.data,
        pageNumber: currPage,
        pageSize: this.menuListTable.page.pageSize
      };
      // 查询用户列表
      return pageMenuRolesVo(data).then(res => {
        this.menuListTable.data = res.data.records;
        // 设置 hasChildren 辅助表格树形展示
        this.menuListTable.data.forEach(o => {
          o.hasChildren = !o.leafFlag;
        });
        this.menuListTable.page.total = res.data.total;
      });
    },
    // 修改用户后刷新列表该行内容
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
            this.updateMenuForm.showDialog = false;
            this.refreshMenu(this.updateMenuForm.row);
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
        addMenu(this.addMenuForm.data).then(res => {
          if (res.data) {
            // 若添加的是根菜单
            if (!this.addMenuForm.row) {
              this.listMenus();
              this.addMenuForm.showDialog = false;
              return;
            }
            const treeInfo = this.addMenuForm.treeMap[this.addMenuForm.row.id];
            if (treeInfo) {
              const {tree, treeNode, resolve} = treeInfo;
              // 重新 load 子菜单
              this.loadMenuChildren(tree, treeNode, resolve);
            }
            this.addMenuForm.showDialog = false;
          }
        });
      });
    },
    deleteMenu(index, row) {
      // TO 目前不能删除非页面的菜单
      if (!row.leafFlag) {
        this.$message.warning('目前不能删除非页面的菜单');
        return;
      }
      deleteMenu([row.id]).then(res => {
        if (res.data) {
          let findIndex = this.menuListTable.data.findIndex(o => o.id === row.id);
          if (findIndex !== -1) {
            this.menuListTable.data.splice(findIndex, 1);
          } else {
            // 不在顶级目录中中
            const store = this.$refs.menuListTable.layout.store;
            // 根据父级 id 找到子节点列表
            const treeNode = store.states.lazyTreeNodeMap[row.parentId];
            findIndex = treeNode.findIndex(o => o.id === row.id);
            treeNode.splice(findIndex, 1);
          }
        }
      });
    },
    loadMenuChildren(tree, treeNode, resolve) {
      // 存储树形关系备用
      this.addMenuForm.treeMap[tree.id] = {tree, treeNode, resolve};
      // pageSize 给大值，复用分页接口
      const data = {
        ...this.menusFilterForm.data,
        pageNumber: 0,
        pageSize: 0,
        parentId: tree.id
      };
      pageMenuRolesVo(data).then(res => {
        const records = res.data.records;
        records.forEach(o => {
          o.hasChildren = !o.leafFlag;
        });
        resolve(records);
      });
    },
    loadRoleTree() {
      // 查询role列表
      listRole().then(res => {
        this.roleTree.data = res.data;
        this.roleTree.data.forEach(o => {
          o.isLeaf = true;
        });
      });
    },
    menuListTableClick(curr, old) {
      this.menuListTable.currClick = curr;
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
        addRoleMenu([{id: this.menuListTable.currClick.id, roleId: obj.id}]).then(res => {
          if (res.data) {
            // 添加到对象中
            this.menuListTable.currClick.roles.push(obj.code);
          }
        });
        return;
      }
      removeRoleMenu([{id: this.menuListTable.currClick.id, roleId: obj.id}]).then(res => {
        if (res.data) {
          // 删除该角色
          const index = this.menuListTable.currClick.roles.indexOf(obj.code);
          this.menuListTable.currClick.roles.splice(index, 1);
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.menu-management-container {
  height: calc(100vh - 60px);
  margin: 10px 20px 0;
  overflow: hidden;

  .menu-management-filter {
    height: 51px;
    overflow: auto;
  }

  .menu-management-menu {
    display: inline-block;
    height: calc(100vh - 131px);
    width: 85%;
    vertical-align: top;
    margin-right: 1%;
  }

  .menu-management-role {
    display: inline-block;
    height: calc(100vh - 131px);
    width: 13%;

    .el-tab-pane {
      height: calc(100vh - 200px);
      overflow: auto;
    }

    ::v-deep .el-tree-node__expand-icon {
      display: none;
    }

    .no-curr-text {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      color: #909399;
      font-size: 14px;
      text-align: center;
    }
  }
}
</style>
