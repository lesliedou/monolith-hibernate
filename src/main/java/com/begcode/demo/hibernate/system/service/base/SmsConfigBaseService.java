package com.begcode.demo.hibernate.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.system.domain.SmsConfig;
import com.begcode.demo.hibernate.system.repository.SmsConfigRepository;
import com.begcode.demo.hibernate.system.service.dto.SmsConfigDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsConfigMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.system.domain.SmsConfig}.
 */
public class SmsConfigBaseService {

    private final Logger log = LoggerFactory.getLogger(SmsConfigBaseService.class);

    protected final SmsConfigRepository smsConfigRepository;

    protected final CacheManager cacheManager;

    protected final SmsConfigMapper smsConfigMapper;

    public SmsConfigBaseService(SmsConfigRepository smsConfigRepository, CacheManager cacheManager, SmsConfigMapper smsConfigMapper) {
        this.smsConfigRepository = smsConfigRepository;
        this.cacheManager = cacheManager;
        this.smsConfigMapper = smsConfigMapper;
    }

    /**
     * Save a smsConfig.
     *
     * @param smsConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsConfigDTO save(SmsConfigDTO smsConfigDTO) {
        log.debug("Request to save SmsConfig : {}", smsConfigDTO);
        SmsConfig smsConfig = smsConfigMapper.toEntity(smsConfigDTO);
        smsConfig = smsConfigRepository.save(smsConfig);
        return smsConfigMapper.toDto(smsConfig);
    }

    /**
     * Update a smsConfig.
     *
     * @param smsConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsConfigDTO update(SmsConfigDTO smsConfigDTO) {
        log.debug("Request to update SmsConfig : {}", smsConfigDTO);

        SmsConfig smsConfig = smsConfigMapper.toEntity(smsConfigDTO);
        smsConfig = smsConfigRepository.save(smsConfig);
        return smsConfigMapper.toDto(smsConfig);
    }

    /**
     * Partially update a smsConfig.
     *
     * @param smsConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SmsConfigDTO> partialUpdate(SmsConfigDTO smsConfigDTO) {
        log.debug("Request to partially update SmsConfig : {}", smsConfigDTO);

        return smsConfigRepository
            .findById(smsConfigDTO.getId())
            .map(existingSmsConfig -> {
                smsConfigMapper.partialUpdate(existingSmsConfig, smsConfigDTO);

                return existingSmsConfig;
            })
            .map(smsConfigRepository::save)
            .map(smsConfigMapper::toDto);
    }

    /**
     * Get all the smsConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmsConfigs");
        return smsConfigRepository.findAll(pageable).map(smsConfigMapper::toDto);
    }

    /**
     * Get one smsConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsConfigDTO> findOne(Long id) {
        log.debug("Request to get SmsConfig : {}", id);
        return smsConfigRepository.findById(id).map(smsConfigMapper::toDto);
    }

    /**
     * Delete the smsConfig by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SmsConfig : {}", id);

        smsConfigRepository.deleteById(id);
    }

    /**
     * Get one smsConfig by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<SmsConfigDTO> findOneByExample(Example<SmsConfig> example) {
        log.debug("Request to get SmsConfig by example");
        return smsConfigRepository.findAll(example).stream().findFirst().map(smsConfigMapper::toDto);
    }

    /**
     * Get all the smsConfigs by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsConfigDTO> findAllByExample(Example<SmsConfig> example, Pageable pageable) {
        log.debug("Request to get SmsConfig by example");
        return smsConfigRepository.findAll(example, pageable).map(smsConfigMapper::toDto);
    }

    /**
     * Update specified field by smsConfig
     */
    public void updateBatch(SmsConfigDTO changeSmsConfigDTO, List<String> fieldNames, List<Long> ids) {
        smsConfigRepository
            .findAllById(ids)
            .stream()
            .peek(smsConfig ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(smsConfig, fieldName, BeanUtil.getFieldValue(changeSmsConfigDTO, fieldName))
                )
            )
            .forEach(smsConfigRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
