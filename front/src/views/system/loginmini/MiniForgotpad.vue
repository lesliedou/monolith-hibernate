<template>
  <div class="aui-content">
    <div class="aui-container">
      <div class="aui-form">
        <div class="aui-image">
          <div class="aui-image-text">
            <img :src="adTextImg" alt="" />
          </div>
        </div>
        <div class="aui-formBox">
          <div class="aui-formWell">
            <div class="aui-step-box">
              <div class="aui-step-item" :class="activeKey === 1 ? 'activeStep' : ''">
                <div class="aui-step-tags">
                  <em>1</em>
                  <p>Translation missing for sys.login.authentication</p>
                </div>
              </div>
              <div class="aui-step-item" :class="activeKey === 2 ? 'activeStep' : ''">
                <div class="aui-step-tags">
                  <em>2</em>
                  <p>Translation missing for sys.login.resetLoginPassword</p>
                </div>
              </div>
              <div class="aui-step-item" :class="activeKey === 3 ? 'activeStep' : ''">
                <div class="aui-step-tags">
                  <em>3</em>
                  <p>Translation missing for sys.login.resetSuccess</p>
                </div>
              </div>
            </div>
            <div class="" style="height: 230px; position: relative">
              <a-form ref="formRef" :model="formData" v-if="activeKey === 1">
                <!-- 身份验证 begin -->
                <div class="aui-account aui-account-line aui-forgot">
                  <a-form-item>
                    <div class="aui-input-line">
                      <a-input type="text" placeholder="手机号码" v-model:value="formData.mobile" />
                    </div>
                  </a-form-item>
                  <div class="aui-input-line">
                    <a-form-item>
                      <a-input type="text" placeholder="短信验证码" v-model:value="formData.smscode" />
                    </a-form-item>
                    <div v-if="showInterval" class="aui-code-line" @click="getLoginCode">获取验证码</div>
                    <div v-else class="aui-code-line">{{ `${unref(timeRuning)}秒后重新获取` }}</div>
                  </div>
                </div>
                <!-- 身份验证 end -->
              </a-form>
              <a-form ref="pwdFormRef" :model="pwdFormData" v-else-if="activeKey === 2">
                <!-- 重置密码 begin -->
                <div class="aui-account aui-account-line aui-forgot">
                  <a-form-item>
                    <div class="aui-input-line">
                      <a-input type="password" placeholder="请输入密码" v-model:value="pwdFormData.password" />
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <div class="aui-input-line">
                      <a-input type="password" placeholder="确认密码" v-model:value="pwdFormData.confirmPassword" />
                    </div>
                  </a-form-item>
                </div>
                <!-- 重置密码 end -->
              </a-form>
              <!-- 重置成功 begin -->
              <div class="aui-success" v-else>
                <div class="aui-success-icon">
                  <img :src="successImg" />
                </div>
                <h3>恭喜您，重置密码成功！</h3>
              </div>
              <!-- 重置成功 end -->
            </div>
            <div class="aui-formButton" style="padding-bottom: 40px">
              <div class="aui-flex" v-if="activeKey === 1 || activeKey === 2">
                <a class="aui-link-login aui-flex-box" @click="nextStepClick">Translation missing for sys.login.nextStep</a>
              </div>
              <div class="aui-flex" v-else>
                <a class="aui-linek-code aui-flex-box" @click="toLogin">Translation missing for sys.login.goToLogin</a>
              </div>
              <div class="aui-flex">
                <a class="aui-linek-code aui-flex-box" @click="goBack">返回</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" name="mini-forgotpad" setup>
import { reactive, ref, toRaw, unref } from 'vue';
import { SmsEnum, useFormRules, useFormValid, useLoginState } from '@/views/sys/login/useLogin';
import { useMessage } from '@/hooks/web/useMessage';
import { getImageCaptcha, passwordChange, phoneVerify } from '@/api-service/sys/user';
import adTextImg from '@/assets/loginmini/icon/jeecg_ad_text.png';
import successImg from '@/assets/loginmini/icon/icon-success.png';

