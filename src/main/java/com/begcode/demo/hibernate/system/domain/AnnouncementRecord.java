package com.begcode.demo.hibernate.system.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 通告阅读记录
 */
@Entity
@Table(name = "announcement_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnnouncementRecord extends AbstractAuditingEntity<Long, AnnouncementRecord> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 通告ID
     */
    @Column(name = "annt_id")
    private Long anntId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 是否已读
     */
    @Column(name = "has_read")
    private Boolean hasRead;

    /**
     * 阅读时间
     */
    @Column(name = "read_time")
    private ZonedDateTime readTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public AnnouncementRecord id(Long id) {
        this.setId(id);
        return this;
    }

    public AnnouncementRecord anntId(Long anntId) {
        this.setAnntId(anntId);
        return this;
    }

    public AnnouncementRecord userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public AnnouncementRecord hasRead(Boolean hasRead) {
        this.setHasRead(hasRead);
        return this;
    }

    public AnnouncementRecord readTime(ZonedDateTime readTime) {
        this.setReadTime(readTime);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnnouncementRecord)) {
            return false;
        }
        return id != null && id.equals(((AnnouncementRecord) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
