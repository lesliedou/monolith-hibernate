package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.ViewPermission;
import com.begcode.demo.hibernate.repository.ViewPermissionRepository;
import com.begcode.demo.hibernate.security.SecurityUtils;
import com.begcode.demo.hibernate.service.dto.ViewPermissionDTO;
import com.begcode.demo.hibernate.service.mapper.ViewPermissionMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.ViewPermission}.
 */
public class ViewPermissionBaseService {

    private final Logger log = LoggerFactory.getLogger(ViewPermissionBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.domain.ViewPermission.class.getName() + ".parent",
        com.begcode.demo.hibernate.domain.ViewPermission.class.getName() + ".children",
        com.begcode.demo.hibernate.domain.Authority.class.getName() + ".viewPermissions"
    );

    protected final ViewPermissionRepository viewPermissionRepository;

    protected final CacheManager cacheManager;

    protected final ViewPermissionMapper viewPermissionMapper;

    public ViewPermissionBaseService(
        ViewPermissionRepository viewPermissionRepository,
        CacheManager cacheManager,
        ViewPermissionMapper viewPermissionMapper
    ) {
        this.viewPermissionRepository = viewPermissionRepository;
        this.cacheManager = cacheManager;
        this.viewPermissionMapper = viewPermissionMapper;
    }

    /**
     * Save a viewPermission.
     *
     * @param viewPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewPermissionDTO save(ViewPermissionDTO viewPermissionDTO) {
        log.debug("Request to save ViewPermission : {}", viewPermissionDTO);
        ViewPermission viewPermission = viewPermissionMapper.toEntity(viewPermissionDTO);
        clearChildrenCache();
        viewPermission = viewPermissionRepository.save(viewPermission);
        // 更新缓存
        if (viewPermission.getParent() != null) {
            viewPermission.getParent().addChildren(viewPermission);
        }
        return viewPermissionMapper.toDto(viewPermission);
    }

    /**
     * Update a viewPermission.
     *
     * @param viewPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewPermissionDTO update(ViewPermissionDTO viewPermissionDTO) {
        log.debug("Request to update ViewPermission : {}", viewPermissionDTO);

        ViewPermission viewPermission = viewPermissionMapper.toEntity(viewPermissionDTO);
        viewPermission = viewPermissionRepository.save(viewPermission);
        return viewPermissionMapper.toDto(viewPermission);
    }

    /**
     * Partially update a viewPermission.
     *
     * @param viewPermissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ViewPermissionDTO> partialUpdate(ViewPermissionDTO viewPermissionDTO) {
        log.debug("Request to partially update ViewPermission : {}", viewPermissionDTO);

        return viewPermissionRepository
            .findById(viewPermissionDTO.getId())
            .map(existingViewPermission -> {
                viewPermissionMapper.partialUpdate(existingViewPermission, viewPermissionDTO);

                return existingViewPermission;
            })
            .map(viewPermissionRepository::save)
            .map(viewPermissionMapper::toDto);
    }

    /**
     * Get all the viewPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ViewPermissions");
        return viewPermissionRepository.findAll(pageable).map(viewPermissionMapper::toDto);
    }

    /**
     * Get all the viewPermissions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ViewPermissionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return viewPermissionRepository.findAllWithEagerRelationships(pageable).map(viewPermissionMapper::toDto);
    }

    /**
     * Get one viewPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViewPermissionDTO> findOne(Long id) {
        log.debug("Request to get ViewPermission : {}", id);
        return viewPermissionRepository.findOneWithEagerRelationships(id).map(viewPermissionMapper::toDto);
    }

    /**
     * Delete the viewPermission by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete ViewPermission : {}", id);

        ViewPermission viewPermission = viewPermissionRepository.getOne(id);
        if (viewPermission.getParent() != null) {
            viewPermission.getParent().removeChildren(viewPermission);
        }
        if (viewPermission.getChildren() != null) {
            viewPermission.getChildren().forEach(subViewPermission -> subViewPermission.setParent(null));
        }

        viewPermissionRepository.deleteById(id);
    }

    /**
     * get all viewPermission by currentUserLogin
     *
     * @return List<ViewPermissionDTO>
     */
    public List<ViewPermissionDTO> getAllByLogin() {
        List<ViewPermission> resultList = new ArrayList<>();
        // 获得所有角色的viewPermission。
        if (!SecurityUtils.isAuthenticated()) {
            return viewPermissionMapper.toDto(resultList);
        }

        List<ViewPermission> userViewPermissions = viewPermissionRepository.findAllViewPermissionsByCurrentUser();
        List<ViewPermission> addViewPermissions = new ArrayList<>();
        userViewPermissions.forEach(viewPermission -> {
            while (viewPermission != null && viewPermission.getParent() != null) {
                Long parentId = viewPermission.getParent().getId();
                if (userViewPermissions.stream().noneMatch(viewPermission1 -> viewPermission1.getId().equals(parentId))) {
                    Optional<ViewPermission> oneNoChildren = viewPermissionRepository.findOneWithEagerRelationships(parentId);
                    if (oneNoChildren.isPresent()) {
                        addViewPermissions.add(oneNoChildren.get());
                        viewPermission = oneNoChildren.get();
                    } else {
                        viewPermission = null;
                    }
                } else {
                    viewPermission = null;
                }
            }
        });
        userViewPermissions.addAll(addViewPermissions);
        // 已经找到所有的当前User相关的ViewPermissions及上级，接下来转换为树形结构。
        userViewPermissions.forEach(userViewPermission -> {
            if (userViewPermission.getParent() == null) {
                Long finalId = userViewPermission.getId();
                if (resultList.stream().noneMatch(resultViewPermission -> resultViewPermission.getId().equals(finalId))) {
                    resultList.add(userViewPermission);
                }
            } else {
                ViewPermission topParent = null;
                while (userViewPermission != null && userViewPermission.getParent() != null) {
                    Long tempId = userViewPermission.getParent().getId();
                    Optional<ViewPermission> tempViewPermission = userViewPermissions
                        .stream()
                        .filter(viewPermission -> viewPermission.getId().equals(tempId))
                        .findAny();
                    if (tempViewPermission.isPresent()) {
                        ViewPermission viewPermission = tempViewPermission.orElseThrow();
                        if (viewPermission.getParent() == null) {
                            topParent = viewPermission;
                            viewPermission.getChildren().add(userViewPermission);
                            userViewPermission = null;
                        } else {
                            viewPermission.getChildren().add(userViewPermission);
                            userViewPermission = viewPermission;
                        }
                    } else {
                        userViewPermission = null;
                    }
                }
                if (topParent != null) {
                    ViewPermission finalTopParent = topParent;
                    Optional<ViewPermission> any = resultList
                        .stream()
                        .filter(resultViewPermission -> resultViewPermission.getId().equals(finalTopParent.getId()))
                        .findAny();
                    if (any.isPresent()) {
                        // 处理子列表
                        Set<ViewPermission> resultChildren = any.orElseThrow().getChildren();
                        Set<ViewPermission> unCheckChildren = topParent.getChildren();
                        addToResult(resultChildren, unCheckChildren);
                    } else {
                        resultList.add(topParent);
                    }
                }
            }
        });
        return viewPermissionMapper.toDto(resultList);
    }

