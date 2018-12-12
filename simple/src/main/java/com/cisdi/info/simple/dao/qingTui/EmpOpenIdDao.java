package com.cisdi.info.simple.dao.qingTui;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.qingTui.EmpOpenId;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "empOpenIdDao")
public interface EmpOpenIdDao {

    public List<EmpOpenId> findEmpOpenIds(PageDTO pageDTO);

    public List<EmpOpenId> findAllEmpOpenIds();

    public List<EmpOpenId> findAllEmpOpenIdsWithIdName();

    public List<EmpOpenId> findEmpOpenIdsWithIdNameByName(@Param("empOpenIdName") String empOpenIdName);

    public EmpOpenId findEmpOpenIdsWithIdNameById(@Param(" empOpenIdId") Long empOpenIdId);

    public Long findEmpOpenIdTotalCount(PageDTO pageDTO);

    public EmpOpenId findEmpOpenId(@Param("empOpenIdId") Long empOpenIdId);

    //所有外键的Name都以加载
    public EmpOpenId findEmpOpenIdWithForeignName(@Param("empOpenIdId") Long empOpenIdId);

    public Integer saveEmpOpenId(EmpOpenId empOpenId);

    public Integer updateEmpOpenId(EmpOpenId empOpenId);

    public Integer deleteEmpOpenId(@Param("empOpenIdId") Long empOpenIdId);

    public EmpOpenId findEmpOpenIdByEmpIdAndOpenId(EmpOpenId empOpenId);

    public EmpOpenId findEmpOpenByOpenId(String openId);

    public EmpOpenId findEmpOpenIdByEmpIdAndType(EmpOpenId empOpenId);

    public EmpOpenId findMemberOpenIdByMemIdAndType(EmpOpenId empOpenId);

    public EmpOpenId findEmpOpenIdByOperatorId(Long operatorId);
}
