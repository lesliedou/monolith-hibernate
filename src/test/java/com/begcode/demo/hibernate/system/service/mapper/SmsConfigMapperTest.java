package com.begcode.demo.hibernate.system.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class SmsConfigMapperTest {

    private SmsConfigMapper smsConfigMapper;

    @BeforeEach
    public void setUp() {
        smsConfigMapper = new SmsConfigMapperImpl();
    }
}
