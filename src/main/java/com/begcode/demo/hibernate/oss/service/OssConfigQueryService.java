package com.begcode.demo.hibernate.oss.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.oss.domain.*; // for static metamodels
import com.begcode.demo.hibernate.oss.domain.OssConfig;
import com.begcode.demo.hibernate.oss.repository.OssConfigRepository;
import com.begcode.demo.hibernate.oss.service.criteria.OssConfigCriteria;
import com.begcode.demo.hibernate.oss.service.dto.OssConfigDTO;
import com.begcode.demo.hibernate.oss.service.mapper.OssConfigMapper;
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
 * 用于对数据库中的{@link OssConfig}实体执行复杂查询的Service。
 * 主要输入是一个{@link OssConfigCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link OssConfigDTO}列表{@link List} 或 {@link OssConfigDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class OssConfigQueryService extends QueryService<OssConfig> {

    private final Logger log = LoggerFactory.getLogger(OssConfigQueryService.class);

    protected final OssConfigRepository ossConfigRepository;

    protected final EntityManager em;

    protected final OssConfigMapper ossConfigMapper;

    public OssConfigQueryService(OssConfigRepository ossConfigRepository, EntityManager em, OssConfigMapper ossConfigMapper) {
        this.ossConfigRepository = ossConfigRepository;
        this.em = em;
        this.ossConfigMapper = ossConfigMapper;
    }

    /**
     * Return a {@link List} of {@link OssConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OssConfigDTO> findByCriteria(OssConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OssConfig> specification = createSpecification(criteria);
        return ossConfigMapper.toDto(ossConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OssConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OssConfigDTO> findByCriteria(OssConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OssConfig> specification = createSpecification(criteria);
        return ossConfigRepository.findAll(specification, page).map(ossConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OssConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OssConfig> specification = createSpecification(criteria);
        return ossConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link OssConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OssConfig> createSpecification(OssConfigCriteria criteria) {
        Specification<OssConfig> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OssConfig_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                OssConfig_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                OssConfig_.id
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                OssConfig_.platform
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                OssConfig_.remark
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                OssConfig_.configData
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), OssConfig_.id));
                }
                if (criteria.getProvider() != null) {
                    specification = specification.and(buildSpecification(criteria.getProvider(), OssConfig_.provider));
                }
                if (criteria.getPlatform() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getPlatform(), OssConfig_.platform));
                }
                if (criteria.getEnabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getEnabled(), OssConfig_.enabled));
                }
                if (criteria.getRemark() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRemark(), OssConfig_.remark));
                }
                if (criteria.getConfigData() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getConfigData(), OssConfig_.configData));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, OssConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<OssConfig> root = q.from(OssConfig.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, OssConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<OssConfig> root = q.from(OssConfig.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, OssConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<OssConfig> q = cb.createCriteriaUpdate(OssConfig.class);
        CriteriaQuery<OssConfig> sq = cb.createQuery(OssConfig.class);
        Root<OssConfig> root = q.from(OssConfig.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link OssConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<OssConfigDTO> getOneByCriteria(OssConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OssConfig> specification = createSpecification(criteria);
        return ossConfigRepository.findOne(specification).map(ossConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<OssConfig> specification) {
        log.debug("count by specification : {}", specification);
        return ossConfigRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link OssConfigDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OssConfigDTO> findBySpecification(Specification<OssConfig> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return ossConfigRepository.findAll(specification, page).map(ossConfigMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(OssConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<OssConfig> root = q.from(OssConfig.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getProvider() != null) {
            getAggregateAndGroupBy(criteria.getProvider(), "provider", selectFields, groupByFields, cb, root);
        }
        if (criteria.getPlatform() != null) {
            getAggregateAndGroupBy(criteria.getPlatform(), "platform", selectFields, groupByFields, cb, root);
        }
        if (criteria.getEnabled() != null) {
            getAggregateAndGroupBy(criteria.getEnabled(), "enabled", selectFields, groupByFields, cb, root);
        }
        if (criteria.getRemark() != null) {
            getAggregateAndGroupBy(criteria.getRemark(), "remark", selectFields, groupByFields, cb, root);
        }
        if (criteria.getConfigData() != null) {
            getAggregateAndGroupBy(criteria.getConfigData(), "config_data", selectFields, groupByFields, cb, root);
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
