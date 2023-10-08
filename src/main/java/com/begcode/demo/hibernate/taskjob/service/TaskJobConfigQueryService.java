package com.begcode.demo.hibernate.taskjob.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.taskjob.domain.*; // for static metamodels
import com.begcode.demo.hibernate.taskjob.domain.TaskJobConfig;
import com.begcode.demo.hibernate.taskjob.repository.TaskJobConfigRepository;
import com.begcode.demo.hibernate.taskjob.service.criteria.TaskJobConfigCriteria;
import com.begcode.demo.hibernate.taskjob.service.dto.TaskJobConfigDTO;
import com.begcode.demo.hibernate.taskjob.service.mapper.TaskJobConfigMapper;
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
 * 用于对数据库中的{@link TaskJobConfig}实体执行复杂查询的Service。
 * 主要输入是一个{@link TaskJobConfigCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link TaskJobConfigDTO}列表{@link List} 或 {@link TaskJobConfigDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class TaskJobConfigQueryService extends QueryService<TaskJobConfig> {

    private final Logger log = LoggerFactory.getLogger(TaskJobConfigQueryService.class);

    protected final TaskJobConfigRepository taskJobConfigRepository;

    protected final EntityManager em;

    protected final TaskJobConfigMapper taskJobConfigMapper;

    public TaskJobConfigQueryService(
        TaskJobConfigRepository taskJobConfigRepository,
        EntityManager em,
        TaskJobConfigMapper taskJobConfigMapper
    ) {
        this.taskJobConfigRepository = taskJobConfigRepository;
        this.em = em;
        this.taskJobConfigMapper = taskJobConfigMapper;
    }

    /**
     * Return a {@link List} of {@link TaskJobConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TaskJobConfigDTO> findByCriteria(TaskJobConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskJobConfig> specification = createSpecification(criteria);
        return taskJobConfigMapper.toDto(taskJobConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TaskJobConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskJobConfigDTO> findByCriteria(TaskJobConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TaskJobConfig> specification = createSpecification(criteria);
        return taskJobConfigRepository.findAll(specification, page).map(taskJobConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TaskJobConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TaskJobConfig> specification = createSpecification(criteria);
        return taskJobConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link TaskJobConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TaskJobConfig> createSpecification(TaskJobConfigCriteria criteria) {
        Specification<TaskJobConfig> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TaskJobConfig_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                TaskJobConfig_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                TaskJobConfig_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                TaskJobConfig_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                TaskJobConfig_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                TaskJobConfig_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                TaskJobConfig_.jobClassName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                TaskJobConfig_.cronExpression
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                TaskJobConfig_.parameter
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                TaskJobConfig_.description
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildRangeSpecification(criteria.getId(), TaskJobConfig_.id)
                        );
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), TaskJobConfig_.name));
                }
                if (criteria.getJobClassName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getJobClassName(), TaskJobConfig_.jobClassName));
                }
                if (criteria.getCronExpression() != null) {
                    specification =
                        specification.and(buildStringSpecification(criteria.getCronExpression(), TaskJobConfig_.cronExpression));
                }
                if (criteria.getParameter() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getParameter(), TaskJobConfig_.parameter));
                }
                if (criteria.getDescription() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDescription(), TaskJobConfig_.description));
                }
                if (criteria.getJobStatus() != null) {
                    specification = specification.and(buildSpecification(criteria.getJobStatus(), TaskJobConfig_.jobStatus));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), TaskJobConfig_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), TaskJobConfig_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), TaskJobConfig_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), TaskJobConfig_.lastModifiedDate));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, TaskJobConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<TaskJobConfig> root = q.from(TaskJobConfig.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, TaskJobConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<TaskJobConfig> root = q.from(TaskJobConfig.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, TaskJobConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<TaskJobConfig> q = cb.createCriteriaUpdate(TaskJobConfig.class);
        CriteriaQuery<TaskJobConfig> sq = cb.createQuery(TaskJobConfig.class);
        Root<TaskJobConfig> root = q.from(TaskJobConfig.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link TaskJobConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskJobConfigDTO> getOneByCriteria(TaskJobConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TaskJobConfig> specification = createSpecification(criteria);
        return taskJobConfigRepository.findOne(specification).map(taskJobConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<TaskJobConfig> specification) {
        log.debug("count by specification : {}", specification);
        return taskJobConfigRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link TaskJobConfigDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskJobConfigDTO> findBySpecification(Specification<TaskJobConfig> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return taskJobConfigRepository.findAll(specification, page).map(taskJobConfigMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(TaskJobConfigCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<TaskJobConfig> root = q.from(TaskJobConfig.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getJobClassName() != null) {
            getAggregateAndGroupBy(criteria.getJobClassName(), "job_class_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCronExpression() != null) {
            getAggregateAndGroupBy(criteria.getCronExpression(), "cron_expression", selectFields, groupByFields, cb, root);
        }
        if (criteria.getParameter() != null) {
            getAggregateAndGroupBy(criteria.getParameter(), "parameter", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDescription() != null) {
            getAggregateAndGroupBy(criteria.getDescription(), "description", selectFields, groupByFields, cb, root);
        }
        if (criteria.getJobStatus() != null) {
            getAggregateAndGroupBy(criteria.getJobStatus(), "job_status", selectFields, groupByFields, cb, root);
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
