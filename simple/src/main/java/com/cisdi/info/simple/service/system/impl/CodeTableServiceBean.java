package com.cisdi.info.simple.service.system.impl;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.util.CodeTableManager;
import com.cisdi.info.simple.util.D4Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CodeTableServiceBean extends BaseService implements CodeTableService {

private static Logger logger = LogManager.getLogger();



public PageResultDTO findCodeTables(PageDTO pageDTO)
{

	if (pageDTO.getColumnName() == null || "".equals(pageDTO.getColumnName()) || pageDTO.getContent() == null || "".equals(pageDTO.getContent())) {
		pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
		List list = new ArrayList(CodeTableManager.findAllCodeTables());
		List returnList = list.subList(pageDTO.getStartIndex(), pageDTO.getStartIndex() + pageDTO.getPageSize() > list.size() ? list.size() : pageDTO.getStartIndex() + pageDTO.getPageSize());
		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount((long) list.size());
		pageResultDTO.setDatas(returnList);
		return pageResultDTO;
	} else if (pageDTO.getSql().contains("AND")) {
		String methodName = D4Util.getAttributerGetterName(pageDTO.getColumnName());
		List<CodeTable> list = new ArrayList(CodeTableManager.findAllCodeTables());
		List<CodeTable> returnedList = new ArrayList<>();
		String[] anditems = pageDTO.getContent().split("\\s+and\\s");
		for (CodeTable module : list) {
			String result = D4Util.invokeMethodByString(module, methodName);
			if (result == null)
				result = "";
			boolean mark = true;
			for (int i = 0; i < anditems.length; i++) {
				if (!result.contains(anditems[i])) {
					mark = false;
					break;
				}
			}
			if (mark) {
				returnedList.add(module);
			}
		}
		pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount((long) returnedList.size());
		pageResultDTO.setDatas(returnedList.subList(pageDTO.getStartIndex(), pageDTO.getStartIndex() + pageDTO.getPageSize() > returnedList.size() ? returnedList.size() : pageDTO.getStartIndex() + pageDTO.getPageSize()));
		return pageResultDTO;
	} else {
		String methodName = D4Util.getAttributerGetterName(pageDTO.getColumnName());
		List<CodeTable> list = new ArrayList(CodeTableManager.findAllCodeTables());
		List<CodeTable> returnedList = new ArrayList<>();
		String[] items = pageDTO.getContent().split("\\s+");//or
		for (CodeTable module : list) {
			String result = D4Util.invokeMethodByString(module, methodName);
			if (result == null)
				result = "";
			boolean mark = false;
			for (int i = 0; i < items.length; i++) {
				if (result.contains(items[i])) {
					mark = true;
					break;
				}
			}
			if (mark) {
				returnedList.add(module);
			}
		}
		pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount((long) returnedList.size());
		pageResultDTO.setDatas(returnedList.subList(pageDTO.getStartIndex(), pageDTO.getStartIndex() + pageDTO.getPageSize() > returnedList.size() ? returnedList.size() : pageDTO.getStartIndex() + pageDTO.getPageSize()));
		return pageResultDTO;
	}
}

public List<CodeTable> findAllCodeTables()
{
	return null;
}

public List<CodeTable> findAllCodeTablesWithIdName()
{
	return null;
}

public CodeTable findCodeTable(Long codeTableId)
{
	return null;
}

//所有外键的Name都以加载
public CodeTable findCodeTableWithForeignName(String codeTableId)
{
	String[] items=codeTableId.split("\\.");
    List<CodeTable> tables=CodeTableManager.findCodeTablesByType(items[0]);
     for(int i=0;i<tables.size();i++) {
		 if (codeTableId.equals(tables.get(i).getFullname())) {
			 return tables.get(i);
		 }
	 }
	return null;
}

public CodeTable saveCodeTable(CodeTable codeTable)
{
	codeTable.setFullname(codeTable.getCodeType()+"."+codeTable.getName());
	List<CodeTable> newCodeTables = new ArrayList<>();
	newCodeTables.add(codeTable);
	CodeTableManager.addCodeTables(codeTable.getCodeType(),newCodeTables);
	return null;
}

public CodeTable updateCodeTable(CodeTable codeTable)
{
	CodeTableManager.updateCodeTable(codeTable);
	return null;
}

public void deleteCodeTable(String codeTableId)
{
	 String[] items=codeTableId.split("\\.");
     CodeTableManager.removeCodeTables(items[0],codeTableId);
}

@Override
public List<CodeTable> findCodeTablesByCodeType(String codeType) {
	  List<CodeTable> list = CodeTableManager.findCodeTablesByType(codeType);
       Collections.sort(list);
	return list ;
}

}
