package com.cisdi.info.simple.service.permission.impl;


import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.util.CaptchaHelper;
import com.cisdi.info.simple.dao.member.MemberDao;
import com.cisdi.info.simple.dao.organization.EmployeeDao;
import com.cisdi.info.simple.dao.permission.OperatorAndRoleDao;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;
import com.cisdi.info.simple.entity.permission.Role;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.member.MemberService;
import com.cisdi.info.simple.service.organization.EmployeeService;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.service.permission.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class OperatorServiceBean extends BaseService implements OperatorService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OperatorDao operatorDao;

    @Autowired
    private OperatorAndRoleDao operatorAndRoleDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CaptchaHelper captchaHelper;

    public PageResultDTO findOperators(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<Operator> operatorDTOS = this.operatorDao.findOperators(pageDTO);
        Long totalCount = this.operatorDao.findOperatorTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(operatorDTOS);

        return pageResultDTO;
    }

    public List<Operator> findAllOperators() {
        return this.operatorDao.findAllOperators();
    }

    public List<Operator> findAllOperatorsWithIdName() {
        return this.operatorDao.findAllOperatorsWithIdName();
    }

    public Operator findOperator(Long operatorId) {
        return this.operatorDao.findOperator(operatorId);
    }

    //所有外键的Name都以加载
    public Operator findOperatorWithForeignName(Long operatorId) {
        return this.operatorDao.findOperatorWithForeignName(operatorId);
    }

    public Operator saveOperator(Operator operator) {
        this.setSavePulicColumns(operator);
//        String password = operator.getCode() + operator.getPassWord();
//        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
//        operator.setPassWord(md5Password);
        return this.operatorDao.saveOperator(operator);
    }

    public Operator updateOperator(Operator operator) {
        this.setUpdatePulicColumns(operator);
//        String password = operator.getCode() + operator.getPassWord();
//        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
//        operator.setPassWord(md5Password);
        return this.operatorDao.updateOperator(operator);
    }

    public void deleteOperator(Long operatorId) {
        this.operatorDao.deleteOperator(operatorId);
    }


    public Map<String, Object> checkOperatorByUserNameAndPassWord(LoginDTO loginDTO) {
        try {
            Map<String, Object> restult = new HashMap<String, Object>();
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            boolean captcha = this.captchaHelper.validate(request, loginDTO.getCaptcha());
            Operator operator = new Operator();
            if (captcha) {
                String[] username = loginDTO.getUserName().split("@");
                if(username.length > 1){//邮箱登录
                    operator = this.operatorDao.findOperatorByEmailAndPassWord(loginDTO);
                }
                else{//用户名登录
                    operator = this.operatorDao.findOperatorByUserNameAndPassWord(loginDTO);
                }
                restult.put("captcha", true);
                if (operator != null) {
                    restult.put("isLogin", true);
                    restult.put("loginUser", this.setLoginUser(operator));
                    restult.put("type", operator.getType());
                } else {
                    restult.put("isLogin", false);
                }

            } else {
                restult.put("captcha", false);
            }
            return restult;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, Object> mobileCheckOperatorByUserNameAndPassWord(LoginDTO loginDTO) {
        try {
            Map<String, Object> restult = new HashMap<String, Object>();
            Operator operator = new Operator();
            String[] username = loginDTO.getUserName().split("@");
            if(username.length > 1){//邮箱登录
                operator = this.operatorDao.findOperatorByEmailAndPassWord(loginDTO);
            }
            else{//用户名登录
                operator = this.operatorDao.findOperatorByUserNameAndPassWord(loginDTO);
            }
            if (operator != null) {
                restult.put("isLogin", true);
                restult.put("loginUser", this.setLoginUser(operator));
                restult.put("type",operator.getType());
            } else {
                restult.put("isLogin", false);
            }
            return restult;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Map<String, Object> checkMemberByUserNameAndPassWord(LoginDTO loginDTO) {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            boolean captcha = this.captchaHelper.validate(request, loginDTO.getCaptcha());
            if (captcha) {
            Operator operator = this.operatorDao.findOperatorByUserNameAndPassWordAndMember(loginDTO);
            result.put("captcha", true);
            if (operator != null) {
                result.put("isLogin", true);
                result.put("loginUser", this.setLoginMember(operator));
                result.put("type", operator.getType());
            } else {
                result.put("isLogin", false);
            }
               } else {
                result.put("captcha", false);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private LoginUser setLoginUser(Operator userOperator) throws Exception {
        try {
            Employee employee = this.employeeService.findEmployeeWithForeignName(userOperator.getPersonId());
            if (employee == null) {
                logger.error("没有找到职员信息");
                throw new DDDException("没有找到职员信息");
            }
            //这行代码可以和 ModuleService中的 constructNewTree 方法合并
            List<String> permissionCodes = this.operatorDao.findPermissions(userOperator.getEId());
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginOperator(userOperator);
            loginUser.setLoginEmployee(employee);
            loginUser.setUserPermissionsCode(permissionCodes);
            loginUser.setUserName(userOperator.getName());
            userOperator.setPassWord("");

            List<Long> roles = this.operatorAndRoleDao.findRoleIdsByOperator(userOperator.getEId());
            loginUser.setUserRoles(roles);

            this.setLoginUser(loginUser);
            return loginUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private LoginUser setLoginMember(Operator userOperator) throws Exception {
        try {
            Member member = this.memberService.findMember(userOperator.getPersonId());
            if (member == null) {
                logger.error("没有找到商家信息");
                throw new DDDException("没有找到商家信息");
            }
            //这行代码可以和 ModuleService中的 constructNewTree 方法合并
            List<String> permissionCodes = this.operatorDao.findPermissions(userOperator.getEId());
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginOperator(userOperator);
            loginUser.setLoginMember(member);
            loginUser.setUserPermissionsCode(permissionCodes);
            loginUser.setUserName(userOperator.getName());
            userOperator.setPassWord("");

            List<Long> roles = this.operatorAndRoleDao.findRoleIdsByOperator(userOperator.getEId());
            loginUser.setUserRoles(roles);

            this.setLoginUser(loginUser);
            return loginUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void logout() {
        this.clearLoginUser();
    }

    public void wisdomCateringLogout() {
        this.clearLoginUser();
    }


    @Override
    public void createSuperUser() {
        Employee employee = this.employeeDao.findEmployeeByCode(SuperUserCode);
        Long employeeId = 0l;
        if (employee == null) {
            employee = new Employee();
            employee.setCode(SuperUserCode);
            employee.setName("超级用户");
            employee.setRemark("这是一个用于开发的超级用户，实际使用时请删除");
            this.employeeDao.saveEmployee(employee);
        }
        Operator superOperator = this.operatorDao.findOperatorByCode(SuperUserCode);
        if (superOperator == null) {
            superOperator = new Operator();
            superOperator.setCode(SuperUserCode);
            superOperator.setName("超级用户");
            //对密码进行加密
            String password = SuperUserCode + SuperUserCode;
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            password = SuperUserCode + md5Password;

            superOperator.setPassWord(password);
            superOperator.setType("虚拟用户");
            superOperator.setPersonId(employee.getEId());
            this.operatorDao.saveOperator(superOperator);
        }

        Role superRole = this.roleService.createSuperRole();
        OperatorAndRole operatorAndRole = this.operatorAndRoleDao.findOperatorAndRoleByOperatorAndRole(SuperUserCode, RoleService.SuperCode);
        if (operatorAndRole == null) {
            operatorAndRole = new OperatorAndRole();
            operatorAndRole.setOperatorId(superOperator.getEId());
            operatorAndRole.setRoleId(superRole.getEId());
            operatorAndRole.setOrganizationId(0l);
            this.operatorAndRoleDao.saveOperatorAndRole(operatorAndRole);
        }

    }
}