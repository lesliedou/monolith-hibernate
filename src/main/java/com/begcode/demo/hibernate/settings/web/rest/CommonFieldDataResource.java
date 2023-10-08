package com.begcode.demo.hibernate.settings.web.rest;

import com.begcode.demo.hibernate.settings.repository.CommonFieldDataRepository;
import com.begcode.demo.hibernate.settings.service.CommonFieldDataQueryService;
import com.begcode.demo.hibernate.settings.service.CommonFieldDataService;
import com.begcode.demo.hibernate.settings.web.rest.base.CommonFieldDataBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.settings.domain.CommonFieldData}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "common-field-data", description = "通用字段数据API接口")
public class CommonFieldDataResource extends CommonFieldDataBaseResource {

    private final Logger log = LoggerFactory.getLogger(CommonFieldDataResource.class);

    public CommonFieldDataResource(
        CommonFieldDataService commonFieldDataService,
        CommonFieldDataRepository commonFieldDataRepository,
        CommonFieldDataQueryService commonFieldDataQueryService
    ) {
        super(commonFieldDataService, commonFieldDataRepository, commonFieldDataQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
