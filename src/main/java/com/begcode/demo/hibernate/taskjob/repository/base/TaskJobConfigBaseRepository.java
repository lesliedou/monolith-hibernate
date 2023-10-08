package com.begcode.demo.hibernate.taskjob.repository.base;

import com.begcode.demo.hibernate.taskjob.domain.TaskJobConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the TaskJobConfig entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface TaskJobConfigBaseRepository extends JpaRepository<TaskJobConfig, Long>, JpaSpecificationExecutor<TaskJobConfig> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
