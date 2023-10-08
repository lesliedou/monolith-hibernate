package com.begcode.demo.hibernate.settings.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.settings.repository.FillRuleItemRepository;
import com.begcode.demo.hibernate.settings.service.FillRuleItemQueryService;
import com.begcode.demo.hibernate.settings.service.FillRuleItemService;
import com.begcode.demo.hibernate.settings.service.criteria.FillRuleItemCriteria;
import com.begcode.demo.hibernate.settings.service.dto.FillRuleItemDTO;
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

 * 管理实体{@link com.begcode.demo.hibernate.settings.domain.FillRuleItem}的REST Controller。
 */
public class FillRuleItemBaseResource {

    protected final Logger log = LoggerFactory.getLogger(FillRuleItemBaseResource.class);

    protected static final String ENTITY_NAME = "settingsFillRuleItem";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final FillRuleItemService fillRuleItemService;

    protected final FillRuleItemRepository fillRuleItemRepository;

    protected final FillRuleItemQueryService fillRuleItemQueryService;

    public FillRuleItemBaseResource(
        FillRuleItemService fillRuleItemService,
        FillRuleItemRepository fillRuleItemRepository,
        FillRuleItemQueryService fillRuleItemQueryService
    ) {
        this.fillRuleItemService = fillRuleItemService;
        this.fillRuleItemRepository = fillRuleItemRepository;
        this.fillRuleItemQueryService = fillRuleItemQueryService;
    }

