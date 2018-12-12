package com.cisdi.info.simple.dto.systemConfig;


import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.entity.systemConfig.SystemConfig;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class SystemConfigEditDto{

    private SystemConfig systemConfig;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    private   List<Attachment> attachments;


    public  SystemConfig getSystemConfig()
    {
        return this.systemConfig;
    }
    public  void setSystemConfig(SystemConfig systemConfig)
    {
        this.systemConfig = systemConfig;
    }

}
