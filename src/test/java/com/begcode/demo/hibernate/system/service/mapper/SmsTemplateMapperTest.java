package com.begcode.demo.hibernate.system.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class SmsTemplateMapperTest {

    private SmsTemplateMapper smsTemplateMapper;

    @BeforeEach
    public void setUp() {
        smsTemplateMapper = new SmsTemplateMapperImpl();
    }
}
