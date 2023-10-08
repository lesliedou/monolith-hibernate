package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.Authority;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AuthorityRepositoryWithBagRelationships {
    Optional<Authority> fetchBagRelationships(Optional<Authority> authority);

    List<Authority> fetchBagRelationships(List<Authority> authorities);

    Page<Authority> fetchBagRelationships(Page<Authority> authorities);
}
