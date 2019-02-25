

package com.cisdi.info.simple.controller.clue;

import com.cisdi.info.simple.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.clue.BasicMessageEditDto;
import com.cisdi.info.simple.entity.clue.BasicMessage;
import com.cisdi.info.simple.service.clue.BasicMessageService;



/**module
{
"simple/clue/BasicMessage": {
"code": "simple/clue/BasicMessage",
"name1": "基础信息",
"url": "/simple/clue/BasicMessage",
"route": "/simple/clue/BasicMessage",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/clue",
"parentName": "线索管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_clue_BasicMessage_Add",
	"name1": "新增基础信息",
	"fullName": "simple.线索管理.基础信息.新增",
	"moduleCode": "simple/clue/BasicMessage",
	urls:[
		"/simple/clue/BasicMessage/createBasicMessage",
		"/simple/clue/BasicMessage/saveBasicMessage"
	]
	},
	{
	"code": "simple_clue_BasicMessage_Edit",
	"name1": "编辑基础信息",
	"fullName": "simple.线索管理.基础信息.编辑",
	"moduleCode": "simple/clue/BasicMessage",
	urls:[
		"/simple/clue/BasicMessage/findBasicMessageForEdit",
		"/simple/clue/BasicMessage/updateBasicMessage"
	]
	},
	{
	"code": "simple_clue_BasicMessage_Delete",
	"name1": "删除基础信息",
	"fullName": "simple.线索管理.基础信息.删除",
	"moduleCode": "simple/clue/BasicMessage",
	urls:[
	"/simple/clue/BasicMessage/deleteBasicMessage"
	]
	},
	{
	"code": "simple_clue_BasicMessage_View",
	"name1": "查看基础信息",
	"fullName": "simple.线索管理.基础信息.查看",
	"moduleCode": "simple/clue/BasicMessage",
	urls:[
	"/simple/clue/BasicMessage/findBasicMessages",
	"/simple/clue/BasicMessage/findBasicMessageForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/clue/BasicMessage")
@CrossOrigin(allowCredentials = "true")
public class BasicMessageController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private BasicMessageService basicMessageService;

	/**
	* 根据分页参数查询基础信息集合
	*
	* @param pageDTO 分页条件
	*/
	@PostMapping("/findBasicMessages")
	public PageResultDTO findBasicMessages(@RequestBody PageDTO pageDTO){
		return this.basicMessageService.findBasicMessages(pageDTO);
	}

	/**
	* 根据ID查询指定的基础信息
	*
	* @param basicMessageId Id
	*/
	@GetMapping("/findBasicMessage")
	public BasicMessage findBasicMessage(@RequestParam Long basicMessageId){
		return this.basicMessageService.findBasicMessage(basicMessageId);
	}

	/**
	* 根据ID查询指定的基础信息(包含外键名称)
	*
	* @param basicMessageId Id
	*/
	@GetMapping("/findBasicMessageForView")
	public BasicMessage findBasicMessageForView(@RequestParam Long basicMessageId){
		return this.basicMessageService.findBasicMessageWithForeignName(basicMessageId);
	}

	/**
	* 根据ID查询指定的基础信息(包含基础信息和外键名称)
	*
	* @param basicMessageId Id
	*/
	@GetMapping("/findBasicMessageForEdit")
	public BasicMessageEditDto findBasicMessageForEdit(@RequestParam Long basicMessageId){
		BasicMessageEditDto basicMessageEditDto = new BasicMessageEditDto();
		basicMessageEditDto.setBasicMessage(this.basicMessageService.findBasicMessageWithForeignName(basicMessageId));

		this.prepareBasicMessageEditDto(basicMessageEditDto);

		return basicMessageEditDto;
	}

	/**
	* 创建新的基础信息
	*
	*/
	@GetMapping("/createBasicMessage")
	public BasicMessageEditDto createBasicMessage(){
		BasicMessageEditDto basicMessageEditDto = new BasicMessageEditDto();
		basicMessageEditDto.setBasicMessage(new BasicMessage());

		this.prepareBasicMessageEditDto(basicMessageEditDto);
		return basicMessageEditDto;
	}

	private void prepareBasicMessageEditDto(BasicMessageEditDto basicMessageEditDto){
	}

	/**
	* 更新基础信息
	*
	* @param basicMessage 实体对象
	*/
	@PostMapping("/saveBasicMessage")
	public BasicMessage saveBasicMessage(@RequestBody BasicMessage basicMessage){
		return this.basicMessageService.saveBasicMessage(basicMessage);
	}

	@PostMapping("/updateBasicMessage")
	public BasicMessage updateBasicMessage(@RequestBody BasicMessage basicMessage){
		return this.basicMessageService.updateBasicMessage(basicMessage);
	}

	/**
	* 根据ID删除基础信息
	*
	* @param basicMessageId ID
	*/
	@GetMapping("/deleteBasicMessage")
	public void deleteBasicMessage(@RequestParam Long basicMessageId){
		this.basicMessageService.deleteBasicMessage(basicMessageId);
	}

	/**
	* 根据ID查询指定的基础信息(只提取ID 和 Name)
	*
	* @param basicMessageId Id
	*/
	@GetMapping("/findBasicMessagesWithIdNameById")
	public BasicMessage findBasicMessagesWithIdNameById(@RequestParam Long basicMessageId){
		return this.basicMessageService.findBasicMessagesWithIdNameById(basicMessageId);
	}

	/**
	* 根据名称查询基础信息集合(只提取ID 和 Name)
	*
	* @param basicMessageName 名称
	*/
	@GetMapping("/findBasicMessagesWithIdNameByName")
	public List<BasicMessage> findBasicMessagesWithIdNameByName(String basicMessageName){
		return this.basicMessageService.findBasicMessagesWithIdNameByName(basicMessageName);
	}
}

