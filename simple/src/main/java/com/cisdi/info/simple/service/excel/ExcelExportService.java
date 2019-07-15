package com.cisdi.info.simple.service.excel;

import java.util.List;
import java.util.Map;

/**
 * @author:chengbg
 * @date:2018/10/22
 */
public interface ExcelExportService {
    public <T> void excelExport(List<T> list, Map<String, String> header, Class<T> clazz, String fineName) throws Exception;
}
