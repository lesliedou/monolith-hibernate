package com.begcode.demo.hibernate.oss.domain;

import com.begcode.demo.hibernate.domain.enumeration.OssProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 对象存储配置
 */
@Entity
@Table(name = "oss_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OssConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 提供商
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private OssProvider provider;

    /**
     * 平台
     */
    @NotNull
    @Size(max = 40)
    @Column(name = "platform", length = 40, nullable = false, unique = true)
    private String platform;

    /**
     * 启用
     */
    @Column(name = "enabled")
    private Boolean enabled;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 配置数据
     */
    @Column(name = "config_data")
    private String configData;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public OssConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public OssConfig provider(OssProvider provider) {
        this.setProvider(provider);
        return this;
    }

    public OssConfig platform(String platform) {
        this.setPlatform(platform);
        return this;
    }

    public OssConfig enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public OssConfig remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public OssConfig configData(String configData) {
        this.setConfigData(configData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OssConfig)) {
            return false;
        }
        return id != null && id.equals(((OssConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
