package com.begcode.demo.hibernate.settings.domain;

import com.begcode.demo.hibernate.domain.enumeration.CommonFieldType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 通用字段数据
 */
@Entity
@Table(name = "common_field_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommonFieldData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 字段值
     */
    @Column(name = "value")
    private String value;

    /**
     * 字段标题
     */
    @Column(name = "label")
    private String label;

    /**
     * 字段类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "value_type")
    private CommonFieldType valueType;

    /**
     * 说明
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 排序
     */
    @Column(name = "sort_value")
    private Integer sortValue;

    /**
     * 是否禁用
     */
    @Column(name = "disabled")
    private Boolean disabled;

    /**
     * 实体名称
     */
    @Column(name = "owner_entity_name")
    private String ownerEntityName;

    /**
     * 使用实体ID
     */
    @Column(name = "owner_entity_id")
    private String ownerEntityId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public CommonFieldData id(Long id) {
        this.setId(id);
        return this;
    }

    public CommonFieldData name(String name) {
        this.setName(name);
        return this;
    }

    public CommonFieldData value(String value) {
        this.setValue(value);
        return this;
    }

    public CommonFieldData label(String label) {
        this.setLabel(label);
        return this;
    }

    public CommonFieldData valueType(CommonFieldType valueType) {
        this.setValueType(valueType);
        return this;
    }

    public CommonFieldData remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public CommonFieldData sortValue(Integer sortValue) {
        this.setSortValue(sortValue);
        return this;
    }

    public CommonFieldData disabled(Boolean disabled) {
        this.setDisabled(disabled);
        return this;
    }

    public CommonFieldData ownerEntityName(String ownerEntityName) {
        this.setOwnerEntityName(ownerEntityName);
        return this;
    }

    public CommonFieldData ownerEntityId(String ownerEntityId) {
        this.setOwnerEntityId(ownerEntityId);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommonFieldData)) {
            return false;
        }
        return id != null && id.equals(((CommonFieldData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
