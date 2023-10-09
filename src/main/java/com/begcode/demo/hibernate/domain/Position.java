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
 * 岗位
 *
 */
@Entity
@Table(name = "position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 岗位代码
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    /**
     * 排序
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 描述
     */
    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    /**
     * 员工列表
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "department", "position", "authorities" }, allowSetters = true)
    private Set<User> users = new LinkedHashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Position id(Long id) {
        this.setId(id);
        return this;
    }

    public Position code(String code) {
        this.setCode(code);
        return this;
    }

    public Position name(String name) {
        this.setName(name);
        return this;
    }

    public Position sortNo(Integer sortNo) {
        this.setSortNo(sortNo);
        return this;
    }

    public Position description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.setPosition(null));
        }
        if (users != null) {
            users.forEach(i -> i.setPosition(this));
        }
        this.users = users;
    }

    public Position users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Position addUsers(User user) {
        this.users.add(user);
        user.setPosition(this);
        return this;
    }

    public Position removeUsers(User user) {
        this.users.remove(user);
        user.setPosition(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return id != null && id.equals(((Position) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
