package com.begcode.demo.hibernate.taskjob.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.taskjob.domain.TaskJobConfig;
import com.begcode.demo.hibernate.taskjob.repository.TaskJobConfigRepository;
import com.begcode.demo.hibernate.taskjob.service.dto.TaskJobConfigDTO;
import com.begcode.demo.hibernate.taskjob.service.mapper.TaskJobConfigMapper;
import java.util.List;
import java.util.Optional;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.taskjob.domain.TaskJobConfig}.
 */
public class TaskJobConfigBaseService {

    private final Logger log = LoggerFactory.getLogger(TaskJobConfigBaseService.class);

    protected final Scheduler scheduler;

    protected final TaskJobConfigRepository taskJobConfigRepository;

    protected final CacheManager cacheManager;

    protected final TaskJobConfigMapper taskJobConfigMapper;

    public TaskJobConfigBaseService(
        Scheduler scheduler,
        TaskJobConfigRepository taskJobConfigRepository,
        CacheManager cacheManager,
        TaskJobConfigMapper taskJobConfigMapper
    ) {
        this.scheduler = scheduler;
        this.taskJobConfigRepository = taskJobConfigRepository;
        this.cacheManager = cacheManager;
        this.taskJobConfigMapper = taskJobConfigMapper;
    }

    /**
     * Save a taskJobConfig.
     *
     * @param taskJobConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskJobConfigDTO save(TaskJobConfigDTO taskJobConfigDTO) {
        log.debug("Request to save TaskJobConfig : {}", taskJobConfigDTO);
        TaskJobConfig taskJobConfig = taskJobConfigMapper.toEntity(taskJobConfigDTO);
        taskJobConfig = taskJobConfigRepository.save(taskJobConfig);
        return taskJobConfigMapper.toDto(taskJobConfig);
    }

    /**
     * Update a taskJobConfig.
     *
     * @param taskJobConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskJobConfigDTO update(TaskJobConfigDTO taskJobConfigDTO) {
        log.debug("Request to update TaskJobConfig : {}", taskJobConfigDTO);

        TaskJobConfig taskJobConfig = taskJobConfigMapper.toEntity(taskJobConfigDTO);
        taskJobConfig = taskJobConfigRepository.save(taskJobConfig);
        return taskJobConfigMapper.toDto(taskJobConfig);
    }

    /**
     * Partially update a taskJobConfig.
     *
     * @param taskJobConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskJobConfigDTO> partialUpdate(TaskJobConfigDTO taskJobConfigDTO) {
        log.debug("Request to partially update TaskJobConfig : {}", taskJobConfigDTO);

        return taskJobConfigRepository
            .findById(taskJobConfigDTO.getId())
            .map(existingTaskJobConfig -> {
                taskJobConfigMapper.partialUpdate(existingTaskJobConfig, taskJobConfigDTO);

                return existingTaskJobConfig;
            })
            .map(taskJobConfigRepository::save)
            .map(taskJobConfigMapper::toDto);
    }

    /**
     * Get all the taskJobConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskJobConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskJobConfigs");
        return taskJobConfigRepository.findAll(pageable).map(taskJobConfigMapper::toDto);
    }

    /**
     * Get one taskJobConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskJobConfigDTO> findOne(Long id) {
        log.debug("Request to get TaskJobConfig : {}", id);
        return taskJobConfigRepository.findById(id).map(taskJobConfigMapper::toDto);
    }

    /**
     * Delete the taskJobConfig by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete TaskJobConfig : {}", id);

        taskJobConfigRepository.deleteById(id);
    }

    private static final String taskIdPrefix = "huayu_task_";

    /**
     * Get one taskJobConfig by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<TaskJobConfigDTO> findOneByExample(Example<TaskJobConfig> example) {
        log.debug("Request to get TaskJobConfig by example");
        return taskJobConfigRepository.findAll(example).stream().findFirst().map(taskJobConfigMapper::toDto);
    }

    /**
     * Get all the taskJobConfigs by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskJobConfigDTO> findAllByExample(Example<TaskJobConfig> example, Pageable pageable) {
        log.debug("Request to get TaskJobConfig by example");
        return taskJobConfigRepository.findAll(example, pageable).map(taskJobConfigMapper::toDto);
    }

    /**
     * Update specified field by taskJobConfig
     */
    public void updateBatch(TaskJobConfigDTO changeTaskJobConfigDTO, List<String> fieldNames, List<Long> ids) {
        taskJobConfigRepository
            .findAllById(ids)
            .stream()
            .peek(taskJobConfig ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(taskJobConfig, fieldName, BeanUtil.getFieldValue(changeTaskJobConfigDTO, fieldName))
                )
            )
            .forEach(taskJobConfigRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
