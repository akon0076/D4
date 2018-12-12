package com.cisdi.info.simple.dao.regist;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.regist.EmployeRegist;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "employeRegistDao")
public interface EmployeRegistDao {

    public List<EmployeRegist> findEmployeRegists(PageDTO pageDTO);

    public List<EmployeRegist> findAllEmployeRegists();

    public List<EmployeRegist> findAllEmployeRegistsWithIdName();

    public List<EmployeRegist> findEmployeRegistsWithIdNameByName(@Param("employeRegistName") String employeRegistName);

    public EmployeRegist findEmployeRegistsWithIdNameById(@Param(" employeRegistId") Long employeRegistId);

    public Long findEmployeRegistTotalCount(PageDTO pageDTO);

    public EmployeRegist findEmployeRegist(@Param("employeRegistId") Long employeRegistId);

    //所有外键的Name都以加载
    public EmployeRegist findEmployeRegistWithForeignName(@Param("employeRegistId") Long employeRegistId);

    public Integer saveEmployeRegist(EmployeRegist employeRegist);

    public Integer updateEmployeRegist(EmployeRegist employeRegist);

    public Integer deleteEmployeRegist(@Param("employeRegistId") Long employeRegistId);
}
