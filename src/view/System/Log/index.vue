<template>
  <div class="log-management-container">
    <div class="log-management-filter">
      <el-form ref="logsFilterForm" size="small" inline :model="logsFilterForm.data" :rules="logsFilterForm.rules">
        <el-form-item label="日志类型" prop="logTypeList">
          <el-select v-model="logsFilterForm.data.logTypeList" collapse-tags multiple filterable placeholder="请选择">
            <el-option
              v-for="item in logsFilterForm.option.logType"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="URL" prop="url">
          <el-input v-model="logsFilterForm.data.url" />
        </el-form-item>
        <el-form-item label="访问用户" prop="accessUser">
          <el-input v-model="logsFilterForm.data.accessUser" />
        </el-form-item>
        <el-form-item label="异常状态" prop="exceptional">
          <el-radio-group v-model="logsFilterForm.data.exceptional">
            <el-radio :label="true">异常</el-radio>
            <el-radio :label="false">正常</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="listLogs">搜索</el-button>
          <el-button @click="() => this.$refs.logsFilterForm.resetFields()">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="log-management-log">
      <el-tab-pane label="日志列表">
        <el-table
          ref="logListTable"
          height="calc(100vh - 221px)"
          :data="logListTable.data"
          highlight-current-row
          @current-change="logListTableClick"
        >
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="logType" label="日志类型" width="80px" fixed />
          <el-table-column property="url" label="URL" width="400px" fixed show-overflow-tooltip />
          <el-table-column
            property="exceptional"
            label="异常状态"
            width="80px"
            :formatter="(row, col, cell) => cell?'异常':'正常'"
          />
          <el-table-column property="accessUser" label="访问用户" width="160px" />
          <el-table-column property="createTime" label="访问时间" width="150px" />
          <el-table-column label="操作" width="180px">
            <template slot-scope="scope">
              <el-button size="mini" @click="() => showParamsDialog.showDialog = true">
                <el-icon class="el-icon-search" />
              </el-button>
              <el-button size="mini" type="danger" @click="deleteLog(scope.$index, scope.row)">
                <el-icon class="el-icon-delete" />
              </el-button>
            </template>
          </el-table-column>
          <el-table-column property="description" label="描述" width="400px" show-overflow-tooltip />
          <el-table-column property="classMethod" label="方法" width="600px" show-overflow-tooltip />
          <el-table-column property="threadId" label="线程ID" width="80px" />
          <el-table-column property="threadName" label="线程名" width="150px" show-overflow-tooltip />
          <el-table-column property="ip" label="IP" width="150px" />
          <el-table-column property="httpMethod" label="HTTP方法" width="80px" />
          <el-table-column property="requestParams" label="参数" width="150px" show-overflow-tooltip />
          <el-table-column property="result" label="结果" width="150px" show-overflow-tooltip />
          <el-table-column property="timeCost" label="耗时ms" width="150px" />
          <el-table-column property="os" label="操作系统" width="150px" show-overflow-tooltip />
          <el-table-column property="browser" label="浏览器" width="150px" show-overflow-tooltip />
          <el-table-column property="userAgent" label="user-agent" width="400px" show-overflow-tooltip />
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          :page-size="logListTable.page.pageSize"
          :total="logListTable.page.total"
          :current-page.sync="logListTable.page.pageNumber"
          @current-change="pageLogs"
        />
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="查看参数/结果" :visible.sync="showParamsDialog.showDialog" width="50%">
      <el-tabs type="border-card">
        <el-tab-pane label="参数">
          <pre class="log-management-show-params">
          {{ showParams }}
          </pre>
        </el-tab-pane>
        <el-tab-pane label="结果">
          <pre class="log-management-show-params">
          {{ showResult }}
          </pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>
<script>
import {deleteLog, pageLog} from '@/api/system';

export default {
  name: 'LogManagement',
  data() {
    return {
      logsFilterForm: {
        data: {
          createTime: null,
          logTypeList: null,
          url: null,
          accessUser: null,
          exceptional: null
        },
        rules: {},
        option: {
          logType: ['Query', 'Remove', 'Update', 'Add', 'Auth', 'Import', 'Export', 'Upload', 'Download', 'Other']
        }
      },
      logListTable: {
        currClick: null,
        data: [],
        page: {
          total: 0,
          pageNumber: 1,
          pageSize: 12
        }
      },
      showParamsDialog: {
        showDialog: false
      }
    };
  },
  computed: {
    showParams() {
      if (!this.logListTable.currClick || !this.logListTable.currClick.requestParams) {
        return;
      }
      try {
        return JSON.stringify(JSON.parse(this.logListTable.currClick.requestParams), null, 2);
      } catch (e) {
        return this.logListTable.currClick.requestParams;
      }
    },
    showResult() {
      if (!this.logListTable.currClick || !this.logListTable.currClick.result) {
        return;
      }
      try {
        return JSON.stringify(JSON.parse(this.logListTable.currClick.result), null, 2);
      } catch (e) {
        return this.logListTable.currClick.result;
      }
    }
  },
  created() {
    this.listLogs();
  },
  methods: {
    listLogs() {
      this.pageLogs(1).then(() => {
        this.logListTable.page.pageNumber = 1;
      });
    },
    pageLogs(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.logsFilterForm.data,
        pageNumber: currPage,
        pageSize: this.logListTable.page.pageSize
      };
      // 查询日志列表
      return pageLog(data).then(res => {
        this.logListTable.data = res.data.records;
        this.logListTable.page.total = res.data.total;
      });
    },
    deleteLog(index, row) {
      deleteLog([row.id]).then(res => {
        if (res.data) {
          this.logListTable.data.splice(index, 1);
        }
      });
    },
    logListTableClick(curr, old) {
      this.logListTable.currClick = curr;
    }
  }
};
</script>
<style lang="scss" scoped>
.log-management-container {
  height: calc(100vh - 60px);
  margin: 10px 20px 0;
  overflow: hidden;

  .log-management-filter {
    height: 51px;
    overflow: auto;
  }

  .log-management-log {
    display: inline-block;
    height: calc(100vh - 131px);
    width: 98%;
    vertical-align: top;
    margin-right: 1%;
  }

  .log-management-show-params {
    height: 50vh;
    overflow: auto;
  }

}
</style>
