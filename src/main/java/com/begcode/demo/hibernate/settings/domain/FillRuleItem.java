package com.begcode.demo.hibernate.settings.domain;

import com.begcode.demo.hibernate.domain.enumeration.FieldParamType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 填充规则条目
 */
@Entity
@Table(name = "fill_rule_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FillRuleItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 排序值
     */
    @Column(name = "sort_value")
    private Integer sortValue;

    /**
     * 字段参数类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "field_param_type")
    private FieldParamType fieldParamType;

    /**
     * 字段参数值
     */
    @Column(name = "field_param_value")
    private String fieldParamValue;

    /**
     * 日期格式
     */
    @Column(name = "date_pattern")
    private String datePattern;

    /**
     * 序列长度
     */
    @Column(name = "seq_length")
    private Integer seqLength;

    /**
     * 序列增量
     */
    @Column(name = "seq_increment")
    private Integer seqIncrement;

    /**
     * 序列起始值
     */
    @Column(name = "seq_start_value")
    private Integer seqStartValue;

    /**
     * 填充规则
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ruleItems" }, allowSetters = true)
    private SysFillRule fillRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public FillRuleItem id(Long id) {
        this.setId(id);
        return this;
    }

    public FillRuleItem sortValue(Integer sortValue) {
        this.setSortValue(sortValue);
        return this;
    }

    public FillRuleItem fieldParamType(FieldParamType fieldParamType) {
        this.setFieldParamType(fieldParamType);
        return this;
    }

    public FillRuleItem fieldParamValue(String fieldParamValue) {
        this.setFieldParamValue(fieldParamValue);
        return this;
    }

    public FillRuleItem datePattern(String datePattern) {
        this.setDatePattern(datePattern);
        return this;
    }

    public FillRuleItem seqLength(Integer seqLength) {
        this.setSeqLength(seqLength);
        return this;
    }

    public FillRuleItem seqIncrement(Integer seqIncrement) {
        this.setSeqIncrement(seqIncrement);
        return this;
    }

    public FillRuleItem seqStartValue(Integer seqStartValue) {
        this.setSeqStartValue(seqStartValue);
        return this;
    }

    public void setFillRule(SysFillRule sysFillRule) {
        this.fillRule = sysFillRule;
    }

    public FillRuleItem fillRule(SysFillRule sysFillRule) {
        this.setFillRule(sysFillRule);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FillRuleItem)) {
            return false;
        }
        return id != null && id.equals(((FillRuleItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
