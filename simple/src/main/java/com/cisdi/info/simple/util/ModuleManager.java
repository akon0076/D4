package com.cisdi.info.simple.util;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.permission.RoleAndPermissionDao;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.entity.permission.RoleAndPermission;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ModuleManager {
    private static Logger logger = LogManager.getLogger();
    private static Map<String, Module> modules = null;
    private static Map<String, Set<Long>> urlRoles = new HashMap<String, Set<Long>>();

    public static Module findModule(String code) {
        return getModules().get(code);
    }

    public static void loadModules() {
        loadModules(Config.moduleFile);
    }

    public static void initialize() {
        RoleAndPermissionDao roleAndPermissionDao = (RoleAndPermissionDao) SpringContextUtils.getBean("roleAndPermissionDao");

        List<RoleAndPermission> roleAndPermissions = roleAndPermissionDao.findAllRoleAndPermissions();
        Map<String, List<String>> permissionMap = new HashMap<String, List<String>>();

        for (Module module : getModules().values()) {
            for (Permission permission : module.getPermissions()) {
                permissionMap.put(permission.getCode(), permission.getUrls());
            }
        }


        for (RoleAndPermission roleAndPermission : roleAndPermissions) {
            List<String> urls = permissionMap.get(roleAndPermission.getPermissionCode());
            if (urls == null) continue;
            for (String url : urls) {
                Set<Long> roles = urlRoles.get(url);
                if (roles == null) {
                    roles = new HashSet<Long>();
                    urlRoles.put(url, roles);
                }
                roles.add(roleAndPermission.getRoleId());
            }
        }
    }

    public static boolean hasPermission(String url, List<Long> roleIds) {
        for (Long roleId : roleIds) {
            if (hasPermission(url, roleId)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断访问者是否具有访问该路径的权限
     *
     * @param url    访问的url
     * @param roleId 用户id
     * @return
     */
    public static boolean hasPermission(String url, Long roleId) {
        Set<Long> roles = urlRoles.get(url);
        if (roles == null) {
            logger.error(String.format("非法请求 %s,可能是恶意攻击，请报告管理员", url));
            return false;
        }
        if (roles.contains(roleId)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 加载模块
     *
     * @param file 文件URL地址
     */
    public static void loadModules(String file) {
        if (modules != null) return;

        Gson gson = new Gson();
        String json = null;
        try {
            json = FileUtils.readFileToString(new File(file), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        modules = gson.fromJson(json, new TypeToken<Map<String, Module>>() {
        }.getType());
    }


    /**
     * 获取模块
     *
     * @return 模块列表
     */
    private static Map<String, Module> getModules() {
        loadModules();
        return modules;
    }

    /**
     * 新增模块
     *
     * @param code
     * @param newModule
     */
    public static void addModule(String code, Module newModule) {

        getModules().put(code, newModule);

        saveModules(Config.moduleFile);
    }

    public static void addModuleFromJson(String moduleJson) {
        Gson gson = new Gson();
        Map<String, Module> newModules = gson.fromJson(moduleJson, new TypeToken<Map<String, Module>>() {
        }.getType());

        for (Module module : newModules.values()) {
            addModule(module);
        }
        saveModules(Config.moduleFile);
    }

    public static void addModule(Module module) {
        String code = StringUtils.trimToEmpty(module.getCode());
        String[] codeParts = StringUtils.split(code, '/');
        if (codeParts.length != 3) {
            throw new DDDException("%s 是无效的模块编码，格式如：system_module_entity");
        }

        String systemCode = codeParts[0];
        Module systemModule = findModuleByCode(systemCode);
        if (systemModule == null) {
            systemModule = new Module(systemCode, systemCode, "", "", "", 1l, "是", module.getModuleType(), "");
            addModule(systemCode, systemModule);
        }

        String moduleCode = systemCode + "/" + codeParts[1];
        Module moduleModule = findModuleByCode(moduleCode);
        if (moduleModule == null) {
            moduleModule = new Module(moduleCode, module.getParentName(), "", "", "", 1l, module.getModuleType(), "是", "", systemModule);
            addModule(moduleCode, moduleModule);
        }
        addModule(code, module);
        saveModules(Config.moduleFile);
    }

//    public static void  addModule(String code, String name, String url, String route, String iconClass, Long displayIndex, String moduleType,  String routeParamsObj,String moduleName)
//    {
//        code = StringUtils.trimToEmpty(code);
//        String[] codeParts = StringUtils.split(code, '/');
//        if(codeParts.length != 3)
//        {
//            throw new DDDException("%s 是无效的模块编码，格式如：system_module_entity");
//        }
//
//        String systemCode =codeParts[0];
//        Module systemModule = findModuleByCode(systemCode);
//        if(systemModule == null)
//        {
//            systemModule = new Module(systemCode,systemCode,"","","",1l,"是",moduleType,"");
//            addModule(systemCode, systemModule);
//        }
//
//        String moduleCode =systemCode+"/"+codeParts[1];
//        Module moduleModule = findModuleByCode(moduleCode);
//        if(moduleModule == null)
//        {
//            moduleModule = new Module(moduleCode,moduleName,"","","",1l,moduleType,"是","",systemModule);
//            addModule(moduleCode, moduleModule);
//        }
//
//        String entityCode =moduleCode+"/"+codeParts[2];
//        Module entityModule = findModuleByCode(entityCode);
//        if(entityModule == null)
//        {
//            entityModule = new Module(entityCode,name,url,route,iconClass,displayIndex,moduleType,"是",routeParamsObj,moduleModule);
//            addModule(entityCode, entityModule);

//            String fulleName = null;
//            String baseUrl = "";
//            List<String> urls = null;
//DDDErrorController
//            fulleName = StringUtils.join(new String[]{systemModule.getName(),moduleModule.getName(),entityModule.getName(),"新增"},".");
//            urls = new ArrayList<String>();
//            urls.add("");
//
////            entityModule.getPermissions().add(new Permission(entityCode+"/Add","新增",fulleName,entityCode));
////
////            fulleName = StringUtils.join(new String[]{systemModule.getName(),moduleModule.getName(),entityModule.getName(),"编辑"},".");
////            entityModule.getPermissions().add(new Permission(entityCode+"/Edit","编辑",fulleName,entityCode));
////
////            fulleName = StringUtils.join(new String[]{systemModule.getName(),moduleModule.getName(),entityModule.getName(),"删除"},".");
////            entityModule.getPermissions().add(new Permission(entityCode+"/Delete","删除",fulleName,entityCode));
////
////            fulleName = StringUtils.join(new String[]{systemModule.getName(),moduleModule.getName(),entityModule.getName(),"查看"},".");
////            entityModule.getPermissions().add(new Permission(entityCode+"/View","查看",fulleName,entityCode));
//        }
//
//        saveModules(Config.moduleFile);
//    }

    /**
     * 根据编码找到指定模块
     *
     * @param code 模块编码
     * @return
     */
    public static Module findModuleByCode(String code) {
        return getModules().get(code);
    }

    /**
     * 删除模块
     *
     * @param code 模块编码
     */
    public static boolean removeModule(String code) {

        Module module = getModules().get(code);
        System.out.println(module.getCode());
        if (module != null) {
            // 如果是父级模块
            if (module.getParentCode().equals("")) {

                logger.error("请先删除:" + code + "对应的子模块");

                throw new DDDException("请先删除:" + code + "对应的子模块");
            } else {
                getModules().remove(code);
                saveModules(Config.moduleFile);
                return true;
            }
        } else {
            logger.error("找不到编码为::" + code + "对应的模块");

            throw new DDDException("找不到编码为:" + code + "对应的模块");
        }
    }

    /**
     * 编辑模块
     *
     * @param module 模块
     */
    public static void updateModule(Module module) {

        Module module1 = getModules().get(module.getCode());

        // 手动填充一些数据进入（因为直接更新会导致一些数据丢失） ---> start
        if (module.getName().split("_").length > 1) {
            module.setName(module.getName().split("_")[1]);
        } else {
            module.setName(module.getName().split("_")[0]);
        }
        module.setParentCode(module1.getParentCode());
        module.setParentName(module1.getParentName());
        // 手动填充一些数据进入（因为直接更新会导致一些数据丢失） ---> end

        // 替换权限点的FullName
        List<Permission> moduleList = module1.getPermissions();

        for (Permission permission : moduleList) {
            // 获取名称分割为数组
            String[] name = permission.getName().split("_");
            // 获取类全名
            String[] fullName = permission.getFullName().split("\\.");

            permission.setName(name[name.length - 1]);
            permission.setFullName(fullName[0] + "." + fullName[1] + "." + module.getName() + "." + fullName[3]);
        }
        // 把权限加入模块对应的位置
        module.setPermissions(moduleList);
        // 先删除模块
        getModules().remove(module.getCode());
        // 再当作新模块加入进去
        getModules().put(module.getCode(), module);

        saveModules(Config.moduleFile);
    }


    public static void saveModules(String file) {
        Gson gson = createGson();
        String json = gson.toJson(getModules());
        try {
            FileUtils.writeStringToFile(new File(file), json, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Collection<Module> getAllModules() {
        return getModules().values();
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
        modules = new HashMap<String, Module>();

        Module simpleModule = new Module("simple", "系统管理", "", "", "", 1l, "电脑模块", "是", "");
        modules.put(simpleModule.getCode(), simpleModule);

//        saveModules("F:\\IDEA\\workspace\\xc-project\\src\\main\\resources\\modules.json");
        loadModules("F:\\IDEA\\workspace\\xc-project\\src\\main\\resources\\modules.json");

        System.out.println(modules.toString());
    }


    /**
     * 权限修改
     * 大概方法就
     *
     * @param permission 需要修改的权限点
     */
    public static void updatePermission(Permission permission) {
        // 根据权限中的模块编码获取指定模块
        Module module = getModules().get(permission.getModuleCode());
        // 取出模块中所有的权限点
        List<Permission> modulePermissions = module.getPermissions();
        // 遍历权限点，找到满足条件的权限点，并删除。
        for (int i = 0; i < modulePermissions.size(); i++) {
            if (modulePermissions.get(i).getCode().equals(permission.getCode())) {
                // 将url设置进入即将修改的权限对象中
                permission.setUrls(modulePermissions.get(i).getUrls());
                modulePermissions.remove(i);
            }
        }
        // 将名字加入进去
        String[] name = permission.getName().split("_");
        permission.setName(name[name.length - 1]);
        // 加入修改过后的权限点进模块里
        modulePermissions.add(permission);
        // 将权限点加入模块
        module.setPermissions(modulePermissions);
        // 删除模块
        getModules().remove(module.getCode());
        // 再当作新模块加入进去
        getModules().put(module.getCode(), module);
        // 保存模块
        saveModules(Config.moduleFile);
    }


    /**
     * 移除权限点
     *
     * @param permission
     * @return
     */
    public static boolean removePermission(Permission permission) {
        // 根据权限中的模块编码获取指定模块
        Module module = getModules().get(permission.getModuleCode());
        if (module != null) {
            // 取出模块中所有的权限点
            List<Permission> modulePermissions = module.getPermissions();
            if (modulePermissions != null) {
                // 遍历权限点，找到满足条件的权限点，并删除。
                for (int i = 0; i < modulePermissions.size(); i++) {
                    if (modulePermissions.get(i).getCode().equals(permission.getCode())) {
                        modulePermissions.remove(i);
                    }
                }
                // 将权限点加入模块
                module.setPermissions(modulePermissions);
                // 删除模块
                getModules().remove(module.getCode());
                // 再当作新模块加入进去
                getModules().put(module.getCode(), module);
                // 保存模块
                saveModules(Config.moduleFile);

                return true;
            } else {
                logger.error("模块 " + permission.getModuleCode() + " 对应的权限点为空！");

                throw new DDDException("模块 " + permission.getModuleCode() + " 对应的权限点为空！");
            }

        } else {
            logger.error("找不到" + permission.getModuleCode() + "对应的权限点！");

            throw new DDDException("找不到" + permission.getModuleCode() + "对应的权限点！");
        }
    }

    /**
     * 新增权限点
     *
     * @param permission
     * @return
     */
    public static void addPermission(Permission permission) {
        // 根据权限中的模块编码获取指定模块
        Module module = getModules().get(permission.getModuleCode());
        if (module != null) {
            // 取出模块中所有的权限点
            List<Permission> modulePermissions = module.getPermissions();
            // 将名字加入进去
            String[] name = permission.getName().split("_");
            // 将名字设置进入权限对象
            permission.setName(name[name.length - 1]);
            // 加入修改过后的权限点进模块里
            modulePermissions.add(permission);
            // 将权限点加入模块
            module.setPermissions(modulePermissions);
            // 删除模块
            getModules().remove(module.getCode());
            // 再当作新模块加入进去
            getModules().put(module.getCode(), module);
            // 保存模块
            saveModules(Config.moduleFile);
        } else {
            logger.error("找不到模块编码为:" + permission.getModuleCode() + "的模块");

            throw new DDDException("找不到模块编码为:" + permission.getModuleCode() + "的模块");
        }


    }

}
