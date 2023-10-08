package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the Department entity.
 *
 * When extending this class, extend DepartmentRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@NoRepositoryBean
public interface DepartmentBaseRepository
    extends DepartmentRepositoryWithBagRelationships, JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    default Optional<Department> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Department> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Department> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select department from Department department left join fetch department.parent",
        countQuery = "select count(department) from Department department"
    )
    Page<Department> findAllWithToOneRelationships(Pageable pageable);

    @Query("select department from Department department left join fetch department.parent")
    List<Department> findAllWithToOneRelationships();

    @Query("select department from Department department left join fetch department.parent where department.id =:id")
    Optional<Department> findOneWithToOneRelationships(@Param("id") Long id);

    List<Department> findAllByParentId(Long parentId);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
