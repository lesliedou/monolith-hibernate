package com.begcode.demo.hibernate.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.repository.ViewPermissionRepository;
import com.begcode.demo.hibernate.service.ViewPermissionQueryService;
import com.begcode.demo.hibernate.service.ViewPermissionService;
import com.begcode.demo.hibernate.service.criteria.ViewPermissionCriteria;
import com.begcode.demo.hibernate.service.dto.ViewPermissionDTO;
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

 * 管理实体{@link com.begcode.demo.hibernate.domain.ViewPermission}的REST Controller。
 */
public class ViewPermissionBaseResource {

    protected final Logger log = LoggerFactory.getLogger(ViewPermissionBaseResource.class);

    protected static final String ENTITY_NAME = "systemViewPermission";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final ViewPermissionService viewPermissionService;

    protected final ViewPermissionRepository viewPermissionRepository;

    protected final ViewPermissionQueryService viewPermissionQueryService;

    public ViewPermissionBaseResource(
        ViewPermissionService viewPermissionService,
        ViewPermissionRepository viewPermissionRepository,
        ViewPermissionQueryService viewPermissionQueryService
    ) {
        this.viewPermissionService = viewPermissionService;
        this.viewPermissionRepository = viewPermissionRepository;
        this.viewPermissionQueryService = viewPermissionQueryService;
    }

