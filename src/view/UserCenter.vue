<template>
  <div class="user-center-container">
    <el-tabs type="border-card" class="user-center-info">
      <el-tab-pane label="个人资料">
        <el-descriptions :column="1" :border="true">
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
            {{ userInfo.roles.toString() }}
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
    <el-tabs type="border-card" class="user-center-modify">
      <el-tab-pane label="修改信息">
        <el-form ref="modifyInfoForm" :model="modifyInfoForm" :rules="modifyInfoFormRules">
          <el-form-item label="昵称" prop="nickName">
            <el-input v-model="modifyInfoForm.nickName" />
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="modifyInfoForm.phone" />
          </el-form-item>
          <el-form-item label="邮箱" prop="mail">
            <el-input v-model="modifyInfoForm.mail" />
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio-group v-model="modifyInfoForm.sex">
              <el-radio label="女" />
              <el-radio label="男" />
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="modifyInfo">修改</el-button>
            <el-button @click="resetForm('modifyInfoForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="修改密码">
        <el-form ref="modifyPassForm" :model="modifyPassForm" :rules="modifyPassFormRules">
          <el-form-item label="原密码" prop="oldPass">
            <el-input v-model="modifyPassForm.oldPass" show-password />
          </el-form-item>
          <el-form-item label="新密码" prop="newPass">
            <el-input v-model="modifyPassForm.newPass" show-password />
          </el-form-item>
          <el-form-item label="确认新密码" prop="confirmedPass">
            <el-input v-model="modifyPassForm.confirmedPass" show-password />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="modifyPass">修改</el-button>
            <el-button @click="resetForm('modifyPassForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
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
        nickName: this.$store.getters.userInfo.nickName,
        phone: this.$store.getters.userInfo.phone,
        mail: this.$store.getters.userInfo.mail,
        sex: this.$store.getters.userInfo.sex
      },
      modifyInfoFormRules: {
        nickName: [{required: true, message: '请输入昵称', trigger: 'blur'}],
        sex: [{required: true, message: '请选择性别', trigger: 'blur'}]
      },
      modifyPassForm: {
        oldPass: '',
        newPass: '',
        confirmedPass: ''
      },
      modifyPassFormRules: {
        oldPass: [{required: true, message: '请输入原密码', trigger: 'blur'}],
        newPass: [{required: true, message: '请输入新密码', trigger: 'blur'}],
        confirmedPass: [{validator: (rule, value, callback) => {
          if (this.modifyPassForm.newPass === this.modifyPassForm.confirmedPass) {
            callback();
          } else {
            callback(new Error('新密码和确认密码不一致'));
          }
        }, trigger: 'blur'}]
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
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    modifyInfo() {
      this.$refs['modifyInfoForm'].validate((valid) => {
        if (valid) {
          modifyInfo(this.modifyInfoForm).then(res => {
            if (res.data) {
              this.$store.commit('user/SET_NAME', this.modifyInfoForm.nickName);
              this.$store.commit('user/SET_USER_INFO', {...this.userInfo, ...this.modifyInfoForm});
            }
          });
        }
      });
    },
    modifyPass() {
      this.$refs['modifyPassForm'].validate((valid) => {
        if (valid) {
          modifyPass(this.modifyPassForm);
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.user-center-container {
  margin: 10px 20px 0;
  overflow: hidden;

  .user-center-info {
    display: inline-block;
    width: 28%;
    margin-right: 1%;
  }

  .user-center-modify {
    display: inline-block;
    width: 70%;
    vertical-align: top;
  }
}
</style>
