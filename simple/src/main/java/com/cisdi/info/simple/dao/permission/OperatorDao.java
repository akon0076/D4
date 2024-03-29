package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.dto.operator.PasswordDto;
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

    /**
     * 根据id查询操作员，不包含密码
     * @param operatorId
     * @return
     */
    public Operator findOperator(Long operatorId);

    /**
     * 根据id查询操作员，包含密码
     * @param operatorId
     * @return
     */
    public Operator findOperatorWithPassword(Long operatorId);

    public Operator findOperatorWithForeignName(Long operatorId);

    public Long saveOperator(Operator operator);

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

    public List<String> findAllModuleCodesByOperatorId(Long operatorId);

    /**
     * 管理员修改密码
     * @param passwordDto
     * @return
     */
    public int changePassword(PasswordDto passwordDto);

    /**
     * 自己修改密码
     * @param passwordDto
     * @return
     */
    public int changeMyPassword(PasswordDto passwordDto);
}
