package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.UReportFile;
import com.begcode.demo.hibernate.repository.UReportFileRepository;
import com.begcode.demo.hibernate.service.dto.UReportFileDTO;
import com.begcode.demo.hibernate.service.mapper.UReportFileMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.UReportFile}.
 */
public class UReportFileBaseService {

    private final Logger log = LoggerFactory.getLogger(UReportFileBaseService.class);

    protected final UReportFileRepository uReportFileRepository;

    protected final CacheManager cacheManager;

    protected final UReportFileMapper uReportFileMapper;

    public UReportFileBaseService(
        UReportFileRepository uReportFileRepository,
        CacheManager cacheManager,
        UReportFileMapper uReportFileMapper
    ) {
        this.uReportFileRepository = uReportFileRepository;
        this.cacheManager = cacheManager;
        this.uReportFileMapper = uReportFileMapper;
    }

    /**
     * Save a uReportFile.
     *
     * @param uReportFileDTO the entity to save.
     * @return the persisted entity.
     */
    public UReportFileDTO save(UReportFileDTO uReportFileDTO) {
        log.debug("Request to save UReportFile : {}", uReportFileDTO);
        UReportFile uReportFile = uReportFileMapper.toEntity(uReportFileDTO);
        uReportFile = uReportFileRepository.save(uReportFile);
        return uReportFileMapper.toDto(uReportFile);
    }

    /**
     * Update a uReportFile.
     *
     * @param uReportFileDTO the entity to save.
     * @return the persisted entity.
     */
    public UReportFileDTO update(UReportFileDTO uReportFileDTO) {
        log.debug("Request to update UReportFile : {}", uReportFileDTO);

        UReportFile uReportFile = uReportFileMapper.toEntity(uReportFileDTO);
        uReportFile = uReportFileRepository.save(uReportFile);
        return uReportFileMapper.toDto(uReportFile);
    }

    /**
     * Partially update a uReportFile.
     *
     * @param uReportFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UReportFileDTO> partialUpdate(UReportFileDTO uReportFileDTO) {
        log.debug("Request to partially update UReportFile : {}", uReportFileDTO);

        return uReportFileRepository
            .findById(uReportFileDTO.getId())
            .map(existingUReportFile -> {
                uReportFileMapper.partialUpdate(existingUReportFile, uReportFileDTO);

                return existingUReportFile;
            })
            .map(uReportFileRepository::save)
            .map(uReportFileMapper::toDto);
    }

    /**
     * Get all the uReportFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UReportFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UReportFiles");
        return uReportFileRepository.findAll(pageable).map(uReportFileMapper::toDto);
    }

    /**
     * Get one uReportFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UReportFileDTO> findOne(Long id) {
        log.debug("Request to get UReportFile : {}", id);
        return uReportFileRepository.findById(id).map(uReportFileMapper::toDto);
    }

    /**
     * Delete the uReportFile by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete UReportFile : {}", id);

        uReportFileRepository.deleteById(id);
    }

    /**
     * Get the uReportFile by name.
     *
     * @param name the name of the entity.
     */
    public Optional<UReportFileDTO> getByName(String name) {
        log.debug("Request to delete UReportFile : {}", name);
        return uReportFileRepository.getByName(name).map(uReportFileMapper::toDto);
    }

    /**
     * Delete the uReportFile by name.
     *
     * @param name the name of the entity.
     */
    public void deleteByName(String name) {
        log.debug("Request to delete UReportFile : {}", name);
        uReportFileRepository.deleteByName(name);
    }

    /**
     * Get one uReportFile by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<UReportFileDTO> findOneByExample(Example<UReportFile> example) {
        log.debug("Request to get UReportFile by example");
        return uReportFileRepository.findAll(example).stream().findFirst().map(uReportFileMapper::toDto);
    }

    /**
     * Get all the uReportFiles by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<UReportFileDTO> findAllByExample(Example<UReportFile> example, Pageable pageable) {
        log.debug("Request to get UReportFile by example");
        return uReportFileRepository.findAll(example, pageable).map(uReportFileMapper::toDto);
    }

    /**
     * Update specified field by uReportFile
     */
    public void updateBatch(UReportFileDTO changeUReportFileDTO, List<String> fieldNames, List<Long> ids) {
        uReportFileRepository
            .findAllById(ids)
            .stream()
            .peek(uReportFile ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(uReportFile, fieldName, BeanUtil.getFieldValue(changeUReportFileDTO, fieldName))
                )
            )
            .forEach(uReportFileRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
