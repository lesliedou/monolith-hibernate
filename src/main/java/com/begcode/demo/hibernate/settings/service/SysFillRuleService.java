package com.begcode.demo.hibernate.settings.service;

import com.begcode.demo.hibernate.settings.repository.SysFillRuleRepository;
import com.begcode.demo.hibernate.settings.service.base.SysFillRuleBaseService;
import com.begcode.demo.hibernate.settings.service.mapper.SysFillRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SysFillRule}.
 */
@Service
@Transactional
public class SysFillRuleService extends SysFillRuleBaseService {

    private final Logger log = LoggerFactory.getLogger(SysFillRuleService.class);

    public SysFillRuleService(SysFillRuleRepository sysFillRuleRepository, CacheManager cacheManager, SysFillRuleMapper sysFillRuleMapper) {
        super(sysFillRuleRepository, cacheManager, sysFillRuleMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
