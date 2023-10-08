package com.begcode.demo.hibernate.settings.service.mapper;

import com.begcode.demo.hibernate.service.mapper.EntityMapper;
import com.begcode.demo.hibernate.settings.domain.SiteConfig;
import com.begcode.demo.hibernate.settings.service.dto.SiteConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteConfig} and its DTO {@link SiteConfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteConfigMapper extends EntityMapper<SiteConfigDTO, SiteConfig> {
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "removeItems", ignore = true)
    SiteConfig toEntity(SiteConfigDTO siteConfigDTO);
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
