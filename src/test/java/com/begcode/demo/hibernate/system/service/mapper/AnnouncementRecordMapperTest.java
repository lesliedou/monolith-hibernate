package com.begcode.demo.hibernate.system.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class AnnouncementRecordMapperTest {

    private AnnouncementRecordMapper announcementRecordMapper;

    @BeforeEach
    public void setUp() {
        announcementRecordMapper = new AnnouncementRecordMapperImpl();
    }
}
