package com.begcode.demo.hibernate.repository;

import com.begcode.demo.hibernate.repository.base.AuthorityBaseRepository;
import com.begcode.demo.hibernate.repository.base.AuthorityRepositoryWithBagRelationships;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends AuthorityRepositoryWithBagRelationships, AuthorityBaseRepository {
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
