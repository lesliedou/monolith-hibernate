package com.begcode.demo.hibernate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 上传文件
 */
@Entity
@Table(name = "upload_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UploadFile extends AbstractAuditingEntity<Long, UploadFile> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 完整文件名
     * 不含路径
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * 文件名
     * 不含扩展名
     */
    @Column(name = "name")
    private String name;

    /**
     * 缩略图Url地址
     */
    @Column(name = "thumb")
    private String thumb;

    /**
     * 扩展名
     */
    @Column(name = "ext")
    private String ext;

    /**
     * 文件类型
     */
    @Column(name = "type")
    private String type;

    /**
     * Url地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 本地路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 存储目录
     */
    @Column(name = "folder")
    private String folder;

    /**
     * 实体名称
     */
    @Column(name = "owner_entity_name", insertable = false, updatable = false)
    private String ownerEntityName;

    /**
     * 使用实体ID
     */
    @Column(name = "owner_entity_id", insertable = false, updatable = false)
    private String ownerEntityId;

    /**
     * 业务标题
     */
    @Column(name = "business_title")
    private String businessTitle;

    /**
     * 业务自定义描述内容
     */
    @Column(name = "business_desc")
    private String businessDesc;

    /**
     * 业务状态
     */
    @Column(name = "business_status")
    private String businessStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private ZonedDateTime createAt;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 被引次数
     */
    @Column(name = "reference_count")
    private Long referenceCount;

    @Any(fetch = FetchType.LAZY)
    @Column(name = "owner_entity_name")
    @JoinColumn(name = "owner_entity_id")
    private Owner owner;

    /**
     * 所属分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "files", "children", "images", "parent" }, allowSetters = true)
    private ResourceCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UploadFile id(Long id) {
        this.setId(id);
        return this;
    }

    public UploadFile fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public UploadFile name(String name) {
        this.setName(name);
        return this;
    }

    public UploadFile thumb(String thumb) {
        this.setThumb(thumb);
        return this;
    }

    public UploadFile ext(String ext) {
        this.setExt(ext);
        return this;
    }

    public UploadFile type(String type) {
        this.setType(type);
        return this;
    }

    public UploadFile url(String url) {
        this.setUrl(url);
        return this;
    }

    public UploadFile path(String path) {
        this.setPath(path);
        return this;
    }

    public UploadFile folder(String folder) {
        this.setFolder(folder);
        return this;
    }

    public UploadFile ownerEntityName(String ownerEntityName) {
        this.setOwnerEntityName(ownerEntityName);
        return this;
    }

    public UploadFile ownerEntityId(String ownerEntityId) {
        this.setOwnerEntityId(ownerEntityId);
        return this;
    }

    public UploadFile businessTitle(String businessTitle) {
        this.setBusinessTitle(businessTitle);
        return this;
    }

    public UploadFile businessDesc(String businessDesc) {
        this.setBusinessDesc(businessDesc);
        return this;
    }

    public UploadFile businessStatus(String businessStatus) {
        this.setBusinessStatus(businessStatus);
        return this;
    }

    public UploadFile createAt(ZonedDateTime createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public UploadFile fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public UploadFile referenceCount(Long referenceCount) {
        this.setReferenceCount(referenceCount);
        return this;
    }

    public void setCategory(ResourceCategory resourceCategory) {
        this.category = resourceCategory;
    }

    public UploadFile category(ResourceCategory resourceCategory) {
        this.setCategory(resourceCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadFile)) {
            return false;
        }
        return id != null && id.equals(((UploadFile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
