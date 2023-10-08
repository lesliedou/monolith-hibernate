package com.begcode.demo.hibernate.settings.web.rest;

import com.begcode.demo.hibernate.settings.repository.FillRuleItemRepository;
import com.begcode.demo.hibernate.settings.service.FillRuleItemQueryService;
import com.begcode.demo.hibernate.settings.service.FillRuleItemService;
import com.begcode.demo.hibernate.settings.web.rest.base.FillRuleItemBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.settings.domain.FillRuleItem}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "fill-rule-items", description = "填充规则条目API接口")
public class FillRuleItemResource extends FillRuleItemBaseResource {

    private final Logger log = LoggerFactory.getLogger(FillRuleItemResource.class);

    public FillRuleItemResource(
        FillRuleItemService fillRuleItemService,
        FillRuleItemRepository fillRuleItemRepository,
        FillRuleItemQueryService fillRuleItemQueryService
    ) {
        super(fillRuleItemService, fillRuleItemRepository, fillRuleItemQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
