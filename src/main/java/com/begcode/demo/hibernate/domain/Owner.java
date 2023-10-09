package com.begcode.demo.hibernate.domain;

import java.util.*;

public interface Owner {
    default String getOwnerEntityName() {
        return this.getClass().getSimpleName();
    }

    Long getId();

    default Map<String, Object> getExtData() {
        return new HashMap<>();
    }
}
