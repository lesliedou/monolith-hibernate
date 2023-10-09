package com.begcode.demo.hibernate.settings.domain;

import com.begcode.demo.hibernate.domain.Owner;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据字典
 */
@Entity
@Table(name = "dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dictionary implements Serializable, Owner {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 字典名称
     */
    @NotNull
    @Column(name = "dict_name", nullable = false)
    private String dictName;

    /**
     * 字典Key
     */
    @NotNull
    @Column(name = "dict_key", nullable = false, unique = true)
    private String dictKey;

    /**
     * 是否禁用
     */
    @Column(name = "disabled")
    private Boolean disabled;

    /**
     * 排序
     */
    @Column(name = "sort_value")
    private Integer sortValue;

    /**
     * 是否内置
     */
    @Column(name = "built_in")
    private Boolean builtIn;

    /**
     * 更新枚举
     */
    @Column(name = "sync_enum")
    private Boolean syncEnum;

    /**
     * 字典项列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dictionary")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommonFieldData> items = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Dictionary id(Long id) {
        this.setId(id);
        return this;
    }

    public Dictionary dictName(String dictName) {
        this.setDictName(dictName);
        return this;
    }

    public Dictionary dictKey(String dictKey) {
        this.setDictKey(dictKey);
        return this;
    }

    public Dictionary disabled(Boolean disabled) {
        this.setDisabled(disabled);
        return this;
    }

    public Dictionary sortValue(Integer sortValue) {
        this.setSortValue(sortValue);
        return this;
    }

    public Dictionary builtIn(Boolean builtIn) {
        this.setBuiltIn(builtIn);
        return this;
    }

    public Dictionary syncEnum(Boolean syncEnum) {
        this.setSyncEnum(syncEnum);
        return this;
    }

    public void setItems(Set<CommonFieldData> commonFieldData) {
        this.items = commonFieldData;
    }

    public Dictionary items(Set<CommonFieldData> commonFieldData) {
        this.setItems(commonFieldData);
        return this;
    }

    public Dictionary addItems(CommonFieldData commonFieldData) {
        this.items.add(commonFieldData);
        commonFieldData.setOwner(this);
        return this;
    }

    public Dictionary removeItems(CommonFieldData commonFieldData) {
        this.items.remove(commonFieldData);
        commonFieldData.setOwner(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dictionary)) {
            return false;
        }
        return id != null && id.equals(((Dictionary) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
