package com.cisdi.info.simple.service.excelTest.impl;

import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.service.excel.ExcelExportService;
import com.cisdi.info.simple.service.excel.ExcelImportService;
import com.cisdi.info.simple.service.excelTest.ExcelExportTestService;
import com.cisdi.info.simple.service.permission.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:chengbg
 * @date:2018/10/22
 */
@Service
public class ExcelExportTestServiceBean implements ExcelExportTestService {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private ExcelImportService excelImportService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public void excelImport(MultipartFile file) {
        try {
            //调用excel导入服务，解析文件内容，返回list集合，其中每个map为一行的数据
            List<Map<String, Object>> list = excelImportService.excelImport(file);
            //需要自己处理对应的数据，如存入数据库
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                String name = (String) map.get("姓名");
                String age = (String) map.get("年龄");
                String sex = (String) map.get("性别");
                String love = (String) map.get("爱好");
                System.out.println(name + age + sex + love);
            }
        } catch (Exception e) {
            logger.error("excel导入失败");
        }
    }

    @Override
    public void excelExport() {
        //需要导出的数据
        List<Permission> list = permissionService.findAllPermissions();
        //excel表格头，必须和对应实体的属性对应，如name，在实体中必须存在name属性
        Map<String, String> header = new HashMap<>();
        header.put("编码", "code");
        header.put("名称", "name");
        header.put("全名", "fullName");
        header.put("模块编码", "moduleCode");
        try {
            //导出excel，需要传入对应的需要导出类的class
            excelExportService.excelExport(list, header, Permission.class);
        } catch (Exception e) {
            logger.error("excel导出失败");
        }
    }
}
