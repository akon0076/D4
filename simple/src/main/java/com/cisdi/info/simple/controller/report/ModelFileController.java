

package com.cisdi.info.simple.controller.report;

import com.cisdi.info.simple.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.report.ModelFileEditDto;
import com.cisdi.info.simple.entity.report.ModelFile;
import com.cisdi.info.simple.service.report.ModelFileService;



/**module
{
"simple/report/ModelFile": {
"code": "simple/report/ModelFile",
"name1": "模板文件管理",
"url": "/simple/report/ModelFile",
"route": "/simple/report/ModelFile",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/report",
"parentName": "报表管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_report_ModelFile_Add",
	"name1": "新增",
	"fullName": "simple.报表管理.模板文件管理.新增",
	"moduleCode": "simple/report/ModelFile",
	urls:[
		"/simple/report/ModelFile/createModelFile",
		"/simple/report/ModelFile/saveModelFile"
	]
	},
	{
	"code": "simple_report_ModelFile_Edit",
	"name1": "编辑",
	"fullName": "simple.报表管理.模板文件管理.编辑",
	"moduleCode": "simple/report/ModelFile",
	urls:[
		"/simple/report/ModelFile/findModelFileForEdit",
		"/simple/report/ModelFile/updateModelFile"
	]
	},
	{
	"code": "simple_report_ModelFile_Delete",
	"name1": "删除",
	"fullName": "simple.报表管理.模板文件管理.删除",
	"moduleCode": "simple/report/ModelFile",
	urls:[
	"/simple/report/ModelFile/deleteModelFile"
	]
	},
	{
	"code": "simple_report_ModelFile_View",
	"name1": "查看",
	"fullName": "simple.报表管理.模板文件管理.查看",
	"moduleCode": "simple/report/ModelFile",
	urls:[
	"/simple/report/ModelFile/findModelFiles",
	"/simple/report/ModelFile/findModelFileForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/report/ModelFile")
@CrossOrigin(allowCredentials = "true")
public class ModelFileController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private ModelFileService modelFileService;

	@PostMapping("/findModelFiles")
	public PageResultDTO findModelFiles(@RequestBody PageDTO pageDTO){
		return this.modelFileService.findModelFiles(pageDTO);
	}

	@GetMapping("/findModelFile")
	public ModelFile findModelFile(@RequestParam Long modelFileId)
	{
		return this.modelFileService.findModelFile(modelFileId);
	}

	@GetMapping("/findModelFileForView")
	public ModelFile findModelFileForView(@RequestParam Long modelFileId)
	{
		return this.modelFileService.findModelFileWithForeignName(modelFileId);
	}

	@GetMapping("/findModelFileForEdit")
	public ModelFileEditDto findModelFileForEdit(@RequestParam Long modelFileId)
	{
		ModelFileEditDto modelFileEditDto = new ModelFileEditDto();
		modelFileEditDto.setModelFile(this.modelFileService.findModelFileWithForeignName(modelFileId));

		this.prepareModelFileEditDto(modelFileEditDto);

		return modelFileEditDto;
	}

	//创建新的模板文件管理
	@GetMapping("/createModelFile")
	public ModelFileEditDto createModelFile()
	{
		ModelFileEditDto modelFileEditDto = new ModelFileEditDto();
		modelFileEditDto.setModelFile(new ModelFile());

		this.prepareModelFileEditDto(modelFileEditDto);
		return modelFileEditDto;
	}

	private void prepareModelFileEditDto(ModelFileEditDto modelFileEditDto)
	{
	}

	@PostMapping("/saveModelFile")
	public ModelFile saveModelFile(@RequestBody ModelFile modelFile)
	{
		return this.modelFileService.saveModelFile(modelFile);
	}

	@PostMapping("/updateModelFile")
	public ModelFile updateModelFile(@RequestBody ModelFile modelFile)
	{
		return this.modelFileService.updateModelFile(modelFile);
	}

	@GetMapping("/deleteModelFile")
	public void deleteModelFile(@RequestParam Long modelFileId)
	{
		this.modelFileService.deleteModelFile(modelFileId);
	}
	@GetMapping("/findModelFilesWithIdNameById")
	public ModelFile findModelFilesWithIdNameById(@RequestParam Long modelFileId)
	{
		return this.modelFileService.findModelFilesWithIdNameById(modelFileId);
	}

	@GetMapping("/findModelFilesWithIdNameByName")
	public List<ModelFile> findModelFilesWithIdNameByName(String modelFileName)
	{
		return this.modelFileService.findModelFilesWithIdNameByName(modelFileName);
	}
}

