

package com.cisdi.info.simple.controller.regist;

import com.cisdi.info.simple.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.regist.EmployeRegistEditDto;
import com.cisdi.info.simple.entity.regist.EmployeRegist;
import com.cisdi.info.simple.service.regist.EmployeRegistService;
import com.cisdi.info.simple.service.organization.EmployeeService;



/**module
{
"simple/regist/EmployeRegist": {
"code": "simple/regist/EmployeRegist",
"name1": "人员注册",
"url": "/simple/regist/EmployeRegist",
"route": "/simple/regist/EmployeRegist",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/regist",
"parentName": "注册管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_regist_EmployeRegist_Add",
	"name1": "新增",
	"fullName": "simple.注册管理.人员注册.新增",
	"moduleCode": "simple/regist/EmployeRegist",
	urls:[
		"/simple/regist/EmployeRegist/createEmployeRegist",
		"/simple/regist/EmployeRegist/saveEmployeRegist"
			
		,"/simple/organization/Employee/findEmployeesWithIdNameByName"
	]
	},
	{
	"code": "simple_regist_EmployeRegist_Edit",
	"name1": "编辑",
	"fullName": "simple.注册管理.人员注册.编辑",
	"moduleCode": "simple/regist/EmployeRegist",
	urls:[
		"/simple/regist/EmployeRegist/findEmployeRegistForEdit",
		"/simple/regist/EmployeRegist/updateEmployeRegist"
		
		,"/simple/organization/Employee/findEmployeesWithIdNameByName"
	]
	},
	{
	"code": "simple_regist_EmployeRegist_Delete",
	"name1": "删除",
	"fullName": "simple.注册管理.人员注册.删除",
	"moduleCode": "simple/regist/EmployeRegist",
	urls:[
	"/simple/regist/EmployeRegist/deleteEmployeRegist"
	]
	},
	{
	"code": "simple_regist_EmployeRegist_View",
	"name1": "查看",
	"fullName": "simple.注册管理.人员注册.查看",
	"moduleCode": "simple/regist/EmployeRegist",
	urls:[
	"/simple/regist/EmployeRegist/findEmployeRegists",
	"/simple/regist/EmployeRegist/findEmployeRegistForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/regist/EmployeRegist")
@CrossOrigin(allowCredentials = "true")
public class EmployeRegistController {
	private static Logger logger = LogManager.getLogger();



			
			
	@Autowired private EmployeRegistService employeRegistService;
	@Autowired private CodeTableService codeTableService;
	@Autowired private EmployeeService employeeService;

	@PostMapping("/findEmployeRegists")
	public PageResultDTO findEmployeRegists(@RequestBody PageDTO pageDTO){
		return this.employeRegistService.findEmployeRegists(pageDTO);
	}

	@GetMapping("/findEmployeRegist")
	public EmployeRegist findEmployeRegist(@RequestParam Long employeRegistId)
	{
		return this.employeRegistService.findEmployeRegist(employeRegistId);
	}

	@GetMapping("/findEmployeRegistForView")
	public EmployeRegist findEmployeRegistForView(@RequestParam Long employeRegistId)
	{
		return this.employeRegistService.findEmployeRegistWithForeignName(employeRegistId);
	}

	@GetMapping("/findEmployeRegistForEdit")
	public EmployeRegistEditDto findEmployeRegistForEdit(@RequestParam Long employeRegistId)
	{
		EmployeRegistEditDto employeRegistEditDto = new EmployeRegistEditDto();
		employeRegistEditDto.setEmployeRegist(this.employeRegistService.findEmployeRegistWithForeignName(employeRegistId));

		this.prepareEmployeRegistEditDto(employeRegistEditDto);

		return employeRegistEditDto;
	}

	//创建新的人员注册
	@GetMapping("/createEmployeRegist")
	public EmployeRegistEditDto createEmployeRegist()
	{
		EmployeRegistEditDto employeRegistEditDto = new EmployeRegistEditDto();
		employeRegistEditDto.setEmployeRegist(new EmployeRegist());

		this.prepareEmployeRegistEditDto(employeRegistEditDto);
		return employeRegistEditDto;
	}

	private void prepareEmployeRegistEditDto(EmployeRegistEditDto employeRegistEditDto)
	{
		employeRegistEditDto.setSexCodeTables(this.codeTableService.findCodeTablesByCodeType("Gender"));
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		employeRegistEditDto.setAuditEmloyeeEmployees(this.employeeService.findAllEmployeesWithIdName());
	}

	@PostMapping("/saveEmployeRegist")
	public EmployeRegist saveEmployeRegist(@RequestBody EmployeRegist employeRegist)
	{
		return this.employeRegistService.saveEmployeRegist(employeRegist);
	}

	@PostMapping("/updateEmployeRegist")
	public EmployeRegist updateEmployeRegist(HttpServletRequest request, @RequestBody EmployeRegist employeRegist)
	{
		return this.employeRegistService.updateEmployeRegist(request,employeRegist);
	}

	@GetMapping("/deleteEmployeRegist")
	public void deleteEmployeRegist(@RequestParam Long employeRegistId)
	{
		this.employeRegistService.deleteEmployeRegist(employeRegistId);
	}
	@GetMapping("/findEmployeRegistsWithIdNameById")
	public EmployeRegist findEmployeRegistsWithIdNameById(@RequestParam Long employeRegistId)
	{
		return this.employeRegistService.findEmployeRegistsWithIdNameById(employeRegistId);
	}

	@GetMapping("/findEmployeRegistsWithIdNameByName")
	public List<EmployeRegist> findEmployeRegistsWithIdNameByName(String employeRegistName)
	{
		return this.employeRegistService.findEmployeRegistsWithIdNameByName(employeRegistName);
	}


}

