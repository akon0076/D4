package com.cisdi.info.simple.config;


import com.cisdi.info.simple.DDDException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@PropertySource(value = "classpath:config.yml", encoding = "UTF-8")
@ConfigurationProperties
public class Config {

    public Config() {
    }

    public static String uploadFileAddress;
    public static String workspapce;
    public static String serverPath;
    public static String uiPath;
    public static String codeTablesFile;
    public static String moduleFile;
    public static String velocityTemplate;
    public static String exceptionLogPath;
    public static String staticFilePath;

    public static String applicationName;
    public static String applicationCode;
    public static String shortCode;
    public static String basePackage;

    public static String charset_UTF8;
    public static String charset_ISO;
    public static String dataBase;
    public static String serverId;
    public static String logPath;
    public static boolean isDeveloping;
    public static Set<String> whiteURLs;

    public void setUploadFileAddress(String uploadFileAddress) {
        Config.uploadFileAddress = uploadFileAddress;
    }

    public String getUploadFileAddress() {
        return uploadFileAddress;
    }

    public String getWorkspapce() {
        return workspapce;
    }

    public void setWorkspapce(String workspapce) {
        Config.workspapce = workspapce;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        Config.serverPath = serverPath;
    }

    public String getUiPath() {
        return uiPath;
    }

    public void setUiPath(String uiPath) {
        Config.uiPath = uiPath;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        Config.applicationName = applicationName;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        Config.applicationCode = applicationCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        Config.shortCode = shortCode;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        Config.basePackage = basePackage;
    }

    public String getCharset_UTF8() {
        return charset_UTF8;
    }

    public void setCharset_UTF8(String charset_UTF8) {
        Config.charset_UTF8 = charset_UTF8;
    }

    public String getCharset_ISO() {
        return charset_ISO;
    }

    public void setCharset_ISO(String charset_ISO) {
        Config.charset_ISO = charset_ISO;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        Config.dataBase = dataBase;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        Config.serverId = serverId;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        Config.logPath = logPath;
    }

    public boolean getIsDeveloping() {
        return isDeveloping;
    }

    public void setIsDeveloping(boolean isDeveloping) {
        Config.isDeveloping = isDeveloping;
    }

    public String getVelocityTemplate() {
        return velocityTemplate;
    }

    public void setVelocityTemplate(String velocityTemplate) {
        Config.velocityTemplate = velocityTemplate;
    }

    public boolean isIsDeveloping() {
        return isDeveloping;
    }

    public String getCodeTablesFile() {
        return codeTablesFile;
    }

    public void setCodeTablesFile(String codeTablesFile) {
        Config.codeTablesFile = codeTablesFile;
    }

    public String getModuleFile() {
        return moduleFile;
    }

    public void setModuleFile(String moduleFile) {
        Config.moduleFile = moduleFile;
    }

    public String getWhiteURLs() {
        return whiteURLs == null ? "" : whiteURLs.toString();
    }

    public void setWhiteURLs(String whiteURLs) {
        whiteURLs = StringUtils.trimToEmpty(whiteURLs);
        String[] temp = StringUtils.split(whiteURLs, ",");

        Config.whiteURLs = new HashSet<String>(Arrays.asList(temp));

    }

    public String getExceptionLogPath() {
        return exceptionLogPath;
    }

    public void setExceptionLogPath(String exceptionLogPath) {
        Config.exceptionLogPath = exceptionLogPath;
        DDDException.setLogPath(Config.exceptionLogPath);
    }

    public String getStaticFilePath() {
        return staticFilePath;
    }

    public void setStaticFilePath(String staticFilePath) {
        Config.staticFilePath = staticFilePath;
    }

}

