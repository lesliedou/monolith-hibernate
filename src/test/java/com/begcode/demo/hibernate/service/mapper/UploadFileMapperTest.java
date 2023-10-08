package com.begcode.demo.hibernate.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class UploadFileMapperTest {

    private UploadFileMapper uploadFileMapper;

    @BeforeEach
    public void setUp() {
        uploadFileMapper = new UploadFileMapperImpl();
    }
}
