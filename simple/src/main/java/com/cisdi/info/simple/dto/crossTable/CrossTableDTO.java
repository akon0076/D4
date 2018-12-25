package com.cisdi.info.simple.dto.crossTable;

import com.cisdi.info.simple.entity.crossTable.CrossTableConfig;

import java.util.List;
import java.util.Map;

/**
 * @author gjt
 * @version 1.0
 * @className
 * @date 2018/12/6 14:48
 * @description
 */
public class CrossTableDTO {

    private CrossTableConfig crossTableConfig;

    private List<Map<String,Object>> data;

    public CrossTableConfig getCrossTableConfig() {
        return crossTableConfig;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setCrossTableConfig(CrossTableConfig crossTableConfig) {
        this.crossTableConfig = crossTableConfig;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
