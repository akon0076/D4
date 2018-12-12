package com.cisdi.info.simple.dao.attachment;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.attachment.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "attachmentDao")
public interface AttachmentDao {

    public List<Attachment> findAttachments(PageDTO pageDTO);

    public List<Attachment> findAllAttachments();

    public List<Attachment> findAllAttachmentsWithIdName();

    public List<Attachment> findAttachmentsWithIdNameByName(@Param("attachmentName") String attachmentName);

    public Attachment findAttachmentsWithIdNameById(@Param(" attachmentId") Long attachmentId);

    public Long findAttachmentTotalCount(PageDTO pageDTO);

    public Attachment findAttachment(@Param("attachmentId") Long attachmentId);

    //所有外键的Name都以加载
    public Attachment findAttachmentWithForeignName(@Param("attachmentId") Long attachmentId);

    public Integer saveAttachment(Attachment attachment);

    public Integer updateAttachment(Attachment attachment);

    public Integer deleteAttachment(@Param("attachmentId") Long attachmentId);

    public Integer deleteUploadedFile(@Param("formId")  String formId,@Param("formName")  String formName);

    public String getFileRealName(String fileAddress);

    public List<Attachment> findAllUploadedFilesByIdAndName(Attachment attachment);

    public Long findLicenseByIdAndName(Attachment attachment);

}
