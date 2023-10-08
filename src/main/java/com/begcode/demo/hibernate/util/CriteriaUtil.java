package com.begcode.demo.hibernate.util;

import org.springframework.data.jpa.domain.Specification;

public class CriteriaUtil {

    public static <ENTITY> Specification<ENTITY> build(Boolean useOr, Specification<ENTITY> specification, Specification<ENTITY> field) {
        if (useOr) {
            return specification.or(field);
        } else {
            return specification.and(field);
        }
    }
}
