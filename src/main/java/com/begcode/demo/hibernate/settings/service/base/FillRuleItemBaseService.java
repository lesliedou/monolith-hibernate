package com.begcode.demo.hibernate.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.settings.domain.FillRuleItem;
import com.begcode.demo.hibernate.settings.repository.FillRuleItemRepository;
import com.begcode.demo.hibernate.settings.service.dto.FillRuleItemDTO;
import com.begcode.demo.hibernate.settings.service.mapper.FillRuleItemMapper;
import java.util.Arrays;
import java.util.List;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.settings.domain.FillRuleItem}.
 */
public class FillRuleItemBaseService {

    private final Logger log = LoggerFactory.getLogger(FillRuleItemBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.settings.domain.SysFillRule.class.getName() + ".ruleItems"
    );

    protected final FillRuleItemRepository fillRuleItemRepository;

    protected final CacheManager cacheManager;

    protected final FillRuleItemMapper fillRuleItemMapper;

    public FillRuleItemBaseService(
        FillRuleItemRepository fillRuleItemRepository,
        CacheManager cacheManager,
        FillRuleItemMapper fillRuleItemMapper
    ) {
        this.fillRuleItemRepository = fillRuleItemRepository;
        this.cacheManager = cacheManager;
        this.fillRuleItemMapper = fillRuleItemMapper;
    }

    /**
     * Save a fillRuleItem.
     *
     * @param fillRuleItemDTO the entity to save.
     * @return the persisted entity.
     */
    public FillRuleItemDTO save(FillRuleItemDTO fillRuleItemDTO) {
        log.debug("Request to save FillRuleItem : {}", fillRuleItemDTO);
        FillRuleItem fillRuleItem = fillRuleItemMapper.toEntity(fillRuleItemDTO);
        fillRuleItem = fillRuleItemRepository.save(fillRuleItem);
        return fillRuleItemMapper.toDto(fillRuleItem);
    }

    /**
     * Update a fillRuleItem.
     *
     * @param fillRuleItemDTO the entity to save.
     * @return the persisted entity.
     */
    public FillRuleItemDTO update(FillRuleItemDTO fillRuleItemDTO) {
        log.debug("Request to update FillRuleItem : {}", fillRuleItemDTO);

        FillRuleItem fillRuleItem = fillRuleItemMapper.toEntity(fillRuleItemDTO);
        fillRuleItem = fillRuleItemRepository.save(fillRuleItem);
        return fillRuleItemMapper.toDto(fillRuleItem);
    }

    /**
     * Partially update a fillRuleItem.
     *
     * @param fillRuleItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FillRuleItemDTO> partialUpdate(FillRuleItemDTO fillRuleItemDTO) {
        log.debug("Request to partially update FillRuleItem : {}", fillRuleItemDTO);

        return fillRuleItemRepository
            .findById(fillRuleItemDTO.getId())
            .map(existingFillRuleItem -> {
                fillRuleItemMapper.partialUpdate(existingFillRuleItem, fillRuleItemDTO);

                return existingFillRuleItem;
            })
            .map(fillRuleItemRepository::save)
            .map(fillRuleItemMapper::toDto);
    }

    /**
     * Get all the fillRuleItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FillRuleItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FillRuleItems");
        return fillRuleItemRepository.findAll(pageable).map(fillRuleItemMapper::toDto);
    }

    /**
     * Get all the fillRuleItems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FillRuleItemDTO> findAllWithEagerRelationships(Pageable pageable) {
        return fillRuleItemRepository.findAllWithEagerRelationships(pageable).map(fillRuleItemMapper::toDto);
    }

    /**
     * Get one fillRuleItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FillRuleItemDTO> findOne(Long id) {
        log.debug("Request to get FillRuleItem : {}", id);
        return fillRuleItemRepository.findOneWithEagerRelationships(id).map(fillRuleItemMapper::toDto);
    }

    /**
     * Delete the fillRuleItem by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete FillRuleItem : {}", id);

        fillRuleItemRepository.deleteById(id);
    }

    /**
     * Get one fillRuleItem by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<FillRuleItemDTO> findOneByExample(Example<FillRuleItem> example) {
        log.debug("Request to get FillRuleItem by example");
        return fillRuleItemRepository.findAll(example).stream().findFirst().map(fillRuleItemMapper::toDto);
    }

    /**
     * Get all the fillRuleItems by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<FillRuleItemDTO> findAllByExample(Example<FillRuleItem> example, Pageable pageable) {
        log.debug("Request to get FillRuleItem by example");
        return fillRuleItemRepository.findAll(example, pageable).map(fillRuleItemMapper::toDto);
    }

    /**
     * Update specified field by fillRuleItem
     */
    public void updateBatch(FillRuleItemDTO changeFillRuleItemDTO, List<String> fieldNames, List<Long> ids) {
        fillRuleItemRepository
            .findAllById(ids)
            .stream()
            .peek(fillRuleItem ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(fillRuleItem, fieldName, BeanUtil.getFieldValue(changeFillRuleItemDTO, fieldName))
                )
            )
            .forEach(fillRuleItemRepository::save);
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
