package com.begcode.demo.hibernate.settings.service;

import com.begcode.demo.hibernate.settings.repository.RegionCodeRepository;
import com.begcode.demo.hibernate.settings.service.base.RegionCodeBaseService;
import com.begcode.demo.hibernate.settings.service.mapper.RegionCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RegionCode}.
 */
@Service
@Transactional
public class RegionCodeService extends RegionCodeBaseService {

    private final Logger log = LoggerFactory.getLogger(RegionCodeService.class);

    public RegionCodeService(RegionCodeRepository regionCodeRepository, CacheManager cacheManager, RegionCodeMapper regionCodeMapper) {
        super(regionCodeRepository, cacheManager, regionCodeMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
