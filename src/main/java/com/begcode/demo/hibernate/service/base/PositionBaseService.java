package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.Position;
import com.begcode.demo.hibernate.repository.PositionRepository;
import com.begcode.demo.hibernate.service.dto.PositionDTO;
import com.begcode.demo.hibernate.service.mapper.PositionMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.Position}.
 */
public class PositionBaseService {

    private final Logger log = LoggerFactory.getLogger(PositionBaseService.class);

    protected final PositionRepository positionRepository;

    protected final CacheManager cacheManager;

    protected final PositionMapper positionMapper;

    public PositionBaseService(PositionRepository positionRepository, CacheManager cacheManager, PositionMapper positionMapper) {
        this.positionRepository = positionRepository;
        this.cacheManager = cacheManager;
        this.positionMapper = positionMapper;
    }

    /**
     * Save a position.
     *
     * @param positionDTO the entity to save.
     * @return the persisted entity.
     */
    public PositionDTO save(PositionDTO positionDTO) {
        log.debug("Request to save Position : {}", positionDTO);
        Position position = positionMapper.toEntity(positionDTO);
        position = positionRepository.save(position);
        return positionMapper.toDto(position);
    }

    /**
     * Update a position.
     *
     * @param positionDTO the entity to save.
     * @return the persisted entity.
     */
    public PositionDTO update(PositionDTO positionDTO) {
        log.debug("Request to update Position : {}", positionDTO);

        Position position = positionMapper.toEntity(positionDTO);
        position = positionRepository.save(position);
        return positionMapper.toDto(position);
    }

    /**
     * Partially update a position.
     *
     * @param positionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PositionDTO> partialUpdate(PositionDTO positionDTO) {
        log.debug("Request to partially update Position : {}", positionDTO);

        return positionRepository
            .findById(positionDTO.getId())
            .map(existingPosition -> {
                positionMapper.partialUpdate(existingPosition, positionDTO);

                return existingPosition;
            })
            .map(positionRepository::save)
            .map(positionMapper::toDto);
    }

    /**
     * Get all the positions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Positions");
        return positionRepository.findAll(pageable).map(positionMapper::toDto);
    }

    /**
     * Get one position by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PositionDTO> findOne(Long id) {
        log.debug("Request to get Position : {}", id);
        return positionRepository.findById(id).map(positionMapper::toDto);
    }

    /**
     * Delete the position by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Position : {}", id);

        positionRepository.deleteById(id);
    }

    /**
     * Get one position by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<PositionDTO> findOneByExample(Example<Position> example) {
        log.debug("Request to get Position by example");
        return positionRepository.findAll(example).stream().findFirst().map(positionMapper::toDto);
    }

    /**
     * Get all the positions by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<PositionDTO> findAllByExample(Example<Position> example, Pageable pageable) {
        log.debug("Request to get Position by example");
        return positionRepository.findAll(example, pageable).map(positionMapper::toDto);
    }

    /**
     * Update specified field by position
     */
    public void updateBatch(PositionDTO changePositionDTO, List<String> fieldNames, List<Long> ids) {
        positionRepository
            .findAllById(ids)
            .stream()
            .peek(position ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(position, fieldName, BeanUtil.getFieldValue(changePositionDTO, fieldName))
                )
            )
            .forEach(positionRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
