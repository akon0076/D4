package com.cisdi.info.simple.service.regist;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.regist.EmployeRegist;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EmployeRegistService {

    public PageResultDTO findEmployeRegists(PageDTO pageDTO);

    public List<EmployeRegist> findAllEmployeRegists();

    public List<EmployeRegist> findEmployeRegistsWithIdNameByName(String employeRegistName);

    public List<EmployeRegist> findAllEmployeRegistsWithIdName();

    public EmployeRegist findEmployeRegistsWithIdNameById(Long employeRegistId);

    public EmployeRegist findEmployeRegist(Long employeRegistId);

    //所有外键的Name都以加载
    public EmployeRegist findEmployeRegistWithForeignName(Long employeRegistId);

    public EmployeRegist saveEmployeRegist(EmployeRegist employeRegist);

    public EmployeRegist updateEmployeRegist(HttpServletRequest request, EmployeRegist employeRegist);

    public void deleteEmployeRegist(Long employeRegistId);
}
