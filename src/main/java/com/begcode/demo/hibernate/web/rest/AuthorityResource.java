package com.begcode.demo.hibernate.web.rest;

import com.begcode.demo.hibernate.repository.AuthorityRepository;
import com.begcode.demo.hibernate.service.AuthorityQueryService;
import com.begcode.demo.hibernate.service.AuthorityService;
import com.begcode.demo.hibernate.web.rest.base.AuthorityBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.domain.Authority}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "authorities", description = "角色API接口")
public class AuthorityResource extends AuthorityBaseResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    public AuthorityResource(
        AuthorityService authorityService,
        AuthorityRepository authorityRepository,
        AuthorityQueryService authorityQueryService
    ) {
        super(authorityService, authorityRepository, authorityQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
