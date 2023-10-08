package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.UReportFile;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the UReportFile entity.
 */
@SuppressWarnings("unused")
@NoRepositoryBean
public interface UReportFileBaseRepository extends JpaRepository<UReportFile, Long>, JpaSpecificationExecutor<UReportFile> {
    Boolean existsByName(String name);

    Optional<UReportFile> getByName(String name);

    void deleteByName(String name);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
