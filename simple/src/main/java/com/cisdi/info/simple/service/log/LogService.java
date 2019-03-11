package com.cisdi.info.simple.service.log;

import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.log.Log;
import com.cisdi.info.simple.dto.base.PageDTO;

import java.util.List;

public interface LogService {
    /**
     * 根据分页参数查询日志集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findLogs(PageDTO pageDTO);

    /**
     * 查询全部日志集合
     *
     */
    public List<Log> findAllLogs();

    /**
     * 根据名称查询日志集合(只提取ID 和 Name)
     *
     * @param logName 名称
     */
    public List<Log> findLogsWithIdNameByName(String logName);

    /**
     * 查询所有日志集合(只提取ID 和 Name)
     *
     */
    public List<Log> findAllLogsWithIdName();

    /**
     * 根据ID查询指定的日志(只提取ID 和 Name)
     *
     * @param logId Id
     */
    public Log findLogsWithIdNameById(Long logId);

    /**
     * 根据ID查询指定的日志
     *
     * @param logId Id
     */
    public Log findLog(Long logId);

    /**
     * 根据ID查询指定的日志(包含外键)
     *
     * @param logId Id
     */
    public Log findLogWithForeignName(Long logId);

    /**
     * 新增日志
     *
     * @param log 实体对象
     */
    public Log saveLog(Log log);

    /**
     * 更新日志
     *
     * @param log 实体对象
     */
    public Log updateLog(Log log);

    /**
     * 根据ID删除日志
     *
     * @param logId ID
     */
    public void deleteLog(Long logId);
}
