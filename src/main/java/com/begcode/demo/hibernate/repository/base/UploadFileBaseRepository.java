package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.UploadFile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the UploadFile entity.
 */
@NoRepositoryBean
public interface UploadFileBaseRepository extends JpaRepository<UploadFile, Long>, JpaSpecificationExecutor<UploadFile> {
    default Optional<UploadFile> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UploadFile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UploadFile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select uploadFile from UploadFile uploadFile left join fetch uploadFile.category",
        countQuery = "select count(uploadFile) from UploadFile uploadFile"
    )
    Page<UploadFile> findAllWithToOneRelationships(Pageable pageable);

    @Query("select uploadFile from UploadFile uploadFile left join fetch uploadFile.category")
    List<UploadFile> findAllWithToOneRelationships();

    @Query("select uploadFile from UploadFile uploadFile left join fetch uploadFile.category where uploadFile.id =:id")
    Optional<UploadFile> findOneWithToOneRelationships(@Param("id") Long id);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
