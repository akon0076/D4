

package com.cisdi.info.simple.controller.task;
import com.cisdi.info.simple.entity.task.TaskConfig;
import com.cisdi.info.simple.service.system.CodeTableService;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.util.QuartzManager;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.task.TaskEditDto;
import com.cisdi.info.simple.entity.task.Task;
import com.cisdi.info.simple.service.task.TaskService;

import javax.validation.Valid;


/**module
{
"simple/task/Task": {
"code": "simple/task/Task",
"name1": "定时任务",
"url": "/simple/task/Task",
"route": "/simple/task/Task",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/task",
"parentName": "定时任务管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_task_Task_Add",
	"name1": "新增定时任务",
	"fullName": "simple.定时任务管理.定时任务.新增",
	"moduleCode": "simple/task/Task",
	urls:[
		"/simple/task/Task/createTask",
		"/simple/task/Task/saveTask"
	]
	},
	{
	"code": "simple_task_Task_Edit",
	"name1": "编辑定时任务",
	"fullName": "simple.定时任务管理.定时任务.编辑",
	"moduleCode": "simple/task/Task",
	urls:[
		"/simple/task/Task/findTaskForEdit",
		"/simple/task/Task/updateTask"
	]
	},
	{
	"code": "simple_task_Task_Delete",
	"name1": "删除定时任务",
	"fullName": "simple.定时任务管理.定时任务.删除",
	"moduleCode": "simple/task/Task",
	urls:[
	"/simple/task/Task/deleteTask"
	]
	},
	{
	"code": "simple_task_Task_View",
	"name1": "查看定时任务",
	"fullName": "simple.定时任务管理.定时任务.查看",
	"moduleCode": "simple/task/Task",
	urls:[
	"/simple/task/Task/findTasks",
	"/simple/task/Task/findTaskForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/task/Task")
@CrossOrigin(allowCredentials = "true")
public class TaskController {
	private static Logger logger = LogManager.getLogger();


    @Autowired
    private QuartzManager quartzManager;

	@Autowired private TaskService taskService;

	/**
	* 根据分页参数查询定时任务集合
	*
	* @param pageDTO 分页条件
	*/
	@PostMapping("/findTasks")
	public PageResultDTO findTasks(@RequestBody PageDTO pageDTO){
		return this.taskService.findTasks(pageDTO);
	}

	/**
	* 根据ID查询指定的定时任务
	*
	* @param taskId Id
	*/
	@GetMapping("/findTask")
	public Task findTask(@RequestParam Long taskId){
		return this.taskService.findTask(taskId);
	}

	/**
	* 根据ID查询指定的定时任务(包含外键名称)
	*
	* @param taskId Id
	*/
	@GetMapping("/findTaskForView")
	public Task findTaskForView(@RequestParam Long taskId){
		return this.taskService.findTaskWithForeignName(taskId);
	}

	/**
	* 根据ID查询指定的定时任务(包含定时任务和外键名称)
	*
	* @param taskId Id
	*/
	@GetMapping("/findTaskForEdit")
	public TaskEditDto findTaskForEdit(@RequestParam Long taskId){
		TaskEditDto taskEditDto = new TaskEditDto();
		taskEditDto.setTask(this.taskService.findTaskWithForeignName(taskId));

		this.prepareTaskEditDto(taskEditDto);

		return taskEditDto;
	}

	/**
	* 创建新的定时任务
	*
	*/
	@GetMapping("/createTask")
	public TaskEditDto createTask(){
		TaskEditDto taskEditDto = new TaskEditDto();
		taskEditDto.setTask(new Task());

		this.prepareTaskEditDto(taskEditDto);
		return taskEditDto;
	}

	private void prepareTaskEditDto(TaskEditDto taskEditDto){
	}

	/**
	* 更新定时任务
	*
	* @param task 实体对象
	*/
	@PostMapping("/saveTask")
	public Task saveTask(@RequestBody Task task){
		return this.taskService.saveTask(task);
	}

	@PostMapping("/updateTask")
	public Task updateTask(@RequestBody Task task){
		return this.taskService.updateTask(task);
	}

	/**
	* 根据ID删除定时任务
	*
	* @param taskId ID
	*/
	@GetMapping("/deleteTask")
	public void deleteTask(@RequestParam Long taskId){
		this.taskService.deleteTask(taskId);
	}

	/**
	* 根据ID查询指定的定时任务(只提取ID 和 Name)
	*
	* @param taskId Id
	*/
	@GetMapping("/findTasksWithIdNameById")
	public Task findTasksWithIdNameById(@RequestParam Long taskId){
		return this.taskService.findTasksWithIdNameById(taskId);
	}

	/**
	* 根据名称查询定时任务集合(只提取ID 和 Name)
	*
	* @param taskName 名称
	*/
	@GetMapping("/findTasksWithIdNameByName")
	public List<Task> findTasksWithIdNameByName(String taskName){
		return this.taskService.findTasksWithIdNameByName(taskName);
	}

	/**
	 * 启动定时任务
	 * @param id 任务ID
	 * @throws SchedulerException 错误需要捕获的异常
	 */
	@RequestMapping(value = "/startSchedulerJob", method = RequestMethod.GET)
	public void startSchedulerJob(@Valid @RequestParam("id") Integer id) throws SchedulerException {
		this.quartzManager.start(id);
	}

	/**
	 * 暂停某个定时任务
	 * @param id 任务ID
	 * @throws SchedulerException 错误需要捕获的异常
	 */
	@RequestMapping(value = "/pauseSchedulerJob", method = RequestMethod.GET)
	public void pauseSchedulerJob(@Valid @RequestParam("id") Integer id) throws SchedulerException {
		this.quartzManager.pauseSchedulerJob(id);
	}

	/**
	 * 恢复指定定时任务
	 * @param id 任务ID
	 * @throws SchedulerException 错误需要捕获的异常
	 */
	@RequestMapping(value = "/resumeSchedulerJob", method = RequestMethod.GET)
	public void resumeSchedulerJob(@Valid @RequestParam("id") Integer id) throws SchedulerException {
		this.quartzManager.resumeSchedulerJob(id);
	}

	/**
	 * 获取指定任务信息
	 * @param id 任务ID
	 * @return 任务信息
	 * @throws SchedulerException 错误需要捕获的异常
	 */
	@ResponseBody
	@RequestMapping(value = "/getJobInfo", method = RequestMethod.GET)
	public String getJobInfo(@Valid @RequestParam("id") Integer id) throws SchedulerException, JSONException {
		String data = this.quartzManager.getJobInfo(id);
		return data;
	}
	/**
	 * 删除指定任务
	 * @param id 任务ID
	 * @return 删除
	 * @throws SchedulerException 错误需要捕获的异常
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteJob", method = RequestMethod.GET)
	public void deleteJob(@Valid @RequestParam("id") Integer id) throws SchedulerException {
		this.quartzManager.deleteJob(id);
	}

    /**
     * 修改定时任务
     * @param id 任务ID
     * @param time 修改时间
     * @return 是否修改成功
     * @throws SchedulerException 错误需要捕获的异常
     */
    @ResponseBody
    @RequestMapping(value = "/modifyJob", method = RequestMethod.GET)
    public String modifyJob(@Valid @RequestParam("id") Integer id, @Valid @RequestParam("time") Date time) throws SchedulerException, JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        String formatStr =formatter.format(time);
        String message = "";
        if (this.quartzManager.modifyJob(id, formatStr)) {
            message = "更新成功";
        } else {
            message = "更新失败";
        }
        return message;
    }

    /**
     * 获取定时任务配置列表
     */
    @ResponseBody
    @RequestMapping(value = "/getTaskConfigList", method = RequestMethod.GET)
    public List<TaskConfig> getTaskConfigList() throws JSONException {
        List<TaskConfig> data = this.taskService.getTaskConfigList();
        return data;
    }
}

