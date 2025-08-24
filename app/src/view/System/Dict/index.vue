<template>
  <div class="app-container dict-management-container">
    <div class="dict-management-filter">
      <el-form ref="dictFilterForm" size="small" inline :model="dictFilterForm.data" :rules="dictFilterForm.rule">
        <el-form-item label="字典名称" prop="name">
          <el-input v-model="dictFilterForm.data.name" />
        </el-form-item>
        <el-form-item label="字典类型" prop="type">
          <el-input v-model="dictFilterForm.data.type" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="pageDict(1)">搜索</el-button>
          <el-button @click="() => this.$refs.dictFilterForm.resetFields()">重置</el-button>
          <el-button type="info" @click="addDictForm.showDialog = true">添加字典</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="dict-management-table">
      <el-tab-pane label="字典列表">
        <el-table
          ref="dictListTable"
          height="calc(100vh - 221px)"
          :data="dictListTable.data"
          highlight-current-row
          @current-change="dictListTableClick"
        >
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="name" label="名称" width="160px" fixed />
          <el-table-column property="type" label="类型" width="160px" fixed />
          <el-table-column property="label" label="标签" width="160px" />
          <el-table-column property="value" label="值" width="290px" show-overflow-tooltip />
          <el-table-column
            property="defaultFlag"
            label="是否默认"
            width="80px"
            :formatter="(row, col, cell) => cell?'是':'否'"
          />
          <el-table-column property="updateTime" label="更新时间" width="150px" />
          <el-table-column property="updateUser" label="更新人" width="160px" />
          <el-table-column property="createTime" label="创建时间" width="150px" />
          <el-table-column label="操作" width="180px">
            <template v-slot="scope">
              <el-button size="mini" @click="updateDict(scope.$index, scope.row)">
                <el-icon class="el-icon-edit" />
              </el-button>
              <el-button size="mini" type="danger" @click="deleteDict(scope.$index, scope.row)">
                <el-icon class="el-icon-delete" />
              </el-button>
            </template>
          </el-table-column>
          <el-table-column property="orderNum" label="排序" width="100px" />
          <el-table-column property="description" label="描述" width="400px" show-overflow-tooltip />
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          :page-size="dictListTable.page.pageSize"
          :total="dictListTable.page.total"
          :current-page.sync="dictListTable.page.pageNumber"
          @current-change="pageDict"
        />
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="更新字典" :visible.sync="updateDictForm.showDialog" width="30%">
      <el-form
        ref="updateDictForm"
        label-width="100px"
        size="small"
        :model="updateDictForm.data"
        :rules="updateDictForm.rule"
      >
        <el-form-item label="名称" prop="name">
          <el-input v-model="updateDictForm.data.name" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="updateDictForm.data.type" />
        </el-form-item>
        <el-form-item label="标签" prop="label">
          <el-input v-model="updateDictForm.data.label" />
        </el-form-item>
        <el-form-item label="值" prop="value">
          <el-input v-model="updateDictForm.data.value" />
        </el-form-item>
        <el-form-item label="是否默认" prop="defaultFlag">
          <el-radio-group v-model="updateDictForm.data.defaultFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="updateDictForm.data.description" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="updateDictForm.data.orderNum" type="number" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.updateDictForm.resetFields()">重置</el-button>
          <el-button @click="updateDictForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateDictCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="添加字典" :visible.sync="addDictForm.showDialog" width="30%">
      <el-form
        ref="addDictForm"
        label-width="100px"
        size="small"
        :model="addDictForm.data"
        :rules="addDictForm.rule"
      >
        <el-form-item label="名称" prop="name">
          <el-input v-model="addDictForm.data.name" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-autocomplete v-model="addDictForm.data.type" :fetch-suggestions="listTypeSuggest" @select="handleTypeSelect" />
        </el-form-item>
        <el-form-item label="标签" prop="label">
          <el-input v-model="addDictForm.data.label" />
        </el-form-item>
        <el-form-item label="值" prop="value">
          <el-input v-model="addDictForm.data.value" />
        </el-form-item>
        <el-form-item label="是否默认" prop="defaultFlag">
          <el-radio-group v-model="addDictForm.data.defaultFlag">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="addDictForm.data.description" />
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="addDictForm.data.orderNum" type="number" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.addDictForm.resetFields()">重置</el-button>
          <el-button @click="addDictForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="addDictCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {addDict, deleteDict, listDictByIds, listDictTypeSuggest, pageDict, updateDict} from '@/api/system';

