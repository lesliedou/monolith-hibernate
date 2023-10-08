package com.begcode.demo.hibernate.taskjob.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.taskjob.repository.TaskJobConfigRepository;
import com.begcode.demo.hibernate.taskjob.service.TaskJobConfigQueryService;
import com.begcode.demo.hibernate.taskjob.service.TaskJobConfigService;
import com.begcode.demo.hibernate.taskjob.service.criteria.TaskJobConfigCriteria;
import com.begcode.demo.hibernate.taskjob.service.dto.TaskJobConfigDTO;
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

 * 管理实体{@link com.begcode.demo.hibernate.taskjob.domain.TaskJobConfig}的REST Controller。
 */
public class TaskJobConfigBaseResource {

    protected final Logger log = LoggerFactory.getLogger(TaskJobConfigBaseResource.class);

    protected static final String ENTITY_NAME = "taskjobTaskJobConfig";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final TaskJobConfigService taskJobConfigService;

    protected final TaskJobConfigRepository taskJobConfigRepository;

    protected final TaskJobConfigQueryService taskJobConfigQueryService;

    public TaskJobConfigBaseResource(
        TaskJobConfigService taskJobConfigService,
        TaskJobConfigRepository taskJobConfigRepository,
        TaskJobConfigQueryService taskJobConfigQueryService
    ) {
        this.taskJobConfigService = taskJobConfigService;
        this.taskJobConfigRepository = taskJobConfigRepository;
        this.taskJobConfigQueryService = taskJobConfigQueryService;
    }

    /**
     * {@code POST  /task-job-configs} : Create a new taskJobConfig.
     *
     * @param taskJobConfigDTO the taskJobConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskJobConfigDTO, or with status {@code 400 (Bad Request)} if the taskJobConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-job-configs")
    @Operation(tags = "新建定时任务", description = "创建并返回一个新的定时任务")
    @AutoLog(value = "新建定时任务", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<TaskJobConfigDTO> createTaskJobConfig(@RequestBody TaskJobConfigDTO taskJobConfigDTO) throws URISyntaxException {
        log.debug("REST request to save TaskJobConfig : {}", taskJobConfigDTO);
        if (taskJobConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskJobConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskJobConfigDTO result = taskJobConfigService.save(taskJobConfigDTO);
        return ResponseEntity
            .created(new URI("/api/task-job-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-job-configs/:id} : Updates an existing taskJobConfig.
     *
     * @param id the id of the taskJobConfigDTO to save.
     * @param taskJobConfigDTO the taskJobConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskJobConfigDTO,
     * or with status {@code 400 (Bad Request)} if the taskJobConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskJobConfigDTO couldn't be updated.
     */
    @PutMapping("/task-job-configs/{id}")
    @Operation(tags = "更新定时任务", description = "根据主键更新并返回一个更新后的定时任务")
    @AutoLog(value = "更新定时任务", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<TaskJobConfigDTO> updateTaskJobConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskJobConfigDTO taskJobConfigDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update TaskJobConfig : {}, {}", id, taskJobConfigDTO);
        if (taskJobConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskJobConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskJobConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskJobConfigDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            taskJobConfigService.updateBatch(taskJobConfigDTO, batchFields, batchIds);
            result = taskJobConfigService.findOne(id).orElseThrow();
        } else {
            result = taskJobConfigService.update(taskJobConfigDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskJobConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-job-configs/:id} : Partial updates given fields of an existing taskJobConfig, field will ignore if it is null
     *
     * @param id the id of the taskJobConfigDTO to save.
     * @param taskJobConfigDTO the taskJobConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskJobConfigDTO,
     * or with status {@code 400 (Bad Request)} if the taskJobConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the taskJobConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskJobConfigDTO couldn't be updated.
     */
    @PatchMapping(value = "/task-job-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(tags = "部分更新定时任务", description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的定时任务")
    @AutoLog(value = "部分更新定时任务", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<TaskJobConfigDTO> partialUpdateTaskJobConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskJobConfigDTO taskJobConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskJobConfig partially : {}, {}", id, taskJobConfigDTO);
        if (taskJobConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskJobConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskJobConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskJobConfigDTO> result = taskJobConfigService.partialUpdate(taskJobConfigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskJobConfigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /task-job-configs} : get all the taskJobConfigs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskJobConfigs in body.
     */
    @GetMapping("/task-job-configs")
    @Operation(tags = "获取定时任务分页列表", description = "获取定时任务的分页列表数据")
    @AutoLog(value = "获取定时任务分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<TaskJobConfigDTO>> getAllTaskJobConfigs(
        TaskJobConfigCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TaskJobConfigs by criteria: {}", criteria);

        Page<TaskJobConfigDTO> page;
        page = taskJobConfigQueryService.findByCriteria(criteria, pageable);
        PageRecord<TaskJobConfigDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /task-job-configs/count} : count all the taskJobConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/task-job-configs/count")
    public ResponseEntity<Long> countTaskJobConfigs(TaskJobConfigCriteria criteria) {
        log.debug("REST request to count TaskJobConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(taskJobConfigQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /task-job-configs/:id} : get the "id" taskJobConfig.
     *
     * @param id the id of the taskJobConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskJobConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-job-configs/{id}")
    @Operation(tags = "获取指定主键的定时任务", description = "获取指定主键的定时任务信息")
    @AutoLog(value = "获取指定主键的定时任务", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<TaskJobConfigDTO> getTaskJobConfig(@PathVariable Long id) {
        log.debug("REST request to get TaskJobConfig : {}", id);
        Optional<TaskJobConfigDTO> taskJobConfigDTO = taskJobConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskJobConfigDTO);
    }

    /**
     * {@code DELETE  /task-job-configs/:id} : delete the "id" taskJobConfig.
     *
     * @param id the id of the taskJobConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-job-configs/{id}")
    @Operation(tags = "删除指定主键的定时任务", description = "删除指定主键的定时任务信息")
    @AutoLog(value = "删除指定主键的定时任务", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteTaskJobConfig(@PathVariable Long id) {
        log.debug("REST request to delete TaskJobConfig : {}", id);

        taskJobConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /task-job-configs/export : export the taskJobConfigs.
     *
     */
    @GetMapping("/task-job-configs/export")
    @Operation(tags = "定时任务EXCEL导出", description = "导出全部定时任务为EXCEL文件")
    @AutoLog(value = "定时任务EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<TaskJobConfigDTO> data = taskJobConfigService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("定时任务一览表", "定时任务"), TaskJobConfigDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "定时任务_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /task-job-configs/import : import the taskJobConfigs from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the taskJobConfigDTO, or with status 404 (Not Found)
     */
    @PostMapping("/task-job-configs/import")
    @Operation(tags = "定时任务EXCEL导入", description = "根据定时任务EXCEL文件导入全部数据")
    @AutoLog(value = "定时任务EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<TaskJobConfigDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), TaskJobConfigDTO.class, params);
        list.forEach(taskJobConfigService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /task-job-configs} : delete all the "ids" TaskJobConfigs.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-job-configs")
    @Operation(tags = "删除多个定时任务", description = "根据主键删除多个定时任务")
    @AutoLog(value = "删除多个定时任务", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteTaskJobConfigsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete TaskJobConfigs : {}", ids);
        if (ids != null) {
            ids.forEach(taskJobConfigService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/task-job-configs/stats")
    @Operation(tags = "根据条件对定时任务进行统计", description = "条件和统计的配置通过定时任务的Criteria类来实现")
    @AutoLog(value = "根据条件对定时任务进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(TaskJobConfigCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = taskJobConfigQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
