<template>
  <div class="app-container-fixed user-management-container">
    <div class="user-management-filter">
      <el-form ref="usersFilterForm" size="small" inline :model="usersFilterForm.data" :rules="usersFilterForm.rules">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="usersFilterForm.data.username" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="usersFilterForm.data.nickName" />
        </el-form-item>
        <el-form-item label="角色" prop="roleIdList">
          <el-select v-model="usersFilterForm.data.roleIdList" collapse-tags multiple filterable placeholder="请选择">
            <el-option
              v-for="item in roleTree.data"
              :key="item.id"
              :label="item.code"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="启用" prop="enableFlag">
          <el-radio-group v-model="usersFilterForm.data.enableFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="listUsers">搜索</el-button>
          <el-button @click="() => this.$refs.usersFilterForm.resetFields()">重置</el-button>
          <el-button type="info" @click="addUserForm.showDialog = true">添加用户</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="user-management-user">
      <el-tab-pane label="用户列表">
        <el-table
          ref="userListTable"
          height="calc(100vh - 221px)"
          :data="userListTable.data"
          highlight-current-row
          @current-change="userListTableClick"
        >
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="username" label="用户名" width="160px" fixed />
          <el-table-column property="nickName" label="昵称" width="298px" fixed />
          <el-table-column property="sex" label="性别" width="60px" />
          <el-table-column property="enableFlag" label="启用" width="60px">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.enableFlag" @change="flag => updateEnable(flag, scope.$index, scope.row)" />
            </template>
          </el-table-column>
          <el-table-column property="updateTime" label="更新时间" width="150px" />
          <el-table-column property="updateUser" label="更新人" width="160px" />
          <el-table-column property="createTime" label="创建时间" width="150px" />
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <el-button size="mini" @click="updateUser(scope.$index, scope.row)"><el-icon class="el-icon-edit" /></el-button>
              <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)"><el-icon class="el-icon-delete" /></el-button>
            </template>
          </el-table-column>
          <el-table-column property="phone" label="电话" width="150px" show-overflow-tooltip />
          <el-table-column property="mail" label="邮箱" width="150px" show-overflow-tooltip />
          <el-table-column property="loginIp" label="登录IP" width="150px" />
          <el-table-column property="loginDate" label="登录时间" width="150px" />
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          :page-size="userListTable.page.pageSize"
          :total="userListTable.page.total"
          :current-page.sync="userListTable.page.pageNumber"
          @current-change="pageUsers"
        />
      </el-tab-pane>
    </el-tabs>
    <el-tabs type="border-card" class="user-management-role">
      <el-tab-pane label="角色绑定">
        <el-tree
          v-if="userListTable.currClick"
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
    <el-dialog title="更新用户" :visible.sync="updateUserForm.showDialog" width="30%">
      <el-form
        ref="updateUserForm"
        label-width="100px"
        size="small"
        :model="updateUserForm.data"
        :rules="updateUserForm.rule"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="updateUserForm.data.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="updateUserForm.data.password" placeholder="如不需要修改密码请置空" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="updateUserForm.data.nickName" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="updateUserForm.data.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="updateUserForm.data.mail" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="updateUserForm.data.sex">
            <el-radio label="女" />
            <el-radio label="男" />
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.updateUserForm.resetFields()">重置</el-button>
          <el-button @click="updateUserForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateUserCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="添加用户" :visible.sync="addUserForm.showDialog" width="30%">
      <el-form
        ref="addUserForm"
        label-width="100px"
        size="small"
        :model="addUserForm.data"
        :rules="addUserForm.rule"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addUserForm.data.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addUserForm.data.password" show-password />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="addUserForm.data.nickName" />
        </el-form-item>
        <el-form-item label="启用" prop="enableFlag">
          <el-switch v-model="addUserForm.data.enableFlag" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="addUserForm.data.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="addUserForm.data.mail" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="addUserForm.data.sex">
            <el-radio label="女" />
            <el-radio label="男" />
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.addUserForm.resetFields()">重置</el-button>
          <el-button @click="addUserForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="addUserCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  addRoleUser,
  addUser,
  deleteUser,
  listRole,
  listUserRolesVoByUserIds,
  pageUserRolesVo,
  removeRoleUser,
  updateUser,
  updateUserEnable
} from '@/api/system';

