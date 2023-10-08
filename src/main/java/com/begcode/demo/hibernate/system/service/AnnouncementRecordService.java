package com.begcode.demo.hibernate.system.service;

import com.begcode.demo.hibernate.system.repository.AnnouncementRecordRepository;
import com.begcode.demo.hibernate.system.service.base.AnnouncementRecordBaseService;
import com.begcode.demo.hibernate.system.service.mapper.AnnouncementRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnnouncementRecord}.
 */
@Service
@Transactional
public class AnnouncementRecordService extends AnnouncementRecordBaseService {

    private final Logger log = LoggerFactory.getLogger(AnnouncementRecordService.class);

    public AnnouncementRecordService(
        AnnouncementRecordRepository announcementRecordRepository,
        CacheManager cacheManager,
        AnnouncementRecordMapper announcementRecordMapper
    ) {
        super(announcementRecordRepository, cacheManager, announcementRecordMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
