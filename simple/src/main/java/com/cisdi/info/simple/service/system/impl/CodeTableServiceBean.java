package com.cisdi.info.simple.service.system.impl;

import com.cisdi.info.simple.dao.system.CodeTableDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.util.CodeTableManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CodeTableServiceBean extends BaseService implements CodeTableService {

private static Logger logger = LogManager.getLogger();

@Autowired
private CodeTableDao codeTableDao;

public PageResultDTO findCodeTables(PageDTO pageDTO)
{
	pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
	List<CodeTable> codeTableDTOS = this.codeTableDao.findCodeTables(pageDTO);
	Long totalCount = this.codeTableDao.findCodeTableTotalCount(pageDTO);

	PageResultDTO pageResultDTO = new PageResultDTO();
	pageResultDTO.setTotalCount(totalCount);
	pageResultDTO.setDatas(codeTableDTOS);

	return pageResultDTO;
}

public List<CodeTable> findAllCodeTables()
{
	return this.codeTableDao.findAllCodeTables();
}

public List<CodeTable> findAllCodeTablesWithIdName()
{
	return this.codeTableDao.findAllCodeTablesWithIdName();
}

public CodeTable findCodeTable(Long codeTableId)
{
	return this.codeTableDao.findCodeTable(codeTableId);
}

//所有外键的Name都以加载
public CodeTable findCodeTableWithForeignName(Long codeTableId)
{
	return this.codeTableDao.findCodeTableWithForeignName(codeTableId);
}

public CodeTable saveCodeTable(CodeTable codeTable)
{
	this.setSavePulicColumns(codeTable);
	return this.codeTableDao.saveCodeTable(codeTable);
}

public CodeTable updateCodeTable(CodeTable codeTable)
{
	this.setUpdatePulicColumns(codeTable);
	return this.codeTableDao.updateCodeTable(codeTable);
}

public void deleteCodeTable(Long codeTableId)
{
	this.codeTableDao.deleteCodeTable(codeTableId);
}

@Override
public List<CodeTable> findCodeTablesByCodeType(String codeType) {
	return CodeTableManager.findCodeTablesByType(codeType);
}

}
