package com.cisdi.info.simple.dao.log;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.log.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "logDao")
public interface LogDao {

    /**
    * 根据分页参数查询日志集合
    *
    * @param pageDTO 分页条件
    */
    public List<Log> findLogs(PageDTO pageDTO);

    /**
    * 查询全部日志集合
    *
    */
    public List<Log> findAllLogs();

    /**
    * 查询所有日志集合(只提取ID 和 Name)
    *
    */
    public List<Log> findAllLogsWithIdName();

    /**
    * 根据名称查询日志集合(只提取ID 和 Name)
    *
    * @param logName 名称
    */
    public List<Log> findLogsWithIdNameByName(@Param("logName") String logName);

    /**
    * 根据ID查询指定的日志(只提取ID 和 Name)
    *
    * @param logId Id
    */
    public Log findLogsWithIdNameById(@Param(" logId") Long logId);

    /**
    * 根据分页参数查询日志集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findLogTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的日志
    *
    * @param logId Id
    */
    public Log findLog(@Param("logId") Long logId);

    /**
    * 根据ID查询指定的日志(包含外键)
    *
    * @param logId Id
    */
    public Log findLogWithForeignName(@Param("logId") Long logId);

    /**
    * 新增日志
    *
    * @param log 实体对象
    */
    public Integer saveLog(Log log);

    /**
    * 更新日志
    *
    * @param log 实体对象
    */
    public Integer updateLog(Log log);

    /**
    * 根据ID删除日志
    *
    * @param logId ID
    */
    public Integer deleteLog(@Param("logId") Long logId);
}
