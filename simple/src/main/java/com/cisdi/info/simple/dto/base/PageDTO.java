package com.cisdi.info.simple.dto.base;

import com.cisdi.info.simple.util.D4Util;

public class PageDTO {

    private Integer currentPage;

    private Integer pageSize;

    private Integer startIndex;//分页开始下标

    private String columnName;

    private String organizationName;

    private Long organizationId;

    private String content;

    private String sql;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if (this.content == null) {
            this.content = "";
        }
        this.sql = D4Util.assembleSql(this.columnName, this.content);
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        if (this.content == null) {
            this.content = "";
        }
        this.sql = D4Util.assembleSql(this.columnName, this.content);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        if (this.content == null) {
            this.content = "";
        }
        this.sql = D4Util.assembleSql(this.columnName, this.content);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
