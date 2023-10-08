package com.begcode.demo.hibernate.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.repository.PositionRepository;
import com.begcode.demo.hibernate.service.PositionQueryService;
import com.begcode.demo.hibernate.service.PositionService;
import com.begcode.demo.hibernate.service.criteria.PositionCriteria;
import com.begcode.demo.hibernate.service.dto.PositionDTO;
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

 * 管理实体{@link com.begcode.demo.hibernate.domain.Position}的REST Controller。
 */
public class PositionBaseResource {

    protected final Logger log = LoggerFactory.getLogger(PositionBaseResource.class);

    protected static final String ENTITY_NAME = "settingsPosition";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final PositionService positionService;

    protected final PositionRepository positionRepository;

    protected final PositionQueryService positionQueryService;

    public PositionBaseResource(
        PositionService positionService,
        PositionRepository positionRepository,
        PositionQueryService positionQueryService
    ) {
        this.positionService = positionService;
        this.positionRepository = positionRepository;
        this.positionQueryService = positionQueryService;
    }

    /**
     * {@code POST  /positions} : Create a new position.
     *
     * @param positionDTO the positionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new positionDTO, or with status {@code 400 (Bad Request)} if the position has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/positions")
    @Operation(tags = "新建岗位", description = "创建并返回一个新的岗位")
    @AutoLog(value = "新建岗位", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<PositionDTO> createPosition(@Valid @RequestBody PositionDTO positionDTO) throws URISyntaxException {
        log.debug("REST request to save Position : {}", positionDTO);
        if (positionDTO.getId() != null) {
            throw new BadRequestAlertException("A new position cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PositionDTO result = positionService.save(positionDTO);
        return ResponseEntity
            .created(new URI("/api/positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /positions/:id} : Updates an existing position.
     *
     * @param id the id of the positionDTO to save.
     * @param positionDTO the positionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated positionDTO,
     * or with status {@code 400 (Bad Request)} if the positionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the positionDTO couldn't be updated.
     */
    @PutMapping("/positions/{id}")
    @Operation(tags = "更新岗位", description = "根据主键更新并返回一个更新后的岗位")
    @AutoLog(value = "更新岗位", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<PositionDTO> updatePosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PositionDTO positionDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update Position : {}, {}", id, positionDTO);
        if (positionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, positionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!positionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PositionDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            positionService.updateBatch(positionDTO, batchFields, batchIds);
            result = positionService.findOne(id).orElseThrow();
        } else {
            result = positionService.update(positionDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, positionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /positions/:id} : Partial updates given fields of an existing position, field will ignore if it is null
     *
     * @param id the id of the positionDTO to save.
     * @param positionDTO the positionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated positionDTO,
     * or with status {@code 400 (Bad Request)} if the positionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the positionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the positionDTO couldn't be updated.
     */
    @PatchMapping(value = "/positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(tags = "部分更新岗位", description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的岗位")
    @AutoLog(value = "部分更新岗位", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<PositionDTO> partialUpdatePosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PositionDTO positionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Position partially : {}, {}", id, positionDTO);
        if (positionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, positionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!positionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PositionDTO> result = positionService.partialUpdate(positionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, positionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /positions} : get all the positions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of positions in body.
     */
    @GetMapping("/positions")
    @Operation(tags = "获取岗位分页列表", description = "获取岗位的分页列表数据")
    @AutoLog(value = "获取岗位分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<PositionDTO>> getAllPositions(
        PositionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Positions by criteria: {}", criteria);

        Page<PositionDTO> page;
        page = positionQueryService.findByCriteria(criteria, pageable);
        PageRecord<PositionDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /positions/count} : count all the positions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/positions/count")
    public ResponseEntity<Long> countPositions(PositionCriteria criteria) {
        log.debug("REST request to count Positions by criteria: {}", criteria);
        return ResponseEntity.ok().body(positionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /positions/:id} : get the "id" position.
     *
     * @param id the id of the positionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the positionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/positions/{id}")
    @Operation(tags = "获取指定主键的岗位", description = "获取指定主键的岗位信息")
    @AutoLog(value = "获取指定主键的岗位", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<PositionDTO> getPosition(@PathVariable Long id) {
        log.debug("REST request to get Position : {}", id);
        Optional<PositionDTO> positionDTO = positionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(positionDTO);
    }

    /**
     * {@code DELETE  /positions/:id} : delete the "id" position.
     *
     * @param id the id of the positionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/positions/{id}")
    @Operation(tags = "删除指定主键的岗位", description = "删除指定主键的岗位信息")
    @AutoLog(value = "删除指定主键的岗位", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        log.debug("REST request to delete Position : {}", id);

        positionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /positions/export : export the positions.
     *
     */
    @GetMapping("/positions/export")
    @Operation(tags = "岗位EXCEL导出", description = "导出全部岗位为EXCEL文件")
    @AutoLog(value = "岗位EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<PositionDTO> data = positionService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("岗位一览表", "岗位"), PositionDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "岗位_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /positions/import : import the positions from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the positionDTO, or with status 404 (Not Found)
     */
    @PostMapping("/positions/import")
    @Operation(tags = "岗位EXCEL导入", description = "根据岗位EXCEL文件导入全部数据")
    @AutoLog(value = "岗位EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<PositionDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), PositionDTO.class, params);
        list.forEach(positionService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /positions} : delete all the "ids" Positions.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/positions")
    @Operation(tags = "删除多个岗位", description = "根据主键删除多个岗位")
    @AutoLog(value = "删除多个岗位", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deletePositionsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete Positions : {}", ids);
        if (ids != null) {
            ids.forEach(positionService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/positions/stats")
    @Operation(tags = "根据条件对岗位进行统计", description = "条件和统计的配置通过岗位的Criteria类来实现")
    @AutoLog(value = "根据条件对岗位进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(PositionCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = positionQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
