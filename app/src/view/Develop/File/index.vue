<template>
  <div class="app-container file-management-container">
    <div class="file-management-filter">
      <el-form ref="fileFilterForm" size="small" inline :model="fileFilterForm.data" :rules="fileFilterForm.rules">
        <el-form-item label="服务" prop="serviceUrl">
          <base-dict-loader
            dict-type="serviceUrl"
            @load="(dict, defaultDict) => fileFilterForm.data.serviceUrl = defaultDict"
          >
            <template v-slot="scope">
              <el-select v-model="fileFilterForm.data.serviceUrl" filterable placeholder="请选择">
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
            v-model="fileFilterForm.data.createTimeRange"
            type="datetimerange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
          />
        </el-form-item>
        <el-form-item label="平台" prop="platformTypeList">
          <base-dict-loader
            dict-type="platformType"
            @load="(dict, defaultDict) => fileFilterForm.data.platform = defaultDict"
          >
            <template v-slot="scope">
              <el-select
                v-model="fileFilterForm.data.platform"
                clearable
                collapse-tags
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
        <el-form-item label="URL" prop="url">
          <el-input v-model="fileFilterForm.data.url" />
        </el-form-item>
        <el-form-item label="文件名" prop="filename">
          <el-input v-model="fileFilterForm.data.filename" />
        </el-form-item>
        <el-form-item label="对象 ID" prop="objectId">
          <el-input v-model="fileFilterForm.data.objectId" />
        </el-form-item>
        <el-form-item label="对象类型" prop="objectTypeList">
          <base-dict-loader
            dict-type="objectType"
            @load="(dict, defaultDict) => fileFilterForm.data.objectType = defaultDict"
          >
            <template v-slot="scope">
              <el-select
                v-model="fileFilterForm.data.objectType"
                clearable
                collapse-tags
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
          <el-button type="primary" @click="handlePageFile(1)">搜索</el-button>
          <el-button @click="() => this.$refs.fileFilterForm.resetFields()">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-tabs type="border-card" class="file-management-file">
      <el-tab-pane label="文件列表">
        <el-table
          ref="fileListTable"
          height="calc(100vh - 322px)"
          :data="fileListTable.data"
          highlight-current-row
          @current-change="fileListTableClick"
        >
          <el-table-column property="id" label="ID" width="170px" fixed />
          <el-table-column property="filename" label="文件名" width="400px" fixed show-overflow-tooltip />
          <el-table-column property="platform" label="平台" width="180px" />
          <el-table-column property="url" label="URL" width="400px" show-overflow-tooltip />
          <el-table-column property="basePath" label="基础路径" width="180px" show-overflow-tooltip />
          <el-table-column label="操作" width="180px">
            <template v-slot="scope">
              <el-button size="mini" type="primary" @click="() => showFileInfoDialog.showDialog = true">
                <el-icon class="el-icon-search" />
              </el-button>
              <el-tooltip class="item" effect="dark" content="删除文件和记录" placement="top-start">
                <el-button size="mini" type="danger" @click="handleDeleteFileStorage(scope.$index, scope.row)">
                  <el-icon class="el-icon-document-delete" />
                </el-button>
              </el-tooltip>
              <el-tooltip class="item" effect="dark" content="仅删除记录" placement="top-start">
                <el-button size="mini" type="warning" @click="handleDeleteFileRecord(scope.$index, scope.row)">
                  <el-icon class="el-icon-delete" />
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column property="size" label="大小" width="180px" />
          <el-table-column property="ext" label="扩展名" width="100px" />
          <el-table-column property="contentType" label="ContentType" width="180px" show-overflow-tooltip />
          <el-table-column property="thUrl" label="缩略图 URL" width="400px" show-overflow-tooltip />
          <el-table-column property="thFilename" label="缩略图文件名" width="400px" show-overflow-tooltip />
          <el-table-column property="thSize" label="缩略图大小" width="180px" />
          <el-table-column property="thContentType" label="缩略图 ContentType" width="180px" show-overflow-tooltip />
          <el-table-column property="objectId" label="对象 Id" width="170px" show-overflow-tooltip />
          <el-table-column property="objectType" label="对象类型" width="180px" show-overflow-tooltip />
          <el-table-column property="updateTime" label="更新时间" width="150px" />
          <el-table-column property="updateUser" label="更新人" width="160px" />
          <el-table-column property="createTime" label="创建时间" width="150px" />
        </el-table>
        <el-pagination
          layout="prev, pager, next"
          :page-size="fileListTable.page.pageSize"
          :total="fileListTable.page.total"
          :current-page.sync="fileListTable.page.pageNumber"
          @current-change="handlePageFile"
        />
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="查看详情" :visible.sync="showFileInfoDialog.showDialog" width="50%">
      <el-tabs type="border-card">
        <el-tab-pane label="FileInfo">
          <pre class="file-management-show-info">{{ showFileInfo }}</pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </div>
