package com.begcode.demo.hibernate.web.rest.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.begcode.demo.hibernate.aop.logging.AutoLog;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import com.begcode.demo.hibernate.repository.AuthorityRepository;
import com.begcode.demo.hibernate.service.AuthorityQueryService;
import com.begcode.demo.hibernate.service.AuthorityService;
import com.begcode.demo.hibernate.service.criteria.AuthorityCriteria;
import com.begcode.demo.hibernate.service.dto.AuthorityDTO;
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

 * 管理实体{@link com.begcode.demo.hibernate.domain.Authority}的REST Controller。
 */
public class AuthorityBaseResource {

    protected final Logger log = LoggerFactory.getLogger(AuthorityBaseResource.class);

    protected static final String ENTITY_NAME = "systemAuthority";

    @Value("${jhipster.clientApp.name}")
    protected String applicationName;

    protected final AuthorityService authorityService;

    protected final AuthorityRepository authorityRepository;

    protected final AuthorityQueryService authorityQueryService;

    public AuthorityBaseResource(
        AuthorityService authorityService,
        AuthorityRepository authorityRepository,
        AuthorityQueryService authorityQueryService
    ) {
        this.authorityService = authorityService;
        this.authorityRepository = authorityRepository;
        this.authorityQueryService = authorityQueryService;
    }

