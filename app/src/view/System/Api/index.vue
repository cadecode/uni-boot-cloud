<template>
  <div class="app-container api-management-container">
    <div class="api-management-filter">
      <el-form ref="apisFilterForm" size="small" inline :model="apisFilterForm.data" :rules="apisFilterForm.rules">
        <el-form-item label="接口路径" prop="url">
          <el-input v-model="apisFilterForm.data.url" />
        </el-form-item>
        <el-form-item label="访问角色" prop="roleIdList">
          <el-select v-model="apisFilterForm.data.roleIdList" collapse-tags multiple filterable placeholder="请选择">
            <el-option
              v-for="item in roleTree.data"
              :key="item.id"
              :label="item._typeCode"
              :value="item.id"
            >
              <span style="float: left">{{ item.code }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.type }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="pageApis(1)">搜索</el-button>
          <el-button @click="() => this.$refs.apisFilterForm.resetFields()">重置</el-button>
          <el-button type="info" @click="addApiForm.showDialog = true">添加API</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-row :gutter="15">
      <el-col :xs="24" :sm="24" :md="24" :lg="21" :xl="21">
        <el-tabs type="border-card" class="api-management-api">
          <el-tab-pane label="API列表">
            <el-table
              ref="apiListTable"
              height="calc(100vh - 221px)"
              :data="apiListTable.data"
              highlight-current-row
              @current-change="apiListTableClick"
            >
              <el-table-column property="id" label="ID" width="170px" />
              <el-table-column property="url" label="接口路径" width="280px" />
              <el-table-column property="description" label="描述" width="298px" show-overflow-tooltip />
              <el-table-column property="updateTime" label="更新时间" width="150px" />
              <el-table-column property="updateUser" label="更新人" width="160px" />
              <el-table-column property="createTime" label="创建时间" width="150px" />
              <el-table-column label="操作" width="180px">
                <template v-slot="scope">
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
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="3" :xl="3">
        <el-tabs type="border-card" class="api-management-role">
          <el-tab-pane label="访问角色绑定">
            <el-tree
              v-if="apiListTable.currClick"
              ref="roleTree"
              :data="roleTree.data"
              :props="roleTree.props"
              node-key="_typeCode"
              show-checkbox
              @check="roleCheck"
            />
            <el-empty v-else description="请先点击表格选择一行数据" />
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
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
        <el-form-item label="服务" prop="serviceUrl">
          <base-dict-loader dict-type="serviceUrl" @load="(dict, defaultDict) => addApiForm.data.serviceUrl = defaultDict">
            <template v-slot="scope">
              <el-select v-model="addApiForm.data.serviceUrl" filterable placeholder="请选择" @change="handleServiceUrlChange">
                <el-option
                  v-for="item in scope.dictList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </template>
          </base-dict-loader>
        </el-form-item>
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
  listSwaggerDescVo,
  listRole,
  pageApiRolesVo,
  removeRoleApi,
  updateApi
} from '@/api/system';
import BaseDictLoader from '@/component/BaseDictLoader';
import {getRoleTypeCode, roleTypes} from '@/util/permission';

export default {
  name: 'ApiManagement',
  components: {BaseDictLoader},
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
          serviceUrl: null,
          url: null,
          description: null
        },
        showDialog: false,
        urlSuggestList: null,
        rule: {
          url: [{required: true, message: '请输入接口路径', trigger: 'blur'}],
          description: [{required: true, message: '请输入描述', trigger: 'blur'}],
          serviceUrl: [{required: true, message: '请选择服务', trigger: 'blur'}]
        },
        option: {
          serviceUrl: []
        }
      }
    };
  },
  created() {
    this.pageApis(1);
    this.loadRoleTree();
  },
  methods: {
    pageApis(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.apisFilterForm.data,
        pageNumber: currPage,
        pageSize: this.apiListTable.page.pageSize
      };
      // 查询API列表
      pageApiRolesVo(data).then(res => {
        this.apiListTable.data = res.data.records;
        this.apiListTable.page.total = res.data.total;
        if (currPage === 1) {
          this.apiListTable.page.pageNumber = 1;
        }
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
              this.pageApis(1);
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
      listRole({type: roleTypes.ROLE_TYPE_ACCESS}).then(res => {
        this.roleTree.data = res.data;
        if (this.roleTree.data && this.roleTree.data.length) {
          this.roleTree.data.forEach(o => {
            o._typeCode = getRoleTypeCode(o);
          });
        }
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
            this.apiListTable.currClick.roles.push(obj._typeCode);
          }
        });
        return;
      }
      removeRoleApi([{id: this.apiListTable.currClick.id, roleId: obj.id}]).then(res => {
        if (res.data) {
          // 删除该角色
          const index = this.apiListTable.currClick.roles.indexOf(obj._typeCode);
          this.apiListTable.currClick.roles.splice(index, 1);
        }
      });
    },
    listUrlSuggest(queryString, cb) {
      if (this.addApiForm.urlSuggestList) {
        cb(this.addApiForm.urlSuggestList.filter(o => !queryString || o.value.includes(queryString)));
        return;
      }
      listSwaggerDescVo(this.addApiForm.data.serviceUrl).then(res => {
        res.data.forEach(o => { o.value = o.description ? `${o.url} | ${o.description}` : o.url; });
        this.addApiForm.urlSuggestList = res.data;
        cb(res.data);
      });
    },
    handleUrlSelect(item) {
      this.addApiForm.data.url = item.url;
      this.addApiForm.data.description = item.description;
    },
    handleServiceUrlChange() {
      this.addApiForm.urlSuggestList = null;
    }
  }
};
</script>
<style lang="scss" scoped>
.api-management-container {

  .api-management-filter {
    min-height: 51px;
    overflow: auto;
  }

  .api-management-api {
    height: calc(100vh - 131px);
  }

  @media only screen and (max-width: 1199px) {
    .api-management-api {
      margin-bottom: 20px;
    }
  }

  .api-management-role {
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
