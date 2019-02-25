package com.cisdi.info.simple.service.test;

import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.test.Test;
import com.cisdi.info.simple.dto.base.PageDTO;

import java.util.List;

public interface TestService {
    /**
     * 根据分页参数查询测试管理集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTests(PageDTO pageDTO);

    /**
     * 查询全部测试管理集合
     *
     */
    public List<Test> findAllTests();

    /**
     * 根据名称查询测试管理集合(只提取ID 和 Name)
     *
     * @param testName 名称
     */
    public List<Test> findTestsWithIdNameByName(String testName);

    /**
     * 查询所有测试管理集合(只提取ID 和 Name)
     *
     */
    public List<Test> findAllTestsWithIdName();

    /**
     * 根据ID查询指定的测试管理(只提取ID 和 Name)
     *
     * @param testId Id
     */
    public Test findTestsWithIdNameById(Long testId);

    /**
     * 根据ID查询指定的测试管理
     *
     * @param testId Id
     */
    public Test findTest(Long testId);

    /**
     * 根据ID查询指定的测试管理(包含外键)
     *
     * @param testId Id
     */
    public Test findTestWithForeignName(Long testId);

    /**
     * 新增测试管理
     *
     * @param test 实体对象
     */
    public Test saveTest(Test test);

    /**
     * 更新测试管理
     *
     * @param test 实体对象
     */
    public Test updateTest(Test test);

    /**
     * 根据ID删除测试管理
     *
     * @param testId ID
     */
    public void deleteTest(Long testId);
}
