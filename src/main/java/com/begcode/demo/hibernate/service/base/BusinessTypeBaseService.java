package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.BusinessType;
import com.begcode.demo.hibernate.repository.BusinessTypeRepository;
import com.begcode.demo.hibernate.service.dto.BusinessTypeDTO;
import com.begcode.demo.hibernate.service.mapper.BusinessTypeMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.BusinessType}.
 */
public class BusinessTypeBaseService {

    private final Logger log = LoggerFactory.getLogger(BusinessTypeBaseService.class);

    protected final BusinessTypeRepository businessTypeRepository;

    protected final CacheManager cacheManager;

    protected final BusinessTypeMapper businessTypeMapper;

    public BusinessTypeBaseService(
        BusinessTypeRepository businessTypeRepository,
        CacheManager cacheManager,
        BusinessTypeMapper businessTypeMapper
    ) {
        this.businessTypeRepository = businessTypeRepository;
        this.cacheManager = cacheManager;
        this.businessTypeMapper = businessTypeMapper;
    }

    /**
     * Save a businessType.
     *
     * @param businessTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessTypeDTO save(BusinessTypeDTO businessTypeDTO) {
        log.debug("Request to save BusinessType : {}", businessTypeDTO);
        BusinessType businessType = businessTypeMapper.toEntity(businessTypeDTO);
        businessType = businessTypeRepository.save(businessType);
        return businessTypeMapper.toDto(businessType);
    }

    /**
     * Update a businessType.
     *
     * @param businessTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessTypeDTO update(BusinessTypeDTO businessTypeDTO) {
        log.debug("Request to update BusinessType : {}", businessTypeDTO);

        BusinessType businessType = businessTypeMapper.toEntity(businessTypeDTO);
        businessType = businessTypeRepository.save(businessType);
        return businessTypeMapper.toDto(businessType);
    }

    /**
     * Partially update a businessType.
     *
     * @param businessTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BusinessTypeDTO> partialUpdate(BusinessTypeDTO businessTypeDTO) {
        log.debug("Request to partially update BusinessType : {}", businessTypeDTO);

        return businessTypeRepository
            .findById(businessTypeDTO.getId())
            .map(existingBusinessType -> {
                businessTypeMapper.partialUpdate(existingBusinessType, businessTypeDTO);

                return existingBusinessType;
            })
            .map(businessTypeRepository::save)
            .map(businessTypeMapper::toDto);
    }

    /**
     * Get all the businessTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessTypes");
        return businessTypeRepository.findAll(pageable).map(businessTypeMapper::toDto);
    }

    /**
     * Get one businessType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessTypeDTO> findOne(Long id) {
        log.debug("Request to get BusinessType : {}", id);
        return businessTypeRepository.findById(id).map(businessTypeMapper::toDto);
    }

    /**
     * Delete the businessType by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete BusinessType : {}", id);

        businessTypeRepository.deleteById(id);
    }

    /**
     * Get one businessType by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<BusinessTypeDTO> findOneByExample(Example<BusinessType> example) {
        log.debug("Request to get BusinessType by example");
        return businessTypeRepository.findAll(example).stream().findFirst().map(businessTypeMapper::toDto);
    }

    /**
     * Get all the businessTypes by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessTypeDTO> findAllByExample(Example<BusinessType> example, Pageable pageable) {
        log.debug("Request to get BusinessType by example");
        return businessTypeRepository.findAll(example, pageable).map(businessTypeMapper::toDto);
    }

    /**
     * Update specified field by businessType
     */
    public void updateBatch(BusinessTypeDTO changeBusinessTypeDTO, List<String> fieldNames, List<Long> ids) {
        businessTypeRepository
            .findAllById(ids)
            .stream()
            .peek(businessType ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(businessType, fieldName, BeanUtil.getFieldValue(changeBusinessTypeDTO, fieldName))
                )
            )
            .forEach(businessTypeRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
