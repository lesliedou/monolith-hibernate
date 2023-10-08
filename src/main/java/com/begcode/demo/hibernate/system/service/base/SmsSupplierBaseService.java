package com.begcode.demo.hibernate.system.service.base;

import cn.hutool.core.bean.BeanUtil;
import com.begcode.demo.hibernate.system.domain.SmsSupplier;
import com.begcode.demo.hibernate.system.repository.SmsSupplierRepository;
import com.begcode.demo.hibernate.system.service.dto.SmsSupplierDTO;
import com.begcode.demo.hibernate.system.service.mapper.SmsSupplierMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.sms4j.aliyun.config.AlibabaConfig;
import org.dromara.sms4j.comm.enumerate.SupplierType;
import org.dromara.sms4j.core.config.SupplierFactory;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.yunpian.config.YunpianConfig;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.system.domain.SmsSupplier}.
 */
public class SmsSupplierBaseService {

    private final Logger log = LoggerFactory.getLogger(SmsSupplierBaseService.class);

    protected final SmsSupplierRepository smsSupplierRepository;

    protected final CacheManager cacheManager;

    protected final SmsSupplierMapper smsSupplierMapper;

    public SmsSupplierBaseService(
        SmsSupplierRepository smsSupplierRepository,
        CacheManager cacheManager,
        SmsSupplierMapper smsSupplierMapper
    ) {
        this.smsSupplierRepository = smsSupplierRepository;
        this.cacheManager = cacheManager;
        this.smsSupplierMapper = smsSupplierMapper;
    }

    /**
     * Save a smsSupplier.
     *
     * @param smsSupplierDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsSupplierDTO save(SmsSupplierDTO smsSupplierDTO) {
        log.debug("Request to save SmsSupplier : {}", smsSupplierDTO);
        SmsSupplier smsSupplier = smsSupplierMapper.toEntity(smsSupplierDTO);
        smsSupplier = smsSupplierRepository.save(smsSupplier);
        return smsSupplierMapper.toDto(smsSupplier);
    }

    /**
     * Update a smsSupplier.
     *
     * @param smsSupplierDTO the entity to save.
     * @return the persisted entity.
     */
    public SmsSupplierDTO update(SmsSupplierDTO smsSupplierDTO) {
        log.debug("Request to update SmsSupplier : {}", smsSupplierDTO);

        SmsSupplier smsSupplier = smsSupplierMapper.toEntity(smsSupplierDTO);
        smsSupplier = smsSupplierRepository.save(smsSupplier);
        return smsSupplierMapper.toDto(smsSupplier);
    }

    /**
     * Partially update a smsSupplier.
     *
     * @param smsSupplierDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SmsSupplierDTO> partialUpdate(SmsSupplierDTO smsSupplierDTO) {
        log.debug("Request to partially update SmsSupplier : {}", smsSupplierDTO);

        return smsSupplierRepository
            .findById(smsSupplierDTO.getId())
            .map(existingSmsSupplier -> {
                smsSupplierMapper.partialUpdate(existingSmsSupplier, smsSupplierDTO);

                return existingSmsSupplier;
            })
            .map(smsSupplierRepository::save)
            .map(smsSupplierMapper::toDto);
    }

    /**
     * Get all the smsSuppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsSupplierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SmsSuppliers");
        return smsSupplierRepository.findAll(pageable).map(smsSupplierMapper::toDto);
    }

    /**
     * Get one smsSupplier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SmsSupplierDTO> findOne(Long id) {
        log.debug("Request to get SmsSupplier : {}", id);
        return smsSupplierRepository.findById(id).map(smsSupplierMapper::toDto);
    }

    /**
     * Delete the smsSupplier by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete SmsSupplier : {}", id);

        smsSupplierRepository.deleteById(id);
    }

    public void initService() {
        List<SmsSupplier> smsSuppliers = smsSupplierRepository.findAll(
            Example.of(new SmsSupplier().enabled(true), ExampleMatcher.matching().withIgnoreNullValues())
        );
        ObjectMapper objectMapper = new ObjectMapper();
        if (CollectionUtils.isNotEmpty(smsSuppliers)) {
            smsSuppliers.forEach(smsSupplier -> {
                switch (smsSupplier.getProvider()) {
                    case ALIBABA:
                        try {
                            Map<String, String> configMap = objectMapper.readValue(
                                smsSupplier.getConfigData(),
                                new TypeReference<Map<String, String>>() {}
                            );
                            AlibabaConfig alibabaConfig = SupplierFactory.getAlibabaConfig();
                            alibabaConfig.setAccessKeyId(configMap.get("accessKeyId"));
                            alibabaConfig.setAccessKeySecret(configMap.get("accessKeySecret"));
                            alibabaConfig.setSignature(configMap.get("signature"));
                            alibabaConfig.setRequestUrl(configMap.get("requestUrl"));
                            alibabaConfig.setAction(StringUtils.defaultIfBlank(configMap.get("action"), "SendSms"));
                            alibabaConfig.setVersion(StringUtils.defaultIfBlank(configMap.get("version"), "2017-05-25"));
                            alibabaConfig.setRegionId(StringUtils.defaultIfBlank(configMap.get("regionId"), "cn-hangzhou"));
                            SmsFactory.refresh(SupplierType.ALIBABA);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case YUNPIAN:
                        try {
                            Map<String, String> configMap = objectMapper.readValue(
                                smsSupplier.getConfigData(),
                                new TypeReference<Map<String, String>>() {}
                            );
                            YunpianConfig yunpianConfig = SupplierFactory.getYunpianConfig();
                            yunpianConfig.setApikey(configMap.get("apikey"));
                            yunpianConfig.setCallbackUrl(configMap.get("callbackUrl"));
                            SmsFactory.refresh(SupplierType.YUNPIAN);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    default:
                        break;
                }
            });
        }
    }

    /**
     * Get one smsSupplier by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<SmsSupplierDTO> findOneByExample(Example<SmsSupplier> example) {
        log.debug("Request to get SmsSupplier by example");
        return smsSupplierRepository.findAll(example).stream().findFirst().map(smsSupplierMapper::toDto);
    }

    /**
     * Get all the smsSuppliers by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<SmsSupplierDTO> findAllByExample(Example<SmsSupplier> example, Pageable pageable) {
        log.debug("Request to get SmsSupplier by example");
        return smsSupplierRepository.findAll(example, pageable).map(smsSupplierMapper::toDto);
    }

    /**
     * Update specified field by smsSupplier
     */
    public void updateBatch(SmsSupplierDTO changeSmsSupplierDTO, List<String> fieldNames, List<Long> ids) {
        smsSupplierRepository
            .findAllById(ids)
            .stream()
            .peek(smsSupplier ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(smsSupplier, fieldName, BeanUtil.getFieldValue(changeSmsSupplierDTO, fieldName))
                )
            )
            .forEach(smsSupplierRepository::save);
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
