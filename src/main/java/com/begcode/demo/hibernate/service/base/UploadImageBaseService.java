package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.begcode.demo.hibernate.domain.UploadImage;
import com.begcode.demo.hibernate.repository.UploadImageRepository;
import com.begcode.demo.hibernate.service.dto.UploadImageDTO;
import com.begcode.demo.hibernate.service.mapper.UploadImageMapper;
import com.begcode.demo.hibernate.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.UploadImage}.
 */
public class UploadImageBaseService {

    private final Logger log = LoggerFactory.getLogger(UploadImageBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".images"
    );

    protected final FileStorageService fileStorageService;

    protected final UploadImageRepository uploadImageRepository;

    protected final CacheManager cacheManager;

    protected final UploadImageMapper uploadImageMapper;

    public UploadImageBaseService(
        FileStorageService fileStorageService,
        UploadImageRepository uploadImageRepository,
        CacheManager cacheManager,
        UploadImageMapper uploadImageMapper
    ) {
        this.fileStorageService = fileStorageService;
        this.uploadImageRepository = uploadImageRepository;
        this.cacheManager = cacheManager;
        this.uploadImageMapper = uploadImageMapper;
    }

    /**
     * Update a uploadImage.
     *
     * @param uploadImageDTO the entity to save.
     * @return the persisted entity.
     */
    public UploadImageDTO update(UploadImageDTO uploadImageDTO) {
        log.debug("Request to update UploadImage : {}", uploadImageDTO);

        UploadImage uploadImage = uploadImageMapper.toEntity(uploadImageDTO);
        uploadImage = uploadImageRepository.save(uploadImage);
        return uploadImageMapper.toDto(uploadImage);
    }

    /**
     * Partially update a uploadImage.
     *
     * @param uploadImageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UploadImageDTO> partialUpdate(UploadImageDTO uploadImageDTO) {
        log.debug("Request to partially update UploadImage : {}", uploadImageDTO);

        return uploadImageRepository
            .findById(uploadImageDTO.getId())
            .map(existingUploadImage -> {
                uploadImageMapper.partialUpdate(existingUploadImage, uploadImageDTO);

                return existingUploadImage;
            })
            .map(uploadImageRepository::save)
            .map(uploadImageMapper::toDto);
    }

    /**
     * Get all the uploadImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadImageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UploadImages");
        return uploadImageRepository.findAll(pageable).map(uploadImageMapper::toDto);
    }

    /**
     * Get all the uploadImages with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UploadImageDTO> findAllWithEagerRelationships(Pageable pageable) {
        return uploadImageRepository.findAllWithEagerRelationships(pageable).map(uploadImageMapper::toDto);
    }

    /**
     * Get one uploadImage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UploadImageDTO> findOne(Long id) {
        log.debug("Request to get UploadImage : {}", id);
        return uploadImageRepository.findOneWithEagerRelationships(id).map(uploadImageMapper::toDto);
    }

    /**
     * Delete the uploadImage by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete UploadImage : {}", id);

        uploadImageRepository.deleteById(id);
    }

    /**
     * Save a uploadImage.
     *
     * @param uploadImageDTO the entity to save
     * @return the persisted entity
     */
    public UploadImageDTO save(UploadImageDTO uploadImageDTO) {
        log.debug("Request to save UploadImage : {}", uploadImageDTO);
        if (!uploadImageDTO.getImage().isEmpty()) {
            final String yearAndMonth = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM"));
            uploadImageDTO.setCreateAt(ZonedDateTime.now());
            uploadImageDTO.setFullName(uploadImageDTO.getImage().getOriginalFilename());
            uploadImageDTO.setName(uploadImageDTO.getImage().getName());
            uploadImageDTO.setFolder(yearAndMonth + File.separator);
            uploadImageDTO.setFileSize(uploadImageDTO.getImage().getSize());
            FileInfo upload = fileStorageService.of(uploadImageDTO.getImage()).setPlatform("local").upload();
            uploadImageDTO.setUrl(upload.getUrl());
            uploadImageDTO.setExt(upload.getExt());
        } else {
            throw new BadRequestAlertException("Invalid file", "UploadFile", "imagesnull");
        }
        UploadImage uploadImage = uploadImageMapper.toEntity(uploadImageDTO);
        uploadImage = uploadImageRepository.save(uploadImage);
        return uploadImageMapper.toDto(uploadImage);
    }

    /**
     * Get one uploadImage by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<UploadImageDTO> findOneByExample(Example<UploadImage> example) {
        log.debug("Request to get UploadImage by example");
        return uploadImageRepository.findAll(example).stream().findFirst().map(uploadImageMapper::toDto);
    }

    /**
     * Get all the uploadImages by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadImageDTO> findAllByExample(Example<UploadImage> example, Pageable pageable) {
        log.debug("Request to get UploadImage by example");
        return uploadImageRepository.findAll(example, pageable).map(uploadImageMapper::toDto);
    }

    /**
     * Update specified field by uploadImage
     */
    public void updateBatch(UploadImageDTO changeUploadImageDTO, List<String> fieldNames, List<Long> ids) {
        uploadImageRepository
            .findAllById(ids)
            .stream()
            .peek(uploadImage ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(uploadImage, fieldName, BeanUtil.getFieldValue(changeUploadImageDTO, fieldName))
                )
            )
            .forEach(uploadImageRepository::save);
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
