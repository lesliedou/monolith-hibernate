package com.begcode.demo.hibernate.settings.domain;

import com.begcode.demo.hibernate.domain.enumeration.ResetFrequency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 填充规则
 */
@Entity
@Table(name = "sys_fill_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SysFillRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 规则名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 规则Code
     */
    @Column(name = "code")
    private String code;

    /**
     * 规则描述
     */
    @Column(name = "jhi_desc")
    private String desc;

    /**
     * 是否启用
     */
    @Column(name = "enabled")
    private Boolean enabled;

    /**
     * 重置频率
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "reset_frequency")
    private ResetFrequency resetFrequency;

    /**
     * 序列值
     */
    @Column(name = "seq_value")
    private Long seqValue;

    /**
     * 生成值
     */
    @Column(name = "fill_value")
    private String fillValue;

    /**
     * 规则实现类
     */
    @Column(name = "impl_class")
    private String implClass;

    /**
     * 规则参数
     */
    @Column(name = "params")
    private String params;

    /**
     * 重置开始日期
     */
    @Column(name = "reset_start_time")
    private ZonedDateTime resetStartTime;

    /**
     * 重置结束日期
     */
    @Column(name = "reset_end_time")
    private ZonedDateTime resetEndTime;

    /**
     * 重置时间
     */
    @Column(name = "reset_time")
    private ZonedDateTime resetTime;

    /**
     * 配置项列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fillRule")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fillRule" }, allowSetters = true)
    private Set<FillRuleItem> ruleItems = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SysFillRule id(Long id) {
        this.setId(id);
        return this;
    }

    public SysFillRule name(String name) {
        this.setName(name);
        return this;
    }

    public SysFillRule code(String code) {
        this.setCode(code);
        return this;
    }

    public SysFillRule desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public SysFillRule enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public SysFillRule resetFrequency(ResetFrequency resetFrequency) {
        this.setResetFrequency(resetFrequency);
        return this;
    }

    public SysFillRule seqValue(Long seqValue) {
        this.setSeqValue(seqValue);
        return this;
    }

    public SysFillRule fillValue(String fillValue) {
        this.setFillValue(fillValue);
        return this;
    }

    public SysFillRule implClass(String implClass) {
        this.setImplClass(implClass);
        return this;
    }

    public SysFillRule params(String params) {
        this.setParams(params);
        return this;
    }

    public SysFillRule resetStartTime(ZonedDateTime resetStartTime) {
        this.setResetStartTime(resetStartTime);
        return this;
    }

    public SysFillRule resetEndTime(ZonedDateTime resetEndTime) {
        this.setResetEndTime(resetEndTime);
        return this;
    }

    public SysFillRule resetTime(ZonedDateTime resetTime) {
        this.setResetTime(resetTime);
        return this;
    }

    public void setRuleItems(Set<FillRuleItem> fillRuleItems) {
        if (this.ruleItems != null) {
            this.ruleItems.forEach(i -> i.setFillRule(null));
        }
        if (fillRuleItems != null) {
            fillRuleItems.forEach(i -> i.setFillRule(this));
        }
        this.ruleItems = fillRuleItems;
    }

    public SysFillRule ruleItems(Set<FillRuleItem> fillRuleItems) {
        this.setRuleItems(fillRuleItems);
        return this;
    }

    public SysFillRule addRuleItems(FillRuleItem fillRuleItem) {
        this.ruleItems.add(fillRuleItem);
        fillRuleItem.setFillRule(this);
        return this;
    }

    public SysFillRule removeRuleItems(FillRuleItem fillRuleItem) {
        this.ruleItems.remove(fillRuleItem);
        fillRuleItem.setFillRule(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysFillRule)) {
            return false;
        }
        return id != null && id.equals(((SysFillRule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
