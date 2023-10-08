package com.begcode.demo.hibernate.service;

import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.begcode.demo.hibernate.repository.UploadImageRepository;
import com.begcode.demo.hibernate.service.base.UploadImageBaseService;
import com.begcode.demo.hibernate.service.mapper.UploadImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UploadImage}.
 */
@Service
@Transactional
public class UploadImageService extends UploadImageBaseService {

    private final Logger log = LoggerFactory.getLogger(UploadImageService.class);

    public UploadImageService(
        FileStorageService fileStorageService,
        UploadImageRepository uploadImageRepository,
        CacheManager cacheManager,
        UploadImageMapper uploadImageMapper
    ) {
        super(fileStorageService, uploadImageRepository, cacheManager, uploadImageMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