export default {
  name: 'VUserManagement',
  data() {
    return {
      usersFilterForm: {
        data: {
          username: null,
          nickName: null,
          roleIdList: [],
          enableFlag: null
        },
        rules: {}
      },
      userListTable: {
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
      updateUserForm: {
        // 元数据引用
        row: null,
        data: {
          // 元数据id
          id: null,
          username: null,
          password: null,
          nickName: null,
          phone: null,
          mail: null,
          sex: null
        },
        showDialog: false,
        rule: {
          username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          nickName: [{required: true, message: '请输入昵称', trigger: 'blur'}]
        }
      },
      addUserForm: {
        data: {
          username: null,
          password: '123456',
          nickName: null,
          phone: null,
          mail: null,
          sex: null,
          enableFlag: true
        },
        showDialog: false,
        rule: {
          username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
          nickName: [{required: true, message: '请输入昵称', trigger: 'blur'}],
          password: [{required: true, message: '请输入密码', trigger: 'blur'}],
          sex: [{required: true, message: '请选择性别', trigger: 'blur'}]
        }
      }
    };
  },
  created() {
    this.listUsers();
    this.loadRoleTree();
  },
  methods: {
    listUsers() {
      this.pageUsers(1).then(() => {
        this.userListTable.page.pageNumber = 1;
      });
    },
    pageUsers(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.usersFilterForm.data,
        pageNumber: currPage,
        pageSize: this.userListTable.page.pageSize
      };
      // 查询用户列表
      return pageUserRolesVo(data).then(res => {
        this.userListTable.data = res.data.records;
        this.userListTable.page.total = res.data.total;
      });
    },
    // 修改用户后刷新列表该行内容
    refreshUser(row) {
      return listUserRolesVoByUserIds([row.id]).then(res => {
        Object.keys(res.data[0]).forEach(k => {
          row[k] = res.data[0][k];
        });
      });
    },
    updateEnable(flag, index, row) {
      updateUserEnable({id: row.id, enableFlag: flag})
        .then(res => {
          if (res.data) {
            this.refreshUser(row);
            return;
          }
          // 没修改成功则刷回原值
          row.enableFlag = !flag;
        })
        .catch(() => { row.enableFlag = !flag; });
    },
    updateUser(index, row) {
      this.updateUserForm.showDialog = true;
      // 存放一个引用，用于修改后刷新内容
      this.updateUserForm.row = row;
      // 保证表单初始值是data中的初始值
      this.$nextTick(() => {
        Object.keys(this.updateUserForm.data).forEach(k => {
          this.updateUserForm.data[k] = row[k];
        });
      });
    },
    updateUserCommit() {
      this.$refs.updateUserForm.validate((valid) => {
        if (valid) {
          updateUser(this.updateUserForm.data).then(res => {
            if (res.data) {
              this.updateUserForm.showDialog = false;
              this.refreshUser(this.updateUserForm.row);
            }
          });
        }
      });
    },
    addUserCommit() {
      this.$refs.addUserForm.validate((valid) => {
        if (valid) {
          addUser(this.addUserForm.data).then(res => {
            if (res.data) {
              this.addUserForm.showDialog = false;
              // 从后端刷新列表
              this.listUsers();
            }
          });
        }
      });
    },
    deleteUser(index, row) {
      deleteUser([row.id]).then(res => {
        if (res.data) {
          this.userListTable.data.splice(index, 1);
        }
      });
    },
    loadRoleTree() {
      // 查询role列表
      listRole().then(res => {
        this.roleTree.data = res.data;
      });
    },
    userListTableClick(curr, old) {
      this.userListTable.currClick = curr;
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
        addRoleUser([{id: this.userListTable.currClick.id, roleId: obj.id}]).then(res => {
          if (res.data) {
            // 添加到对象中
            this.userListTable.currClick.roles.push(obj.code);
          }
        });
        return;
      }
      removeRoleUser([{id: this.userListTable.currClick.id, roleId: obj.id}]).then(res => {
        if (res.data) {
          // 删除该角色
          const index = this.userListTable.currClick.roles.indexOf(obj.code);
          this.userListTable.currClick.roles.splice(index, 1);
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.user-management-container {

  .user-management-filter {
    height: 51px;
    overflow: auto;
  }

  .user-management-user {
    display: inline-block;
    height: calc(100vh - 131px);
    width: 85%;
    vertical-align: top;
    margin-right: 1%;
  }

  .user-management-role {
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
  }
}
</style>
