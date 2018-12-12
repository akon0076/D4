

package com.cisdi.info.simple.controller.attachment;

import com.cisdi.info.simple.dto.attachment.AttachmentEditDto;
import com.cisdi.info.simple.dto.attachment.FileUploadDto;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.service.attachment.AttachmentService;
import com.cisdi.info.simple.service.organization.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 *Created by 向鑫 on 2018年8月22日22:00:11
 */

@RestController
@RequestMapping("/simple/attachment/Attachment")
@CrossOrigin(allowCredentials = "true")
public class AttachmentController {
	private static Logger logger = LogManager.getLogger();

			//得到
	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/findAttachments")
	public PageResultDTO findAttachments(@RequestBody PageDTO pageDTO){
		return this.attachmentService.findAttachments(pageDTO);
	}

	@GetMapping("/findAttachment")
	public Attachment findAttachment(@RequestParam Long attachmentId)
	{
		return this.attachmentService.findAttachment(attachmentId);
	}

	@GetMapping("/findAttachmentForView")
	public Attachment findAttachmentForView(@RequestParam Long attachmentId)
	{
		return this.attachmentService.findAttachmentWithForeignName(attachmentId);
	}

	@GetMapping("/findAttachmentForEdit")
	public AttachmentEditDto findAttachmentForEdit(@RequestParam Long attachmentId)
	{
		AttachmentEditDto attachmentEditDto = new AttachmentEditDto();
		attachmentEditDto.setAttachment(this.attachmentService.findAttachmentWithForeignName(attachmentId));

		this.prepareAttachmentEditDto(attachmentEditDto);

		return attachmentEditDto;
	}

	//创建新的附件
	@GetMapping("/createAttachment")
	public AttachmentEditDto createAttachment()
	{
		AttachmentEditDto attachmentEditDto = new AttachmentEditDto();
		attachmentEditDto.setAttachment(new Attachment());

		this.prepareAttachmentEditDto(attachmentEditDto);
		return attachmentEditDto;
	}

	private void prepareAttachmentEditDto(AttachmentEditDto attachmentEditDto)
	{
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		attachmentEditDto.setUploadEmployeeEmployees(this.employeeService.findAllEmployeesWithIdName());
	}

	/*文件上传*/
	@PostMapping("/saveAttachment")
	public Attachment saveAttachment(FileUploadDto fileUploadDto, List<MultipartFile> files) throws IOException {
		Attachment a=new Attachment();
		a.setAssociateFormId(fileUploadDto.getAssociateFormId());//行id
		a.setAssociateFormName(fileUploadDto.getAssociateFormName());//表名
		for(int i=0;i<files.size();i++) {
		    Long id=attachmentService.uploadMultipartFile(files.get(i), a);
		}
		return null;
	}

	@PostMapping("/updateAttachment")
	public Attachment updateAttachment(@RequestBody Attachment attachment)
	{
		return this.attachmentService.updateAttachment(attachment);
	}

	@GetMapping("/deleteAttachment")
	public void deleteAttachment(@RequestParam Long attachmentId)
	{
		this.attachmentService.deleteAttachment(attachmentId);
	}
	@GetMapping("/findAttachmentsWithIdNameById")
	public Attachment findAttachmentsWithIdNameById(@RequestParam Long attachmentId)
	{
		return this.attachmentService.findAttachmentsWithIdNameById(attachmentId);
	}

	@GetMapping("/findAttachmentsWithIdNameByName")
	public List<Attachment> findAttachmentsWithIdNameByName(String attachmentName)
	{
		return this.attachmentService.findAttachmentsWithIdNameByName(attachmentName);
	}



/*文件下载*/
//String path="D:\\spring_files\\20180822\\1529499953475_ec-mh_dev201808221703475198.sql";
	@GetMapping("/download")
	public void downLoad(boolean isOnline,String fileAddress) throws Exception {
		attachmentService.downloadAttachment(fileAddress,isOnline);
	}

	/*通过id和name找到所有的附件*/
	@GetMapping("/findAllUploadedFilesByIdAndName")
	public  List<Attachment>  findAllUploadedFilesByIdAndName(String id,String name){
		return attachmentService.findAllUploadedFilesByIdAndName(id,name);
	}

	/*通过表单id和name删除附件*/
	@GetMapping("deleteUploadedFilesByIdAndName")
	public Integer deleteUploadedFile(String formId,String formName){
		return attachmentService.deleteUploadedFile(formId,formName);
	}
}

