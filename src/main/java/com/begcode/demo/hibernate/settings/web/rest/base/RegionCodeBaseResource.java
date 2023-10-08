package com.begcode.demo.hibernate.settings.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.settings.repository.RegionCodeRepository;
import com.begcode.demo.hibernate.settings.service.RegionCodeQueryService;
import com.begcode.demo.hibernate.settings.service.RegionCodeService;
import com.begcode.demo.hibernate.settings.service.criteria.RegionCodeCriteria;
import com.begcode.demo.hibernate.settings.service.dto.RegionCodeDTO;
import com.begcode.demo.hibernate.util.ExportUtil;
import com.begcode.demo.hibernate.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
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

 * 管理实体{@link com.begcode.demo.hibernate.settings.domain.RegionCode}的REST Controller。
 */
public class RegionCodeBaseResource {

    protected final Logger log = LoggerFactory.getLogger(RegionCodeBaseResource.class);

    protected static final String ENTITY_NAME = "settingsRegionCode";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final RegionCodeService regionCodeService;

    protected final RegionCodeRepository regionCodeRepository;

    protected final RegionCodeQueryService regionCodeQueryService;

    public RegionCodeBaseResource(
        RegionCodeService regionCodeService,
        RegionCodeRepository regionCodeRepository,
        RegionCodeQueryService regionCodeQueryService
    ) {
        this.regionCodeService = regionCodeService;
        this.regionCodeRepository = regionCodeRepository;
        this.regionCodeQueryService = regionCodeQueryService;
    }

