package com.begcode.demo.hibernate.settings.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import com.begcode.demo.hibernate.domain.Owner;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 网站配置
 */
@Entity
@Table(name = "site_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteConfig extends AbstractAuditingEntity<Long, SiteConfig> implements Serializable, Owner {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 分类名称
     */
    @NotNull
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    /**
     * 分类Key
     */
    @NotNull
    @Column(name = "category_key", nullable = false)
    private String categoryKey;

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
     * 配置项列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "siteConfig")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CommonFieldData> items = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SiteConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public SiteConfig categoryName(String categoryName) {
        this.setCategoryName(categoryName);
        return this;
    }

    public SiteConfig categoryKey(String categoryKey) {
        this.setCategoryKey(categoryKey);
        return this;
    }

    public SiteConfig disabled(Boolean disabled) {
        this.setDisabled(disabled);
        return this;
    }

    public SiteConfig sortValue(Integer sortValue) {
        this.setSortValue(sortValue);
        return this;
    }

    public SiteConfig builtIn(Boolean builtIn) {
        this.setBuiltIn(builtIn);
        return this;
    }

    public void setItems(Set<CommonFieldData> commonFieldData) {
        this.items = commonFieldData;
    }

    public SiteConfig items(Set<CommonFieldData> commonFieldData) {
        this.setItems(commonFieldData);
        return this;
    }

    public SiteConfig addItems(CommonFieldData commonFieldData) {
        this.items.add(commonFieldData);
        commonFieldData.setOwner(this);
        return this;
    }

    public SiteConfig removeItems(CommonFieldData commonFieldData) {
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
        if (!(o instanceof SiteConfig)) {
            return false;
        }
        return id != null && id.equals(((SiteConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
