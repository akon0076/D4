

package com.cisdi.info.simple.controller.task;
import com.cisdi.info.simple.service.system.CodeTableService;
import com.cisdi.info.simple.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.task.TaskConfigEditDto;
import com.cisdi.info.simple.entity.task.TaskConfig;
import com.cisdi.info.simple.service.task.TaskConfigService;



/**module
{
"simple/task/TaskConfig": {
"code": "simple/task/TaskConfig",
"name1": "定时任务",
"url": "/simple/task/TaskConfig",
"route": "/simple/task/TaskConfig",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/task",
"parentName": "定时任务配置管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_task_TaskConfig_Add",
	"name1": "新增定时任务",
	"fullName": "simple.定时任务配置管理.定时任务.新增",
	"moduleCode": "simple/task/TaskConfig",
	urls:[
		"/simple/task/TaskConfig/createTaskConfig",
		"/simple/task/TaskConfig/saveTaskConfig"
	]
	},
	{
	"code": "simple_task_TaskConfig_Edit",
	"name1": "编辑定时任务",
	"fullName": "simple.定时任务配置管理.定时任务.编辑",
	"moduleCode": "simple/task/TaskConfig",
	urls:[
		"/simple/task/TaskConfig/findTaskConfigForEdit",
		"/simple/task/TaskConfig/updateTaskConfig"
	]
	},
	{
	"code": "simple_task_TaskConfig_Delete",
	"name1": "删除定时任务",
	"fullName": "simple.定时任务配置管理.定时任务.删除",
	"moduleCode": "simple/task/TaskConfig",
	urls:[
	"/simple/task/TaskConfig/deleteTaskConfig"
	]
	},
	{
	"code": "simple_task_TaskConfig_View",
	"name1": "查看定时任务",
	"fullName": "simple.定时任务配置管理.定时任务.查看",
	"moduleCode": "simple/task/TaskConfig",
	urls:[
	"/simple/task/TaskConfig/findTaskConfigs",
	"/simple/task/TaskConfig/findTaskConfigForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/task/TaskConfig")
@CrossOrigin(allowCredentials = "true")
public class TaskConfigController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private TaskConfigService taskConfigService;

	/**
	* 根据分页参数查询定时任务集合
	*
	* @param pageDTO 分页条件
	*/
	@PostMapping("/findTaskConfigs")
	public PageResultDTO findTaskConfigs(@RequestBody PageDTO pageDTO){
		return this.taskConfigService.findTaskConfigs(pageDTO);
	}

	/**
	* 根据ID查询指定的定时任务
	*
	* @param taskConfigId Id
	*/
	@GetMapping("/findTaskConfig")
	public TaskConfig findTaskConfig(@RequestParam Long taskConfigId){
		return this.taskConfigService.findTaskConfig(taskConfigId);
	}

	/**
	* 根据ID查询指定的定时任务(包含外键名称)
	*
	* @param taskConfigId Id
	*/
	@GetMapping("/findTaskConfigForView")
	public TaskConfig findTaskConfigForView(@RequestParam Long taskConfigId){
		return this.taskConfigService.findTaskConfigWithForeignName(taskConfigId);
	}

	/**
	* 根据ID查询指定的定时任务(包含定时任务和外键名称)
	*
	* @param taskConfigId Id
	*/
	@GetMapping("/findTaskConfigForEdit")
	public TaskConfigEditDto findTaskConfigForEdit(@RequestParam Long taskConfigId){
		TaskConfigEditDto taskConfigEditDto = new TaskConfigEditDto();
		taskConfigEditDto.setTaskConfig(this.taskConfigService.findTaskConfigWithForeignName(taskConfigId));

		this.prepareTaskConfigEditDto(taskConfigEditDto);

		return taskConfigEditDto;
	}

	/**
	* 创建新的定时任务
	*
	*/
	@GetMapping("/createTaskConfig")
	public TaskConfigEditDto createTaskConfig(){
		TaskConfigEditDto taskConfigEditDto = new TaskConfigEditDto();
		taskConfigEditDto.setTaskConfig(new TaskConfig());

		this.prepareTaskConfigEditDto(taskConfigEditDto);
		return taskConfigEditDto;
	}

	private void prepareTaskConfigEditDto(TaskConfigEditDto taskConfigEditDto){
	}

	/**
	* 更新定时任务
	*
	* @param taskConfig 实体对象
	*/
	@PostMapping("/saveTaskConfig")
	public TaskConfig saveTaskConfig(@RequestBody TaskConfig taskConfig){
		return this.taskConfigService.saveTaskConfig(taskConfig);
	}

	@PostMapping("/updateTaskConfig")
	public TaskConfig updateTaskConfig(@RequestBody TaskConfig taskConfig){
		return this.taskConfigService.updateTaskConfig(taskConfig);
	}

	/**
	* 根据ID删除定时任务
	*
	* @param taskConfigId ID
	*/
	@GetMapping("/deleteTaskConfig")
	public void deleteTaskConfig(@RequestParam Long taskConfigId){
		this.taskConfigService.deleteTaskConfig(taskConfigId);
	}

	/**
	* 根据ID查询指定的定时任务(只提取ID 和 Name)
	*
	* @param taskConfigId Id
	*/
	@GetMapping("/findTaskConfigsWithIdNameById")
	public TaskConfig findTaskConfigsWithIdNameById(@RequestParam Long taskConfigId){
		return this.taskConfigService.findTaskConfigsWithIdNameById(taskConfigId);
	}

	/**
	* 根据名称查询定时任务集合(只提取ID 和 Name)
	*
	* @param taskConfigName 名称
	*/
	@GetMapping("/findTaskConfigsWithIdNameByName")
	public List<TaskConfig> findTaskConfigsWithIdNameByName(String taskConfigName){
		return this.taskConfigService.findTaskConfigsWithIdNameByName(taskConfigName);
	}
}