//下一步控制
const activeKey = ref<number>(1);
const { notification, createMessage, createErrorModal } = useMessage();
//是否显示获取验证码
const showInterval = ref<boolean>(true);
//60s
const timeRuning = ref<number>(60);
//定时器
const timer = ref<any>(null);
const formRef = ref();
const pwdFormRef = ref();
//账号数据
const accountInfo = reactive<any>({});
//手机号表单
const formData = reactive({
  mobile: '',
  smscode: '',
});
//密码表单
const pwdFormData = reactive<any>({
  password: '',
  confirmPassword: '',
});
const emit = defineEmits(['go-back', 'success', 'register']);

/**
 * 下一步
 */
async function handleNext() {
  if (!formData.mobile) {
    createMessage.warn('请输入手机号码');
    return;
  }
  if (!formData.smscode) {
    createMessage.warn('请输入验证码');
    return;
  }
  const resultInfo = await phoneVerify(
    toRaw({
      phone: formData.mobile,
      smscode: formData.smscode,
    }),
  );
  if (resultInfo.success) {
    Object.assign(accountInfo, {
      username: resultInfo.result.username,
      phone: formData.mobile,
      smscode: formData.smscode,
    });
    activeKey.value = 2;
    setTimeout(() => {
      pwdFormRef.value.resetFields();
    }, 300);
  } else {
    notification.error({
      message: '错误提示',
      description: resultInfo.message || '网络异常，请检查您的网络连接是否正常!',
      duration: 3,
    });
  }
}

/**
 * 完成修改密码
 */
async function finishedPwd() {
  if (!pwdFormData.password) {
    createMessage.warn('请输入密码');
    return;
  }
  if (!pwdFormData.confirmPassword) {
    createMessage.warn('确认密码');
    return;
  }
  if (pwdFormData.password !== pwdFormData.confirmPassword) {
    createMessage.warn('两次输入密码不一致');
    return;
  }
  const resultInfo = await passwordChange(
    toRaw({
      username: accountInfo.username,
      password: pwdFormData.password,
      smscode: accountInfo.smscode,
      phone: accountInfo.phone,
    }),
  );
  if (resultInfo.success) {
    accountInfo.password = pwdFormData.password;
    //修改密码
    activeKey.value = 3;
  } else {
    //错误提示
    createErrorModal({
      title: '错误提示',
      content: resultInfo.message || '网络异常，请检查您的网络连接是否正常!',
    });
  }
}
/**
 * 下一步
 */
function nextStepClick() {
  if (unref(activeKey) === 1) {
    handleNext();
  } else if (unref(activeKey) === 2) {
    finishedPwd();
  }
}

/**
 * 去登录
 */
function toLogin() {
  emit('success', { username: accountInfo.username, password: accountInfo.password });
  initForm();
}

/**
 * 返回
 */
function goBack() {
  emit('go-back');
  initForm();
}

/**
 * 获取手机验证码
 */
async function getLoginCode() {
  if (!formData.mobile) {
    createMessage.warn('请输入手机号码');
    return;
  }
  const result = await getImageCaptcha({ mobile: formData.mobile, smsmode: SmsEnum.FORGET_PASSWORD });
  if (result) {
    const TIME_COUNT = 60;
    if (!unref(timer)) {
      timeRuning.value = TIME_COUNT;
      showInterval.value = false;
      timer.value = setInterval(() => {
        if (unref(timeRuning) > 0 && unref(timeRuning) <= TIME_COUNT) {
          timeRuning.value = timeRuning.value - 1;
        } else {
          showInterval.value = true;
          clearInterval(unref(timer));
          timer.value = null;
        }
      }, 1000);
    }
  }
}

/**
 * 初始化表单
 */
function initForm() {
  activeKey.value = 1;
  Object.assign(formData, { phone: '', smscode: '' });
  Object.assign(pwdFormData, { password: '', confirmPassword: '' });
  Object.assign(accountInfo, {});
  if (unref(timer)) {
    clearInterval(unref(timer));
    timer.value = null;
    showInterval.value = true;
  }
  setTimeout(() => {
    formRef.value.resetFields();
  }, 300);
}

defineExpose({
  initForm,
});
</script>
<style lang="less" scoped>
@import '@/assets/loginmini/style/home.less';
@import '@/assets/loginmini/style/base.less';
</style>
