package com.begcode.demo.hibernate.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.system.domain.SmsTemplate;
import com.begcode.demo.hibernate.system.repository.SmsTemplateRepository;
import com.begcode.demo.hibernate.system.service.dto.SmsTemplateDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsTemplateMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.system.domain.SmsTemplate}.
 */
public class SmsTemplateBaseService {

    private final Logger log = LoggerFactory.getLogger(SmsTemplateBaseService.class);

    protected final SmsTemplateRepository smsTemplateRepository;

    protected final CacheManager cacheManager;

    protected final SmsTemplateMapper smsTemplateMapper;

    public SmsTemplateBaseService(
        SmsTemplateRepository smsTemplateRepository,
        CacheManager cacheManager,
        SmsTemplateMapper smsTemplateMapper
    ) {
        this.smsTemplateRepository = smsTemplateRepository;
        this.cacheManager = cacheManager;
        this.smsTemplateMapper = smsTemplateMapper;
    }

    /**
     * Save a smsTemplate.
     *
     * @param smsTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsTemplateDTO save(SmsTemplateDTO smsTemplateDTO) {
        log.debug("Request to save SmsTemplate : {}", smsTemplateDTO);
        SmsTemplate smsTemplate = smsTemplateMapper.toEntity(smsTemplateDTO);
        smsTemplate = smsTemplateRepository.save(smsTemplate);
        return smsTemplateMapper.toDto(smsTemplate);
    }

    /**
     * Update a smsTemplate.
     *
     * @param smsTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsTemplateDTO update(SmsTemplateDTO smsTemplateDTO) {
        log.debug("Request to update SmsTemplate : {}", smsTemplateDTO);

        SmsTemplate smsTemplate = smsTemplateMapper.toEntity(smsTemplateDTO);
        smsTemplate = smsTemplateRepository.save(smsTemplate);
        return smsTemplateMapper.toDto(smsTemplate);
    }

    /**
     * Partially update a smsTemplate.
     *
     * @param smsTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SmsTemplateDTO> partialUpdate(SmsTemplateDTO smsTemplateDTO) {
        log.debug("Request to partially update SmsTemplate : {}", smsTemplateDTO);

        return smsTemplateRepository
            .findById(smsTemplateDTO.getId())
            .map(existingSmsTemplate -> {
                smsTemplateMapper.partialUpdate(existingSmsTemplate, smsTemplateDTO);

                return existingSmsTemplate;
            })
            .map(smsTemplateRepository::save)
            .map(smsTemplateMapper::toDto);
    }

    /**
     * Get all the smsTemplates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsTemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmsTemplates");
        return smsTemplateRepository.findAll(pageable).map(smsTemplateMapper::toDto);
    }

    /**
     * Get one smsTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsTemplateDTO> findOne(Long id) {
        log.debug("Request to get SmsTemplate : {}", id);
        return smsTemplateRepository.findById(id).map(smsTemplateMapper::toDto);
    }

    /**
     * Delete the smsTemplate by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SmsTemplate : {}", id);

        smsTemplateRepository.deleteById(id);
    }

    /**
     * Get one smsTemplate by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<SmsTemplateDTO> findOneByExample(Example<SmsTemplate> example) {
        log.debug("Request to get SmsTemplate by example");
        return smsTemplateRepository.findAll(example).stream().findFirst().map(smsTemplateMapper::toDto);
    }

    /**
     * Get all the smsTemplates by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsTemplateDTO> findAllByExample(Example<SmsTemplate> example, Pageable pageable) {
        log.debug("Request to get SmsTemplate by example");
        return smsTemplateRepository.findAll(example, pageable).map(smsTemplateMapper::toDto);
    }

    /**
     * Update specified field by smsTemplate
     */
    public void updateBatch(SmsTemplateDTO changeSmsTemplateDTO, List<String> fieldNames, List<Long> ids) {
        smsTemplateRepository
            .findAllById(ids)
            .stream()
            .peek(smsTemplate ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(smsTemplate, fieldName, BeanUtil.getFieldValue(changeSmsTemplateDTO, fieldName))
                )
            )
            .forEach(smsTemplateRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
