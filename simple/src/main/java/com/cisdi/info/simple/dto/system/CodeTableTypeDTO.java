package com.cisdi.info.simple.dto.system;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;

/**
 * @author: chengbg
 * @date: 2019/6/2
 **/
public class CodeTableTypeDTO {
    private static final long serialVersionUID = 1L;

    @Expose
    @NotBlank
    private String code;

    @Expose
    @NotBlank
    private String name;

    @Expose
    @NotBlank
    private String codeType;

    @Expose
    private int displayIndex = 1;

    @Expose
    private boolean isPublic = true;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
