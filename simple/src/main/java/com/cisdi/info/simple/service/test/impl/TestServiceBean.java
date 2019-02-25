package com.cisdi.info.simple.service.test.impl;

import com.cisdi.info.simple.dao.test.TestDao;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.util.D4Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.test.Test;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class TestServiceBean extends BaseService implements TestService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private TestDao testDao;

	/**
	 * 根据分页参数查询测试管理集合
	 *
	 * @param pageDTO 分页条件
	 */
	public PageResultDTO findTests(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<Test> testDTOS = this.testDao.findTests(pageDTO);
		Long totalCount = this.testDao.findTestTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(testDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部测试管理集合
	 *
	 */
	public List<Test> findAllTests(){
		return this.testDao.findAllTests();
	}

	/**
	 * 查询所有测试管理集合(只提取ID 和 Name)
	 *
	 */
	public List<Test> findAllTestsWithIdName(){
		return this.testDao.findAllTestsWithIdName();
	}

	/**
	 * 根据名称查询测试管理集合(只提取ID 和 Name)
	 *
	 * @param testName 名称
	 */
	public List<Test> findTestsWithIdNameByName(String testName){
		return this.testDao.findTestsWithIdNameByName(testName);
	}

	/**
	 * 根据ID查询指定的测试管理(只提取ID 和 Name)
	 *
	 * @param testId Id
	 */
	public Test findTestsWithIdNameById(Long testId){
		return this.testDao.findTestsWithIdNameById(testId);
	}

	/**
	 * 根据ID查询指定的测试管理
	 *
	 * @param testId Id
	 */
	public Test findTest(Long testId){
		return this.testDao.findTest(testId);
	}

	/**
	 * 根据ID查询指定的测试管理(包含外键)
	 *
	 * @param testId Id
	 */
	public Test findTestWithForeignName(Long testId){
		return this.testDao.findTestWithForeignName(testId);
	}

	/**
	 * 新增测试管理
	 *
	 * @param test 实体对象
	 */
	public Test saveTest(Test test){
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(test);
		Integer rows = this.testDao.saveTest(test);
		if(rows != 1)
		{
			String error = "新增保存测试管理出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return test;
	}

	/**
	 * 更新测试管理
	 *
	 * @param test 实体对象
	 */
	public Test updateTest(Test test){
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(test);
		Integer rows = this.testDao.updateTest(test);
		if(rows != 1)
		{
			String error = "修改保存测试管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return test;
	}

	/**
	 * 根据ID删除测试管理
	 *
	 * @param testId ID
	 */
	public void deleteTest(Long testId){
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(Test.class, testId);
		if(entityUsageMap != null && entityUsageMap.size() >0){
			StringBuilder errors = new StringBuilder();
			errors.append("计划删除的数据正在被以下数引用\n");
			for(EntityUsage entityUsage : entityUsageMap.values()){
				errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
				for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() ){
					errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
				}
			}
			errors.append("，不能删除，请检查处理后再删除");
			throw  new DDDException(errors.toString());
		}

		Integer rows = this.testDao.deleteTest(testId);
		if(rows != 1){
			String error = "删除测试管理出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
