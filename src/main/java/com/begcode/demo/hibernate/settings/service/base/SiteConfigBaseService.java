package com.begcode.demo.hibernate.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.settings.domain.SiteConfig;
import com.begcode.demo.hibernate.settings.repository.SiteConfigRepository;
import com.begcode.demo.hibernate.settings.service.dto.SiteConfigDTO;
import com.begcode.demo.hibernate.settings.service.mapper.SiteConfigMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.settings.domain.SiteConfig}.
 */
public class SiteConfigBaseService {

    private final Logger log = LoggerFactory.getLogger(SiteConfigBaseService.class);

    protected final SiteConfigRepository siteConfigRepository;

    protected final CacheManager cacheManager;

    protected final SiteConfigMapper siteConfigMapper;

    public SiteConfigBaseService(SiteConfigRepository siteConfigRepository, CacheManager cacheManager, SiteConfigMapper siteConfigMapper) {
        this.siteConfigRepository = siteConfigRepository;
        this.cacheManager = cacheManager;
        this.siteConfigMapper = siteConfigMapper;
    }

    /**
     * Save a siteConfig.
     *
     * @param siteConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteConfigDTO save(SiteConfigDTO siteConfigDTO) {
        log.debug("Request to save SiteConfig : {}", siteConfigDTO);
        SiteConfig siteConfig = siteConfigMapper.toEntity(siteConfigDTO);
        siteConfig = siteConfigRepository.save(siteConfig);
        return siteConfigMapper.toDto(siteConfig);
    }

    /**
     * Update a siteConfig.
     *
     * @param siteConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteConfigDTO update(SiteConfigDTO siteConfigDTO) {
        log.debug("Request to update SiteConfig : {}", siteConfigDTO);

        SiteConfig siteConfig = siteConfigMapper.toEntity(siteConfigDTO);
        siteConfig = siteConfigRepository.save(siteConfig);
        return siteConfigMapper.toDto(siteConfig);
    }

    /**
     * Partially update a siteConfig.
     *
     * @param siteConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiteConfigDTO> partialUpdate(SiteConfigDTO siteConfigDTO) {
        log.debug("Request to partially update SiteConfig : {}", siteConfigDTO);

        return siteConfigRepository
            .findById(siteConfigDTO.getId())
            .map(existingSiteConfig -> {
                siteConfigMapper.partialUpdate(existingSiteConfig, siteConfigDTO);

                return existingSiteConfig;
            })
            .map(siteConfigRepository::save)
            .map(siteConfigMapper::toDto);
    }

    /**
     * Get all the siteConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteConfigs");
        return siteConfigRepository.findAll(pageable).map(siteConfigMapper::toDto);
    }

    /**
     * Get one siteConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteConfigDTO> findOne(Long id) {
        log.debug("Request to get SiteConfig : {}", id);
        return siteConfigRepository.findById(id).map(siteConfigMapper::toDto);
    }

    /**
     * Delete the siteConfig by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SiteConfig : {}", id);

        siteConfigRepository.deleteById(id);
    }

    /**
     * Get one siteConfig by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<SiteConfigDTO> findOneByExample(Example<SiteConfig> example) {
        log.debug("Request to get SiteConfig by example");
        return siteConfigRepository.findAll(example).stream().findFirst().map(siteConfigMapper::toDto);
    }

    /**
     * Get all the siteConfigs by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteConfigDTO> findAllByExample(Example<SiteConfig> example, Pageable pageable) {
        log.debug("Request to get SiteConfig by example");
        return siteConfigRepository.findAll(example, pageable).map(siteConfigMapper::toDto);
    }

    /**
     * Update specified field by siteConfig
     */
    public void updateBatch(SiteConfigDTO changeSiteConfigDTO, List<String> fieldNames, List<Long> ids) {
        siteConfigRepository
            .findAllById(ids)
            .stream()
            .peek(siteConfig ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(siteConfig, fieldName, BeanUtil.getFieldValue(changeSiteConfigDTO, fieldName))
                )
            )
            .forEach(siteConfigRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
