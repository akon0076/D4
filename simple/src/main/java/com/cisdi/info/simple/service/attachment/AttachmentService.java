package com.cisdi.info.simple.service.attachment;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.attachment.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AttachmentService {

    public PageResultDTO findAttachments(PageDTO pageDTO);

    public List<Attachment> findAllAttachments();

    public List<Attachment> findAttachmentsWithIdNameByName(String attachmentName);

    public List<Attachment> findAllAttachmentsWithIdName();

    public Attachment findAttachmentsWithIdNameById(Long attachmentId);

    public Attachment findAttachment(Long attachmentId);

    //所有外键的Name都以加载
    public Attachment findAttachmentWithForeignName(Long attachmentId);

    public Attachment saveAttachment(Attachment attachment);

    public Attachment updateAttachment(Attachment attachment);

    public void deleteAttachment(Long attachmentId);

    public Long uploadMultipartFile(MultipartFile file, Attachment attachment ) throws IOException;

    public OutputStream downloadAttachment(String fileAddress, boolean isOnline) throws UnsupportedEncodingException;

    public List<Attachment> findAllUploadedFilesByIdAndName(String id,String name);

    public Integer deleteUploadedFile(String formId,String formName);

    public  void getZipFile(String[] fileAddress,String[] fileNames,String compressFileName)  throws IOException ;
}
