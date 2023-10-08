package com.begcode.demo.hibernate.system.service;

import com.begcode.demo.hibernate.system.repository.SmsTemplateRepository;
import com.begcode.demo.hibernate.system.service.base.SmsTemplateBaseService;
import com.begcode.demo.hibernate.system.service.mapper.SmsTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SmsTemplate}.
 */
@Service
@Transactional
public class SmsTemplateService extends SmsTemplateBaseService {

    private final Logger log = LoggerFactory.getLogger(SmsTemplateService.class);

    public SmsTemplateService(SmsTemplateRepository smsTemplateRepository, CacheManager cacheManager, SmsTemplateMapper smsTemplateMapper) {
        super(smsTemplateRepository, cacheManager, smsTemplateMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
