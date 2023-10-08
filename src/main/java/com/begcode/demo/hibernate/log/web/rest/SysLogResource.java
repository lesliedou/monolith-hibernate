package com.begcode.demo.hibernate.log.web.rest;

import com.begcode.demo.hibernate.log.repository.SysLogRepository;
import com.begcode.demo.hibernate.log.service.SysLogQueryService;
import com.begcode.demo.hibernate.log.service.SysLogService;
import com.begcode.demo.hibernate.log.web.rest.base.SysLogBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.log.domain.SysLog}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "sys-logs", description = "系统日志API接口")
public class SysLogResource extends SysLogBaseResource {

    private final Logger log = LoggerFactory.getLogger(SysLogResource.class);

    public SysLogResource(SysLogService sysLogService, SysLogRepository sysLogRepository, SysLogQueryService sysLogQueryService) {
        super(sysLogService, sysLogRepository, sysLogQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
