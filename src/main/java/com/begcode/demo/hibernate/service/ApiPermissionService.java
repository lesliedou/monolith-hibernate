package com.begcode.demo.hibernate.service;

import com.begcode.demo.hibernate.repository.ApiPermissionRepository;
import com.begcode.demo.hibernate.service.base.ApiPermissionBaseService;
import com.begcode.demo.hibernate.service.mapper.ApiPermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Service Implementation for managing {@link ApiPermission}.
 */
@Service
@Transactional
public class ApiPermissionService extends ApiPermissionBaseService {

    private final Logger log = LoggerFactory.getLogger(ApiPermissionService.class);

    public ApiPermissionService(
        ApiPermissionRepository apiPermissionRepository,
        CacheManager cacheManager,
        ApiPermissionMapper apiPermissionMapper,
        RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        super(apiPermissionRepository, cacheManager, apiPermissionMapper, requestMappingHandlerMapping);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
