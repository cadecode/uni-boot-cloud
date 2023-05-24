<template>
  <div class="api-management-container">
    <div class="api-management-filter">
      <el-form ref="apisFilterForm" size="small" inline :model="apisFilterForm.data" :rules="apisFilterForm.rules">
        <el-form-item label="接口路径" prop="url">
          <el-input v-model="apisFilterForm.data.url" />
        </el-form-item>
        <el-form-item label="角色" prop="roleIdList">
          <el-select v-model="apisFilterForm.data.roleIdList" collapse-tags multiple filterable placeholder="请选择">
            <el-option
              v-for="item in roleTree.data"
              :key="item.id"
              :label="item.code"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="listApis">搜索</el-button>
          <el-button @click="() => this.$refs.apisFilterForm.resetFields()">重置</el-button>
          <el-button type="info" @click="addApiForm.showDialog = true">添加API</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="api-management-api">
      <el-tab-pane label="API列表">
        <el-table
          ref="apiListTable"
          height="calc(100vh - 221px)"
          :data="apiListTable.data"
          highlight-current-row
          @current-change="apiListTableClick"
        >
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="url" label="接口路径" width="280px" fixed />
          <el-table-column property="description" label="描述" width="298px" fixed />
          <el-table-column property="updateTime" label="更新时间" width="150px" />
          <el-table-column property="updateUser" label="更新人" width="160px" />
          <el-table-column property="createTime" label="创建时间" width="150px" />
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <el-button size="mini" @click="updateApi(scope.$index, scope.row)">
                <el-icon class="el-icon-edit" />
              </el-button>
              <el-button size="mini" type="danger" @click="deleteApi(scope.$index, scope.row)">
                <el-icon class="el-icon-delete" />
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          :page-size="apiListTable.page.pageSize"
          :total="apiListTable.page.total"
          :current-page.sync="apiListTable.page.pageNumber"
          @current-change="pageApis"
        />
      </el-tab-pane>
    </el-tabs>
    <el-tabs type="border-card" class="api-management-role">
      <el-tab-pane label="角色绑定">
        <el-tree
          v-if="apiListTable.currClick"
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
    <el-dialog title="更新API" :visible.sync="updateApiForm.showDialog" width="30%">
      <el-form
        ref="updateApiForm"
        label-width="100px"
        size="small"
        :model="updateApiForm.data"
        :rules="updateApiForm.rule"
      >
        <el-form-item label="接口路径" prop="url">
          <el-input v-model="updateApiForm.data.url" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="updateApiForm.data.description" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.updateApiForm.resetFields()">重置</el-button>
          <el-button @click="updateApiForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateApiCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="添加API" :visible.sync="addApiForm.showDialog" width="30%">
      <el-form
        ref="addApiForm"
        label-width="100px"
        size="small"
        :model="addApiForm.data"
        :rules="addApiForm.rule"
      >
        <el-form-item label="接口路径" prop="url">
          <el-autocomplete v-model="addApiForm.data.url" :fetch-suggestions="listUrlSuggest" @select="handleUrlSelect" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="addApiForm.data.description" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.addApiForm.resetFields()">重置</el-button>
          <el-button @click="addApiForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="addApiCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  addApi,
  addRoleApi,
  deleteApi,
  listApiRolesVoByApiIds,
  listApiSwaggerVo,
  listRole,
  pageApiRolesVo,
  removeRoleApi,
  updateApi
} from '@/api/system';

