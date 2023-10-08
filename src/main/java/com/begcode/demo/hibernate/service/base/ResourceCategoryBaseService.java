package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.ResourceCategory;
import com.begcode.demo.hibernate.repository.ResourceCategoryRepository;
import com.begcode.demo.hibernate.service.dto.ResourceCategoryDTO;
import com.begcode.demo.hibernate.service.mapper.ResourceCategoryMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.ResourceCategory}.
 */
public class ResourceCategoryBaseService {

    private final Logger log = LoggerFactory.getLogger(ResourceCategoryBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.domain.UploadFile.class.getName() + ".category",
        com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".parent",
        com.begcode.demo.hibernate.domain.UploadImage.class.getName() + ".category",
        com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".children"
    );

    protected final ResourceCategoryRepository resourceCategoryRepository;

    protected final CacheManager cacheManager;

    protected final ResourceCategoryMapper resourceCategoryMapper;

    public ResourceCategoryBaseService(
        ResourceCategoryRepository resourceCategoryRepository,
        CacheManager cacheManager,
        ResourceCategoryMapper resourceCategoryMapper
    ) {
        this.resourceCategoryRepository = resourceCategoryRepository;
        this.cacheManager = cacheManager;
        this.resourceCategoryMapper = resourceCategoryMapper;
    }

    /**
     * Save a resourceCategory.
     *
     * @param resourceCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public ResourceCategoryDTO save(ResourceCategoryDTO resourceCategoryDTO) {
        log.debug("Request to save ResourceCategory : {}", resourceCategoryDTO);
        ResourceCategory resourceCategory = resourceCategoryMapper.toEntity(resourceCategoryDTO);
        clearChildrenCache();
        resourceCategory = resourceCategoryRepository.save(resourceCategory);
        // 更新缓存
        if (resourceCategory.getParent() != null) {
            resourceCategory.getParent().addChildren(resourceCategory);
        }
        return resourceCategoryMapper.toDto(resourceCategory);
    }

    /**
     * Update a resourceCategory.
     *
     * @param resourceCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public ResourceCategoryDTO update(ResourceCategoryDTO resourceCategoryDTO) {
        log.debug("Request to update ResourceCategory : {}", resourceCategoryDTO);

        ResourceCategory resourceCategory = resourceCategoryMapper.toEntity(resourceCategoryDTO);
        resourceCategory = resourceCategoryRepository.save(resourceCategory);
        return resourceCategoryMapper.toDto(resourceCategory);
    }

    /**
     * Partially update a resourceCategory.
     *
     * @param resourceCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ResourceCategoryDTO> partialUpdate(ResourceCategoryDTO resourceCategoryDTO) {
        log.debug("Request to partially update ResourceCategory : {}", resourceCategoryDTO);

        return resourceCategoryRepository
            .findById(resourceCategoryDTO.getId())
            .map(existingResourceCategory -> {
                resourceCategoryMapper.partialUpdate(existingResourceCategory, resourceCategoryDTO);

                return existingResourceCategory;
            })
            .map(resourceCategoryRepository::save)
            .map(resourceCategoryMapper::toDto);
    }

    /**
     * Get all the resourceCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourceCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceCategories");
        return resourceCategoryRepository.findAll(pageable).map(resourceCategoryMapper::toDto);
    }

    /**
     * Get all the resourceCategories with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ResourceCategoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return resourceCategoryRepository.findAllWithEagerRelationships(pageable).map(resourceCategoryMapper::toDto);
    }

    /**
     * Get one resourceCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResourceCategoryDTO> findOne(Long id) {
        log.debug("Request to get ResourceCategory : {}", id);
        return resourceCategoryRepository.findOneWithEagerRelationships(id).map(resourceCategoryMapper::toDto);
    }

    /**
     * Delete the resourceCategory by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete ResourceCategory : {}", id);

        ResourceCategory resourceCategory = resourceCategoryRepository.getOne(id);
        if (resourceCategory.getParent() != null) {
            resourceCategory.getParent().removeChildren(resourceCategory);
        }
        if (resourceCategory.getChildren() != null) {
            resourceCategory.getChildren().forEach(subResourceCategory -> subResourceCategory.setParent(null));
        }

        resourceCategoryRepository.deleteById(id);
    }

    /**
     * Get one resourceCategory by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<ResourceCategoryDTO> findOneByExample(Example<ResourceCategory> example) {
        log.debug("Request to get ResourceCategory by example");
        return resourceCategoryRepository.findAll(example).stream().findFirst().map(resourceCategoryMapper::toDto);
    }

    /**
     * Get all the resourceCategories by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<ResourceCategoryDTO> findAllByExample(Example<ResourceCategory> example, Pageable pageable) {
        log.debug("Request to get ResourceCategory by example");
        return resourceCategoryRepository.findAll(example, pageable).map(resourceCategoryMapper::toDto);
    }

    /**
     * Update specified field by resourceCategory
     */
    public void updateBatch(ResourceCategoryDTO changeResourceCategoryDTO, List<String> fieldNames, List<Long> ids) {
        resourceCategoryRepository
            .findAllById(ids)
            .stream()
            .peek(resourceCategory ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(resourceCategory, fieldName, BeanUtil.getFieldValue(changeResourceCategoryDTO, fieldName))
                )
            )
            .forEach(resourceCategoryRepository::save);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects
            .requireNonNull(cacheManager.getCache(com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".children"))
            .clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
