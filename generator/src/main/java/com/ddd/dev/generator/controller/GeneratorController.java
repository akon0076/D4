package com.ddd.dev.generator.controller;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.system.CodeTableDao;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.ddd.dev.generator.service.GeneratorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/generator")
@CrossOrigin(allowCredentials = "true")
public class GeneratorController {

    @Autowired
    private CodeTableDao codeTableDao;

    @Autowired
    private GeneratorService  generatorService;

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/generateModel")
    public Map<String, String> generateModel(String modelDefinition)
    {
        Map<String, String> result = this.generatorService.generateModel(modelDefinition);
        //System.out.println(modelDefinition);
        return result;
    }

    @GetMapping("/generateBaseCode")
    public Map<String,String> generateBaseCode(String className)
    {
//        CodeTable codeTable = this.codeTableDao.findCodeTable(1l);
//        System.out.println(codeTable.toString());
//        PageDTO pageDTO = new PageDTO();
//        pageDTO.setStartIndex(0);
//        pageDTO.setPageSize(100);
//        List<CodeTable> codeTables = this.codeTableDao.findCodeTables(pageDTO);
//        System.out.println(codeTables);
//
//        codeTables = this.codeTableDao.findAllCodeTables();
//        System.out.println(codeTables);
//
//        codeTables = this.codeTableDao.findAllCodeTablesWithIdName();
//        System.out.println(codeTables);

        Map<String,String> results = new HashMap<String, String>();

        className = StringUtils.trimToEmpty(className);
        for(String className1:StringUtils.split(className, "\n"))
        {
            results.putAll(this.generatorService.generateBaseCode(className1));
        }

        return results;
    }

    @GetMapping("/generateMethod")
    public  String generateMethod(String controllerMethodName) throws IOException {
        return this.generatorService.generateMethod(controllerMethodName);
    }

    @GetMapping("/createSuperOperator")
    public  void createSuperOperator() throws IOException {
        this.operatorService.createSuperUser();
    }

    @GetMapping("/testException")
    public  void testException() throws Exception {
         DDDException dddException = new DDDException("这是个测试");
         dddException.setCode("590");
         dddException.putExtendedData("name", "小明");
         dddException.putExtendedData("age", "11");
        throw dddException;

    }




}
