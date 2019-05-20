package com.cisdi.info.simple.service.attachment.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.config.Config;
import com.cisdi.info.simple.dao.attachment.AttachmentDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.service.attachment.AttachmentService;
import com.cisdi.info.simple.service.base.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional
public class AttachmentServiceBean extends BaseService implements AttachmentService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Override
    public PageResultDTO findAttachments(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<Attachment> attachmentDTOS = this.attachmentDao.findAttachments(pageDTO);
        Long totalCount = this.attachmentDao.findAttachmentTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(attachmentDTOS);

        return pageResultDTO;
    }

    @Override
    public List<Attachment> findAllAttachments() {
        return this.attachmentDao.findAllAttachments();
    }

    @Override
    public List<Attachment> findAllAttachmentsWithIdName() {
        return this.attachmentDao.findAllAttachmentsWithIdName();
    }

    @Override
    public List<Attachment> findAttachmentsWithIdNameByName(String attachmentName) {
        return this.attachmentDao.findAttachmentsWithIdNameByName(attachmentName);
    }

    @Override
    public Attachment findAttachmentsWithIdNameById(Long attachmentId) {
        return this.attachmentDao.findAttachmentsWithIdNameById(attachmentId);
    }

    @Override
    public Attachment findAttachment(Long attachmentId) {
        return this.attachmentDao.findAttachment(attachmentId);
    }

    //所有外键的Name都以加载
    @Override
    public Attachment findAttachmentWithForeignName(Long attachmentId) {
        return this.attachmentDao.findAttachmentWithForeignName(attachmentId);
    }

    @Override
    public Attachment saveAttachment(Attachment attachment) {
        //TODO:请在此校验参数的合法性
        this.setSavePulicColumns(attachment);
        Integer rows = this.attachmentDao.saveAttachment(attachment);
        if (rows != 1) {
            String error = "新增保存附件出错，数据库应该返回1,但返回了 " + rows;
            throw new DDDException(error);
        }
        return attachment;
    }

    public Attachment updateAttachment(Attachment attachment) {
        //TODO:请在此校验参数的合法性
        this.setUpdatePulicColumns(attachment);
        Integer rows = this.attachmentDao.updateAttachment(attachment);
        if (rows != 1) {
            String error = "修改保存附件出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new DDDException(error);
        }
        return attachment;
    }

    @Override
    public void deleteAttachment(Long attachmentId) {
        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Attachment.class, attachmentId);
        if (entityUsageMap != null && entityUsageMap.size() > 0) {
            StringBuilder errors = new StringBuilder();
            errors.append("计划删除的数据正在被以下数引用\n");
            for (EntityUsage entityUsage : entityUsageMap.values()) {
                errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
                for (Map.Entry<Long, String> entry : entityUsage.getUsageIdNames().entrySet()) {
                    errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
                }
            }
            errors.append("，不能删除，请检查处理后再删除");
            throw new DDDException(errors.toString());
        }

        Integer rows = this.attachmentDao.deleteAttachment(attachmentId);
        if (rows != 1) {
            String error = "删除附件出错，数据库应该返回1,但返回了 " + rows + ",数据可能已经被删除";
            throw new DDDException(error);
        }
    }

    @Override
    public Long uploadMultipartFile(MultipartFile file, Attachment attachment) {
        if (file == null) {
            throw new DDDException("必须选择一个文件");
        }
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String ymd = dateFormat.format(date);
        String fileDirectory = Config.uploadFileAddress + "/" + ymd;
        if (!fileIsExists(fileDirectory)) {
            File f = new File(fileDirectory);
            if (!f.mkdirs()) {
                throw new DDDException("文件夹创建失败,请检查配置路径是否正确");
            }
        }

        if (attachment.getAssociateFormId() == "" || attachment.getAssociateFormId() == null || attachment.getAssociateFormName() == ""
                || attachment.getAssociateFormName() == null) {
            throw new DDDException("关联表单Id和关联表单名称不能为空");
        }

        String[] fileInfo = getFileInfo(file);
        String toPrefix = fileInfo[0] + uniqueIdentifier();
        String toSuffix = fileInfo[1];
        String logicalFileName = toPrefix + toSuffix;
        if (!attachment.getAssociateFormName().equals("simple_organizationRegit")) {
            this.setSavePulicColumns(attachment);
        } else {
            attachment.setCreateDatetime(new Date());
        }
        attachment.setUploadEmployeeId(attachment.getCreateId());
        attachment.setUploadTime(attachment.getCreateDatetime());
        attachment.setAssociateSize(file.getSize());
        attachment.setAttachmentRealName(file.getOriginalFilename());
        attachment.setAttachmentLogicalName(logicalFileName);
        attachment.setAttachmentAddr("/" + ymd + "/" + logicalFileName);
        if (attachment.getIsEffective() == null) {
            attachment.setIsEffective(true);
        }
        try {
            file.transferTo(new File(Config.uploadFileAddress + "/" + ymd + "/" + logicalFileName));
        } catch (IOException e) {
            throw new DDDException("请检查磁盘大小是否充足或文件夹是否存在");
        }
        Integer rows;
        // 判断文件是否更新
        Long eid = isUpdate(attachment);
        if (eid != null) {
            attachment.setEId(eid);
            rows = this.attachmentDao.updateAttachment(attachment);
        } else {
            rows = this.attachmentDao.saveAttachment(attachment);
        }

        if (rows != 1) {
            String error = "新增保存附件出错，数据库应该返回1,但返回了 " + rows;
            throw new DDDException(error);
        }
        return attachment.getEId();
    }

    /**
     * 判断文件是否更新
     * @param attachment
     * @return
     */
    private Long isUpdate(Attachment attachment) {
        Long result = this.attachmentDao.findLicenseByIdAndName(attachment);
        return result;
    }

    public static String[] getFileInfo(MultipartFile from) {
        String fileName = from.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String toPrefix = "";
        String toSuffix = "";
        if (index == -1) {
            toPrefix = fileName;
        } else {
            //去除所有特殊字符
            toPrefix = fileName.substring(0, index).replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", "");
            toSuffix = fileName.substring(index, fileName.length());
        }
        return new String[]{toPrefix, toSuffix};
    }

    public static String generateSuffix() {
        // 获得当前时间
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        // 转换为字符串
        String formatDate = format.format(new Date());
        // 随机生成文件编号
        int random = new Random().nextInt(10000);
        return new StringBuffer().append(formatDate).append(
                random).toString();
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public OutputStream downloadAttachment(String fileAddress, boolean isOnline) throws UnsupportedEncodingException {
        if (fileAddress == null || fileAddress.equals("")) {
            throw new DDDException("文件地址不能为空或请求地址过长");
        }
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");

        String fileName = fileAddress;
        String path = Config.uploadFileAddress;
        String agent = request.getHeader("User-Agent");
        try {
            // 打开文件
            // 获取到要下载文件的全路径
            // 得到要下载的文件名，小伙伴可以根据自己的实际文件名更改，这里是博主自己定义的文件名
            String destinationfileName = fileName;
            // 得到要下载的文件的所在目录，可以根据自己项目更改内容
            String uploadpath = path;
            // 得到要下载的文件
            File file = new File(uploadpath + destinationfileName);

            //如果文件不存在，则显示下载失败
            if (!file.exists()) {
                throw new DDDException("该文件不存在,下载失败");
            } else {
                String fileRealName = attachmentDao.getFileRealName(fileAddress);//从数据库中得到文件的真实名称
                //isOnline表示附件是否支持在线查看,附件仅支持图片
                if (isOnline) {
                    BufferedInputStream br = new BufferedInputStream(new FileInputStream(uploadpath + "\\" + destinationfileName));
                    byte[] buf = new byte[1024];
                    int len = 0;
                    URL u = new URL("file:///" + file.getPath());
                    response.setContentType(u.openConnection().getContentType());
                    if (agent.contains("Firefox")) {

                        response.setHeader("Content-Disposition", "inline; filename=" + new String(fileRealName.getBytes("UTF-8"), "iso-8859-1"));
                    } else {
                        response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileRealName, "utf-8"));
                    }
                    OutputStream out = response.getOutputStream();
                    while ((len = br.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    br.close();
                    out.close();
                    return out;
                } else {
                    // 设置相应头，控制浏览器下载该文件，这里就是会出现当你点击下载后，出现的下载地址框
                    if (agent.contains("Firefox")) {
                        response.setHeader("content-disposition",
                                "attachment;filename=" + new String(fileRealName.getBytes("UTF-8"), "iso-8859-1"));
                    } else {
                        response.setHeader("content-disposition",
                                "attachment;filename=" + URLEncoder.encode(fileRealName, "utf-8"));
                    }
                    // 读取要下载的文件，保存到文件输入流
                    FileInputStream in = new FileInputStream(uploadpath + "\\" + destinationfileName);
                    // 创建输出流
                    OutputStream out = response.getOutputStream();
                    // 创建缓冲区
                    byte buffer[] = new byte[1024];
                    int len = 0;
                    // 循环将输入流中的内容读取到缓冲区中
                    while ((len = in.read(buffer)) > 0) {
                        // 输出缓冲区内容到浏览器，实现文件下载
                        out.write(buffer, 0, len);
                    }
                    // 关闭文件流
                    in.close();
                    // 关闭输出流
                    out.close();
                    return out;
				}
			}
		} catch (Exception e) {
			throw new DDDException("下载附件出错,请确认是否成功上传该附件");
		}

	}


	public OutputStream downloadAttachment(String fileAddress,String compressFileName) throws UnsupportedEncodingException {
		if(fileAddress==null||fileAddress.equals("")){
			throw new DDDException("文件地址不能为空或请求地址过长");
		}
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("utf-8");

		String fileName=fileAddress;
		String path=Config.uploadFileAddress;
		String agent=request.getHeader("User-Agent");
		try {
			// 打开文件
			// 获取到要下载文件的全路径
			// 得到要下载的文件名，小伙伴可以根据自己的实际文件名更改，这里是博主自己定义的文件名
			String destinationfileName = fileName;
			// 得到要下载的文件的所在目录，可以根据自己项目更改内容
			String uploadpath = path;
			// 得到要下载的文件
			File file = new File(uploadpath + destinationfileName);

			//如果文件不存在，则显示下载失败
			if (!file.exists()) {
				throw new DDDException("该文件不存在,下载失败");
			} else {
				//isOnline表示附件是否支持在线查看,附件仅支持图片
					// 设置相应头，控制浏览器下载该文件，这里就是会出现当你点击下载后，出现的下载地址框
					if(agent!=null&&agent.contains("Firefox")){
						response.setHeader("content-disposition",
								"attachment;filename=" +new String(compressFileName.getBytes("UTF-8"),"iso-8859-1"));
					}
					else
					{
						response.setHeader("content-disposition",
								"attachment;filename=" + URLEncoder.encode(compressFileName, "utf-8"));
					}
					// 读取要下载的文件，保存到文件输入流
					FileInputStream in = new FileInputStream(uploadpath + "\\" + destinationfileName);
					// 创建输出流
					OutputStream out = response.getOutputStream();
					// 创建缓冲区
					byte buffer[] = new byte[1024];
					int len = 0;
					// 循环将输入流中的内容读取到缓冲区中
					while ((len = in.read(buffer)) > 0) {
						// 输出缓冲区内容到浏览器，实现文件下载
						out.write(buffer, 0, len);
					}
					// 关闭文件流
					in.close();
					// 关闭输出流
					out.close();
					return out;


			}
		} catch (Exception e) {
			throw new DDDException("下载附件出错,请确认是否成功上传该附件");
		}

	}
	/*
	 * @parms fileAddress 数据库的 attachment_addr字段数组(必填)
	 * @parms fileNames 数据库的attachment_real_name 字段数组(必填)
	 * @parms compressFileName要下载的压缩文件名，如果不填默认为当前时间戳 如(压缩文件.zip)
	 */
	@Override
    public  void getZipFile(String[] fileAddress, String[] fileNames, String compressFileName)  throws IOException {
		if(fileAddress!=null&&fileAddress.length>0){

			if(compressFileName==null||compressFileName.equals("")||!compressFileName.contains(".zip")){
				compressFileName=System.currentTimeMillis()+""+(int)(Math.random()*10000)+".zip";
			}
			File zipFile=new File(Config.uploadFileAddress+"\\"+compressFileName);
			//实例化 ZipOutputStream对象
			ZipOutputStream zipOutputStream=new ZipOutputStream(new FileOutputStream(zipFile));
			//创建ZipEntry对象
			ZipEntry zipEntry=null;
			FileInputStream fileInputStream=null;
			File[]  files=new File[fileAddress.length];
			for(int i=0;i<fileAddress.length;i++){
				String file=Config.uploadFileAddress+fileAddress[i];
				files[i] =new File(file);
				if(!files[i].exists()){
					throw new DDDException(fileNames[i]+"文件不存在请重新上传");
				}
				fileInputStream=new FileInputStream(files[i]);
				zipEntry=new ZipEntry(fileNames[i]);
				zipOutputStream.putNextEntry(zipEntry);
				int len;
				byte[] buffer=new byte[1024];
				while ((len=fileInputStream.read(buffer))>0){
					zipOutputStream.write(buffer,0,len);
				}
                zipOutputStream.closeEntry();
			}
			try{
				zipOutputStream.close();
				fileInputStream.close();
				this.downloadAttachment("/"+zipFile.getName(),compressFileName);
				zipFile.delete();
			}
			catch (Exception e){
				zipFile.delete();
				throw new DDDException(e.getMessage());
			}

		}
		else{
			throw new DDDException("传入文件地址不能为空");
		}

	}
    //生成唯一编码
    public String uniqueIdentifier(){
        String id= UUID.randomUUID().toString();
        id=id.replace("-", "");
        return id;
    }
    @Override
    public List<Attachment> findAllUploadedFilesByIdAndName(String  id, String name){
		Attachment attachment = new Attachment();
		attachment.setAssociateFormId(id);
		attachment.setAssociateFormName(name);
		return  attachmentDao.findAllUploadedFilesByIdAndName(attachment);
	}

	@Override
	public Integer deleteUploadedFile(String formId, String formName) {
		Integer rows = this.attachmentDao.deleteUploadedFile(formId,formName);
		return rows;
	}
}
