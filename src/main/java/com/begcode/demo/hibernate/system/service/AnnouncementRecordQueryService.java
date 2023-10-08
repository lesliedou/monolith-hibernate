package com.begcode.demo.hibernate.system.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.system.domain.*; // for static metamodels
import com.begcode.demo.hibernate.system.domain.AnnouncementRecord;
import com.begcode.demo.hibernate.system.repository.AnnouncementRecordRepository;
import com.begcode.demo.hibernate.system.service.criteria.AnnouncementRecordCriteria;
import com.begcode.demo.hibernate.system.service.dto.AnnouncementRecordDTO;
import com.begcode.demo.hibernate.system.service.mapper.AnnouncementRecordMapper;
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

/**
 * 用于对数据库中的{@link AnnouncementRecord}实体执行复杂查询的Service。
 * 主要输入是一个{@link AnnouncementRecordCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link AnnouncementRecordDTO}列表{@link List} 或 {@link AnnouncementRecordDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class AnnouncementRecordQueryService extends QueryService<AnnouncementRecord> {

    private final Logger log = LoggerFactory.getLogger(AnnouncementRecordQueryService.class);

    protected final AnnouncementRecordRepository announcementRecordRepository;

    protected final EntityManager em;

    protected final AnnouncementRecordMapper announcementRecordMapper;

    public AnnouncementRecordQueryService(
        AnnouncementRecordRepository announcementRecordRepository,
        EntityManager em,
        AnnouncementRecordMapper announcementRecordMapper
    ) {
        this.announcementRecordRepository = announcementRecordRepository;
        this.em = em;
        this.announcementRecordMapper = announcementRecordMapper;
    }

    /**
     * Return a {@link List} of {@link AnnouncementRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnnouncementRecordDTO> findByCriteria(AnnouncementRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnnouncementRecord> specification = createSpecification(criteria);
        return announcementRecordMapper.toDto(announcementRecordRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnnouncementRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementRecordDTO> findByCriteria(AnnouncementRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AnnouncementRecord> specification = createSpecification(criteria);
        return announcementRecordRepository.findAll(specification, page).map(announcementRecordMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnnouncementRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnnouncementRecord> specification = createSpecification(criteria);
        return announcementRecordRepository.count(specification);
    }

    /**
     * Function to convert {@link AnnouncementRecordCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnnouncementRecord> createSpecification(AnnouncementRecordCriteria criteria) {
        Specification<AnnouncementRecord> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnnouncementRecord_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                AnnouncementRecord_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                AnnouncementRecord_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                AnnouncementRecord_.anntId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                AnnouncementRecord_.userId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                AnnouncementRecord_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                AnnouncementRecord_.lastModifiedBy
                            )
                        );
                } else {}
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildRangeSpecification(criteria.getId(), AnnouncementRecord_.id)
                        );
                }
                if (criteria.getAnntId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getAnntId(), AnnouncementRecord_.anntId));
                }
                if (criteria.getUserId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getUserId(), AnnouncementRecord_.userId));
                }
                if (criteria.getHasRead() != null) {
                    specification = specification.and(buildSpecification(criteria.getHasRead(), AnnouncementRecord_.hasRead));
                }
                if (criteria.getReadTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getReadTime(), AnnouncementRecord_.readTime));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), AnnouncementRecord_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), AnnouncementRecord_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), AnnouncementRecord_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), AnnouncementRecord_.lastModifiedDate));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, AnnouncementRecordCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<AnnouncementRecord> root = q.from(AnnouncementRecord.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, AnnouncementRecordCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<AnnouncementRecord> root = q.from(AnnouncementRecord.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, AnnouncementRecordCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<AnnouncementRecord> q = cb.createCriteriaUpdate(AnnouncementRecord.class);
        CriteriaQuery<AnnouncementRecord> sq = cb.createQuery(AnnouncementRecord.class);
        Root<AnnouncementRecord> root = q.from(AnnouncementRecord.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link AnnouncementRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnnouncementRecordDTO> getOneByCriteria(AnnouncementRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnnouncementRecord> specification = createSpecification(criteria);
        return announcementRecordRepository.findOne(specification).map(announcementRecordMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<AnnouncementRecord> specification) {
        log.debug("count by specification : {}", specification);
        return announcementRecordRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link AnnouncementRecordDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementRecordDTO> findBySpecification(Specification<AnnouncementRecord> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return announcementRecordRepository.findAll(specification, page).map(announcementRecordMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(AnnouncementRecordCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<AnnouncementRecord> root = q.from(AnnouncementRecord.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getAnntId() != null) {
            getAggregateAndGroupBy(criteria.getAnntId(), "annt_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getUserId() != null) {
            getAggregateAndGroupBy(criteria.getUserId(), "user_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getHasRead() != null) {
            getAggregateAndGroupBy(criteria.getHasRead(), "has_read", selectFields, groupByFields, cb, root);
        }
        if (criteria.getReadTime() != null) {
            getAggregateAndGroupBy(criteria.getReadTime(), "read_time", selectFields, groupByFields, cb, root);
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