    private void addToResult(Set<ViewPermission> resultChildren, Set<ViewPermission> unCheckChildren) {
        if (!unCheckChildren.isEmpty()) {
            unCheckChildren.forEach(child -> {
                Long childId = child.getId();
                Optional<ViewPermission> any = resultChildren
                    .stream()
                    .filter(viewPermission -> viewPermission.getId().equals(childId))
                    .findFirst();
                if (any.isPresent()) {
                    addToResult(any.orElseThrow().getChildren(), child.getChildren());
                } else {
                    resultChildren.add(child);
                }
            });
        }
    }

    /**
     * Get one viewPermission by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<ViewPermissionDTO> findOneByExample(Example<ViewPermission> example) {
        log.debug("Request to get ViewPermission by example");
        return viewPermissionRepository.findAll(example).stream().findFirst().map(viewPermissionMapper::toDto);
    }

    /**
     * Get all the viewPermissions by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> findAllByExample(Example<ViewPermission> example, Pageable pageable) {
        log.debug("Request to get ViewPermission by example");
        return viewPermissionRepository.findAll(example, pageable).map(viewPermissionMapper::toDto);
    }

    /**
     * Update specified field by viewPermission
     */
    public void updateBatch(ViewPermissionDTO changeViewPermissionDTO, List<String> fieldNames, List<Long> ids) {
        viewPermissionRepository
            .findAllById(ids)
            .stream()
            .peek(viewPermission ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(viewPermission, fieldName, BeanUtil.getFieldValue(changeViewPermissionDTO, fieldName))
                )
            )
            .forEach(viewPermissionRepository::save);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects
            .requireNonNull(cacheManager.getCache(com.begcode.demo.hibernate.domain.ViewPermission.class.getName() + ".children"))
            .clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
