package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.domain.Department;
import com.begcode.demo.hibernate.repository.DepartmentRepository;
import com.begcode.demo.hibernate.service.dto.DepartmentDTO;
import com.begcode.demo.hibernate.service.mapper.DepartmentMapper;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.Department}.
 */
public class DepartmentBaseService {

    private final Logger log = LoggerFactory.getLogger(DepartmentBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.domain.Department.class.getName() + ".parent",
        com.begcode.demo.hibernate.domain.Authority.class.getName() + ".department",
        com.begcode.demo.hibernate.domain.Department.class.getName() + ".children",
        com.begcode.demo.hibernate.domain.User.class.getName() + ".department"
    );

    protected final DepartmentRepository departmentRepository;

    protected final CacheManager cacheManager;

    protected final DepartmentMapper departmentMapper;

    public DepartmentBaseService(DepartmentRepository departmentRepository, CacheManager cacheManager, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.cacheManager = cacheManager;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Save a department.
     *
     * @param departmentDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        log.debug("Request to save Department : {}", departmentDTO);
        Department department = departmentMapper.toEntity(departmentDTO);
        clearChildrenCache();
        department = departmentRepository.save(department);
        // 更新缓存
        if (department.getParent() != null) {
            department.getParent().addChildren(department);
        }
        return departmentMapper.toDto(department);
    }

    /**
     * Update a department.
     *
     * @param departmentDTO the entity to save.
     * @return the persisted entity.
     */
    public DepartmentDTO update(DepartmentDTO departmentDTO) {
        log.debug("Request to update Department : {}", departmentDTO);

        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    /**
     * Partially update a department.
     *
     * @param departmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DepartmentDTO> partialUpdate(DepartmentDTO departmentDTO) {
        log.debug("Request to partially update Department : {}", departmentDTO);

        return departmentRepository
            .findById(departmentDTO.getId())
            .map(existingDepartment -> {
                departmentMapper.partialUpdate(existingDepartment, departmentDTO);

                return existingDepartment;
            })
            .map(departmentRepository::save)
            .map(departmentMapper::toDto);
    }

    /**
     * Get all the departments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        return departmentRepository.findAll(pageable).map(departmentMapper::toDto);
    }

    /**
     * Get all the departments with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DepartmentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return departmentRepository.findAllWithEagerRelationships(pageable).map(departmentMapper::toDto);
    }

    /**
     * Get one department by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findOneWithEagerRelationships(id).map(departmentMapper::toDto);
    }

    /**
     * Delete the department by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);

        Department department = departmentRepository.getOne(id);
        if (department.getParent() != null) {
            department.getParent().removeChildren(department);
        }
        if (department.getChildren() != null) {
            department.getChildren().forEach(subDepartment -> subDepartment.setParent(null));
        }

        departmentRepository.deleteById(id);
    }

    /**
     * Get one department by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<DepartmentDTO> findOneByExample(Example<Department> example) {
        log.debug("Request to get Department by example");
        return departmentRepository.findAll(example).stream().findFirst().map(departmentMapper::toDto);
    }

    /**
     * Get all the departments by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findAllByExample(Example<Department> example, Pageable pageable) {
        log.debug("Request to get Department by example");
        return departmentRepository.findAll(example, pageable).map(departmentMapper::toDto);
    }

    /**
     * Update specified field by department
     */
    public void updateBatch(DepartmentDTO changeDepartmentDTO, List<String> fieldNames, List<Long> ids) {
        departmentRepository
            .findAllById(ids)
            .stream()
            .peek(department ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(department, fieldName, BeanUtil.getFieldValue(changeDepartmentDTO, fieldName))
                )
            )
            .forEach(departmentRepository::save);
    }

    // 清除children缓存
    private void clearChildrenCache() {
        Objects.requireNonNull(cacheManager.getCache(com.begcode.demo.hibernate.domain.Department.class.getName() + ".children")).clear();
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
