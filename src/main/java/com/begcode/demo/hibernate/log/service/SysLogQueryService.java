package com.begcode.demo.hibernate.log.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.log.domain.*; // for static metamodels
import com.begcode.demo.hibernate.log.domain.SysLog;
import com.begcode.demo.hibernate.log.repository.SysLogRepository;
import com.begcode.demo.hibernate.log.service.criteria.SysLogCriteria;
import com.begcode.demo.hibernate.log.service.dto.SysLogDTO;
import com.begcode.demo.hibernate.log.service.mapper.SysLogMapper;
import com.begcode.demo.hibernate.util.CriteriaUtil;
import com.begcode.demo.hibernate.util.JpaUtil;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.stream.Collectors;
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
 * 用于对数据库中的{@link SysLog}实体执行复杂查询的Service。
 * 主要输入是一个{@link SysLogCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link SysLogDTO}列表{@link List} 或 {@link SysLogDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class SysLogQueryService extends QueryService<SysLog> {

    private final Logger log = LoggerFactory.getLogger(SysLogQueryService.class);

    protected final SysLogRepository sysLogRepository;

    protected final EntityManager em;

    protected final SysLogMapper sysLogMapper;

    public SysLogQueryService(SysLogRepository sysLogRepository, EntityManager em, SysLogMapper sysLogMapper) {
        this.sysLogRepository = sysLogRepository;
        this.em = em;
        this.sysLogMapper = sysLogMapper;
    }

    /**
     * Return a {@link List} of {@link SysLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysLogDTO> findByCriteria(SysLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysLog> specification = createSpecification(criteria);
        return sysLogMapper.toDto(sysLogRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysLogDTO> findByCriteria(SysLogCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysLog> specification = createSpecification(criteria);
        return sysLogRepository.findAll(specification, page).map(sysLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysLogCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysLog> specification = createSpecification(criteria);
        return sysLogRepository.count(specification);
    }

    /**
     * Function to convert {@link SysLogCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysLog> createSpecification(SysLogCriteria criteria) {
        Specification<SysLog> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysLog_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())), SysLog_.id)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SysLog_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SysLog_.costTime
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SysLog_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                SysLog_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysLog_.logContent
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), SysLog_.userid)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysLog_.username
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), SysLog_.ip)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), SysLog_.method)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysLog_.requestUrl
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                SysLog_.requestType
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), SysLog_.id));
                }
                if (criteria.getLogType() != null) {
                    specification = specification.and(buildSpecification(criteria.getLogType(), SysLog_.logType));
                }
                if (criteria.getLogContent() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLogContent(), SysLog_.logContent));
                }
                if (criteria.getOperateType() != null) {
                    specification = specification.and(buildSpecification(criteria.getOperateType(), SysLog_.operateType));
                }
                if (criteria.getUserid() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getUserid(), SysLog_.userid));
                }
                if (criteria.getUsername() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getUsername(), SysLog_.username));
                }
                if (criteria.getIp() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getIp(), SysLog_.ip));
                }
                if (criteria.getMethod() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getMethod(), SysLog_.method));
                }
                if (criteria.getRequestUrl() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRequestUrl(), SysLog_.requestUrl));
                }
                if (criteria.getRequestType() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRequestType(), SysLog_.requestType));
                }
                if (criteria.getCostTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCostTime(), SysLog_.costTime));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), SysLog_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SysLog_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), SysLog_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), SysLog_.lastModifiedDate));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, SysLogCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<SysLog> root = q.from(SysLog.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, SysLogCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<SysLog> root = q.from(SysLog.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, SysLogCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<SysLog> q = cb.createCriteriaUpdate(SysLog.class);
        CriteriaQuery<SysLog> sq = cb.createQuery(SysLog.class);
        Root<SysLog> root = q.from(SysLog.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link SysLogDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysLogDTO> getOneByCriteria(SysLogCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysLog> specification = createSpecification(criteria);
        return sysLogRepository.findOne(specification).map(sysLogMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<SysLog> specification) {
        log.debug("count by specification : {}", specification);
        return sysLogRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link SysLogDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysLogDTO> findBySpecification(Specification<SysLog> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return sysLogRepository.findAll(specification, page).map(sysLogMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(SysLogCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<SysLog> root = q.from(SysLog.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLogType() != null) {
            getAggregateAndGroupBy(criteria.getLogType(), "log_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLogContent() != null) {
            getAggregateAndGroupBy(criteria.getLogContent(), "log_content", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOperateType() != null) {
            getAggregateAndGroupBy(criteria.getOperateType(), "operate_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getUserid() != null) {
            getAggregateAndGroupBy(criteria.getUserid(), "userid", selectFields, groupByFields, cb, root);
        }
        if (criteria.getUsername() != null) {
            getAggregateAndGroupBy(criteria.getUsername(), "username", selectFields, groupByFields, cb, root);
        }
        if (criteria.getIp() != null) {
            getAggregateAndGroupBy(criteria.getIp(), "ip", selectFields, groupByFields, cb, root);
        }
        if (criteria.getMethod() != null) {
            getAggregateAndGroupBy(criteria.getMethod(), "method", selectFields, groupByFields, cb, root);
        }
        if (criteria.getRequestUrl() != null) {
            getAggregateAndGroupBy(criteria.getRequestUrl(), "request_url", selectFields, groupByFields, cb, root);
        }
        if (criteria.getRequestType() != null) {
            getAggregateAndGroupBy(criteria.getRequestType(), "request_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCostTime() != null) {
            getAggregateAndGroupBy(criteria.getCostTime(), "cost_time", selectFields, groupByFields, cb, root);
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

    public Map<String, Object> logInfo() {
        Map<String, Object> result = new HashMap<>();
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        SysLogCriteria sysLogCriteria = new SysLogCriteria();
        sysLogCriteria.logType().setEquals(LogType.LOGIN);
        Long totalVisitCount = countByCriteria(sysLogCriteria);
        result.put("totalVisitCount", totalVisitCount);
        sysLogCriteria
            .createdDate()
            .setGreaterThanOrEqual(dayStart.toInstant().atZone(ZoneId.systemDefault()))
            .setLessThan(dayEnd.toInstant().atZone(ZoneId.systemDefault()));
        Long todayVisitCount = countByCriteria(sysLogCriteria);
        result.put("todayVisitCount", todayVisitCount);
        Long todayIp = countByFieldNameAndCriteria("ip", true, sysLogCriteria);
        result.put("todayIp", todayIp);
        return result;
    }

    public List<Map<String, Object>> countVisit() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Selection<?>> selections = new ArrayList<>();
        List<Expression<?>> groups = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createTupleQuery();
        Root<SysLog> root = q.from(SysLog.class);
        selections.add(cb.count(root.get(SysLog_.id)).alias("visit"));
        selections.add(cb.countDistinct(root.get(SysLog_.ip)).alias("ip"));
        selections.add(cb.function("DATE_FORMAT", Instant.class, root.get(SysLog_.createdDate), cb.literal("%Y-%m-%d")).alias("day"));
        selections.add(cb.function("DATE_FORMAT", Instant.class, root.get(SysLog_.createdDate), cb.literal("%m-%d")));
        groups.add(cb.function("DATE_FORMAT", Instant.class, root.get(SysLog_.createdDate), cb.literal("%Y-%m-%d")));
        List<Order> orders = new ArrayList<>();
        orders.add(cb.asc(cb.function("DATE_FORMAT", Instant.class, root.get(SysLog_.createdDate), cb.literal("%m-%d"))));
        SysLogCriteria sysLogCriteria = new SysLogCriteria();
        sysLogCriteria
            .createdDate()
            .setGreaterThanOrEqual(dayStart.toInstant().atZone(ZoneId.systemDefault()))
            .setLessThan(dayEnd.toInstant().atZone(ZoneId.systemDefault()));
        q.where(createSpecification(sysLogCriteria).toPredicate(root, q, cb));
        q.multiselect(selections);
        groups.add(cb.function("DATE_FORMAT", Instant.class, root.get(SysLog_.createdDate), cb.literal("%m-%d")));
        q.groupBy(groups);
        q.orderBy(orders);
        TypedQuery<Tuple> query = em.createQuery(q);
        List<Tuple> resultList = query.getResultList();
        return resultList.stream().map(JpaUtil::toMap).collect(Collectors.toList());
    }
}
