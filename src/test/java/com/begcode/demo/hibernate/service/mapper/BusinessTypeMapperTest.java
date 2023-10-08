package com.begcode.demo.hibernate.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class BusinessTypeMapperTest {

    private BusinessTypeMapper businessTypeMapper;

    @BeforeEach
    public void setUp() {
        businessTypeMapper = new BusinessTypeMapperImpl();
    }
}