    /**
     * {@code POST  /view-permissions} : Create a new viewPermission.
     *
     * @param viewPermissionDTO the viewPermissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viewPermissionDTO, or with status {@code 400 (Bad Request)} if the viewPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/view-permissions")
    @Operation(tags = "新建可视权限", description = "创建并返回一个新的可视权限")
    @AutoLog(value = "新建可视权限", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<ViewPermissionDTO> createViewPermission(@Valid @RequestBody ViewPermissionDTO viewPermissionDTO)
        throws URISyntaxException {
        log.debug("REST request to save ViewPermission : {}", viewPermissionDTO);
        if (viewPermissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new viewPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViewPermissionDTO result = viewPermissionService.save(viewPermissionDTO);
        return ResponseEntity
            .created(new URI("/api/view-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /view-permissions/:id} : Updates an existing viewPermission.
     *
     * @param id the id of the viewPermissionDTO to save.
     * @param viewPermissionDTO the viewPermissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the viewPermissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viewPermissionDTO couldn't be updated.
     */
    @PutMapping("/view-permissions/{id}")
    @Operation(tags = "更新可视权限", description = "根据主键更新并返回一个更新后的可视权限")
    @AutoLog(value = "更新可视权限", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<ViewPermissionDTO> updateViewPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ViewPermissionDTO viewPermissionDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update ViewPermission : {}, {}", id, viewPermissionDTO);
        if (viewPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viewPermissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viewPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ViewPermissionDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            viewPermissionService.updateBatch(viewPermissionDTO, batchFields, batchIds);
            result = viewPermissionService.findOne(id).orElseThrow();
        } else {
            result = viewPermissionService.update(viewPermissionDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, viewPermissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /view-permissions/:id} : Partial updates given fields of an existing viewPermission, field will ignore if it is null
     *
     * @param id the id of the viewPermissionDTO to save.
     * @param viewPermissionDTO the viewPermissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the viewPermissionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the viewPermissionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the viewPermissionDTO couldn't be updated.
     */
    @PatchMapping(value = "/view-permissions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(tags = "部分更新可视权限", description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的可视权限")
    @AutoLog(value = "部分更新可视权限", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<ViewPermissionDTO> partialUpdateViewPermission(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ViewPermissionDTO viewPermissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ViewPermission partially : {}, {}", id, viewPermissionDTO);
        if (viewPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viewPermissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viewPermissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ViewPermissionDTO> result = viewPermissionService.partialUpdate(viewPermissionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, viewPermissionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /view-permissions} : get all the viewPermissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewPermissions in body.
     */
    @GetMapping("/view-permissions")
    @Operation(tags = "获取可视权限分页列表", description = "获取可视权限的分页列表数据")
    @AutoLog(value = "获取可视权限分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<ViewPermissionDTO>> getAllViewPermissions(
        ViewPermissionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ViewPermissions by criteria: {}", criteria);

        Page<ViewPermissionDTO> page;
        page = viewPermissionQueryService.findByCriteria(criteria, pageable);
        PageRecord<ViewPermissionDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /view-permissions/count} : count all the viewPermissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/view-permissions/count")
    public ResponseEntity<Long> countViewPermissions(ViewPermissionCriteria criteria) {
        log.debug("REST request to count ViewPermissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(viewPermissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /view-permissions/:id} : get the "id" viewPermission.
     *
     * @param id the id of the viewPermissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewPermissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/view-permissions/{id}")
    @Operation(tags = "获取指定主键的可视权限", description = "获取指定主键的可视权限信息")
    @AutoLog(value = "获取指定主键的可视权限", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<ViewPermissionDTO> getViewPermission(@PathVariable Long id) {
        log.debug("REST request to get ViewPermission : {}", id);
        Optional<ViewPermissionDTO> viewPermissionDTO = viewPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viewPermissionDTO);
    }

    /**
     * {@code DELETE  /view-permissions/:id} : delete the "id" viewPermission.
     *
     * @param id the id of the viewPermissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/view-permissions/{id}")
    @Operation(tags = "删除指定主键的可视权限", description = "删除指定主键的可视权限信息")
    @AutoLog(value = "删除指定主键的可视权限", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteViewPermission(@PathVariable Long id) {
        log.debug("REST request to delete ViewPermission : {}", id);

        viewPermissionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /view-permissions/export : export the viewPermissions.
     *
     */
    @GetMapping("/view-permissions/export")
    @Operation(tags = "可视权限EXCEL导出", description = "导出全部可视权限为EXCEL文件")
    @AutoLog(value = "可视权限EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<ViewPermissionDTO> data = viewPermissionService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("可视权限一览表", "可视权限"), ViewPermissionDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "可视权限_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /view-permissions/import : import the viewPermissions from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the viewPermissionDTO, or with status 404 (Not Found)
     */
    @PostMapping("/view-permissions/import")
    @Operation(tags = "可视权限EXCEL导入", description = "根据可视权限EXCEL文件导入全部数据")
    @AutoLog(value = "可视权限EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<ViewPermissionDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), ViewPermissionDTO.class, params);
        list.forEach(viewPermissionService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /view-permissions/tree : get all the viewPermissions for parent is null.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of viewPermissions in body
     */
    @GetMapping("/view-permissions/tree")
    @Operation(tags = "获取可视权限树形列表", description = "获取可视权限的树形列表数据")
    @AutoLog(value = "获取可视权限树形列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<ViewPermissionDTO>> getAllViewPermissionsofTree(ViewPermissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get a page of ViewPermissions");
        Page<ViewPermissionDTO> page = viewPermissionQueryService.findAllTop(criteria, pageable);
        PageRecord<ViewPermissionDTO> result = new PageRecord<>();
        result.records(page.getContent()).size((int) page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * GET  /view-permissions/{parentId}/tree : get all the viewPermissions for parent is parentId.
     *
     * @param parentId the parent of Id
     * @return the ResponseEntity with status 200 (OK) and the list of viewPermissions in body
     */
    @GetMapping("/view-permissions/{parentId}/tree")
    @Operation(tags = "获取可视权限指定节点下的树形列表", description = "获取可视权限指定节点下的树形列表数据")
    @AutoLog(value = "获取可视权限指定节点下的树形列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<List<ViewPermissionDTO>> getAllViewPermissionsofParent(@PathVariable Long parentId) {
        log.debug("REST request to get all ViewPermissions of parentId");
        List<ViewPermissionDTO> list = viewPermissionQueryService.findChildrenByParentId(parentId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code DELETE  /view-permissions} : delete all the "ids" ViewPermissions.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/view-permissions")
    @Operation(tags = "删除多个可视权限", description = "根据主键删除多个可视权限")
    @AutoLog(value = "删除多个可视权限", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteViewPermissionsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete ViewPermissions : {}", ids);
        if (ids != null) {
            ids.forEach(viewPermissionService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/view-permissions/stats")
    @Operation(tags = "根据条件对可视权限进行统计", description = "条件和统计的配置通过可视权限的Criteria类来实现")
    @AutoLog(value = "根据条件对可视权限进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(ViewPermissionCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = viewPermissionQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }

    /**
     * {@code GET  /view-permissions/current-user} : get all the viewPermissions of currentUser.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewPermissions in body.
     */
    @GetMapping("/view-permissions/current-user")
    public ResponseEntity<List<ViewPermissionDTO>> getAllViewPermissionsByLogin() {
        log.debug("REST request to get ViewPermissions by current-user");
        return ResponseEntity.ok().body(viewPermissionService.getAllByLogin());
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
