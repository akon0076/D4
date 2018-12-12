

package com.cisdi.info.simple.controller.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.OperatorEditDto;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**module
{
"simple/permission/Operator": {
"code": "simple/permission/Operator",
"name1": "操作员",
"url": "/simple/permission/Operator",
"route": "/simple/permission/Operator",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/permission",
"parentName": "授权",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_permission_Operator_Add",
	"name1": "新增",
	"fullName": "simple.授权.操作员.新增",
	"moduleCode": "simple/permission/Operator",
	urls:[
		"/simple/permission/Operator/createOperator",
		"/simple/permission/Operator/saveOperator"
	]
	},
	{
	"code": "simple_permission_Operator_Edit",
	"name1": "编辑",
	"fullName": "simple.授权.操作员.编辑",
	"moduleCode": "simple/permission/Operator",
	urls:[
		"/simple/permission/Operator/findOperatorForEdit",
		"/simple/permission/Operator/updateOperator"
	]
	},
	{
	"code": "simple_permission_Operator_Delete",
	"name1": "删除",
	"fullName": "simple.授权.操作员.删除",
	"moduleCode": "simple/permission/Operator",
	urls:[
	"/simple/permission/Operator/deleteOperator"
	]
	},
	{
	"code": "simple_permission_Operator_View",
	"name1": "查看",
	"fullName": "simple.授权.操作员.查看",
	"moduleCode": "simple/permission/Operator",
	urls:[
	"/simple/permission/Operator/findOperators",
	"/simple/permission/Operator/findOperatorForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/permission/Operator")
@CrossOrigin(allowCredentials = "true")
public class OperatorController {
	private static Logger logger = LogManager.getLogger();



			
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private CodeTableService codeTableService;

	@PostMapping("/findOperators")
	public PageResultDTO findOperators(@RequestBody PageDTO pageDTO){
		return this.operatorService.findOperators(pageDTO);
	}

	@GetMapping("/findOperator")
	public Operator findOperator(@RequestParam Long operatorId)
	{
		return this.operatorService.findOperator(operatorId);
	}

	@GetMapping("/findOperatorForView")
	public Operator findOperatorForView(@RequestParam Long operatorId)
	{
		return this.operatorService.findOperatorWithForeignName(operatorId);
	}

	@GetMapping("/findOperatorForEdit")
	public OperatorEditDto findOperatorForEdit(@RequestParam Long operatorId)
	{
		OperatorEditDto operatorEditDto = new OperatorEditDto();
		operatorEditDto.setOperator(this.operatorService.findOperatorWithForeignName(operatorId));

		this.prepareOperatorEditDto(operatorEditDto);

		return operatorEditDto;
	}

	//创建新的操作员
	@GetMapping("/createOperator")
	public OperatorEditDto createOperator()
	{
		OperatorEditDto operatorEditDto = new OperatorEditDto();
		operatorEditDto.setOperator(new Operator());

		this.prepareOperatorEditDto(operatorEditDto);
		return operatorEditDto;
	}

	private void prepareOperatorEditDto(OperatorEditDto operatorEditDto)
	{
		operatorEditDto.setStatusCodeTables(this.codeTableService.findCodeTablesByCodeType("OperatorStatus"));
		operatorEditDto.setTypeCodeTables(this.codeTableService.findCodeTablesByCodeType("OperatorType"));
	}

	@PostMapping("/saveOperator")
	public Operator saveOperator(@RequestBody Operator operator)
	{
		return this.operatorService.saveOperator(operator);
	}

	@PostMapping("/updateOperator")
	public Operator updateOperator(@RequestBody Operator operator)
	{
		return this.operatorService.updateOperator(operator);
	}

	@GetMapping("/deleteOperator")
	public void deleteOperator(@RequestParam Long operatorId)
	{
		this.operatorService.deleteOperator(operatorId);
	}
	@GetMapping("/findOperatorsWithIdNameById")
	public Operator findOperatorsWithIdNameById(@RequestParam Long operatorId)
	{
		return null;//this.operatorService.findOperatorsWithIdNameById(operatorId);
	}

	@GetMapping("/findOperatorsWithIdNameByName")
	public List<Operator> findOperatorsWithIdNameByName(String operatorName)
	{
		return null;//this.operatorService.findOperatorsWithIdNameByName(operatorName);
	}
}

