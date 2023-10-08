package com.begcode.demo.hibernate.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.settings.domain.RegionCode;
import com.begcode.demo.hibernate.settings.repository.RegionCodeRepository;
import com.begcode.demo.hibernate.settings.service.dto.RegionCodeDTO;
import com.begcode.demo.hibernate.settings.service.mapper.RegionCodeMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.settings.domain.RegionCode}.
 */
public class RegionCodeBaseService {

    private final Logger log = LoggerFactory.getLogger(RegionCodeBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.settings.domain.RegionCode.class.getName() + ".parent",
        com.begcode.demo.hibernate.settings.domain.RegionCode.class.getName() + ".children"
    );

    protected final RegionCodeRepository regionCodeRepository;

    protected final CacheManager cacheManager;

    protected final RegionCodeMapper regionCodeMapper;

    public RegionCodeBaseService(RegionCodeRepository regionCodeRepository, CacheManager cacheManager, RegionCodeMapper regionCodeMapper) {
        this.regionCodeRepository = regionCodeRepository;
        this.cacheManager = cacheManager;
        this.regionCodeMapper = regionCodeMapper;
    }

    /**
     * Save a regionCode.
     *
     * @param regionCodeDTO the entity to save.
     * @return the persisted entity.
     */
    public RegionCodeDTO save(RegionCodeDTO regionCodeDTO) {
        log.debug("Request to save RegionCode : {}", regionCodeDTO);
        RegionCode regionCode = regionCodeMapper.toEntity(regionCodeDTO);
        clearChildrenCache();
        regionCode = regionCodeRepository.save(regionCode);
        // 更新缓存
        if (regionCode.getParent() != null) {
            regionCode.getParent().addChildren(regionCode);
        }
        return regionCodeMapper.toDto(regionCode);
    }

    /**
     * Update a regionCode.
     *
     * @param regionCodeDTO the entity to save.
     * @return the persisted entity.
     */
    public RegionCodeDTO update(RegionCodeDTO regionCodeDTO) {
        log.debug("Request to update RegionCode : {}", regionCodeDTO);

        RegionCode regionCode = regionCodeMapper.toEntity(regionCodeDTO);
        regionCode = regionCodeRepository.save(regionCode);
        return regionCodeMapper.toDto(regionCode);
    }

    /**
     * Partially update a regionCode.
     *
     * @param regionCodeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RegionCodeDTO> partialUpdate(RegionCodeDTO regionCodeDTO) {
        log.debug("Request to partially update RegionCode : {}", regionCodeDTO);

        return regionCodeRepository
            .findById(regionCodeDTO.getId())
            .map(existingRegionCode -> {
                regionCodeMapper.partialUpdate(existingRegionCode, regionCodeDTO);

                return existingRegionCode;
            })
            .map(regionCodeRepository::save)
            .map(regionCodeMapper::toDto);
    }

    /**
     * Get all the regionCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionCodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegionCodes");
        return regionCodeRepository.findAll(pageable).map(regionCodeMapper::toDto);
    }

    /**
     * Get all the regionCodes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RegionCodeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return regionCodeRepository.findAllWithEagerRelationships(pageable).map(regionCodeMapper::toDto);
    }

    /**
     * Get one regionCode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegionCodeDTO> findOne(Long id) {
        log.debug("Request to get RegionCode : {}", id);
        return regionCodeRepository.findOneWithEagerRelationships(id).map(regionCodeMapper::toDto);
    }

    /**
     * Delete the regionCode by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete RegionCode : {}", id);

        RegionCode regionCode = regionCodeRepository.getOne(id);
        if (regionCode.getParent() != null) {
            regionCode.getParent().removeChildren(regionCode);
        }
        if (regionCode.getChildren() != null) {
            regionCode.getChildren().forEach(subRegionCode -> subRegionCode.setParent(null));
        }

        regionCodeRepository.deleteById(id);
    }

    /**
     * Get one regionCode by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<RegionCodeDTO> findOneByExample(Example<RegionCode> example) {
        log.debug("Request to get RegionCode by example");
        return regionCodeRepository.findAll(example).stream().findFirst().map(regionCodeMapper::toDto);
    }

    /**
     * Get all the regionCodes by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionCodeDTO> findAllByExample(Example<RegionCode> example, Pageable pageable) {
        log.debug("Request to get RegionCode by example");
        return regionCodeRepository.findAll(example, pageable).map(regionCodeMapper::toDto);
    }

    /**
     * Update specified field by regionCode
     */
    public void updateBatch(RegionCodeDTO changeRegionCodeDTO, List<String> fieldNames, List<Long> ids) {
        regionCodeRepository
            .findAllById(ids)
            .stream()
            .peek(regionCode ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(regionCode, fieldName, BeanUtil.getFieldValue(changeRegionCodeDTO, fieldName))
                )
            )
            .forEach(regionCodeRepository::save);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects
            .requireNonNull(cacheManager.getCache(com.begcode.demo.hibernate.settings.domain.RegionCode.class.getName() + ".children"))
            .clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
