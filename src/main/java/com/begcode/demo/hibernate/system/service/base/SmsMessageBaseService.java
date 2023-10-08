package com.begcode.demo.hibernate.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.system.domain.SmsMessage;
import com.begcode.demo.hibernate.system.repository.SmsMessageRepository;
import com.begcode.demo.hibernate.system.service.dto.SmsMessageDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsMessageMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.system.domain.SmsMessage}.
 */
public class SmsMessageBaseService {

    private final Logger log = LoggerFactory.getLogger(SmsMessageBaseService.class);

    protected final SmsMessageRepository smsMessageRepository;

    protected final CacheManager cacheManager;

    protected final SmsMessageMapper smsMessageMapper;

    public SmsMessageBaseService(SmsMessageRepository smsMessageRepository, CacheManager cacheManager, SmsMessageMapper smsMessageMapper) {
        this.smsMessageRepository = smsMessageRepository;
        this.cacheManager = cacheManager;
        this.smsMessageMapper = smsMessageMapper;
    }

    /**
     * Save a smsMessage.
     *
     * @param smsMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsMessageDTO save(SmsMessageDTO smsMessageDTO) {
        log.debug("Request to save SmsMessage : {}", smsMessageDTO);
        SmsMessage smsMessage = smsMessageMapper.toEntity(smsMessageDTO);
        smsMessage = smsMessageRepository.save(smsMessage);
        return smsMessageMapper.toDto(smsMessage);
    }

    /**
     * Update a smsMessage.
     *
     * @param smsMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsMessageDTO update(SmsMessageDTO smsMessageDTO) {
        log.debug("Request to update SmsMessage : {}", smsMessageDTO);

        SmsMessage smsMessage = smsMessageMapper.toEntity(smsMessageDTO);
        smsMessage = smsMessageRepository.save(smsMessage);
        return smsMessageMapper.toDto(smsMessage);
    }

    /**
     * Partially update a smsMessage.
     *
     * @param smsMessageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SmsMessageDTO> partialUpdate(SmsMessageDTO smsMessageDTO) {
        log.debug("Request to partially update SmsMessage : {}", smsMessageDTO);

        return smsMessageRepository
            .findById(smsMessageDTO.getId())
            .map(existingSmsMessage -> {
                smsMessageMapper.partialUpdate(existingSmsMessage, smsMessageDTO);

                return existingSmsMessage;
            })
            .map(smsMessageRepository::save)
            .map(smsMessageMapper::toDto);
    }

    /**
     * Get all the smsMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsMessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmsMessages");
        return smsMessageRepository.findAll(pageable).map(smsMessageMapper::toDto);
    }

    /**
     * Get one smsMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsMessageDTO> findOne(Long id) {
        log.debug("Request to get SmsMessage : {}", id);
        return smsMessageRepository.findById(id).map(smsMessageMapper::toDto);
    }

    /**
     * Delete the smsMessage by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SmsMessage : {}", id);

        smsMessageRepository.deleteById(id);
    }

    /**
     * Get one smsMessage by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<SmsMessageDTO> findOneByExample(Example<SmsMessage> example) {
        log.debug("Request to get SmsMessage by example");
        return smsMessageRepository.findAll(example).stream().findFirst().map(smsMessageMapper::toDto);
    }

    /**
     * Get all the smsMessages by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsMessageDTO> findAllByExample(Example<SmsMessage> example, Pageable pageable) {
        log.debug("Request to get SmsMessage by example");
        return smsMessageRepository.findAll(example, pageable).map(smsMessageMapper::toDto);
    }

    /**
     * Update specified field by smsMessage
     */
    public void updateBatch(SmsMessageDTO changeSmsMessageDTO, List<String> fieldNames, List<Long> ids) {
        smsMessageRepository
            .findAllById(ids)
            .stream()
            .peek(smsMessage ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(smsMessage, fieldName, BeanUtil.getFieldValue(changeSmsMessageDTO, fieldName))
                )
            )
            .forEach(smsMessageRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
