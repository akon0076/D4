package com.cisdi.info.simple.service.qingTui;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.qingTui.BindingDto;
import com.cisdi.info.simple.entity.qingTui.EmpOpenId;

import java.util.List;
import java.util.Map;

public interface EmpOpenIdService {

    public PageResultDTO findEmpOpenIds(PageDTO pageDTO);

    public List<EmpOpenId> findAllEmpOpenIds();

    public List<EmpOpenId> findEmpOpenIdsWithIdNameByName(String empOpenIdName);

    public List<EmpOpenId> findAllEmpOpenIdsWithIdName();

    public EmpOpenId findEmpOpenIdsWithIdNameById(Long empOpenIdId);

    public EmpOpenId findEmpOpenId(Long empOpenIdId);

    //所有外键的Name都以加载
    public EmpOpenId findEmpOpenIdWithForeignName(Long empOpenIdId);

    public EmpOpenId saveEmpOpenId(EmpOpenId empOpenId);

    public EmpOpenId updateEmpOpenId(EmpOpenId empOpenId);

    public void deleteEmpOpenId(Long empOpenIdId);

    public Map<String,Object> bindingQingtui(BindingDto bindingDto);
}
