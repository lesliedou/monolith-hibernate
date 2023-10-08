package com.begcode.demo.hibernate.system.domain;

import com.begcode.demo.hibernate.domain.enumeration.SmsProvider;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 短信配置
 */
@Entity
@Table(name = "sms_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsConfig implements Serializable {

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
     * 资源编号
     */
    @Column(name = "sms_code")
    private String smsCode;

    /**
     * 模板ID
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * accessKey
     */
    @Column(name = "access_key")
    private String accessKey;

    /**
     * secretKey
     */
    @Column(name = "secret_key")
    private String secretKey;

    /**
     * regionId
     */
    @Column(name = "region_id")
    private String regionId;

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

    public SmsConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public SmsConfig provider(SmsProvider provider) {
        this.setProvider(provider);
        return this;
    }

    public SmsConfig smsCode(String smsCode) {
        this.setSmsCode(smsCode);
        return this;
    }

    public SmsConfig templateId(String templateId) {
        this.setTemplateId(templateId);
        return this;
    }

    public SmsConfig accessKey(String accessKey) {
        this.setAccessKey(accessKey);
        return this;
    }

    public SmsConfig secretKey(String secretKey) {
        this.setSecretKey(secretKey);
        return this;
    }

    public SmsConfig regionId(String regionId) {
        this.setRegionId(regionId);
        return this;
    }

    public SmsConfig signName(String signName) {
        this.setSignName(signName);
        return this;
    }

    public SmsConfig remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public SmsConfig enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsConfig)) {
            return false;
        }
        return id != null && id.equals(((SmsConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
