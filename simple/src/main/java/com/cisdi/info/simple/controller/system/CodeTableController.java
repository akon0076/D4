

package com.cisdi.info.simple.controller.system;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.system.CodeTableEditDto;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**module
{
"simple/system/CodeTable": {
"code": "simple/system/CodeTable",
"name1": "码表",
"url": "/simple/system/CodeTable",
"route": "/simple/system/CodeTable",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/system",
"parentName": "系统管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_system_CodeTable_Add",
	"name1": "新增",
	"fullName": "simple.系统管理.码表.新增",
	"moduleCode": "simple/system/CodeTable",
	urls:[
		"/simple/system/CodeTable/createCodeTable",
		"/simple/system/CodeTable/saveCodeTable"
			
		,"/simple/system/CodeTable/findCodeTablesWithIdNameByName"
	]
	},
	{
	"code": "simple_system_CodeTable_Edit",
	"name1": "编辑",
	"fullName": "simple.系统管理.码表.编辑",
	"moduleCode": "simple/system/CodeTable",
	urls:[
		"/simple/system/CodeTable/findCodeTableForEdit",
		"/simple/system/CodeTable/updateCodeTable"
		
		,"/simple/system/CodeTable/findCodeTablesWithIdNameByName"
	]
	},
	{
	"code": "simple_system_CodeTable_Delete",
	"name1": "删除",
	"fullName": "simple.系统管理.码表.删除",
	"moduleCode": "simple/system/CodeTable",
	urls:[
	"/simple/system/CodeTable/deleteCodeTable"
	]
	},
	{
	"code": "simple_system_CodeTable_View",
	"name1": "查看",
	"fullName": "simple.系统管理.码表.查看",
	"moduleCode": "simple/system/CodeTable",
	urls:[
	"/simple/system/CodeTable/findCodeTables",
	"/simple/system/CodeTable/findCodeTableForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/system/CodeTable")
@CrossOrigin(allowCredentials = "true")
public class CodeTableController {
	private static Logger logger = LogManager.getLogger();



			
	@Autowired private CodeTableService codeTableService;

	@PostMapping("/findCodeTables")
	public PageResultDTO findCodeTables(@RequestBody PageDTO pageDTO){
		return this.codeTableService.findCodeTables(pageDTO);
	}

	@GetMapping("/findCodeTable")
	public CodeTable findCodeTable(@RequestParam Long codeTableId)
	{
		return this.codeTableService.findCodeTable(codeTableId);
	}

	@GetMapping("/findCodeTableForView")
	public CodeTable findCodeTableForView(@RequestParam Long codeTableId)
	{
		return this.codeTableService.findCodeTableWithForeignName(codeTableId);
	}

	@GetMapping("/findCodeTableForEdit")
	public CodeTableEditDto findCodeTableForEdit(@RequestParam Long codeTableId)
	{
		CodeTableEditDto codeTableEditDto = new CodeTableEditDto();
		codeTableEditDto.setCodeTable(this.codeTableService.findCodeTableWithForeignName(codeTableId));

		this.prepareCodeTableEditDto(codeTableEditDto);

		return codeTableEditDto;
	}

	//创建新的码表
	@GetMapping("/createCodeTable")
	public CodeTableEditDto createCodeTable()
	{
		CodeTableEditDto codeTableEditDto = new CodeTableEditDto();
		codeTableEditDto.setCodeTable(new CodeTable());

		this.prepareCodeTableEditDto(codeTableEditDto);
		return codeTableEditDto;
	}

	private void prepareCodeTableEditDto(CodeTableEditDto codeTableEditDto)
	{
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		codeTableEditDto.setParentCodeTables(this.codeTableService.findAllCodeTablesWithIdName());
	}

	@PostMapping("/saveCodeTable")
	public CodeTable saveCodeTable(@RequestBody CodeTable codeTable)
	{
		return this.codeTableService.saveCodeTable(codeTable);
	}

	@PostMapping("/updateCodeTable")
	public CodeTable updateCodeTable(@RequestBody CodeTable codeTable)
	{
		return this.codeTableService.updateCodeTable(codeTable);
	}

	@GetMapping("/deleteCodeTable")
	public void deleteCodeTable(@RequestParam Long codeTableId)
	{
		this.codeTableService.deleteCodeTable(codeTableId);
	}
	@GetMapping("/findCodeTablesWithIdNameById")
	public CodeTable findCodeTablesWithIdNameById(@RequestParam Long codeTableId)
	{
		return null;//this.codeTableService.findCodeTablesWithIdNameById(codeTableId);
	}

	@GetMapping("/findCodeTablesWithIdNameByName")
	public List<CodeTable> findCodeTablesWithIdNameByName(String codeTableName)
	{
		return null;//this.codeTableService.findCodeTablesWithIdNameByName(codeTableName);
	}
}

