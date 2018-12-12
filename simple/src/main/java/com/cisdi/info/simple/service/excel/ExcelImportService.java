package com.cisdi.info.simple.service.excel;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author:chengbg
 * @date:2018/10/31
 */
public interface ExcelImportService {
    public List<Map<String, Object>> excelImport(MultipartFile file) throws Exception;
}
