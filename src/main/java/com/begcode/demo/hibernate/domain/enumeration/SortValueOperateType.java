package com.begcode.demo.hibernate.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The AnnoBusinessType enumeration.
 */
public enum SortValueOperateType {
    DROP("DROP", "拖拽"),
    STEP("STEP", "单步"),
    VALUE("VALUE", "修改值");

    @JsonValue
    private String value;

    private String desc;

    SortValueOperateType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static SortValueOperateType getByValue(String value) {
        for (SortValueOperateType enumAnnoBusinessType : SortValueOperateType.values()) {
            if (enumAnnoBusinessType.getValue().equals(value)) {
                return enumAnnoBusinessType;
            }
        }
        return null;
    }

    public static SortValueOperateType getByDesc(String desc) {
        for (SortValueOperateType enumAnnoBusinessType : SortValueOperateType.values()) {
            if (enumAnnoBusinessType.getDesc().equals(desc)) {
                return enumAnnoBusinessType;
            }
        }
        return null;
    }
}
