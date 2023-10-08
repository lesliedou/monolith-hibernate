package com.begcode.demo.hibernate.service;

import com.begcode.demo.hibernate.repository.UReportFileRepository;
import com.begcode.demo.hibernate.service.base.UReportFileBaseService;
import com.begcode.demo.hibernate.service.mapper.UReportFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UReportFile}.
 */
@Service
@Transactional
public class UReportFileService extends UReportFileBaseService {

    private final Logger log = LoggerFactory.getLogger(UReportFileService.class);

    public UReportFileService(UReportFileRepository uReportFileRepository, CacheManager cacheManager, UReportFileMapper uReportFileMapper) {
        super(uReportFileRepository, cacheManager, uReportFileMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
