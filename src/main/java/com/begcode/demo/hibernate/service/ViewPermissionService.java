package com.begcode.demo.hibernate.service;

import com.begcode.demo.hibernate.repository.ViewPermissionRepository;
import com.begcode.demo.hibernate.service.base.ViewPermissionBaseService;
import com.begcode.demo.hibernate.service.mapper.ViewPermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ViewPermission}.
 */
@Service
@Transactional
public class ViewPermissionService extends ViewPermissionBaseService {

    private final Logger log = LoggerFactory.getLogger(ViewPermissionService.class);

    public ViewPermissionService(
        ViewPermissionRepository viewPermissionRepository,
        CacheManager cacheManager,
        ViewPermissionMapper viewPermissionMapper
    ) {
        super(viewPermissionRepository, cacheManager, viewPermissionMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
