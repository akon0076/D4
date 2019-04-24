package com.cisdi.info.simple.service.log.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.log.LogDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.log.Log;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.log.LogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceBean extends BaseService implements LogService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private LogDao logDao;

    /**
     * 根据分页参数查询日志集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findLogs(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<Log> logDTOS = this.logDao.findLogs(pageDTO);
        Long totalCount = this.logDao.findLogTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(logDTOS);

        return pageResultDTO;
    }

    /**
     * 查询全部日志集合
     */
    public List<Log> findAllLogs() {
        return this.logDao.findAllLogs();
    }

    /**
     * 查询所有日志集合(只提取ID 和 Name)
     */
    public List<Log> findAllLogsWithIdName() {
        return this.logDao.findAllLogsWithIdName();
    }

    /**
     * 根据名称查询日志集合(只提取ID 和 Name)
     *
     * @param logName 名称
     */
    public List<Log> findLogsWithIdNameByName(String logName) {
        return this.logDao.findLogsWithIdNameByName(logName);
    }

    /**
     * 根据ID查询指定的日志(只提取ID 和 Name)
     *
     * @param logId Id
     */
    public Log findLogsWithIdNameById(Long logId) {
        return this.logDao.findLogsWithIdNameById(logId);
    }

    /**
     * 根据ID查询指定的日志
     *
     * @param logId Id
     */
    public Log findLog(Long logId) {
        return this.logDao.findLog(logId);
    }

    /**
     * 根据ID查询指定的日志(包含外键)
     *
     * @param logId Id
     */
    public Log findLogWithForeignName(Long logId) {
        return this.logDao.findLogWithForeignName(logId);
    }

    /**
     * 新增日志
     *
     * @param log 实体对象
     */
    public Log saveLog(Log log) {
        //TODO:请在此校验参数的合法性
        this.setSavePulicColumns(log);
        Integer rows = this.logDao.saveLog(log);
        if (rows != 1) {
            String error = "新增保存日志出错，数据库应该返回1,但返回了 " + rows;
            throw new DDDException(error);
        }
        return log;
    }

    /**
     * 更新日志
     *
     * @param log 实体对象
     */
    public Log updateLog(Log log) {
        //TODO:请在此校验参数的合法性
        this.setUpdatePulicColumns(log);
        Integer rows = this.logDao.updateLog(log);
        if (rows != 1) {
            String error = "修改保存日志出错，数据库应该返回1,但返回了 " + rows + ",数据可能被删除";
            throw new DDDException(error);
        }
        return log;
    }

    /**
     * 根据ID删除日志
     *
     * @param logId ID
     */
    public void deleteLog(Long logId) {
        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = this.checkForeignEntity(Log.class, logId);
        if (entityUsageMap != null && entityUsageMap.size() > 0) {
            StringBuilder errors = new StringBuilder();
            errors.append("计划删除的数据正在被以下数引用\n");
            for (EntityUsage entityUsage : entityUsageMap.values()) {
                errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
                for (Map.Entry<Long, String> entry : entityUsage.getUsageIdNames().entrySet()) {
                    errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
                }
            }
            errors.append("，不能删除，请检查处理后再删除");
            throw new DDDException(errors.toString());
        }

        Integer rows = this.logDao.deleteLog(logId);
        if (rows != 1) {
            String error = "删除日志出错，数据库应该返回1,但返回了 " + rows + ",数据可能已经被删除";
            throw new DDDException(error);
        }
    }
}
