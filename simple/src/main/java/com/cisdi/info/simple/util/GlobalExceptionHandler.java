package com.cisdi.info.simple.util;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.entity.log.Log;
import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.service.log.LogService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private LogService logService;

    /**
     * 所有异常报错
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public String allExceptionHandler(HttpServletRequest request, HttpServletResponse response,
                                      Exception exception) throws Exception {
        exception.printStackTrace();
        logger.error(exception.getMessage(), exception);

        ResponseEntry responseEntry = null;
        if (exception instanceof DDDException) {
            DDDException dddException = (DDDException) exception;
            responseEntry = new ResponseEntry(dddException.getCode(), dddException.getMessage(), dddException.getExtendedData());
            response.setStatus(590);
        } else {
            DDDException dddException = new DDDException(exception);
            responseEntry = new ResponseEntry(dddException.getCode(), dddException.getMessage(), dddException.getExtendedData());
            response.setStatus(590);
        }
        String json = gson.toJson(responseEntry);

        return json;
    }

    /**
     * 记录异常日志
     *
     * @param request
     */
    public void saveLog(HttpServletRequest request, Exception exception) {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        if (loginUser != null) {
            String userName = loginUser.getUserName();
            Operator operator = loginUser.getLoginOperator();
            String moduleCode = url.substring(1, url.lastIndexOf("/"));
            Module module = ModuleManager.findModuleByCode(moduleCode);
            if (module != null) {
                String moduleName = module.getName();
                String entity = moduleCode.substring(moduleCode.lastIndexOf("/") + 1);
                List<Permission> permissions = module.getPermissions();
                for (Permission permission : permissions) {
                    List<String> urls = permission.getUrls();
                    String operationContent = permission.getName();
                    for (String s : urls) {
                        if (s.equals(url)) {
                            String fullName = permission.getFullName();
                            int index = fullName.lastIndexOf(".");
                            String operationType = fullName.substring(index + 1);
                            Log log = new Log();
                            log.setIpAddress(ip);
                            log.setUrl(url);
                            log.setLogDate(new Date());
                            log.setOperator(operator);
                            log.setOperationType(operationType);
                            log.setModule(moduleName);
                            log.setEntity(entity);
                            log.setOperationContent(userName + operationContent + "，异常信息：" + exception.getMessage());
                            log.setOperatorId(operator.getEId());
                            log.setLogType("异常日志");
                            logService.saveLog(log);
                        }
                    }
                }
            }
        }
    }

    private Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
            .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
            .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
            .setPrettyPrinting() //对结果进行格式化，增加换行
            .disableHtmlEscaping().create(); //防止特殊字符出现乱码

    private class ResponseEntry {
        private static final long serialVersionUID = 1L;
        private String code;
        private String message;
        private Map<String, Object> extendedData;

        public ResponseEntry(String code, String message, Map<String, Object> extendedData) {
            this.code = code;
            this.message = message;
            this.extendedData = extendedData;
        }

        public ResponseEntry(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Map<String, Object> getExtendedData() {
            return extendedData;
        }

        public void setExtendedData(Map<String, Object> extendedData) {
            this.extendedData = extendedData;
        }
    }
}