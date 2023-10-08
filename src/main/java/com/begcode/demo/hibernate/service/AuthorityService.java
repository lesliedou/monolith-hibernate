package com.begcode.demo.hibernate.service;

import com.begcode.demo.hibernate.repository.AuthorityRepository;
import com.begcode.demo.hibernate.service.base.AuthorityBaseService;
import com.begcode.demo.hibernate.service.mapper.AuthorityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Authority}.
 */
@Service
@Transactional
public class AuthorityService extends AuthorityBaseService {

    private final Logger log = LoggerFactory.getLogger(AuthorityService.class);

    public AuthorityService(AuthorityRepository authorityRepository, CacheManager cacheManager, AuthorityMapper authorityMapper) {
        super(authorityRepository, cacheManager, authorityMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
