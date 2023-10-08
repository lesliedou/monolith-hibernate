<template>
  <div class="aui-content">
    <div class="aui-container">
      <div class="aui-form">
        <div class="aui-image">
          <div class="aui-image-text">
            <img :src="jeecgAdTextImg" alt="" />
          </div>
        </div>
        <div class="aui-formBox">
          <div class="aui-formWell">
            <a-form ref="formRef" :model="formData">
              <div class="aui-flex aui-form-nav aui-clear-left" style="padding-bottom: 21px">
                <div class="aui-flex-box activeNav on">注册</div>
              </div>
              <div class="aui-form-box">
                <div class="aui-account aui-account-line">
                  <a-form-item>
                    <div class="aui-input-line">
                      <Icon class="aui-icon" icon="ant-design:user-outlined" />
                      <a-input class="fix-auto-fill" type="text" 账号 v-model:value="formData.username" />
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <div class="aui-input-line">
                      <Icon class="aui-icon" icon="ant-design:mobile-outlined" />
                      <a-input class="fix-auto-fill" type="text" placeholder="手机号码" v-model:value="formData.mobile" />
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <div class="aui-input-line">
                      <Icon class="aui-icon" icon="ant-design:mail-outlined" />
                      <a-input class="fix-auto-fill" type="text" placeholder="短信验证码" v-model:value="formData.smscode" />
                      <div v-if="showInterval" class="aui-code-line" @click="getLoginCode">获取验证码</div>
                      <div v-else class="aui-code-line">{{ `${unref(timeRuning)}秒后重新获取` }}</div>
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <div class="aui-input-line">
                      <Icon class="aui-icon" icon="ant-design:lock-outlined" />
                      <a-input
                        class="fix-auto-fill"
                        :type="pwdIndex === 'close' ? 'password' : 'text'"
                        placeholder="密码"
                        v-model:value="formData.password"
                      />
                      <div class="aui-eye">
                        <img :src="eyeKImg" alt="开启" v-if="pwdIndex === 'open'" @click="pwdClick('close')" />
                        <img :src="eyeGImg" alt="关闭" v-else-if="pwdIndex === 'close'" @click="pwdClick('open')" />
                      </div>
                    </div>
                  </a-form-item>
                  <a-form-item>
                    <div class="aui-input-line">
                      <Icon class="aui-icon" icon="ant-design:lock-outlined" />
                      <a-input
                        class="fix-auto-fill"
                        :type="confirmPwdIndex === 'close' ? 'password' : 'text'"
                        placeholder="确认密码"
                        v-model:value="formData.confirmPassword"
                      />
                      <div class="aui-eye">
                        <img :src="eyeKImg" alt="开启" v-if="confirmPwdIndex === 'open'" @click="confirmPwdClick('close')" />
                        <img :src="eyeGImg" alt="关闭" v-else-if="confirmPwdIndex === 'close'" @click="confirmPwdClick('open')" />
                      </div>
                    </div>
                  </a-form-item>
                  <a-form-item name="policy">
                    <div class="aui-flex">
                      <div class="aui-flex-box">
                        <div class="aui-choice">
                          <a-checkbox v-model:checked="formData.policy" />
                          <span style="color: #1b90ff; margin-left: 4px">我同意xxx隐私政策</span>
                        </div>
                      </div>
                    </div>
                  </a-form-item>
                </div>
              </div>
              <div class="aui-formButton">
                <div class="aui-flex">
                  <a class="aui-link-login aui-flex-box" @click="registerHandleClick">注册</a>
                </div>
                <div class="aui-flex">
                  <a class="aui-linek-code aui-flex-box" @click="goBackHandleClick">返回</a>
                </div>
              </div>
            </a-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup name="mini-register">
import { ref, reactive, unref, toRaw } from 'vue';
import { getSmsCaptcha, register } from '@/api-service/sys/user';
import { SmsEnum } from '@/views/sys/login/useLogin';
import { useMessage } from '@/hooks/web/useMessage';
import jeecgAdTextImg from '@/assets/loginmini/icon/jeecg_ad_text.png';
import eyeKImg from '@/assets/loginmini/icon/icon-eye-k.png';
import eyeGImg from '@/assets/loginmini/icon/icon-eye-g.png';
const { notification, createMessage } = useMessage();
const emit = defineEmits(['go-back', 'success', 'register']);
const formRef = ref();
const formData = reactive<any>({
  username: '',
  mobile: '',
  smscode: '',
  password: '',
  confirmPassword: '',
  policy: false,
});
//是否显示获取验证码
const showInterval = ref<boolean>(true);
//60s
const timeRuning = ref<number>(60);
//定时器
const timer = ref<any>(null);
//密码眼睛打开关闭
const pwdIndex = ref<string>('close');
//确认密码眼睛打开关闭
const confirmPwdIndex = ref<string>('close');

/**
 * 返回
 */
function goBackHandleClick() {
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
  const result = await getSmsCaptcha({ mobile: formData.mobile, smsmode: SmsEnum.REGISTER });
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

function registerHandleClick() {
  if (!formData.username) {
    createMessage.warn('请输入账号');
    return;
  }
  if (!formData.mobile) {
    createMessage.warn('请输入手机号码');
    return;
  }
  if (!formData.smscode) {
    createMessage.warn('请输入验证码');
    return;
  }
  if (!formData.password) {
    createMessage.warn('请输入密码');
    return;
  }
  if (!formData.confirmPassword) {
    createMessage.warn('确认密码');
    return;
  }
  if (formData.password !== formData.confirmPassword) {
    createMessage.warn('两次输入密码不一致');
    return;
  }
  if (!formData.policy) {
    createMessage.warn('勾选后才能注册');
    return;
  }
  registerAccount();
}

/**
 * 注册账号
 */
async function registerAccount() {
  try {
    const resultInfo = await register(
      toRaw({
        username: formData.username,
        password: formData.password,
        phone: formData.mobile,
        smscode: formData.smscode,
      }),
    );
    if (resultInfo && resultInfo.data.success) {
      notification.success({
        message: '成功提示',
        description: resultInfo.data.message || '注册成功',
        duration: 3,
      });
      emit('success', { username: formData.username, password: formData.password });
      initForm();
    } else {
      notification.warning({
        message: '错误提示',
        description: resultInfo.data.message || '网络异常，请检查您的网络连接是否正常!',
        duration: 3,
      });
    }
  } catch (error: any) {
    notification.error({
      message: '错误提示',
      description: error.message || '网络异常，请检查您的网络连接是否正常!',
      duration: 3,
    });
  }
}

/**
 * 初始化表单
 */
function initForm() {
  Object.assign(formData, { username: '', mobile: '', smscode: '', password: '', confirmPassword: '', policy: false });
  if (!unref(timer)) {
    showInterval.value = true;
    clearInterval(unref(timer));
    timer.value = null;
  }
  formRef.value.resetFields();
}

/**
 * 密码打开或关闭
 * @param value
 */
function pwdClick(value) {
  pwdIndex.value = value;
}

/**
 * 确认密码打开或关闭
 * @param value
 */
function confirmPwdClick(value) {
  confirmPwdIndex.value = value;
}

defineExpose({
  initForm,
});
</script>
<style lang="less" scoped>
@import '@/assets/loginmini/style/home.less';
@import '@/assets/loginmini/style/base.less';
.aui-input-line .aui-icon {
  position: absolute;
  z-index: 2;
  top: 10px;
  left: 10px;
  font-size: 20px !important;
}
</style>
