package com.begcode.demo.hibernate.settings.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.settings.domain.Dictionary;
import com.begcode.demo.hibernate.settings.repository.DictionaryRepository;
import com.begcode.demo.hibernate.settings.service.dto.DictionaryDTO;
import com.begcode.demo.hibernate.settings.service.mapper.DictionaryMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.settings.domain.Dictionary}.
 */
public class DictionaryBaseService {

    private final Logger log = LoggerFactory.getLogger(DictionaryBaseService.class);

    protected final DictionaryRepository dictionaryRepository;

    protected final CacheManager cacheManager;

    protected final DictionaryMapper dictionaryMapper;

    public DictionaryBaseService(DictionaryRepository dictionaryRepository, CacheManager cacheManager, DictionaryMapper dictionaryMapper) {
        this.dictionaryRepository = dictionaryRepository;
        this.cacheManager = cacheManager;
        this.dictionaryMapper = dictionaryMapper;
    }

    /**
     * Save a dictionary.
     *
     * @param dictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public DictionaryDTO save(DictionaryDTO dictionaryDTO) {
        log.debug("Request to save Dictionary : {}", dictionaryDTO);
        Dictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);
        dictionary = dictionaryRepository.save(dictionary);
        return dictionaryMapper.toDto(dictionary);
    }

    /**
     * Update a dictionary.
     *
     * @param dictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public DictionaryDTO update(DictionaryDTO dictionaryDTO) {
        log.debug("Request to update Dictionary : {}", dictionaryDTO);

        Dictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);
        dictionary = dictionaryRepository.save(dictionary);
        return dictionaryMapper.toDto(dictionary);
    }

    /**
     * Partially update a dictionary.
     *
     * @param dictionaryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DictionaryDTO> partialUpdate(DictionaryDTO dictionaryDTO) {
        log.debug("Request to partially update Dictionary : {}", dictionaryDTO);

        return dictionaryRepository
            .findById(dictionaryDTO.getId())
            .map(existingDictionary -> {
                dictionaryMapper.partialUpdate(existingDictionary, dictionaryDTO);

                return existingDictionary;
            })
            .map(dictionaryRepository::save)
            .map(dictionaryMapper::toDto);
    }

    /**
     * Get all the dictionaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DictionaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dictionaries");
        return dictionaryRepository.findAll(pageable).map(dictionaryMapper::toDto);
    }

    /**
     * Get one dictionary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DictionaryDTO> findOne(Long id) {
        log.debug("Request to get Dictionary : {}", id);
        return dictionaryRepository.findById(id).map(dictionaryMapper::toDto);
    }

    /**
     * Delete the dictionary by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Dictionary : {}", id);

        dictionaryRepository.deleteById(id);
    }

    /**
     * Get one dictionary by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<DictionaryDTO> findOneByExample(Example<Dictionary> example) {
        log.debug("Request to get Dictionary by example");
        return dictionaryRepository.findAll(example).stream().findFirst().map(dictionaryMapper::toDto);
    }

    /**
     * Get all the dictionaries by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<DictionaryDTO> findAllByExample(Example<Dictionary> example, Pageable pageable) {
        log.debug("Request to get Dictionary by example");
        return dictionaryRepository.findAll(example, pageable).map(dictionaryMapper::toDto);
    }

    /**
     * Update specified field by dictionary
     */
    public void updateBatch(DictionaryDTO changeDictionaryDTO, List<String> fieldNames, List<Long> ids) {
        dictionaryRepository
            .findAllById(ids)
            .stream()
            .peek(dictionary ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(dictionary, fieldName, BeanUtil.getFieldValue(changeDictionaryDTO, fieldName))
                )
            )
            .forEach(dictionaryRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
