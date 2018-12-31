package com.cisdi.info.simple.entity.crossTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gjt
 * @version 1.0
 * @className
 * @date 2018/12/6 14:49
 * @description
 */
public class CrossTableConfig {
    /**
     * 类名
     */
    private String className;
    /**
     * 交叉表主键
     */
    private List<String>  crossKeys;
    /**
     * 交叉表中相同数据的字段
     */
    private List<String> rowFields;
    /**
     * 交叉表的核心字段{项目：值}
     */
    private Map<String,String> crossFields;
    /**
     * 交叉表常规定义，比如 名称标签等等
     */
    private List<CrossFieldDef> crossFieldDefs  = new ArrayList<CrossFieldDef>();

    public String getClassName() {
        return className;
    }

    public List<String> getCrossKeys() {
        return crossKeys;
    }

    public List<String> getRowFields() {
        return rowFields;
    }

    public Map<String, String> getCrossFields() {
        return crossFields;
    }

    public List<CrossFieldDef> getCrossFieldDefs() {
        return crossFieldDefs;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCrossKeys(List<String> crossKeys) {
        this.crossKeys = crossKeys;
    }

    public void setRowFields(List<String> rowFields) {
        this.rowFields = rowFields;
    }

    public void setCrossFields(Map<String, String> crossFields) {
        this.crossFields = crossFields;
    }

    public void setCrossFieldDefs(List<CrossFieldDef> crossFieldDefs) {
        this.crossFieldDefs = crossFieldDefs;
    }
}