    /**
     * {@code POST  /fill-rule-items} : Create a new fillRuleItem.
     *
     * @param fillRuleItemDTO the fillRuleItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fillRuleItemDTO, or with status {@code 400 (Bad Request)} if the fillRuleItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fill-rule-items")
    @Operation(tags = "新建填充规则条目", description = "创建并返回一个新的填充规则条目")
    @AutoLog(value = "新建填充规则条目", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<FillRuleItemDTO> createFillRuleItem(@RequestBody FillRuleItemDTO fillRuleItemDTO) throws URISyntaxException {
        log.debug("REST request to save FillRuleItem : {}", fillRuleItemDTO);
        if (fillRuleItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new fillRuleItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FillRuleItemDTO result = fillRuleItemService.save(fillRuleItemDTO);
        return ResponseEntity
            .created(new URI("/api/fill-rule-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fill-rule-items/:id} : Updates an existing fillRuleItem.
     *
     * @param id the id of the fillRuleItemDTO to save.
     * @param fillRuleItemDTO the fillRuleItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fillRuleItemDTO,
     * or with status {@code 400 (Bad Request)} if the fillRuleItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fillRuleItemDTO couldn't be updated.
     */
    @PutMapping("/fill-rule-items/{id}")
    @Operation(tags = "更新填充规则条目", description = "根据主键更新并返回一个更新后的填充规则条目")
    @AutoLog(value = "更新填充规则条目", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<FillRuleItemDTO> updateFillRuleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FillRuleItemDTO fillRuleItemDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update FillRuleItem : {}, {}", id, fillRuleItemDTO);
        if (fillRuleItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fillRuleItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fillRuleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FillRuleItemDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            fillRuleItemService.updateBatch(fillRuleItemDTO, batchFields, batchIds);
            result = fillRuleItemService.findOne(id).orElseThrow();
        } else {
            result = fillRuleItemService.update(fillRuleItemDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fillRuleItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fill-rule-items/:id} : Partial updates given fields of an existing fillRuleItem, field will ignore if it is null
     *
     * @param id the id of the fillRuleItemDTO to save.
     * @param fillRuleItemDTO the fillRuleItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fillRuleItemDTO,
     * or with status {@code 400 (Bad Request)} if the fillRuleItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fillRuleItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fillRuleItemDTO couldn't be updated.
     */
    @PatchMapping(value = "/fill-rule-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(
        tags = "部分更新填充规则条目",
        description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的填充规则条目"
    )
    @AutoLog(value = "部分更新填充规则条目", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<FillRuleItemDTO> partialUpdateFillRuleItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FillRuleItemDTO fillRuleItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FillRuleItem partially : {}, {}", id, fillRuleItemDTO);
        if (fillRuleItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fillRuleItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fillRuleItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FillRuleItemDTO> result = fillRuleItemService.partialUpdate(fillRuleItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fillRuleItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fill-rule-items} : get all the fillRuleItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fillRuleItems in body.
     */
    @GetMapping("/fill-rule-items")
    @Operation(tags = "获取填充规则条目分页列表", description = "获取填充规则条目的分页列表数据")
    @AutoLog(value = "获取填充规则条目分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<FillRuleItemDTO>> getAllFillRuleItems(
        FillRuleItemCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FillRuleItems by criteria: {}", criteria);

        Page<FillRuleItemDTO> page;
        page = fillRuleItemQueryService.findByCriteria(criteria, pageable);
        PageRecord<FillRuleItemDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /fill-rule-items/count} : count all the fillRuleItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fill-rule-items/count")
    public ResponseEntity<Long> countFillRuleItems(FillRuleItemCriteria criteria) {
        log.debug("REST request to count FillRuleItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(fillRuleItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fill-rule-items/:id} : get the "id" fillRuleItem.
     *
     * @param id the id of the fillRuleItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fillRuleItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fill-rule-items/{id}")
    @Operation(tags = "获取指定主键的填充规则条目", description = "获取指定主键的填充规则条目信息")
    @AutoLog(value = "获取指定主键的填充规则条目", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<FillRuleItemDTO> getFillRuleItem(@PathVariable Long id) {
        log.debug("REST request to get FillRuleItem : {}", id);
        Optional<FillRuleItemDTO> fillRuleItemDTO = fillRuleItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fillRuleItemDTO);
    }

    /**
     * {@code DELETE  /fill-rule-items/:id} : delete the "id" fillRuleItem.
     *
     * @param id the id of the fillRuleItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fill-rule-items/{id}")
    @Operation(tags = "删除指定主键的填充规则条目", description = "删除指定主键的填充规则条目信息")
    @AutoLog(value = "删除指定主键的填充规则条目", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteFillRuleItem(@PathVariable Long id) {
        log.debug("REST request to delete FillRuleItem : {}", id);

        fillRuleItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /fill-rule-items/export : export the fillRuleItems.
     *
     */
    @GetMapping("/fill-rule-items/export")
    @Operation(tags = "填充规则条目EXCEL导出", description = "导出全部填充规则条目为EXCEL文件")
    @AutoLog(value = "填充规则条目EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<FillRuleItemDTO> data = fillRuleItemService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(
            new ExportParams("填充规则条目一览表", "填充规则条目"),
            FillRuleItemDTO.class,
            data
        );
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "填充规则条目_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /fill-rule-items/import : import the fillRuleItems from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the fillRuleItemDTO, or with status 404 (Not Found)
     */
    @PostMapping("/fill-rule-items/import")
    @Operation(tags = "填充规则条目EXCEL导入", description = "根据填充规则条目EXCEL文件导入全部数据")
    @AutoLog(value = "填充规则条目EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<FillRuleItemDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), FillRuleItemDTO.class, params);
        list.forEach(fillRuleItemService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /fill-rule-items} : delete all the "ids" FillRuleItems.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fill-rule-items")
    @Operation(tags = "删除多个填充规则条目", description = "根据主键删除多个填充规则条目")
    @AutoLog(value = "删除多个填充规则条目", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteFillRuleItemsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete FillRuleItems : {}", ids);
        if (ids != null) {
            ids.forEach(fillRuleItemService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/fill-rule-items/stats")
    @Operation(tags = "根据条件对填充规则条目进行统计", description = "条件和统计的配置通过填充规则条目的Criteria类来实现")
    @AutoLog(value = "根据条件对填充规则条目进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(FillRuleItemCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = fillRuleItemQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
