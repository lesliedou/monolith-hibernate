package com.begcode.demo.hibernate.system.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import com.begcode.demo.hibernate.domain.enumeration.AnnoBusinessType;
import com.begcode.demo.hibernate.domain.enumeration.AnnoCategory;
import com.begcode.demo.hibernate.domain.enumeration.AnnoOpenType;
import com.begcode.demo.hibernate.domain.enumeration.AnnoSendStatus;
import com.begcode.demo.hibernate.domain.enumeration.PriorityLevel;
import com.begcode.demo.hibernate.domain.enumeration.ReceiverType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统通告
 */
@Entity
@Table(name = "announcement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Announcement extends AbstractAuditingEntity<Long, Announcement> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 内容
     */
    @Lob
    @Column(name = "content")
    private String content;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private ZonedDateTime startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    /**
     * 发布人Id
     */
    @Column(name = "sender_id")
    private Long senderId;

    /**
     * 优先级
     * （L低，M中，H高）
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private PriorityLevel priority;

    /**
     * 消息类型
     * 通知公告,系统消息
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private AnnoCategory category;

    /**
     * 通告对象类型
     * （USER:指定用户，ALL:全体用户）
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "receiver_type")
    private ReceiverType receiverType;

    /**
     * 发布状态
     *
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "send_status")
    private AnnoSendStatus sendStatus;

    /**
     * 发布时间
     */
    @Column(name = "send_time")
    private ZonedDateTime sendTime;

    /**
     * 撤销时间
     */
    @Column(name = "cancel_time")
    private ZonedDateTime cancelTime;

    /**
     * 业务类型
     * (email:邮件 bpm:流程)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "business_type")
    private AnnoBusinessType businessType;

    /**
     * 业务id
     */
    @Column(name = "business_id")
    private Long businessId;

    /**
     * 打开方式
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "open_type")
    private AnnoOpenType openType;

    /**
     * 组件/路由 地址
     */
    @Column(name = "open_page")
    private String openPage;

    /**
     * 指定接收者id
     */
    @Lob
    @Column(name = "receiver_ids")
    private String receiverIds;

    /**
     * 摘要
     */
    @Lob
    @Column(name = "summary")
    private String summary;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Announcement id(Long id) {
        this.setId(id);
        return this;
    }

    public Announcement title(String title) {
        this.setTitle(title);
        return this;
    }

    public Announcement content(String content) {
        this.setContent(content);
        return this;
    }

    public Announcement startTime(ZonedDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public Announcement endTime(ZonedDateTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public Announcement senderId(Long senderId) {
        this.setSenderId(senderId);
        return this;
    }

    public Announcement priority(PriorityLevel priority) {
        this.setPriority(priority);
        return this;
    }

    public Announcement category(AnnoCategory category) {
        this.setCategory(category);
        return this;
    }

    public Announcement receiverType(ReceiverType receiverType) {
        this.setReceiverType(receiverType);
        return this;
    }

    public Announcement sendStatus(AnnoSendStatus sendStatus) {
        this.setSendStatus(sendStatus);
        return this;
    }

    public Announcement sendTime(ZonedDateTime sendTime) {
        this.setSendTime(sendTime);
        return this;
    }

    public Announcement cancelTime(ZonedDateTime cancelTime) {
        this.setCancelTime(cancelTime);
        return this;
    }

    public Announcement businessType(AnnoBusinessType businessType) {
        this.setBusinessType(businessType);
        return this;
    }

    public Announcement businessId(Long businessId) {
        this.setBusinessId(businessId);
        return this;
    }

    public Announcement openType(AnnoOpenType openType) {
        this.setOpenType(openType);
        return this;
    }

    public Announcement openPage(String openPage) {
        this.setOpenPage(openPage);
        return this;
    }

    public Announcement receiverIds(String receiverIds) {
        this.setReceiverIds(receiverIds);
        return this;
    }

    public Announcement summary(String summary) {
        this.setSummary(summary);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Announcement)) {
            return false;
        }
        return id != null && id.equals(((Announcement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
