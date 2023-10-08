package com.begcode.demo.hibernate.settings.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.settings.repository.DictionaryRepository;
import com.begcode.demo.hibernate.settings.service.DictionaryQueryService;
import com.begcode.demo.hibernate.settings.service.DictionaryService;
import com.begcode.demo.hibernate.settings.service.criteria.DictionaryCriteria;
import com.begcode.demo.hibernate.settings.service.dto.DictionaryDTO;
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

 * 管理实体{@link com.begcode.demo.hibernate.settings.domain.Dictionary}的REST Controller。
 */
public class DictionaryBaseResource {

    protected final Logger log = LoggerFactory.getLogger(DictionaryBaseResource.class);

    protected static final String ENTITY_NAME = "settingsDictionary";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final DictionaryService dictionaryService;

    protected final DictionaryRepository dictionaryRepository;

    protected final DictionaryQueryService dictionaryQueryService;

    public DictionaryBaseResource(
        DictionaryService dictionaryService,
        DictionaryRepository dictionaryRepository,
        DictionaryQueryService dictionaryQueryService
    ) {
        this.dictionaryService = dictionaryService;
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryQueryService = dictionaryQueryService;
    }

    /**
     * {@code POST  /dictionaries} : Create a new dictionary.
     *
     * @param dictionaryDTO the dictionaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dictionaryDTO, or with status {@code 400 (Bad Request)} if the dictionary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dictionaries")
    @Operation(tags = "新建数据字典", description = "创建并返回一个新的数据字典")
    @AutoLog(value = "新建数据字典", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<DictionaryDTO> createDictionary(@Valid @RequestBody DictionaryDTO dictionaryDTO) throws URISyntaxException {
        log.debug("REST request to save Dictionary : {}", dictionaryDTO);
        if (dictionaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new dictionary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DictionaryDTO result = dictionaryService.save(dictionaryDTO);
        return ResponseEntity
            .created(new URI("/api/dictionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dictionaries/:id} : Updates an existing dictionary.
     *
     * @param id the id of the dictionaryDTO to save.
     * @param dictionaryDTO the dictionaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dictionaryDTO,
     * or with status {@code 400 (Bad Request)} if the dictionaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dictionaryDTO couldn't be updated.
     */
    @PutMapping("/dictionaries/{id}")
    @Operation(tags = "更新数据字典", description = "根据主键更新并返回一个更新后的数据字典")
    @AutoLog(value = "更新数据字典", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<DictionaryDTO> updateDictionary(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DictionaryDTO dictionaryDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update Dictionary : {}, {}", id, dictionaryDTO);
        if (dictionaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dictionaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dictionaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DictionaryDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            dictionaryService.updateBatch(dictionaryDTO, batchFields, batchIds);
            result = dictionaryService.findOne(id).orElseThrow();
        } else {
            result = dictionaryService.update(dictionaryDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dictionaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dictionaries/:id} : Partial updates given fields of an existing dictionary, field will ignore if it is null
     *
     * @param id the id of the dictionaryDTO to save.
     * @param dictionaryDTO the dictionaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dictionaryDTO,
     * or with status {@code 400 (Bad Request)} if the dictionaryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dictionaryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dictionaryDTO couldn't be updated.
     */
    @PatchMapping(value = "/dictionaries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(tags = "部分更新数据字典", description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的数据字典")
    @AutoLog(value = "部分更新数据字典", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<DictionaryDTO> partialUpdateDictionary(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DictionaryDTO dictionaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dictionary partially : {}, {}", id, dictionaryDTO);
        if (dictionaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dictionaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dictionaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DictionaryDTO> result = dictionaryService.partialUpdate(dictionaryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dictionaryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dictionaries} : get all the dictionaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dictionaries in body.
     */
    @GetMapping("/dictionaries")
    @Operation(tags = "获取数据字典分页列表", description = "获取数据字典的分页列表数据")
    @AutoLog(value = "获取数据字典分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<DictionaryDTO>> getAllDictionaries(
        DictionaryCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Dictionaries by criteria: {}", criteria);

        Page<DictionaryDTO> page;
        page = dictionaryQueryService.findByCriteria(criteria, pageable);
        PageRecord<DictionaryDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /dictionaries/count} : count all the dictionaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/dictionaries/count")
    public ResponseEntity<Long> countDictionaries(DictionaryCriteria criteria) {
        log.debug("REST request to count Dictionaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(dictionaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /dictionaries/:id} : get the "id" dictionary.
     *
     * @param id the id of the dictionaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dictionaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dictionaries/{id}")
    @Operation(tags = "获取指定主键的数据字典", description = "获取指定主键的数据字典信息")
    @AutoLog(value = "获取指定主键的数据字典", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<DictionaryDTO> getDictionary(@PathVariable Long id) {
        log.debug("REST request to get Dictionary : {}", id);
        Optional<DictionaryDTO> dictionaryDTO = dictionaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dictionaryDTO);
    }

    /**
     * {@code DELETE  /dictionaries/:id} : delete the "id" dictionary.
     *
     * @param id the id of the dictionaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dictionaries/{id}")
    @Operation(tags = "删除指定主键的数据字典", description = "删除指定主键的数据字典信息")
    @AutoLog(value = "删除指定主键的数据字典", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteDictionary(@PathVariable Long id) {
        log.debug("REST request to delete Dictionary : {}", id);

        dictionaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /dictionaries/export : export the dictionaries.
     *
     */
    @GetMapping("/dictionaries/export")
    @Operation(tags = "数据字典EXCEL导出", description = "导出全部数据字典为EXCEL文件")
    @AutoLog(value = "数据字典EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<DictionaryDTO> data = dictionaryService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("数据字典一览表", "数据字典"), DictionaryDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "数据字典_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /dictionaries/import : import the dictionaries from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the dictionaryDTO, or with status 404 (Not Found)
     */
    @PostMapping("/dictionaries/import")
    @Operation(tags = "数据字典EXCEL导入", description = "根据数据字典EXCEL文件导入全部数据")
    @AutoLog(value = "数据字典EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<DictionaryDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), DictionaryDTO.class, params);
        list.forEach(dictionaryService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /dictionaries} : delete all the "ids" Dictionaries.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dictionaries")
    @Operation(tags = "删除多个数据字典", description = "根据主键删除多个数据字典")
    @AutoLog(value = "删除多个数据字典", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteDictionariesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete Dictionaries : {}", ids);
        if (ids != null) {
            ids.forEach(dictionaryService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/dictionaries/stats")
    @Operation(tags = "根据条件对数据字典进行统计", description = "条件和统计的配置通过数据字典的Criteria类来实现")
    @AutoLog(value = "根据条件对数据字典进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(DictionaryCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = dictionaryQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
