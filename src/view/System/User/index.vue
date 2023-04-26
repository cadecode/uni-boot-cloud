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
          <el-table-column type="selection" />
          <el-table-column property="id" label="ID" />
          <el-table-column property="username" label="用户名" />
          <el-table-column property="nickName" label="昵称" />
          <el-table-column property="enableFlag" label="启用状态">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.enableFlag" @change="flag => updateEnable(flag, scope.row)" />
            </template>
          </el-table-column>
          <el-table-column property="updateTime" label="更新时间" />
          <el-table-column property="updateUser" label="更新人" />
          <el-table-column property="createTime" label="创建时间" />
          <el-table-column label="操作">
            <el-button size="mini">编辑</el-button>
            <el-button size="mini" type="danger">删除</el-button>
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
      <el-tab-pane label="添加用户">
        2
      </el-tab-pane>
    </el-tabs>
    <el-tabs type="border-card" class="user-management-role">
      <el-tab-pane label="角色绑定">
        <el-tree
          :props="roleTree.props"
          :load="loadRoleTree"
          show-checkbox
          lazy
        />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import {listRole, pageUserRolesVo, updateUserEnable} from '@/api/system';

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
    updateEnable(flag, row) {
      updateUserEnable({id: row.id, enableFlag: flag})
        .then(res => {
          // 没修改成功则恢复原值
          if (!res.data) {
            row.enableFlag = !flag;
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
