package com.begcode.demo.hibernate.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ResourceCategoryMapperTest {

    private ResourceCategoryMapper resourceCategoryMapper;

    @BeforeEach
    public void setUp() {
        resourceCategoryMapper = new ResourceCategoryMapperImpl();
    }
}
