package com.cisdi.info.simple.controller.wordTest;

import com.cisdi.info.simple.service.wordTest.WordExportTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author:chengbg
 * @date:2018/10/24
 */
@RestController
@RequestMapping("/simple/word")
@CrossOrigin(allowCredentials = "true")
public class WordController {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private WordExportTestService wordExportTestService;

    @GetMapping("/wordTemplate")
    public String template() throws IOException {
        String filename = this.wordExportTestService.template();
        return filename;
    }

    @GetMapping("/wordExport")
    public void wordExport() throws IOException {
        this.wordExportTestService.wordExport();
    }

}
