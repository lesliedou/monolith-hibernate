package com.begcode.demo.hibernate.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 部门
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 代码
     */
    @Column(name = "code")
    private String code;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 联系电话
     */
    @Column(name = "phone_num")
    private String phoneNum;

    /**
     * logo地址
     */
    @Column(name = "logo")
    private String logo;

    /**
     * 联系人
     */
    @Column(name = "contact")
    private String contact;

    /**
     * 创建用户 Id
     */
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private ZonedDateTime createTime;

    /**
     * 下级部门
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "authorities", "parent", "users" }, allowSetters = true)
    private Set<Department> children = new LinkedHashSet<>();

    /**
     * 角色列表
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_department__authorities",
        joinColumns = @JoinColumn(name = "department_id"),
        inverseJoinColumns = @JoinColumn(name = "authorities_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "viewPermissions", "apiPermissions", "parent", "users" }, allowSetters = true)
    private Set<Authority> authorities = new LinkedHashSet<>();

    /**
     * 上级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "children", "authorities", "parent", "users" }, allowSetters = true)
    private Department parent;

    /**
     * 用户列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "department", "position", "authorities" }, allowSetters = true)
    private Set<User> users = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Department id(Long id) {
        this.setId(id);
        return this;
    }

    public Department name(String name) {
        this.setName(name);
        return this;
    }

    public Department code(String code) {
        this.setCode(code);
        return this;
    }

    public Department address(String address) {
        this.setAddress(address);
        return this;
    }

    public Department phoneNum(String phoneNum) {
        this.setPhoneNum(phoneNum);
        return this;
    }

    public Department logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public Department contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public Department createUserId(Long createUserId) {
        this.setCreateUserId(createUserId);
        return this;
    }

    public Department createTime(ZonedDateTime createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setChildren(Set<Department> departments) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (departments != null) {
            departments.forEach(i -> i.setParent(this));
        }
        this.children = departments;
    }

    public Department children(Set<Department> departments) {
        this.setChildren(departments);
        return this;
    }

    public Department addChildren(Department department) {
        this.children.add(department);
        department.setParent(this);
        return this;
    }

    public Department removeChildren(Department department) {
        this.children.remove(department);
        department.setParent(null);
        return this;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Department authorities(Set<Authority> authorities) {
        this.setAuthorities(authorities);
        return this;
    }

    public Department addAuthorities(Authority authority) {
        this.authorities.add(authority);
//        authority.set(this);
        return this;
    }

    public Department removeAuthorities(Authority authority) {
        this.authorities.remove(authority);
//        authority.set(null);
        return this;
    }

    public void setParent(Department department) {
        this.parent = department;
    }

    public Department parent(Department department) {
        this.setParent(department);
        return this;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.setDepartment(null));
        }
        if (users != null) {
            users.forEach(i -> i.setDepartment(this));
        }
        this.users = users;
    }

    public Department users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Department addUsers(User user) {
        this.users.add(user);
        user.setDepartment(this);
        return this;
    }

    public Department removeUsers(User user) {
        this.users.remove(user);
        user.setDepartment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
