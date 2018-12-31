package com.cisdi.info.simple.util;


import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.entity.base.Condition;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class D4Util {

    public static  String convertCamelCaseName2UnderScore(String name)
    {
        return StringUtils.lowerCase(StringUtils.join( StringUtils.splitByCharacterTypeCamelCase(name),"_"));
    }

    public static Map<String,String> createMap()
    {

        Map aaa = null;
//        aaa.pu
        return new LinkedHashMap<String,String>();
    }

    public static String addMapItem(Map<String,String> map,String key, String value)
    {
//        System.out.print(key);
//        System.out.print(value);
//        System.out.print(map.toString());
        map.put(key, value);
        return "";
    }
    public  static String assembleSql(String columnName,String content){
        if(columnName==null||columnName==""||content==null||content==""){
            return  " (t0.eid like '%')";
        }
        String [] items=content.split("\\s+");//or
        String [] anditems=content.split("\\s+and\\s");//and
        String sql="";
        String  hump="";
        hump= humpToUnderline(columnName);
    if(anditems.length>1)
    {
        sql= "(";
        for(int i=0;i<anditems.length;i++){
            if(i==anditems.length-1){
                sql+="t0."+hump+"  LIKE '%"+anditems[i]+"%')";
            }
            else{
                sql+="t0."+hump+"  LIKE '%"+anditems[i]+"%'  AND ";
            }

        }
    }
    else{
        if( items.length==0||items.length==1&&items[0]==""||items==null){
            sql="("+"t0."+hump+"  LIKE '%' )";
        }
        else{
            sql= "(";
            for(int i=0;i<items.length;i++){
                if(i==items.length-1){
                    sql+="t0."+hump+"  LIKE '%"+items[i]+"%')";
                }
                else{
                    sql+="t0."+hump+"  LIKE '%"+items[i]+"%'  OR ";
                }

            }
        }
        }

        return sql;
    }
    /***
     * 驼峰命名转为下划线命名
     *
     * @param para
     *        驼峰命名的字符串
     */

    public static String humpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        for(int i=0;i<para.length();i++){
            if(Character.isUpperCase(para.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toUpperCase();
    }

    /***
     * 下划线命名转为驼峰命名
     *
     * @param para
     *        下划线命名的字符串
     */

    public static String underlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
    //通过传入FileInputStream数组,返回压缩文件
    /*
    * @parms fileAddress 数据库的 attachment_addr字段数组(必填)
    * @parms fileNames 数据库的attachment_real_name 字段数组(必填)
    * @parms compressFileName要下载的压缩文件名，如果不填默认为当前时间戳
    * @parms fileType 压缩文件的后缀,不填默认为zip
    */
   /* public static OutputStream getZipFile(String[] fileAddress,String[] fileNames,String compressFileName) throws IOException {
        if(fileAddress!=null&&fileAddress.length>0){

            if(compressFileName==null||compressFileName.equals("")){
                compressFileName=System.currentTimeMillis()+""+(int)(Math.random()*10000);
            }
            File zipFile=new File(Config.uploadFileAddress+"\\"+compressFileName+".zip");
            //实例化 ZipOutputStream对象
            ZipOutputStream zipOutputStream=new ZipOutputStream(new FileOutputStream(zipFile));
            //创建ZipEntry对象
            ZipEntry zipEntry=null;
            FileInputStream fileInputStream=null;
            File[]  files=new File[fileAddress.length];
            for(int i=0;i<fileAddress.length;i++){
                String file=Config.uploadFileAddress+fileAddress[i];
                 files[i] =new File(file);
                if(!files[i].exists()){
                    throw new DDDException(fileNames[i]+"文件不存在请重新上传");
                }
                fileInputStream=new FileInputStream(files[i]);
             zipEntry=new ZipEntry(fileNames[i]);
             zipOutputStream.putNextEntry(zipEntry);
             int len;
             byte[] buffer=new byte[1024];
             while ((len=fileInputStream.read(buffer))>0){
                   zipOutputStream.write(buffer,0,len);
             }
            }



            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileInputStream.close();
            zipFile.delete();
        }
        else{
            throw new DDDException("输入流不能为空");
        }



        return null;
    }
*/

    /**
     * 处理数据方法
     * <p>批量将List<Object>类型的数据转换成List<Map<T>>的数据</p>
     * @param rows 行数据
     * @return rowList 返回处理的结果 List<Map<String ,String>> 其中是已经处理好的  对象->map
     * @author gjt
     */
    public static List<Map> objectToListMap(List<Object> rows ) {

        if (rows.size() < 0) {
            throw new DDDException("传入数据不能为空");
        }

        List<Map> rowList = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            Class clazz = rows.get(i).getClass();
            Field[] fields = clazz.getDeclaredFields();
            Map<String, Object> getFieldsNames = new HashMap<>();
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                try {
                    getFieldsNames.put(fields[j].getName() ,fields[j].get(rows.get(i)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rowList.add(getFieldsNames);
        }
        return rowList;
    }
}
