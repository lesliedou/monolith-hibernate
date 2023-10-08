package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.ApiPermission;
import com.begcode.demo.hibernate.domain.enumeration.ApiPermissionType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the ApiPermission entity.
 */
@NoRepositoryBean
public interface ApiPermissionBaseRepository extends JpaRepository<ApiPermission, Long>, JpaSpecificationExecutor<ApiPermission> {
    default Optional<ApiPermission> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ApiPermission> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ApiPermission> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select apiPermission from ApiPermission apiPermission left join fetch apiPermission.parent",
        countQuery = "select count(apiPermission) from ApiPermission apiPermission"
    )
    Page<ApiPermission> findAllWithToOneRelationships(Pageable pageable);

    @Query("select apiPermission from ApiPermission apiPermission left join fetch apiPermission.parent")
    List<ApiPermission> findAllWithToOneRelationships();

    @Query("select apiPermission from ApiPermission apiPermission left join fetch apiPermission.parent where apiPermission.id =:id")
    Optional<ApiPermission> findOneWithToOneRelationships(@Param("id") Long id);

    List<ApiPermission> findAllByParentId(Long parentId);

    Optional<ApiPermission> findOneByCode(String groupCode);

    List<ApiPermission> findAllByType(ApiPermissionType type);

    @Query("select authority.apiPermissions from User u join u.authorities authority where u.login = ?#{principal.username}")
    List<ApiPermission> findAllApiPermissionsByCurrentUser();
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
