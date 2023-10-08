package com.begcode.demo.hibernate.settings.repository.base;

import com.begcode.demo.hibernate.settings.domain.RegionCode;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the RegionCode entity.
 */
@NoRepositoryBean
public interface RegionCodeBaseRepository extends JpaRepository<RegionCode, Long>, JpaSpecificationExecutor<RegionCode> {
    default Optional<RegionCode> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<RegionCode> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<RegionCode> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select regionCode from RegionCode regionCode left join fetch regionCode.parent",
        countQuery = "select count(regionCode) from RegionCode regionCode"
    )
    Page<RegionCode> findAllWithToOneRelationships(Pageable pageable);

    @Query("select regionCode from RegionCode regionCode left join fetch regionCode.parent")
    List<RegionCode> findAllWithToOneRelationships();

    @Query("select regionCode from RegionCode regionCode left join fetch regionCode.parent where regionCode.id =:id")
    Optional<RegionCode> findOneWithToOneRelationships(@Param("id") Long id);

    List<RegionCode> findAllByParentId(Long parentId);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
