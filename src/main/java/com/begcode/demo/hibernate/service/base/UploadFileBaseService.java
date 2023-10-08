package com.begcode.demo.hibernate.service.base;

import cn.hutool.core.bean.BeanUtil;
import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.begcode.demo.hibernate.domain.UploadFile;
import com.begcode.demo.hibernate.repository.UploadFileRepository;
import com.begcode.demo.hibernate.service.dto.UploadFileDTO;
import com.begcode.demo.hibernate.service.mapper.UploadFileMapper;
import com.begcode.demo.hibernate.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
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
 * Service Implementation for managing {@link com.begcode.demo.hibernate.domain.UploadFile}.
 */
public class UploadFileBaseService {

    private final Logger log = LoggerFactory.getLogger(UploadFileBaseService.class);

    private final List<String> relationCacheNames = Arrays.asList(
        com.begcode.demo.hibernate.domain.ResourceCategory.class.getName() + ".files"
    );

    protected final FileStorageService fileStorageService;

    protected final UploadFileRepository uploadFileRepository;

    protected final CacheManager cacheManager;

    protected final UploadFileMapper uploadFileMapper;

    public UploadFileBaseService(
        FileStorageService fileStorageService,
        UploadFileRepository uploadFileRepository,
        CacheManager cacheManager,
        UploadFileMapper uploadFileMapper
    ) {
        this.fileStorageService = fileStorageService;
        this.uploadFileRepository = uploadFileRepository;
        this.cacheManager = cacheManager;
        this.uploadFileMapper = uploadFileMapper;
    }

    /**
     * Update a uploadFile.
     *
     * @param uploadFileDTO the entity to save.
     * @return the persisted entity.
     */
    public UploadFileDTO update(UploadFileDTO uploadFileDTO) {
        log.debug("Request to update UploadFile : {}", uploadFileDTO);

        UploadFile uploadFile = uploadFileMapper.toEntity(uploadFileDTO);
        uploadFile = uploadFileRepository.save(uploadFile);
        return uploadFileMapper.toDto(uploadFile);
    }

    /**
     * Partially update a uploadFile.
     *
     * @param uploadFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UploadFileDTO> partialUpdate(UploadFileDTO uploadFileDTO) {
        log.debug("Request to partially update UploadFile : {}", uploadFileDTO);

        return uploadFileRepository
            .findById(uploadFileDTO.getId())
            .map(existingUploadFile -> {
                uploadFileMapper.partialUpdate(existingUploadFile, uploadFileDTO);

                return existingUploadFile;
            })
            .map(uploadFileRepository::save)
            .map(uploadFileMapper::toDto);
    }

    /**
     * Get all the uploadFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UploadFiles");
        return uploadFileRepository.findAll(pageable).map(uploadFileMapper::toDto);
    }

    /**
     * Get all the uploadFiles with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UploadFileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return uploadFileRepository.findAllWithEagerRelationships(pageable).map(uploadFileMapper::toDto);
    }

    /**
     * Get one uploadFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UploadFileDTO> findOne(Long id) {
        log.debug("Request to get UploadFile : {}", id);
        return uploadFileRepository.findOneWithEagerRelationships(id).map(uploadFileMapper::toDto);
    }

    /**
     * Delete the uploadFile by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete UploadFile : {}", id);

        uploadFileRepository.deleteById(id);
    }

    /**
     * Save a uploadFile.
     *
     * @param uploadFileDTO the entity to save
     * @return the persisted entity
     */
    public UploadFileDTO save(UploadFileDTO uploadFileDTO) {
        log.debug("Request to save UploadFile : {}", uploadFileDTO);
        if (!uploadFileDTO.getFile().isEmpty()) {
            final String extName = FilenameUtils.getExtension(uploadFileDTO.getFile().getOriginalFilename());
            final String randomNameNew = UUID.randomUUID().toString().replaceAll("\\-", "");
            final String yearAndMonth = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM"));
            final String savePathNew = yearAndMonth + File.separator;
            final String saveFileName = savePathNew + randomNameNew + "." + extName;
            final long fileSize = uploadFileDTO.getFile().getSize();
            FileInfo upload = fileStorageService.of(uploadFileDTO.getFile()).setPath(savePathNew).upload();
            uploadFileDTO.setCreateAt(ZonedDateTime.now());
            uploadFileDTO.setExt(extName);
            uploadFileDTO.setFullName(uploadFileDTO.getFile().getOriginalFilename());
            uploadFileDTO.setName(uploadFileDTO.getFile().getName());
            uploadFileDTO.setFolder(savePathNew);
            uploadFileDTO.setUrl(upload.getUrl());
            uploadFileDTO.setFileSize(fileSize);
        } else {
            throw new BadRequestAlertException("Invalid file", "UploadFile", "imagesnull");
        }
        UploadFile uploadFile = uploadFileMapper.toEntity(uploadFileDTO);
        uploadFile = uploadFileRepository.save(uploadFile);
        return uploadFileMapper.toDto(uploadFile);
    }

    /**
     * Get one uploadFile by example.
     *
     * @param example the example of the entity.
     * @return the entity.
     */
    public Optional<UploadFileDTO> findOneByExample(Example<UploadFile> example) {
        log.debug("Request to get UploadFile by example");
        return uploadFileRepository.findAll(example).stream().findFirst().map(uploadFileMapper::toDto);
    }

    /**
     * Get all the uploadFiles by example.
     *
     * @param example the example of the entity.
     * @return the entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadFileDTO> findAllByExample(Example<UploadFile> example, Pageable pageable) {
        log.debug("Request to get UploadFile by example");
        return uploadFileRepository.findAll(example, pageable).map(uploadFileMapper::toDto);
    }

    /**
     * Update specified field by uploadFile
     */
    public void updateBatch(UploadFileDTO changeUploadFileDTO, List<String> fieldNames, List<Long> ids) {
        uploadFileRepository
            .findAllById(ids)
            .stream()
            .peek(uploadFile ->
                fieldNames.forEach(fieldName ->
                    BeanUtil.setFieldValue(uploadFile, fieldName, BeanUtil.getFieldValue(changeUploadFileDTO, fieldName))
                )
            )
            .forEach(uploadFileRepository::save);
    }

    protected void clearRelationsCache() {
        this.relationCacheNames.forEach(cacheName -> Optional.ofNullable(cacheManager.getCache(cacheName)).ifPresent(Cache::clear));
    }
    // jhipster-needle-service-add-method - JHipster will add getters and setters here, do not remove

}
