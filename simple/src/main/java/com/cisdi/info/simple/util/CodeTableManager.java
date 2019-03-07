package com.cisdi.info.simple.util;

import com.cisdi.info.simple.config.Config;
import com.cisdi.info.simple.entity.system.CodeTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.*;

public class CodeTableManager {
    private static Map<String,List<CodeTable>> codeTables;

    public static List<CodeTable> findAllCodeTables(){
        List returnedList = new ArrayList();
        Collection<List<CodeTable>> list=codeTables.values();
        Iterator iterator=list.iterator();
        while (iterator.hasNext()) {
            List eachList = (List) iterator.next();
            for(int i=0;i<eachList.size();i++) {
                returnedList.add(eachList.get(i));
            }
        }
        return returnedList;
    }
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
        json = FileLockUtils.readFileToString(new File(file), "UTF-8");
        codeTables = gson.fromJson(json, new TypeToken<Map<String,List<CodeTable>>>() {}.getType());
    }
    public static Map<String,List<CodeTable>> getCodeTables()
    {
        loadCodeTables();
        return codeTables;
    }
   public static void updateCodeTable(CodeTable newCodeTables){
       loadCodeTables();
       String type = newCodeTables.getCodeType();
       if(getCodeTables().containsKey(type))
       {
           List<CodeTable> list = findCodeTablesByType(type);
           for(int i=0;i<list.size();i++) {
               if (newCodeTables.getFullname().equals(list.get(i).getFullname())) {
                   list.remove(i);
                   break;
               }
           }
           newCodeTables.setFullname(newCodeTables.getCodeType()+"."+newCodeTables.getName());
           list.add(newCodeTables);
       }
       else
       {
           List<CodeTable> list = new ArrayList<>();
           list.add(newCodeTables);
           newCodeTables.setFullname(newCodeTables.getCodeType()+"."+newCodeTables.getName());
           getCodeTables().put(type, list);
       }
       saveCodeTables(Config.codeTablesFile);

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
    public static  void removeCodeTables(String type,String codeTableId)
    {

        if(getCodeTables().containsKey(type))
        {
          List<CodeTable> list=  getCodeTables().get(type);
            for (int i=0;i<list.size();i++) {
                if (codeTableId.equals(list.get(i).getFullname())){
                    list.remove(i);
                    break;
                }

            }
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
        FileLockUtils.writeStringToFile(new File(file), json, "UTF-8");

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

    public static void main(String[] args) {
        loadCodeTables("E:\\SpringBoot\\workplace\\D4\\simple\\src\\main\\resources\\codetables.json");
        List list = new ArrayList(  findAllCodeTables() );

    }
}
