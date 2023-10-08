package com.begcode.demo.hibernate.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.domain.*; // for static metamodels
import com.begcode.demo.hibernate.domain.Authority;
import com.begcode.demo.hibernate.repository.AuthorityRepository;
import com.begcode.demo.hibernate.service.criteria.AuthorityCriteria;
import com.begcode.demo.hibernate.service.dto.AuthorityDTO;
import com.begcode.demo.hibernate.service.mapper.AuthorityMapper;
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
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * 用于对数据库中的{@link Authority}实体执行复杂查询的Service。
 * 主要输入是一个{@link AuthorityCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link AuthorityDTO}列表{@link List} 或 {@link AuthorityDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class AuthorityQueryService extends QueryService<Authority> {

    private final Logger log = LoggerFactory.getLogger(AuthorityQueryService.class);

    protected final AuthorityRepository authorityRepository;

    protected final EntityManager em;

    protected final AuthorityMapper authorityMapper;

    public AuthorityQueryService(AuthorityRepository authorityRepository, EntityManager em, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.em = em;
        this.authorityMapper = authorityMapper;
    }

    /**
     * Return a {@link List} of {@link AuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuthorityDTO> findByCriteria(AuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Authority> specification = createSpecification(criteria);
        return authorityMapper.toDto(authorityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthorityDTO> findByCriteria(AuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Authority> specification = createSpecification(criteria);
        return authorityRepository.findAll(specification, page).map(authorityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuthorityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Authority> specification = createSpecification(criteria);
        return authorityRepository.count(specification);
    }

    /**
     * Function to convert {@link AuthorityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Authority> createSpecification(AuthorityCriteria criteria) {
        Specification<Authority> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Authority_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Authority_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Authority_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Authority_.order
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), Authority_.name)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), Authority_.code)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), Authority_.info)
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), Authority_.id));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), Authority_.name));
                }
                if (criteria.getCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCode(), Authority_.code));
                }
                if (criteria.getInfo() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getInfo(), Authority_.info));
                }
                if (criteria.getOrder() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getOrder(), Authority_.order));
                }
                if (criteria.getDisplay() != null) {
                    specification = specification.and(buildSpecification(criteria.getDisplay(), Authority_.display));
                }
                if (criteria.getChildrenId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getChildrenId(),
                                root -> root.join(Authority_.children, JoinType.LEFT).get(Authority_.id)
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
                                root -> root.join(Authority_.children, JoinType.LEFT).get(Authority_.name)
                            )
                        );
                }
                if (criteria.getViewPermissionsId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getViewPermissionsId(),
                                root -> root.join(Authority_.viewPermissions, JoinType.LEFT).get(ViewPermission_.id)
                            )
                        );
                }
                if (criteria.getViewPermissionsText() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getViewPermissionsText(),
                                root -> root.join(Authority_.viewPermissions, JoinType.LEFT).get(ViewPermission_.text)
                            )
                        );
                }
                if (criteria.getApiPermissionsId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getApiPermissionsId(),
                                root -> root.join(Authority_.apiPermissions, JoinType.LEFT).get(ApiPermission_.id)
                            )
                        );
                }
                if (criteria.getApiPermissionsName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getApiPermissionsName(),
                                root -> root.join(Authority_.apiPermissions, JoinType.LEFT).get(ApiPermission_.name)
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
                                root -> root.join(Authority_.parent, JoinType.LEFT).get(Authority_.id)
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
                                root -> root.join(Authority_.parent, JoinType.LEFT).get(Authority_.name)
                            )
                        );
                }
                if (criteria.getUsersId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(criteria.getUsersId(), root -> root.join(Authority_.users, JoinType.LEFT).get(User_.id))
                        );
                }
                if (criteria.getUsersFirstName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getUsersFirstName(),
                                root -> root.join(Authority_.users, JoinType.LEFT).get(User_.firstName)
                            )
                        );
                }
            }
        }
        return specification;
    }

    /**
     * Get all the authorities for parent is null.
     *
     * @param page the pagination information
     * @return the list of entities
     */
    @Transactional
    public Page<AuthorityDTO> findAllTop(AuthorityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.parentId().setSpecified(false);
        final Specification<Authority> specification = createSpecification(criteria);
        return authorityRepository.findAll(specification, page).map(authorityMapper::toDto);
    }

    /**
     * Get all the authorities for parent is parentId.
     *
     * @param parentId the Id of parent
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AuthorityDTO> findChildrenByParentId(Long parentId) {
        log.debug("Request to get all Authorities for parent is parentId");
        return authorityMapper.toDto(authorityRepository.findAllByParentId(parentId));
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, AuthorityCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<Authority> root = q.from(Authority.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, AuthorityCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Authority> root = q.from(Authority.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, AuthorityCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Authority> q = cb.createCriteriaUpdate(Authority.class);
        CriteriaQuery<Authority> sq = cb.createQuery(Authority.class);
        Root<Authority> root = q.from(Authority.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link AuthorityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuthorityDTO> getOneByCriteria(AuthorityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Authority> specification = createSpecification(criteria);
        return authorityRepository.findOne(specification).map(authorityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<Authority> specification) {
        log.debug("count by specification : {}", specification);
        return authorityRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link AuthorityDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuthorityDTO> findBySpecification(Specification<Authority> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return authorityRepository.findAll(specification, page).map(authorityMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(AuthorityCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<Authority> root = q.from(Authority.class);
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
        if (criteria.getInfo() != null) {
            getAggregateAndGroupBy(criteria.getInfo(), "info", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOrder() != null) {
            getAggregateAndGroupBy(criteria.getOrder(), "jhi_order", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDisplay() != null) {
            getAggregateAndGroupBy(criteria.getDisplay(), "display", selectFields, groupByFields, cb, root);
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
