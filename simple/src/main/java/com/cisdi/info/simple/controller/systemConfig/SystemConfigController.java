

package com.cisdi.info.simple.controller.systemConfig;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.systemConfig.SystemConfigInputDTO;
import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.service.attachment.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.systemConfig.SystemConfigEditDto;
import com.cisdi.info.simple.entity.systemConfig.SystemConfig;
import com.cisdi.info.simple.service.systemConfig.SystemConfigService;
import org.springframework.web.multipart.MultipartFile;


/**module
{
"simple/systemConfig/SystemConfig": {
"code": "simple/systemConfig/SystemConfig",
"name1": "系统参数",
"url": "/simple/systemConfig/SystemConfig",
"route": "/simple/systemConfig/SystemConfig",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/systemConfig",
"parentName": "系统参数",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_systemConfig_SystemConfig_Add",
	"name1": "新增",
	"fullName": "simple.系统参数.系统参数.新增",
	"moduleCode": "simple/systemConfig/SystemConfig",
	urls:[
		"/simple/systemConfig/SystemConfig/createSystemConfig",
		"/simple/systemConfig/SystemConfig/saveSystemConfig"
	]
	},
	{
	"code": "simple_systemConfig_SystemConfig_Edit",
	"name1": "编辑",
	"fullName": "simple.系统参数.系统参数.编辑",
	"moduleCode": "simple/systemConfig/SystemConfig",
	urls:[
		"/simple/systemConfig/SystemConfig/findSystemConfigForEdit",
		"/simple/systemConfig/SystemConfig/updateSystemConfig"
	]
	},
	{
	"code": "simple_systemConfig_SystemConfig_Delete",
	"name1": "删除",
	"fullName": "simple.系统参数.系统参数.删除",
	"moduleCode": "simple/systemConfig/SystemConfig",
	urls:[
	"/simple/systemConfig/SystemConfig/deleteSystemConfig"
	]
	},
	{
	"code": "simple_systemConfig_SystemConfig_View",
	"name1": "查看",
	"fullName": "simple.系统参数.系统参数.查看",
	"moduleCode": "simple/systemConfig/SystemConfig",
	urls:[
	"/simple/systemConfig/SystemConfig/findSystemConfigs",
	"/simple/systemConfig/SystemConfig/findSystemConfigForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/systemConfig/SystemConfig")
@CrossOrigin(allowCredentials = "true")
public class SystemConfigController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private SystemConfigService systemConfigService;
	@Autowired
	private AttachmentService attachmentService;

	@PostMapping("/findSystemConfigs")
	public PageResultDTO findSystemConfigs(@RequestBody PageDTO pageDTO){
		return this.systemConfigService.findSystemConfigs(pageDTO);
	}

	@GetMapping("/findSystemConfig")
	public SystemConfig findSystemConfig(@RequestParam Long systemConfigId)
	{
		return this.systemConfigService.findSystemConfig(systemConfigId);
	}

	@GetMapping("/findSystemConfigForView")
	public SystemConfig findSystemConfigForView(@RequestParam Long systemConfigId)
	{
		return this.systemConfigService.findSystemConfigWithForeignName(systemConfigId);
	}

	@GetMapping("/findSystemConfigForEdit")
	public SystemConfigEditDto findSystemConfigForEdit(@RequestParam Long systemConfigId)
	{
		SystemConfigEditDto systemConfigEditDto = new SystemConfigEditDto();
		systemConfigEditDto.setSystemConfig(this.systemConfigService.findSystemConfigWithForeignName(systemConfigId));

        List<Attachment> attachments=  attachmentService.findAllUploadedFilesByIdAndName(systemConfigId+"","simple_system_config");
        systemConfigEditDto.setAttachments(attachments);
		this.prepareSystemConfigEditDto(systemConfigEditDto);

		return systemConfigEditDto;
	}

	//创建新的系统参数
	@GetMapping("/createSystemConfig")
	public SystemConfigEditDto createSystemConfig()
	{
		SystemConfigEditDto systemConfigEditDto = new SystemConfigEditDto();
		SystemConfig systemConfig=new SystemConfig();
		systemConfig.setRemark("");
		systemConfig.setSystemConfigDescription("");
		systemConfig.setSystemConfigValue("");
		systemConfig.setSystemConfigKey("");
		systemConfig.setName("");
		systemConfigEditDto.setSystemConfig(systemConfig);

		this.prepareSystemConfigEditDto(systemConfigEditDto);
		return systemConfigEditDto;
	}

	private void prepareSystemConfigEditDto(SystemConfigEditDto systemConfigEditDto)
	{
	}

	@PostMapping("/saveSystemConfig")
	public SystemConfig saveSystemConfig(SystemConfigInputDTO inputDTO, List<MultipartFile> files)
	{
		SystemConfig systemConfig=new SystemConfig();
		systemConfig.setName(inputDTO.getName());
		systemConfig.setSystemConfigKey(inputDTO.getSystemConfigKey());
		systemConfig.setSystemConfigValue(inputDTO.getSystemConfigValue());
		systemConfig.setSystemConfigDescription(inputDTO.getSystemConfigDescription());
		systemConfig.setRemark(inputDTO.getRemark());
		systemConfig=systemConfigService.saveSystemConfig(systemConfig);
		if(files!=null&&files.size()>0){
			Attachment attachment=new Attachment();
			attachment.setAssociateFormName("simple_system_config");
			attachment.setAssociateFormId(systemConfig.getEId()+"");
			for(int i=0;i< files.size();i++){
				try {
					attachmentService.uploadMultipartFile(files.get(i),attachment);
				} catch (IOException e) {
					throw new DDDException("保存附件出错");
				}
			}
		}

		return systemConfig;
	}

	@PostMapping("/updateSystemConfig")
	public SystemConfig updateSystemConfig( SystemConfigInputDTO inputDTO,List<MultipartFile> files)
	{

		if(inputDTO.getDeleteIds()!=null&&inputDTO.getDeleteIds().length>0){
			for(int i=0;i<inputDTO.getDeleteIds().length;i++){
				this.attachmentService.deleteAttachment(inputDTO.getDeleteIds()[i]);
			}
		}
		if(files!=null&&files.size()>0){
			Attachment attachment=new Attachment();
			attachment.setAssociateFormName("simple_system_config");
			attachment.setAssociateFormId(inputDTO.getEid()+"");
			for(int i=0;i< files.size();i++){
				try {
					attachmentService.uploadMultipartFile(files.get(i),attachment);
				} catch (IOException e) {
					throw new DDDException("保存附件出错");
				}
			}
		}
		SystemConfig systemConfig=new SystemConfig();
		systemConfig.setName(inputDTO.getName());
		systemConfig.setSystemConfigKey(inputDTO.getSystemConfigKey());
		systemConfig.setSystemConfigValue(inputDTO.getSystemConfigValue());
		systemConfig.setSystemConfigDescription(inputDTO.getSystemConfigDescription());
		systemConfig.setRemark(inputDTO.getRemark());
		systemConfig.setEId(Long.parseLong(inputDTO.getEid()));
        return this.systemConfigService.updateSystemConfig(systemConfig);
	}

	@GetMapping("/deleteSystemConfig")
	public void deleteSystemConfig(@RequestParam Long systemConfigId)
	{
		attachmentService.deleteUploadedFile(systemConfigId+"","simple_system_config");
		this.systemConfigService.deleteSystemConfig(systemConfigId);
	}
	@GetMapping("/findSystemConfigsWithIdNameById")
	public SystemConfig findSystemConfigsWithIdNameById(@RequestParam Long systemConfigId)
	{
		return this.systemConfigService.findSystemConfigsWithIdNameById(systemConfigId);
	}

	@GetMapping("/findSystemConfigsWithIdNameByName")
	public List<SystemConfig> findSystemConfigsWithIdNameByName(String systemConfigName)
	{
		return this.systemConfigService.findSystemConfigsWithIdNameByName(systemConfigName);
	}
}

