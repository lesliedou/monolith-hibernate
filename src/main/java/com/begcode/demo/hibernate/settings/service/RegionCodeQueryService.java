package com.begcode.demo.hibernate.settings.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.settings.domain.*; // for static metamodels
import com.begcode.demo.hibernate.settings.domain.RegionCode;
import com.begcode.demo.hibernate.settings.repository.RegionCodeRepository;
import com.begcode.demo.hibernate.settings.service.criteria.RegionCodeCriteria;
import com.begcode.demo.hibernate.settings.service.dto.RegionCodeDTO;
import com.begcode.demo.hibernate.settings.service.mapper.RegionCodeMapper;
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
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * 用于对数据库中的{@link RegionCode}实体执行复杂查询的Service。
 * 主要输入是一个{@link RegionCodeCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link RegionCodeDTO}列表{@link List} 或 {@link RegionCodeDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class RegionCodeQueryService extends QueryService<RegionCode> {

    private final Logger log = LoggerFactory.getLogger(RegionCodeQueryService.class);

    protected final RegionCodeRepository regionCodeRepository;

    protected final EntityManager em;

    protected final RegionCodeMapper regionCodeMapper;

    public RegionCodeQueryService(RegionCodeRepository regionCodeRepository, EntityManager em, RegionCodeMapper regionCodeMapper) {
        this.regionCodeRepository = regionCodeRepository;
        this.em = em;
        this.regionCodeMapper = regionCodeMapper;
    }

    /**
     * Return a {@link List} of {@link RegionCodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegionCodeDTO> findByCriteria(RegionCodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegionCode> specification = createSpecification(criteria);
        return regionCodeMapper.toDto(regionCodeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegionCodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionCodeDTO> findByCriteria(RegionCodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegionCode> specification = createSpecification(criteria);
        return regionCodeRepository.findAll(specification, page).map(regionCodeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegionCodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegionCode> specification = createSpecification(criteria);
        return regionCodeRepository.count(specification);
    }

    /**
     * Function to convert {@link RegionCodeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RegionCode> createSpecification(RegionCodeCriteria criteria) {
        Specification<RegionCode> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RegionCode_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                RegionCode_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                RegionCode_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (DoubleFilter) new DoubleFilter().setEquals(Double.valueOf(criteria.getJhiCommonSearchKeywords())),
                                RegionCode_.lng
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (DoubleFilter) new DoubleFilter().setEquals(Double.valueOf(criteria.getJhiCommonSearchKeywords())),
                                RegionCode_.lat
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                RegionCode_.name
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                RegionCode_.areaCode
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                RegionCode_.cityCode
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                RegionCode_.mergerName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                RegionCode_.shortName
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                RegionCode_.zipCode
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), RegionCode_.id));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), RegionCode_.name));
                }
                if (criteria.getAreaCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getAreaCode(), RegionCode_.areaCode));
                }
                if (criteria.getCityCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCityCode(), RegionCode_.cityCode));
                }
                if (criteria.getMergerName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getMergerName(), RegionCode_.mergerName));
                }
                if (criteria.getShortName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getShortName(), RegionCode_.shortName));
                }
                if (criteria.getZipCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getZipCode(), RegionCode_.zipCode));
                }
                if (criteria.getLevel() != null) {
                    specification = specification.and(buildSpecification(criteria.getLevel(), RegionCode_.level));
                }
                if (criteria.getLng() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLng(), RegionCode_.lng));
                }
                if (criteria.getLat() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLat(), RegionCode_.lat));
                }
                if (criteria.getChildrenId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getChildrenId(),
                                root -> root.join(RegionCode_.children, JoinType.LEFT).get(RegionCode_.id)
                            )
                        );
                }
                if (criteria.getChildrenName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getChildrenName(),
                                root -> root.join(RegionCode_.children, JoinType.LEFT).get(RegionCode_.name)
                            )
                        );
                }
                if (criteria.getParentId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getParentId(),
                                root -> root.join(RegionCode_.parent, JoinType.LEFT).get(RegionCode_.id)
                            )
                        );
                }
                if (criteria.getParentName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getParentName(),
                                root -> root.join(RegionCode_.parent, JoinType.LEFT).get(RegionCode_.name)
                            )
                        );
                }
            }
        }
        return specification;
    }

    /**
     * Get all the regionCodes for parent is null.
     *
     * @param page the pagination information
     * @return the list of entities
     */
    @Transactional
    public Page<RegionCodeDTO> findAllTop(RegionCodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.parentId().setSpecified(false);
        final Specification<RegionCode> specification = createSpecification(criteria);
        return regionCodeRepository.findAll(specification, page).map(regionCodeMapper::toDto);
    }

    /**
     * Get all the regionCodes for parent is parentId.
     *
     * @param parentId the Id of parent
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<RegionCodeDTO> findChildrenByParentId(Long parentId) {
        log.debug("Request to get all RegionCodes for parent is parentId");
        return regionCodeMapper.toDto(regionCodeRepository.findAllByParentId(parentId));
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, RegionCodeCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<RegionCode> root = q.from(RegionCode.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, RegionCodeCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<RegionCode> root = q.from(RegionCode.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, RegionCodeCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<RegionCode> q = cb.createCriteriaUpdate(RegionCode.class);
        CriteriaQuery<RegionCode> sq = cb.createQuery(RegionCode.class);
        Root<RegionCode> root = q.from(RegionCode.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link RegionCodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<RegionCodeDTO> getOneByCriteria(RegionCodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegionCode> specification = createSpecification(criteria);
        return regionCodeRepository.findOne(specification).map(regionCodeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<RegionCode> specification) {
        log.debug("count by specification : {}", specification);
        return regionCodeRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link RegionCodeDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegionCodeDTO> findBySpecification(Specification<RegionCode> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return regionCodeRepository.findAll(specification, page).map(regionCodeMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(RegionCodeCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<RegionCode> root = q.from(RegionCode.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getName() != null) {
            getAggregateAndGroupBy(criteria.getName(), "name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getAreaCode() != null) {
            getAggregateAndGroupBy(criteria.getAreaCode(), "area_code", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCityCode() != null) {
            getAggregateAndGroupBy(criteria.getCityCode(), "city_code", selectFields, groupByFields, cb, root);
        }
        if (criteria.getMergerName() != null) {
            getAggregateAndGroupBy(criteria.getMergerName(), "merger_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getShortName() != null) {
            getAggregateAndGroupBy(criteria.getShortName(), "short_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getZipCode() != null) {
            getAggregateAndGroupBy(criteria.getZipCode(), "zip_code", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLevel() != null) {
            getAggregateAndGroupBy(criteria.getLevel(), "level", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLng() != null) {
            getAggregateAndGroupBy(criteria.getLng(), "lng", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLat() != null) {
            getAggregateAndGroupBy(criteria.getLat(), "lat", selectFields, groupByFields, cb, root);
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
