package com.begcode.demo.hibernate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 资源分类
 */
@Entity
@Table(name = "resource_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResourceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 标题
     */
    @Size(max = 40)
    @Column(name = "title", length = 40)
    private String title;

    /**
     * 代码
     */
    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    /**
     * 排序
     */
    @Column(name = "order_number")
    private Integer orderNumber;

    /**
     * 文件列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Set<UploadFile> files = new LinkedHashSet<>();

    /**
     * 下级列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "files", "children", "images", "parent" }, allowSetters = true)
    private Set<ResourceCategory> children = new LinkedHashSet<>();

    /**
     * 图片列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "category" }, allowSetters = true)
    private Set<UploadImage> images = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "files", "children", "images", "parent" }, allowSetters = true)
    private ResourceCategory parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public ResourceCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public ResourceCategory title(String title) {
        this.setTitle(title);
        return this;
    }

    public ResourceCategory code(String code) {
        this.setCode(code);
        return this;
    }

    public ResourceCategory orderNumber(Integer orderNumber) {
        this.setOrderNumber(orderNumber);
        return this;
    }

    public void setFiles(Set<UploadFile> uploadFiles) {
        if (this.files != null) {
            this.files.forEach(i -> i.setCategory(null));
        }
        if (uploadFiles != null) {
            uploadFiles.forEach(i -> i.setCategory(this));
        }
        this.files = uploadFiles;
    }

    public ResourceCategory files(Set<UploadFile> uploadFiles) {
        this.setFiles(uploadFiles);
        return this;
    }

    public ResourceCategory addFiles(UploadFile uploadFile) {
        this.files.add(uploadFile);
        uploadFile.setCategory(this);
        return this;
    }

    public ResourceCategory removeFiles(UploadFile uploadFile) {
        this.files.remove(uploadFile);
        uploadFile.setCategory(null);
        return this;
    }

    public void setChildren(Set<ResourceCategory> resourceCategories) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (resourceCategories != null) {
            resourceCategories.forEach(i -> i.setParent(this));
        }
        this.children = resourceCategories;
    }

    public ResourceCategory children(Set<ResourceCategory> resourceCategories) {
        this.setChildren(resourceCategories);
        return this;
    }

    public ResourceCategory addChildren(ResourceCategory resourceCategory) {
        this.children.add(resourceCategory);
        resourceCategory.setParent(this);
        return this;
    }

    public ResourceCategory removeChildren(ResourceCategory resourceCategory) {
        this.children.remove(resourceCategory);
        resourceCategory.setParent(null);
        return this;
    }

    public void setImages(Set<UploadImage> uploadImages) {
        if (this.images != null) {
            this.images.forEach(i -> i.setCategory(null));
        }
        if (uploadImages != null) {
            uploadImages.forEach(i -> i.setCategory(this));
        }
        this.images = uploadImages;
    }

    public ResourceCategory images(Set<UploadImage> uploadImages) {
        this.setImages(uploadImages);
        return this;
    }

    public ResourceCategory addImages(UploadImage uploadImage) {
        this.images.add(uploadImage);
        uploadImage.setCategory(this);
        return this;
    }

    public ResourceCategory removeImages(UploadImage uploadImage) {
        this.images.remove(uploadImage);
        uploadImage.setCategory(null);
        return this;
    }

    public void setParent(ResourceCategory resourceCategory) {
        this.parent = resourceCategory;
    }

    public ResourceCategory parent(ResourceCategory resourceCategory) {
        this.setParent(resourceCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceCategory)) {
            return false;
        }
        return id != null && id.equals(((ResourceCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
