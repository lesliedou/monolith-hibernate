package com.begcode.demo.hibernate.service;

import static tech.jhipster.service.AggregateUtil.buildAggregate;
import static tech.jhipster.service.AggregateUtil.buildGroupBy;

import com.begcode.demo.hibernate.domain.*; // for static metamodels
import com.begcode.demo.hibernate.domain.User;
import com.begcode.demo.hibernate.repository.UserRepository;
import com.begcode.demo.hibernate.service.criteria.UserCriteria;
import com.begcode.demo.hibernate.service.dto.UserDTO;
import com.begcode.demo.hibernate.service.mapper.UserMapper;
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
 * 用于对数据库中的{@link User}实体执行复杂查询的Service。
 * 主要输入是一个{@link UserCriteria}，它被转换为{@link Specification}，
 * 所有字段过滤器都将应用到表达式中。
 * 它返回满足条件的{@link UserDTO}列表{@link List} 或 {@link UserDTO} 的分页列表 {@link Page}。
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);

    protected final UserRepository userRepository;

    protected final EntityManager em;

    protected final UserMapper userMapper;

    public UserQueryService(UserRepository userRepository, EntityManager em, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.em = em;
        this.userMapper = userMapper;
    }

    /**
     * Return a {@link List} of {@link UserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserDTO> findByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userMapper.toDto(userRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, page).map(userMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.count(specification);
    }

    /**
     * Function to convert {@link UserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), User_.id));
            }
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())), User_.id)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                User_.id
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                User_.createdBy
                            )
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildRangeSpecification(
                                (LongFilter) new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),
                                User_.lastModifiedBy
                            )
                        );
                } else {
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), User_.login)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), User_.firstName)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), User_.lastName)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), User_.email)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), User_.mobile)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), User_.langKey)
                        );
                    specification =
                        CriteriaUtil.build(
                            true,
                            specification,
                            buildStringSpecification(new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()), User_.imageUrl)
                        );
                }
            } else {
                if (criteria.getId() != null) {
                    specification =
                        CriteriaUtil.build(criteria.getUseOr(), specification, buildRangeSpecification(criteria.getId(), User_.id));
                }
                if (criteria.getLogin() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLogin(), User_.login));
                }
                if (criteria.getFirstName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFirstName(), User_.firstName));
                }
                if (criteria.getLastName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLastName(), User_.lastName));
                }
                if (criteria.getEmail() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getEmail(), User_.email));
                }
                if (criteria.getMobile() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getMobile(), User_.mobile));
                }
                if (criteria.getBirthday() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getBirthday(), User_.birthday));
                }
                if (criteria.getActivated() != null) {
                    specification = specification.and(buildSpecification(criteria.getActivated(), User_.activated));
                }
                if (criteria.getLangKey() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLangKey(), User_.langKey));
                }
                if (criteria.getImageUrl() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getImageUrl(), User_.imageUrl));
                }
                if (criteria.getCreatedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), User_.createdBy));
                }
                if (criteria.getCreatedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), User_.createdDate));
                }
                if (criteria.getLastModifiedBy() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedBy(), User_.lastModifiedBy));
                }
                if (criteria.getLastModifiedDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), User_.lastModifiedDate));
                }
                if (criteria.getDepartmentId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getDepartmentId(),
                                root -> root.join(User_.department, JoinType.LEFT).get(Department_.id)
                            )
                        );
                }
                if (criteria.getDepartmentName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getDepartmentName(),
                                root -> root.join(User_.department, JoinType.LEFT).get(Department_.name)
                            )
                        );
                }
                if (criteria.getPositionId() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(criteria.getPositionId(), root -> root.join(User_.position, JoinType.LEFT).get(Position_.id))
                        );
                }
                if (criteria.getPositionName() != null) {
                    specification =
                        CriteriaUtil.build(
                            criteria.getUseOr(),
                            specification,
                            buildSpecification(
                                criteria.getPositionName(),
                                root -> root.join(User_.position, JoinType.LEFT).get(Position_.name)
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
                                root -> root.join(User_.authorities, JoinType.LEFT).get(Authority_.id)
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
                                root -> root.join(User_.authorities, JoinType.LEFT).get(Authority_.name)
                            )
                        );
                }
            }
        }
        return specification;
    }

    public <T> List<T> getFieldByCriteria(Class<T> clazz, String fieldName, Boolean distinct, UserCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<User> root = q.from(User.class);
        Selection<T> selection = root.get(fieldName);
        q.distinct(distinct);
        q.select(selection);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        q.where(criteriaPredicate);
        return em.createQuery(q).getResultList();
    }

    public long countByFieldNameAndCriteria(String fieldName, Boolean distinct, UserCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<User> root = q.from(User.class);
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
    public boolean updateBySpecifield(String fieldName, Object value, UserCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<User> q = cb.createCriteriaUpdate(User.class);
        CriteriaQuery<User> sq = cb.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.set(root.get(fieldName), value).where(createSpecification(criteria).toPredicate(root, sq, cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * Return a {@link UserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> getOneByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<User> specification = createSpecification(criteria);
        return userRepository.findOne(specification).map(userMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countBySpecification(Specification<User> specification) {
        log.debug("count by specification : {}", specification);
        return userRepository.count(specification);
    }

    /**
     * Return a {@link Page} of {@link UserDTO} which matches the criteria from the database.
     * @param specification The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> findBySpecification(Specification<User> specification, Pageable page) {
        log.debug("find by criteria : {}, page: {}", specification, page);
        return userRepository.findAll(specification, page).map(userMapper::toDto);
    }

    public List<Map<String, Object>> statsByAggregateCriteria(UserCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createQuery(Tuple.class);
        Root<User> root = q.from(User.class);
        List<Selection<?>> selectFields = new ArrayList<>();
        List<Expression<?>> groupByFields = new ArrayList<>();
        if (criteria.getId() != null) {
            getAggregateAndGroupBy(criteria.getId(), "id", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLogin() != null) {
            getAggregateAndGroupBy(criteria.getLogin(), "login", selectFields, groupByFields, cb, root);
        }
        if (criteria.getFirstName() != null) {
            getAggregateAndGroupBy(criteria.getFirstName(), "first_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLastName() != null) {
            getAggregateAndGroupBy(criteria.getLastName(), "last_name", selectFields, groupByFields, cb, root);
        }
        if (criteria.getEmail() != null) {
            getAggregateAndGroupBy(criteria.getEmail(), "email", selectFields, groupByFields, cb, root);
        }
        if (criteria.getMobile() != null) {
            getAggregateAndGroupBy(criteria.getMobile(), "mobile", selectFields, groupByFields, cb, root);
        }
        if (criteria.getBirthday() != null) {
            getAggregateAndGroupBy(criteria.getBirthday(), "birthday", selectFields, groupByFields, cb, root);
        }
        if (criteria.getActivated() != null) {
            getAggregateAndGroupBy(criteria.getActivated(), "activated", selectFields, groupByFields, cb, root);
        }
        if (criteria.getLangKey() != null) {
            getAggregateAndGroupBy(criteria.getLangKey(), "lang_key", selectFields, groupByFields, cb, root);
        }
        if (criteria.getImageUrl() != null) {
            getAggregateAndGroupBy(criteria.getImageUrl(), "image_url", selectFields, groupByFields, cb, root);
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
