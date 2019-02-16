package com.cisdi.info.simple.service.organization.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.organization.EmployeeDao;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.organization.EmployeeService;
import com.cisdi.info.simple.service.permission.OperatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EmployeeServiceBean extends BaseService implements EmployeeService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private OperatorDao operatorDao;

    public PageResultDTO findEmployees(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<Employee> employeeDTOS = this.employeeDao.findEmployees(pageDTO);
        Long totalCount = this.employeeDao.findEmployeeTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(employeeDTOS);

        return pageResultDTO;
    }

    public List<Employee> findAllEmployees() {
        return this.employeeDao.findAllEmployees();
    }

    public List<Employee> findAllEmployeesWithIdName() {
        return this.employeeDao.findAllEmployeesWithIdName();
    }

    public List<Employee> findEmployeesWithIdNameByName(String employeeName) {
        return this.employeeDao.findEmployeesWithIdNameByName(employeeName);
    }

    public Employee findEmployeesWithIdNameById(Long employeeId) {
        return this.employeeDao.findEmployeesWithIdNameById(employeeId);
    }

    public Employee findEmployee(Long employeeId) {
        return this.employeeDao.findEmployee(employeeId);
    }

    //所有外键的Name都以加载
    public Employee findEmployeeWithForeignName(Long employeeId) {
        return this.employeeDao.findEmployeeWithForeignName(employeeId);
    }

    /**
     * 新增操作员
     * @param employee
     * @return
     */
    public Employee saveEmployee(Employee employee) {
        //TODO:请在此校验参数的合法性
        this.setSavePulicColumns(employee);
        Integer rows = this.employeeDao.saveEmployee(employee);
        if (rows != 1) {
            throw new DDDException("新增保存职员出错");
        }
        Operator operatorByCode = operatorDao.findOperatorByCode(employee.getCode());
        //如果对于的操作员不存在，则默认自动增加
        if (operatorByCode == null) {
            Operator operator = new Operator();
            operator.setCode(employee.getCode());
            //对密码进行加密
            String password = DigestUtils.md5DigestAsHex(employee.getPassWord().getBytes());
            operator.setPassWord(password);
            operator.setStatus("在用");
            operator.setType("职员");
            operator.setPersonId(employee.getEId());
            operator.setName(employee.getName());
            //设置公共自动
            this.setSavePulicColumns(operator);
            operatorService.saveOperator(operator);
        } else {
            operatorByCode.setPersonId(employee.getEId());
            operatorDao.updateOperator(operatorByCode);
        }
        return employee;
    }

    public Employee updateEmployee(Employee employee) {
        //TODO:请在此校验参数的合法性
        this.setUpdatePulicColumns(employee);
        Integer rows = this.employeeDao.updateEmployee(employee);
        if (rows != 1) {
            String error = "修改保存职员出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new DDDException(error);
        }
        return employee;
    }

    public void deleteEmployee(Long employeeId) {
        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Employee.class, employeeId);
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

        Integer rows = this.employeeDao.deleteEmployee(employeeId);
        if (rows != 1) {
            String error = "删除职员出错，数据库应该返回1,但返回了 " + rows + ",数据可能已经被删除";
            throw new DDDException(error);
        }
    }
}
