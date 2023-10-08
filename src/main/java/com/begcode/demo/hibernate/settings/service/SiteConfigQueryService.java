package com.begcode.demo.hibernate.settings.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.settings.domain.*; // for static metamodels
import com.begcode.demo.hibernate.settings.domain.SiteConfig;
import com.begcode.demo.hibernate.settings.repository.SiteConfigRepository;
import com.begcode.demo.hibernate.settings.service.criteria.SiteConfigCriteria;
import com.begcode.demo.hibernate.settings.service.dto.SiteConfigDTO;
import com.begcode.demo.hibernate.settings.service.mapper.SiteConfigMapper;
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
 * 用于对数据库中的{@link SiteConfig}实体执行复杂查询的Service。
 * 主要输入是一个{@link SiteConfigCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SiteConfigDTO}列表{@link List} 或 {@link SiteConfigDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class SiteConfigQueryService extends QueryService<SiteConfig> {

    private final Logger log = LoggerFactory.getLogger(SiteConfigQueryService.class);

    protected final SiteConfigRepository siteConfigRepository;

    protected final EntityManager em;

    protected final SiteConfigMapper siteConfigMapper;

    public SiteConfigQueryService(SiteConfigRepository siteConfigRepository, EntityManager em, SiteConfigMapper siteConfigMapper) {
        this.siteConfigRepository = siteConfigRepository;
        this.em = em;
        this.siteConfigMapper = siteConfigMapper;
    }

    /**
     * Return a {@link List} of {@link SiteConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SiteConfigDTO> findByCriteria(SiteConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SiteConfig> specification = createSpecification(criteria);
        return siteConfigMapper.toDto(siteConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SiteConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteConfigDTO> findByCriteria(SiteConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SiteConfig> specification = createSpecification(criteria);
        return siteConfigRepository.findAll(specification, page).map(siteConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SiteConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SiteConfig> specification = createSpecification(criteria);
        return siteConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link SiteConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SiteConfig> createSpecification(SiteConfigCriteria criteria) {
        Specification<SiteConfig> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SiteConfig_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SiteConfig_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SiteConfig_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SiteConfig_.sortValue
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SiteConfig_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SiteConfig_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SiteConfig_.categoryName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SiteConfig_.categoryKey
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), SiteConfig_.id));
                }
                if (criteria.getCategoryName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCategoryName(), SiteConfig_.categoryName));
                }
                if (criteria.getCategoryKey() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCategoryKey(), SiteConfig_.categoryKey));
                }
                if (criteria.getDisabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getDisabled(), SiteConfig_.disabled));
                }
                if (criteria.getSortValue() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSortValue(), SiteConfig_.sortValue));
                }
                if (criteria.getBuiltIn() != null) {
                    specification = specification.and(buildSpecification(criteria.getBuiltIn(), SiteConfig_.builtIn));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), SiteConfig_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SiteConfig_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), SiteConfig_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SiteConfig_.lastModifiedDate));
                }
                if (criteria.getItemsId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getItemsId(),
                                root -> root.join(SiteConfig_.items, JoinType.LEFT).get(CommonFieldData_.id)
                            )
                        );
                }
                if (criteria.getItemsName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getItemsName(),
                                root -> root.join(SiteConfig_.items, JoinType.LEFT).get(CommonFieldData_.name)
                            )
                        );
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SiteConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<SiteConfig> root = q.from(SiteConfig.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SiteConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<SiteConfig> root = q.from(SiteConfig.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, SiteConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<SiteConfig> q = cb.createCriteriaUpdate(SiteConfig.class);
        CriteriaQuery<SiteConfig> sq = cb.createQuery(SiteConfig.class);
        Root<SiteConfig> root = q.from(SiteConfig.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link SiteConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteConfigDTO> getOneByCriteria(SiteConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SiteConfig> specification = createSpecification(criteria);
        return siteConfigRepository.findOne(specification).map(siteConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<SiteConfig> specification) {
        log.debug("count by specification : {}", specification);
        return siteConfigRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link SiteConfigDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteConfigDTO> findBySpecification(Specification<SiteConfig> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return siteConfigRepository.findAll(specification, page).map(siteConfigMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SiteConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<SiteConfig> root = q.from(SiteConfig.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCategoryName() != null) {
            getAggregateAndGroupBy(criteria.getCategoryName(), "category_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCategoryKey() != null) {
            getAggregateAndGroupBy(criteria.getCategoryKey(), "category_key", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDisabled() != null) {
            getAggregateAndGroupBy(criteria.getDisabled(), "disabled", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSortValue() != null) {
            getAggregateAndGroupBy(criteria.getSortValue(), "sort_value", selectFields, groupByFields, cb, root);
        }
        if (criteria.getBuiltIn() != null) {
            getAggregateAndGroupBy(criteria.getBuiltIn(), "built_in", selectFields, groupByFields, cb, root);
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
