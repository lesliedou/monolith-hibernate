package com.begcode.demo.hibernate.system.web.rest;

import static com.begcode.demo.hibernate.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.begcode.demo.hibernate.IntegrationTest;
import com.begcode.demo.hibernate.domain.enumeration.MessageSendType;
import com.begcode.demo.hibernate.system.domain.SmsTemplate;
import com.begcode.demo.hibernate.system.repository.SmsTemplateRepository;
import com.begcode.demo.hibernate.system.service.dto.SmsTemplateDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsTemplateMapper;
import com.begcode.demo.hibernate.web.rest.TestUtil;
import com.begcode.demo.hibernate.web.rest.TestUtil;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SmsTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class SmsTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final MessageSendType DEFAULT_TYPE = MessageSendType.EMAIL;
    private static final MessageSendType UPDATED_TYPE = MessageSendType.SMS;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_TEST_JSON = "AAAAAAAAAA";
    private static final String UPDATED_TEST_JSON = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;
    private static final Long SMALLER_CREATED_BY = 1L - 1L;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Long DEFAULT_LAST_MODIFIED_BY = 1L;
    private static final Long UPDATED_LAST_MODIFIED_BY = 2L;
    private static final Long SMALLER_LAST_MODIFIED_BY = 1L - 1L;

    private static final ZonedDateTime DEFAULT_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_LAST_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String ENTITY_API_URL = "/api/sms-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSmsTemplateMockMvc;

    private SmsTemplate smsTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmsTemplate createEntity(EntityManager em) {
        SmsTemplate smsTemplate = new SmsTemplate()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .testJson(DEFAULT_TEST_JSON)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return smsTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmsTemplate createUpdatedEntity(EntityManager em) {
        SmsTemplate smsTemplate = new SmsTemplate()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .testJson(UPDATED_TEST_JSON)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return smsTemplate;
    }

    @BeforeEach
    public void initTest() {
        smsTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createSmsTemplate() throws Exception {
        int databaseSizeBeforeCreate = smsTemplateRepository.findAll().size();
        // Create the SmsTemplate
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);
        restSmsTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SmsTemplate testSmsTemplate = smsTemplateList.get(smsTemplateList.size() - 1);
        assertThat(testSmsTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSmsTemplate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSmsTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSmsTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testSmsTemplate.getTestJson()).isEqualTo(DEFAULT_TEST_JSON);
        assertThat(testSmsTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSmsTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSmsTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSmsTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSmsTemplateWithExistingId() throws Exception {
        // Create the SmsTemplate with an existing ID
        smsTemplate.setId(1L);
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);

        int databaseSizeBeforeCreate = smsTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSmsTemplates() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList
        restSmsTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].testJson").value(hasItem(DEFAULT_TEST_JSON)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getSmsTemplate() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get the smsTemplate
        restSmsTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, smsTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(smsTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.testJson").value(DEFAULT_TEST_JSON))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY.intValue()))
            .andExpect(jsonPath("$.lastModifiedDate").value(sameInstant(DEFAULT_LAST_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getSmsTemplatesByIdFiltering() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        Long id = smsTemplate.getId();

        defaultSmsTemplateShouldBeFound("id.equals=" + id);
        defaultSmsTemplateShouldNotBeFound("id.notEquals=" + id);

        defaultSmsTemplateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSmsTemplateShouldNotBeFound("id.greaterThan=" + id);

        defaultSmsTemplateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSmsTemplateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where name equals to DEFAULT_NAME
        defaultSmsTemplateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the smsTemplateList where name equals to UPDATED_NAME
        defaultSmsTemplateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSmsTemplateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the smsTemplateList where name equals to UPDATED_NAME
        defaultSmsTemplateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where name is not null
        defaultSmsTemplateShouldBeFound("name.specified=true");

        // Get all the smsTemplateList where name is null
        defaultSmsTemplateShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByNameContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where name contains DEFAULT_NAME
        defaultSmsTemplateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the smsTemplateList where name contains UPDATED_NAME
        defaultSmsTemplateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where name does not contain DEFAULT_NAME
        defaultSmsTemplateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the smsTemplateList where name does not contain UPDATED_NAME
        defaultSmsTemplateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where code equals to DEFAULT_CODE
        defaultSmsTemplateShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the smsTemplateList where code equals to UPDATED_CODE
        defaultSmsTemplateShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSmsTemplateShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the smsTemplateList where code equals to UPDATED_CODE
        defaultSmsTemplateShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where code is not null
        defaultSmsTemplateShouldBeFound("code.specified=true");

        // Get all the smsTemplateList where code is null
        defaultSmsTemplateShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCodeContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where code contains DEFAULT_CODE
        defaultSmsTemplateShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the smsTemplateList where code contains UPDATED_CODE
        defaultSmsTemplateShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where code does not contain DEFAULT_CODE
        defaultSmsTemplateShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the smsTemplateList where code does not contain UPDATED_CODE
        defaultSmsTemplateShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where type equals to DEFAULT_TYPE
        defaultSmsTemplateShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the smsTemplateList where type equals to UPDATED_TYPE
        defaultSmsTemplateShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSmsTemplateShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the smsTemplateList where type equals to UPDATED_TYPE
        defaultSmsTemplateShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where type is not null
        defaultSmsTemplateShouldBeFound("type.specified=true");

        // Get all the smsTemplateList where type is null
        defaultSmsTemplateShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where content equals to DEFAULT_CONTENT
        defaultSmsTemplateShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the smsTemplateList where content equals to UPDATED_CONTENT
        defaultSmsTemplateShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByContentIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultSmsTemplateShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the smsTemplateList where content equals to UPDATED_CONTENT
        defaultSmsTemplateShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where content is not null
        defaultSmsTemplateShouldBeFound("content.specified=true");

        // Get all the smsTemplateList where content is null
        defaultSmsTemplateShouldNotBeFound("content.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByContentContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where content contains DEFAULT_CONTENT
        defaultSmsTemplateShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the smsTemplateList where content contains UPDATED_CONTENT
        defaultSmsTemplateShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByContentNotContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where content does not contain DEFAULT_CONTENT
        defaultSmsTemplateShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the smsTemplateList where content does not contain UPDATED_CONTENT
        defaultSmsTemplateShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTestJsonIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where testJson equals to DEFAULT_TEST_JSON
        defaultSmsTemplateShouldBeFound("testJson.equals=" + DEFAULT_TEST_JSON);

        // Get all the smsTemplateList where testJson equals to UPDATED_TEST_JSON
        defaultSmsTemplateShouldNotBeFound("testJson.equals=" + UPDATED_TEST_JSON);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTestJsonIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where testJson in DEFAULT_TEST_JSON or UPDATED_TEST_JSON
        defaultSmsTemplateShouldBeFound("testJson.in=" + DEFAULT_TEST_JSON + "," + UPDATED_TEST_JSON);

        // Get all the smsTemplateList where testJson equals to UPDATED_TEST_JSON
        defaultSmsTemplateShouldNotBeFound("testJson.in=" + UPDATED_TEST_JSON);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTestJsonIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where testJson is not null
        defaultSmsTemplateShouldBeFound("testJson.specified=true");

        // Get all the smsTemplateList where testJson is null
        defaultSmsTemplateShouldNotBeFound("testJson.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTestJsonContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where testJson contains DEFAULT_TEST_JSON
        defaultSmsTemplateShouldBeFound("testJson.contains=" + DEFAULT_TEST_JSON);

        // Get all the smsTemplateList where testJson contains UPDATED_TEST_JSON
        defaultSmsTemplateShouldNotBeFound("testJson.contains=" + UPDATED_TEST_JSON);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByTestJsonNotContainsSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where testJson does not contain DEFAULT_TEST_JSON
        defaultSmsTemplateShouldNotBeFound("testJson.doesNotContain=" + DEFAULT_TEST_JSON);

        // Get all the smsTemplateList where testJson does not contain UPDATED_TEST_JSON
        defaultSmsTemplateShouldBeFound("testJson.doesNotContain=" + UPDATED_TEST_JSON);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdBy equals to DEFAULT_CREATED_BY
        defaultSmsTemplateShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the smsTemplateList where createdBy equals to UPDATED_CREATED_BY
        defaultSmsTemplateShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSmsTemplateShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the smsTemplateList where createdBy equals to UPDATED_CREATED_BY
        defaultSmsTemplateShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdBy is not null
        defaultSmsTemplateShouldBeFound("createdBy.specified=true");

        // Get all the smsTemplateList where createdBy is null
        defaultSmsTemplateShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdBy is greater than or equal to DEFAULT_CREATED_BY
        defaultSmsTemplateShouldBeFound("createdBy.greaterThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the smsTemplateList where createdBy is greater than or equal to UPDATED_CREATED_BY
        defaultSmsTemplateShouldNotBeFound("createdBy.greaterThanOrEqual=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdBy is less than or equal to DEFAULT_CREATED_BY
        defaultSmsTemplateShouldBeFound("createdBy.lessThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the smsTemplateList where createdBy is less than or equal to SMALLER_CREATED_BY
        defaultSmsTemplateShouldNotBeFound("createdBy.lessThanOrEqual=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedByIsLessThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdBy is less than DEFAULT_CREATED_BY
        defaultSmsTemplateShouldNotBeFound("createdBy.lessThan=" + DEFAULT_CREATED_BY);

        // Get all the smsTemplateList where createdBy is less than UPDATED_CREATED_BY
        defaultSmsTemplateShouldBeFound("createdBy.lessThan=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdBy is greater than DEFAULT_CREATED_BY
        defaultSmsTemplateShouldNotBeFound("createdBy.greaterThan=" + DEFAULT_CREATED_BY);

        // Get all the smsTemplateList where createdBy is greater than SMALLER_CREATED_BY
        defaultSmsTemplateShouldBeFound("createdBy.greaterThan=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSmsTemplateShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the smsTemplateList where createdDate equals to UPDATED_CREATED_DATE
        defaultSmsTemplateShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSmsTemplateShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the smsTemplateList where createdDate equals to UPDATED_CREATED_DATE
        defaultSmsTemplateShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdDate is not null
        defaultSmsTemplateShouldBeFound("createdDate.specified=true");

        // Get all the smsTemplateList where createdDate is null
        defaultSmsTemplateShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultSmsTemplateShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the smsTemplateList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultSmsTemplateShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultSmsTemplateShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the smsTemplateList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultSmsTemplateShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdDate is less than DEFAULT_CREATED_DATE
        defaultSmsTemplateShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the smsTemplateList where createdDate is less than UPDATED_CREATED_DATE
        defaultSmsTemplateShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultSmsTemplateShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the smsTemplateList where createdDate is greater than SMALLER_CREATED_DATE
        defaultSmsTemplateShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSmsTemplateShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the smsTemplateList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSmsTemplateShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSmsTemplateShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the smsTemplateList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSmsTemplateShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedBy is not null
        defaultSmsTemplateShouldBeFound("lastModifiedBy.specified=true");

        // Get all the smsTemplateList where lastModifiedBy is null
        defaultSmsTemplateShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedBy is greater than or equal to DEFAULT_LAST_MODIFIED_BY
        defaultSmsTemplateShouldBeFound("lastModifiedBy.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the smsTemplateList where lastModifiedBy is greater than or equal to UPDATED_LAST_MODIFIED_BY
        defaultSmsTemplateShouldNotBeFound("lastModifiedBy.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedBy is less than or equal to DEFAULT_LAST_MODIFIED_BY
        defaultSmsTemplateShouldBeFound("lastModifiedBy.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the smsTemplateList where lastModifiedBy is less than or equal to SMALLER_LAST_MODIFIED_BY
        defaultSmsTemplateShouldNotBeFound("lastModifiedBy.lessThanOrEqual=" + SMALLER_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedByIsLessThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedBy is less than DEFAULT_LAST_MODIFIED_BY
        defaultSmsTemplateShouldNotBeFound("lastModifiedBy.lessThan=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the smsTemplateList where lastModifiedBy is less than UPDATED_LAST_MODIFIED_BY
        defaultSmsTemplateShouldBeFound("lastModifiedBy.lessThan=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedBy is greater than DEFAULT_LAST_MODIFIED_BY
        defaultSmsTemplateShouldNotBeFound("lastModifiedBy.greaterThan=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the smsTemplateList where lastModifiedBy is greater than SMALLER_LAST_MODIFIED_BY
        defaultSmsTemplateShouldBeFound("lastModifiedBy.greaterThan=" + SMALLER_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the smsTemplateList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the smsTemplateList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedDate is not null
        defaultSmsTemplateShouldBeFound("lastModifiedDate.specified=true");

        // Get all the smsTemplateList where lastModifiedDate is null
        defaultSmsTemplateShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedDate is greater than or equal to DEFAULT_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldBeFound("lastModifiedDate.greaterThanOrEqual=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the smsTemplateList where lastModifiedDate is greater than or equal to UPDATED_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldNotBeFound("lastModifiedDate.greaterThanOrEqual=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedDate is less than or equal to DEFAULT_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldBeFound("lastModifiedDate.lessThanOrEqual=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the smsTemplateList where lastModifiedDate is less than or equal to SMALLER_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldNotBeFound("lastModifiedDate.lessThanOrEqual=" + SMALLER_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedDate is less than DEFAULT_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldNotBeFound("lastModifiedDate.lessThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the smsTemplateList where lastModifiedDate is less than UPDATED_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldBeFound("lastModifiedDate.lessThan=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllSmsTemplatesByLastModifiedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList where lastModifiedDate is greater than DEFAULT_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldNotBeFound("lastModifiedDate.greaterThan=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the smsTemplateList where lastModifiedDate is greater than SMALLER_LAST_MODIFIED_DATE
        defaultSmsTemplateShouldBeFound("lastModifiedDate.greaterThan=" + SMALLER_LAST_MODIFIED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSmsTemplateShouldBeFound(String filter) throws Exception {
        restSmsTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].testJson").value(hasItem(DEFAULT_TEST_JSON)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY.intValue())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(sameInstant(DEFAULT_LAST_MODIFIED_DATE))));

        // Check, that the count call also returns 1
        restSmsTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSmsTemplateShouldNotBeFound(String filter) throws Exception {
        restSmsTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSmsTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSmsTemplate() throws Exception {
        // Get the smsTemplate
        restSmsTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSmsTemplate() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();

        // Update the smsTemplate
        SmsTemplate updatedSmsTemplate = smsTemplateRepository.findById(smsTemplate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSmsTemplate are not directly saved in db
        em.detach(updatedSmsTemplate);
        updatedSmsTemplate
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .testJson(UPDATED_TEST_JSON)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(updatedSmsTemplate);

        restSmsTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, smsTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
        SmsTemplate testSmsTemplate = smsTemplateList.get(smsTemplateList.size() - 1);
        assertThat(testSmsTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSmsTemplate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSmsTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSmsTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testSmsTemplate.getTestJson()).isEqualTo(UPDATED_TEST_JSON);
        assertThat(testSmsTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSmsTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSmsTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSmsTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();
        smsTemplate.setId(count.incrementAndGet());

        // Create the SmsTemplate
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSmsTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, smsTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();
        smsTemplate.setId(count.incrementAndGet());

        // Create the SmsTemplate
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSmsTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();
        smsTemplate.setId(count.incrementAndGet());

        // Create the SmsTemplate
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSmsTemplateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSmsTemplateWithPatch() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();

        // Update the smsTemplate using partial update
        SmsTemplate partialUpdatedSmsTemplate = new SmsTemplate();
        partialUpdatedSmsTemplate.setId(smsTemplate.getId());

        partialUpdatedSmsTemplate.code(UPDATED_CODE).type(UPDATED_TYPE).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSmsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSmsTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSmsTemplate))
            )
            .andExpect(status().isOk());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
        SmsTemplate testSmsTemplate = smsTemplateList.get(smsTemplateList.size() - 1);
        assertThat(testSmsTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSmsTemplate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSmsTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSmsTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testSmsTemplate.getTestJson()).isEqualTo(DEFAULT_TEST_JSON);
        assertThat(testSmsTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSmsTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSmsTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSmsTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSmsTemplateWithPatch() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();

        // Update the smsTemplate using partial update
        SmsTemplate partialUpdatedSmsTemplate = new SmsTemplate();
        partialUpdatedSmsTemplate.setId(smsTemplate.getId());

        partialUpdatedSmsTemplate
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .testJson(UPDATED_TEST_JSON)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restSmsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSmsTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSmsTemplate))
            )
            .andExpect(status().isOk());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
        SmsTemplate testSmsTemplate = smsTemplateList.get(smsTemplateList.size() - 1);
        assertThat(testSmsTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSmsTemplate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSmsTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSmsTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testSmsTemplate.getTestJson()).isEqualTo(UPDATED_TEST_JSON);
        assertThat(testSmsTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSmsTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSmsTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSmsTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();
        smsTemplate.setId(count.incrementAndGet());

        // Create the SmsTemplate
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSmsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, smsTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();
        smsTemplate.setId(count.incrementAndGet());

        // Create the SmsTemplate
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSmsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();
        smsTemplate.setId(count.incrementAndGet());

        // Create the SmsTemplate
        SmsTemplateDTO smsTemplateDTO = smsTemplateMapper.toDto(smsTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSmsTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(smsTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSmsTemplate() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        int databaseSizeBeforeDelete = smsTemplateRepository.findAll().size();

        // Delete the smsTemplate
        restSmsTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, smsTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
