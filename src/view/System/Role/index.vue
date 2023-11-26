<template>
  <div class="app-container  role-management-container">
    <div class="role-management-filter">
      <el-form ref="rolesFilterForm" size="small" inline :model="rolesFilterForm.data" :rules="rolesFilterForm.rules">
        <el-form-item label="角色代码" prop="code">
          <el-input v-model="rolesFilterForm.data.code" />
        </el-form-item>
        <el-form-item label="角色名" prop="name">
          <el-input v-model="rolesFilterForm.data.name" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="pageRoles(1)">搜索</el-button>
          <el-button @click="() => this.$refs.rolesFilterForm.resetFields()">重置</el-button>
          <el-button type="info" @click="addRoleForm.showDialog = true">添加角色</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="15">
      <el-col :xs="24" :sm="24" :md="24" :lg="18" :xl="18">
        <el-tabs type="border-card" class="role-management-role">
          <el-tab-pane label="角色列表">
            <el-table
              ref="roleListTable"
              height="calc(100vh - 221px)"
              :data="roleListTable.data"
              highlight-current-row
              @current-change="roleListTableClick"
            >
              <el-table-column property="id" label="ID" width="170px" fixed />
              <el-table-column property="code" label="角色代码" width="140px" fixed />
              <el-table-column property="name" label="角色名" width="180px" fixed />
              <el-table-column property="updateTime" label="更新时间" width="150px" />
              <el-table-column property="updateUser" label="更新人" width="160px" />
              <el-table-column property="createTime" label="创建时间" width="150px" />
              <el-table-column label="操作" width="180px">
                <template v-slot="scope">
                  <el-button size="mini" @click="updateRole(scope.$index, scope.row)">
                    <el-icon class="el-icon-edit" />
                  </el-button>
                  <el-button size="mini" type="danger" @click="deleteRole(scope.$index, scope.row)">
                    <el-icon class="el-icon-delete" />
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column property="description" label="描述" width="300px" show-overflow-tooltip />
            </el-table>
            <el-pagination
              layout="prev, pager, next"
              :page-size="roleListTable.page.pageSize"
              :total="roleListTable.page.total"
              :current-page.sync="roleListTable.page.pageNumber"
              @current-change="pageRoles"
            />
          </el-tab-pane>
        </el-tabs>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="6" :xl="6">
        <el-tabs type="border-card" class="role-management-bind">
          <el-tab-pane label="菜单绑定" class="role-management-bind-menu">
            <el-tree
              v-if="roleListTable.currClick"
              ref="menuTree"
              :data="menuTree.data"
              :props="menuTree.props"
              node-key="id"
              show-checkbox
              check-strictly
              default-expand-all
              @check="menuCheck"
            />
            <el-empty v-else description="请先点击表格选择一行数据" />
          </el-tab-pane>
          <el-tab-pane label="API绑定" class="role-management-bind-api">
            <el-tree
              v-if="roleListTable.currClick"
              ref="apiTree"
              :data="apiTree.data"
              :props="apiTree.props"
              node-key="id"
              show-checkbox
              @check="apiCheck"
            />
            <el-empty v-else description="请先点击表格选择一行数据" />
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
    <el-dialog title="更新角色" :visible.sync="updateRoleForm.showDialog" width="30%">
      <el-form
        ref="updateRoleForm"
        label-width="100px"
        size="small"
        :model="updateRoleForm.data"
        :rules="updateRoleForm.rule"
      >
        <el-form-item label="角色代码" prop="code">
          <el-input v-model="updateRoleForm.data.code" />
        </el-form-item>
        <el-form-item label="角色名" prop="name">
          <el-input v-model="updateRoleForm.data.name" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="updateRoleForm.data.description" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.updateRoleForm.resetFields()">重置</el-button>
          <el-button @click="updateRoleForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateRoleCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="添加角色" :visible.sync="addRoleForm.showDialog" width="30%">
      <el-form
        ref="addRoleForm"
        label-width="100px"
        size="small"
        :model="addRoleForm.data"
        :rules="addRoleForm.rule"
      >
        <el-form-item label="角色代码" prop="code">
          <el-input v-model="addRoleForm.data.code" />
        </el-form-item>
        <el-form-item label="角色名" prop="name">
          <el-input v-model="addRoleForm.data.name" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="addRoleForm.data.description" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.addRoleForm.resetFields()">重置</el-button>
          <el-button @click="addRoleForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="addRoleCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  addRole,
  addRoleApi,
  addRoleMenu,
  deleteRole,
  listRoleUnionVoByRoleIds,
  pageApiRolesVo,
  pageMenuRolesVo,
  pageRoleUnionVo,
  removeRoleApi,
  removeRoleMenu,
  updateRole
} from '@/api/system';

