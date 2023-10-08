package com.begcode.demo.hibernate.service.mapper;

import com.begcode.demo.hibernate.domain.ResourceCategory;
import com.begcode.demo.hibernate.service.dto.ResourceCategoryDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ResourceCategory} and its DTO {@link ResourceCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResourceCategoryMapper extends EntityMapper<ResourceCategoryDTO, ResourceCategory> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "resourceCategoryTitle")
    ResourceCategoryDTO toDto(ResourceCategory s);

    @Mapping(target = "children", ignore = true)
    @Mapping(target = "removeChildren", ignore = true)
    ResourceCategory toEntity(ResourceCategoryDTO resourceCategoryDTO);

    @Named("resourceCategoryTitle")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ResourceCategoryDTO toDtoResourceCategoryTitle(ResourceCategory resourceCategory);

    @Named("resourceCategoryTitleSet")
    default Set<ResourceCategoryDTO> toDtoResourceCategoryTitleSet(Set<ResourceCategory> resourceCategory) {
        return resourceCategory.stream().map(this::toDtoResourceCategoryTitle).collect(Collectors.toSet());
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
