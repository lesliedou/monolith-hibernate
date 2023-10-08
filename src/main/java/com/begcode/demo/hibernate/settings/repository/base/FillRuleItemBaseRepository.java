package com.begcode.demo.hibernate.settings.repository.base;

import com.begcode.demo.hibernate.settings.domain.FillRuleItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the FillRuleItem entity.
 */
@NoRepositoryBean
public interface FillRuleItemBaseRepository extends JpaRepository<FillRuleItem, Long>, JpaSpecificationExecutor<FillRuleItem> {
    default Optional<FillRuleItem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FillRuleItem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FillRuleItem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select fillRuleItem from FillRuleItem fillRuleItem left join fetch fillRuleItem.fillRule",
        countQuery = "select count(fillRuleItem) from FillRuleItem fillRuleItem"
    )
    Page<FillRuleItem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select fillRuleItem from FillRuleItem fillRuleItem left join fetch fillRuleItem.fillRule")
    List<FillRuleItem> findAllWithToOneRelationships();

    @Query("select fillRuleItem from FillRuleItem fillRuleItem left join fetch fillRuleItem.fillRule where fillRuleItem.id =:id")
    Optional<FillRuleItem> findOneWithToOneRelationships(@Param("id") Long id);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
