package com.begcode.demo.hibernate.settings.service;

import com.begcode.demo.hibernate.settings.repository.DictionaryRepository;
import com.begcode.demo.hibernate.settings.service.base.DictionaryBaseService;
import com.begcode.demo.hibernate.settings.service.mapper.DictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dictionary}.
 */
@Service
@Transactional
public class DictionaryService extends DictionaryBaseService {

    private final Logger log = LoggerFactory.getLogger(DictionaryService.class);

    public DictionaryService(DictionaryRepository dictionaryRepository, CacheManager cacheManager, DictionaryMapper dictionaryMapper) {
        super(dictionaryRepository, cacheManager, dictionaryMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
