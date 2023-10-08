package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.Position;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the Position entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface PositionBaseRepository extends JpaRepository<Position, Long>, JpaSpecificationExecutor<Position> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
