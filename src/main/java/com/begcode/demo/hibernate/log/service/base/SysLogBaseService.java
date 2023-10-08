package com.begcode.demo.hibernate.log.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.log.domain.SysLog;
import com.begcode.demo.hibernate.log.repository.SysLogRepository;
import com.begcode.demo.hibernate.log.service.dto.SysLogDTO;
import com.begcode.demo.hibernate.log.service.mapper.SysLogMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.log.domain.SysLog}.
 */
public class SysLogBaseService {

    private final Logger log = LoggerFactory.getLogger(SysLogBaseService.class);

    protected final SysLogRepository sysLogRepository;

    protected final CacheManager cacheManager;

    protected final SysLogMapper sysLogMapper;

    public SysLogBaseService(SysLogRepository sysLogRepository, CacheManager cacheManager, SysLogMapper sysLogMapper) {
        this.sysLogRepository = sysLogRepository;
        this.cacheManager = cacheManager;
        this.sysLogMapper = sysLogMapper;
    }

    /**
     * Save a sysLog.
     *
     * @param sysLogDTO the entity to save.
     * @return the persisted entity.
     */
    public SysLogDTO save(SysLogDTO sysLogDTO) {
        log.debug("Request to save SysLog : {}", sysLogDTO);
        SysLog sysLog = sysLogMapper.toEntity(sysLogDTO);
        sysLog = sysLogRepository.save(sysLog);
        return sysLogMapper.toDto(sysLog);
    }

    /**
     * Update a sysLog.
     *
     * @param sysLogDTO the entity to save.
     * @return the persisted entity.
     */
    public SysLogDTO update(SysLogDTO sysLogDTO) {
        log.debug("Request to update SysLog : {}", sysLogDTO);

        SysLog sysLog = sysLogMapper.toEntity(sysLogDTO);
        sysLog = sysLogRepository.save(sysLog);
        return sysLogMapper.toDto(sysLog);
    }

    /**
     * Partially update a sysLog.
     *
     * @param sysLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SysLogDTO> partialUpdate(SysLogDTO sysLogDTO) {
        log.debug("Request to partially update SysLog : {}", sysLogDTO);

        return sysLogRepository
            .findById(sysLogDTO.getId())
            .map(existingSysLog -> {
                sysLogMapper.partialUpdate(existingSysLog, sysLogDTO);

                return existingSysLog;
            })
            .map(sysLogRepository::save)
            .map(sysLogMapper::toDto);
    }

    /**
     * Get all the sysLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SysLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysLogs");
        return sysLogRepository.findAll(pageable).map(sysLogMapper::toDto);
    }

    /**
     * Get one sysLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysLogDTO> findOne(Long id) {
        log.debug("Request to get SysLog : {}", id);
        return sysLogRepository.findById(id).map(sysLogMapper::toDto);
    }

    /**
     * Delete the sysLog by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SysLog : {}", id);

        sysLogRepository.deleteById(id);
    }

    /**
     * Get one sysLog by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<SysLogDTO> findOneByExample(Example<SysLog> example) {
        log.debug("Request to get SysLog by example");
        return sysLogRepository.findAll(example).stream().findFirst().map(sysLogMapper::toDto);
    }

    /**
     * Get all the sysLogs by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<SysLogDTO> findAllByExample(Example<SysLog> example, Pageable pageable) {
        log.debug("Request to get SysLog by example");
        return sysLogRepository.findAll(example, pageable).map(sysLogMapper::toDto);
    }

    /**
     * Update specified field by sysLog
     */
    public void updateBatch(SysLogDTO changeSysLogDTO, List<String> fieldNames, List<Long> ids) {
        sysLogRepository
            .findAllById(ids)
            .stream()
            .peek(sysLog ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(sysLog, fieldName, BeanUtil.getFieldValue(changeSysLogDTO, fieldName))
                )
            )
            .forEach(sysLogRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
