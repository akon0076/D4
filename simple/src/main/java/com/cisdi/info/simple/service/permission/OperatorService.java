package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.dto.operator.PasswordDto;
import com.cisdi.info.simple.entity.permission.Operator;

import java.util.List;
import java.util.Map;

public interface OperatorService {

    public PageResultDTO findOperators(PageDTO pageDTO);

    public List<Operator> findAllOperators();

    public List<Operator> findAllOperatorsWithIdName();

    public void logout();

    public void wisdomCateringLogout();

    public Operator findOperator(Long operatorId);

    //所有外键的Name都以加载
    public Operator findOperatorWithForeignName(Long operatorId);

    public Long saveOperator(Operator operator);

    public Operator updateOperator(Operator operator);

    public void deleteOperator(Long operatorId);

    public Map<String, Object> checkOperatorByUserNameAndPassWord(LoginDTO loginDTO);

    public Map<String, Object> getOrganizations(LoginDTO loginDTO);

    public Map<String, Object> mobileCheckOperatorByUserNameAndPassWord(LoginDTO loginDTO);

    public Map<String, Object> checkMemberByUserNameAndPassWord(LoginDTO loginDTO);

    public static String SuperUserCode = "000";

    public void createSuperUser();

    public List<String> findAllModuleCodesByOperatorId(Long operatorId);

    public boolean changePassword(PasswordDto passwordDto);

    public boolean changeMyPassword(PasswordDto passwordDto);

}
