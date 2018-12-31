package com.cisdi.info.simple.config;

import com.cisdi.info.simple.DDDException;

import java.util.List;

/**
 * @author gjt
 * @version 1.0
 * @className  SelectTreeConfig
 * @date 2018/12/6 00:00
 * @description 选择树配置
 */
public class SelectTreeConfig {

    /**
     * 组成树的条件（最多2个字段，上下级关系）
     * 源编码
     */
    private String code;
    /**
     * 组成树的条件（最多2个字段，上下级关系）
     * 父级编码
     */
    private String parentCode;
    /**
     * 总长度
     */
    private Long size;
    /**
     * 用于前端渲染树的时候所展示的标签
     */
    private String label;
    /**
     * 这个树的名称（root树根）
     */
    private String treeName;

    private void setSize(){
        if (size <= 2) {
            size++;
        } else {
            throw new DDDException("最多只能提供2个组成树的条件，当前长度：" + size);
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
