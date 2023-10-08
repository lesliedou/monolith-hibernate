package com.begcode.demo.hibernate.oss.service.base;

import cn.hutool.core.bean.BeanUtil;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import cn.xuyanwu.spring.file.storage.platform.*;
import com.begcode.demo.hibernate.SpringBootUtil;
import com.begcode.demo.hibernate.oss.domain.OssConfig;
import com.begcode.demo.hibernate.oss.repository.OssConfigRepository;
import com.begcode.demo.hibernate.oss.service.dto.OssConfigDTO;
import com.begcode.demo.hibernate.oss.service.mapper.OssConfigMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.oss.domain.OssConfig}.
 */
public class OssConfigBaseService {

    private final Logger log = LoggerFactory.getLogger(OssConfigBaseService.class);

    protected final FileStorageService fileStorageService;

    protected final OssConfigRepository ossConfigRepository;

    protected final CacheManager cacheManager;

    protected final OssConfigMapper ossConfigMapper;

    public OssConfigBaseService(
        FileStorageService fileStorageService,
        OssConfigRepository ossConfigRepository,
        CacheManager cacheManager,
        OssConfigMapper ossConfigMapper
    ) {
        this.fileStorageService = fileStorageService;
        this.ossConfigRepository = ossConfigRepository;
        this.cacheManager = cacheManager;
        this.ossConfigMapper = ossConfigMapper;
    }

    /**
     * Save a ossConfig.
     *
     * @param ossConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public OssConfigDTO save(OssConfigDTO ossConfigDTO) {
        log.debug("Request to save OssConfig : {}", ossConfigDTO);
        OssConfig ossConfig = ossConfigMapper.toEntity(ossConfigDTO);
        ossConfig = ossConfigRepository.save(ossConfig);
        return ossConfigMapper.toDto(ossConfig);
    }

    /**
     * Update a ossConfig.
     *
     * @param ossConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public OssConfigDTO update(OssConfigDTO ossConfigDTO) {
        log.debug("Request to update OssConfig : {}", ossConfigDTO);

        OssConfig ossConfig = ossConfigMapper.toEntity(ossConfigDTO);
        ossConfig = ossConfigRepository.save(ossConfig);
        return ossConfigMapper.toDto(ossConfig);
    }

    /**
     * Partially update a ossConfig.
     *
     * @param ossConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OssConfigDTO> partialUpdate(OssConfigDTO ossConfigDTO) {
        log.debug("Request to partially update OssConfig : {}", ossConfigDTO);

        return ossConfigRepository
            .findById(ossConfigDTO.getId())
            .map(existingOssConfig -> {
                ossConfigMapper.partialUpdate(existingOssConfig, ossConfigDTO);

                return existingOssConfig;
            })
            .map(ossConfigRepository::save)
            .map(ossConfigMapper::toDto);
    }

    /**
     * Get all the ossConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OssConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OssConfigs");
        return ossConfigRepository.findAll(pageable).map(ossConfigMapper::toDto);
    }

    /**
     * Get one ossConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OssConfigDTO> findOne(Long id) {
        log.debug("Request to get OssConfig : {}", id);
        return ossConfigRepository.findById(id).map(ossConfigMapper::toDto);
    }

    /**
     * Delete the ossConfig by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete OssConfig : {}", id);

        ossConfigRepository.deleteById(id);
    }

    public void initPlatforms() {
        List<OssConfig> ossConfigs = ossConfigRepository.findAll(
            Example.of(new OssConfig().enabled(true), ExampleMatcher.matching().withIgnoreNullValues())
        );
        //获得存储平台 List
        CopyOnWriteArrayList<FileStorage> storageList = fileStorageService.getFileStorageList();
        for (FileStorage storage : storageList) {
            storage.close();
        }
        storageList.clear();
        if (CollectionUtils.isEmpty(ossConfigs)) {
            LocalPlusFileStorage storage = new LocalPlusFileStorage();
            String storagePath = "data/";
            String domain = "";
            String basePath = "/upload/";
            storagePath = SpringBootUtil.getApplicationPathEndWithSeparator() + storagePath;
            storage.setStoragePath(storagePath);
            storage.setBasePath(basePath);
            storage.setDomain(domain);
            storage.setPlatform("local");
            storageList.add(storage);
        }
        for (OssConfig ossConfig : ossConfigs) {
            switch (ossConfig.getProvider()) {
                case LOCAL -> {
                    LocalPlusFileStorage storage = new LocalPlusFileStorage();
                    ObjectMapper objectMapper = new ObjectMapper();
                    // 将json字符串转为对象
                    String json = ossConfig.getConfigData();
                    try {
                        Map map = objectMapper.readValue(json, Map.class);
                        String storagePath = (String) map.get("storagePath");
                        if (StringUtils.isBlank(storagePath)) {
                            storagePath = "data/";
                        }
                        String domain = (String) map.get("domain");
                        if (StringUtils.isBlank(domain)) {
                            domain = "";
                        }
                        String basePath = (String) map.get("basePath");
                        if (StringUtils.isBlank(basePath)) {
                            basePath = "/upload/";
                        }
                        if (!storagePath.startsWith("/")) {
                            storagePath = SpringBootUtil.getApplicationPathEndWithSeparator() + storagePath;
                        }
                        storage.setStoragePath(storagePath);
                        storage.setBasePath(basePath);
                        storage.setDomain(domain);
                        storage.setPlatform(ossConfig.getPlatform());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    storageList.add(storage);
                }
                case ALI -> {
                    AliyunOssFileStorage aliyunOssFileStorage = new AliyunOssFileStorage();
                    storageList.add(aliyunOssFileStorage);
                }
                case MINIO -> {
                    MinIOFileStorage minIOFileStorage = new MinIOFileStorage();
                    storageList.add(minIOFileStorage);
                }
                case QINIU -> {
                    QiniuKodoFileStorage qiniuKodoFileStorage = new QiniuKodoFileStorage();
                    storageList.add(qiniuKodoFileStorage);
                }
                case TENCENT -> {
                    TencentCosFileStorage tencentCosFileStorage = new TencentCosFileStorage();
                    storageList.add(tencentCosFileStorage);
                }
                default -> log.warn("未知的存储平台:{}", ossConfig.getProvider());
            }
        }
    }

    /**
     * Get one ossConfig by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<OssConfigDTO> findOneByExample(Example<OssConfig> example) {
        log.debug("Request to get OssConfig by example");
        return ossConfigRepository.findAll(example).stream().findFirst().map(ossConfigMapper::toDto);
    }

    /**
     * Get all the ossConfigs by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<OssConfigDTO> findAllByExample(Example<OssConfig> example, Pageable pageable) {
        log.debug("Request to get OssConfig by example");
        return ossConfigRepository.findAll(example, pageable).map(ossConfigMapper::toDto);
    }

    /**
     * Update specified field by ossConfig
     */
    public void updateBatch(OssConfigDTO changeOssConfigDTO, List<String> fieldNames, List<Long> ids) {
        ossConfigRepository
            .findAllById(ids)
            .stream()
            .peek(ossConfig ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(ossConfig, fieldName, BeanUtil.getFieldValue(changeOssConfigDTO, fieldName))
                )
            )
            .forEach(ossConfigRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
