package com.cisdi.info.simple.service.base;


import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.foreignKey.ForeignKeyExcuteDao;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.base.ColumnInfo;
import com.cisdi.info.simple.entity.base.EntityClass;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.util.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseService {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private ForeignKeyExcuteDao foreignKeyExcuteDao;

    //设置session
    private boolean setSessionItem(String key, Object value) {
        if (request != null && request.getSession() != null) {
            request.getSession().setAttribute(key, value);
            return true;
        }
        return false;
    }

    //获取session
    private Object getSessionItem(String key) {
        if (request != null && request.getSession() != null) {
            return request.getSession().getAttribute(key);
        }
        return null;
    }

    //删除session
    private boolean removeSessionItem(String key) {
        if (request != null && request.getSession() != null) {
            request.getSession().removeAttribute(key);
        }
        return true;
    }


    //设置登录用户
    public void setLoginUser(LoginUser loginUser) {

        if (loginUser.getLoginEmployee() != null) {
            loginUser.getLoginEmployee().getEId();
        }
        this.setSessionItem("loginUser", loginUser);
    }

    //取消登录用户
    public void clearLoginUser() {
        this.removeSessionItem("loginUser");
        if (request != null && request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

    //获取登录用户
    public LoginUser getLoginUser() {
        try {
            Object loginUser = this.getSessionItem("loginUser");
            return loginUser == null ? null : (LoginUser) loginUser;
        } catch (IllegalStateException e) {
            //如果此方法不是由前台请求，而是在启动时，或者定时服务，就没有登陆信息
            return null;
        }
    }

    //获取当前登录用户的单位
    public Organization getCurrentLoginOrganization() {
        LoginUser loginUser = this.getLoginUser();
        if (loginUser == null) {
            throw new DDDException("当前用户未登录");
        }
        return loginUser.getCurrentOrganization();
    }

    //获取当前登录用户（操作员）
    public Operator getCurrentLoginOperator() {
        LoginUser loginUser = this.getLoginUser();
        if (loginUser == null) {
            throw new DDDException("当前用户未登录");
        }
        return loginUser.getLoginOperator();
    }

    //获取当前登录用户（具体人员）
    public Employee getCurrentLoginEmployee() {
        LoginUser loginUser = this.getLoginUser();
        if (loginUser.getLoginEmployee() == null) {
            throw new DDDException("当前用户未登录");
        }
        return loginUser.getLoginEmployee();
    }

    //保存实体是设置公共字段
    public void setSavePulicColumns(BaseEntity entity) {
        if (this.getCurrentLoginOperator() == null) {
            throw new DDDException("当前用户未登录");
        }
        entity.setCreateId(this.getCurrentLoginOperator().getEId());
        entity.setUpdateId(this.getCurrentLoginOperator().getEId());
        entity.setCreateDatetime(new Date());
        entity.setUpdateDatetime(new Date());
    }

    //更新实体是设置公共字段
    public void setUpdatePulicColumns(BaseEntity entity) {
        if (this.getCurrentLoginOperator() == null) {
            throw new DDDException("当前用户未登录");
        }
        entity.setUpdateId(this.getCurrentLoginOperator().getEId());
        entity.setUpdateDatetime(new Date());
    }

    public Map<Class<? extends BaseEntity>, EntityUsage> checkForeignEntity(Class<? extends BaseEntity> clazz, Long entityId) {
        //本方法所实现的功能是检查所传入的类删除时判断是否被其他实体所引用  作者:April
        Map<Class<? extends BaseEntity>, EntityUsage> entityUsageMap = new HashMap<Class<? extends BaseEntity>, EntityUsage>();
        List<ColumnInfo> s = EntityManager.getEntityReferenceClasses(clazz);//与传入类相关的所有实体列表
        System.out.println("共有" + s.size() + "个实体与" + clazz.getSimpleName() + "有关");
        for (ColumnInfo e : s) {
            System.out.println(e.getForeignEntity() + "=>" + e.getFieldName()); //输出打印
        }
        for (int i = 0; i < s.size(); i++) {
            HashMap<String, String> maps = new HashMap<String, String>();
            Map<Long, String> idAndNames = new HashMap<Long, String>();
            EntityUsage eu = new EntityUsage();
            maps.put("tableName", s.get(i).getForeignEntity());
            maps.put("eid", entityId + "");
            maps.put("fieldName", s.get(i).getFieldName());
            List<HashMap<String, String>> oneOfEntitymaps = foreignKeyExcuteDao.findAllForeignKeys(maps);

            if (oneOfEntitymaps.size() > 0) {
                eu.setEntityLabel(s.get(i).getLabel());
                for (HashMap<String, String> oneMap : oneOfEntitymaps) {
                    idAndNames.put(Long.valueOf(String.valueOf(oneMap.get("eid"))), oneMap.get("name") + "");
                }
                eu.setUsageIdNames(idAndNames);
                entityUsageMap.put((Class<? extends BaseEntity>) s.get(i).getFieldType(), eu);
            }
        }
        return entityUsageMap;
    }

    public static class EntityUsage {
        private Class<?> clazz;
        private String tableName;
        private String entityName;
        private String entityLabel;
        private EntityClass entityClass;
        private Map<Long, String> usageIdNames = new HashMap<Long, String>();

        public EntityUsage add(Long id, String name) {
            this.getUsageIdNames().put(id, name);
            return this;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public String getEntityLabel() {
            return entityLabel;
        }

        public void setEntityLabel(String entityLabel) {
            this.entityLabel = entityLabel;
        }

        public EntityClass getEntityClass() {
            return entityClass;
        }

        public void setEntityClass(EntityClass entityClass) {
            this.entityClass = entityClass;
        }

        public Map<Long, String> getUsageIdNames() {
            return usageIdNames;
        }

        public void setUsageIdNames(Map<Long, String> usageIdNames) {
            this.usageIdNames = usageIdNames;
        }
    }
}
