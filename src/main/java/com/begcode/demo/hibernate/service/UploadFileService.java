package com.begcode.demo.hibernate.service;

import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.begcode.demo.hibernate.repository.UploadFileRepository;
import com.begcode.demo.hibernate.service.base.UploadFileBaseService;
import com.begcode.demo.hibernate.service.mapper.UploadFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UploadFile}.
 */
@Service
@Transactional
public class UploadFileService extends UploadFileBaseService {

    private final Logger log = LoggerFactory.getLogger(UploadFileService.class);

    public UploadFileService(
        FileStorageService fileStorageService,
        UploadFileRepository uploadFileRepository,
        CacheManager cacheManager,
        UploadFileMapper uploadFileMapper
    ) {
        super(fileStorageService, uploadFileRepository, cacheManager, uploadFileMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
