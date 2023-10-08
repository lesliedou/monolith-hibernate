package com.begcode.demo.hibernate.settings.domain;

import com.begcode.demo.hibernate.domain.enumeration.RegionCodeLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 行政区划码
 */
@Entity
@Table(name = "region_code")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegionCode implements Serializable {

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
     * 地区代码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 城市代码
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 全名
     */
    @Column(name = "merger_name")
    private String mergerName;

    /**
     * 短名称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 邮政编码
     */
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 等级
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private RegionCodeLevel level;

    /**
     * 经度
     */
    @Column(name = "lng")
    private Double lng;

    /**
     * 纬度
     */
    @Column(name = "lat")
    private Double lat;

    /**
     * 子节点
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "children", "parent" }, allowSetters = true)
    private Set<RegionCode> children = new LinkedHashSet<>();

    /**
     * 上级节点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "children", "parent" }, allowSetters = true)
    private RegionCode parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public RegionCode id(Long id) {
        this.setId(id);
        return this;
    }

    public RegionCode name(String name) {
        this.setName(name);
        return this;
    }

    public RegionCode areaCode(String areaCode) {
        this.setAreaCode(areaCode);
        return this;
    }

    public RegionCode cityCode(String cityCode) {
        this.setCityCode(cityCode);
        return this;
    }

    public RegionCode mergerName(String mergerName) {
        this.setMergerName(mergerName);
        return this;
    }

    public RegionCode shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public RegionCode zipCode(String zipCode) {
        this.setZipCode(zipCode);
        return this;
    }

    public RegionCode level(RegionCodeLevel level) {
        this.setLevel(level);
        return this;
    }

    public RegionCode lng(Double lng) {
        this.setLng(lng);
        return this;
    }

    public RegionCode lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setChildren(Set<RegionCode> regionCodes) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (regionCodes != null) {
            regionCodes.forEach(i -> i.setParent(this));
        }
        this.children = regionCodes;
    }

    public RegionCode children(Set<RegionCode> regionCodes) {
        this.setChildren(regionCodes);
        return this;
    }

    public RegionCode addChildren(RegionCode regionCode) {
        this.children.add(regionCode);
        regionCode.setParent(this);
        return this;
    }

    public RegionCode removeChildren(RegionCode regionCode) {
        this.children.remove(regionCode);
        regionCode.setParent(null);
        return this;
    }

    public void setParent(RegionCode regionCode) {
        this.parent = regionCode;
    }

    public RegionCode parent(RegionCode regionCode) {
        this.setParent(regionCode);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegionCode)) {
            return false;
        }
        return id != null && id.equals(((RegionCode) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }
}
