package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.ViewPermission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the ViewPermission entity.
 */
@NoRepositoryBean
public interface ViewPermissionBaseRepository extends JpaRepository<ViewPermission, Long>, JpaSpecificationExecutor<ViewPermission> {
    default Optional<ViewPermission> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ViewPermission> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ViewPermission> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select viewPermission from ViewPermission viewPermission left join fetch viewPermission.parent",
        countQuery = "select count(viewPermission) from ViewPermission viewPermission"
    )
    Page<ViewPermission> findAllWithToOneRelationships(Pageable pageable);

    @Query("select viewPermission from ViewPermission viewPermission left join fetch viewPermission.parent")
    List<ViewPermission> findAllWithToOneRelationships();

    @Query("select viewPermission from ViewPermission viewPermission left join fetch viewPermission.parent where viewPermission.id =:id")
    Optional<ViewPermission> findOneWithToOneRelationships(@Param("id") Long id);

    List<ViewPermission> findAllByParentId(Long parentId);

    @Query("select authority.viewPermissions from User u join u.authorities authority where u.login = ?#{principal.username}")
    List<ViewPermission> findAllViewPermissionsByCurrentUser();
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
