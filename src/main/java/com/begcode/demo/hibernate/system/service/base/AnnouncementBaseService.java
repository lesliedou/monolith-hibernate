package com.begcode.demo.hibernate.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.enumeration.AnnoSendStatus;
import com.begcode.demo.hibernate.security.SecurityUtils;
import com.begcode.demo.hibernate.service.UserQueryService;
import com.begcode.demo.hibernate.service.criteria.UserCriteria;
import com.begcode.demo.hibernate.system.domain.Announcement;
import com.begcode.demo.hibernate.system.domain.AnnouncementRecord;
import com.begcode.demo.hibernate.system.repository.AnnouncementRecordRepository;
import com.begcode.demo.hibernate.system.repository.AnnouncementRepository;
import com.begcode.demo.hibernate.system.service.dto.AnnouncementDTO;
import com.begcode.demo.hibernate.system.service.mapper.AnnouncementMapper;
import com.begcode.demo.hibernate.web.rest.errors.BadRequestAlertException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.system.domain.Announcement}.
 */
public class AnnouncementBaseService {

    private final Logger log = LoggerFactory.getLogger(AnnouncementBaseService.class);

    protected final UserQueryService userQueryService;

    protected final AnnouncementRecordRepository announcementRecordRepository;

    protected final AnnouncementRepository announcementRepository;

    protected final CacheManager cacheManager;

    protected final AnnouncementMapper announcementMapper;

    public AnnouncementBaseService(
        UserQueryService userQueryService,
        AnnouncementRecordRepository announcementRecordRepository,
        AnnouncementRepository announcementRepository,
        CacheManager cacheManager,
        AnnouncementMapper announcementMapper
    ) {
        this.userQueryService = userQueryService;
        this.announcementRecordRepository = announcementRecordRepository;
        this.announcementRepository = announcementRepository;
        this.cacheManager = cacheManager;
        this.announcementMapper = announcementMapper;
    }

    /**
     * Save a announcement.
     *
     * @param announcementDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnouncementDTO save(AnnouncementDTO announcementDTO) {
        log.debug("Request to save Announcement : {}", announcementDTO);
        Announcement announcement = announcementMapper.toEntity(announcementDTO);
        announcement = announcementRepository.save(announcement);
        return announcementMapper.toDto(announcement);
    }

    /**
     * Update a announcement.
     *
     * @param announcementDTO the entity to save.
     * @return the persisted entity.
     */
    public AnnouncementDTO update(AnnouncementDTO announcementDTO) {
        log.debug("Request to update Announcement : {}", announcementDTO);

        Announcement announcement = announcementMapper.toEntity(announcementDTO);
        announcement = announcementRepository.save(announcement);
        return announcementMapper.toDto(announcement);
    }

    /**
     * Partially update a announcement.
     *
     * @param announcementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnnouncementDTO> partialUpdate(AnnouncementDTO announcementDTO) {
        log.debug("Request to partially update Announcement : {}", announcementDTO);

        return announcementRepository
            .findById(announcementDTO.getId())
            .map(existingAnnouncement -> {
                announcementMapper.partialUpdate(existingAnnouncement, announcementDTO);

                return existingAnnouncement;
            })
            .map(announcementRepository::save)
            .map(announcementMapper::toDto);
    }

    /**
     * Get all the announcements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Announcements");
        return announcementRepository.findAll(pageable).map(announcementMapper::toDto);
    }

    /**
     * Get one announcement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnnouncementDTO> findOne(Long id) {
        log.debug("Request to get Announcement : {}", id);
        return announcementRepository.findById(id).map(announcementMapper::toDto);
    }

    /**
     * Delete the announcement by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Announcement : {}", id);

        announcementRepository.deleteById(id);
    }

    public void release(Long id) {
        Announcement announcement = announcementRepository.getOne(id);
        if (announcement != null) {
            announcement
                .sendStatus(AnnoSendStatus.RELEASED)
                .sendTime(ZonedDateTime.now())
                .senderId(SecurityUtils.getCurrentUserId().orElse(null));
            announcement = announcementRepository.save(announcement);
            List<AnnouncementRecord> records = new ArrayList<>();
            ZonedDateTime sendTime = ZonedDateTime.now();
            Long[] userIds = {};
            UserCriteria criteria = new UserCriteria();
            List<Long> receiverIds = Arrays
                .stream(announcement.getReceiverIds().split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
            switch (announcement.getReceiverType()) {
                case ALL:
                    return;
                case USER:
                    userIds = Arrays.stream((announcement.getReceiverIds().split(","))).map(Long::valueOf).toArray(Long[]::new);
                    break;
                case POSITION:
                    criteria.positionId().setIn(receiverIds);
                    userIds = userQueryService.getFieldByCriteria(Long.class, "id", true, criteria).toArray(userIds);
                    break;
                case DEPARTMENT:
                    criteria.departmentId().setIn(receiverIds);
                    userIds = userQueryService.getFieldByCriteria(Long.class, "id", true, criteria).toArray(userIds);
                    break;
                case AUTHORITY:
                    criteria.authoritiesId().setIn(receiverIds);
                    userIds = userQueryService.getFieldByCriteria(Long.class, "id", true, criteria).toArray(userIds);
                    break;
            }
            for (Long userId : userIds) {
                records.add(new AnnouncementRecord().anntId(announcement.getId()).userId(userId).hasRead(false));
            }
            announcementRecordRepository.saveAll(records);
        } else {
            throw new BadRequestAlertException("未找到指定Id的通知", "Announcement", "IdNotFound");
        }
    }

    /**
     * Get one announcement by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<AnnouncementDTO> findOneByExample(Example<Announcement> example) {
        log.debug("Request to get Announcement by example");
        return announcementRepository.findAll(example).stream().findFirst().map(announcementMapper::toDto);
    }

    /**
     * Get all the announcements by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementDTO> findAllByExample(Example<Announcement> example, Pageable pageable) {
        log.debug("Request to get Announcement by example");
        return announcementRepository.findAll(example, pageable).map(announcementMapper::toDto);
    }

    /**
     * Update specified field by announcement
     */
    public void updateBatch(AnnouncementDTO changeAnnouncementDTO, List<String> fieldNames, List<Long> ids) {
        announcementRepository
            .findAllById(ids)
            .stream()
            .peek(announcement ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(announcement, fieldName, BeanUtil.getFieldValue(changeAnnouncementDTO, fieldName))
                )
            )
            .forEach(announcementRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
