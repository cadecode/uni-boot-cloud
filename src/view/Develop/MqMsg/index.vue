<template>
  <div class="app-container mq-msg-management-container">
    <div class="mq-msg-management-filter">
      <el-form ref="mqMsgFilterForm" size="small" inline :model="mqMsgFilterForm.data" :rules="mqMsgFilterForm.rules">
        <el-form-item label="服务" prop="serviceUrl">
          <base-dict-loader dict-type="serviceUrl" @load="(dict, defaultDict) => mqMsgFilterForm.data.serviceUrl = defaultDict">
            <template v-slot="scope">
              <el-select v-model="mqMsgFilterForm.data.serviceUrl" filterable placeholder="请选择">
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
        <br>
        <el-form-item label="日期" prop="createTimeRange">
          <el-date-picker
            v-model="mqMsgFilterForm.data.createTimeRange"
            type="datetimerange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="业务类型" prop="bizType">
          <el-input v-model="mqMsgFilterForm.data.bizType" />
        </el-form-item>
        <el-form-item label="发送状态" prop="sendStateList">
          <base-dict-loader
            dict-type="mqMsgSendState"
            multiple
            @load="loadSendStateDict"
          >
            <template v-slot="scope">
              <el-select
                v-model="mqMsgFilterForm.data.sendStateList"
                clearable
                collapse-tags
                multiple
                filterable
                placeholder="请选择"
              >
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
        <el-form-item>
          <el-button type="primary" @click="pageMqMsg(1)">搜索</el-button>
          <el-button @click="() => this.$refs.mqMsgFilterForm.resetFields()">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="mq-msg-management-mq-msg">
      <el-tab-pane label="消息列表">
        <el-table
          ref="mqMsgListTable"
          height="calc(100vh - 272px)"
          :data="mqMsgListTable.data"
          highlight-current-row
          @current-change="mqMsgListTableClick"
        >
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="bizType" label="业务类型" width="200px" show-overflow-tooltip />
          <el-table-column property="bizKey" label="业务键" width="200px" show-overflow-tooltip />
          <el-table-column property="exchange" label="交换机" width="200px" show-overflow-tooltip />
          <el-table-column property="routingKey" label="路由" width="200px" show-overflow-tooltip />
          <el-table-column property="connectionName" label="连接名称" width="200px" show-overflow-tooltip />
          <el-table-column property="sendState" label="发送状态" width="100px" />
          <el-table-column property="consumeState" label="消费状态" width="100px" />
          <el-table-column property="leftRetryTimes" label="剩余重试次数" width="80px" />
          <el-table-column label="操作" width="180px">
            <template v-slot="scope">
              <el-button size="mini" type="primary" @click="() => showDetailDialog.showDialog = true">
                <el-icon class="el-icon-search" />
              </el-button>
              <el-button size="mini" @click="updateMqMsg(scope.$index, scope.row)">
                <el-icon class="el-icon-edit" />
              </el-button>
            </template>
          </el-table-column>
          <el-table-column property="nextRetryTime" label="下次重试时间" width="150px" />
          <el-table-column property="message" label="消息" width="400px" show-overflow-tooltip />
          <el-table-column property="cause" label="异常原因" width="400px" show-overflow-tooltip />
          <el-table-column property="maxRetryTimes" label="最大重试次数" width="80px" />
          <el-table-column property="backoffInitInterval" label="初始重试间隔" width="120px" show-overflow-tooltip />
          <el-table-column property="backoffMultiplier" label="重试间隔乘子" width="80px" />
          <el-table-column property="backoffMaxInterval" label="最大重试间隔" width="120px" show-overflow-tooltip />
          <el-table-column property="updateTime" label="更新时间" width="150px" />
          <el-table-column property="updateUser" label="更新人" width="160px" />
          <el-table-column property="createTime" label="创建时间" width="150px" />
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          :page-size="mqMsgListTable.page.pageSize"
          :total="mqMsgListTable.page.total"
          :current-page.sync="mqMsgListTable.page.pageNumber"
          @current-change="pageMqMsg"
        />
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="查看详情" :visible.sync="showDetailDialog.showDialog" width="50%">
      <el-tabs type="border-card">
        <el-tab-pane label="消息内容">
          <pre class="mq-msg-management-show-details">{{ showMessage }}</pre>
        </el-tab-pane>
        <el-tab-pane label="异常原因">
          <pre class="mq-msg-management-show-details">{{ showCause }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <el-dialog title="更新消息" :visible.sync="updateMqMsgForm.showDialog" width="30%">
      <el-form
        ref="updateMqMsgForm"
        label-width="100px"
        size="small"
        :model="updateMqMsgForm.data"
        :rules="updateMqMsgForm.rule"
      >
        <el-form-item label="发送状态" prop="sendState">
          <el-select
            v-model="updateMqMsgForm.data.sendState"
            clearable
            filterable
            placeholder="请选择"
          >
            <el-option
              v-for="item in mqMsgFilterForm.option.sendStateList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="剩余重试次数" prop="leftRetryTimes">
          <el-input v-model="updateMqMsgForm.data.leftRetryTimes" type="number" />
        </el-form-item>
        <el-form-item>
          <el-button @click="() => this.$refs.updateMqMsgForm.resetFields()">重置</el-button>
          <el-button @click="updateMqMsgForm.showDialog = false">取消</el-button>
          <el-button type="primary" @click="updateMqMsgCommit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import {listMqMsgByIdList, pageMqMsg, updateMqMsg} from '@/api/develop';
import BaseDictLoader from '@/component/BaseDictLoader';

export default {
  name: 'VMqMsgManagement',
  components: {BaseDictLoader},
  data() {
    return {
      mqMsgFilterForm: {
        data: {
          serviceUrl: null,
          createTimeRange: [],
          bizType: null,
          sendStateList: null
        },
        rules: {
          serviceUrl: [{required: true, message: '请选择服务', trigger: 'blur'}]
        },
        option: {
          sendStateList: []
        }
      },
      mqMsgListTable: {
        currClick: null,
        data: [],
        page: {
          total: 0,
          pageNumber: 1,
          pageSize: 12
        }
      },
      showDetailDialog: {
        showDialog: false
      },
      updateMqMsgForm: {
        showDialog: false,
        row: null,
        data: {
          id: null,
          sendState: null,
          leftRetryTimes: null
        },
        rule: {}
      }
    };
  },
  computed: {
    showMessage() {
      if (!this.mqMsgListTable.currClick || !this.mqMsgListTable.currClick.message) {
        return;
      }
      try {
        return JSON.stringify(JSON.parse(this.mqMsgListTable.currClick.message), null, 2);
      } catch (e) {
        return this.mqMsgListTable.currClick.message;
      }
    },
    showCause() {
      if (!this.mqMsgListTable.currClick || !this.mqMsgListTable.currClick.cause) {
        return;
      }
      try {
        return JSON.stringify(JSON.parse(this.mqMsgListTable.currClick.cause), null, 2);
      } catch (e) {
        return this.mqMsgListTable.currClick.cause;
      }
    }
  },
  methods: {
    loadSendStateDict(dict, defaultDict) {
      this.mqMsgFilterForm.option.sendStateList = dict;
      this.mqMsgFilterForm.data.sendStateList = defaultDict;
    },
    pageMqMsg(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.mqMsgFilterForm.data,
        startCreateTime: this.mqMsgFilterForm.data.createTimeRange[0],
        endCreateTime: this.mqMsgFilterForm.data.createTimeRange[1],
        pageNumber: currPage,
        pageSize: this.mqMsgListTable.page.pageSize
      };
      this.$refs.mqMsgFilterForm.validate((valid) => {
        if (valid) {
          // 查询消息列表
          pageMqMsg(this.mqMsgFilterForm.data.serviceUrl, data).then(res => {
            this.mqMsgListTable.data = res.data.records;
            this.mqMsgListTable.page.total = res.data.total;
            // 重置页码
            if (currPage === 1) {
              this.mqMsgListTable.page.pageNumber = 1;
            }
          });
        }
      });
    },
    mqMsgListTableClick(curr, old) {
      this.mqMsgListTable.currClick = curr;
    },
    updateMqMsg(index, row) {
      this.updateMqMsgForm.showDialog = true;
      this.updateMqMsgForm.row = row;
      this.$nextTick(() => {
        Object.keys(this.updateMqMsgForm.data).forEach(k => {
          this.updateMqMsgForm.data[k] = row[k];
        });
      });
    },
    updateMqMsgCommit() {
      this.$refs.updateMqMsgForm.validate((valid) => {
        if (valid) {
          updateMqMsg(this.mqMsgFilterForm.data.serviceUrl, this.updateMqMsgForm.data).then(res => {
            if (res.data) {
              this.updateMqMsgForm.showDialog = false;
              this.refreshMqMsg(this.updateMqMsgForm.row);
            }
          });
        }
      });
    },
    refreshMqMsg(row) {
      return listMqMsgByIdList(this.mqMsgFilterForm.data.serviceUrl, [row.id]).then(res => {
        Object.keys(res.data[0]).forEach(k => {
          row[k] = res.data[0][k];
        });
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.mq-msg-management-container {

  .mq-msg-management-filter {
    min-height: 51px;
    overflow: auto;
  }

  .mq-msg-management-mq-msg {
    height: calc(100vh - 182px);
    width: 100%;
    vertical-align: top;
  }

  .mq-msg-management-show-details {
    height: 50vh;
    overflow: auto;
  }

}
</style>
