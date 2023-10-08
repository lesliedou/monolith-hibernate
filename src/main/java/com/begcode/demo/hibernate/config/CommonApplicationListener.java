package com.begcode.demo.hibernate.config;

import com.begcode.demo.hibernate.oss.service.OssConfigService;
import com.begcode.demo.hibernate.system.service.SmsSupplierService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final SmsSupplierService smsSupplierService;

    private final OssConfigService ossConfigService;

    public CommonApplicationListener(SmsSupplierService smsSupplierService, OssConfigService ossConfigService) {
        this.smsSupplierService = smsSupplierService;
        this.ossConfigService = ossConfigService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ossConfigService.initPlatforms();
        smsSupplierService.initService();
    }
}
