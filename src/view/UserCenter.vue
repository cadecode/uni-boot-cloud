<template>
  <div class="app-container user-center-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
        <el-tabs type="border-card" class="user-center-info">
          <el-tab-pane label="个人资料">
            <el-descriptions :column="1" border>
              <el-descriptions-item>
                <span slot="label"><i class="el-icon-key" />用户ID</span>
                {{ userInfo.id }}
              </el-descriptions-item>
              <el-descriptions-item>
                <span slot="label"><i class="el-icon-user-solid" />用户名</span>
                {{ userInfo.username }}
              </el-descriptions-item>
              <el-descriptions-item>
                <span slot="label"><i class="el-icon-s-flag" />昵称</span>
                {{ userInfo.nickName }}
              </el-descriptions-item>
              <el-descriptions-item>
                <span slot="label"><i class="el-icon-s-custom" />角色</span>
                <el-tag v-for="o in userInfo.roles" :key="o" type="small" style="margin-right: 2px">
                  {{ o }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item>
                <span slot="label"><i class="el-icon-female" />性别</span>
                {{ userInfo.sex }}
              </el-descriptions-item>
              <el-descriptions-item>
                <span slot="label"><i class="el-icon-phone" />电话</span>
                {{ userInfo.phone }}
              </el-descriptions-item>
              <el-descriptions-item>
                <span slot="label"><i class="el-icon-message" />邮箱</span>
                {{ userInfo.mail }}
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
          </el-tab-pane>
        </el-tabs>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
        <el-tabs type="border-card" class="user-center-modify">
          <el-tab-pane label="修改信息">
            <el-form ref="modifyInfoForm" :model="modifyInfoForm.data" :rules="modifyInfoForm.rule">
              <el-form-item label="昵称" prop="nickName">
                <el-input v-model="modifyInfoForm.data.nickName" />
              </el-form-item>
              <el-form-item label="电话" prop="phone">
                <el-input v-model="modifyInfoForm.data.phone" />
              </el-form-item>
              <el-form-item label="邮箱" prop="mail">
                <el-input v-model="modifyInfoForm.data.mail" />
              </el-form-item>
              <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="modifyInfoForm.data.sex">
                  <el-radio label="女" />
                  <el-radio label="男" />
                </el-radio-group>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="modifyInfo">修改</el-button>
                <el-button @click="() => this.$refs.modifyInfoForm.resetFields()">重置</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="修改密码">
            <el-form ref="modifyPassForm" :model="modifyPassForm.data" :rules="modifyPassForm.rules">
              <el-form-item label="原密码" prop="oldPass">
                <el-input v-model="modifyPassForm.data.oldPass" show-password />
              </el-form-item>
              <el-form-item label="新密码" prop="newPass">
                <el-input v-model="modifyPassForm.data.newPass" show-password />
              </el-form-item>
              <el-form-item label="确认新密码" prop="confirmedPass">
                <el-input v-model="modifyPassForm.data.confirmedPass" show-password />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="modifyPass">修改</el-button>
                <el-button @click="() => this.$refs.modifyPassForm.resetFields()">重置</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>

<script>

import {mapGetters} from 'vuex';
import {modifyInfo, modifyPass} from '@/api/userCenter';

export default {
  name: 'UserCenter',
  data() {
    return {
      modifyInfoForm: {
        data: {
          nickName: this.$store.getters.userInfo.nickName,
          phone: this.$store.getters.userInfo.phone,
          mail: this.$store.getters.userInfo.mail,
          sex: this.$store.getters.userInfo.sex
        },
        rule: {
          nickName: [{required: true, message: '请输入昵称', trigger: 'blur'}],
          sex: [{required: true, message: '请选择性别', trigger: 'blur'}]
        }
      },
      modifyPassForm: {
        data: {
          oldPass: '',
          newPass: '',
          confirmedPass: ''
        },
        rules: {
          oldPass: [{required: true, message: '请输入原密码', trigger: 'blur'}],
          newPass: [{required: true, message: '请输入新密码', trigger: 'blur'}],
          confirmedPass: [{
            validator: (rule, value, callback) => {
              if (this.modifyPassForm.data.newPass === this.modifyPassForm.data.confirmedPass) {
                callback();
              } else {
                callback(new Error('新密码和确认密码不一致'));
              }
            }, trigger: 'blur'
          }]
        }
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
  },
  methods: {
    modifyInfo() {
      this.$refs.modifyInfoForm.validate((valid) => {
        if (valid) {
          modifyInfo(this.modifyInfoForm.data).then(res => {
            if (res.data) {
              this.$store.commit('user/SET_NAME', this.modifyInfoForm.data.nickName);
              this.$store.commit('user/SET_USER_INFO', {...this.userInfo, ...this.modifyInfoForm.data});
            }
          });
        }
      });
    },
    modifyPass() {
      this.$refs.modifyPassForm.validate((valid) => {
        if (valid) {
          modifyPass(this.modifyPassForm.data);
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.user-center-container {

  .user-center-info {
    margin-bottom: 20px;
  }
}
</style>
