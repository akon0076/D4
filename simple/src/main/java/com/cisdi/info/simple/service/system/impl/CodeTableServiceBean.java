package com.cisdi.info.simple.service.system.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.organization.OrganizationDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.system.CodeTableOptionDTO;
import com.cisdi.info.simple.dto.system.CodeTableOrgDTO;
import com.cisdi.info.simple.dto.system.CodeTableTypeDTO;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.system.CodeTableService;
import com.cisdi.info.simple.util.CodeTableManager;
import com.cisdi.info.simple.util.D4Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CodeTableServiceBean extends BaseService implements CodeTableService {

    private static final String CODE_TYPE = "码表类型";
    private static final String ORG_TYPE = "组织单位";
    private static final String CODE_OPTION = "码表选项";

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private OrganizationDao organizationDao;

    public List<CodeTable> findCodeTables(PageDTO pageDTO) {
        List<CodeTable> list = CodeTableManager.findAllCodeTables();
        Integer currentPage = pageDTO == null ? 1 : pageDTO.getCurrentPage();
        Integer pageSize = pageDTO == null ? 10 : pageDTO.getPageSize();
        int formIndex = (currentPage - 1) * pageSize;
        int toIndex = formIndex + pageSize;
        toIndex = toIndex > list.size() ? list.size() : toIndex;
        List<CodeTable> resultlist = list.subList(formIndex, toIndex);
        CodeTable resultModule = new CodeTable();
        resultModule.getChildren().addAll(resultlist);
        sort(resultModule);
        return resultModule.getChildren();
    }

    @Override
    public List<CodeTable> findAllCodeTables() {
        List<CodeTable> list = CodeTableManager.findAllCodeTables();
        CodeTable resultModule = new CodeTable();
        resultModule.getChildren().addAll(list);
        sort(resultModule);
        return resultModule.getChildren();
    }

    /**
     * 找到全部码表树
     *
     * @return
     */
    @Override
    public List<CodeTable> findAllCodeTablesTree(PageDTO pageDTO) {
        List<CodeTable> list = CodeTableManager.findAllCodeTables();
        List<CodeTable> tree = new ArrayList<>();
        for (CodeTable codeTable : list) {
            if (codeTable.getParentUUID() == null) {
                tree.add(codeTable);
                setChildren(list, codeTable);
            }
        }
        Integer currentPage = pageDTO == null ? 1 : pageDTO.getCurrentPage();
        Integer pageSize = pageDTO == null ? 10 : pageDTO.getPageSize();
        int formIndex = (currentPage - 1) * pageSize;
        int toIndex = formIndex + pageSize;
        toIndex = toIndex > tree.size() ? tree.size() : toIndex;
        CodeTable resultModule = new CodeTable();
        resultModule.getChildren().addAll(tree);
        sort(resultModule);
        List<CodeTable> resultlist = resultModule.getChildren().subList(formIndex, toIndex);
        return resultlist;
    }

    /**
     * 设置码表树的子节点
     *
     * @param list
     * @param codeTable
     */
    private void setChildren(List<CodeTable> list, CodeTable codeTable) {
        String uuid = codeTable.getUuid();
        for (CodeTable child : list) {
            if (uuid.equals(child.getParentUUID())) {
                codeTable.addChildren(child);
                setChildren(list, child);
            }
        }
    }

    @Override
    public CodeTable findCodeTable(String uuid) {
        return CodeTableManager.findCodeTablesByUUID(uuid);
    }

    /**
     * 新增码表
     *
     * @param codeTableTypeDTO
     * @return
     */
    public CodeTable saveCodeTable(CodeTableTypeDTO codeTableTypeDTO) {
        String code = codeTableTypeDTO.getCode();
        if (CodeTableManager.findCodeTablesByCode(code) != null) {
            throw new DDDException("码表编码必须唯一，当前编码已存在");
        }
        CodeTable codeTable = new CodeTable();
        String uuid = D4Util.getUUID();
        codeTable.setUuid(uuid);
        codeTable.setCode(codeTableTypeDTO.getCode());
        codeTable.setName(codeTableTypeDTO.getName());
        codeTable.setPublic(codeTableTypeDTO.isPublic());
        codeTable.setCodeType(CODE_TYPE);
        codeTable.setDisplayIndex(codeTableTypeDTO.getDisplayIndex());
        CodeTableManager.addCodeTable(codeTable);
        return codeTable;
    }

    /**
     * 新增组织单位码表
     *
     * @param codeTableOrgDTO
     * @return
     */
    public CodeTable saveOrganization(CodeTableOrgDTO codeTableOrgDTO) {
        String codeTypeId = codeTableOrgDTO.getCodeTypeId();
        CodeTable table = findCodeTableByUUID(codeTypeId);
        Long orgId = codeTableOrgDTO.getOrgId();
        List<CodeTable> children = table.getChildren();
        for (CodeTable child : children) {
            if (orgId.equals(child.getOrgId())) {
                throw new DDDException("此组织单位已存在对应的码表");
            }
        }
        Organization organization = organizationDao.findOrganization(orgId);
        CodeTable codeTable = new CodeTable();
        String tableName = table.getName() + "-" + organization.getName();
        String uuid = D4Util.getUUID();
        codeTable.setUuid(uuid);
        codeTable.setParentUUID(codeTypeId);
        codeTable.setCodeTypeId(codeTypeId);
        codeTable.setOrgId(codeTableOrgDTO.getOrgId());
        codeTable.setPublic(codeTableOrgDTO.isPublic());
        codeTable.setName(tableName);
        codeTable.setCodeType(ORG_TYPE);
        codeTable.setOrgName(tableName);
        codeTable.setCode(table.getCode());
        codeTable.setDisplayIndex(codeTableOrgDTO.getDisplayIndex());
        CodeTableManager.addCodeTable(codeTable);
        return codeTable;
    }

    /**
     * 新增码表选项
     *
     * @param codeTableOptionDTO
     * @return
     */
    public CodeTable saveOption(CodeTableOptionDTO codeTableOptionDTO) {
        String codeTypeId = codeTableOptionDTO.getCodeTypeId();
        CodeTable table = findCodeTableByUUID(codeTypeId);
        if (table.isPublic()) {
            return savePublicOption(codeTableOptionDTO);
        } else {
            return savePrivateOption(codeTableOptionDTO);
        }
    }

    /**
     * 保存公开码表
     *
     * @param codeTableOptionDTO
     */
    private CodeTable savePublicOption(CodeTableOptionDTO codeTableOptionDTO) {
        String codeTypeId = codeTableOptionDTO.getCodeTypeId();
        CodeTable table = findCodeTableByUUID(codeTypeId);
        CodeTable codeTable = new CodeTable();
        String uuid = D4Util.getUUID();
        codeTable.setUuid(uuid);
        codeTable.setParentUUID(codeTypeId);
        codeTable.setCodeTypeId(codeTypeId);
        codeTable.setLabel(codeTableOptionDTO.getLabel());
        codeTable.setValue(codeTableOptionDTO.getValue());
        codeTable.setCodeType(CODE_OPTION);
        codeTable.setCode(table.getCode());
        codeTable.setPublic(true);
        codeTable.setDisplayIndex(codeTableOptionDTO.getDisplayIndex());
        CodeTableManager.addCodeTable(codeTable);
        return codeTable;
    }

    /**
     * 保存私有码表
     *
     * @param codeTableOptionDTO
     */
    private CodeTable savePrivateOption(CodeTableOptionDTO codeTableOptionDTO) {
        String codeTypeId = codeTableOptionDTO.getCodeTypeId();
        Long orgId = this.getCurrentLoginOrganization().getEId();
        CodeTable orgCodeTable = findOrgCodeType(codeTypeId, orgId);
        if (orgCodeTable == null) {
            CodeTableOrgDTO codeTableOrgDTO = new CodeTableOrgDTO();
            codeTableOrgDTO.setCodeTypeId(codeTypeId);
            codeTableOrgDTO.setPublic(false);
            codeTableOrgDTO.setOrgId(orgId);
            saveOrganization(codeTableOrgDTO);
            orgCodeTable = findOrgCodeType(codeTypeId, orgId);
        }
        CodeTable codeTable = new CodeTable();
        String uuid = D4Util.getUUID();
        codeTable.setUuid(uuid);
        codeTable.setCodeTypeId(codeTypeId);
        codeTable.setParentUUID(orgCodeTable.getUuid());
        codeTable.setOrgId(orgId);
        codeTable.setLabel(codeTableOptionDTO.getLabel());
        codeTable.setValue(codeTableOptionDTO.getValue());
        codeTable.setCodeType(CODE_OPTION);
        codeTable.setCode(orgCodeTable.getCode());
        codeTable.setPublic(false);
        codeTable.setDisplayIndex(codeTableOptionDTO.getDisplayIndex());
        CodeTableManager.addCodeTable(codeTable);
        return codeTable;
    }


    /**
     * 根据码表uuid和组织id，找到对应码表
     *
     * @param uuid
     * @param orgId
     * @return
     */
    public CodeTable findOrgCodeType(String uuid, Long orgId) {
        if (StringUtils.isBlank(uuid) || orgId == null) {
            return null;
        }
        CodeTable table = findCodeTableByUUID(uuid);
        List<CodeTable> children = table.getChildren();
        for (CodeTable codeTable : children) {
            if (orgId.equals(codeTable.getOrgId())) {
                return findCodeTableByUUID(codeTable.getUuid());
            }
        }
        return null;
    }

    /**
     * 更新码表
     *
     * @param codeTable
     * @return
     */
    public CodeTable updateCodeTable(CodeTable codeTable) {
        String uuid = codeTable.getUuid();
        CodeTable table = CodeTableManager.findCodeTablesByUUID(uuid);
        if (table == null) {
            throw new DDDException("当前码表不存在，可能已经被删除或修改");
        }
        if (!CODE_TYPE.equals(codeTable.getCodeType())) {
            throw new DDDException("码表类型错误");
        }
        if (codeTable.isPublic() != table.isPublic()) {
            throw new DDDException("码表是否私有字段禁止修改");
        }
        if (!codeTable.getCode().equals(table.getCode())) {
            if (CodeTableManager.findCodeTablesByCode(codeTable.getCode()) != null) {
                throw new DDDException("码表编码必须唯一，当前编码已存在");
            }
        }
        //修改码表
        CodeTableManager.updateCodeTable(codeTable);
        updateChildrenCode(table.getCode(), codeTable.getCode());
        return codeTable;
    }

    /**
     * 更新码表选项
     *
     * @param codeTable
     * @return
     */
    public CodeTable updateCodeTableOption(CodeTable codeTable) {
        String uuid = codeTable.getUuid();
        CodeTable table = CodeTableManager.findCodeTablesByUUID(uuid);
        if (table == null) {
            throw new DDDException("当前码表不存在，可能已经被删除或修改");
        }
        if (!CODE_OPTION.equals(codeTable.getCodeType())) {
            throw new DDDException("码表类型错误");
        }
        //修改码表
        CodeTableManager.updateCodeTable(codeTable);
        return codeTable;
    }

    /**
     * 修改所有子码表的编码
     *
     * @param code
     * @param newCode
     */
    private void updateChildrenCode(String code, String newCode) {
        Map<String, CodeTable> codeTables = CodeTableManager.getCodeTables();
        Collection<CodeTable> values = codeTables.values();
        for (CodeTable codeTable : values) {
            if (code.equals(codeTable.getCode())) {
                codeTable.setCode(newCode);
            }
        }
        CodeTableManager.saveCodeTables();
    }

    /**
     * 根据uuid找到码表，包含子码表
     *
     * @param uuid
     * @return
     */
    public CodeTable findCodeTableByUUID(String uuid) {
        CodeTable table = CodeTableManager.findCodeTablesByUUID(uuid);
        if (table == null) {
            throw new DDDException("当前码表不存在，可能已经被删除或修改");
        }
        List<CodeTable> allCodeTables = CodeTableManager.findAllCodeTables();
        for (CodeTable codeTable : allCodeTables) {
            if (uuid.equals(codeTable.getParentUUID())) {
                table.addChildren(codeTable);
            }
        }
        return table;
    }

    /**
     * 删除码表
     *
     * @param uuid
     */
    public void deleteCodeTable(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            throw new DDDException("参数为null，请检查参数是否正确");
        }
        CodeTableManager.removeCodeTables(uuid);
    }

    /**
     * 根据编码找到对应码表选项
     *
     * @param code
     * @return
     */
    @Override
    public List<CodeTable> findCodeTableByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        //找到对应码表
        CodeTable table = CodeTableManager.findCodeTablesByCode(code);
        if (table == null) {
            return null;
        }
        List<CodeTable> allCodeTables = CodeTableManager.findAllCodeTables();
        String uuid = table.getUuid();
        List<CodeTable> list;
        if (table.isPublic()) {
            //找到公开码表选项
            list = findPublicCodeTable(allCodeTables, uuid);
        } else {
            //找到私有码表选项
            list = findPrivateCodeTable(allCodeTables, uuid);
        }
        return list;
    }

    /**
     * 找到公开码表选项
     *
     * @param allCodeTables
     * @param uuid
     * @return
     */
    private List<CodeTable> findPublicCodeTable(List<CodeTable> allCodeTables, String uuid) {
        List<CodeTable> list = new ArrayList<>();
        allCodeTables.forEach(codeTable -> {
            if (uuid.equals(codeTable.getParentUUID())) {
                list.add(codeTable);
            }
        });
        return list;
    }

    /**
     * 找到私有码表选项
     *
     * @param allCodeTables
     * @param uuid
     * @return
     */
    private List<CodeTable> findPrivateCodeTable(List<CodeTable> allCodeTables, String uuid) {
        List<CodeTable> list = new ArrayList<>();
        Long orgId = this.getCurrentLoginOrganization().getEId();
        allCodeTables.forEach(codeTable -> {
            if (uuid.equals(codeTable.getCodeTypeId()) && orgId.equals(codeTable.getOrgId()) && CODE_OPTION.equals(codeTable.getCodeType())) {
                list.add(codeTable);
            }
        });
        return list;
    }

    /**
     * 查找所有码表类型
     *
     * @return
     */
    @Override
    public List<CodeTable> findAllCodeType() {
        List<CodeTable> allCodeTables = CodeTableManager.findAllCodeTables();
        List<CodeTable> list = new ArrayList<>();
        for (CodeTable codeTable : allCodeTables) {
            if (CODE_TYPE.equals(codeTable.getCodeType())) {
                list.add(codeTable);
            }
        }
        return list;
    }

    /**
     * 对码表排序
     *
     * @param codeTable
     */
    private void sort(CodeTable codeTable) {
        List<CodeTable> children = codeTable.getChildren();
        if (children.size() == 0) {
            return;
        }
        children.sort(Comparator.comparing(CodeTable::getDisplayIndex));
        for (CodeTable child : children) {
            sort(child);
        }
    }

}