</template>
<script>
import {deleteFileRecord, deleteFileStorage, pageFile} from '@/api/develop';
import BaseDictLoader from '@/component/BaseDictLoader/index.vue';

export default {
  name: 'FileManagement',
  components: {BaseDictLoader},
  data() {
    return {
      fileFilterForm: {
        data: {
          serviceUrl: null,
          createTimeRange: [],
          platform: null,
          url: null,
          filename: null,
          objectId: null,
          objectType: null
        },
        rules: {
          serviceUrl: [{required: true, message: '请选择服务', trigger: 'blur'}]
        }
      },
      fileListTable: {
        currClick: null,
        data: [],
        page: {
          total: 0,
          pageNumber: 1,
          pageSize: 10
        }
      },
      showFileInfoDialog: {
        showDialog: false
      }
    };
  },
  computed: {
    showFileInfo() {
      if (!this.fileListTable.currClick) {
        return;
      }
      try {
        return JSON.stringify(this.fileListTable.currClick, null, 2);
      } catch (e) {
        return this.fileListTable.currClick;
      }
    }
  },
  methods: {
    handlePageFile(currPage) {
      // 分页插件回调传递当前页号
      const data = {
        ...this.fileFilterForm.data,
        startTime: this.fileFilterForm.data.createTimeRange[0],
        endTime: this.fileFilterForm.data.createTimeRange[1],
        pageNumber: currPage,
        pageSize: this.fileListTable.page.pageSize
      };
      this.$refs.fileFilterForm.validate((valid) => {
        if (valid) {
          // 查询日志列表
          pageFile(this.fileFilterForm.data.serviceUrl, data).then(res => {
            this.fileListTable.data = res.data.records;
            this.fileListTable.page.total = res.data.total;
            // 重置页码
            if (currPage === 1) {
              this.fileListTable.page.pageNumber = 1;
            }
          });
        }
      });
    },
    handleDeleteFileRecord(index, row) {
      deleteFileRecord(this.fileFilterForm.data.serviceUrl, [row.id]).then(res => {
        if (res.data) {
          this.fileListTable.data.splice(index, 1);
        }
      });
    },
    handleDeleteFileStorage(index, row) {
      deleteFileStorage(this.fileFilterForm.data.serviceUrl, [row.id]).then(res => {
        if (res.data) {
          this.fileListTable.data.splice(index, 1);
        }
      });
    },
    fileListTableClick(curr, old) {
      this.fileListTable.currClick = curr;
    }
  }
};
</script>
<style lang="scss" scoped>
.file-management-container {

  .file-management-filter {
    min-height: 51px;
    overflow: auto;
  }

  .file-management-file {
    height: calc(100vh - 232px);
    width: 100%;
    vertical-align: top;
  }

  .file-management-show-info {
    height: 50vh;
    overflow: auto;
  }
}
</style>
