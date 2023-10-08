package com.begcode.demo.hibernate.system.web.rest;

import com.begcode.demo.hibernate.system.repository.SmsSupplierRepository;
import com.begcode.demo.hibernate.system.service.SmsSupplierQueryService;
import com.begcode.demo.hibernate.system.service.SmsSupplierService;
import com.begcode.demo.hibernate.system.web.rest.base.SmsSupplierBaseResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.system.domain.SmsSupplier}的REST Controller。
 */
@RestController
@RequestMapping("/api")
@Tag(name = "sms-suppliers", description = "短信服务配置API接口")
public class SmsSupplierResource extends SmsSupplierBaseResource {

    private final Logger log = LoggerFactory.getLogger(SmsSupplierResource.class);

    public SmsSupplierResource(
        SmsSupplierService smsSupplierService,
        SmsSupplierRepository smsSupplierRepository,
        SmsSupplierQueryService smsSupplierQueryService
    ) {
        super(smsSupplierService, smsSupplierRepository, smsSupplierQueryService);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
