package com.begcode.demo.hibernate.oss.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.oss.repository.OssConfigRepository;
import com.begcode.demo.hibernate.oss.service.OssConfigQueryService;
import com.begcode.demo.hibernate.oss.service.OssConfigService;
import com.begcode.demo.hibernate.oss.service.criteria.OssConfigCriteria;
import com.begcode.demo.hibernate.oss.service.dto.OssConfigDTO;
import com.begcode.demo.hibernate.util.ExportUtil;
import com.begcode.demo.hibernate.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PageRecord;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * 管理实体{@link com.begcode.demo.hibernate.oss.domain.OssConfig}的REST Controller。
 */
public class OssConfigBaseResource {

    protected final Logger log = LoggerFactory.getLogger(OssConfigBaseResource.class);

    protected static final String ENTITY_NAME = "filesOssConfig";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final OssConfigService ossConfigService;

    protected final OssConfigRepository ossConfigRepository;

    protected final OssConfigQueryService ossConfigQueryService;

    public OssConfigBaseResource(
        OssConfigService ossConfigService,
        OssConfigRepository ossConfigRepository,
        OssConfigQueryService ossConfigQueryService
    ) {
        this.ossConfigService = ossConfigService;
        this.ossConfigRepository = ossConfigRepository;
        this.ossConfigQueryService = ossConfigQueryService;
    }

    /**
     * {@code POST  /oss-configs} : Create a new ossConfig.
     *
     * @param ossConfigDTO the ossConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ossConfigDTO, or with status {@code 400 (Bad Request)} if the ossConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/oss-configs")
    @Operation(tags = "新建对象存储配置", description = "创建并返回一个新的对象存储配置")
    @AutoLog(value = "新建对象存储配置", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<OssConfigDTO> createOssConfig(@Valid @RequestBody OssConfigDTO ossConfigDTO) throws URISyntaxException {
        log.debug("REST request to save OssConfig : {}", ossConfigDTO);
        if (ossConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new ossConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OssConfigDTO result = ossConfigService.save(ossConfigDTO);
        return ResponseEntity
            .created(new URI("/api/oss-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /oss-configs/:id} : Updates an existing ossConfig.
     *
     * @param id the id of the ossConfigDTO to save.
     * @param ossConfigDTO the ossConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ossConfigDTO,
     * or with status {@code 400 (Bad Request)} if the ossConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ossConfigDTO couldn't be updated.
     */
    @PutMapping("/oss-configs/{id}")
    @Operation(tags = "更新对象存储配置", description = "根据主键更新并返回一个更新后的对象存储配置")
    @AutoLog(value = "更新对象存储配置", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<OssConfigDTO> updateOssConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OssConfigDTO ossConfigDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update OssConfig : {}, {}", id, ossConfigDTO);
        if (ossConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ossConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ossConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OssConfigDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            ossConfigService.updateBatch(ossConfigDTO, batchFields, batchIds);
            result = ossConfigService.findOne(id).orElseThrow();
        } else {
            result = ossConfigService.update(ossConfigDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ossConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /oss-configs/:id} : Partial updates given fields of an existing ossConfig, field will ignore if it is null
     *
     * @param id the id of the ossConfigDTO to save.
     * @param ossConfigDTO the ossConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ossConfigDTO,
     * or with status {@code 400 (Bad Request)} if the ossConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ossConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ossConfigDTO couldn't be updated.
     */
    @PatchMapping(value = "/oss-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(
        tags = "部分更新对象存储配置",
        description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的对象存储配置"
    )
    @AutoLog(value = "部分更新对象存储配置", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<OssConfigDTO> partialUpdateOssConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OssConfigDTO ossConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OssConfig partially : {}, {}", id, ossConfigDTO);
        if (ossConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ossConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ossConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OssConfigDTO> result = ossConfigService.partialUpdate(ossConfigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ossConfigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /oss-configs} : get all the ossConfigs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ossConfigs in body.
     */
    @GetMapping("/oss-configs")
    @Operation(tags = "获取对象存储配置分页列表", description = "获取对象存储配置的分页列表数据")
    @AutoLog(value = "获取对象存储配置分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<OssConfigDTO>> getAllOssConfigs(
        OssConfigCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OssConfigs by criteria: {}", criteria);

        Page<OssConfigDTO> page;
        page = ossConfigQueryService.findByCriteria(criteria, pageable);
        PageRecord<OssConfigDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /oss-configs/count} : count all the ossConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/oss-configs/count")
    public ResponseEntity<Long> countOssConfigs(OssConfigCriteria criteria) {
        log.debug("REST request to count OssConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(ossConfigQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /oss-configs/:id} : get the "id" ossConfig.
     *
     * @param id the id of the ossConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ossConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/oss-configs/{id}")
    @Operation(tags = "获取指定主键的对象存储配置", description = "获取指定主键的对象存储配置信息")
    @AutoLog(value = "获取指定主键的对象存储配置", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<OssConfigDTO> getOssConfig(@PathVariable Long id) {
        log.debug("REST request to get OssConfig : {}", id);
        Optional<OssConfigDTO> ossConfigDTO = ossConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ossConfigDTO);
    }

    /**
     * {@code DELETE  /oss-configs/:id} : delete the "id" ossConfig.
     *
     * @param id the id of the ossConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/oss-configs/{id}")
    @Operation(tags = "删除指定主键的对象存储配置", description = "删除指定主键的对象存储配置信息")
    @AutoLog(value = "删除指定主键的对象存储配置", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteOssConfig(@PathVariable Long id) {
        log.debug("REST request to delete OssConfig : {}", id);

        ossConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /oss-configs/export : export the ossConfigs.
     *
     */
    @GetMapping("/oss-configs/export")
    @Operation(tags = "对象存储配置EXCEL导出", description = "导出全部对象存储配置为EXCEL文件")
    @AutoLog(value = "对象存储配置EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<OssConfigDTO> data = ossConfigService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("对象存储配置一览表", "对象存储配置"), OssConfigDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "对象存储配置_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /oss-configs/import : import the ossConfigs from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the ossConfigDTO, or with status 404 (Not Found)
     */
    @PostMapping("/oss-configs/import")
    @Operation(tags = "对象存储配置EXCEL导入", description = "根据对象存储配置EXCEL文件导入全部数据")
    @AutoLog(value = "对象存储配置EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<OssConfigDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), OssConfigDTO.class, params);
        list.forEach(ossConfigService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /oss-configs} : delete all the "ids" OssConfigs.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/oss-configs")
    @Operation(tags = "删除多个对象存储配置", description = "根据主键删除多个对象存储配置")
    @AutoLog(value = "删除多个对象存储配置", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteOssConfigsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete OssConfigs : {}", ids);
        if (ids != null) {
            ids.forEach(ossConfigService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/oss-configs/stats")
    @Operation(tags = "根据条件对对象存储配置进行统计", description = "条件和统计的配置通过对象存储配置的Criteria类来实现")
    @AutoLog(value = "根据条件对对象存储配置进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(OssConfigCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = ossConfigQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
