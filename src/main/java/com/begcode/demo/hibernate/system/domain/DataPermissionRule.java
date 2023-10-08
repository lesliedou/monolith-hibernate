package com.begcode.demo.hibernate.system.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据权限规则
 */
@Entity
@Table(name = "data_permission_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DataPermissionRule extends AbstractAuditingEntity<Long, DataPermissionRule> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 菜单ID
     */
    @Column(name = "permission_id")
    private String permissionId;

    /**
     * 规则名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 字段
     */
    @Column(name = "jhi_column")
    private String column;

    /**
     * 条件
     */
    @Column(name = "conditions")
    private String conditions;

    /**
     * 规则值
     */
    @Column(name = "value")
    private String value;

    /**
     * 禁用状态
     */
    @Column(name = "disabled")
    private Boolean disabled;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public DataPermissionRule id(Long id) {
        this.setId(id);
        return this;
    }

    public DataPermissionRule permissionId(String permissionId) {
        this.setPermissionId(permissionId);
        return this;
    }

    public DataPermissionRule name(String name) {
        this.setName(name);
        return this;
    }

    public DataPermissionRule column(String column) {
        this.setColumn(column);
        return this;
    }

    public DataPermissionRule conditions(String conditions) {
        this.setConditions(conditions);
        return this;
    }

    public DataPermissionRule value(String value) {
        this.setValue(value);
        return this;
    }

    public DataPermissionRule disabled(Boolean disabled) {
        this.setDisabled(disabled);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataPermissionRule)) {
            return false;
        }
        return id != null && id.equals(((DataPermissionRule) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
