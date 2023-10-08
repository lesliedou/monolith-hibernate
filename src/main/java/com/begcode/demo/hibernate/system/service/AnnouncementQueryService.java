package com.begcode.demo.hibernate.system.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.system.domain.*; // for static metamodels
import com.begcode.demo.hibernate.system.domain.Announcement;
import com.begcode.demo.hibernate.system.repository.AnnouncementRepository;
import com.begcode.demo.hibernate.system.service.criteria.AnnouncementCriteria;
import com.begcode.demo.hibernate.system.service.dto.AnnouncementDTO;
import com.begcode.demo.hibernate.system.service.mapper.AnnouncementMapper;
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
 * 用于对数据库中的{@link Announcement}实体执行复杂查询的Service。
 * 主要输入是一个{@link AnnouncementCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link AnnouncementDTO}列表{@link List} 或 {@link AnnouncementDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class AnnouncementQueryService extends QueryService<Announcement> {

    private final Logger log = LoggerFactory.getLogger(AnnouncementQueryService.class);

    protected final AnnouncementRepository announcementRepository;

    protected final EntityManager em;

    protected final AnnouncementMapper announcementMapper;

    public AnnouncementQueryService(
        AnnouncementRepository announcementRepository,
        EntityManager em,
        AnnouncementMapper announcementMapper
    ) {
        this.announcementRepository = announcementRepository;
        this.em = em;
        this.announcementMapper = announcementMapper;
    }

    /**
     * Return a {@link List} of {@link AnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnnouncementDTO> findByCriteria(AnnouncementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Announcement> specification = createSpecification(criteria);
        return announcementMapper.toDto(announcementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementDTO> findByCriteria(AnnouncementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Announcement> specification = createSpecification(criteria);
        return announcementRepository.findAll(specification, page).map(announcementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnnouncementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Announcement> specification = createSpecification(criteria);
        return announcementRepository.count(specification);
    }

    /**
     * Function to convert {@link AnnouncementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Announcement> createSpecification(AnnouncementCriteria criteria) {
        Specification<Announcement> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Announcement_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Announcement_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Announcement_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Announcement_.senderId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Announcement_.businessId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Announcement_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                Announcement_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Announcement_.title
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                Announcement_.openPage
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), Announcement_.id));
                }
                if (criteria.getTitle() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getTitle(), Announcement_.title));
                }
                if (criteria.getStartTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getStartTime(), Announcement_.startTime));
                }
                if (criteria.getEndTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getEndTime(), Announcement_.endTime));
                }
                if (criteria.getSenderId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSenderId(), Announcement_.senderId));
                }
                if (criteria.getPriority() != null) {
                    specification = specification.and(buildSpecification(criteria.getPriority(), Announcement_.priority));
                }
                if (criteria.getCategory() != null) {
                    specification = specification.and(buildSpecification(criteria.getCategory(), Announcement_.category));
                }
                if (criteria.getReceiverType() != null) {
                    specification = specification.and(buildSpecification(criteria.getReceiverType(), Announcement_.receiverType));
                }
                if (criteria.getSendStatus() != null) {
                    specification = specification.and(buildSpecification(criteria.getSendStatus(), Announcement_.sendStatus));
                }
                if (criteria.getSendTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getSendTime(), Announcement_.sendTime));
                }
                if (criteria.getCancelTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCancelTime(), Announcement_.cancelTime));
                }
                if (criteria.getBusinessType() != null) {
                    specification = specification.and(buildSpecification(criteria.getBusinessType(), Announcement_.businessType));
                }
                if (criteria.getBusinessId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getBusinessId(), Announcement_.businessId));
                }
                if (criteria.getOpenType() != null) {
                    specification = specification.and(buildSpecification(criteria.getOpenType(), Announcement_.openType));
                }
                if (criteria.getOpenPage() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getOpenPage(), Announcement_.openPage));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), Announcement_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Announcement_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), Announcement_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Announcement_.lastModifiedDate));
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, AnnouncementCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<Announcement> root = q.from(Announcement.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, AnnouncementCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Announcement> root = q.from(Announcement.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, AnnouncementCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Announcement> q = cb.createCriteriaUpdate(Announcement.class);
        CriteriaQuery<Announcement> sq = cb.createQuery(Announcement.class);
        Root<Announcement> root = q.from(Announcement.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link AnnouncementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnnouncementDTO> getOneByCriteria(AnnouncementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Announcement> specification = createSpecification(criteria);
        return announcementRepository.findOne(specification).map(announcementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<Announcement> specification) {
        log.debug("count by specification : {}", specification);
        return announcementRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link AnnouncementDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AnnouncementDTO> findBySpecification(Specification<Announcement> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return announcementRepository.findAll(specification, page).map(announcementMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(AnnouncementCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<Announcement> root = q.from(Announcement.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getTitle() != null) {
            getAggregateAndGroupBy(criteria.getTitle(), "title", selectFields, groupByFields, cb, root);
        }
        if (criteria.getStartTime() != null) {
            getAggregateAndGroupBy(criteria.getStartTime(), "start_time", selectFields, groupByFields, cb, root);
        }
        if (criteria.getEndTime() != null) {
            getAggregateAndGroupBy(criteria.getEndTime(), "end_time", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSenderId() != null) {
            getAggregateAndGroupBy(criteria.getSenderId(), "sender_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getPriority() != null) {
            getAggregateAndGroupBy(criteria.getPriority(), "priority", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCategory() != null) {
            getAggregateAndGroupBy(criteria.getCategory(), "category", selectFields, groupByFields, cb, root);
        }
        if (criteria.getReceiverType() != null) {
            getAggregateAndGroupBy(criteria.getReceiverType(), "receiver_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSendStatus() != null) {
            getAggregateAndGroupBy(criteria.getSendStatus(), "send_status", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSendTime() != null) {
            getAggregateAndGroupBy(criteria.getSendTime(), "send_time", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCancelTime() != null) {
            getAggregateAndGroupBy(criteria.getCancelTime(), "cancel_time", selectFields, groupByFields, cb, root);
        }
        if (criteria.getBusinessType() != null) {
            getAggregateAndGroupBy(criteria.getBusinessType(), "business_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getBusinessId() != null) {
            getAggregateAndGroupBy(criteria.getBusinessId(), "business_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOpenType() != null) {
            getAggregateAndGroupBy(criteria.getOpenType(), "open_type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOpenPage() != null) {
            getAggregateAndGroupBy(criteria.getOpenPage(), "open_page", selectFields, groupByFields, cb, root);
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
