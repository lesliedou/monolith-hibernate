package com.begcode.demo.hibernate.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.settings.domain.CommonFieldData;
import com.begcode.demo.hibernate.settings.repository.CommonFieldDataRepository;
import com.begcode.demo.hibernate.settings.service.dto.CommonFieldDataDTO;
import com.begcode.demo.hibernate.settings.service.mapper.CommonFieldDataMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.settings.domain.CommonFieldData}.
 */
public class CommonFieldDataBaseService {

    private final Logger log = LoggerFactory.getLogger(CommonFieldDataBaseService.class);

    protected final CommonFieldDataRepository commonFieldDataRepository;

    protected final CacheManager cacheManager;

    protected final CommonFieldDataMapper commonFieldDataMapper;

    public CommonFieldDataBaseService(
        CommonFieldDataRepository commonFieldDataRepository,
        CacheManager cacheManager,
        CommonFieldDataMapper commonFieldDataMapper
    ) {
        this.commonFieldDataRepository = commonFieldDataRepository;
        this.cacheManager = cacheManager;
        this.commonFieldDataMapper = commonFieldDataMapper;
    }

    /**
     * Save a commonFieldData.
     *
     * @param commonFieldDataDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonFieldDataDTO save(CommonFieldDataDTO commonFieldDataDTO) {
        log.debug("Request to save CommonFieldData : {}", commonFieldDataDTO);
        CommonFieldData commonFieldData = commonFieldDataMapper.toEntity(commonFieldDataDTO);
        commonFieldData = commonFieldDataRepository.save(commonFieldData);
        return commonFieldDataMapper.toDto(commonFieldData);
    }

    /**
     * Update a commonFieldData.
     *
     * @param commonFieldDataDTO the entity to save.
     * @return the persisted entity.
     */
    public CommonFieldDataDTO update(CommonFieldDataDTO commonFieldDataDTO) {
        log.debug("Request to update CommonFieldData : {}", commonFieldDataDTO);

        CommonFieldData commonFieldData = commonFieldDataMapper.toEntity(commonFieldDataDTO);
        commonFieldData = commonFieldDataRepository.save(commonFieldData);
        return commonFieldDataMapper.toDto(commonFieldData);
    }

    /**
     * Partially update a commonFieldData.
     *
     * @param commonFieldDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommonFieldDataDTO> partialUpdate(CommonFieldDataDTO commonFieldDataDTO) {
        log.debug("Request to partially update CommonFieldData : {}", commonFieldDataDTO);

        return commonFieldDataRepository
            .findById(commonFieldDataDTO.getId())
            .map(existingCommonFieldData -> {
                commonFieldDataMapper.partialUpdate(existingCommonFieldData, commonFieldDataDTO);

                return existingCommonFieldData;
            })
            .map(commonFieldDataRepository::save)
            .map(commonFieldDataMapper::toDto);
    }

    /**
     * Get all the commonFieldData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonFieldDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommonFieldData");
        return commonFieldDataRepository.findAll(pageable).map(commonFieldDataMapper::toDto);
    }

    /**
     * Get one commonFieldData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonFieldDataDTO> findOne(Long id) {
        log.debug("Request to get CommonFieldData : {}", id);
        return commonFieldDataRepository.findById(id).map(commonFieldDataMapper::toDto);
    }

    /**
     * Delete the commonFieldData by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete CommonFieldData : {}", id);

        commonFieldDataRepository.deleteById(id);
    }

    /**
     * Get one commonFieldData by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<CommonFieldDataDTO> findOneByExample(Example<CommonFieldData> example) {
        log.debug("Request to get CommonFieldData by example");
        return commonFieldDataRepository.findAll(example).stream().findFirst().map(commonFieldDataMapper::toDto);
    }

    /**
     * Get all the commonFieldData by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonFieldDataDTO> findAllByExample(Example<CommonFieldData> example, Pageable pageable) {
        log.debug("Request to get CommonFieldData by example");
        return commonFieldDataRepository.findAll(example, pageable).map(commonFieldDataMapper::toDto);
    }

    /**
     * Update specified field by commonFieldData
     */
    public void updateBatch(CommonFieldDataDTO changeCommonFieldDataDTO, List<String> fieldNames, List<Long> ids) {
        commonFieldDataRepository
            .findAllById(ids)
            .stream()
            .peek(commonFieldData ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(commonFieldData, fieldName, BeanUtil.getFieldValue(changeCommonFieldDataDTO, fieldName))
                )
            )
            .forEach(commonFieldDataRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
