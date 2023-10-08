package com.begcode.demo.hibernate.system.domain;

import com.begcode.demo.hibernate.domain.enumeration.SmsProvider;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 短信服务配置
 */
@Entity
@Table(name = "sms_supplier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsSupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 提供商
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private SmsProvider provider;

    /**
     * 配置数据
     */
    @Column(name = "config_data")
    private String configData;

    /**
     * 短信签名
     */
    @Column(name = "sign_name")
    private String signName;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 启用
     */
    @Column(name = "enabled")
    private Boolean enabled;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SmsSupplier id(Long id) {
        this.setId(id);
        return this;
    }

    public SmsSupplier provider(SmsProvider provider) {
        this.setProvider(provider);
        return this;
    }

    public SmsSupplier configData(String configData) {
        this.setConfigData(configData);
        return this;
    }

    public SmsSupplier signName(String signName) {
        this.setSignName(signName);
        return this;
    }

    public SmsSupplier remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public SmsSupplier enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsSupplier)) {
            return false;
        }
        return id != null && id.equals(((SmsSupplier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
