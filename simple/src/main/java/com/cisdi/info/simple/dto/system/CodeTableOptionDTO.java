package com.cisdi.info.simple.dto.system;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;

/**
 * @author: chengbg
 * @date: 2019/6/2
 **/
public class CodeTableOptionDTO {
    private static final long serialVersionUID = 1L;

    @Expose
    @NotBlank
    private String codeTypeId;

    @Expose
    @NotBlank
    private String codeType;

    @Expose
    private int displayIndex = 1;

    @Expose
    private Long orgId;

    @Expose
    @NotBlank
    private String label;

    @Expose
    @NotBlank
    private String value;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public int getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(int displayIndex) {
        this.displayIndex = displayIndex;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(String codeTypeId) {
        this.codeTypeId = codeTypeId;
    }
}
