package com.begcode.demo.hibernate.oss.service;

import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.begcode.demo.hibernate.oss.repository.OssConfigRepository;
import com.begcode.demo.hibernate.oss.service.base.OssConfigBaseService;
import com.begcode.demo.hibernate.oss.service.mapper.OssConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OssConfig}.
 */
@Service
@Transactional
public class OssConfigService extends OssConfigBaseService {

    private final Logger log = LoggerFactory.getLogger(OssConfigService.class);

    public OssConfigService(
        FileStorageService fileStorageService,
        OssConfigRepository ossConfigRepository,
        CacheManager cacheManager,
        OssConfigMapper ossConfigMapper
    ) {
        super(fileStorageService, ossConfigRepository, cacheManager, ossConfigMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
