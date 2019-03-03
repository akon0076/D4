package com.cisdi.info.simple.service.system;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.system.CodeTable;

import java.util.List;

public interface CodeTableService {

public PageResultDTO findCodeTables(PageDTO pageDTO);

public List<CodeTable> findAllCodeTables();

public List<CodeTable> findAllCodeTablesWithIdName();

public CodeTable findCodeTable(Long codeTableId);

//所有外键的Name都以加载
public CodeTable findCodeTableWithForeignName(String codeTableId);

public CodeTable saveCodeTable(CodeTable codeTable);

public CodeTable updateCodeTable(CodeTable codeTable);

public void deleteCodeTable(String codeTableId);

public List<CodeTable> findCodeTablesByCodeType(String codeType);
}
