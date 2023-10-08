package com.begcode.demo.hibernate.log.repository.base;

import com.begcode.demo.hibernate.log.domain.SysLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the SysLog entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface SysLogBaseRepository extends JpaRepository<SysLog, Long>, JpaSpecificationExecutor<SysLog> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
