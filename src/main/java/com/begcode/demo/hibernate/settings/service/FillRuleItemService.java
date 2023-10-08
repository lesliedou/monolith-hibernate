package com.begcode.demo.hibernate.settings.service;

import com.begcode.demo.hibernate.settings.repository.FillRuleItemRepository;
import com.begcode.demo.hibernate.settings.service.base.FillRuleItemBaseService;
import com.begcode.demo.hibernate.settings.service.mapper.FillRuleItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FillRuleItem}.
 */
@Service
@Transactional
public class FillRuleItemService extends FillRuleItemBaseService {

    private final Logger log = LoggerFactory.getLogger(FillRuleItemService.class);

    public FillRuleItemService(
        FillRuleItemRepository fillRuleItemRepository,
        CacheManager cacheManager,
        FillRuleItemMapper fillRuleItemMapper
    ) {
        super(fillRuleItemRepository, cacheManager, fillRuleItemMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
