<template>
  <div class="app-container  dept-management-container">
    <div class="dept-management-filter">
      <el-form
        ref="deptFilterForm"
        size="small"
        inline
        :model="deptFilterForm.data"
        :rules="deptFilterForm.rules"
      >
        <el-form-item>
          <el-button type="primary" @click="listDept()">搜索</el-button>
          <!--<el-button @click="() => this.$refs.deptFilterForm.resetFields()">重置</el-button>-->
          <el-button type="info" @click="addDept">添加部门</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="dept-management-dept">
      <el-tab-pane label="部门列表">
        <el-table
          ref="deptListTable"
          height="calc(100vh - 221px)"
          :data="deptListTable.data"
          highlight-current-row
          row-key="id"
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
          @row-click="listTableClick"
        >
          <el-table-column property="orderNum" label="NO." width="200px" fixed />
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="deptName" label="部门名" width="170px" fixed />
          <el-table-column property="updateTime" label="更新时间" width="150px" />
          <el-table-column property="updateUser" label="更新人" width="160px" />
          <el-table-column property="createTime" label="创建时间" width="150px" />
          <el-table-column label="操作" width="180px">
            <template v-slot="scope">
              <el-button size="mini" @click="updateDept(scope.$index, scope.row)">
                <el-icon class="el-icon-edit" />
              </el-button>
              <el-button size="mini" type="danger" @click="deleteDept(scope.$index, scope.row)">
                <el-icon class="el-icon-delete" />
              </el-button>
              <el-button
                size="mini"
                type="info"
                @click="addDept(scope.$index, scope.row)"
              >
                <el-icon class="el-icon-plus" />
              </el-button>
            </template>
          </el-table-column>
          <el-table-column property="parentId" label="父级ID" width="170px" />
          <el-table-column property="leader" label="Leader" width="170px" />
          <el-table-column property="phone" label="电话" width="150px" show-overflow-tooltip />
          <el-table-column property="mail" label="邮箱" width="150px" show-overflow-tooltip />
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <el-dialog
      title="更新部门"
      :visible.sync="updateDeptForm.showDialog"
      width="30%"
    >
      <el-form
        ref="updateDeptForm"
        label-width="100px"
        size="small"
        :model="updateDeptForm.data"
        :rules="updateDeptForm.rule"
      >
        <el-form-item label="部门名" prop="deptName">
          <el-input v-model="updateDeptForm.data.deptName" />
        </el-form-item>
        <el-form-item label="Leader" prop="leader">
          <el-input v-model="updateDeptForm.data.leader" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="updateDeptForm.data.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="updateDeptForm.data.mail" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="updateDeptForm.data.orderNum" type="number" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.updateDeptForm.resetFields()">重置</el-button>
          <el-button @click="updateDeptForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateDeptCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog
      title="添加部门"
      :visible.sync="addDeptForm.showDialog"
      width="30%"
    >
      <el-form
        ref="addDeptForm"
        label-width="100px"
        size="small"
        :model="addDeptForm.data"
        :rules="addDeptForm.rule"
      >
        <el-form-item label="部门名" prop="deptName">
          <el-input v-model="addDeptForm.data.deptName" />
        </el-form-item>
        <el-form-item label="父级 ID" prop="parentId">
          <el-autocomplete
            v-model="addDeptForm.data.parentId"
            :fetch-suggestions="listParentDeptSuggest"
            placeholder="不填写时为一级部门"
            @select="handleParentDeptSelect"
          />
        </el-form-item>
        <el-form-item label="Leader" prop="leader">
          <el-input v-model="addDeptForm.data.leader" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="addDeptForm.data.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="addDeptForm.data.mail" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="addDeptForm.data.orderNum" type="number" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.addDeptForm.resetFields()">重置</el-button>
          <el-button @click="addDeptForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="addDeptCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {
  addDept,
  deleteDept, listDeptParentSuggest,
  listDeptQueryVoByDeptIds,
  listDeptTreeVo,
  updateDept
} from '@/api/system';

