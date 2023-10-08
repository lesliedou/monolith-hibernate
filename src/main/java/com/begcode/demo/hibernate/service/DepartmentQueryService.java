package com.begcode.demo.hibernate.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.domain.*; // for static metamodels
import com.begcode.demo.hibernate.domain.Department;
import com.begcode.demo.hibernate.repository.DepartmentRepository;
import com.begcode.demo.hibernate.service.criteria.DepartmentCriteria;
import com.begcode.demo.hibernate.service.dto.DepartmentDTO;
import com.begcode.demo.hibernate.service.mapper.DepartmentMapper;
import com.begcode.demo.hibernate.util.CriteriaUtil;
import com.begcode.demo.hibernate.util.JpaUtil;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.aggregate.*;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * 用于对数据库中的{@link Department}实体执行复杂查询的Service。
 * 主要输入是一个{@link DepartmentCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link DepartmentDTO}列表{@link List} 或 {@link DepartmentDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class DepartmentQueryService extends QueryService<Department> {

    private final Logger log = LoggerFactory.getLogger(DepartmentQueryService.class);

    protected final DepartmentRepository departmentRepository;

    protected final EntityManager em;

    protected final DepartmentMapper departmentMapper;

    public DepartmentQueryService(DepartmentRepository departmentRepository, EntityManager em, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.em = em;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Return a {@link List} of {@link DepartmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findByCriteria(DepartmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentMapper.toDto(departmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DepartmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findByCriteria(DepartmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.findAll(specification, page).map(departmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.count(specification);
    }

    /**
     * Function to convert {@link DepartmentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Department> createSpecification(DepartmentCriteria criteria) {
        Specification<Department> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Department_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Department_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Department_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Department_.createUserId
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Department_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Department_.code
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Department_.address
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Department_.phoneNum
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Department_.logo
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Department_.contact
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), Department_.id));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), Department_.name));
                }
                if (criteria.getCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCode(), Department_.code));
                }
                if (criteria.getAddress() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getAddress(), Department_.address));
                }
                if (criteria.getPhoneNum() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getPhoneNum(), Department_.phoneNum));
                }
                if (criteria.getLogo() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLogo(), Department_.logo));
                }
                if (criteria.getContact() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getContact(), Department_.contact));
                }
                if (criteria.getCreateUserId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreateUserId(), Department_.createUserId));
                }
                if (criteria.getCreateTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), Department_.createTime));
                }
                if (criteria.getChildrenId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getChildrenId(),
                                root -> root.join(Department_.children, JoinType.LEFT).get(Department_.id)
                            )
                        );
                }
                if (criteria.getChildrenName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getChildrenName(),
                                root -> root.join(Department_.children, JoinType.LEFT).get(Department_.name)
                            )
                        );
                }
                if (criteria.getAuthoritiesId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getAuthoritiesId(),
                                root -> root.join(Department_.authorities, JoinType.LEFT).get(Authority_.id)
                            )
                        );
                }
                if (criteria.getAuthoritiesName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getAuthoritiesName(),
                                root -> root.join(Department_.authorities, JoinType.LEFT).get(Authority_.name)
                            )
                        );
                }
                if (criteria.getParentId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getParentId(),
                                root -> root.join(Department_.parent, JoinType.LEFT).get(Department_.id)
                            )
                        );
                }
                if (criteria.getParentName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getParentName(),
                                root -> root.join(Department_.parent, JoinType.LEFT).get(Department_.name)
                            )
                        );
                }
                if (criteria.getUsersId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(criteria.getUsersId(), root -> root.join(Department_.users, JoinType.LEFT).get(User_.id))
                        );
                }
                if (criteria.getUsersFirstName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getUsersFirstName(),
                                root -> root.join(Department_.users, JoinType.LEFT).get(User_.firstName)
                            )
                        );
                }
            }
        }
        return specification;
    }

    /**
     * Get all the departments for parent is null.
     *
     * @param page the pagination information
     * @return the list of entities
     */
    @Transactional
    public Page<DepartmentDTO> findAllTop(DepartmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.parentId().setSpecified(false);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.findAll(specification, page).map(departmentMapper::toDto);
    }

    /**
     * Get all the departments for parent is parentId.
     *
     * @param parentId the Id of parent
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<DepartmentDTO> findChildrenByParentId(Long parentId) {
        log.debug("Request to get all Departments for parent is parentId");
        return departmentMapper.toDto(departmentRepository.findAllByParentId(parentId));
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, DepartmentCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<Department> root = q.from(Department.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, DepartmentCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Department> root = q.from(Department.class);
        Selection<Long> selection;
        if (distinct != null && distinct) {
            selection = cb.countDistinct(root.get(fieldName)).alias("result");
        } else {
            selection = cb.countDistinct(root.get(fieldName)).alias("result");
        }
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getSingleResult();
    }

    @Transactional
    public boolean updateBySpecifield(String fieldName, Object value, DepartmentCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Department> q = cb.createCriteriaUpdate(Department.class);
        CriteriaQuery<Department> sq = cb.createQuery(Department.class);
        Root<Department> root = q.from(Department.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link DepartmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> getOneByCriteria(DepartmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Department> specification = createSpecification(criteria);
        return departmentRepository.findOne(specification).map(departmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<Department> specification) {
        log.debug("count by specification : {}", specification);
        return departmentRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link DepartmentDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findBySpecification(Specification<Department> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return departmentRepository.findAll(specification, page).map(departmentMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(DepartmentCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<Department> root = q.from(Department.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCode() != null) {
            getAggregateAndGroupBy(criteria.getCode(), "code", selectFields, groupByFields, cb, root);
        }
        if (criteria.getAddress() != null) {
            getAggregateAndGroupBy(criteria.getAddress(), "address", selectFields, groupByFields, cb, root);
        }
        if (criteria.getPhoneNum() != null) {
            getAggregateAndGroupBy(criteria.getPhoneNum(), "phone_num", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLogo() != null) {
            getAggregateAndGroupBy(criteria.getLogo(), "logo", selectFields, groupByFields, cb, root);
        }
        if (criteria.getContact() != null) {
            getAggregateAndGroupBy(criteria.getContact(), "contact", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCreateUserId() != null) {
            getAggregateAndGroupBy(criteria.getCreateUserId(), "create_user_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCreateTime() != null) {
            getAggregateAndGroupBy(criteria.getCreateTime(), "create_time", selectFields, groupByFields, cb, root);
        }
        if (CollectionUtils.isNotEmpty(selectFields)) {
            q.multiselect(selectFields);
            Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
            q.where(criteriaPredicate);
            q.groupBy(groupByFields);
            return em.createQuery(q).getResultList().stream().map(JpaUtil::toMap).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void getAggregateAndGroupBy(
        Filter<?> filter,
        String fieldName,
        List<Selection<?>> selects,
        List<Expression<?>> groupBys,
        CriteriaBuilder cb,
        Root<?> root
    ) {
        if (filter.getAggregate() != null) {
            if (filter.getAggregate() instanceof NumberAggregate) {
                buildAggregate((NumberAggregate) filter.getAggregate(), fieldName, selects, cb, root);
            } else {
                buildAggregate(filter.getAggregate(), fieldName, selects, cb, root);
            }
        }
        if (filter.getGroupBy() != null) {
            if (filter.getGroupBy() instanceof DateTimeGroupBy) {
                buildGroupBy((DateTimeGroupBy) filter.getGroupBy(), fieldName, groupBys, selects, cb, root);
            } else {
                buildGroupBy(filter.getGroupBy(), fieldName, groupBys, selects, cb, root);
            }
        }
    }
}
