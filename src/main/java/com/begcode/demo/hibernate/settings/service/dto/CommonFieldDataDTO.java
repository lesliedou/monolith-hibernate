package com.begcode.demo.hibernate.settings.service.dto;

import com.begcode.demo.hibernate.domain.enumeration.CommonFieldType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**

 * {@link com.begcode.demo.hibernate.settings.domain.CommonFieldData}的DTO。
 */
@Schema(description = "通用字段数据")
@Data
@ToString
@EqualsAndHashCode
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommonFieldDataDTO implements Serializable {

    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 字段值
     */
    @Schema(description = "字段值")
    private String value;

    /**
     * 字段标题
     */
    @Schema(description = "字段标题")
    private String label;

    /**
     * 字段类型
     */
    @Schema(description = "字段类型")
    private CommonFieldType valueType;

    /**
     * 说明
     */
    @Schema(description = "说明")
    private String remark;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sortValue;

    /**
     * 是否禁用
     */
    @Schema(description = "是否禁用")
    private Boolean disabled;

    /**
     * 实体名称
     */
    @Schema(description = "实体名称")
    private String ownerEntityName;

    /**
     * 使用实体ID
     */
    @Schema(description = "使用实体ID")
    private String ownerEntityId;

    // jhipster-needle-dto-add-field - JHipster will add fields here, do not remove

    public CommonFieldDataDTO id(Long id) {
        this.id = id;
        return this;
    }

    public CommonFieldDataDTO name(String name) {
        this.name = name;
        return this;
    }

    public CommonFieldDataDTO value(String value) {
        this.value = value;
        return this;
    }

    public CommonFieldDataDTO label(String label) {
        this.label = label;
        return this;
    }

    public CommonFieldDataDTO valueType(CommonFieldType valueType) {
        this.valueType = valueType;
        return this;
    }

    public CommonFieldDataDTO remark(String remark) {
        this.remark = remark;
        return this;
    }

    public CommonFieldDataDTO sortValue(Integer sortValue) {
        this.sortValue = sortValue;
        return this;
    }

    public CommonFieldDataDTO disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public CommonFieldDataDTO ownerEntityName(String ownerEntityName) {
        this.ownerEntityName = ownerEntityName;
        return this;
    }

    public CommonFieldDataDTO ownerEntityId(String ownerEntityId) {
        this.ownerEntityId = ownerEntityId;
        return this;
    }
    // jhipster-needle-dto-add-getters-setters - JHipster will add getters and setters here, do not remove

}
