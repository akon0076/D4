package com.cisdi.info.simple.service.excelTest;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author:chengbg
 * @date:2018/10/22
 */
public interface ExcelExportTestService {
    public void excelExport();

    public void excelImport(MultipartFile file);
}
