package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.UploadImage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the UploadImage entity.
 */
@NoRepositoryBean
public interface UploadImageBaseRepository extends JpaRepository<UploadImage, Long>, JpaSpecificationExecutor<UploadImage> {
    default Optional<UploadImage> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UploadImage> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UploadImage> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select uploadImage from UploadImage uploadImage left join fetch uploadImage.category",
        countQuery = "select count(uploadImage) from UploadImage uploadImage"
    )
    Page<UploadImage> findAllWithToOneRelationships(Pageable pageable);

    @Query("select uploadImage from UploadImage uploadImage left join fetch uploadImage.category")
    List<UploadImage> findAllWithToOneRelationships();

    @Query("select uploadImage from UploadImage uploadImage left join fetch uploadImage.category where uploadImage.id =:id")
    Optional<UploadImage> findOneWithToOneRelationships(@Param("id") Long id);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
