package com.begcode.demo.hibernate.system.web.rest;

import com.begcode.demo.hibernate.system.repository.AnnouncementRepository;
import com.begcode.demo.hibernate.system.service.AnnouncementQueryService;
import com.begcode.demo.hibernate.system.service.AnnouncementRecordQueryService;
import com.begcode.demo.hibernate.system.service.AnnouncementRecordService;
import com.begcode.demo.hibernate.system.service.AnnouncementService;
import com.begcode.demo.hibernate.system.web.rest.base.AnnouncementBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.system.domain.Announcement}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "announcements", description = "系统通告API接口")
public class AnnouncementResource extends AnnouncementBaseResource {

    private final Logger log = LoggerFactory.getLogger(AnnouncementResource.class);

    public AnnouncementResource(
        AnnouncementRecordService announcementRecordService,
        AnnouncementRecordQueryService announcementRecordQueryService,
        AnnouncementService announcementService,
        AnnouncementRepository announcementRepository,
        AnnouncementQueryService announcementQueryService
    ) {
        super(
            announcementRecordService,
            announcementRecordQueryService,
            announcementService,
            announcementRepository,
            announcementQueryService
        );
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
