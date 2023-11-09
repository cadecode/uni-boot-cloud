<template>
  <div class="app-container home-management-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
        <el-card shadow="hover" class="user-card">
          <div class="user-card-greet">
            <h3>
              <i class="el-icon-user-solid" />
              <span>您好，{{ userInfo.nickName }}</span>
            </h3>
          </div>
          <el-descriptions :column="1">
            <el-descriptions-item>
              <span slot="label"><i class="el-icon-key" />用户ID</span>
              {{ userInfo.id }}
            </el-descriptions-item>
            <el-descriptions-item>
              <span slot="label"><i class="el-icon-user-solid" />用户名</span>
              {{ userInfo.username }}
            </el-descriptions-item>
            <el-descriptions-item>
              <span slot="label"><i class="el-icon-s-custom" />角色</span>
              <el-tag v-for="o in userInfo.roles" :key="o" type="small" style="margin-right: 2px">
                {{ o }}
              </el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
              <span slot="label"><i class="el-icon-position" />登录IP</span>
              {{ userInfo.loginIp }}
            </el-descriptions-item>
            <el-descriptions-item>
              <span slot="label"><i class="el-icon-time" />登录时间</span>
              {{ userInfo.loginDate }}
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
        <el-card shadow="hover" class="dev-info-card">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-info" />开发信息</span>
          </div>
          <el-descriptions :column="2" border>
            <el-descriptions-item label="@vue/cli">{{ devInfoTable.devDependencies['@vue/cli-service'] }}</el-descriptions-item>
            <el-descriptions-item label="vue">{{ devInfoTable.dependencies['vue'] }}</el-descriptions-item>
            <el-descriptions-item label="vuex">{{ devInfoTable.dependencies['vuex'] }}</el-descriptions-item>
            <el-descriptions-item label="vue-router">{{ devInfoTable.dependencies['vue-router'] }}</el-descriptions-item>
            <el-descriptions-item label="element-ui">{{ devInfoTable.dependencies['element-ui'] }}</el-descriptions-item>
            <el-descriptions-item label="axios">{{ devInfoTable.dependencies['axios'] }}</el-descriptions-item>
            <el-descriptions-item label="echarts">{{ devInfoTable.dependencies['echarts'] }}</el-descriptions-item>
            <el-descriptions-item label="sass">{{ devInfoTable.devDependencies['sass'] }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
        <el-card shadow="hover" class="product-card">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-data-line" />产量</span>
          </div>
          <base-echarts id="productCard" width="100%" height="300px" :option="productCard.option" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
        <el-card shadow="hover" class="task-card">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-s-data" />任务量</span>
          </div>
          <base-echarts id="taskCard" width="100%" height="300px" :option="taskCard.option" />
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
        <el-card shadow="hover" class="map-card">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-map-location" />地图</span>
          </div>
          <base-echarts id="chineseMap" width="100%" height="300px" :option="mapCard.option" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import * as echarts from 'echarts';
import BaseEcharts from '@/component/BaseEcharts/index.vue';
import {dependencies, devDependencies} from '../../../package.json';
import {mapGetters} from 'vuex';
// 注册地图数据
echarts.registerMap('china', require('@/asset/meta/geo_china.json'));

export default {
  name: 'DemoHome',
  components: {BaseEcharts},
  data() {
    return {
      mapCard: {
        option: {
          geo: {
            map: 'china',
            zoom: 1.5,
            top: '60px',
            label: {
              emphasis: {
                show: false
              }
            }
          }
        }
      },
      productCard: {
        option: {
          grid: {
            top: '4%',
            left: '2%',
            right: '4%',
            bottom: '0%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: ['0时', '4时', '8时', '12时', '16时', '20时', '24时'],
              axisTick: {
                alignWithLabel: true
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '产量',
              type: 'bar',
              barWidth: '60%',
              data: [10, 52, 20, 33, 39, 33, 22]
            }
          ]
        }
      },
      taskCard: {
        option: {
          grid: {
            top: '4%',
            left: '2%',
            right: '4%',
            bottom: '0%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: ['2023/6/1', '2023/6/2', '2023/6/3', '2023/6/4'],
              axisTick: {
                alignWithLabel: true
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '任务量',
              type: 'line',
              data: [80, 124, 70, 115],
              smooth: true,
              areaStyle: {}
            }
          ]
        }
      },
      devInfoTable: {
        data: [],
        dependencies: dependencies,
        devDependencies: devDependencies
      }
    };
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles',
      'userInfo'
    ])
  }
};
</script>
<style lang="scss" scoped>
.home-management-container {

  ::v-deep .el-card {
    border-radius: 1.5px;
  }
  .user-card, .product-card, .task-card, .dev-info-card, .map-card {
    margin-bottom: 20px;
  }
  .user-card, .dev-info-card{
    height: 283px;
  }
}

</style>
