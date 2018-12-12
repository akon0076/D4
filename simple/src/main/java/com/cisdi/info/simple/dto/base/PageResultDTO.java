package com.cisdi.info.simple.dto.base;

import java.util.List;

public class PageResultDTO {

    private Long totalCount;
    private List<?> datas;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<?> getDatas() {
        return datas;
    }

    public void setDatas(List<?> datas) {
        this.datas = datas;
    }
}
