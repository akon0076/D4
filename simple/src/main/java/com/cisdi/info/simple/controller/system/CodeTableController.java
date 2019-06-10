

package com.cisdi.info.simple.controller.system;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.system.CodeTableEditDto;
import com.cisdi.info.simple.dto.system.CodeTableOptionDTO;
import com.cisdi.info.simple.dto.system.CodeTableTypeDTO;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * module
 * {
 * "simple/system/CodeTable": {
 * "code": "simple/system/CodeTable",
 * "name1": "码表",
 * "url": "/simple/system/CodeTable",
 * "route": "/simple/system/CodeTable",
 * "iconClass": "",
 * "displayIndex": 1,
 * "parentCode": "simple/system",
 * "parentName": "系统管理",
 * "moduleType": "电脑模块",
 * "isInUse": "是",
 * "routeParamsObj": "",
 * "permissions":
 * [
 * {
 * "code": "simple_system_CodeTable_Add",
 * "name1": "新增",
 * "fullName": "simple.系统管理.码表.新增",
 * "moduleCode": "simple/system/CodeTable",
 * urls:[
 * "/simple/system/CodeTable/createCodeTable",
 * "/simple/system/CodeTable/saveCodeTable"
 * <p>
 * ,"/simple/system/CodeTable/findCodeTablesWithIdNameByName"
 * ]
 * },
 * {
 * "code": "simple_system_CodeTable_Edit",
 * "name1": "编辑",
 * "fullName": "simple.系统管理.码表.编辑",
 * "moduleCode": "simple/system/CodeTable",
 * urls:[
 * "/simple/system/CodeTable/findCodeTableForEdit",
 * "/simple/system/CodeTable/updateCodeTable"
 * <p>
 * ,"/simple/system/CodeTable/findCodeTablesWithIdNameByName"
 * ]
 * },
 * {
 * "code": "simple_system_CodeTable_Delete",
 * "name1": "删除",
 * "fullName": "simple.系统管理.码表.删除",
 * "moduleCode": "simple/system/CodeTable",
 * urls:[
 * "/simple/system/CodeTable/deleteCodeTable"
 * ]
 * },
 * {
 * "code": "simple_system_CodeTable_View",
 * "name1": "查看",
 * "fullName": "simple.系统管理.码表.查看",
 * "moduleCode": "simple/system/CodeTable",
 * urls:[
 * "/simple/system/CodeTable/findCodeTables",
 * "/simple/system/CodeTable/findCodeTableForView"
 * ]
 * }
 * ]
 * }
 * }
 */

@RestController
@RequestMapping("/simple/system/CodeTable")
@CrossOrigin(allowCredentials = "true")
public class CodeTableController {
    private static Logger logger = LogManager.getLogger();


    @Autowired
    private CodeTableService codeTableService;

    @PostMapping("/findCodeTables")
    public PageResultDTO findCodeTables(@RequestBody PageDTO pageDTO) {
        PageResultDTO pageResultDTO = new PageResultDTO();
        List<CodeTable> codeTables = this.codeTableService.findCodeTables(pageDTO);
        pageResultDTO.setDatas(codeTables);
        pageResultDTO.setTotalCount((long) codeTables.size());
        return pageResultDTO;
    }

    @GetMapping("/findCodeTable")
    public CodeTable findCodeTable(@RequestParam String uuid) {
        return this.codeTableService.findCodeTable(uuid);
    }

    //创建新的码表
    @GetMapping("/createCodeTable")
    public CodeTableEditDto createCodeTable() {
        CodeTableEditDto codeTableEditDto = new CodeTableEditDto();
        codeTableEditDto.setCodeTable(new CodeTable());

        this.prepareCodeTableEditDto(codeTableEditDto);
        return codeTableEditDto;
    }

    private void prepareCodeTableEditDto(CodeTableEditDto codeTableEditDto) {
    }

    @PostMapping("/updateCodeTable")
    public CodeTable updateCodeTable(@RequestBody CodeTable codeTable) {
        return this.codeTableService.updateCodeTable(codeTable);
    }

    @PostMapping("/updateCodeTableOption")
    public CodeTable updateCodeTableOption(@RequestBody CodeTable codeTable) {
        return this.codeTableService.updateCodeTableOption(codeTable);
    }

    @GetMapping("/deleteCodeTable")
    public void deleteCodeTable(@RequestParam String codeTableId) {
        this.codeTableService.deleteCodeTable(codeTableId);
    }

    @GetMapping("/findAllCodeType")
    public List<CodeTable> findAllCodeType() {
        return this.codeTableService.findAllCodeType();
    }

    @PostMapping("/saveCodeTable")
    public CodeTable saveCodeTable(@RequestBody @Valid CodeTableTypeDTO codeTable) {
        return this.codeTableService.saveCodeTable(codeTable);
    }

    @PostMapping("/saveOption")
    public CodeTable saveOption(@RequestBody @Valid CodeTableOptionDTO codeTable) {
        return this.codeTableService.saveOption(codeTable);
    }

    @PostMapping("/findAllCodeTablesTree")
    public PageResultDTO saveOption(@RequestBody PageDTO pageDTO) {
        return this.codeTableService.findAllCodeTablesTree(pageDTO);
    }

    @GetMapping("/findCodeTableByCode")
    public List<CodeTable> findCodeTableByCode(@RequestParam String code) {
        return this.codeTableService.findCodeTableByCode(code);
    }

}

