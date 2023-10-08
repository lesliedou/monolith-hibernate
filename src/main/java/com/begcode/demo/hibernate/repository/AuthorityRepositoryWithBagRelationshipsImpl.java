package com.begcode.demo.hibernate.repository.base;

import com.begcode.demo.hibernate.domain.Authority;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AuthorityRepositoryWithBagRelationshipsImpl implements AuthorityRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Authority> fetchBagRelationships(Optional<Authority> authority) {
        return authority.map(this::fetchViewPermissions).map(this::fetchApiPermissions);
    }

    @Override
    public Page<Authority> fetchBagRelationships(Page<Authority> authorities) {
        return new PageImpl<>(fetchBagRelationships(authorities.getContent()), authorities.getPageable(), authorities.getTotalElements());
    }

    @Override
    public List<Authority> fetchBagRelationships(List<Authority> authorities) {
        return Optional.of(authorities).map(this::fetchViewPermissions).map(this::fetchApiPermissions).orElse(Collections.emptyList());
    }

    Authority fetchViewPermissions(Authority result) {
        return entityManager
            .createQuery(
                "select authority from Authority authority left join fetch authority.viewPermissions where authority.id = :id",
                Authority.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Authority> fetchViewPermissions(List<Authority> authorities) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, authorities.size()).forEach(index -> order.put(authorities.get(index).getId(), index));
        List<Authority> result = entityManager
            .createQuery(
                "select authority from Authority authority left join fetch authority.viewPermissions where authority in :authorities",
                Authority.class
            )
            .setParameter("authorities", authorities)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Authority fetchApiPermissions(Authority result) {
        return entityManager
            .createQuery(
                "select authority from Authority authority left join fetch authority.apiPermissions where authority.id = :id",
                Authority.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Authority> fetchApiPermissions(List<Authority> authorities) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, authorities.size()).forEach(index -> order.put(authorities.get(index).getId(), index));
        List<Authority> result = entityManager
            .createQuery(
                "select authority from Authority authority left join fetch authority.apiPermissions where authority in :authorities",
                Authority.class
            )
            .setParameter("authorities", authorities)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
