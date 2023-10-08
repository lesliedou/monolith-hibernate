package com.begcode.demo.hibernate.service.mapper;

import com.begcode.demo.hibernate.domain.ApiPermission;
import com.begcode.demo.hibernate.service.dto.ApiPermissionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApiPermission} and its DTO {@link ApiPermissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApiPermissionMapper extends EntityMapper<ApiPermissionDTO, ApiPermission> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "apiPermissionName")
    ApiPermissionDTO toDto(ApiPermission s);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    ApiPermission toEntity(ApiPermissionDTO apiPermissionDTO);

    @Named("apiPermissionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ApiPermissionDTO toDtoApiPermissionName(ApiPermission apiPermission);

    @Named("apiPermissionNameSet")
    default Set<ApiPermissionDTO> toDtoApiPermissionNameSet(Set<ApiPermission> apiPermission) {
        return apiPermission.stream().map(this::toDtoApiPermissionName).collect(Collectors.toSet());
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
