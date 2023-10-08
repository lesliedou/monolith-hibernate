package com.begcode.demo.hibernate.system.service;

import com.begcode.demo.hibernate.service.UserQueryService;
import com.begcode.demo.hibernate.system.repository.AnnouncementRecordRepository;
import com.begcode.demo.hibernate.system.repository.AnnouncementRepository;
import com.begcode.demo.hibernate.system.service.base.AnnouncementBaseService;
import com.begcode.demo.hibernate.system.service.mapper.AnnouncementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Announcement}.
 */
@Service
@Transactional
public class AnnouncementService extends AnnouncementBaseService {

    private final Logger log = LoggerFactory.getLogger(AnnouncementService.class);

    public AnnouncementService(
        UserQueryService userQueryService,
        AnnouncementRecordRepository announcementRecordRepository,
        AnnouncementRepository announcementRepository,
        CacheManager cacheManager,
        AnnouncementMapper announcementMapper
    ) {
        super(userQueryService, announcementRecordRepository, announcementRepository, cacheManager, announcementMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
