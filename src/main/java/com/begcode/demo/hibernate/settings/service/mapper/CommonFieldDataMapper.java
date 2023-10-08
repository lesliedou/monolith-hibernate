package com.begcode.demo.hibernate.settings.service.mapper;

import com.begcode.demo.hibernate.service.mapper.EntityMapper;
import com.begcode.demo.hibernate.settings.domain.CommonFieldData;
import com.begcode.demo.hibernate.settings.service.dto.CommonFieldDataDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommonFieldData} and its DTO {@link CommonFieldDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommonFieldDataMapper extends EntityMapper<CommonFieldDataDTO, CommonFieldData> {
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
