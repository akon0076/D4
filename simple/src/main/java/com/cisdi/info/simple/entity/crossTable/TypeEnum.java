package com.cisdi.info.simple.entity.crossTable;

/**
 * @author gjt
 * @version 1.0
 * @className type
 * @date 2018/12/6 10:58
 * @description 字段类型
 */
public enum TypeEnum {

    /**
     *  交叉表主键
     */
    CROSS_KEY("交叉表主键","crossKey"),
    /**
     *  交叉表中相同字段
     */
    RAW_FIELD("交叉表中相同字段", "rawField"),
    /**
     *  交叉表数据字段
     */
    CROSS_FIELD("交叉表数据字段", "crossField");

    /**
     * 枚举类型的key
     */
    private String key;
    /**
     * 枚举类型的value
     */
    private String value;

    TypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}