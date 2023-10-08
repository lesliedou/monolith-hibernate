package com.begcode.demo.hibernate.system.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.system.domain.*; // for static metamodels
import com.begcode.demo.hibernate.system.domain.SmsConfig;
import com.begcode.demo.hibernate.system.repository.SmsConfigRepository;
import com.begcode.demo.hibernate.system.service.criteria.SmsConfigCriteria;
import com.begcode.demo.hibernate.system.service.dto.SmsConfigDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsConfigMapper;
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
 * 用于对数据库中的{@link SmsConfig}实体执行复杂查询的Service。
 * 主要输入是一个{@link SmsConfigCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SmsConfigDTO}列表{@link List} 或 {@link SmsConfigDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class SmsConfigQueryService extends QueryService<SmsConfig> {

    private final Logger log = LoggerFactory.getLogger(SmsConfigQueryService.class);

    protected final SmsConfigRepository smsConfigRepository;

    protected final EntityManager em;

    protected final SmsConfigMapper smsConfigMapper;

    public SmsConfigQueryService(SmsConfigRepository smsConfigRepository, EntityManager em, SmsConfigMapper smsConfigMapper) {
        this.smsConfigRepository = smsConfigRepository;
        this.em = em;
        this.smsConfigMapper = smsConfigMapper;
    }

    /**
     * Return a {@link List} of {@link SmsConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SmsConfigDTO> findByCriteria(SmsConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsConfig> specification = createSpecification(criteria);
        return smsConfigMapper.toDto(smsConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SmsConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsConfigDTO> findByCriteria(SmsConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SmsConfig> specification = createSpecification(criteria);
        return smsConfigRepository.findAll(specification, page).map(smsConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SmsConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SmsConfig> specification = createSpecification(criteria);
        return smsConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link SmsConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SmsConfig> createSpecification(SmsConfigCriteria criteria) {
        Specification<SmsConfig> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SmsConfig_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsConfig_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsConfig_.id
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsConfig_.smsCode
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsConfig_.templateId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsConfig_.accessKey
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsConfig_.secretKey
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsConfig_.regionId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsConfig_.signName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsConfig_.remark
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), SmsConfig_.id));
                }
                if (criteria.getProvider() != null) {
                    specification = specification.and(buildSpecification(criteria.getProvider(), SmsConfig_.provider));
                }
                if (criteria.getSmsCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getSmsCode(), SmsConfig_.smsCode));
                }
                if (criteria.getTemplateId() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getTemplateId(), SmsConfig_.templateId));
                }
                if (criteria.getAccessKey() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getAccessKey(), SmsConfig_.accessKey));
                }
                if (criteria.getSecretKey() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getSecretKey(), SmsConfig_.secretKey));
                }
                if (criteria.getRegionId() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRegionId(), SmsConfig_.regionId));
                }
                if (criteria.getSignName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getSignName(), SmsConfig_.signName));
                }
                if (criteria.getRemark() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRemark(), SmsConfig_.remark));
                }
                if (criteria.getEnabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getEnabled(), SmsConfig_.enabled));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SmsConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<SmsConfig> root = q.from(SmsConfig.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SmsConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<SmsConfig> root = q.from(SmsConfig.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, SmsConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<SmsConfig> q = cb.createCriteriaUpdate(SmsConfig.class);
        CriteriaQuery<SmsConfig> sq = cb.createQuery(SmsConfig.class);
        Root<SmsConfig> root = q.from(SmsConfig.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link SmsConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsConfigDTO> getOneByCriteria(SmsConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsConfig> specification = createSpecification(criteria);
        return smsConfigRepository.findOne(specification).map(smsConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<SmsConfig> specification) {
        log.debug("count by specification : {}", specification);
        return smsConfigRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link SmsConfigDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsConfigDTO> findBySpecification(Specification<SmsConfig> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return smsConfigRepository.findAll(specification, page).map(smsConfigMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SmsConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<SmsConfig> root = q.from(SmsConfig.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getProvider() != null) {
            getAggregateAndGroupBy(criteria.getProvider(), "provider", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSmsCode() != null) {
            getAggregateAndGroupBy(criteria.getSmsCode(), "sms_code", selectFields, groupByFields, cb, root);
        }
        if (criteria.getTemplateId() != null) {
            getAggregateAndGroupBy(criteria.getTemplateId(), "template_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getAccessKey() != null) {
            getAggregateAndGroupBy(criteria.getAccessKey(), "access_key", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSecretKey() != null) {
            getAggregateAndGroupBy(criteria.getSecretKey(), "secret_key", selectFields, groupByFields, cb, root);
        }
        if (criteria.getRegionId() != null) {
            getAggregateAndGroupBy(criteria.getRegionId(), "region_id", selectFields, groupByFields, cb, root);
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
