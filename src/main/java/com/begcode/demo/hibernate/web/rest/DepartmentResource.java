package com.begcode.demo.hibernate.web.rest;

import com.begcode.demo.hibernate.repository.DepartmentRepository;
import com.begcode.demo.hibernate.service.DepartmentQueryService;
import com.begcode.demo.hibernate.service.DepartmentService;
import com.begcode.demo.hibernate.web.rest.base.DepartmentBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.domain.Department}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "departments", description = "部门API接口")
public class DepartmentResource extends DepartmentBaseResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    public DepartmentResource(
        DepartmentService departmentService,
        DepartmentRepository departmentRepository,
        DepartmentQueryService departmentQueryService
    ) {
        super(departmentService, departmentRepository, departmentQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
