package com.begcode.demo.hibernate.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.security.SecurityUtils;
import com.begcode.demo.hibernate.system.domain.AnnouncementRecord;
import com.begcode.demo.hibernate.system.repository.AnnouncementRecordRepository;
import com.begcode.demo.hibernate.system.service.dto.AnnouncementDTO;
import com.begcode.demo.hibernate.system.service.dto.AnnouncementRecordDTO;
import com.begcode.demo.hibernate.system.service.mapper.AnnouncementRecordMapper;
import java.time.ZonedDateTime;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.system.domain.AnnouncementRecord}.
 */
public class AnnouncementRecordBaseService {

    private final Logger log = LoggerFactory.getLogger(AnnouncementRecordBaseService.class);

    protected final AnnouncementRecordRepository announcementRecordRepository;

    protected final CacheManager cacheManager;

    protected final AnnouncementRecordMapper announcementRecordMapper;

    public AnnouncementRecordBaseService(
        AnnouncementRecordRepository announcementRecordRepository,
        CacheManager cacheManager,
        AnnouncementRecordMapper announcementRecordMapper
    ) {
        this.announcementRecordRepository = announcementRecordRepository;
        this.cacheManager = cacheManager;
        this.announcementRecordMapper = announcementRecordMapper;
    }

    /**
     * Save a announcementRecord.
     *
     * @param announcementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnouncementRecordDTO save(AnnouncementRecordDTO announcementRecordDTO) {
        log.debug("Request to save AnnouncementRecord : {}", announcementRecordDTO);
        AnnouncementRecord announcementRecord = announcementRecordMapper.toEntity(announcementRecordDTO);
        announcementRecord = announcementRecordRepository.save(announcementRecord);
        return announcementRecordMapper.toDto(announcementRecord);
    }

    /**
     * Update a announcementRecord.
     *
     * @param announcementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnouncementRecordDTO update(AnnouncementRecordDTO announcementRecordDTO) {
        log.debug("Request to update AnnouncementRecord : {}", announcementRecordDTO);

        AnnouncementRecord announcementRecord = announcementRecordMapper.toEntity(announcementRecordDTO);
        announcementRecord = announcementRecordRepository.save(announcementRecord);
        return announcementRecordMapper.toDto(announcementRecord);
    }

    /**
     * Partially update a announcementRecord.
     *
     * @param announcementRecordDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnnouncementRecordDTO> partialUpdate(AnnouncementRecordDTO announcementRecordDTO) {
        log.debug("Request to partially update AnnouncementRecord : {}", announcementRecordDTO);

        return announcementRecordRepository
            .findById(announcementRecordDTO.getId())
            .map(existingAnnouncementRecord -> {
                announcementRecordMapper.partialUpdate(existingAnnouncementRecord, announcementRecordDTO);

                return existingAnnouncementRecord;
            })
            .map(announcementRecordRepository::save)
            .map(announcementRecordMapper::toDto);
    }

    /**
     * Get all the announcementRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnnouncementRecords");
        return announcementRecordRepository.findAll(pageable).map(announcementRecordMapper::toDto);
    }

    /**
     * Get one announcementRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnnouncementRecordDTO> findOne(Long id) {
        log.debug("Request to get AnnouncementRecord : {}", id);
        return announcementRecordRepository.findById(id).map(announcementRecordMapper::toDto);
    }

    /**
     * Delete the announcementRecord by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete AnnouncementRecord : {}", id);

        announcementRecordRepository.deleteById(id);
    }

    public void updateRecord(List<AnnouncementDTO> announcementDTOS) {
        Long userId = SecurityUtils.getCurrentUserId().orElseThrow();
        announcementDTOS.forEach(announcementDTO -> {
            Long anntId = announcementDTO.getId();
            if (!announcementRecordRepository.exists(Example.of(new AnnouncementRecord().anntId(announcementDTO.getId()).userId(userId)))) {
                announcementRecordRepository.save(new AnnouncementRecord().anntId(anntId).userId(userId));
            }
        });
    }

    public void setRead(Long anntId) {
        Long userId = SecurityUtils.getCurrentUserId().orElseThrow();

        Optional<AnnouncementRecord> one = announcementRecordRepository.findOne(
            Example.of(new AnnouncementRecord().anntId(anntId).userId(userId))
        );
        if (one.isPresent()) {
            one.get().hasRead(true).readTime(ZonedDateTime.now());
            announcementRecordRepository.save(one.get());
        }
    }

    /**
     * Get one announcementRecord by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<AnnouncementRecordDTO> findOneByExample(Example<AnnouncementRecord> example) {
        log.debug("Request to get AnnouncementRecord by example");
        return announcementRecordRepository.findAll(example).stream().findFirst().map(announcementRecordMapper::toDto);
    }

    /**
     * Get all the announcementRecords by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementRecordDTO> findAllByExample(Example<AnnouncementRecord> example, Pageable pageable) {
        log.debug("Request to get AnnouncementRecord by example");
        return announcementRecordRepository.findAll(example, pageable).map(announcementRecordMapper::toDto);
    }

    /**
     * Update specified field by announcementRecord
     */
    public void updateBatch(AnnouncementRecordDTO changeAnnouncementRecordDTO, List<String> fieldNames, List<Long> ids) {
        announcementRecordRepository
            .findAllById(ids)
            .stream()
            .peek(announcementRecord ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(announcementRecord, fieldName, BeanUtil.getFieldValue(changeAnnouncementRecordDTO, fieldName))
                )
            )
            .forEach(announcementRecordRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
