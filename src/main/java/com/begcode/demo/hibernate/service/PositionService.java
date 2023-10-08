package com.begcode.demo.hibernate.service;

import com.begcode.demo.hibernate.repository.PositionRepository;
import com.begcode.demo.hibernate.service.base.PositionBaseService;
import com.begcode.demo.hibernate.service.mapper.PositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Position}.
 */
@Service
@Transactional
public class PositionService extends PositionBaseService {

    private final Logger log = LoggerFactory.getLogger(PositionService.class);

    public PositionService(PositionRepository positionRepository, CacheManager cacheManager, PositionMapper positionMapper) {
        super(positionRepository, cacheManager, positionMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
