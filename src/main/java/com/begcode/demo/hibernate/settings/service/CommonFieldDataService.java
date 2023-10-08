package com.begcode.demo.hibernate.settings.service;

import com.begcode.demo.hibernate.settings.repository.CommonFieldDataRepository;
import com.begcode.demo.hibernate.settings.service.base.CommonFieldDataBaseService;
import com.begcode.demo.hibernate.settings.service.mapper.CommonFieldDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommonFieldData}.
 */
@Service
@Transactional
public class CommonFieldDataService extends CommonFieldDataBaseService {

    private final Logger log = LoggerFactory.getLogger(CommonFieldDataService.class);

    public CommonFieldDataService(
        CommonFieldDataRepository commonFieldDataRepository,
        CacheManager cacheManager,
        CommonFieldDataMapper commonFieldDataMapper
    ) {
        super(commonFieldDataRepository, cacheManager, commonFieldDataMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
