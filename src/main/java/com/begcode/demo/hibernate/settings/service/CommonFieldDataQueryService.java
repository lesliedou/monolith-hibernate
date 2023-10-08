package com.begcode.demo.hibernate.settings.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.settings.domain.*; // for static metamodels
import com.begcode.demo.hibernate.settings.domain.CommonFieldData;
import com.begcode.demo.hibernate.settings.repository.CommonFieldDataRepository;
import com.begcode.demo.hibernate.settings.service.criteria.CommonFieldDataCriteria;
import com.begcode.demo.hibernate.settings.service.dto.CommonFieldDataDTO;
import com.begcode.demo.hibernate.settings.service.mapper.CommonFieldDataMapper;
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
 * 用于对数据库中的{@link CommonFieldData}实体执行复杂查询的Service。
 * 主要输入是一个{@link CommonFieldDataCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link CommonFieldDataDTO}列表{@link List} 或 {@link CommonFieldDataDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class CommonFieldDataQueryService extends QueryService<CommonFieldData> {

    private final Logger log = LoggerFactory.getLogger(CommonFieldDataQueryService.class);

    protected final CommonFieldDataRepository commonFieldDataRepository;

    protected final EntityManager em;

    protected final CommonFieldDataMapper commonFieldDataMapper;

    public CommonFieldDataQueryService(
        CommonFieldDataRepository commonFieldDataRepository,
        EntityManager em,
        CommonFieldDataMapper commonFieldDataMapper
    ) {
        this.commonFieldDataRepository = commonFieldDataRepository;
        this.em = em;
        this.commonFieldDataMapper = commonFieldDataMapper;
    }

    /**
     * Return a {@link List} of {@link CommonFieldDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommonFieldDataDTO> findByCriteria(CommonFieldDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommonFieldData> specification = createSpecification(criteria);
        return commonFieldDataMapper.toDto(commonFieldDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommonFieldDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonFieldDataDTO> findByCriteria(CommonFieldDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommonFieldData> specification = createSpecification(criteria);
        return commonFieldDataRepository.findAll(specification, page).map(commonFieldDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommonFieldDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommonFieldData> specification = createSpecification(criteria);
        return commonFieldDataRepository.count(specification);
    }

    /**
     * Function to convert {@link CommonFieldDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommonFieldData> createSpecification(CommonFieldDataCriteria criteria) {
        Specification<CommonFieldData> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CommonFieldData_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                CommonFieldData_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                CommonFieldData_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                CommonFieldData_.sortValue
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                CommonFieldData_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                CommonFieldData_.value
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                CommonFieldData_.label
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                CommonFieldData_.remark
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                CommonFieldData_.ownerEntityName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                CommonFieldData_.ownerEntityId
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildRangeSpecification(criteria.getId(), CommonFieldData_.id)
                        );
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), CommonFieldData_.name));
                }
                if (criteria.getValue() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getValue(), CommonFieldData_.value));
                }
                if (criteria.getLabel() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLabel(), CommonFieldData_.label));
                }
                if (criteria.getValueType() != null) {
                    specification = specification.and(buildSpecification(criteria.getValueType(), CommonFieldData_.valueType));
                }
                if (criteria.getRemark() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRemark(), CommonFieldData_.remark));
                }
                if (criteria.getSortValue() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSortValue(), CommonFieldData_.sortValue));
                }
                if (criteria.getDisabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getDisabled(), CommonFieldData_.disabled));
                }
                if (criteria.getOwnerEntityName() != null) {
                    specification =
                        specification.and(buildStringSpecification(criteria.getOwnerEntityName(), CommonFieldData_.ownerEntityName));
                }
                if (criteria.getOwnerEntityId() != null) {
                    specification =
                        specification.and(buildStringSpecification(criteria.getOwnerEntityId(), CommonFieldData_.ownerEntityId));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, CommonFieldDataCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<CommonFieldData> root = q.from(CommonFieldData.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, CommonFieldDataCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<CommonFieldData> root = q.from(CommonFieldData.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, CommonFieldDataCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<CommonFieldData> q = cb.createCriteriaUpdate(CommonFieldData.class);
        CriteriaQuery<CommonFieldData> sq = cb.createQuery(CommonFieldData.class);
        Root<CommonFieldData> root = q.from(CommonFieldData.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link CommonFieldDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommonFieldDataDTO> getOneByCriteria(CommonFieldDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommonFieldData> specification = createSpecification(criteria);
        return commonFieldDataRepository.findOne(specification).map(commonFieldDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<CommonFieldData> specification) {
        log.debug("count by specification : {}", specification);
        return commonFieldDataRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link CommonFieldDataDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonFieldDataDTO> findBySpecification(Specification<CommonFieldData> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return commonFieldDataRepository.findAll(specification, page).map(commonFieldDataMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(CommonFieldDataCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<CommonFieldData> root = q.from(CommonFieldData.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getValue() != null) {
            getAggregateAndGroupBy(criteria.getValue(), "value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLabel() != null) {
            getAggregateAndGroupBy(criteria.getLabel(), "label", selectFields, groupByFields, cb, root);
        }
        if (criteria.getValueType() != null) {
            getAggregateAndGroupBy(criteria.getValueType(), "value_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getRemark() != null) {
            getAggregateAndGroupBy(criteria.getRemark(), "remark", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSortValue() != null) {
            getAggregateAndGroupBy(criteria.getSortValue(), "sort_value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDisabled() != null) {
            getAggregateAndGroupBy(criteria.getDisabled(), "disabled", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOwnerEntityName() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityName(), "owner_entity_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOwnerEntityId() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityId(), "owner_entity_id", selectFields, groupByFields, cb, root);
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
