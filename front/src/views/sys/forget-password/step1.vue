<template>
  <Form class="p-4 enter-x" :model="formData" :rules="getFormRules" ref="formRef">
    <FormItem name="mobile" class="enter-x">
      <Input size="large" v-model:value="formData.mobile" placeholder="手机号码" />
    </FormItem>
    <FormItem name="sms" class="enter-x">
      <CountdownInput size="large" v-model:value="formData.sms" placeholder="短信验证码" :sendCodeApi="sendCodeApi" />
    </FormItem>
    <FormItem class="enter-x">
      <Button type="primary" size="large" block @click="handleNext" :loading="loading"> 下一步 </Button>
      <Button size="large" block class="mt-4" @click="handleBackLogin"> 返回 </Button>
    </FormItem>
  </Form>
</template>
<script lang="ts">
import { defineComponent, reactive, ref, computed, unref, toRaw } from 'vue';

import { Form, Input, Button } from 'ant-design-vue';
import { CountdownInput } from '@/components/CountDown';

import { useMessage } from '@/hooks/web/useMessage';
import { useLoginState, useFormRules, useFormValid, LoginStateEnum, SmsEnum } from '../login/useLogin';
import { phoneVerify, getSmsCaptcha } from '@/api-service/sys/user';

export default defineComponent({
  name: 'step1',
  components: {
    Button,
    Form,
    FormItem: Form.Item,
    Input,
    CountdownInput,
  },
  emits: ['nextStep'],
  setup(_, { emit }) {
    const { handleBackLogin } = useLoginState();
    const { notification } = useMessage();

    const formRef = ref();
    const { validForm } = useFormValid(formRef);
    const { getFormRules } = useFormRules();

    const loading = ref(false);
    const formData = reactive({
      mobile: '',
      sms: '',
    });

    /**
     * 下一步
     */
    async function handleNext() {
      const data = await validForm();
      if (!data) return;
      const resultInfo = await phoneVerify(
        toRaw({
          phone: data.mobile,
          smscode: data.sms,
        }),
      );
      if (resultInfo.success) {
        let accountInfo = {
          username: resultInfo.result.username,
          phone: data.mobile,
          smscode: resultInfo.result.smscode,
        };
        emit('nextStep', accountInfo);
      } else {
        notification.error({
          message: t('sys.api.errorTip'),
          description: resultInfo.message || t('sys.api.networkExceptionMsg'),
          duration: 3,
        });
      }
    }
    //倒计时执行前的函数
    function sendCodeApi() {
      return getSmsCaptcha({ mobile: formData.mobile, smsmode: SmsEnum.FORGET_PASSWORD });
    }
    return {
      formRef,
      formData,
      getFormRules,
      handleNext,
      loading,
      handleBackLogin,
      sendCodeApi,
    };
  },
});
</script>
