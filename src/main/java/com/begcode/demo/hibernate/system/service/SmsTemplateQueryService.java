package com.begcode.demo.hibernate.system.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.system.domain.*; // for static metamodels
import com.begcode.demo.hibernate.system.domain.SmsTemplate;
import com.begcode.demo.hibernate.system.repository.SmsTemplateRepository;
import com.begcode.demo.hibernate.system.service.criteria.SmsTemplateCriteria;
import com.begcode.demo.hibernate.system.service.dto.SmsTemplateDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsTemplateMapper;
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
 * 用于对数据库中的{@link SmsTemplate}实体执行复杂查询的Service。
 * 主要输入是一个{@link SmsTemplateCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SmsTemplateDTO}列表{@link List} 或 {@link SmsTemplateDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class SmsTemplateQueryService extends QueryService<SmsTemplate> {

    private final Logger log = LoggerFactory.getLogger(SmsTemplateQueryService.class);

    protected final SmsTemplateRepository smsTemplateRepository;

    protected final EntityManager em;

    protected final SmsTemplateMapper smsTemplateMapper;

    public SmsTemplateQueryService(SmsTemplateRepository smsTemplateRepository, EntityManager em, SmsTemplateMapper smsTemplateMapper) {
        this.smsTemplateRepository = smsTemplateRepository;
        this.em = em;
        this.smsTemplateMapper = smsTemplateMapper;
    }

    /**
     * Return a {@link List} of {@link SmsTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SmsTemplateDTO> findByCriteria(SmsTemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsTemplate> specification = createSpecification(criteria);
        return smsTemplateMapper.toDto(smsTemplateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SmsTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsTemplateDTO> findByCriteria(SmsTemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SmsTemplate> specification = createSpecification(criteria);
        return smsTemplateRepository.findAll(specification, page).map(smsTemplateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SmsTemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SmsTemplate> specification = createSpecification(criteria);
        return smsTemplateRepository.count(specification);
    }

    /**
     * Function to convert {@link SmsTemplateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SmsTemplate> createSpecification(SmsTemplateCriteria criteria) {
        Specification<SmsTemplate> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SmsTemplate_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsTemplate_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsTemplate_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsTemplate_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SmsTemplate_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsTemplate_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsTemplate_.code
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsTemplate_.content
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SmsTemplate_.testJson
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), SmsTemplate_.id));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), SmsTemplate_.name));
                }
                if (criteria.getCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCode(), SmsTemplate_.code));
                }
                if (criteria.getType() != null) {
                    specification = specification.and(buildSpecification(criteria.getType(), SmsTemplate_.type));
                }
                if (criteria.getContent() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getContent(), SmsTemplate_.content));
                }
                if (criteria.getTestJson() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getTestJson(), SmsTemplate_.testJson));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), SmsTemplate_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SmsTemplate_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), SmsTemplate_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SmsTemplate_.lastModifiedDate));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SmsTemplateCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<SmsTemplate> root = q.from(SmsTemplate.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SmsTemplateCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<SmsTemplate> root = q.from(SmsTemplate.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, SmsTemplateCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<SmsTemplate> q = cb.createCriteriaUpdate(SmsTemplate.class);
        CriteriaQuery<SmsTemplate> sq = cb.createQuery(SmsTemplate.class);
        Root<SmsTemplate> root = q.from(SmsTemplate.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link SmsTemplateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsTemplateDTO> getOneByCriteria(SmsTemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SmsTemplate> specification = createSpecification(criteria);
        return smsTemplateRepository.findOne(specification).map(smsTemplateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<SmsTemplate> specification) {
        log.debug("count by specification : {}", specification);
        return smsTemplateRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link SmsTemplateDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsTemplateDTO> findBySpecification(Specification<SmsTemplate> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return smsTemplateRepository.findAll(specification, page).map(smsTemplateMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SmsTemplateCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<SmsTemplate> root = q.from(SmsTemplate.class);
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
        if (criteria.getType() != null) {
            getAggregateAndGroupBy(criteria.getType(), "type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getContent() != null) {
            getAggregateAndGroupBy(criteria.getContent(), "content", selectFields, groupByFields, cb, root);
        }
        if (criteria.getTestJson() != null) {
            getAggregateAndGroupBy(criteria.getTestJson(), "test_json", selectFields, groupByFields, cb, root);
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
