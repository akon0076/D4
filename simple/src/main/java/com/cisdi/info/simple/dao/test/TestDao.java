package com.cisdi.info.simple.dao.test;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.test.Test;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "testDao")
public interface TestDao {

    /**
    * 根据分页参数查询测试管理集合
    *
    * @param pageDTO 分页条件
    */
    public List<Test> findTests(PageDTO pageDTO);

    /**
    * 查询全部测试管理集合
    *
    */
    public List<Test> findAllTests();

    /**
    * 查询所有测试管理集合(只提取ID 和 Name)
    *
    */
    public List<Test> findAllTestsWithIdName();

    /**
    * 根据名称查询测试管理集合(只提取ID 和 Name)
    *
    * @param testName 名称
    */
    public List<Test> findTestsWithIdNameByName(@Param("testName") String testName);

    /**
    * 根据ID查询指定的测试管理(只提取ID 和 Name)
    *
    * @param testId Id
    */
    public Test findTestsWithIdNameById(@Param(" testId") Long testId);

    /**
    * 根据分页参数查询测试管理集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTestTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的测试管理
    *
    * @param testId Id
    */
    public Test findTest(@Param("testId") Long testId);

    /**
    * 根据ID查询指定的测试管理(包含外键)
    *
    * @param testId Id
    */
    public Test findTestWithForeignName(@Param("testId") Long testId);

    /**
    * 新增测试管理
    *
    * @param test 实体对象
    */
    public Integer saveTest(Test test);

    /**
    * 更新测试管理
    *
    * @param test 实体对象
    */
    public Integer updateTest(Test test);

    /**
    * 根据ID删除测试管理
    *
    * @param testId ID
    */
    public Integer deleteTest(@Param("testId") Long testId);
}
