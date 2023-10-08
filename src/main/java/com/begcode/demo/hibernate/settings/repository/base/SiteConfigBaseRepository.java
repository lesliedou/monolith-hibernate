package com.begcode.demo.hibernate.settings.repository.base;

import com.begcode.demo.hibernate.settings.domain.SiteConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the SiteConfig entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface SiteConfigBaseRepository extends JpaRepository<SiteConfig, Long>, JpaSpecificationExecutor<SiteConfig> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
