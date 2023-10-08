package com.begcode.demo.hibernate.system.repository.base;

import com.begcode.demo.hibernate.system.domain.DataPermissionRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the DataPermissionRule entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface DataPermissionRuleBaseRepository
    extends JpaRepository<DataPermissionRule, Long>, JpaSpecificationExecutor<DataPermissionRule> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