export default {
  name: 'DeptManagement',
  data() {
    return {
      deptFilterForm: {
        data: {
        },
        rules: {}
      },
      // 表格选中的行
      listTableCurrClick: null,
      deptListTable: {
        data: []
      },
      updateDeptForm: {
        // 元数据引用
        row: null,
        data: {
          // 元数据id
          id: null,
          deptName: null,
          orderNum: null,
          leader: null,
          phone: null,
          mail: null
        },
        showDialog: false,
        rule: {
          deptName: [{required: true, message: '请输入部门名', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}]
        }
      },
      addDeptForm: {
        // 元数据引用
        row: null,
        // 映射 id 和 children tree
        treeMap: {},
        data: {
          parentId: null,
          deptName: null,
          orderNum: null,
          leader: null,
          phone: null,
          mail: null
        },
        showDialog: false,
        rule: {
          deptName: [{required: true, message: '请输入部门名', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}]
        },
        parentDeptSuggestList: null
      }
    };
  },
  created() {
    this.listDept();
  },
  methods: {
    listDept() {
      // const data = {
      //   ...this.deptFilterForm.data
      // };
      // 查询部门列表
      listDeptTreeVo().then(res => {
        // 侧边栏菜单数据
        this.deptListTable.data = res.data;
      });
    },
    refreshDept(row) {
      return listDeptQueryVoByDeptIds([row.id]).then(res => {
        Object.keys(res.data[0]).forEach(k => {
          row[k] = res.data[0][k];
        });
      });
    },
    updateDept(index, row) {
      this.updateDeptForm.showDialog = true;
      // 存放一个引用，用于修改后刷新内容
      this.updateDeptForm.row = row;
      // 保证表单初始值是data中的初始值
      this.$nextTick(() => {
        Object.keys(this.updateDeptForm.data).forEach(k => {
          this.updateDeptForm.data[k] = row[k];
        });
      });
    },
    updateDeptCommit() {
      this.$refs.updateDeptForm.validate((valid) => {
        if (!valid) {
          return;
        }
        updateDept(this.updateDeptForm.data).then(res => {
          if (res.data) {
            this.refreshDept(this.updateDeptForm.row);
            this.addDeptForm.parentDeptSuggestList = null;
            this.updateDeptForm.showDialog = false;
          }
        });
      });
    },
    addDept(index, row) {
      // 清理 parentId 以便添加根节点
      this.addDeptForm.data.parentId = null;
      // 存放当前行的引用，添加根节点时为 undefined
      this.addDeptForm.row = row;
      this.addDeptForm.showDialog = true;
      this.$nextTick(() => {
        // 若是添加子节点，注入当前行的 id 作为新节点的父级ID
        if (row) {
          this.addDeptForm.data.parentId = row.id;
        }
      });
    },
    addDeptCommit() {
      this.$refs.addDeptForm.validate((valid) => {
        if (!valid) {
          return;
        }
        addDept({...this.addDeptForm.data}).then(res => {
          if (res.data) {
            this.listDept();
            this.addDeptForm.parentDeptSuggestList = null;
            this.addDeptForm.showDialog = false;
          }
        });
      });
    },
    deleteDept(index, row) {
      const del = () => {
        deleteDept([row.id]).then(res => {
          if (res.data) {
            this.listDept();
            this.listTableCurrClick = null;
            this.addDeptForm.parentDeptSuggestList = null;
          }
        });
      };
      this.$messageBox.confirm('删除部门会删除其全部子部门，是否继续？', '删除确认', {
        type: 'warning',
        confirmButtonText: '确认',
        cancelButtonText: '取消'
      }).then(() => {
        del();
      });
    },
    listTableClick(curr, old) {
      this.listTableCurrClick = curr;
    },
    listParentDeptSuggest(queryString, cb) {
      if (this.addDeptForm.parentDeptSuggestList) {
        cb(this.addDeptForm.parentDeptSuggestList.filter(o => !queryString || o.value.includes(queryString)));
        return;
      }
      listDeptParentSuggest().then(res => {
        res.data.forEach(o => { o.value = `${o.id} | ${o.deptName}`; });
        this.addDeptForm.parentDeptSuggestList = res.data;
        cb(this.addDeptForm.parentDeptSuggestList);
      });
    },
    handleParentDeptSelect(item) {
      this.addDeptForm.data.parentId = item.id;
    }
  }
};
</script>
<style lang="scss" scoped>
.dept-management-container {

  .dept-management-filter {
    min-height: 51px;
    overflow: auto;
  }

  .dept-management-dept {
    height: calc(100vh - 131px);
  }

  @media only screen and (max-width: 1199px) {
    .dept-management-dept {
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
