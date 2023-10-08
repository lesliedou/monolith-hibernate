package com.begcode.demo.hibernate.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.domain.*; // for static metamodels
import com.begcode.demo.hibernate.domain.ViewPermission;
import com.begcode.demo.hibernate.repository.ViewPermissionRepository;
import com.begcode.demo.hibernate.service.criteria.ViewPermissionCriteria;
import com.begcode.demo.hibernate.service.dto.ViewPermissionDTO;
import com.begcode.demo.hibernate.service.mapper.ViewPermissionMapper;
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
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * 用于对数据库中的{@link ViewPermission}实体执行复杂查询的Service。
 * 主要输入是一个{@link ViewPermissionCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link ViewPermissionDTO}列表{@link List} 或 {@link ViewPermissionDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class ViewPermissionQueryService extends QueryService<ViewPermission> {

    private final Logger log = LoggerFactory.getLogger(ViewPermissionQueryService.class);

    protected final ViewPermissionRepository viewPermissionRepository;

    protected final EntityManager em;

    protected final ViewPermissionMapper viewPermissionMapper;

    public ViewPermissionQueryService(
        ViewPermissionRepository viewPermissionRepository,
        EntityManager em,
        ViewPermissionMapper viewPermissionMapper
    ) {
        this.viewPermissionRepository = viewPermissionRepository;
        this.em = em;
        this.viewPermissionMapper = viewPermissionMapper;
    }

    /**
     * Return a {@link List} of {@link ViewPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ViewPermissionDTO> findByCriteria(ViewPermissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionMapper.toDto(viewPermissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ViewPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> findByCriteria(ViewPermissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionRepository.findAll(specification, page).map(viewPermissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViewPermissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionRepository.count(specification);
    }

    /**
     * Function to convert {@link ViewPermissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ViewPermission> createSpecification(ViewPermissionCriteria criteria) {
        Specification<ViewPermission> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ViewPermission_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(
                                new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                ViewPermission_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                ViewPermission_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (IntegerFilter) new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),
                                ViewPermission_.order
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.text
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.i18n
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.link
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.externalLink
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.icon
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.code
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.description
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.apiPermissionCodes
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.componentFile
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(
                                new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),
                                ViewPermission_.redirect
                            )
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildRangeSpecification(criteria.getId(), ViewPermission_.id)
                        );
                }
                if (criteria.getText() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getText(), ViewPermission_.text));
                }
                if (criteria.getI18n() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getI18n(), ViewPermission_.i18n));
                }
                if (criteria.getGroup() != null) {
                    specification = specification.and(buildSpecification(criteria.getGroup(), ViewPermission_.group));
                }
                if (criteria.getLink() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLink(), ViewPermission_.link));
                }
                if (criteria.getExternalLink() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getExternalLink(), ViewPermission_.externalLink));
                }
                if (criteria.getTarget() != null) {
                    specification = specification.and(buildSpecification(criteria.getTarget(), ViewPermission_.target));
                }
                if (criteria.getIcon() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getIcon(), ViewPermission_.icon));
                }
                if (criteria.getDisabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getDisabled(), ViewPermission_.disabled));
                }
                if (criteria.getHide() != null) {
                    specification = specification.and(buildSpecification(criteria.getHide(), ViewPermission_.hide));
                }
                if (criteria.getHideInBreadcrumb() != null) {
                    specification = specification.and(buildSpecification(criteria.getHideInBreadcrumb(), ViewPermission_.hideInBreadcrumb));
                }
                if (criteria.getShortcut() != null) {
                    specification = specification.and(buildSpecification(criteria.getShortcut(), ViewPermission_.shortcut));
                }
                if (criteria.getShortcutRoot() != null) {
                    specification = specification.and(buildSpecification(criteria.getShortcutRoot(), ViewPermission_.shortcutRoot));
                }
                if (criteria.getReuse() != null) {
                    specification = specification.and(buildSpecification(criteria.getReuse(), ViewPermission_.reuse));
                }
                if (criteria.getCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCode(), ViewPermission_.code));
                }
                if (criteria.getDescription() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDescription(), ViewPermission_.description));
                }
                if (criteria.getType() != null) {
                    specification = specification.and(buildSpecification(criteria.getType(), ViewPermission_.type));
                }
                if (criteria.getOrder() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getOrder(), ViewPermission_.order));
                }
                if (criteria.getApiPermissionCodes() != null) {
                    specification =
                        specification.and(buildStringSpecification(criteria.getApiPermissionCodes(), ViewPermission_.apiPermissionCodes));
                }
                if (criteria.getComponentFile() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getComponentFile(), ViewPermission_.componentFile));
                }
                if (criteria.getRedirect() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRedirect(), ViewPermission_.redirect));
                }
                if (criteria.getChildrenId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getChildrenId(),
                                root -> root.join(ViewPermission_.children, JoinType.LEFT).get(ViewPermission_.id)
                            )
                        );
                }
                if (criteria.getChildrenText() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getChildrenText(),
                                root -> root.join(ViewPermission_.children, JoinType.LEFT).get(ViewPermission_.text)
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
                                root -> root.join(ViewPermission_.parent, JoinType.LEFT).get(ViewPermission_.id)
                            )
                        );
                }
                if (criteria.getParentText() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getParentText(),
                                root -> root.join(ViewPermission_.parent, JoinType.LEFT).get(ViewPermission_.text)
                            )
                        );
                }
                if (criteria.getAuthoritiesId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getAuthoritiesId(),
                                root -> root.join(ViewPermission_.authorities, JoinType.LEFT).get(Authority_.id)
                            )
                        );
                }
                if (criteria.getAuthoritiesName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getAuthoritiesName(),
                                root -> root.join(ViewPermission_.authorities, JoinType.LEFT).get(Authority_.name)
                            )
                        );
                }
            }
        }
        return specification;
    }

    /**
     * Get all the viewPermissions for parent is null.
     *
     * @param page the pagination information
     * @return the list of entities
     */
    @Transactional
    public Page<ViewPermissionDTO> findAllTop(ViewPermissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        criteria.parentId().setSpecified(false);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionRepository.findAll(specification, page).map(viewPermissionMapper::toDto);
    }

    /**
     * Get all the viewPermissions for parent is parentId.
     *
     * @param parentId the Id of parent
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ViewPermissionDTO> findChildrenByParentId(Long parentId) {
        log.debug("Request to get all ViewPermissions for parent is parentId");
        return viewPermissionMapper.toDto(viewPermissionRepository.findAllByParentId(parentId));
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, ViewPermissionCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<ViewPermission> root = q.from(ViewPermission.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, ViewPermissionCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<ViewPermission> root = q.from(ViewPermission.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, ViewPermissionCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<ViewPermission> q = cb.createCriteriaUpdate(ViewPermission.class);
        CriteriaQuery<ViewPermission> sq = cb.createQuery(ViewPermission.class);
        Root<ViewPermission> root = q.from(ViewPermission.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link ViewPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViewPermissionDTO> getOneByCriteria(ViewPermissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionRepository.findOne(specification).map(viewPermissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<ViewPermission> specification) {
        log.debug("count by specification : {}", specification);
        return viewPermissionRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link ViewPermissionDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> findBySpecification(Specification<ViewPermission> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return viewPermissionRepository.findAll(specification, page).map(viewPermissionMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(ViewPermissionCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<ViewPermission> root = q.from(ViewPermission.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getText() != null) {
            getAggregateAndGroupBy(criteria.getText(), "text", selectFields, groupByFields, cb, root);
        }
        if (criteria.getI18n() != null) {
            getAggregateAndGroupBy(criteria.getI18n(), "i_18_n", selectFields, groupByFields, cb, root);
        }
        if (criteria.getGroup() != null) {
            getAggregateAndGroupBy(criteria.getGroup(), "jhi_group", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLink() != null) {
            getAggregateAndGroupBy(criteria.getLink(), "jhi_link", selectFields, groupByFields, cb, root);
        }
        if (criteria.getExternalLink() != null) {
            getAggregateAndGroupBy(criteria.getExternalLink(), "external_link", selectFields, groupByFields, cb, root);
        }
        if (criteria.getTarget() != null) {
            getAggregateAndGroupBy(criteria.getTarget(), "target", selectFields, groupByFields, cb, root);
        }
        if (criteria.getIcon() != null) {
            getAggregateAndGroupBy(criteria.getIcon(), "icon", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDisabled() != null) {
            getAggregateAndGroupBy(criteria.getDisabled(), "disabled", selectFields, groupByFields, cb, root);
        }
        if (criteria.getHide() != null) {
            getAggregateAndGroupBy(criteria.getHide(), "hide", selectFields, groupByFields, cb, root);
        }
        if (criteria.getHideInBreadcrumb() != null) {
            getAggregateAndGroupBy(criteria.getHideInBreadcrumb(), "hide_in_breadcrumb", selectFields, groupByFields, cb, root);
        }
        if (criteria.getShortcut() != null) {
            getAggregateAndGroupBy(criteria.getShortcut(), "shortcut", selectFields, groupByFields, cb, root);
        }
        if (criteria.getShortcutRoot() != null) {
            getAggregateAndGroupBy(criteria.getShortcutRoot(), "shortcut_root", selectFields, groupByFields, cb, root);
        }
        if (criteria.getReuse() != null) {
            getAggregateAndGroupBy(criteria.getReuse(), "reuse", selectFields, groupByFields, cb, root);
        }
        if (criteria.getCode() != null) {
            getAggregateAndGroupBy(criteria.getCode(), "code", selectFields, groupByFields, cb, root);
        }
        if (criteria.getDescription() != null) {
            getAggregateAndGroupBy(criteria.getDescription(), "description", selectFields, groupByFields, cb, root);
        }
        if (criteria.getType() != null) {
            getAggregateAndGroupBy(criteria.getType(), "type", selectFields, groupByFields, cb, root);
        }
        if (criteria.getOrder() != null) {
            getAggregateAndGroupBy(criteria.getOrder(), "jhi_order", selectFields, groupByFields, cb, root);
        }
        if (criteria.getApiPermissionCodes() != null) {
            getAggregateAndGroupBy(criteria.getApiPermissionCodes(), "api_permission_codes", selectFields, groupByFields, cb, root);
        }
        if (criteria.getComponentFile() != null) {
            getAggregateAndGroupBy(criteria.getComponentFile(), "component_file", selectFields, groupByFields, cb, root);
        }
        if (criteria.getRedirect() != null) {
            getAggregateAndGroupBy(criteria.getRedirect(), "redirect", selectFields, groupByFields, cb, root);
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
