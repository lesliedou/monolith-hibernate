package com.begcode.demo.hibernate.settings.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.settings.domain.*; // for static metamodels
import com.begcode.demo.hibernate.settings.domain.Dictionary;
import com.begcode.demo.hibernate.settings.repository.DictionaryRepository;
import com.begcode.demo.hibernate.settings.service.criteria.DictionaryCriteria;
import com.begcode.demo.hibernate.settings.service.dto.DictionaryDTO;
import com.begcode.demo.hibernate.settings.service.mapper.DictionaryMapper;
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
 * 用于对数据库中的{@link Dictionary}实体执行复杂查询的Service。
 * 主要输入是一个{@link DictionaryCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link DictionaryDTO}列表{@link List} 或 {@link DictionaryDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class DictionaryQueryService extends QueryService<Dictionary> {

    private final Logger log = LoggerFactory.getLogger(DictionaryQueryService.class);

    protected final DictionaryRepository dictionaryRepository;

    protected final EntityManager em;

    protected final DictionaryMapper dictionaryMapper;

    public DictionaryQueryService(DictionaryRepository dictionaryRepository, EntityManager em, DictionaryMapper dictionaryMapper) {
        this.dictionaryRepository = dictionaryRepository;
        this.em = em;
        this.dictionaryMapper = dictionaryMapper;
    }

    /**
     * Return a {@link List} of {@link DictionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DictionaryDTO> findByCriteria(DictionaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dictionary> specification = createSpecification(criteria);
        return dictionaryMapper.toDto(dictionaryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DictionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DictionaryDTO> findByCriteria(DictionaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dictionary> specification = createSpecification(criteria);
        return dictionaryRepository.findAll(specification, page).map(dictionaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DictionaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dictionary> specification = createSpecification(criteria);
        return dictionaryRepository.count(specification);
    }

    /**
     * Function to convert {@link DictionaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Dictionary> createSpecification(DictionaryCriteria criteria) {
        Specification<Dictionary> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Dictionary_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Dictionary_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Dictionary_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Dictionary_.sortValue
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Dictionary_.dictName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Dictionary_.dictKey
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), Dictionary_.id));
                }
                if (criteria.getDictName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDictName(), Dictionary_.dictName));
                }
                if (criteria.getDictKey() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDictKey(), Dictionary_.dictKey));
                }
                if (criteria.getDisabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getDisabled(), Dictionary_.disabled));
                }
                if (criteria.getSortValue() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSortValue(), Dictionary_.sortValue));
                }
                if (criteria.getBuiltIn() != null) {
                    specification = specification.and(buildSpecification(criteria.getBuiltIn(), Dictionary_.builtIn));
                }
                if (criteria.getSyncEnum() != null) {
                    specification = specification.and(buildSpecification(criteria.getSyncEnum(), Dictionary_.syncEnum));
                }
                if (criteria.getItemsId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getItemsId(),
                                root -> root.join(Dictionary_.items, JoinType.LEFT).get(CommonFieldData_.id)
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
                                root -> root.join(Dictionary_.items, JoinType.LEFT).get(CommonFieldData_.name)
                            )
                        );
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, DictionaryCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<Dictionary> root = q.from(Dictionary.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, DictionaryCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Dictionary> root = q.from(Dictionary.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, DictionaryCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Dictionary> q = cb.createCriteriaUpdate(Dictionary.class);
        CriteriaQuery<Dictionary> sq = cb.createQuery(Dictionary.class);
        Root<Dictionary> root = q.from(Dictionary.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link DictionaryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<DictionaryDTO> getOneByCriteria(DictionaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dictionary> specification = createSpecification(criteria);
        return dictionaryRepository.findOne(specification).map(dictionaryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<Dictionary> specification) {
        log.debug("count by specification : {}", specification);
        return dictionaryRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link DictionaryDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DictionaryDTO> findBySpecification(Specification<Dictionary> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return dictionaryRepository.findAll(specification, page).map(dictionaryMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(DictionaryCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<Dictionary> root = q.from(Dictionary.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDictName() != null) {
            getAggregateAndGroupBy(criteria.getDictName(), "dict_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDictKey() != null) {
            getAggregateAndGroupBy(criteria.getDictKey(), "dict_key", selectFields, groupByFields, cb, root);
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
        if (criteria.getSyncEnum() != null) {
            getAggregateAndGroupBy(criteria.getSyncEnum(), "sync_enum", selectFields, groupByFields, cb, root);
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
