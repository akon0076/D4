package com.cisdi.info.simple.dto.attachment;


import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.organization.Employee;
public class AttachmentEditDto{

    private Attachment attachment;


    //外键实体是：Employee
    private List<Employee> uploadEmployeeEmployees;


    public  Attachment getAttachment()
    {
        return this.attachment;
    }
    public  void setAttachment(Attachment attachment)
    {
        this.attachment = attachment;
    }

    public List<Employee> getUploadEmployeeEmployees()
    {
        return this.uploadEmployeeEmployees;
    }
    public void setUploadEmployeeEmployees(List<Employee> uploadEmployeeEmployees)
    {
        this.uploadEmployeeEmployees = uploadEmployeeEmployees;
    }
}
