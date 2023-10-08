package com.begcode.demo.hibernate.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.begcode.demo.hibernate.common.IFillRuleHandler;
import com.begcode.demo.hibernate.settings.domain.SysFillRule;
import com.begcode.demo.hibernate.settings.repository.SysFillRuleRepository;
import com.begcode.demo.hibernate.settings.service.dto.SysFillRuleDTO;
import com.begcode.demo.hibernate.settings.service.mapper.SysFillRuleMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.settings.domain.SysFillRule}.
 */
public class SysFillRuleBaseService implements IFillRuleHandler {

    private final Logger log = LoggerFactory.getLogger(SysFillRuleBaseService.class);

    protected final SysFillRuleRepository sysFillRuleRepository;

    protected final CacheManager cacheManager;

    protected final SysFillRuleMapper sysFillRuleMapper;

    public SysFillRuleBaseService(
        SysFillRuleRepository sysFillRuleRepository,
        CacheManager cacheManager,
        SysFillRuleMapper sysFillRuleMapper
    ) {
        this.sysFillRuleRepository = sysFillRuleRepository;
        this.cacheManager = cacheManager;
        this.sysFillRuleMapper = sysFillRuleMapper;
    }

    /**
     * Save a sysFillRule.
     *
     * @param sysFillRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public SysFillRuleDTO save(SysFillRuleDTO sysFillRuleDTO) {
        log.debug("Request to save SysFillRule : {}", sysFillRuleDTO);
        SysFillRule sysFillRule = sysFillRuleMapper.toEntity(sysFillRuleDTO);
        sysFillRule = sysFillRuleRepository.save(sysFillRule);
        return sysFillRuleMapper.toDto(sysFillRule);
    }

    /**
     * Update a sysFillRule.
     *
     * @param sysFillRuleDTO the entity to save.
     * @return the persisted entity.
     */
    public SysFillRuleDTO update(SysFillRuleDTO sysFillRuleDTO) {
        log.debug("Request to update SysFillRule : {}", sysFillRuleDTO);

        SysFillRule sysFillRule = sysFillRuleMapper.toEntity(sysFillRuleDTO);
        sysFillRule = sysFillRuleRepository.save(sysFillRule);
        return sysFillRuleMapper.toDto(sysFillRule);
    }

    /**
     * Partially update a sysFillRule.
     *
     * @param sysFillRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SysFillRuleDTO> partialUpdate(SysFillRuleDTO sysFillRuleDTO) {
        log.debug("Request to partially update SysFillRule : {}", sysFillRuleDTO);

        return sysFillRuleRepository
            .findById(sysFillRuleDTO.getId())
            .map(existingSysFillRule -> {
                sysFillRuleMapper.partialUpdate(existingSysFillRule, sysFillRuleDTO);

                return existingSysFillRule;
            })
            .map(sysFillRuleRepository::save)
            .map(sysFillRuleMapper::toDto);
    }

    /**
     * Get all the sysFillRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SysFillRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysFillRules");
        return sysFillRuleRepository.findAll(pageable).map(sysFillRuleMapper::toDto);
    }

    /**
     * Get one sysFillRule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysFillRuleDTO> findOne(Long id) {
        log.debug("Request to get SysFillRule : {}", id);
        return sysFillRuleRepository.findById(id).map(sysFillRuleMapper::toDto);
    }

    /**
     * Delete the sysFillRule by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SysFillRule : {}", id);

        sysFillRuleRepository.deleteById(id);
    }

    @Override
    public Object execute(JSONObject params, JSONObject formData) {
        return null;
    }

    /**
     * Get one sysFillRule by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<SysFillRuleDTO> findOneByExample(Example<SysFillRule> example) {
        log.debug("Request to get SysFillRule by example");
        return sysFillRuleRepository.findAll(example).stream().findFirst().map(sysFillRuleMapper::toDto);
    }

    /**
     * Get all the sysFillRules by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<SysFillRuleDTO> findAllByExample(Example<SysFillRule> example, Pageable pageable) {
        log.debug("Request to get SysFillRule by example");
        return sysFillRuleRepository.findAll(example, pageable).map(sysFillRuleMapper::toDto);
    }

    /**
     * Update specified field by sysFillRule
     */
    public void updateBatch(SysFillRuleDTO changeSysFillRuleDTO, List<String> fieldNames, List<Long> ids) {
        sysFillRuleRepository
            .findAllById(ids)
            .stream()
            .peek(sysFillRule ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(sysFillRule, fieldName, BeanUtil.getFieldValue(changeSysFillRuleDTO, fieldName))
                )
            )
            .forEach(sysFillRuleRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
