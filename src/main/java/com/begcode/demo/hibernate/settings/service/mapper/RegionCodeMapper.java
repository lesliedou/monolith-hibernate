package com.begcode.demo.hibernate.settings.service.mapper;

import com.begcode.demo.hibernate.service.mapper.EntityMapper;
import com.begcode.demo.hibernate.settings.domain.RegionCode;
import com.begcode.demo.hibernate.settings.service.dto.RegionCodeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegionCode} and its DTO {@link RegionCodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionCodeMapper extends EntityMapper<RegionCodeDTO, RegionCode> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "regionCodeName")
    RegionCodeDTO toDto(RegionCode s);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    RegionCode toEntity(RegionCodeDTO regionCodeDTO);

    @Named("regionCodeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RegionCodeDTO toDtoRegionCodeName(RegionCode regionCode);

    @Named("regionCodeNameSet")
    default Set<RegionCodeDTO> toDtoRegionCodeNameSet(Set<RegionCode> regionCode) {
        return regionCode.stream().map(this::toDtoRegionCodeName).collect(Collectors.toSet());
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
