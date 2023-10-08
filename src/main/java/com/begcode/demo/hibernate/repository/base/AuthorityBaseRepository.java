package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.Authority;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data JPA repository for the Authority entity.
 *
 * When extending this class, extend AuthorityRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@NoRepositoryBean
public interface AuthorityBaseRepository
    extends AuthorityRepositoryWithBagRelationships, JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {
    default Optional<Authority> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Authority> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Authority> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select authority from Authority authority left join fetch authority.parent",
        countQuery = "select count(authority) from Authority authority"
    )
    Page<Authority> findAllWithToOneRelationships(Pageable pageable);

    @Query("select authority from Authority authority left join fetch authority.parent")
    List<Authority> findAllWithToOneRelationships();

    @Query("select authority from Authority authority left join fetch authority.parent where authority.id =:id")
    Optional<Authority> findOneWithToOneRelationships(@Param("id") Long id);

    List<Authority> findAllByParentId(Long parentId);

    Optional<Authority> findFirstByCode(String code);

    @Query("select user.authorities from User user where user.id =:userId")
    List<Authority> findByUsersId(@Param("userId") Long userId);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove

}