export default {
  name: 'RoleManagement',
  data() {
    return {
      rolesFilterForm: {
        data: {
          code: null,
          name: null
        },
        rules: {}
      },
      roleListTable: {
        currClick: null,
        data: [],
        page: {
          total: 0,
          pageNumber: 1,
          pageSize: 12
        }
      },
      menuTree: {
        data: [],
        props: {
          label: 'menuName',
          children: 'children',
          isLeaf: 'leafFlag'
        }
      },
      apiTree: {
        data: [],
        props: {
          label: 'url',
          children: 'children'
        }
      },
      updateRoleForm: {
        // 元数据引用
        row: null,
        data: {
          // 元数据id
          id: null,
          code: null,
          name: null,
          description: null
        },
        showDialog: false,
        rule: {
          code: [{required: true, message: '请输入角色代码', trigger: 'blur'}],
          name: [{required: true, message: '请输入角色名', trigger: 'blur'}],
          description: [{required: true, message: '请输入描述', trigger: 'blur'}]
        }
      },
      addRoleForm: {
        data: {
          code: null,
          name: null,
          description: null
        },
        showDialog: false,
        rule: {
          code: [{required: true, message: '请输入角色代码', trigger: 'blur'}],
          name: [{required: true, message: '请输入角色名', trigger: 'blur'}],
          description: [{required: true, message: '请输入描述', trigger: 'blur'}]
        }
      }
    };
  },
  created() {
    this.pageRoles(1);
    this.loadMenuTree();
    this.loadApiTree();
  },
  methods: {
    pageRoles(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.rolesFilterForm.data,
        pageNumber: currPage,
        pageSize: this.roleListTable.page.pageSize
      };
      // 查询角色列表
      pageRoleUnionVo(data).then(res => {
        this.roleListTable.data = res.data.records;
        this.roleListTable.page.total = res.data.total;
        if (currPage === 1) {
          this.roleListTable.page.pageNumber = 1;
        }
      });
    },
    // 修改角色后刷新列表该行内容
    refreshRole(row) {
      return listRoleUnionVoByRoleIds([row.id]).then(res => {
        Object.keys(res.data[0]).forEach(k => {
          row[k] = res.data[0][k];
        });
      });
    },
    updateRole(index, row) {
      this.updateRoleForm.showDialog = true;
      // 存放一个引用，用于修改后刷新内容
      this.updateRoleForm.row = row;
      // 保证表单初始值是data中的初始值
      this.$nextTick(() => {
        Object.keys(this.updateRoleForm.data).forEach(k => {
          this.updateRoleForm.data[k] = row[k];
        });
      });
    },
    updateRoleCommit() {
      this.$refs.updateRoleForm.validate((valid) => {
        if (valid) {
          updateRole(this.updateRoleForm.data).then(res => {
            if (res.data) {
              this.updateRoleForm.showDialog = false;
              this.refreshRole(this.updateRoleForm.row);
            }
          });
        }
      });
    },
    addRoleCommit() {
      this.$refs.addRoleForm.validate((valid) => {
        if (valid) {
          addRole(this.addRoleForm.data).then(res => {
            if (res.data) {
              this.addRoleForm.showDialog = false;
              // 从后端刷新列表
              this.pageRoles(1);
            }
          });
        }
      });
    },
    deleteRole(index, row) {
      deleteRole([row.id]).then(res => {
        if (res.data) {
          this.roleListTable.data.splice(index, 1);
        }
      });
    },
    roleListTableClick(curr, old) {
      this.roleListTable.currClick = curr;
      if (curr) {
        this.$nextTick(() => {
          this.$refs.menuTree.setCheckedKeys(curr.menus);
          this.$refs.apiTree.setCheckedKeys(curr.apis);
        });
      }
    },
    loadMenuTree() {
      pageMenuRolesVo({pageNumber: 0, pageSize: 0, hiddenFlag: false}).then(res => {
        this.menuTree.data = res.data.records;
      });
    },
    loadApiTree() {
      pageApiRolesVo({pageNumber: 0, pageSize: 0}).then(res => {
        this.apiTree.data = res.data.records;
      });
    },
    menuCheck(obj, state) {
      const checked = state.checkedNodes.includes(obj);
      if (checked) {
        addRoleMenu([{roleId: this.roleListTable.currClick.id, id: obj.id}]).then(res => {
          if (res.data) {
            // 添加到对象中
            this.roleListTable.currClick.menus.push(obj.id);
          }
        });
        return;
      }
      removeRoleMenu([{roleId: this.roleListTable.currClick.id, id: obj.id}]).then(res => {
        if (res.data) {
          this.roleListTable.currClick.menus = this.roleListTable.currClick.menus.filter(o => o !== obj.id);
        }
      });
    },
    apiCheck(obj, state) {
      const checked = state.checkedNodes.includes(obj);
      if (checked) {
        addRoleApi([{roleId: this.roleListTable.currClick.id, id: obj.id}]).then(res => {
          if (res.data) {
            // 添加到对象中
            this.roleListTable.currClick.apis.push(obj.id);
          }
        });
        return;
      }
      removeRoleApi([{roleId: this.roleListTable.currClick.id, id: obj.id}]).then(res => {
        if (res.data) {
          this.roleListTable.currClick.apis = this.roleListTable.currClick.apis.filter(o => o !== obj.id);
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.role-management-container {

  .role-management-filter {
    min-height: 51px;
    overflow: auto;
  }

  .role-management-role {
    height: calc(100vh - 131px);
  }

  @media only screen and (max-width: 1199px) {
    .role-management-role {
      margin-bottom: 20px;
    }
  }

  .role-management-bind {
    height: calc(100vh - 131px);

    .el-tab-pane {
      height: calc(100vh - 200px);
      overflow: auto;
    }

    .role-management-bind-api {
      ::v-deep .el-tree-node__expand-icon {
        display: none;
      }
    }
  }

  ::v-deep .el-autocomplete .el-input {
    width: 436px !important;
  }
}
</style>
