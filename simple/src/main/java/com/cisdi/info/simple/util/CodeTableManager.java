package com.cisdi.info.simple.util;

import com.cisdi.info.simple.entity.system.CodeTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CodeTableManager {
    private static Map<String,List<CodeTable>> codeTables;

    public static  List<CodeTable> findCodeTablesByType(String type)
    {
        loadCodeTables();
        return codeTables.get(type);
    }
    public static void loadCodeTables()
    {
        loadCodeTables(Config.codeTablesFile);
    }

    public static void loadCodeTables(String file)
    {
        if(codeTables != null) return ;

        Gson gson = new Gson();
        String json = null;
        try {
            json = FileUtils.readFileToString(new File(file), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        codeTables = gson.fromJson(json, new TypeToken<Map<String,List<CodeTable>>>() {}.getType());
    }
    public static Map<String,List<CodeTable>> getCodeTables()
    {
        loadCodeTables();
        return codeTables;
    }

    public static  void addCodeTables(String type, List<CodeTable> newCodeTables)
    {
        loadCodeTables();

        if(getCodeTables().containsKey(type))
        {
            Set<CodeTable> oldCodeTables = new HashSet<CodeTable>();
            oldCodeTables.addAll(getCodeTables().get(type));
            oldCodeTables.addAll(newCodeTables);
            List<CodeTable>  mergesCodeTables = new ArrayList<CodeTable>();
            mergesCodeTables.addAll(oldCodeTables);
            getCodeTables().put(type, mergesCodeTables);
        }
        else
        {
            getCodeTables().put(type, newCodeTables);
        }
        saveCodeTables(Config.codeTablesFile);
    }
    public static  void removeCodeTables(String type,String name)
    {

        if(getCodeTables().containsKey(type))
        {
            getCodeTables().get(type).remove(name);
        }
        saveCodeTables(Config.codeTablesFile);
    }
    public static  void removeCodeTables(String type)
    {
        getCodeTables().remove(type);

        saveCodeTables(Config.codeTablesFile);
    }
    public static void saveCodeTables(String file)
    {
       Gson gson = createGson();
        String json = gson.toJson(getCodeTables());
        try {
            FileUtils.writeStringToFile(new File(file), json, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static Gson createGson()
    {
        return new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
            .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
            .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
            .setPrettyPrinting() //对结果进行格式化，增加换行
            .disableHtmlEscaping().create(); //防止特殊字符出现乱码
    }
    public static  void main(String[] args)
    {
//        codeTables = new HashMap<String, List<CodeTable>>();
//        codeTables.put("ddd", new ArrayList<CodeTable>());
//
//        codeTables.get("ddd").add(new CodeTable("ddd","ddd1"));
//        codeTables.get("ddd").add(new CodeTable("ddd","ddd2"));
//        codeTables.get("ddd").add(new CodeTable("ddd","ddd3"));
//        CodeTable codeTable =new CodeTable("ddd","ddd4");
//        codeTables.get("ddd").add(codeTable);
//        new CodeTable("aaa1",codeTable);
//        new CodeTable("aaa2",codeTable);
//        codeTable =new CodeTable("aaa3",codeTable);
//        new CodeTable("ccc1",codeTable);
//        new CodeTable("ccc2",codeTable);
//
//        saveCodeTables("F:\\IDEA\\workspace\\xc-project\\src\\main\\resources\\codetables.json");
//        loadCodeTables("F:\\IDEA\\workspace\\xc-project\\src\\main\\resources\\codetables.json");
//
//        System.out.println(codeTables.toString());
    }
}
