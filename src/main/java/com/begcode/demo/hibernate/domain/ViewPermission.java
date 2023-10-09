package com.begcode.demo.hibernate.domain;

import com.begcode.demo.hibernate.domain.enumeration.TargetType;
import com.begcode.demo.hibernate.domain.enumeration.ViewPermissionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 可视权限
 * 权限分为菜单权限、按钮权限等
 *
 */
@Entity
@Table(name = "view_permission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 权限名称
     */
    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    /**
     * i18n主键
     */
    @Column(name = "i_18_n")
    private String i18n;

    /**
     * 显示分组名
     */
    @Column(name = "jhi_group")
    private Boolean group;

    /**
     * 路由
     */
    @Column(name = "jhi_link")
    private String link;

    /**
     * 外部链接
     */
    @Column(name = "external_link")
    private String externalLink;

    /**
     * 链接目标
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target")
    private TargetType target;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 禁用菜单
     */
    @Column(name = "disabled")
    private Boolean disabled;

    /**
     * 隐藏菜单
     */
    @Column(name = "hide")
    private Boolean hide;

    /**
     * 隐藏面包屑
     */
    @Column(name = "hide_in_breadcrumb")
    private Boolean hideInBreadcrumb;

    /**
     * 快捷菜单项
     */
    @Column(name = "shortcut")
    private Boolean shortcut;

    /**
     * 菜单根节点
     */
    @Column(name = "shortcut_root")
    private Boolean shortcutRoot;

    /**
     * 允许复用
     */
    @Column(name = "reuse")
    private Boolean reuse;

    /**
     * 权限代码
     * (ROLE_开头)
     */
    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 权限描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 权限类型
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ViewPermissionType type;

    /**
     * 排序
     */
    @Column(name = "jhi_order")
    private Integer order;

    /**
     * api权限标识串
     */
    @Column(name = "api_permission_codes")
    private String apiPermissionCodes;

    /**
     * 组件名称
     */
    @Column(name = "component_file")
    private String componentFile;

    /**
     * 重定向路径
     */
    @Column(name = "redirect")
    private String redirect;

    /**
     * 子节点
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent", "authorities" }, allowSetters = true)
    private Set<ViewPermission> children = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "children", "parent", "authorities" }, allowSetters = true)
    private ViewPermission parent;

    /**
     * 角色列表
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "viewPermissions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "viewPermissions", "apiPermissions", "parent", "users" }, allowSetters = true)
    private Set<Authority> authorities = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public ViewPermission id(Long id) {
        this.setId(id);
        return this;
    }

    public ViewPermission text(String text) {
        this.setText(text);
        return this;
    }

    public ViewPermission i18n(String i18n) {
        this.setI18n(i18n);
        return this;
    }

    public ViewPermission group(Boolean group) {
        this.setGroup(group);
        return this;
    }

    public ViewPermission link(String link) {
        this.setLink(link);
        return this;
    }

    public ViewPermission externalLink(String externalLink) {
        this.setExternalLink(externalLink);
        return this;
    }

    public ViewPermission target(TargetType target) {
        this.setTarget(target);
        return this;
    }

    public ViewPermission icon(String icon) {
        this.setIcon(icon);
        return this;
    }

    public ViewPermission disabled(Boolean disabled) {
        this.setDisabled(disabled);
        return this;
    }

    public ViewPermission hide(Boolean hide) {
        this.setHide(hide);
        return this;
    }

    public ViewPermission hideInBreadcrumb(Boolean hideInBreadcrumb) {
        this.setHideInBreadcrumb(hideInBreadcrumb);
        return this;
    }

    public ViewPermission shortcut(Boolean shortcut) {
        this.setShortcut(shortcut);
        return this;
    }

    public ViewPermission shortcutRoot(Boolean shortcutRoot) {
        this.setShortcutRoot(shortcutRoot);
        return this;
    }

    public ViewPermission reuse(Boolean reuse) {
        this.setReuse(reuse);
        return this;
    }

    public ViewPermission code(String code) {
        this.setCode(code);
        return this;
    }

    public ViewPermission description(String description) {
        this.setDescription(description);
        return this;
    }

    public ViewPermission type(ViewPermissionType type) {
        this.setType(type);
        return this;
    }

    public ViewPermission order(Integer order) {
        this.setOrder(order);
        return this;
    }

    public ViewPermission apiPermissionCodes(String apiPermissionCodes) {
        this.setApiPermissionCodes(apiPermissionCodes);
        return this;
    }

    public ViewPermission componentFile(String componentFile) {
        this.setComponentFile(componentFile);
        return this;
    }

    public ViewPermission redirect(String redirect) {
        this.setRedirect(redirect);
        return this;
    }

    public void setChildren(Set<ViewPermission> viewPermissions) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (viewPermissions != null) {
            viewPermissions.forEach(i -> i.setParent(this));
        }
        this.children = viewPermissions;
    }

    public ViewPermission children(Set<ViewPermission> viewPermissions) {
        this.setChildren(viewPermissions);
        return this;
    }

    public ViewPermission addChildren(ViewPermission viewPermission) {
        this.children.add(viewPermission);
        viewPermission.setParent(this);
        return this;
    }

    public ViewPermission removeChildren(ViewPermission viewPermission) {
        this.children.remove(viewPermission);
        viewPermission.setParent(null);
        return this;
    }

    public void setParent(ViewPermission viewPermission) {
        this.parent = viewPermission;
    }

    public ViewPermission parent(ViewPermission viewPermission) {
        this.setParent(viewPermission);
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        if (this.authorities != null) {
            this.authorities.forEach(i -> i.removeViewPermissions(this));
        }
        if (authorities != null) {
            authorities.forEach(i -> i.addViewPermissions(this));
        }
        this.authorities = authorities;
    }

    public ViewPermission authorities(Set<Authority> authorities) {
        this.setAuthorities(authorities);
        return this;
    }

    public ViewPermission addAuthorities(Authority authority) {
        this.authorities.add(authority);
        authority.getViewPermissions().add(this);
        return this;
    }

    public ViewPermission removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
        authority.getViewPermissions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewPermission)) {
            return false;
        }
        return id != null && id.equals(((ViewPermission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
