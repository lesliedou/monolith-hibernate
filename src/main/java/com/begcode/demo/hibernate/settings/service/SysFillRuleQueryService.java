package com.begcode.demo.hibernate.settings.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.settings.domain.*; // for static metamodels
import com.begcode.demo.hibernate.settings.domain.SysFillRule;
import com.begcode.demo.hibernate.settings.repository.SysFillRuleRepository;
import com.begcode.demo.hibernate.settings.service.criteria.SysFillRuleCriteria;
import com.begcode.demo.hibernate.settings.service.dto.SysFillRuleDTO;
import com.begcode.demo.hibernate.settings.service.mapper.SysFillRuleMapper;
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
 * 用于对数据库中的{@link SysFillRule}实体执行复杂查询的Service。
 * 主要输入是一个{@link SysFillRuleCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SysFillRuleDTO}列表{@link List} 或 {@link SysFillRuleDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class SysFillRuleQueryService extends QueryService<SysFillRule> {

    private final Logger log = LoggerFactory.getLogger(SysFillRuleQueryService.class);

    protected final SysFillRuleRepository sysFillRuleRepository;

    protected final EntityManager em;

    protected final SysFillRuleMapper sysFillRuleMapper;

    public SysFillRuleQueryService(SysFillRuleRepository sysFillRuleRepository, EntityManager em, SysFillRuleMapper sysFillRuleMapper) {
        this.sysFillRuleRepository = sysFillRuleRepository;
        this.em = em;
        this.sysFillRuleMapper = sysFillRuleMapper;
    }

    /**
     * Return a {@link List} of {@link SysFillRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysFillRuleDTO> findByCriteria(SysFillRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysFillRule> specification = createSpecification(criteria);
        return sysFillRuleMapper.toDto(sysFillRuleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysFillRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysFillRuleDTO> findByCriteria(SysFillRuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysFillRule> specification = createSpecification(criteria);
        return sysFillRuleRepository.findAll(specification, page).map(sysFillRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysFillRuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysFillRule> specification = createSpecification(criteria);
        return sysFillRuleRepository.count(specification);
    }

    /**
     * Function to convert {@link SysFillRuleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysFillRule> createSpecification(SysFillRuleCriteria criteria) {
        Specification<SysFillRule> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysFillRule_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SysFillRule_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SysFillRule_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SysFillRule_.seqValue
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysFillRule_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysFillRule_.code
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysFillRule_.desc
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysFillRule_.fillValue
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysFillRule_.implClass
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysFillRule_.params
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), SysFillRule_.id));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), SysFillRule_.name));
                }
                if (criteria.getCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCode(), SysFillRule_.code));
                }
                if (criteria.getDesc() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDesc(), SysFillRule_.desc));
                }
                if (criteria.getEnabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getEnabled(), SysFillRule_.enabled));
                }
                if (criteria.getResetFrequency() != null) {
                    specification = specification.and(buildSpecification(criteria.getResetFrequency(), SysFillRule_.resetFrequency));
                }
                if (criteria.getSeqValue() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSeqValue(), SysFillRule_.seqValue));
                }
                if (criteria.getFillValue() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFillValue(), SysFillRule_.fillValue));
                }
                if (criteria.getImplClass() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getImplClass(), SysFillRule_.implClass));
                }
                if (criteria.getParams() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getParams(), SysFillRule_.params));
                }
                if (criteria.getResetStartTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getResetStartTime(), SysFillRule_.resetStartTime));
                }
                if (criteria.getResetEndTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getResetEndTime(), SysFillRule_.resetEndTime));
                }
                if (criteria.getResetTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getResetTime(), SysFillRule_.resetTime));
                }
                if (criteria.getRuleItemsId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getRuleItemsId(),
                                root -> root.join(SysFillRule_.ruleItems, JoinType.LEFT).get(FillRuleItem_.id)
                            )
                        );
                }
                if (criteria.getRuleItemsDatePattern() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getRuleItemsDatePattern(),
                                root -> root.join(SysFillRule_.ruleItems, JoinType.LEFT).get(FillRuleItem_.datePattern)
                            )
                        );
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SysFillRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<SysFillRule> root = q.from(SysFillRule.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SysFillRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<SysFillRule> root = q.from(SysFillRule.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, SysFillRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<SysFillRule> q = cb.createCriteriaUpdate(SysFillRule.class);
        CriteriaQuery<SysFillRule> sq = cb.createQuery(SysFillRule.class);
        Root<SysFillRule> root = q.from(SysFillRule.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link SysFillRuleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysFillRuleDTO> getOneByCriteria(SysFillRuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysFillRule> specification = createSpecification(criteria);
        return sysFillRuleRepository.findOne(specification).map(sysFillRuleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<SysFillRule> specification) {
        log.debug("count by specification : {}", specification);
        return sysFillRuleRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link SysFillRuleDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysFillRuleDTO> findBySpecification(Specification<SysFillRule> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return sysFillRuleRepository.findAll(specification, page).map(sysFillRuleMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SysFillRuleCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<SysFillRule> root = q.from(SysFillRule.class);
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
        if (criteria.getDesc() != null) {
            getAggregateAndGroupBy(criteria.getDesc(), "jhi_desc", selectFields, groupByFields, cb, root);
        }
        if (criteria.getEnabled() != null) {
            getAggregateAndGroupBy(criteria.getEnabled(), "enabled", selectFields, groupByFields, cb, root);
        }
        if (criteria.getResetFrequency() != null) {
            getAggregateAndGroupBy(criteria.getResetFrequency(), "reset_frequency", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSeqValue() != null) {
            getAggregateAndGroupBy(criteria.getSeqValue(), "seq_value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getFillValue() != null) {
            getAggregateAndGroupBy(criteria.getFillValue(), "fill_value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getImplClass() != null) {
            getAggregateAndGroupBy(criteria.getImplClass(), "impl_class", selectFields, groupByFields, cb, root);
        }
        if (criteria.getParams() != null) {
            getAggregateAndGroupBy(criteria.getParams(), "params", selectFields, groupByFields, cb, root);
        }
        if (criteria.getResetStartTime() != null) {
            getAggregateAndGroupBy(criteria.getResetStartTime(), "reset_start_time", selectFields, groupByFields, cb, root);
        }
        if (criteria.getResetEndTime() != null) {
            getAggregateAndGroupBy(criteria.getResetEndTime(), "reset_end_time", selectFields, groupByFields, cb, root);
        }
        if (criteria.getResetTime() != null) {
            getAggregateAndGroupBy(criteria.getResetTime(), "reset_time", selectFields, groupByFields, cb, root);
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
