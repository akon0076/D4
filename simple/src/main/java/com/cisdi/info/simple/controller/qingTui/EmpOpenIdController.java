

package com.cisdi.info.simple.controller.qingTui;

import com.cisdi.info.simple.dto.qingTui.BindingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.qingTui.EmpOpenIdEditDto;
import com.cisdi.info.simple.entity.qingTui.EmpOpenId;
import com.cisdi.info.simple.service.qingTui.EmpOpenIdService;
import com.cisdi.info.simple.service.organization.EmployeeService;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.service.member.MemberService;



/**module
{
"simple/qingTui/EmpOpenId": {
"code": "simple/qingTui/EmpOpenId",
"name1": "人员与应用",
"url": "/simple/qingTui/EmpOpenId",
"route": "/simple/qingTui/EmpOpenId",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/qingTui",
"parentName": "人员与应用",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_qingTui_EmpOpenId_Add",
	"name1": "新增",
	"fullName": "simple.人员与应用.人员与应用.新增",
	"moduleCode": "simple/qingTui/EmpOpenId",
	urls:[
		"/simple/qingTui/EmpOpenId/createEmpOpenId",
		"/simple/qingTui/EmpOpenId/saveEmpOpenId"
			
			
			
		,"/simple/organization/Employee/findEmployeesWithIdNameByName"
		,"/simple/permission/Operator/findOperatorsWithIdNameByName"
		,"/simple/member/Member/findMembersWithIdNameByName"
	]
	},
	{
	"code": "simple_qingTui_EmpOpenId_Edit",
	"name1": "编辑",
	"fullName": "simple.人员与应用.人员与应用.编辑",
	"moduleCode": "simple/qingTui/EmpOpenId",
	urls:[
		"/simple/qingTui/EmpOpenId/findEmpOpenIdForEdit",
		"/simple/qingTui/EmpOpenId/updateEmpOpenId"
		
		
		
		,"/simple/organization/Employee/findEmployeesWithIdNameByName"
		,"/simple/permission/Operator/findOperatorsWithIdNameByName"
		,"/simple/member/Member/findMembersWithIdNameByName"
	]
	},
	{
	"code": "simple_qingTui_EmpOpenId_Delete",
	"name1": "删除",
	"fullName": "simple.人员与应用.人员与应用.删除",
	"moduleCode": "simple/qingTui/EmpOpenId",
	urls:[
	"/simple/qingTui/EmpOpenId/deleteEmpOpenId"
	]
	},
	{
	"code": "simple_qingTui_EmpOpenId_View",
	"name1": "查看",
	"fullName": "simple.人员与应用.人员与应用.查看",
	"moduleCode": "simple/qingTui/EmpOpenId",
	urls:[
	"/simple/qingTui/EmpOpenId/findEmpOpenIds",
	"/simple/qingTui/EmpOpenId/findEmpOpenIdForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/qingTui/EmpOpenId")
@CrossOrigin(allowCredentials = "true")
public class EmpOpenIdController {
	private static Logger logger = LogManager.getLogger();



			
			
			
	@Autowired private EmpOpenIdService empOpenIdService;
	@Autowired private EmployeeService employeeService;
	@Autowired private OperatorService operatorService;
	@Autowired private MemberService memberService;

	@PostMapping("/findEmpOpenIds")
	public PageResultDTO findEmpOpenIds(@RequestBody PageDTO pageDTO){
		return this.empOpenIdService.findEmpOpenIds(pageDTO);
	}

	@GetMapping("/findEmpOpenId")
	public EmpOpenId findEmpOpenId(@RequestParam Long empOpenIdId)
	{
		return this.empOpenIdService.findEmpOpenId(empOpenIdId);
	}

	@GetMapping("/findEmpOpenIdForView")
	public EmpOpenId findEmpOpenIdForView(@RequestParam Long empOpenIdId)
	{
		return this.empOpenIdService.findEmpOpenIdWithForeignName(empOpenIdId);
	}

	@GetMapping("/findEmpOpenIdForEdit")
	public EmpOpenIdEditDto findEmpOpenIdForEdit(@RequestParam Long empOpenIdId)
	{
		EmpOpenIdEditDto empOpenIdEditDto = new EmpOpenIdEditDto();
		empOpenIdEditDto.setEmpOpenId(this.empOpenIdService.findEmpOpenIdWithForeignName(empOpenIdId));

		this.prepareEmpOpenIdEditDto(empOpenIdEditDto);

		return empOpenIdEditDto;
	}

	//创建新的人员与应用
	@GetMapping("/createEmpOpenId")
	public EmpOpenIdEditDto createEmpOpenId()
	{
		EmpOpenIdEditDto empOpenIdEditDto = new EmpOpenIdEditDto();
		empOpenIdEditDto.setEmpOpenId(new EmpOpenId());

		this.prepareEmpOpenIdEditDto(empOpenIdEditDto);
		return empOpenIdEditDto;
	}

	private void prepareEmpOpenIdEditDto(EmpOpenIdEditDto empOpenIdEditDto)
	{
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		empOpenIdEditDto.setEmployeeEmployees(this.employeeService.findAllEmployeesWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		empOpenIdEditDto.setOperatorOperators(this.operatorService.findAllOperatorsWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		empOpenIdEditDto.setMemberMembers(this.memberService.findAllMembersWithIdName());
	}

	@PostMapping("/saveEmpOpenId")
	public EmpOpenId saveEmpOpenId(@RequestBody EmpOpenId empOpenId)
	{
		return this.empOpenIdService.saveEmpOpenId(empOpenId);
	}

	@PostMapping("/updateEmpOpenId")
	public EmpOpenId updateEmpOpenId(@RequestBody EmpOpenId empOpenId)
	{
		return this.empOpenIdService.updateEmpOpenId(empOpenId);
	}

	@GetMapping("/deleteEmpOpenId")
	public void deleteEmpOpenId(@RequestParam Long empOpenIdId)
	{
		this.empOpenIdService.deleteEmpOpenId(empOpenIdId);
	}
	@GetMapping("/findEmpOpenIdsWithIdNameById")
	public EmpOpenId findEmpOpenIdsWithIdNameById(@RequestParam Long empOpenIdId)
	{
		return this.empOpenIdService.findEmpOpenIdsWithIdNameById(empOpenIdId);
	}

	@GetMapping("/findEmpOpenIdsWithIdNameByName")
	public List<EmpOpenId> findEmpOpenIdsWithIdNameByName(String empOpenIdName)
	{
		return this.empOpenIdService.findEmpOpenIdsWithIdNameByName(empOpenIdName);
	}

	@PostMapping("/bindingQingtui")
	public Map<String,Object> bindingQingtui(@RequestBody BindingDto bindingDto)
	{
		return this.empOpenIdService.bindingQingtui(bindingDto);
	}
}

