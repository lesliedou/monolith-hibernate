package com.begcode.demo.hibernate.domain;

import com.begcode.demo.hibernate.domain.enumeration.ApiPermissionState;
import com.begcode.demo.hibernate.domain.enumeration.ApiPermissionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * API权限
 * 菜单或按钮下有相关的api权限
 */
@Entity
@Table(name = "api_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApiPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 服务名称
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * 权限名称
     */
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    /**
     * 权限描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ApiPermissionType type;

    /**
     * 请求类型
     */
    @Column(name = "method")
    private String method;

    /**
     * url 地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApiPermissionState status;

    /**
     * 子节点
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "authorities" }, allowSetters = true)
    private Set<ApiPermission> children = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "children", "parent", "authorities" }, allowSetters = true)
    private ApiPermission parent;

    /**
     * 角色列表
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "apiPermissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "viewPermissions", "apiPermissions", "parent", "users" }, allowSetters = true)
    private Set<Authority> authorities = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public ApiPermission id(Long id) {
        this.setId(id);
        return this;
    }

    public ApiPermission serviceName(String serviceName) {
        this.setServiceName(serviceName);
        return this;
    }

    public ApiPermission name(String name) {
        this.setName(name);
        return this;
    }

    public ApiPermission code(String code) {
        this.setCode(code);
        return this;
    }

    public ApiPermission description(String description) {
        this.setDescription(description);
        return this;
    }

    public ApiPermission type(ApiPermissionType type) {
        this.setType(type);
        return this;
    }

    public ApiPermission method(String method) {
        this.setMethod(method);
        return this;
    }

    public ApiPermission url(String url) {
        this.setUrl(url);
        return this;
    }

    public ApiPermission status(ApiPermissionState status) {
        this.setStatus(status);
        return this;
    }

    public void setChildren(Set<ApiPermission> apiPermissions) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (apiPermissions != null) {
            apiPermissions.forEach(i -> i.setParent(this));
        }
        this.children = apiPermissions;
    }

    public ApiPermission children(Set<ApiPermission> apiPermissions) {
        this.setChildren(apiPermissions);
        return this;
    }

    public ApiPermission addChildren(ApiPermission apiPermission) {
        this.children.add(apiPermission);
        apiPermission.setParent(this);
        return this;
    }

    public ApiPermission removeChildren(ApiPermission apiPermission) {
        this.children.remove(apiPermission);
        apiPermission.setParent(null);
        return this;
    }

    public void setParent(ApiPermission apiPermission) {
        this.parent = apiPermission;
    }

    public ApiPermission parent(ApiPermission apiPermission) {
        this.setParent(apiPermission);
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        if (this.authorities != null) {
            this.authorities.forEach(i -> i.removeApiPermissions(this));
        }
        if (authorities != null) {
            authorities.forEach(i -> i.addApiPermissions(this));
        }
        this.authorities = authorities;
    }

    public ApiPermission authorities(Set<Authority> authorities) {
        this.setAuthorities(authorities);
        return this;
    }

    public ApiPermission addAuthorities(Authority authority) {
        this.authorities.add(authority);
        authority.getApiPermissions().add(this);
        return this;
    }

    public ApiPermission removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
        authority.getApiPermissions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiPermission)) {
            return false;
        }
        return id != null && id.equals(((ApiPermission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
