package com.begcode.demo.hibernate.taskjob.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class TaskJobConfigMapperTest {

    private TaskJobConfigMapper taskJobConfigMapper;

    @BeforeEach
    public void setUp() {
        taskJobConfigMapper = new TaskJobConfigMapperImpl();
    }
}
