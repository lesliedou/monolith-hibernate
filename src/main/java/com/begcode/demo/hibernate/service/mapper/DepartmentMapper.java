package com.begcode.demo.hibernate.service.mapper;

import com.begcode.demo.hibernate.domain.Authority;
import com.begcode.demo.hibernate.domain.Department;
import com.begcode.demo.hibernate.service.dto.AuthorityDTO;
import com.begcode.demo.hibernate.service.dto.DepartmentDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "authorityNameSet")
    @Mapping(target = "parent", source = "parent", qualifiedByName = "departmentName")
    DepartmentDTO toDto(Department s);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    @Mapping(target = "removeAuthorities", ignore = true)
    Department toEntity(DepartmentDTO departmentDTO);

    @Named("departmentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DepartmentDTO toDtoDepartmentName(Department department);

    @Named("departmentNameSet")
    default Set<DepartmentDTO> toDtoDepartmentNameSet(Set<Department> department) {
        return department.stream().map(this::toDtoDepartmentName).collect(Collectors.toSet());
    }

    @Named("authorityName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AuthorityDTO toDtoAuthorityName(Authority authority);

    @Named("authorityNameSet")
    default Set<AuthorityDTO> toDtoAuthorityNameSet(Set<Authority> authority) {
        return authority.stream().map(this::toDtoAuthorityName).collect(Collectors.toSet());
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
