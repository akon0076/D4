package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.entity.permission.Operator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "operatorDao")
public interface OperatorDao {

    public List<Operator> findOperators(PageDTO pageDTO);

    public List<Operator> findAllOperators();

    public List<Operator> findAllOperatorsWithIdName();

    public Long findOperatorTotalCount(PageDTO pageDTO);

    public Operator findOperator(Long operatorId);

    //所有外键的Name都以加载
    public Operator findOperatorWithForeignName(Long operatorId);

    public Operator saveOperator(Operator operator);

    public Operator updateOperator(Operator operator);

    public void deleteOperator(Long operatorId);

    /**
     * 根据用户名和密码查找操作员
     *
     * @param loginDTO
     * @return 操作员ID
     */
    public Operator findOperatorByUserNameAndPassWord(LoginDTO loginDTO);

    public Operator findOperatorByUserNameAndPassWordAndMember(LoginDTO loginDTO);

    public List<String> findPermissionsByOperatorId(Long operatorId);

    public Operator findOperatorByCode(String code);

    public List<String> findPermissions(Long operatorId);
    public List<String> findPermissionsByIdAndType(PageDTO pageDTO);

    public Operator findOperatorByEmailAndPassWord(LoginDTO loginDTO);
}
