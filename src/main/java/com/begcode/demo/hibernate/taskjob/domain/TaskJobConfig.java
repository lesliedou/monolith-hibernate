package com.begcode.demo.hibernate.taskjob.domain;

import com.begcode.demo.hibernate.domain.AbstractAuditingEntity;
import com.begcode.demo.hibernate.domain.enumeration.JobStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 定时任务
 */
@Entity
@Table(name = "task_job_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaskJobConfig extends AbstractAuditingEntity<Long, TaskJobConfig> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 任务名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 任务类名
     */
    @Column(name = "job_class_name")
    private String jobClassName;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 参数
     */
    @Column(name = "parameter")
    private String parameter;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 任务状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "job_status")
    private JobStatus jobStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public TaskJobConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public TaskJobConfig name(String name) {
        this.setName(name);
        return this;
    }

    public TaskJobConfig jobClassName(String jobClassName) {
        this.setJobClassName(jobClassName);
        return this;
    }

    public TaskJobConfig cronExpression(String cronExpression) {
        this.setCronExpression(cronExpression);
        return this;
    }

    public TaskJobConfig parameter(String parameter) {
        this.setParameter(parameter);
        return this;
    }

    public TaskJobConfig description(String description) {
        this.setDescription(description);
        return this;
    }

    public TaskJobConfig jobStatus(JobStatus jobStatus) {
        this.setJobStatus(jobStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskJobConfig)) {
            return false;
        }
        return id != null && id.equals(((TaskJobConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
