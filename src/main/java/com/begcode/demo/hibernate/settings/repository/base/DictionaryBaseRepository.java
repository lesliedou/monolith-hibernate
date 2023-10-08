package com.begcode.demo.hibernate.settings.repository.base;

import com.begcode.demo.hibernate.settings.domain.Dictionary;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the Dictionary entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface DictionaryBaseRepository extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
