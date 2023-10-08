package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.Authority;
import com.begcode.demo.hibernate.repository.AuthorityRepository;
import com.begcode.demo.hibernate.service.dto.AuthorityDTO;
import com.begcode.demo.hibernate.service.mapper.AuthorityMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.Authority}.
 */
public class AuthorityBaseService {

    private final Logger log = LoggerFactory.getLogger(AuthorityBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.domain.Authority.class.getName() + ".parent",
        com.begcode.demo.hibernate.domain.ViewPermission.class.getName() + ".authorities",
        com.begcode.demo.hibernate.domain.ApiPermission.class.getName() + ".authorities",
        com.begcode.demo.hibernate.domain.Authority.class.getName() + ".children",
        com.begcode.demo.hibernate.domain.User.class.getName() + ".authorities"
    );

    protected final AuthorityRepository authorityRepository;

    protected final CacheManager cacheManager;

    protected final AuthorityMapper authorityMapper;

    public AuthorityBaseService(AuthorityRepository authorityRepository, CacheManager cacheManager, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.authorityMapper = authorityMapper;
    }

    /**
     * Save a authority.
     *
     * @param authorityDTO the entity to save.
     * @return the persisted entity.
     */
    public AuthorityDTO save(AuthorityDTO authorityDTO) {
        log.debug("Request to save Authority : {}", authorityDTO);
        Authority authority = authorityMapper.toEntity(authorityDTO);
        clearChildrenCache();
        authority = authorityRepository.save(authority);
        // 更新缓存
        if (authority.getParent() != null) {
            authority.getParent().addChildren(authority);
        }
        return authorityMapper.toDto(authority);
    }

    /**
     * Update a authority.
     *
     * @param authorityDTO the entity to save.
     * @return the persisted entity.
     */
    public AuthorityDTO update(AuthorityDTO authorityDTO) {
        log.debug("Request to update Authority : {}", authorityDTO);

        Authority authority = authorityMapper.toEntity(authorityDTO);
        authority = authorityRepository.save(authority);
        return authorityMapper.toDto(authority);
    }

    /**
     * Partially update a authority.
     *
     * @param authorityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuthorityDTO> partialUpdate(AuthorityDTO authorityDTO) {
        log.debug("Request to partially update Authority : {}", authorityDTO);

        return authorityRepository
            .findById(authorityDTO.getId())
            .map(existingAuthority -> {
                authorityMapper.partialUpdate(existingAuthority, authorityDTO);

                return existingAuthority;
            })
            .map(authorityRepository::save)
            .map(authorityMapper::toDto);
    }

    /**
     * Get all the authorities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthorityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Authorities");
        return authorityRepository.findAll(pageable).map(authorityMapper::toDto);
    }

    /**
     * Get all the authorities with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AuthorityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return authorityRepository.findAllWithEagerRelationships(pageable).map(authorityMapper::toDto);
    }

    /**
     * Get one authority by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuthorityDTO> findOne(Long id) {
        log.debug("Request to get Authority : {}", id);
        return authorityRepository.findOneWithEagerRelationships(id).map(authorityMapper::toDto);
    }

    /**
     * Delete the authority by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Authority : {}", id);

        Authority authority = authorityRepository.getOne(id);
        if (authority.getParent() != null) {
            authority.getParent().removeChildren(authority);
        }
        if (authority.getChildren() != null) {
            authority.getChildren().forEach(subAuthority -> subAuthority.setParent(null));
        }

        authorityRepository.deleteById(id);
    }

    /**
     * Get one authority by code.
     *
     * @param code the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuthorityDTO> findFirstByCode(String code) {
        log.debug("Request to get Authority : {}", code);
        return authorityRepository.findFirstByCode(code).map(authorityMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<AuthorityDTO> findByUserId(Long userId) {
        return authorityMapper.toDto(authorityRepository.findByUsersId(userId));
    }

    /**
     * Get one authority by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<AuthorityDTO> findOneByExample(Example<Authority> example) {
        log.debug("Request to get Authority by example");
        return authorityRepository.findAll(example).stream().findFirst().map(authorityMapper::toDto);
    }

    /**
     * Get all the authorities by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthorityDTO> findAllByExample(Example<Authority> example, Pageable pageable) {
        log.debug("Request to get Authority by example");
        return authorityRepository.findAll(example, pageable).map(authorityMapper::toDto);
    }

    /**
     * Update specified field by authority
     */
    public void updateBatch(AuthorityDTO changeAuthorityDTO, List<String> fieldNames, List<Long> ids) {
        authorityRepository
            .findAllById(ids)
            .stream()
            .peek(authority ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(authority, fieldName, BeanUtil.getFieldValue(changeAuthorityDTO, fieldName))
                )
            )
            .forEach(authorityRepository::save);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.begcode.demo.hibernate.domain.Authority.class.getName() + ".children")).clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
