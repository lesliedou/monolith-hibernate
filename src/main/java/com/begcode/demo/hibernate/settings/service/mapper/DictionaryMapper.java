package com.begcode.demo.hibernate.settings.service.mapper;

import com.begcode.demo.hibernate.service.mapper.EntityMapper;
import com.begcode.demo.hibernate.settings.domain.Dictionary;
import com.begcode.demo.hibernate.settings.service.dto.DictionaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dictionary} and its DTO {@link DictionaryDTO}.
 */
@Mapper(componentModel = "spring")
public interface DictionaryMapper extends EntityMapper<DictionaryDTO, Dictionary> {
    @Mapping(target = "removeItems", ignore = true)
    Dictionary toEntity(DictionaryDTO dictionaryDTO);
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
