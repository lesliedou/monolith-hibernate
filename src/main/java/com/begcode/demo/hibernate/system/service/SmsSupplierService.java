package com.begcode.demo.hibernate.system.service;

import com.begcode.demo.hibernate.system.repository.SmsSupplierRepository;
import com.begcode.demo.hibernate.system.service.base.SmsSupplierBaseService;
import com.begcode.demo.hibernate.system.service.mapper.SmsSupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SmsSupplier}.
 */
@Service
@Transactional
public class SmsSupplierService extends SmsSupplierBaseService {

    private final Logger log = LoggerFactory.getLogger(SmsSupplierService.class);

    public SmsSupplierService(SmsSupplierRepository smsSupplierRepository, CacheManager cacheManager, SmsSupplierMapper smsSupplierMapper) {
        super(smsSupplierRepository, cacheManager, smsSupplierMapper);
    }
    // begcode-please-regenerate-this-file 如果您不希望重新生成代码时被覆盖，将please修改为don't ！！！
}
