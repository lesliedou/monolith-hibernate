package com.begcode.demo.hibernate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 上传图片
 */
@Entity
@Table(name = "upload_image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UploadImage extends AbstractAuditingEntity<Long, UploadImage> implements Serializable {

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
     * Web Url地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 本地路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 本地存储目录
     */
    @Column(name = "folder")
    private String folder;

    /**
     * 使用实体名称
     */
    @Column(name = "owner_entity_name")
    private String ownerEntityName;

    /**
     * 使用实体ID
     */
    @Column(name = "owner_entity_id")
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
     * 小图Url
     */
    @Column(name = "smart_url")
    private String smartUrl;

    /**
     * 中等图Url
     */
    @Column(name = "medium_url")
    private String mediumUrl;

    /**
     * 文件被引用次数
     */
    @Column(name = "reference_count")
    private Long referenceCount;

    /**
     * 所属分类
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "files", "children", "images", "parent" }, allowSetters = true)
    private ResourceCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UploadImage id(Long id) {
        this.setId(id);
        return this;
    }

    public UploadImage fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public UploadImage name(String name) {
        this.setName(name);
        return this;
    }

    public UploadImage ext(String ext) {
        this.setExt(ext);
        return this;
    }

    public UploadImage type(String type) {
        this.setType(type);
        return this;
    }

    public UploadImage url(String url) {
        this.setUrl(url);
        return this;
    }

    public UploadImage path(String path) {
        this.setPath(path);
        return this;
    }

    public UploadImage folder(String folder) {
        this.setFolder(folder);
        return this;
    }

    public UploadImage ownerEntityName(String ownerEntityName) {
        this.setOwnerEntityName(ownerEntityName);
        return this;
    }

    public UploadImage ownerEntityId(String ownerEntityId) {
        this.setOwnerEntityId(ownerEntityId);
        return this;
    }

    public UploadImage businessTitle(String businessTitle) {
        this.setBusinessTitle(businessTitle);
        return this;
    }

    public UploadImage businessDesc(String businessDesc) {
        this.setBusinessDesc(businessDesc);
        return this;
    }

    public UploadImage businessStatus(String businessStatus) {
        this.setBusinessStatus(businessStatus);
        return this;
    }

    public UploadImage createAt(ZonedDateTime createAt) {
        this.setCreateAt(createAt);
        return this;
    }

    public UploadImage fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public UploadImage smartUrl(String smartUrl) {
        this.setSmartUrl(smartUrl);
        return this;
    }

    public UploadImage mediumUrl(String mediumUrl) {
        this.setMediumUrl(mediumUrl);
        return this;
    }

    public UploadImage referenceCount(Long referenceCount) {
        this.setReferenceCount(referenceCount);
        return this;
    }

    public void setCategory(ResourceCategory resourceCategory) {
        this.category = resourceCategory;
    }

    public UploadImage category(ResourceCategory resourceCategory) {
        this.setCategory(resourceCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadImage)) {
            return false;
        }
        return id != null && id.equals(((UploadImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
