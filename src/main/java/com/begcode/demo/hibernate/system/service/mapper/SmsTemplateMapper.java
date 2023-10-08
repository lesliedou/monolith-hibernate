package com.begcode.demo.hibernate.system.service.mapper;

import com.begcode.demo.hibernate.service.mapper.EntityMapper;
import com.begcode.demo.hibernate.system.domain.SmsTemplate;
import com.begcode.demo.hibernate.system.service.dto.SmsTemplateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SmsTemplate} and its DTO {@link SmsTemplateDTO}.
 */
@Mapper(componentModel = "spring")
public interface SmsTemplateMapper extends EntityMapper<SmsTemplateDTO, SmsTemplate> {
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    SmsTemplate toEntity(SmsTemplateDTO smsTemplateDTO);
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
