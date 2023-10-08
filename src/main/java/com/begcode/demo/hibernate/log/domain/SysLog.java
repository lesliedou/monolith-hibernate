package com.begcode.demo.hibernate.log.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import com.begcode.demo.hibernate.domain.enumeration.LogType;
import com.begcode.demo.hibernate.domain.enumeration.OperateType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统日志
 */
@Entity
@Table(name = "sys_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SysLog extends AbstractAuditingEntity<Long, SysLog> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 日志类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "log_type")
    private LogType logType;

    /**
     * 日志内容
     */
    @Column(name = "log_content")
    private String logContent;

    /**
     * 操作类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "operate_type")
    private OperateType operateType;

    /**
     * 操作用户账号
     */
    @Column(name = "userid")
    private String userid;

    /**
     * 操作用户名称
     */
    @Column(name = "username")
    private String username;

    /**
     * IP
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 请求java方法
     */
    @Column(name = "method")
    private String method;

    /**
     * 请求路径
     */
    @Column(name = "request_url")
    private String requestUrl;

    /**
     * 请求参数
     */
    @Lob
    @Column(name = "request_param")
    private String requestParam;

    /**
     * 请求类型
     */
    @Column(name = "request_type")
    private String requestType;

    /**
     * 耗时
     */
    @Column(name = "cost_time")
    private Long costTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public SysLog id(Long id) {
        this.setId(id);
        return this;
    }

    public SysLog logType(LogType logType) {
        this.setLogType(logType);
        return this;
    }

    public SysLog logContent(String logContent) {
        this.setLogContent(logContent);
        return this;
    }

    public SysLog operateType(OperateType operateType) {
        this.setOperateType(operateType);
        return this;
    }

    public SysLog userid(String userid) {
        this.setUserid(userid);
        return this;
    }

    public SysLog username(String username) {
        this.setUsername(username);
        return this;
    }

    public SysLog ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public SysLog method(String method) {
        this.setMethod(method);
        return this;
    }

    public SysLog requestUrl(String requestUrl) {
        this.setRequestUrl(requestUrl);
        return this;
    }

    public SysLog requestParam(String requestParam) {
        this.setRequestParam(requestParam);
        return this;
    }

    public SysLog requestType(String requestType) {
        this.setRequestType(requestType);
        return this;
    }

    public SysLog costTime(Long costTime) {
        this.setCostTime(costTime);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysLog)) {
            return false;
        }
        return id != null && id.equals(((SysLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
