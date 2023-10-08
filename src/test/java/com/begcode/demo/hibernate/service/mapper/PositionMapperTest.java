package com.begcode.demo.hibernate.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class PositionMapperTest {

    private PositionMapper positionMapper;

    @BeforeEach
    public void setUp() {
        positionMapper = new PositionMapperImpl();
    }
}
