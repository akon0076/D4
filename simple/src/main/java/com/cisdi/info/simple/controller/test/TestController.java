

package com.cisdi.info.simple.controller.test;

import com.cisdi.info.simple.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.test.TestEditDto;
import com.cisdi.info.simple.entity.test.Test;
import com.cisdi.info.simple.service.test.TestService;



/**module
{
"simple/test/Test": {
"code": "simple/test/Test",
"name1": "测试管理",
"url": "/simple/test/Test",
"route": "/simple/test/Test",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/test",
"parentName": "测试管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_test_Test_Add",
	"name1": "新增测试管理",
	"fullName": "simple.测试管理.测试管理.新增",
	"moduleCode": "simple/test/Test",
	urls:[
		"/simple/test/Test/createTest",
		"/simple/test/Test/saveTest"
	]
	},
	{
	"code": "simple_test_Test_Edit",
	"name1": "编辑测试管理",
	"fullName": "simple.测试管理.测试管理.编辑",
	"moduleCode": "simple/test/Test",
	urls:[
		"/simple/test/Test/findTestForEdit",
		"/simple/test/Test/updateTest"
	]
	},
	{
	"code": "simple_test_Test_Delete",
	"name1": "删除测试管理",
	"fullName": "simple.测试管理.测试管理.删除",
	"moduleCode": "simple/test/Test",
	urls:[
	"/simple/test/Test/deleteTest"
	]
	},
	{
	"code": "simple_test_Test_View",
	"name1": "查看测试管理",
	"fullName": "simple.测试管理.测试管理.查看",
	"moduleCode": "simple/test/Test",
	urls:[
	"/simple/test/Test/findTests",
	"/simple/test/Test/findTestForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/test/Test")
@CrossOrigin(allowCredentials = "true")
public class TestController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private TestService testService;

	/**
	* 根据分页参数查询测试管理集合
	*
	* @param pageDTO 分页条件
	*/
	@PostMapping("/findTests")
	public PageResultDTO findTests(@RequestBody PageDTO pageDTO){
		return this.testService.findTests(pageDTO);
	}

	/**
	* 根据ID查询指定的测试管理
	*
	* @param testId Id
	*/
	@GetMapping("/findTest")
	public Test findTest(@RequestParam Long testId){
		return this.testService.findTest(testId);
	}

	/**
	* 根据ID查询指定的测试管理(包含外键名称)
	*
	* @param testId Id
	*/
	@GetMapping("/findTestForView")
	public Test findTestForView(@RequestParam Long testId){
		return this.testService.findTestWithForeignName(testId);
	}

	/**
	* 根据ID查询指定的测试管理(包含测试管理和外键名称)
	*
	* @param testId Id
	*/
	@GetMapping("/findTestForEdit")
	public TestEditDto findTestForEdit(@RequestParam Long testId){
		TestEditDto testEditDto = new TestEditDto();
		testEditDto.setTest(this.testService.findTestWithForeignName(testId));

		this.prepareTestEditDto(testEditDto);

		return testEditDto;
	}

	/**
	* 创建新的测试管理
	*
	*/
	@GetMapping("/createTest")
	public TestEditDto createTest(){
		TestEditDto testEditDto = new TestEditDto();
		testEditDto.setTest(new Test());

		this.prepareTestEditDto(testEditDto);
		return testEditDto;
	}

	private void prepareTestEditDto(TestEditDto testEditDto){
	}

	/**
	* 更新测试管理
	*
	* @param test 实体对象
	*/
	@PostMapping("/saveTest")
	public Test saveTest(@RequestBody Test test){
		return this.testService.saveTest(test);
	}

	@PostMapping("/updateTest")
	public Test updateTest(@RequestBody Test test){
		return this.testService.updateTest(test);
	}

	/**
	* 根据ID删除测试管理
	*
	* @param testId ID
	*/
	@GetMapping("/deleteTest")
	public void deleteTest(@RequestParam Long testId){
		this.testService.deleteTest(testId);
	}

	/**
	* 根据ID查询指定的测试管理(只提取ID 和 Name)
	*
	* @param testId Id
	*/
	@GetMapping("/findTestsWithIdNameById")
	public Test findTestsWithIdNameById(@RequestParam Long testId){
		return this.testService.findTestsWithIdNameById(testId);
	}

	/**
	* 根据名称查询测试管理集合(只提取ID 和 Name)
	*
	* @param testName 名称
	*/
	@GetMapping("/findTestsWithIdNameByName")
	public List<Test> findTestsWithIdNameByName(String testName){
		return this.testService.findTestsWithIdNameByName(testName);
	}
}

