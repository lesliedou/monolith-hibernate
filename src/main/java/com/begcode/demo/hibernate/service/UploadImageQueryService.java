package com.begcode.demo.hibernate.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.domain.*; // for static metamodels
import com.begcode.demo.hibernate.domain.UploadImage;
import com.begcode.demo.hibernate.repository.UploadImageRepository;
import com.begcode.demo.hibernate.service.criteria.UploadImageCriteria;
import com.begcode.demo.hibernate.service.dto.UploadImageDTO;
import com.begcode.demo.hibernate.service.mapper.UploadImageMapper;
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
 * 用于对数据库中的{@link UploadImage}实体执行复杂查询的Service。
 * 主要输入是一个{@link UploadImageCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link UploadImageDTO}列表{@link List} 或 {@link UploadImageDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class UploadImageQueryService extends QueryService<UploadImage> {

    private final Logger log = LoggerFactory.getLogger(UploadImageQueryService.class);

    protected final UploadImageRepository uploadImageRepository;

    protected final EntityManager em;

    protected final UploadImageMapper uploadImageMapper;

    public UploadImageQueryService(UploadImageRepository uploadImageRepository, EntityManager em, UploadImageMapper uploadImageMapper) {
        this.uploadImageRepository = uploadImageRepository;
        this.em = em;
        this.uploadImageMapper = uploadImageMapper;
    }

    /**
     * Return a {@link List} of {@link UploadImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UploadImageDTO> findByCriteria(UploadImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UploadImage> specification = createSpecification(criteria);
        return uploadImageMapper.toDto(uploadImageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UploadImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadImageDTO> findByCriteria(UploadImageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UploadImage> specification = createSpecification(criteria);
        return uploadImageRepository.findAll(specification, page).map(uploadImageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UploadImageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UploadImage> specification = createSpecification(criteria);
        return uploadImageRepository.count(specification);
    }

    /**
     * Function to convert {@link UploadImageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UploadImage> createSpecification(UploadImageCriteria criteria) {
        Specification<UploadImage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UploadImage_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                UploadImage_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                UploadImage_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                UploadImage_.fileSize
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                UploadImage_.referenceCount
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                UploadImage_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                UploadImage_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.fullName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.ext
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.type
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.url
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.path
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.folder
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.ownerEntityName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.ownerEntityId
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.businessTitle
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.businessDesc
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.businessStatus
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.smartUrl
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                UploadImage_.mediumUrl
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), UploadImage_.id));
                }
                if (criteria.getFullName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFullName(), UploadImage_.fullName));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), UploadImage_.name));
                }
                if (criteria.getExt() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getExt(), UploadImage_.ext));
                }
                if (criteria.getType() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getType(), UploadImage_.type));
                }
                if (criteria.getUrl() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getUrl(), UploadImage_.url));
                }
                if (criteria.getPath() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getPath(), UploadImage_.path));
                }
                if (criteria.getFolder() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFolder(), UploadImage_.folder));
                }
                if (criteria.getOwnerEntityName() != null) {
                    specification =
                        specification.and(buildStringSpecification(criteria.getOwnerEntityName(), UploadImage_.ownerEntityName));
                }
                if (criteria.getOwnerEntityId() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getOwnerEntityId(), UploadImage_.ownerEntityId));
                }
                if (criteria.getBusinessTitle() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getBusinessTitle(), UploadImage_.businessTitle));
                }
                if (criteria.getBusinessDesc() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getBusinessDesc(), UploadImage_.businessDesc));
                }
                if (criteria.getBusinessStatus() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getBusinessStatus(), UploadImage_.businessStatus));
                }
                if (criteria.getCreateAt() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreateAt(), UploadImage_.createAt));
                }
                if (criteria.getFileSize() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getFileSize(), UploadImage_.fileSize));
                }
                if (criteria.getSmartUrl() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getSmartUrl(), UploadImage_.smartUrl));
                }
                if (criteria.getMediumUrl() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getMediumUrl(), UploadImage_.mediumUrl));
                }
                if (criteria.getReferenceCount() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getReferenceCount(), UploadImage_.referenceCount));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), UploadImage_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), UploadImage_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), UploadImage_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification =
                        specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), UploadImage_.lastModifiedDate));
                }
                if (criteria.getCategoryId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getCategoryId(),
                                root -> root.join(UploadImage_.category, JoinType.LEFT).get(ResourceCategory_.id)
                            )
                        );
                }
                if (criteria.getCategoryTitle() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getCategoryTitle(),
                                root -> root.join(UploadImage_.category, JoinType.LEFT).get(ResourceCategory_.title)
                            )
                        );
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, UploadImageCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<UploadImage> root = q.from(UploadImage.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, UploadImageCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<UploadImage> root = q.from(UploadImage.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, UploadImageCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<UploadImage> q = cb.createCriteriaUpdate(UploadImage.class);
        CriteriaQuery<UploadImage> sq = cb.createQuery(UploadImage.class);
        Root<UploadImage> root = q.from(UploadImage.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link UploadImageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<UploadImageDTO> getOneByCriteria(UploadImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UploadImage> specification = createSpecification(criteria);
        return uploadImageRepository.findOne(specification).map(uploadImageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<UploadImage> specification) {
        log.debug("count by specification : {}", specification);
        return uploadImageRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link UploadImageDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadImageDTO> findBySpecification(Specification<UploadImage> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return uploadImageRepository.findAll(specification, page).map(uploadImageMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(UploadImageCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<UploadImage> root = q.from(UploadImage.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getFullName() != null) {
            getAggregateAndGroupBy(criteria.getFullName(), "full_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getExt() != null) {
            getAggregateAndGroupBy(criteria.getExt(), "ext", selectFields, groupByFields, cb, root);
        }
        if (criteria.getType() != null) {
            getAggregateAndGroupBy(criteria.getType(), "type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getUrl() != null) {
            getAggregateAndGroupBy(criteria.getUrl(), "url", selectFields, groupByFields, cb, root);
        }
        if (criteria.getPath() != null) {
            getAggregateAndGroupBy(criteria.getPath(), "path", selectFields, groupByFields, cb, root);
        }
        if (criteria.getFolder() != null) {
            getAggregateAndGroupBy(criteria.getFolder(), "folder", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOwnerEntityName() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityName(), "owner_entity_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOwnerEntityId() != null) {
            getAggregateAndGroupBy(criteria.getOwnerEntityId(), "owner_entity_id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getBusinessTitle() != null) {
            getAggregateAndGroupBy(criteria.getBusinessTitle(), "business_title", selectFields, groupByFields, cb, root);
        }
        if (criteria.getBusinessDesc() != null) {
            getAggregateAndGroupBy(criteria.getBusinessDesc(), "business_desc", selectFields, groupByFields, cb, root);
        }
        if (criteria.getBusinessStatus() != null) {
            getAggregateAndGroupBy(criteria.getBusinessStatus(), "business_status", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCreateAt() != null) {
            getAggregateAndGroupBy(criteria.getCreateAt(), "create_at", selectFields, groupByFields, cb, root);
        }
        if (criteria.getFileSize() != null) {
            getAggregateAndGroupBy(criteria.getFileSize(), "file_size", selectFields, groupByFields, cb, root);
        }
        if (criteria.getSmartUrl() != null) {
            getAggregateAndGroupBy(criteria.getSmartUrl(), "smart_url", selectFields, groupByFields, cb, root);
        }
        if (criteria.getMediumUrl() != null) {
            getAggregateAndGroupBy(criteria.getMediumUrl(), "medium_url", selectFields, groupByFields, cb, root);
        }
        if (criteria.getReferenceCount() != null) {
            getAggregateAndGroupBy(criteria.getReferenceCount(), "reference_count", selectFields, groupByFields, cb, root);
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
