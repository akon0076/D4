package com.cisdi.info.simple.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cisdi.info.simple.DDDException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.util.ErrorHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 所有异常报错
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value=Exception.class)  
    public String allExceptionHandler(HttpServletRequest request,HttpServletResponse response,
            Exception exception) throws Exception  
    {
        DDDException.logInFile(exception);

        ResponseEntry responseEntry = null;
        if(exception instanceof  DDDException)
        {
            DDDException dddException = (DDDException)exception;
            responseEntry = new ResponseEntry(dddException.getCode(),dddException.getMessage(),dddException.getExtendedData());
            response.setStatus(590);
        }
        else
        {
//            responseEntry = new ResponseEntry("exception",exception.getMessage());
//            response.setStatus(500);
            DDDException dddException = new DDDException(exception);
            responseEntry = new ResponseEntry(dddException.getCode(),dddException.getMessage(),dddException.getExtendedData());
            response.setStatus(590);
        }
        String json = gson.toJson(responseEntry);

        return json;
    }
    private Gson gson  = new GsonBuilder()
                .enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
                .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
                .setPrettyPrinting() //对结果进行格式化，增加换行
                .disableHtmlEscaping().create(); //防止特殊字符出现乱码

    private class ResponseEntry {
        private static final long serialVersionUID = 1L;
        private String code;
        private String message;
        private Map<String, Object> extendedData ;

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