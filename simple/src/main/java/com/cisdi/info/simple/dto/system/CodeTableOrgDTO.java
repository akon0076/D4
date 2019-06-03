package com.cisdi.info.simple.dto.system;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: chengbg
 * @date: 2019/6/2
 **/
public class CodeTableOrgDTO {
    private static final long serialVersionUID = 1L;

    @Expose
    @NotBlank
    private String codeTypeId;

    @Expose
    @NotBlank
    private String codeType;

    @Expose
    @NotBlank
    private String name;

    @Expose
    private int displayIndex = 1;

    @Expose
    @NotNull
    private Long orgId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(String codeTypeId) {
        this.codeTypeId = codeTypeId;
    }
}