    /**
     * {@code POST  /region-codes} : Create a new regionCode.
     *
     * @param regionCodeDTO the regionCodeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regionCodeDTO, or with status {@code 400 (Bad Request)} if the regionCode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/region-codes")
    @Operation(tags = "新建行政区划码", description = "创建并返回一个新的行政区划码")
    @AutoLog(value = "新建行政区划码", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<RegionCodeDTO> createRegionCode(@RequestBody RegionCodeDTO regionCodeDTO) throws URISyntaxException {
        log.debug("REST request to save RegionCode : {}", regionCodeDTO);
        if (regionCodeDTO.getId() != null) {
            throw new BadRequestAlertException("A new regionCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegionCodeDTO result = regionCodeService.save(regionCodeDTO);
        return ResponseEntity
            .created(new URI("/api/region-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /region-codes/:id} : Updates an existing regionCode.
     *
     * @param id the id of the regionCodeDTO to save.
     * @param regionCodeDTO the regionCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionCodeDTO,
     * or with status {@code 400 (Bad Request)} if the regionCodeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regionCodeDTO couldn't be updated.
     */
    @PutMapping("/region-codes/{id}")
    @Operation(tags = "更新行政区划码", description = "根据主键更新并返回一个更新后的行政区划码")
    @AutoLog(value = "更新行政区划码", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<RegionCodeDTO> updateRegionCode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RegionCodeDTO regionCodeDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update RegionCode : {}, {}", id, regionCodeDTO);
        if (regionCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regionCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regionCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegionCodeDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            regionCodeService.updateBatch(regionCodeDTO, batchFields, batchIds);
            result = regionCodeService.findOne(id).orElseThrow();
        } else {
            result = regionCodeService.update(regionCodeDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regionCodeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /region-codes/:id} : Partial updates given fields of an existing regionCode, field will ignore if it is null
     *
     * @param id the id of the regionCodeDTO to save.
     * @param regionCodeDTO the regionCodeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regionCodeDTO,
     * or with status {@code 400 (Bad Request)} if the regionCodeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the regionCodeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the regionCodeDTO couldn't be updated.
     */
    @PatchMapping(value = "/region-codes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(
        tags = "部分更新行政区划码",
        description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的行政区划码"
    )
    @AutoLog(value = "部分更新行政区划码", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<RegionCodeDTO> partialUpdateRegionCode(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RegionCodeDTO regionCodeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegionCode partially : {}, {}", id, regionCodeDTO);
        if (regionCodeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regionCodeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regionCodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegionCodeDTO> result = regionCodeService.partialUpdate(regionCodeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regionCodeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /region-codes} : get all the regionCodes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regionCodes in body.
     */
    @GetMapping("/region-codes")
    @Operation(tags = "获取行政区划码分页列表", description = "获取行政区划码的分页列表数据")
    @AutoLog(value = "获取行政区划码分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<RegionCodeDTO>> getAllRegionCodes(
        RegionCodeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RegionCodes by criteria: {}", criteria);

        Page<RegionCodeDTO> page;
        page = regionCodeQueryService.findByCriteria(criteria, pageable);
        PageRecord<RegionCodeDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /region-codes/count} : count all the regionCodes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/region-codes/count")
    public ResponseEntity<Long> countRegionCodes(RegionCodeCriteria criteria) {
        log.debug("REST request to count RegionCodes by criteria: {}", criteria);
        return ResponseEntity.ok().body(regionCodeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /region-codes/:id} : get the "id" regionCode.
     *
     * @param id the id of the regionCodeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regionCodeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/region-codes/{id}")
    @Operation(tags = "获取指定主键的行政区划码", description = "获取指定主键的行政区划码信息")
    @AutoLog(value = "获取指定主键的行政区划码", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<RegionCodeDTO> getRegionCode(@PathVariable Long id) {
        log.debug("REST request to get RegionCode : {}", id);
        Optional<RegionCodeDTO> regionCodeDTO = regionCodeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regionCodeDTO);
    }

    /**
     * {@code DELETE  /region-codes/:id} : delete the "id" regionCode.
     *
     * @param id the id of the regionCodeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/region-codes/{id}")
    @Operation(tags = "删除指定主键的行政区划码", description = "删除指定主键的行政区划码信息")
    @AutoLog(value = "删除指定主键的行政区划码", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteRegionCode(@PathVariable Long id) {
        log.debug("REST request to delete RegionCode : {}", id);

        regionCodeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /region-codes/export : export the regionCodes.
     *
     */
    @GetMapping("/region-codes/export")
    @Operation(tags = "行政区划码EXCEL导出", description = "导出全部行政区划码为EXCEL文件")
    @AutoLog(value = "行政区划码EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<RegionCodeDTO> data = regionCodeService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("行政区划码一览表", "行政区划码"), RegionCodeDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "行政区划码_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /region-codes/import : import the regionCodes from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the regionCodeDTO, or with status 404 (Not Found)
     */
    @PostMapping("/region-codes/import")
    @Operation(tags = "行政区划码EXCEL导入", description = "根据行政区划码EXCEL文件导入全部数据")
    @AutoLog(value = "行政区划码EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<RegionCodeDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), RegionCodeDTO.class, params);
        list.forEach(regionCodeService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /region-codes/tree : get all the regionCodes for parent is null.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of regionCodes in body
     */
    @GetMapping("/region-codes/tree")
    @Operation(tags = "获取行政区划码树形列表", description = "获取行政区划码的树形列表数据")
    @AutoLog(value = "获取行政区划码树形列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<RegionCodeDTO>> getAllRegionCodesofTree(RegionCodeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get a page of RegionCodes");
        Page<RegionCodeDTO> page = regionCodeQueryService.findAllTop(criteria, pageable);
        PageRecord<RegionCodeDTO> result = new PageRecord<>();
        result.records(page.getContent()).size((int) page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * GET  /region-codes/{parentId}/tree : get all the regionCodes for parent is parentId.
     *
     * @param parentId the parent of Id
     * @return the ResponseEntity with status 200 (OK) and the list of regionCodes in body
     */
    @GetMapping("/region-codes/{parentId}/tree")
    @Operation(tags = "获取行政区划码指定节点下的树形列表", description = "获取行政区划码指定节点下的树形列表数据")
    @AutoLog(value = "获取行政区划码指定节点下的树形列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<List<RegionCodeDTO>> getAllRegionCodesofParent(@PathVariable Long parentId) {
        log.debug("REST request to get all RegionCodes of parentId");
        List<RegionCodeDTO> list = regionCodeQueryService.findChildrenByParentId(parentId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code DELETE  /region-codes} : delete all the "ids" RegionCodes.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/region-codes")
    @Operation(tags = "删除多个行政区划码", description = "根据主键删除多个行政区划码")
    @AutoLog(value = "删除多个行政区划码", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteRegionCodesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete RegionCodes : {}", ids);
        if (ids != null) {
            ids.forEach(regionCodeService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/region-codes/stats")
    @Operation(tags = "根据条件对行政区划码进行统计", description = "条件和统计的配置通过行政区划码的Criteria类来实现")
    @AutoLog(value = "根据条件对行政区划码进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(RegionCodeCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = regionCodeQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
