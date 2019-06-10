package com.cisdi.info.simple.service.system;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.system.CodeTableOptionDTO;
import com.cisdi.info.simple.dto.system.CodeTableOrgDTO;
import com.cisdi.info.simple.dto.system.CodeTableTypeDTO;
import com.cisdi.info.simple.entity.system.CodeTable;

import java.util.List;

public interface CodeTableService {

    public List<CodeTable> findCodeTables(PageDTO pageDTO);

    public List<CodeTable> findAllCodeTables();

    public CodeTable findCodeTable(String uuid);

    /**
     * 新增码表
     *
     * @param codeTableTypeDTO
     * @return
     */
    public CodeTable saveCodeTable(CodeTableTypeDTO codeTableTypeDTO);

    /**
     * 新增组织单位码表
     *
     * @param codeTableOrgDTO
     * @return
     */
    public CodeTable saveOrganization(CodeTableOrgDTO codeTableOrgDTO);

    /**
     * 新增码表选项
     *
     * @param codeTableOptionDTO
     * @return
     */
    public CodeTable saveOption(CodeTableOptionDTO codeTableOptionDTO);

    /**
     * 更新码表
     *
     * @param codeTable
     * @return
     */
    public CodeTable updateCodeTable(CodeTable codeTable);

    /**
     * 更新码表选项
     *
     * @param codeTable
     * @return
     */
    public CodeTable updateCodeTableOption(CodeTable codeTable);

    public void deleteCodeTable(String codeTableId);

    /**
     * 根据编码找到对应码表
     *
     * @param code
     * @return
     */
    public List<CodeTable> findCodeTableByCode(String code);

    /**
     * 查找所有码表类型
     *
     * @return
     */
    public List<CodeTable> findAllCodeType();

    /**
     * 根据uuid找到码表，包含子码表
     *
     * @param uuid
     * @return
     */
    public CodeTable findCodeTableByUUID(String uuid);

    /**
     * 找到全部码表树
     *
     * @param pageDTO
     * @return
     */
    public PageResultDTO findAllCodeTablesTree(PageDTO pageDTO);
}
