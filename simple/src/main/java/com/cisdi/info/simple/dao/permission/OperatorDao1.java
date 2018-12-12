package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component()
public interface OperatorDao1 {

//    /**
//     * 保存操作员
//     * @param operator
//     * @return 操作员ID
//     */
//    public Long saveOperator(Operator operator);
//
//    /**
//     * 编辑操作员
//     * @param operator
//     * @return 受影响行数
//     */
//    public int updateOperator(Operator operator);
//
//    /**
//     * 删除操作员
//     * @param operatorId
//     */
//    public void deleteOperator(Long operatorId);
//
//    /**
//     * 根据 操作员ID 查找操作员
//     * @param operatorId
//     * @return 操作员
//     */
//    public Operator findOperatorById(Long operatorId);
//
//    /**
//     * 查找所有操作员
//     * @return 操作员集合
//     */
//    public List<Operator> findAllOperators();

    /**
     * 根据用户名和密码查找操作员
     * @param loginDTO
     * @return 操作员ID
     */
      public Operator findOperatorByUserNameAndPassWord(LoginDTO loginDTO);

      public List<String> findPermissionsByOperatorId(Long operatorId);

}
