package com.begcode.demo.hibernate.system.service;

import com.begcode.demo.hibernate.system.repository.SmsMessageRepository;
import com.begcode.demo.hibernate.system.service.base.SmsMessageBaseService;
import com.begcode.demo.hibernate.system.service.mapper.SmsMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SmsMessage}.
 */
@Service
@Transactional
public class SmsMessageService extends SmsMessageBaseService {

    private final Logger log = LoggerFactory.getLogger(SmsMessageService.class);

    public SmsMessageService(SmsMessageRepository smsMessageRepository, CacheManager cacheManager, SmsMessageMapper smsMessageMapper) {
        super(smsMessageRepository, cacheManager, smsMessageMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
