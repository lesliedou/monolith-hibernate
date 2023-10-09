package com.begcode.demo.hibernate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色
 */
@Entity
@Table(name = "jhi_authority")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色代号
     */
    @Column(name = "code")
    private String code;

    /**
     * 信息
     */
    @Column(name = "info")
    private String info;

    /**
     * 排序
     */
    @Column(name = "jhi_order")
    private Integer order;

    /**
     * 展示
     */
    @Column(name = "display")
    private Boolean display;

    /**
     * 子节点
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "viewPermissions", "apiPermissions", "parent", "users" }, allowSetters = true)
    private Set<Authority> children = new LinkedHashSet<>();

    /**
     * 菜单列表
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_jhi_authority__view_permissions",
        joinColumns = @JoinColumn(name = "jhi_authority_id"),
        inverseJoinColumns = @JoinColumn(name = "view_permissions_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "authorities" }, allowSetters = true)
    private Set<ViewPermission> viewPermissions = new LinkedHashSet<>();

    /**
     * Api权限列表
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_jhi_authority__api_permissions",
        joinColumns = @JoinColumn(name = "jhi_authority_id"),
        inverseJoinColumns = @JoinColumn(name = "api_permissions_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "authorities" }, allowSetters = true)
    private Set<ApiPermission> apiPermissions = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "children", "viewPermissions", "apiPermissions", "parent", "users" }, allowSetters = true)
    private Authority parent;

    /**
     * 用户列表
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "department", "position", "authorities" }, allowSetters = true)
    private Set<User> users = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Authority id(Long id) {
        this.setId(id);
        return this;
    }

    public Authority name(String name) {
        this.setName(name);
        return this;
    }

    public Authority code(String code) {
        this.setCode(code);
        return this;
    }

    public Authority info(String info) {
        this.setInfo(info);
        return this;
    }

    public Authority order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public Authority display(Boolean display) {
        this.setDisplay(display);
        return this;
    }

    public void setChildren(Set<Authority> authorities) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (authorities != null) {
            authorities.forEach(i -> i.setParent(this));
        }
        this.children = authorities;
    }

    public Authority children(Set<Authority> authorities) {
        this.setChildren(authorities);
        return this;
    }

    public Authority addChildren(Authority authority) {
        this.children.add(authority);
        authority.setParent(this);
        return this;
    }

    public Authority removeChildren(Authority authority) {
        this.children.remove(authority);
        authority.setParent(null);
        return this;
    }

    public void setViewPermissions(Set<ViewPermission> viewPermissions) {
        this.viewPermissions = viewPermissions;
    }

    public Authority viewPermissions(Set<ViewPermission> viewPermissions) {
        this.setViewPermissions(viewPermissions);
        return this;
    }

    public Authority addViewPermissions(ViewPermission viewPermission) {
        this.viewPermissions.add(viewPermission);
        viewPermission.getAuthorities().add(this);
        return this;
    }

    public Authority removeViewPermissions(ViewPermission viewPermission) {
        this.viewPermissions.remove(viewPermission);
        viewPermission.getAuthorities().remove(this);
        return this;
    }

    public void setApiPermissions(Set<ApiPermission> apiPermissions) {
        this.apiPermissions = apiPermissions;
    }

    public Authority apiPermissions(Set<ApiPermission> apiPermissions) {
        this.setApiPermissions(apiPermissions);
        return this;
    }

    public Authority addApiPermissions(ApiPermission apiPermission) {
        this.apiPermissions.add(apiPermission);
        apiPermission.getAuthorities().add(this);
        return this;
    }

    public Authority removeApiPermissions(ApiPermission apiPermission) {
        this.apiPermissions.remove(apiPermission);
        apiPermission.getAuthorities().remove(this);
        return this;
    }

    public void setParent(Authority authority) {
        this.parent = authority;
    }

    public Authority parent(Authority authority) {
        this.setParent(authority);
        return this;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeAuthorities(this));
        }
        if (users != null) {
            users.forEach(i -> i.addAuthorities(this));
        }
        this.users = users;
    }

    public Authority users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Authority addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public Authority removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        return id != null && id.equals(((Authority) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