    /**
     * {@code POST  /authorities} : Create a new authority.
     *
     * @param authorityDTO the authorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authorityDTO, or with status {@code 400 (Bad Request)} if the authority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authorities")
    @Operation(tags = "新建角色", description = "创建并返回一个新的角色")
    @AutoLog(value = "新建角色", logType = LogType.OPERATE, operateType = OperateType.ADD)
    public ResponseEntity<AuthorityDTO> createAuthority(@RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to save Authority : {}", authorityDTO);
        if (authorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new authority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuthorityDTO result = authorityService.save(authorityDTO);
        return ResponseEntity
            .created(new URI("/api/authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /authorities/:id} : Updates an existing authority.
     *
     * @param id the id of the authorityDTO to save.
     * @param authorityDTO the authorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorityDTO,
     * or with status {@code 400 (Bad Request)} if the authorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authorityDTO couldn't be updated.
     */
    @PutMapping("/authorities/{id}")
    @Operation(tags = "更新角色", description = "根据主键更新并返回一个更新后的角色")
    @AutoLog(value = "更新角色", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<AuthorityDTO> updateAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthorityDTO authorityDTO,
        @RequestParam(value = "batchIds", required = false) List<Long> batchIds,
        @RequestParam(value = "batchFields", required = false) List<String> batchFields
    ) {
        log.debug("REST request to update Authority : {}, {}", id, authorityDTO);
        if (authorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuthorityDTO result = null;
        if (CollectionUtils.isNotEmpty(batchFields) && CollectionUtils.isNotEmpty(batchIds)) {
            batchIds.add(id);
            authorityService.updateBatch(authorityDTO, batchFields, batchIds);
            result = authorityService.findOne(id).orElseThrow();
        } else {
            result = authorityService.update(authorityDTO);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, authorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /authorities/:id} : Partial updates given fields of an existing authority, field will ignore if it is null
     *
     * @param id the id of the authorityDTO to save.
     * @param authorityDTO the authorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authorityDTO,
     * or with status {@code 400 (Bad Request)} if the authorityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the authorityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the authorityDTO couldn't be updated.
     */
    @PatchMapping(value = "/authorities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @Operation(tags = "部分更新角色", description = "根据主键及实体信息实现部分更新，值为null的属性将忽略，并返回一个更新后的角色")
    @AutoLog(value = "部分更新角色", logType = LogType.OPERATE, operateType = OperateType.EDIT)
    public ResponseEntity<AuthorityDTO> partialUpdateAuthority(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthorityDTO authorityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Authority partially : {}, {}", id, authorityDTO);
        if (authorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authorityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authorityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthorityDTO> result = authorityService.partialUpdate(authorityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, authorityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /authorities} : get all the authorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authorities in body.
     */
    @GetMapping("/authorities")
    @Operation(tags = "获取角色分页列表", description = "获取角色的分页列表数据")
    @AutoLog(value = "获取角色分页列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<AuthorityDTO>> getAllAuthorities(
        AuthorityCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Authorities by criteria: {}", criteria);

        Page<AuthorityDTO> page;
        page = authorityQueryService.findByCriteria(criteria, pageable);
        PageRecord<AuthorityDTO> result = new PageRecord<>();
        result.records(page.getContent()).size(page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * {@code GET  /authorities/count} : count all the authorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/authorities/count")
    public ResponseEntity<Long> countAuthorities(AuthorityCriteria criteria) {
        log.debug("REST request to count Authorities by criteria: {}", criteria);
        return ResponseEntity.ok().body(authorityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /authorities/:id} : get the "id" authority.
     *
     * @param id the id of the authorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/authorities/{id}")
    @Operation(tags = "获取指定主键的角色", description = "获取指定主键的角色信息")
    @AutoLog(value = "获取指定主键的角色", logType = LogType.OPERATE, operateType = OperateType.VIEW)
    public ResponseEntity<AuthorityDTO> getAuthority(@PathVariable Long id) {
        log.debug("REST request to get Authority : {}", id);
        Optional<AuthorityDTO> authorityDTO = authorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authorityDTO);
    }

    /**
     * {@code DELETE  /authorities/:id} : delete the "id" authority.
     *
     * @param id the id of the authorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/authorities/{id}")
    @Operation(tags = "删除指定主键的角色", description = "删除指定主键的角色信息")
    @AutoLog(value = "删除指定主键的角色", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        log.debug("REST request to delete Authority : {}", id);

        authorityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * GET  /authorities/export : export the authorities.
     *
     */
    @GetMapping("/authorities/export")
    @Operation(tags = "角色EXCEL导出", description = "导出全部角色为EXCEL文件")
    @AutoLog(value = "角色EXCEL导出", logType = LogType.OPERATE, operateType = OperateType.EXPORT)
    public void exportToExcel(HttpServletResponse response) {
        List<AuthorityDTO> data = authorityService.findAll(Pageable.unpaged()).getContent();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("角色一览表", "角色"), AuthorityDTO.class, data);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "角色_" + sdf.format(new Date()) + ".xlsx";
        try {
            ExportUtil.excel(workbook, filename, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * POST  /authorities/import : import the authorities from excel file.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the authorityDTO, or with status 404 (Not Found)
     */
    @PostMapping("/authorities/import")
    @Operation(tags = "角色EXCEL导入", description = "根据角色EXCEL文件导入全部数据")
    @AutoLog(value = "角色EXCEL导入", logType = LogType.OPERATE, operateType = OperateType.IMPORT)
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<AuthorityDTO> list = ExcelImportUtil.importExcel(file.getInputStream(), AuthorityDTO.class, params);
        list.forEach(authorityService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /authorities/tree : get all the authorities for parent is null.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities/tree")
    @Operation(tags = "获取角色树形列表", description = "获取角色的树形列表数据")
    @AutoLog(value = "获取角色树形列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<PageRecord<AuthorityDTO>> getAllAuthoritiesofTree(AuthorityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get a page of Authorities");
        Page<AuthorityDTO> page = authorityQueryService.findAllTop(criteria, pageable);
        PageRecord<AuthorityDTO> result = new PageRecord<>();
        result.records(page.getContent()).size((int) page.getSize()).total(page.getTotalElements()).page(pageable.getPageNumber());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result);
    }

    /**
     * GET  /authorities/{parentId}/tree : get all the authorities for parent is parentId.
     *
     * @param parentId the parent of Id
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities/{parentId}/tree")
    @Operation(tags = "获取角色指定节点下的树形列表", description = "获取角色指定节点下的树形列表数据")
    @AutoLog(value = "获取角色指定节点下的树形列表", logType = LogType.OPERATE, operateType = OperateType.LIST)
    public ResponseEntity<List<AuthorityDTO>> getAllAuthoritiesofParent(@PathVariable Long parentId) {
        log.debug("REST request to get all Authorities of parentId");
        List<AuthorityDTO> list = authorityQueryService.findChildrenByParentId(parentId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code DELETE  /authorities} : delete all the "ids" Authorities.
     *
     * @param ids the ids of the articleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/authorities")
    @Operation(tags = "删除多个角色", description = "根据主键删除多个角色")
    @AutoLog(value = "删除多个角色", logType = LogType.OPERATE, operateType = OperateType.DELETE)
    public ResponseEntity<Void> deleteAuthoritiesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete Authorities : {}", ids);
        if (ids != null) {
            ids.forEach(authorityService::delete);
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds")))
            .build();
    }

    @GetMapping("/authorities/stats")
    @Operation(tags = "根据条件对角色进行统计", description = "条件和统计的配置通过角色的Criteria类来实现")
    @AutoLog(value = "根据条件对角色进行统计", logType = LogType.OPERATE, operateType = OperateType.STATS)
    public ResponseEntity<List<Map<String, Object>>> stats(AuthorityCriteria criteria) {
        log.debug("REST request to get stats by criteria: {}", criteria);
        List<Map<String, Object>> statsMapList = authorityQueryService.statsByAggregateCriteria(criteria);
        return ResponseEntity.ok().body(statsMapList);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

}
