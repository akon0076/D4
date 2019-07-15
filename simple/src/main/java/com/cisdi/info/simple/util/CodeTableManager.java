package com.cisdi.info.simple.util;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.config.Config;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.*;

public class CodeTableManager {
    private static Map<String, CodeTable> codeTables;
    private static final String CODE_TYPE = "码表类型";
    private static final String ORG_TYPE = "组织单位";
    private static final String CODE_OPTION = "码表选项";

    /**
     * 获取全部码表
     *
     * @return
     */
    public static List<CodeTable> findAllCodeTables() {
        loadCodeTables();
        List<CodeTable> list = new ArrayList();
        Collection<CodeTable> tables = codeTables.values();
        if (tables != null && tables.size() != 0) {
            list.addAll(tables);
        }
        return list;
    }

    /**
     * 根据code找到码表
     *
     * @param code
     * @return
     */
    public static CodeTable findCodeTablesByCode(String code) {
        List<CodeTable> codeTables = findAllCodeTables();
        for (CodeTable codeTable : codeTables) {
            if (CODE_TYPE.equals(codeTable.getCodeType()) && code.equals(codeTable.getCode())) {
                return codeTable;
            }
        }
        return null;
    }

    /**
     * 根据唯一标识找到码表
     *
     * @param uuid
     * @return
     */
    public static CodeTable findCodeTablesByUUID(String uuid) {
        loadCodeTables();
        return codeTables.get(uuid);
    }

    /**
     * 从文件中加载码表
     */
    public static void loadCodeTables() {
        loadCodeTables(Config.codeTablesFile);
    }

    public static void loadCodeTables(String file) {
        Gson gson = new Gson();
        String json = FileLockUtils.readFileToString(new File(file), "UTF-8");
        codeTables = gson.fromJson(json, new TypeToken<Map<String, CodeTable>>() {
        }.getType());
    }

    public static void refresh() {
        Gson gson = new Gson();
        String json = FileLockUtils.readFileToString(new File(Config.codeTablesFile), "UTF-8");
        codeTables = gson.fromJson(json, new TypeToken<Map<String, CodeTable>>() {
        }.getType());
    }

    public static Map<String, CodeTable> getCodeTables() {
        if (codeTables == null) {
            loadCodeTables();
        }
        return codeTables;
    }

    /**
     * 删除码表
     *
     * @param uuid
     */
    public static void removeCodeTables(String uuid) {
        loadCodeTables();
        Map<String, CodeTable> codeTables = CodeTableManager.getCodeTables();
        if (codeTables.remove(uuid) == null) {
            throw new DDDException("该码表不存在，可能已经被删除或更改");
        }
        //删除子码表
        deleteChildrenCodeTable(codeTables, uuid);
        saveCodeTables();
    }

    /**
     * 删除子码表
     *
     * @param codeTables
     * @param uuid
     */
    private static void deleteChildrenCodeTable(Map<String, CodeTable> codeTables, String uuid) {
        Iterator<CodeTable> iterator = codeTables.values().iterator();
        List<CodeTable> list = new ArrayList<>();
        while (iterator.hasNext()) {
            CodeTable codeTable = iterator.next();
            if (uuid.equals(codeTable.getParentUUID())) {
                //删除子码表
                iterator.remove();
                list.add(codeTable);
            }
        }
        //继续处理子码表
        for (CodeTable codeTable : list) {
            deleteChildrenCodeTable(codeTables, codeTable.getUuid());
        }
    }

    public static void saveCodeTables(String file) {
        Gson gson = createGson();
        String json = gson.toJson(getCodeTables());
        FileLockUtils.writeStringToFile(new File(file), json, "UTF-8");

    }

    /**
     * 新增码表
     *
     * @param codeTable
     */
    public static void addCodeTable(CodeTable codeTable) {
        refresh();
        getCodeTables().put(codeTable.getUuid(), codeTable);
        saveCodeTables();
    }

    /**
     * 修改码表
     *
     * @param codeTable
     */
    public static void updateCodeTable(CodeTable codeTable) {
        refresh();
        getCodeTables().put(codeTable.getUuid(), codeTable);
        saveCodeTables();
    }

    /**
     * 保存码表到文件
     */
    public static void saveCodeTables() {
        Gson gson = createGson();
        String json = gson.toJson(CodeTableManager.codeTables);
        FileLockUtils.writeStringToFile(new File(Config.codeTablesFile), json, "UTF-8");
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
                .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
                .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
                .setPrettyPrinting() //对结果进行格式化，增加换行
                .disableHtmlEscaping().create(); //防止特殊字符出现乱码
    }

    public static void main(String[] args) {
        loadCodeTables("E:\\SpringBoot\\workplace\\D4\\simple\\src\\main\\resources\\codetables.json");
        List list = new ArrayList(findAllCodeTables());

    }
}
