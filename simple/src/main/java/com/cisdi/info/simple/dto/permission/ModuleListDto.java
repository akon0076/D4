package com.cisdi.info.simple.dto.permission;
;

import javax.validation.constraints.NotNull;

/**
 * @className: ModuleListDto
 * @Auther: gjt
 * @Date: 2018/9/9 20:17
 * @Description: 模块返回Dto
 * @version: v1.0
 */
public class ModuleListDto {

    private Integer currentPage;

    @NotNull(message = "单页显示条数不能为空")
    private Integer pageSize;

    private Integer startIndex;//分页开始下标

    private String modelName;

    private String code;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
