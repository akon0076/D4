package com.cisdi.info.simple.entity.crossTable;

/**
 * @author gjt
 * @version 1.0
 * @className DataTypeEnum
 * @date 2018/12/6 11:10
 * @description 交叉表数据类型
 */
public enum  DataTypeEnum {
    /**
     * 整型
     */
    INT("int"),
    /**
     * 字符串
     */
    STRING("String"),
    /**
     *
     */
    BOOLEAN("boolean"),

    FLOAT("float"),

    DATE("date");

    private String dataType;

    DataTypeEnum(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

}
