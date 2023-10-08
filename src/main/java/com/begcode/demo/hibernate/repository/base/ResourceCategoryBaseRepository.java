package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.ResourceCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the ResourceCategory entity.
 */
@NoRepositoryBean
public interface ResourceCategoryBaseRepository extends JpaRepository<ResourceCategory, Long>, JpaSpecificationExecutor<ResourceCategory> {
    default Optional<ResourceCategory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ResourceCategory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ResourceCategory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select resourceCategory from ResourceCategory resourceCategory left join fetch resourceCategory.parent",
        countQuery = "select count(resourceCategory) from ResourceCategory resourceCategory"
    )
    Page<ResourceCategory> findAllWithToOneRelationships(Pageable pageable);

    @Query("select resourceCategory from ResourceCategory resourceCategory left join fetch resourceCategory.parent")
    List<ResourceCategory> findAllWithToOneRelationships();

    @Query(
        "select resourceCategory from ResourceCategory resourceCategory left join fetch resourceCategory.parent where resourceCategory.id =:id"
    )
    Optional<ResourceCategory> findOneWithToOneRelationships(@Param("id") Long id);

    List<ResourceCategory> findAllByParentId(Long parentId);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
