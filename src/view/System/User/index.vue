<template>
  <div class="user-management-container">
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
        <el-form-item label="启用状态" prop="enableFlag">
          <el-radio-group v-model="usersFilterForm.data.enableFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="listUsers">搜索</el-button>
          <el-button @click="() => this.$refs['usersFilterForm'].resetFields()">重置</el-button>
          <el-button @click="addUserForm.showDialog = true">添加用户</el-button>
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
        >
          <el-table-column property="id" label="ID" />
          <el-table-column property="username" label="用户名" />
          <el-table-column property="nickName" label="昵称" />
          <el-table-column property="enableFlag" label="启用状态">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.enableFlag" @change="flag => updateEnable(flag, scope.$index, scope.row)" />
            </template>
          </el-table-column>
          <el-table-column property="updateTime" label="更新时间" />
          <el-table-column property="updateUser" label="更新人" />
          <el-table-column property="createTime" label="创建时间" />
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="updateUser(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="deleteUser(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-descriptions :column="1">
                <el-descriptions-item label="性别">{{ scope.row.sex }}</el-descriptions-item>
                <el-descriptions-item label="电话">{{ scope.row.phone }}</el-descriptions-item>
                <el-descriptions-item label="邮箱">{{ scope.row.mail }}</el-descriptions-item>
                <el-descriptions-item label="登录IP">{{ scope.row.loginIp }}</el-descriptions-item>
                <el-descriptions-item label="登录时间">{{ scope.row.loginDate }}</el-descriptions-item>
              </el-descriptions>
            </template>
          </el-table-column>
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
        <el-tree :props="roleTree.props" :load="loadRoleTree" show-checkbox lazy />
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
          <el-button @click="() => this.$refs['updateUserForm'].resetFields()">重置</el-button>
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
          <el-button @click="() => this.$refs['addUserForm'].resetFields()">重置</el-button>
          <el-button @click="addUserForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="addUserCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {listRole, pageUserRolesVo, updateUserEnable, updateUser, addUser, deleteUser} from '@/api/system';

export default {
  name: 'UserManagement',
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
        data: [],
        page: {
          total: 0,
          pageNumber: 1,
          pageSize: 10
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
    updateEnable(flag, index, row) {
      updateUserEnable({id: row.id, enableFlag: flag})
        .then(res => {
          // 没修改成功则刷回原值
          if (!res.data) {
            row.enableFlag = !flag;
          }
        });
    },
    updateUser(index, row) {
      this.updateUserForm.showDialog = true;
      this.updateUserForm.row = row;
      // 保证表单初始值是data中的初始值
      this.$nextTick(() => {
        Object.keys(this.updateUserForm.data).forEach(k => {
          this.updateUserForm.data[k] = row[k];
        });
      });
    },
    updateUserCommit() {
      this.$refs['updateUserForm'].validate((valid) => {
        if (valid) {
          updateUser(this.updateUserForm.data).then(res => {
            if (res.data) {
              // 修改内容刷到列表中，不包括更新时间/人
              Object.keys(this.updateUserForm.data).forEach(k => {
                this.updateUserForm.row[k] = this.updateUserForm.data[k];
              });
              this.updateUserForm.showDialog = false;
            }
          });
        }
      });
    },
    addUserCommit() {
      this.$refs['addUserForm'].validate((valid) => {
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
    loadRoleTree(node, resolve) {
      if (node.level === 0) {
        // 查询role列表
        listRole().then(res => {
          this.roleTree.data = res.data;
          this.roleTree.data.forEach(o => {
            o.isLeaf = true;
          });
          resolve(this.roleTree.data);
        });
        return;
      }
      resolve([]);
    }
  }
};
</script>
<style lang="scss" scoped>
.user-management-container {
  height: calc(100vh - 60px);
  margin: 10px 20px 0;
  overflow: hidden;

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