export default {
  name: 'DictManagement',
  data() {
    return {
      dictFilterForm: {
        data: {
          name: null,
          typ: null
        },
        rule: {}
      },
      dictListTable: {
        currClick: null,
        data: [],
        page: {
          total: 0,
          pageNumber: 1,
          pageSize: 12
        }
      },
      updateDictForm: {
        row: null,
        showDialog: false,
        data: {
          id: null,
          name: null,
          type: null,
          label: null,
          value: null,
          defaultFlag: false,
          description: null,
          orderNum: null
        },
        rule: {
          name: [{required: true, message: '请输入字典名称', trigger: 'blur'}],
          type: [{required: true, message: '请输入字典类型', trigger: 'blur'}],
          value: [{required: true, message: '请输入字典值', trigger: 'blur'}],
          defaultFlag: [{required: true, message: '请选择是否默认', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}]
        }
      },
      addDictForm: {
        showDialog: false,
        data: {
          name: null,
          type: null,
          label: null,
          value: null,
          defaultFlag: false,
          description: null,
          orderNum: null
        },
        urlSuggestList: null,
        rule: {
          name: [{required: true, message: '请输入字典名称', trigger: 'blur'}],
          type: [{required: true, message: '请输入字典类型', trigger: 'blur'}],
          value: [{required: true, message: '请输入字典值', trigger: 'blur'}],
          defaultFlag: [{required: true, message: '请选择是否默认', trigger: 'blur'}],
          orderNum: [{required: true, message: '请输入排序', trigger: 'blur'}]
        }
      }
    };
  },
  computed: {
    showParams() {
      if (!this.dictListTable.currClick || !this.dictListTable.currClick.requestParams) {
        return;
      }
      try {
        return JSON.stringify(JSON.parse(this.dictListTable.currClick.requestParams), null, 2);
      } catch (e) {
        return this.dictListTable.currClick.requestParams;
      }
    },
    showResult() {
      if (!this.dictListTable.currClick || !this.dictListTable.currClick.result) {
        return;
      }
      try {
        return JSON.stringify(JSON.parse(this.dictListTable.currClick.result), null, 2);
      } catch (e) {
        return this.dictListTable.currClick.result;
      }
    }
  },
  created() {
    this.pageDict(1);
  },
  methods: {
    pageDict(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.dictFilterForm.data,
        pageNumber: currPage,
        pageSize: this.dictListTable.page.pageSize
      };
      // 查询字典列表
      pageDict(data).then(res => {
        this.dictListTable.data = res.data.records;
        this.dictListTable.page.total = res.data.total;
        if (currPage === 1) {
          this.dictListTable.page.pageNumber = 1;
        }
      });
    },
    deleteDict(index, row) {
      deleteDict([row.id]).then(res => {
        if (res.data) {
          this.dictListTable.data.splice(index, 1);
          this.addDictForm.urlSuggestList = null;
        }
      });
    },
    dictListTableClick(curr, old) {
      this.dictListTable.currClick = curr;
    },
    refreshDict(row) {
      return listDictByIds([row.id]).then(res => {
        Object.keys(res.data[0]).forEach(k => {
          row[k] = res.data[0][k];
        });
      });
    },
    updateDict(index, row) {
      this.updateDictForm.showDialog = true;
      this.updateDictForm.row = row;
      this.$nextTick(() => {
        Object.keys(this.updateDictForm.data).forEach(k => {
          this.updateDictForm.data[k] = row[k];
        });
      });
    },
    updateDictCommit() {
      this.$refs.updateDictForm.validate((valid) => {
        if (valid) {
          updateDict(this.updateDictForm.data).then(res => {
            if (res.data) {
              this.refreshDict(this.updateDictForm.row);
              this.addDictForm.urlSuggestList = null;
              this.updateDictForm.showDialog = false;
            }
          });
        }
      });
    },
    addDictCommit() {
      this.$refs.addDictForm.validate((valid) => {
        if (valid) {
          addDict(this.addDictForm.data).then(res => {
            if (res.data) {
              this.addDictForm.showDialog = false;
              // 重新拉取建议列表
              this.addDictForm.urlSuggestList = null;
              this.pageDict(1);
            }
          });
        }
      });
    },
    listTypeSuggest(queryString, cb) {
      if (this.addDictForm.urlSuggestList) {
        cb(this.addDictForm.urlSuggestList.filter(o => o.value.includes(queryString)));
        return;
      }
      listDictTypeSuggest().then(res => {
        res.data.forEach(o => { o.value = `${o.type} | ${o.name}`; });
        this.addDictForm.urlSuggestList = res.data;
        cb(res.data);
      });
    },
    handleTypeSelect(item) {
      this.addDictForm.data.type = item.type;
      this.addDictForm.data.name = item.name;
    }
  }
};
</script>
<style lang="scss" scoped>
.dict-management-container {

  .dict-management-filter {
    min-height: 51px;
    overflow: auto;
  }

  .dict-management-table {
    height: calc(100vh - 131px);
    width: 100%;
    vertical-align: top;
  }

  .dict-management-show-params {
    height: 50vh;
    overflow: auto;
  }

  ::v-deep .el-autocomplete .el-input {
    width: 436px !important;
  }

}
</style>
