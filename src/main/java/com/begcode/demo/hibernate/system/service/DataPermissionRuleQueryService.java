package com.begcode.demo.hibernate.system.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.system.domain.*; // for static metamodels
import com.begcode.demo.hibernate.system.domain.DataPermissionRule;
import com.begcode.demo.hibernate.system.repository.DataPermissionRuleRepository;
import com.begcode.demo.hibernate.system.service.criteria.DataPermissionRuleCriteria;
import com.begcode.demo.hibernate.system.service.dto.DataPermissionRuleDTO;
import com.begcode.demo.hibernate.system.service.mapper.DataPermissionRuleMapper;
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
 * 用于对数据库中的{@link DataPermissionRule}实体执行复杂查询的Service。
 * 主要输入是一个{@link DataPermissionRuleCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link DataPermissionRuleDTO}列表{@link List} 或 {@link DataPermissionRuleDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class DataPermissionRuleQueryService extends QueryService<DataPermissionRule> {

    private final Logger log = LoggerFactory.getLogger(DataPermissionRuleQueryService.class);

    protected final DataPermissionRuleRepository dataPermissionRuleRepository;

    protected final EntityManager em;

    protected final DataPermissionRuleMapper dataPermissionRuleMapper;

    public DataPermissionRuleQueryService(
        DataPermissionRuleRepository dataPermissionRuleRepository,
        EntityManager em,
        DataPermissionRuleMapper dataPermissionRuleMapper
    ) {
        this.dataPermissionRuleRepository = dataPermissionRuleRepository;
        this.em = em;
        this.dataPermissionRuleMapper = dataPermissionRuleMapper;
    }

    /**
     * Return a {@link List} of {@link DataPermissionRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataPermissionRuleDTO> findByCriteria(DataPermissionRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataPermissionRule> specification = createSpecification(criteria);
        return dataPermissionRuleMapper.toDto(dataPermissionRuleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DataPermissionRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataPermissionRuleDTO> findByCriteria(DataPermissionRuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataPermissionRule> specification = createSpecification(criteria);
        return dataPermissionRuleRepository.findAll(specification, page).map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataPermissionRuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataPermissionRule> specification = createSpecification(criteria);
        return dataPermissionRuleRepository.count(specification);
    }

    /**
     * Function to convert {@link DataPermissionRuleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DataPermissionRule> createSpecification(DataPermissionRuleCriteria criteria) {
        Specification<DataPermissionRule> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DataPermissionRule_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                DataPermissionRule_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                DataPermissionRule_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                DataPermissionRule_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                DataPermissionRule_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                DataPermissionRule_.permissionId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                DataPermissionRule_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                DataPermissionRule_.column
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                DataPermissionRule_.conditions
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                DataPermissionRule_.value
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildRangeSpecification(criteria.getId(), DataPermissionRule_.id)
                        );
                }
                if (criteria.getPermissionId() != null) {
                    specification =
                        specification.and(buildStringSpecification(criteria.getPermissionId(), DataPermissionRule_.permissionId));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), DataPermissionRule_.name));
                }
                if (criteria.getColumn() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getColumn(), DataPermissionRule_.column));
                }
                if (criteria.getConditions() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getConditions(), DataPermissionRule_.conditions));
                }
                if (criteria.getValue() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getValue(), DataPermissionRule_.value));
                }
                if (criteria.getDisabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getDisabled(), DataPermissionRule_.disabled));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), DataPermissionRule_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), DataPermissionRule_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), DataPermissionRule_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), DataPermissionRule_.lastModifiedDate));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, DataPermissionRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<DataPermissionRule> root = q.from(DataPermissionRule.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, DataPermissionRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<DataPermissionRule> root = q.from(DataPermissionRule.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, DataPermissionRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<DataPermissionRule> q = cb.createCriteriaUpdate(DataPermissionRule.class);
        CriteriaQuery<DataPermissionRule> sq = cb.createQuery(DataPermissionRule.class);
        Root<DataPermissionRule> root = q.from(DataPermissionRule.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link DataPermissionRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<DataPermissionRuleDTO> getOneByCriteria(DataPermissionRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataPermissionRule> specification = createSpecification(criteria);
        return dataPermissionRuleRepository.findOne(specification).map(dataPermissionRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<DataPermissionRule> specification) {
        log.debug("count by specification : {}", specification);
        return dataPermissionRuleRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link DataPermissionRuleDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataPermissionRuleDTO> findBySpecification(Specification<DataPermissionRule> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return dataPermissionRuleRepository.findAll(specification, page).map(dataPermissionRuleMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(DataPermissionRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<DataPermissionRule> root = q.from(DataPermissionRule.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getPermissionId() != null) {
            getAggregateAndGroupBy(criteria.getPermissionId(), "permission_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getColumn() != null) {
            getAggregateAndGroupBy(criteria.getColumn(), "jhi_column", selectFields, groupByFields, cb, root);
        }
        if (criteria.getConditions() != null) {
            getAggregateAndGroupBy(criteria.getConditions(), "conditions", selectFields, groupByFields, cb, root);
        }
        if (criteria.getValue() != null) {
            getAggregateAndGroupBy(criteria.getValue(), "value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDisabled() != null) {
            getAggregateAndGroupBy(criteria.getDisabled(), "disabled", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCreatedBy() != null) {
            getAggregateAndGroupBy(criteria.getCreatedBy(), "created_by", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCreatedDate() != null) {
            getAggregateAndGroupBy(criteria.getCreatedDate(), "created_date", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLastModifiedBy() != null) {
            getAggregateAndGroupBy(criteria.getLastModifiedBy(), "last_modified_by", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLastModifiedDate() != null) {
            getAggregateAndGroupBy(criteria.getLastModifiedDate(), "last_modified_date", selectFields, groupByFields, cb, root);
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
