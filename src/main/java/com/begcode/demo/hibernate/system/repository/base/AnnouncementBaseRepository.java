package com.begcode.demo.hibernate.system.repository.base;

import com.begcode.demo.hibernate.system.domain.Announcement;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the Announcement entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface AnnouncementBaseRepository extends JpaRepository<Announcement, Long>, JpaSpecificationExecutor<Announcement> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
