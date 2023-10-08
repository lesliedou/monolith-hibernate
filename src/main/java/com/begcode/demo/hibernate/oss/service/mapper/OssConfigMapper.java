package com.begcode.demo.hibernate.oss.service.mapper;

import com.begcode.demo.hibernate.oss.domain.OssConfig;
import com.begcode.demo.hibernate.oss.service.dto.OssConfigDTO;
import com.begcode.demo.hibernate.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OssConfig} and its DTO {@link OssConfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface OssConfigMapper extends EntityMapper<OssConfigDTO, OssConfig> {
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
