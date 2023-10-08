package com.begcode.demo.hibernate.log.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.log.repository.SysLogRepository;
import com.begcode.demo.hibernate.log.service.SysLogQueryService;
import com.begcode.demo.hibernate.log.service.SysLogService;
import com.begcode.demo.hibernate.log.service.criteria.SysLogCriteria;
import com.begcode.demo.hibernate.log.service.dto.SysLogDTO;
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

 * 管理实体{@link com.begcode.demo.hibernate.log.domain.SysLog}的REST Controller。
 */
public class SysLogBaseResource {

    protected final Logger log = LoggerFactory.getLogger(SysLogBaseResource.class);

    protected static final String ENTITY_NAME = "logSysLog";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final SysLogService sysLogService;

    protected final SysLogRepository sysLogRepository;

    protected final SysLogQueryService sysLogQueryService;

    public SysLogBaseResource(SysLogService sysLogService, SysLogRepository sysLogRepository, SysLogQueryService sysLogQueryService) {
        this.sysLogService = sysLogService;
        this.sysLogRepository = sysLogRepository;
        this.sysLogQueryService = sysLogQueryService;
    }

    /**
     * {@code POST  /sys-logs} : Create a new sysLog.
     *
     * @param sysLogDTO the sysLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysLogDTO, or with status {@code 400 (Bad Request)} if the sysLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-logs")
    @Operation(tags = "新建系统日志", description = "创建并返回一个新的系统日志")
    @AutoLog(value = "新建系统日志", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<SysLogDTO> createSysLog(@RequestBody SysLogDTO sysLogDTO) throws URISyntaxException {
        log.debug("REST request to save SysLog : {}", sysLogDTO);
        if (sysLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysLogDTO result = sysLogService.save(sysLogDTO);
        return ResponseEntity
            .created(new URI("/api/sys-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-logs/:id} : Updates an existing sysLog.
     *
     * @param id the id of the sysLogDTO to save.
     * @param sysLogDTO the sysLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysLogDTO,
     * or with status {@code 400 (Bad Request)} if the sysLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysLogDTO couldn't be updated.
     */
    @PutMapping("/sys-logs/{id}")
    @Operation(tags = "更新系统日志", description = "根据主键更新并返回一个更新后的系统日志")
    @AutoLog(value = "更新系统日志", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<SysLogDTO> updateSysLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysLogDTO sysLogDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update SysLog : {}, {}", id, sysLogDTO);
        if (sysLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SysLogDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            sysLogService.updateBatch(sysLogDTO, batchFields, batchIds);
            result = sysLogService.findOne(id).orElseThrow();
        } else {
            result = sysLogService.update(sysLogDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sys-logs/:id} : Partial updates given fields of an existing sysLog, field will ignore if it is null
     *
     * @param id the id of the sysLogDTO to save.
     * @param sysLogDTO the sysLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysLogDTO,
     * or with status {@code 400 (Bad Request)} if the sysLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sysLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sysLogDTO couldn't be updated.
     */
    @PatchMapping(value = "/sys-logs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(tags = "部分更新系统日志", description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的系统日志")
    @AutoLog(value = "部分更新系统日志", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<SysLogDTO> partialUpdateSysLog(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysLogDTO sysLogDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SysLog partially : {}, {}", id, sysLogDTO);
        if (sysLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sysLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sysLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SysLogDTO> result = sysLogService.partialUpdate(sysLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sys-logs} : get all the sysLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysLogs in body.
     */
    @GetMapping("/sys-logs")
    @Operation(tags = "获取系统日志分页列表", description = "获取系统日志的分页列表数据")
    @AutoLog(value = "获取系统日志分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<SysLogDTO>> getAllSysLogs(
        SysLogCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SysLogs by criteria: {}", criteria);

        Page<SysLogDTO> page;
        page = sysLogQueryService.findByCriteria(criteria, pageable);
        PageRecord<SysLogDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /sys-logs/count} : count all the sysLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sys-logs/count")
    public ResponseEntity<Long> countSysLogs(SysLogCriteria criteria) {
        log.debug("REST request to count SysLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(sysLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sys-logs/:id} : get the "id" sysLog.
     *
     * @param id the id of the sysLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-logs/{id}")
    @Operation(tags = "获取指定主键的系统日志", description = "获取指定主键的系统日志信息")
    @AutoLog(value = "获取指定主键的系统日志", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<SysLogDTO> getSysLog(@PathVariable Long id) {
        log.debug("REST request to get SysLog : {}", id);
        Optional<SysLogDTO> sysLogDTO = sysLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysLogDTO);
    }

    /**
     * {@code DELETE  /sys-logs/:id} : delete the "id" sysLog.
     *
     * @param id the id of the sysLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-logs/{id}")
    @Operation(tags = "删除指定主键的系统日志", description = "删除指定主键的系统日志信息")
    @AutoLog(value = "删除指定主键的系统日志", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteSysLog(@PathVariable Long id) {
        log.debug("REST request to delete SysLog : {}", id);

        sysLogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /sys-logs/export : export the sysLogs.
     *
     */
    @GetMapping("/sys-logs/export")
    @Operation(tags = "系统日志EXCEL导出", description = "导出全部系统日志为EXCEL文件")
    @AutoLog(value = "系统日志EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<SysLogDTO> data = sysLogService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("系统日志一览表", "系统日志"), SysLogDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "系统日志_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /sys-logs/import : import the sysLogs from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the sysLogDTO, or with status 404 (Not Found)
     */
    @PostMapping("/sys-logs/import")
    @Operation(tags = "系统日志EXCEL导入", description = "根据系统日志EXCEL文件导入全部数据")
    @AutoLog(value = "系统日志EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<SysLogDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), SysLogDTO.class, params);
        list.forEach(sysLogService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /sys-logs} : delete all the "ids" SysLogs.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-logs")
    @Operation(tags = "删除多个系统日志", description = "根据主键删除多个系统日志")
    @AutoLog(value = "删除多个系统日志", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteSysLogsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete SysLogs : {}", ids);
        if (ids != null) {
            ids.forEach(sysLogService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/sys-logs/stats")
    @Operation(tags = "根据条件对系统日志进行统计", description = "条件和统计的配置通过系统日志的Criteria类来实现")
    @AutoLog(value = "根据条件对系统日志进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(SysLogCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = sysLogQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }

    /**
     * 获取访客统计
     * @return ResponseEntity<List<Map<String,Object>>>
     */
    @GetMapping("/sys-logs/visit-info")
    public ResponseEntity<List<Map<String, Object>>> visitInfo() {
        return ResponseEntity.ok(sysLogQueryService.countVisit());
    }

    /**
     * 获取访问量统计
     */
    @GetMapping("/sys-logs/stats-user")
    public ResponseEntity<Map<String, Object>> loginfo() {
        return ResponseEntity.ok(sysLogQueryService.logInfo());
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
