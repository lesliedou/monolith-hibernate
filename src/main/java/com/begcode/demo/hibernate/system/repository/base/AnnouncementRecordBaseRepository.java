package com.begcode.demo.hibernate.system.repository.base;

import com.begcode.demo.hibernate.system.domain.AnnouncementRecord;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the AnnouncementRecord entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface AnnouncementRecordBaseRepository
    extends JpaRepository<AnnouncementRecord, Long>, JpaSpecificationExecutor<AnnouncementRecord> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
