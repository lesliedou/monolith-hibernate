package com.begcode.demo.hibernate.service.mapper;

import com.begcode.demo.hibernate.domain.ApiPermission;
import com.begcode.demo.hibernate.domain.Authority;
import com.begcode.demo.hibernate.domain.ViewPermission;
import com.begcode.demo.hibernate.service.dto.ApiPermissionDTO;
import com.begcode.demo.hibernate.service.dto.AuthorityDTO;
import com.begcode.demo.hibernate.service.dto.ViewPermissionDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Authority} and its DTO {@link AuthorityDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, Authority> {
    @Mapping(target = "viewPermissions", source = "viewPermissions", qualifiedByName = "viewPermissionTextSet")
    @Mapping(target = "apiPermissions", source = "apiPermissions", qualifiedByName = "apiPermissionNameSet")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "authorityName")
    AuthorityDTO toDto(Authority s);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(target = "removeViewPermissions", ignore = true)
    @Mapping(target = "removeApiPermissions", ignore = true)
    Authority toEntity(AuthorityDTO authorityDTO);

    @Named("authorityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AuthorityDTO toDtoAuthorityName(Authority authority);

    @Named("authorityNameSet")
    default Set<AuthorityDTO> toDtoAuthorityNameSet(Set<Authority> authority) {
        return authority.stream().map(this::toDtoAuthorityName).collect(Collectors.toSet());
    }

    @Named("viewPermissionText")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    ViewPermissionDTO toDtoViewPermissionText(ViewPermission viewPermission);

    @Named("viewPermissionTextSet")
    default Set<ViewPermissionDTO> toDtoViewPermissionTextSet(Set<ViewPermission> viewPermission) {
        return viewPermission.stream().map(this::toDtoViewPermissionText).collect(Collectors.toSet());
    }

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
