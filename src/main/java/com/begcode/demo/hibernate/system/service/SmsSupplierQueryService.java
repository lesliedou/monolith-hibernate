package com.begcode.demo.hibernate.system.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.system.domain.*; // for static metamodels
import com.begcode.demo.hibernate.system.domain.SmsSupplier;
import com.begcode.demo.hibernate.system.repository.SmsSupplierRepository;
import com.begcode.demo.hibernate.system.service.criteria.SmsSupplierCriteria;
import com.begcode.demo.hibernate.system.service.dto.SmsSupplierDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsSupplierMapper;
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
 * 用于对数据库中的{@link SmsSupplier}实体执行复杂查询的Service。
 * 主要输入是一个{@link SmsSupplierCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SmsSupplierDTO}列表{@link List} 或 {@link SmsSupplierDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class SmsSupplierQueryService extends QueryService<SmsSupplier> {

    private final Logger log = LoggerFactory.getLogger(SmsSupplierQueryService.class);

    protected final SmsSupplierRepository smsSupplierRepository;

    protected final EntityManager em;

    protected final SmsSupplierMapper smsSupplierMapper;

    public SmsSupplierQueryService(SmsSupplierRepository smsSupplierRepository, EntityManager em, SmsSupplierMapper smsSupplierMapper) {
        this.smsSupplierRepository = smsSupplierRepository;
        this.em = em;
        this.smsSupplierMapper = smsSupplierMapper;
    }

    /**
     * Return a {@link List} of {@link SmsSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SmsSupplierDTO> findByCriteria(SmsSupplierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsSupplier> specification = createSpecification(criteria);
        return smsSupplierMapper.toDto(smsSupplierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SmsSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsSupplierDTO> findByCriteria(SmsSupplierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SmsSupplier> specification = createSpecification(criteria);
        return smsSupplierRepository.findAll(specification, page).map(smsSupplierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SmsSupplierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SmsSupplier> specification = createSpecification(criteria);
        return smsSupplierRepository.count(specification);
    }

    /**
     * Function to convert {@link SmsSupplierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SmsSupplier> createSpecification(SmsSupplierCriteria criteria) {
        Specification<SmsSupplier> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SmsSupplier_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsSupplier_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsSupplier_.id
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsSupplier_.configData
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsSupplier_.signName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsSupplier_.remark
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), SmsSupplier_.id));
                }
                if (criteria.getProvider() != null) {
                    specification = specification.and(buildSpecification(criteria.getProvider(), SmsSupplier_.provider));
                }
                if (criteria.getConfigData() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getConfigData(), SmsSupplier_.configData));
                }
                if (criteria.getSignName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getSignName(), SmsSupplier_.signName));
                }
                if (criteria.getRemark() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRemark(), SmsSupplier_.remark));
                }
                if (criteria.getEnabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getEnabled(), SmsSupplier_.enabled));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SmsSupplierCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<SmsSupplier> root = q.from(SmsSupplier.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SmsSupplierCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<SmsSupplier> root = q.from(SmsSupplier.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, SmsSupplierCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<SmsSupplier> q = cb.createCriteriaUpdate(SmsSupplier.class);
        CriteriaQuery<SmsSupplier> sq = cb.createQuery(SmsSupplier.class);
        Root<SmsSupplier> root = q.from(SmsSupplier.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link SmsSupplierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsSupplierDTO> getOneByCriteria(SmsSupplierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsSupplier> specification = createSpecification(criteria);
        return smsSupplierRepository.findOne(specification).map(smsSupplierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<SmsSupplier> specification) {
        log.debug("count by specification : {}", specification);
        return smsSupplierRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link SmsSupplierDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsSupplierDTO> findBySpecification(Specification<SmsSupplier> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return smsSupplierRepository.findAll(specification, page).map(smsSupplierMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SmsSupplierCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<SmsSupplier> root = q.from(SmsSupplier.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getProvider() != null) {
            getAggregateAndGroupBy(criteria.getProvider(), "provider", selectFields, groupByFields, cb, root);
        }
        if (criteria.getConfigData() != null) {
            getAggregateAndGroupBy(criteria.getConfigData(), "config_data", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSignName() != null) {
            getAggregateAndGroupBy(criteria.getSignName(), "sign_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getRemark() != null) {
            getAggregateAndGroupBy(criteria.getRemark(), "remark", selectFields, groupByFields, cb, root);
        }
        if (criteria.getEnabled() != null) {
            getAggregateAndGroupBy(criteria.getEnabled(), "enabled", selectFields, groupByFields, cb, root);
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
