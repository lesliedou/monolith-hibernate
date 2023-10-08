package com.begcode.demo.hibernate.oss.repository.base;

import com.begcode.demo.hibernate.oss.domain.OssConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the OssConfig entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface OssConfigBaseRepository extends JpaRepository<OssConfig, Long>, JpaSpecificationExecutor<OssConfig> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
