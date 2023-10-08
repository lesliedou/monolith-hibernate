package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.ApiPermission;
import com.begcode.demo.hibernate.domain.enumeration.ApiPermissionType;
import com.begcode.demo.hibernate.repository.ApiPermissionRepository;
import com.begcode.demo.hibernate.security.SecurityUtils;
import com.begcode.demo.hibernate.security.annotation.PermissionDefine;
import com.begcode.demo.hibernate.service.dto.ApiPermissionDTO;
import com.begcode.demo.hibernate.service.mapper.ApiPermissionMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.ApiPermission}.
 */
public class ApiPermissionBaseService {

    private final Logger log = LoggerFactory.getLogger(ApiPermissionBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.domain.ApiPermission.class.getName() + ".parent",
        com.begcode.demo.hibernate.domain.ApiPermission.class.getName() + ".children",
        com.begcode.demo.hibernate.domain.Authority.class.getName() + ".apiPermissions"
    );

    protected final ApiPermissionRepository apiPermissionRepository;

    protected final CacheManager cacheManager;

    protected final ApiPermissionMapper apiPermissionMapper;

    protected final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public ApiPermissionBaseService(
        ApiPermissionRepository apiPermissionRepository,
        CacheManager cacheManager,
        ApiPermissionMapper apiPermissionMapper,
        RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        this.apiPermissionRepository = apiPermissionRepository;
        this.cacheManager = cacheManager;
        this.apiPermissionMapper = apiPermissionMapper;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    /**
     * Save a apiPermission.
     *
     * @param apiPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public ApiPermissionDTO save(ApiPermissionDTO apiPermissionDTO) {
        log.debug("Request to save ApiPermission : {}", apiPermissionDTO);
        ApiPermission apiPermission = apiPermissionMapper.toEntity(apiPermissionDTO);
        clearChildrenCache();
        apiPermission = apiPermissionRepository.save(apiPermission);
        // 更新缓存
        if (apiPermission.getParent() != null) {
            apiPermission.getParent().addChildren(apiPermission);
        }
        return apiPermissionMapper.toDto(apiPermission);
    }

    /**
     * Update a apiPermission.
     *
     * @param apiPermissionDTO the entity to save.
     * @return the persisted entity.
     */
    public ApiPermissionDTO update(ApiPermissionDTO apiPermissionDTO) {
        log.debug("Request to update ApiPermission : {}", apiPermissionDTO);

        ApiPermission apiPermission = apiPermissionMapper.toEntity(apiPermissionDTO);
        apiPermission = apiPermissionRepository.save(apiPermission);
        return apiPermissionMapper.toDto(apiPermission);
    }

    /**
     * Partially update a apiPermission.
     *
     * @param apiPermissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ApiPermissionDTO> partialUpdate(ApiPermissionDTO apiPermissionDTO) {
        log.debug("Request to partially update ApiPermission : {}", apiPermissionDTO);

        return apiPermissionRepository
            .findById(apiPermissionDTO.getId())
            .map(existingApiPermission -> {
                apiPermissionMapper.partialUpdate(existingApiPermission, apiPermissionDTO);

                return existingApiPermission;
            })
            .map(apiPermissionRepository::save)
            .map(apiPermissionMapper::toDto);
    }

    /**
     * Get all the apiPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ApiPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiPermissions");
        return apiPermissionRepository.findAll(pageable).map(apiPermissionMapper::toDto);
    }

    /**
     * Get all the apiPermissions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ApiPermissionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return apiPermissionRepository.findAllWithEagerRelationships(pageable).map(apiPermissionMapper::toDto);
    }

    /**
     * Get one apiPermission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ApiPermissionDTO> findOne(Long id) {
        log.debug("Request to get ApiPermission : {}", id);
        return apiPermissionRepository.findOneWithEagerRelationships(id).map(apiPermissionMapper::toDto);
    }

    /**
     * Delete the apiPermission by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete ApiPermission : {}", id);

        ApiPermission apiPermission = apiPermissionRepository.getOne(id);
        if (apiPermission.getParent() != null) {
            apiPermission.getParent().removeChildren(apiPermission);
        }
        if (apiPermission.getChildren() != null) {
            apiPermission.getChildren().forEach(subApiPermission -> subApiPermission.setParent(null));
        }

        apiPermissionRepository.deleteById(id);
    }

    /**
     * Get all the apiPermissions.
     *
     * @param type the ApiPermissionType.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ApiPermissionDTO> findAllByType(ApiPermissionType type) {
        log.debug("Request to get all ApiPermissions by type");
        return apiPermissionMapper.toDto(apiPermissionRepository.findAllByType(type));
    }

    /**
     * regenerate ApiPermissions from Annotation
     */
    public void regenerateApiPermissions() {
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            HandlerMethod method = m.getValue();
            PermissionDefine permissionDefineAnnotation = method.getMethodAnnotation(PermissionDefine.class);
            if (permissionDefineAnnotation != null) {
                // 处理group
                ApiPermission apiPermissionGroup = apiPermissionRepository
                    .findOneByCode(permissionDefineAnnotation.groupCode())
                    .orElseGet(() ->
                        apiPermissionRepository.save(
                            new ApiPermission()
                                .code(permissionDefineAnnotation.groupCode())
                                .name(permissionDefineAnnotation.groupName())
                                .type(ApiPermissionType.BUSINESS)
                        )
                    );
                // 处理实体
                ApiPermission apiPermissionEntity = apiPermissionRepository
                    .findOneByCode(permissionDefineAnnotation.groupCode() + "_" + permissionDefineAnnotation.entityCode())
                    .orElseGet(() ->
                        apiPermissionRepository.save(
                            new ApiPermission()
                                .code(permissionDefineAnnotation.groupCode() + "_" + permissionDefineAnnotation.entityCode())
                                .name(permissionDefineAnnotation.entityName())
                                .type(ApiPermissionType.ENTITY)
                        )
                    );
                apiPermissionRepository.save(apiPermissionEntity.parent(apiPermissionGroup));
                // 处理permission
                // 获得相关的methodType

                ApiPermission apiPermission = apiPermissionRepository
                    .findOneByCode(
                        permissionDefineAnnotation.groupCode() +
                        "_" +
                        permissionDefineAnnotation.entityCode() +
                        "_" +
                        permissionDefineAnnotation.permissionCode()
                    )
                    .orElseGet(() ->
                        apiPermissionRepository.save(
                            new ApiPermission()
                                .code(
                                    permissionDefineAnnotation.groupCode() +
                                    "_" +
                                    permissionDefineAnnotation.entityCode() +
                                    "_" +
                                    permissionDefineAnnotation.permissionCode()
                                )
                                .name(permissionDefineAnnotation.permissionName())
                                .parent(apiPermissionEntity)
                                .type(ApiPermissionType.API)
                                .status(permissionDefineAnnotation.state())
                        )
                    );

                GetMapping getMappingAnnotation = method.getMethodAnnotation(GetMapping.class);
                PostMapping postMappingAnnotation = method.getMethodAnnotation(PostMapping.class);
                DeleteMapping deleteMappingAnnotation = method.getMethodAnnotation(DeleteMapping.class);
                PutMapping putMappingAnnotation = method.getMethodAnnotation(PutMapping.class);
                RequestMapping requestMappingAnnotation = method.getMethodAnnotation(RequestMapping.class);
                StringBuilder methodType = new StringBuilder();
                if (getMappingAnnotation != null) {
                    methodType = new StringBuilder("GET");
                }
                if (postMappingAnnotation != null) {
                    methodType = new StringBuilder("POST");
                }
                if (deleteMappingAnnotation != null) {
                    methodType = new StringBuilder("DELETE");
                }
                if (putMappingAnnotation != null) {
                    methodType = new StringBuilder("PUT");
                }
                if (requestMappingAnnotation != null && requestMappingAnnotation.method() != null) {
                    if (requestMappingAnnotation.method().length > 0) {
                        RequestMethod[] methods = requestMappingAnnotation.method();
                        for (RequestMethod r : methods) {
                            if (methodType.indexOf(r.name()) == -1) {
                                methodType.append(",").append(r.name());
                            }
                        }
                        if (methodType.charAt(0) == ',') {
                            methodType.deleteCharAt(0);
                        }
                    }
                }
                // url
                PatternsRequestCondition patternsCondition = m.getKey().getPatternsCondition();
                String url = patternsCondition.toString();
                apiPermission.method(methodType.toString()).url(url).status(permissionDefineAnnotation.state());
                apiPermissionRepository.save(apiPermission.parent(apiPermissionEntity));
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ApiPermissionDTO> findAllApiPermissionsByCurrentUser() {
        if (SecurityUtils.isAuthenticated()) {
            return apiPermissionMapper.toDto(apiPermissionRepository.findAllApiPermissionsByCurrentUser());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Get one apiPermission by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<ApiPermissionDTO> findOneByExample(Example<ApiPermission> example) {
        log.debug("Request to get ApiPermission by example");
        return apiPermissionRepository.findAll(example).stream().findFirst().map(apiPermissionMapper::toDto);
    }

    /**
     * Get all the apiPermissions by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<ApiPermissionDTO> findAllByExample(Example<ApiPermission> example, Pageable pageable) {
        log.debug("Request to get ApiPermission by example");
        return apiPermissionRepository.findAll(example, pageable).map(apiPermissionMapper::toDto);
    }

    /**
     * Update specified field by apiPermission
     */
    public void updateBatch(ApiPermissionDTO changeApiPermissionDTO, List<String> fieldNames, List<Long> ids) {
        apiPermissionRepository
            .findAllById(ids)
            .stream()
            .peek(apiPermission ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(apiPermission, fieldName, BeanUtil.getFieldValue(changeApiPermissionDTO, fieldName))
                )
            )
            .forEach(apiPermissionRepository::save);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects
            .requireNonNull(cacheManager.getCache(com.begcode.demo.hibernate.domain.ApiPermission.class.getName() + ".children"))
            .clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
