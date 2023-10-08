package com.begcode.demo.hibernate.settings.repository.base;

import com.begcode.demo.hibernate.settings.domain.CommonFieldData;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the CommonFieldData entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface CommonFieldDataBaseRepository extends JpaRepository<CommonFieldData, Long>, JpaSpecificationExecutor<CommonFieldData> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
