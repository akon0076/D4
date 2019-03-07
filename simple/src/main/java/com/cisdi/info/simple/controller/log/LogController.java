

package com.cisdi.info.simple.controller.log;
import com.cisdi.info.simple.service.system.CodeTableService;
import com.cisdi.info.simple.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.log.LogEditDto;
import com.cisdi.info.simple.entity.log.Log;
import com.cisdi.info.simple.service.log.LogService;
import com.cisdi.info.simple.service.permission.OperatorService;



/**module
{
"simple/log/Log": {
"code": "simple/log/Log",
"name1": "日志",
"url": "/simple/log/Log",
"route": "/simple/log/Log",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/log",
"parentName": "日志",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_log_Log_Add",
	"name1": "新增日志",
	"fullName": "simple.日志.日志.新增",
	"moduleCode": "simple/log/Log",
	urls:[
		"/simple/log/Log/createLog",
		"/simple/log/Log/saveLog"
			
		,"/simple/permission/Operator/findOperatorsWithIdNameByName"
	]
	},
	{
	"code": "simple_log_Log_Edit",
	"name1": "编辑日志",
	"fullName": "simple.日志.日志.编辑",
	"moduleCode": "simple/log/Log",
	urls:[
		"/simple/log/Log/findLogForEdit",
		"/simple/log/Log/updateLog"
		
		,"/simple/permission/Operator/findOperatorsWithIdNameByName"
	]
	},
	{
	"code": "simple_log_Log_Delete",
	"name1": "删除日志",
	"fullName": "simple.日志.日志.删除",
	"moduleCode": "simple/log/Log",
	urls:[
	"/simple/log/Log/deleteLog"
	]
	},
	{
	"code": "simple_log_Log_View",
	"name1": "查看日志",
	"fullName": "simple.日志.日志.查看",
	"moduleCode": "simple/log/Log",
	urls:[
	"/simple/log/Log/findLogs",
	"/simple/log/Log/findLogForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/log/Log")
@CrossOrigin(allowCredentials = "true")
public class LogController {
	private static Logger logger = LogManager.getLogger();



			
	@Autowired private LogService logService;
	@Autowired private OperatorService operatorService;

	/**
	* 根据分页参数查询日志集合
	*
	* @param pageDTO 分页条件
	*/
	@PostMapping("/findLogs")
	public PageResultDTO findLogs(@RequestBody PageDTO pageDTO){
		return this.logService.findLogs(pageDTO);
	}

	/**
	* 根据ID查询指定的日志
	*
	* @param logId Id
	*/
	@GetMapping("/findLog")
	public Log findLog(@RequestParam Long logId){
		return this.logService.findLog(logId);
	}

	/**
	* 根据ID查询指定的日志(包含外键名称)
	*
	* @param logId Id
	*/
	@GetMapping("/findLogForView")
	public Log findLogForView(@RequestParam Long logId){
		return this.logService.findLogWithForeignName(logId);
	}

	/**
	* 根据ID查询指定的日志(包含日志和外键名称)
	*
	* @param logId Id
	*/
	@GetMapping("/findLogForEdit")
	public LogEditDto findLogForEdit(@RequestParam Long logId){
		LogEditDto logEditDto = new LogEditDto();
		logEditDto.setLog(this.logService.findLogWithForeignName(logId));

		this.prepareLogEditDto(logEditDto);

		return logEditDto;
	}

	/**
	* 创建新的日志
	*
	*/
	@GetMapping("/createLog")
	public LogEditDto createLog(){
		LogEditDto logEditDto = new LogEditDto();
		logEditDto.setLog(new Log());

		this.prepareLogEditDto(logEditDto);
		return logEditDto;
	}

	private void prepareLogEditDto(LogEditDto logEditDto){
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		logEditDto.setOperatorOperators(this.operatorService.findAllOperatorsWithIdName());
	}

	/**
	* 更新日志
	*
	* @param log 实体对象
	*/
	@PostMapping("/saveLog")
	public Log saveLog(@RequestBody Log log){
		return this.logService.saveLog(log);
	}

	@PostMapping("/updateLog")
	public Log updateLog(@RequestBody Log log){
		return this.logService.updateLog(log);
	}

	/**
	* 根据ID删除日志
	*
	* @param logId ID
	*/
	@GetMapping("/deleteLog")
	public void deleteLog(@RequestParam Long logId){
		this.logService.deleteLog(logId);
	}

	/**
	* 根据ID查询指定的日志(只提取ID 和 Name)
	*
	* @param logId Id
	*/
	@GetMapping("/findLogsWithIdNameById")
	public Log findLogsWithIdNameById(@RequestParam Long logId){
		return this.logService.findLogsWithIdNameById(logId);
	}

	/**
	* 根据名称查询日志集合(只提取ID 和 Name)
	*
	* @param logName 名称
	*/
	@GetMapping("/findLogsWithIdNameByName")
	public List<Log> findLogsWithIdNameByName(String logName){
		return this.logService.findLogsWithIdNameByName(logName);
	}
}

