package com.cisdi.info.simple.controller.excelTest;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.service.excelTest.ExcelExportTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:chengbg
 * @date:2018/10/24
 */
@RestController
@RequestMapping("/simple/excel")
@CrossOrigin(allowCredentials = "true")
public class ExcelController {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private ExcelExportTestService excelExportTestService;

    @GetMapping("/excelExport")
    public void excelExport(PageDTO pageDTO) {
        this.excelExportTestService.excelExport();
    }

    @GetMapping("/excelExportAll")
    public void excelExportAll() {
        this.excelExportTestService.excelExport();
    }

    @PostMapping("/excelImport")
    public void excelImport(MultipartFile file) {
        this.excelExportTestService.excelImport(file);
    }


}
