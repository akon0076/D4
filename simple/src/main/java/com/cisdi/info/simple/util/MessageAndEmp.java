package com.cisdi.info.simple.util;

import com.cisdi.info.simple.entity.organization.Employee;

/**
 * @ClassName MessageAndEmp
 * @Author 龚翔
 * @Date 2018-10-08 10:29
 **/
public class MessageAndEmp {
    private Long employeeId;//用户的Id

    private String message;//用户需接收的消息

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
