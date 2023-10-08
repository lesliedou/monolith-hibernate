package com.begcode.demo.hibernate.service;

import com.begcode.demo.hibernate.repository.BusinessTypeRepository;
import com.begcode.demo.hibernate.service.base.BusinessTypeBaseService;
import com.begcode.demo.hibernate.service.mapper.BusinessTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessType}.
 */
@Service
@Transactional
public class BusinessTypeService extends BusinessTypeBaseService {

    private final Logger log = LoggerFactory.getLogger(BusinessTypeService.class);

    public BusinessTypeService(
        BusinessTypeRepository businessTypeRepository,
        CacheManager cacheManager,
        BusinessTypeMapper businessTypeMapper
    ) {
        super(businessTypeRepository, cacheManager, businessTypeMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
