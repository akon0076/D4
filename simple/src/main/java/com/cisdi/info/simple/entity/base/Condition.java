package com.cisdi.info.simple.entity.base;

import com.cisdi.info.simple.dto.base.PageDTO;

/**
 * Created by 向鑫 on 2018/10/14
 */
public class Condition {
    private String columnName;

    private String content;

    private  PageDTO pageDTO;

    private String sql;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PageDTO getPageDTO() {
        return pageDTO;
    }

    public void setPageDTO(PageDTO pageDTO) {
        this.pageDTO = pageDTO;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
