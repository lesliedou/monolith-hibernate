package com.begcode.demo.hibernate.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ApiPermissionMapperTest {

    private ApiPermissionMapper apiPermissionMapper;

    @BeforeEach
    public void setUp() {
        apiPermissionMapper = new ApiPermissionMapperImpl();
    }
}
