package com.begcode.demo.hibernate.system.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import com.begcode.demo.hibernate.domain.enumeration.MessageSendType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 消息模板
 */
@Entity
@Table(name = "sms_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SmsTemplate extends AbstractAuditingEntity<Long, SmsTemplate> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 模板标题
     */
    @Column(name = "name")
    private String name;

    /**
     * 模板CODE
     */
    @Column(name = "code")
    private String code;

    /**
     * 模板类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MessageSendType type;

    /**
     * 模板内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 模板测试json
     */
    @Column(name = "test_json")
    private String testJson;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SmsTemplate id(Long id) {
        this.setId(id);
        return this;
    }

    public SmsTemplate name(String name) {
        this.setName(name);
        return this;
    }

    public SmsTemplate code(String code) {
        this.setCode(code);
        return this;
    }

    public SmsTemplate type(MessageSendType type) {
        this.setType(type);
        return this;
    }

    public SmsTemplate content(String content) {
        this.setContent(content);
        return this;
    }

    public SmsTemplate testJson(String testJson) {
        this.setTestJson(testJson);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsTemplate)) {
            return false;
        }
        return id != null && id.equals(((SmsTemplate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
