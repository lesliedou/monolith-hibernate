package com.begcode.demo.hibernate.system.repository.base;

import com.begcode.demo.hibernate.system.domain.SmsConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the SmsConfig entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface SmsConfigBaseRepository extends JpaRepository<SmsConfig, Long>, JpaSpecificationExecutor<SmsConfig> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