export default {
  name: 'ApiManagement',
  data() {
    return {
      apisFilterForm: {
        data: {
          url: null,
          roleIdList: []
        },
        rules: {}
      },
      apiListTable: {
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
      updateApiForm: {
        // 元数据引用
        row: null,
        data: {
          // 元数据id
          id: null,
          url: null,
          description: null
        },
        showDialog: false,
        rule: {
          url: [{required: true, message: '请输入接口路径', trigger: 'blur'}],
          description: [{required: true, message: '请输入描述', trigger: 'blur'}]
        }
      },
      addApiForm: {
        data: {
          url: null,
          description: null
        },
        showDialog: false,
        urlSuggestList: null,
        rule: {
          url: [{required: true, message: '请输入接口路径', trigger: 'blur'}],
          description: [{required: true, message: '请输入描述', trigger: 'blur'}]
        }
      }
    };
  },
  created() {
    this.listApis();
    this.loadRoleTree();
  },
  methods: {
    listApis() {
      this.pageApis(1).then(() => {
        this.apiListTable.page.pageNumber = 1;
      });
    },
    pageApis(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.apisFilterForm.data,
        pageNumber: currPage,
        pageSize: this.apiListTable.page.pageSize
      };
      // 查询API列表
      return pageApiRolesVo(data).then(res => {
        this.apiListTable.data = res.data.records;
        this.apiListTable.page.total = res.data.total;
      });
    },
    // 修改API后刷新列表该行内容
    refreshApi(row) {
      return listApiRolesVoByApiIds([row.id]).then(res => {
        Object.keys(res.data[0]).forEach(k => {
          row[k] = res.data[0][k];
        });
      });
    },
    updateApi(index, row) {
      this.updateApiForm.showDialog = true;
      // 存放一个引用，用于修改后刷新内容
      this.updateApiForm.row = row;
      // 保证表单初始值是data中的初始值
      this.$nextTick(() => {
        Object.keys(this.updateApiForm.data).forEach(k => {
          this.updateApiForm.data[k] = row[k];
        });
      });
    },
    updateApiCommit() {
      this.$refs.updateApiForm.validate((valid) => {
        if (valid) {
          updateApi(this.updateApiForm.data).then(res => {
            if (res.data) {
              this.updateApiForm.showDialog = false;
              this.refreshApi(this.updateApiForm.row);
            }
          });
        }
      });
    },
    addApiCommit() {
      this.$refs.addApiForm.validate((valid) => {
        if (valid) {
          addApi(this.addApiForm.data).then(res => {
            if (res.data) {
              this.addApiForm.showDialog = false;
              // 从后端刷新列表
              this.listApis();
            }
          });
        }
      });
    },
    deleteApi(index, row) {
      deleteApi([row.id]).then(res => {
        if (res.data) {
          this.apiListTable.data.splice(index, 1);
        }
      });
    },
    loadRoleTree() {
      // 查询role列表
      listRole().then(res => {
        this.roleTree.data = res.data;
      });
    },
    apiListTableClick(curr, old) {
      this.apiListTable.currClick = curr;
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
        addRoleApi([{id: this.apiListTable.currClick.id, roleId: obj.id}]).then(res => {
          if (res.data) {
            // 添加到对象中
            this.apiListTable.currClick.roles.push(obj.code);
          }
        });
        return;
      }
      removeRoleApi([{id: this.apiListTable.currClick.id, roleId: obj.id}]).then(res => {
        if (res.data) {
          // 删除该角色
          const index = this.apiListTable.currClick.roles.indexOf(obj.code);
          this.apiListTable.currClick.roles.splice(index, 1);
        }
      });
    },
    listUrlSuggest(queryString, cb) {
      if (this.addApiForm.urlSuggestList) {
        cb(this.addApiForm.urlSuggestList.filter(o => o.url.includes(queryString)));
        return;
      }
      listApiSwaggerVo().then(res => {
        res.data.forEach(o => { o.value = o.description ? `${o.url} | ${o.description}` : o.url; });
        this.addApiForm.urlSuggestList = res.data;
        cb(res.data);
      });
    },
    handleUrlSelect(item) {
      this.addApiForm.data.url = item.url;
      this.addApiForm.data.description = item.description;
    }
  }
};
</script>
<style lang="scss" scoped>
.api-management-container {
  height: calc(100vh - 60px);
  margin: 10px 20px 0;
  overflow: hidden;

  .api-management-filter {
    height: 51px;
    overflow: auto;
  }

  .api-management-api {
    display: inline-block;
    height: calc(100vh - 131px);
    width: 85%;
    vertical-align: top;
    margin-right: 1%;
  }

  .api-management-role {
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

  ::v-deep .el-autocomplete .el-input {
    width: 436px !important;
  }
}
</style>
