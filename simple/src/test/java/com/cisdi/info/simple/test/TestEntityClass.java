package com.cisdi.info.simple.test;


import com.cisdi.info.simple.Application;
import com.cisdi.info.simple.dao.foreignKey.ForeignKeyExcuteDao;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.base.ColumnInfo;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.service.attachment.AttachmentService;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.util.D4Util;
import com.cisdi.info.simple.util.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestEntityClass {

/*    @Autowired
    private EntityManager entityManager;*/

    @Autowired
    private ForeignKeyExcuteDao foreignKeyExcuteDao;

    @Autowired
    private BaseService baseService;

    @Autowired
    AttachmentService attachmentService;
    @Test
    public void testPath(){
        URL path= Thread.currentThread().getContextClassLoader().getResource("");
        System.out.println(path.getPath());
    }
    @Test
    public void testZipFile(){

        try {
            String[] files= new String[]{"\\20180822\\1bftmcavo65_265_220 (1)2018082220522116.jpg","\\20180822\\1529499953475_ec-mh_dev201808221703475198.sql"};
            String[] names=new String[]{"4654.jpg","111.sql"};
             attachmentService.getZipFile(files,names,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


@Test
public void testEntityManager(){
  /*  assertNotNull(entityManager);*/
    List<ColumnInfo> s=EntityManager.getEntityReferenceClasses(Organization.class);
    System.out.println("共有"+s.size()+"个实体与"+Organization.class.getSimpleName()+"有关");
    for(ColumnInfo  e:s){
        System.out.println(e.getForeignEntity()+"=>"+e.getFieldName());
    }
    for(int i=0;i<s.size();i++) {
        HashMap<String,String> maps=new HashMap<String,String>();
        maps.put("tableName",s.get(i).getForeignEntity());
        maps.put("eid","1");
        maps.put("fieldName",s.get(i).getFieldName());
     List< HashMap<String,String>>  oneMap=    foreignKeyExcuteDao.findAllForeignKeys(maps);
     System.out.println(oneMap);
    }
}





@Test
public void checkForeignEntity()
{
    Map<Class<? extends BaseEntity>,BaseService.EntityUsage> entityUsageMap = new HashMap<Class<? extends BaseEntity>, BaseService.EntityUsage>();
     List<ColumnInfo> s=EntityManager.getEntityReferenceClasses(Organization.class);
        System.out.println("共有"+s.size()+"个实体与"+Organization.class.getSimpleName()+"有关");
        for(ColumnInfo  e:s){
            System.out.println(e.getForeignEntity()+"=>"+e.getFieldName());
        }
        for(int i=0;i<s.size();i++) {
            HashMap<String,String> maps=new HashMap<String,String>();
            Map<Long,String>  idAndNames=new HashMap<Long,String>();
            BaseService.EntityUsage eu=new BaseService.EntityUsage();
            maps.put("tableName",s.get(i).getForeignEntity());
            maps.put("eid","1");
            maps.put("fieldName",s.get(i).getFieldName());
            List< HashMap<String,String>>  oneOfEntitymaps=    foreignKeyExcuteDao.findAllForeignKeys(maps);

             if(oneOfEntitymaps.size()>0) {
                 eu.setEntityLabel(s.get(i).getLabel());
                 for (HashMap<String, String> oneMap : oneOfEntitymaps) {
                     idAndNames.put(Long.valueOf(String.valueOf(oneMap.get("eid"))), oneMap.get("name")+"");
                 }
                 eu.setUsageIdNames(idAndNames);
                 entityUsageMap.put((Class<? extends BaseEntity>) s.get(i).getFieldType(),eu);
             }
        }


    if(entityUsageMap != null && entityUsageMap.size() >0)
    {
        StringBuilder errors = new StringBuilder();
        errors.append("计划删除的数据正在被以下数引用\n");
        for(BaseService.EntityUsage entityUsage : entityUsageMap.values())
        {
            errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
            for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() )
            {
                errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
            }
        }
        errors.append("，不能删除，请检查处理后再删除");
    }












}

}
