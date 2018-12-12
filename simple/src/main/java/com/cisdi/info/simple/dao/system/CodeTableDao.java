package com.cisdi.info.simple.dao.system;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.system.CodeTable;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "codeTableDao")
public interface CodeTableDao {

public List<CodeTable> findCodeTables(PageDTO pageDTO);

public List<CodeTable> findAllCodeTables();

public List<CodeTable> findAllCodeTablesWithIdName();

public Long findCodeTableTotalCount(PageDTO pageDTO);

public CodeTable findCodeTable(Long codeTableId);

//所有外键的Name都以加载
public CodeTable findCodeTableWithForeignName(Long codeTableId);

public CodeTable saveCodeTable(CodeTable codeTable);

public CodeTable updateCodeTable(CodeTable codeTable);

public void deleteCodeTable(Long codeTableId);
}
