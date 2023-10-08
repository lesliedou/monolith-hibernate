package com.begcode.demo.hibernate.service;

import com.begcode.demo.hibernate.repository.ResourceCategoryRepository;
import com.begcode.demo.hibernate.service.base.ResourceCategoryBaseService;
import com.begcode.demo.hibernate.service.mapper.ResourceCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ResourceCategory}.
 */
@Service
@Transactional
public class ResourceCategoryService extends ResourceCategoryBaseService {

    private final Logger log = LoggerFactory.getLogger(ResourceCategoryService.class);

    public ResourceCategoryService(
        ResourceCategoryRepository resourceCategoryRepository,
        CacheManager cacheManager,
        ResourceCategoryMapper resourceCategoryMapper
    ) {
        super(resourceCategoryRepository, cacheManager, resourceCategoryMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
