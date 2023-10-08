package com.begcode.demo.hibernate.log.service;

import com.begcode.demo.hibernate.log.repository.SysLogRepository;
import com.begcode.demo.hibernate.log.service.base.SysLogBaseService;
import com.begcode.demo.hibernate.log.service.mapper.SysLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SysLog}.
 */
@Service
@Transactional
public class SysLogService extends SysLogBaseService {

    private final Logger log = LoggerFactory.getLogger(SysLogService.class);

    public SysLogService(SysLogRepository sysLogRepository, CacheManager cacheManager, SysLogMapper sysLogMapper) {
        super(sysLogRepository, cacheManager, sysLogMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
