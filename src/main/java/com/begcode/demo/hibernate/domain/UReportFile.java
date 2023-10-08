package com.begcode.demo.hibernate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 报表存储
 */
@Entity
@Table(name = "u_report_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UReportFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 内容
     */
    @Lob
    @Column(name = "content")
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private ZonedDateTime createAt;

    /**
     * 更新时间
     */
    @Column(name = "update_at")
    private ZonedDateTime updateAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UReportFile id(Long id) {
        this.setId(id);
        return this;
    }

    public UReportFile name(String name) {
        this.setName(name);
        return this;
    }

    public UReportFile content(String content) {
        this.setContent(content);
        return this;
    }

    public UReportFile createAt(ZonedDateTime createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public UReportFile updateAt(ZonedDateTime updateAt) {
        this.setUpdateAt(updateAt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UReportFile)) {
            return false;
        }
        return id != null && id.equals(((UReportFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
