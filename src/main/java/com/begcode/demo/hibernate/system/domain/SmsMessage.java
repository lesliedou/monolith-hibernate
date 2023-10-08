package com.begcode.demo.hibernate.system.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import com.begcode.demo.hibernate.domain.enumeration.MessageSendType;
import com.begcode.demo.hibernate.domain.enumeration.SendStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 短信消息
 */
@Entity
@Table(name = "sms_message")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsMessage extends AbstractAuditingEntity<Long, SmsMessage> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 消息标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 发送方式
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "send_type")
    private MessageSendType sendType;

    /**
     * 接收人
     */
    @Column(name = "receiver")
    private String receiver;

    /**
     * 发送所需参数
     * Json格式
     */
    @Column(name = "params")
    private String params;

    /**
     * 推送内容
     */
    @Lob
    @Column(name = "content")
    private String content;

    /**
     * 推送时间
     */
    @Column(name = "send_time")
    private ZonedDateTime sendTime;

    /**
     * 推送状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "send_status")
    private SendStatus sendStatus;

    /**
     * 发送次数 超过5次不再发送
     */
    @Column(name = "retry_num")
    private Integer retryNum;

    /**
     * 推送失败原因
     */
    @Column(name = "fail_result")
    private String failResult;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SmsMessage id(Long id) {
        this.setId(id);
        return this;
    }

    public SmsMessage title(String title) {
        this.setTitle(title);
        return this;
    }

    public SmsMessage sendType(MessageSendType sendType) {
        this.setSendType(sendType);
        return this;
    }

    public SmsMessage receiver(String receiver) {
        this.setReceiver(receiver);
        return this;
    }

    public SmsMessage params(String params) {
        this.setParams(params);
        return this;
    }

    public SmsMessage content(String content) {
        this.setContent(content);
        return this;
    }

    public SmsMessage sendTime(ZonedDateTime sendTime) {
        this.setSendTime(sendTime);
        return this;
    }

    public SmsMessage sendStatus(SendStatus sendStatus) {
        this.setSendStatus(sendStatus);
        return this;
    }

    public SmsMessage retryNum(Integer retryNum) {
        this.setRetryNum(retryNum);
        return this;
    }

    public SmsMessage failResult(String failResult) {
        this.setFailResult(failResult);
        return this;
    }

    public SmsMessage remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsMessage)) {
            return false;
        }
        return id != null && id.equals(((SmsMessage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
