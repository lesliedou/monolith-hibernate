package com.begcode.demo.hibernate.settings.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.settings.domain.*; // for static metamodels
import com.begcode.demo.hibernate.settings.domain.FillRuleItem;
import com.begcode.demo.hibernate.settings.repository.FillRuleItemRepository;
import com.begcode.demo.hibernate.settings.service.criteria.FillRuleItemCriteria;
import com.begcode.demo.hibernate.settings.service.dto.FillRuleItemDTO;
import com.begcode.demo.hibernate.settings.service.mapper.FillRuleItemMapper;
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
 * 用于对数据库中的{@link FillRuleItem}实体执行复杂查询的Service。
 * 主要输入是一个{@link FillRuleItemCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link FillRuleItemDTO}列表{@link List} 或 {@link FillRuleItemDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class FillRuleItemQueryService extends QueryService<FillRuleItem> {

    private final Logger log = LoggerFactory.getLogger(FillRuleItemQueryService.class);

    protected final FillRuleItemRepository fillRuleItemRepository;

    protected final EntityManager em;

    protected final FillRuleItemMapper fillRuleItemMapper;

    public FillRuleItemQueryService(
        FillRuleItemRepository fillRuleItemRepository,
        EntityManager em,
        FillRuleItemMapper fillRuleItemMapper
    ) {
        this.fillRuleItemRepository = fillRuleItemRepository;
        this.em = em;
        this.fillRuleItemMapper = fillRuleItemMapper;
    }

    /**
     * Return a {@link List} of {@link FillRuleItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FillRuleItemDTO> findByCriteria(FillRuleItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FillRuleItem> specification = createSpecification(criteria);
        return fillRuleItemMapper.toDto(fillRuleItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FillRuleItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FillRuleItemDTO> findByCriteria(FillRuleItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FillRuleItem> specification = createSpecification(criteria);
        return fillRuleItemRepository.findAll(specification, page).map(fillRuleItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FillRuleItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FillRuleItem> specification = createSpecification(criteria);
        return fillRuleItemRepository.count(specification);
    }

    /**
     * Function to convert {@link FillRuleItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FillRuleItem> createSpecification(FillRuleItemCriteria criteria) {
        Specification<FillRuleItem> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FillRuleItem_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                FillRuleItem_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                FillRuleItem_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                FillRuleItem_.sortValue
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                FillRuleItem_.seqLength
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                FillRuleItem_.seqIncrement
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                FillRuleItem_.seqStartValue
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                FillRuleItem_.fieldParamValue
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                FillRuleItem_.datePattern
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), FillRuleItem_.id));
                }
                if (criteria.getSortValue() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSortValue(), FillRuleItem_.sortValue));
                }
                if (criteria.getFieldParamType() != null) {
                    specification = specification.and(buildSpecification(criteria.getFieldParamType(), FillRuleItem_.fieldParamType));
                }
                if (criteria.getFieldParamValue() != null) {
                    specification =
                        specification.and(buildStringSpecification(criteria.getFieldParamValue(), FillRuleItem_.fieldParamValue));
                }
                if (criteria.getDatePattern() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDatePattern(), FillRuleItem_.datePattern));
                }
                if (criteria.getSeqLength() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSeqLength(), FillRuleItem_.seqLength));
                }
                if (criteria.getSeqIncrement() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSeqIncrement(), FillRuleItem_.seqIncrement));
                }
                if (criteria.getSeqStartValue() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSeqStartValue(), FillRuleItem_.seqStartValue));
                }
                if (criteria.getFillRuleId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getFillRuleId(),
                                root -> root.join(FillRuleItem_.fillRule, JoinType.LEFT).get(SysFillRule_.id)
                            )
                        );
                }
                if (criteria.getFillRuleName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getFillRuleName(),
                                root -> root.join(FillRuleItem_.fillRule, JoinType.LEFT).get(SysFillRule_.name)
                            )
                        );
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, FillRuleItemCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<FillRuleItem> root = q.from(FillRuleItem.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, FillRuleItemCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<FillRuleItem> root = q.from(FillRuleItem.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, FillRuleItemCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<FillRuleItem> q = cb.createCriteriaUpdate(FillRuleItem.class);
        CriteriaQuery<FillRuleItem> sq = cb.createQuery(FillRuleItem.class);
        Root<FillRuleItem> root = q.from(FillRuleItem.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link FillRuleItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<FillRuleItemDTO> getOneByCriteria(FillRuleItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FillRuleItem> specification = createSpecification(criteria);
        return fillRuleItemRepository.findOne(specification).map(fillRuleItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<FillRuleItem> specification) {
        log.debug("count by specification : {}", specification);
        return fillRuleItemRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link FillRuleItemDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FillRuleItemDTO> findBySpecification(Specification<FillRuleItem> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return fillRuleItemRepository.findAll(specification, page).map(fillRuleItemMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(FillRuleItemCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<FillRuleItem> root = q.from(FillRuleItem.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSortValue() != null) {
            getAggregateAndGroupBy(criteria.getSortValue(), "sort_value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getFieldParamType() != null) {
            getAggregateAndGroupBy(criteria.getFieldParamType(), "field_param_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getFieldParamValue() != null) {
            getAggregateAndGroupBy(criteria.getFieldParamValue(), "field_param_value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDatePattern() != null) {
            getAggregateAndGroupBy(criteria.getDatePattern(), "date_pattern", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSeqLength() != null) {
            getAggregateAndGroupBy(criteria.getSeqLength(), "seq_length", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSeqIncrement() != null) {
            getAggregateAndGroupBy(criteria.getSeqIncrement(), "seq_increment", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSeqStartValue() != null) {
            getAggregateAndGroupBy(criteria.getSeqStartValue(), "seq_start_value", selectFields, groupByFields, cb, root);
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
