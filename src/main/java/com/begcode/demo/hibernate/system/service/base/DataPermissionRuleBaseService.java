package com.begcode.demo.hibernate.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.system.domain.DataPermissionRule;
import com.begcode.demo.hibernate.system.repository.DataPermissionRuleRepository;
import com.begcode.demo.hibernate.system.service.dto.DataPermissionRuleDTO;
import com.begcode.demo.hibernate.system.service.mapper.DataPermissionRuleMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.system.domain.DataPermissionRule}.
 */
public class DataPermissionRuleBaseService {

    private final Logger log = LoggerFactory.getLogger(DataPermissionRuleBaseService.class);

    protected final DataPermissionRuleRepository dataPermissionRuleRepository;

    protected final CacheManager cacheManager;

    protected final DataPermissionRuleMapper dataPermissionRuleMapper;

    public DataPermissionRuleBaseService(
        DataPermissionRuleRepository dataPermissionRuleRepository,
        CacheManager cacheManager,
        DataPermissionRuleMapper dataPermissionRuleMapper
    ) {
        this.dataPermissionRuleRepository = dataPermissionRuleRepository;
        this.cacheManager = cacheManager;
        this.dataPermissionRuleMapper = dataPermissionRuleMapper;
    }

    /**
     * Save a dataPermissionRule.
     *
     * @param dataPermissionRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public DataPermissionRuleDTO save(DataPermissionRuleDTO dataPermissionRuleDTO) {
        log.debug("Request to save DataPermissionRule : {}", dataPermissionRuleDTO);
        DataPermissionRule dataPermissionRule = dataPermissionRuleMapper.toEntity(dataPermissionRuleDTO);
        dataPermissionRule = dataPermissionRuleRepository.save(dataPermissionRule);
        return dataPermissionRuleMapper.toDto(dataPermissionRule);
    }

    /**
     * Update a dataPermissionRule.
     *
     * @param dataPermissionRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public DataPermissionRuleDTO update(DataPermissionRuleDTO dataPermissionRuleDTO) {
        log.debug("Request to update DataPermissionRule : {}", dataPermissionRuleDTO);

        DataPermissionRule dataPermissionRule = dataPermissionRuleMapper.toEntity(dataPermissionRuleDTO);
        dataPermissionRule = dataPermissionRuleRepository.save(dataPermissionRule);
        return dataPermissionRuleMapper.toDto(dataPermissionRule);
    }

    /**
     * Partially update a dataPermissionRule.
     *
     * @param dataPermissionRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DataPermissionRuleDTO> partialUpdate(DataPermissionRuleDTO dataPermissionRuleDTO) {
        log.debug("Request to partially update DataPermissionRule : {}", dataPermissionRuleDTO);

        return dataPermissionRuleRepository
            .findById(dataPermissionRuleDTO.getId())
            .map(existingDataPermissionRule -> {
                dataPermissionRuleMapper.partialUpdate(existingDataPermissionRule, dataPermissionRuleDTO);

                return existingDataPermissionRule;
            })
            .map(dataPermissionRuleRepository::save)
            .map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Get all the dataPermissionRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DataPermissionRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataPermissionRules");
        return dataPermissionRuleRepository.findAll(pageable).map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Get one dataPermissionRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataPermissionRuleDTO> findOne(Long id) {
        log.debug("Request to get DataPermissionRule : {}", id);
        return dataPermissionRuleRepository.findById(id).map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Delete the dataPermissionRule by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete DataPermissionRule : {}", id);

        dataPermissionRuleRepository.deleteById(id);
    }

    /**
     * Get one dataPermissionRule by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<DataPermissionRuleDTO> findOneByExample(Example<DataPermissionRule> example) {
        log.debug("Request to get DataPermissionRule by example");
        return dataPermissionRuleRepository.findAll(example).stream().findFirst().map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Get all the dataPermissionRules by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<DataPermissionRuleDTO> findAllByExample(Example<DataPermissionRule> example, Pageable pageable) {
        log.debug("Request to get DataPermissionRule by example");
        return dataPermissionRuleRepository.findAll(example, pageable).map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Update specified field by dataPermissionRule
     */
    public void updateBatch(DataPermissionRuleDTO changeDataPermissionRuleDTO, List<String> fieldNames, List<Long> ids) {
        dataPermissionRuleRepository
            .findAllById(ids)
            .stream()
            .peek(dataPermissionRule ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(dataPermissionRule, fieldName, BeanUtil.getFieldValue(changeDataPermissionRuleDTO, fieldName))
                )
            )
            .forEach(dataPermissionRuleRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
