package com.cisdi.info.simple.service.permission.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.permission.ModuleDao;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.ModuleTreeNode;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.permission.ExtendsMouleService;
import com.cisdi.info.simple.service.permission.ModuleService;
import com.cisdi.info.simple.service.permission.PermissionService;
import com.cisdi.info.simple.service.permission.RoleAndPermissionService;
import com.cisdi.info.simple.util.ModuleManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class ModuleServiceBean extends BaseService implements ModuleService {

    private static final String STOP_USE = "停用";

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private OperatorDao operatorDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleAndPermissionService roleAndPermissionService;

    @Autowired
    private final Map<String, ExtendsMouleService> extendsMouleServiceMap = new ConcurrentHashMap<>();

    private ExtendsMouleService getExtendsMouleService(String serviceName) {
        return this.extendsMouleServiceMap.get(serviceName);
    }

    /**
     * 获取全部模块
     *
     * @return
     */
    @Override
    public List<Module> findAllModules() {
        Collection<Module> modules = ModuleManager.findAllModules();
        List<Module> list = new ArrayList<>();
        list.addAll(modules);
        return list;
    }

    /**
     * 找到全部非叶节点模块
     *
     * @return
     */
    @Override
    public List<Module> findAllParentModules() {
        List<Module> moduleTree = findModuleTree();
        List<Module> resultList = new ArrayList<>();
        for (Module module : moduleTree) {
            if (STOP_USE.equals(module.getIsInUse())) {
                continue;
            }
            if (module.getPermissions().size() == 0) {
                resultList.add(module);
            }
            List<Module> children = module.getChildren();
            if (children.size() != 0) {
                findAllParentModules(resultList, children);
            }
        }
        for (Module module : resultList) {
            module.setChildren(null);
        }
        return resultList;
    }

    /**
     * 找到全部叶节点模块
     *
     * @return
     */
    @Override
    public List<Module> findAllLeafModules() {
        List<Module> moduleTree = findModuleTree();
        List<Module> resultList = new ArrayList<>();
        for (Module module : moduleTree) {
            if (STOP_USE.equals(module.getIsInUse())) {
                continue;
            }
            List<Module> children = module.getChildren();
            if (children.size() == 0) {
                resultList.add(module);
            } else {
                findAllLeafModules(resultList, children);
            }
        }
        return resultList;
    }

    /**
     * 处理子节点
     *
     * @param resultList
     * @param children
     */
    private void findAllParentModules(List<Module> resultList, List<Module> children) {
        for (Module module : children) {
            if (STOP_USE.equals(module.getIsInUse())) {
                continue;
            }
            if (module.getPermissions().size() == 0) {
                resultList.add(module);
            }
            List<Module> child = module.getChildren();
            if (child.size() != 0) {
                findAllParentModules(resultList, child);
            }
        }
    }

    /**
     * 处理子节点
     *
     * @param resultList
     * @param children
     */
    private void findAllLeafModules(List<Module> resultList, List<Module> children) {
        for (Module module : children) {
            if (STOP_USE.equals(module.getIsInUse())) {
                continue;
            }
            List<Module> child = module.getChildren();
            if (child.size() == 0) {
                resultList.add(module);
            } else {
                findAllLeafModules(resultList, child);
            }
        }
    }

    @Override
    public List<Module> findAllModulesWithIdName() {
        return new ArrayList<>(ModuleManager.findAllModules());
    }

    @Override
    public Module findModule(Long moduleId) {
        return this.moduleDao.findModule(moduleId);
    }


    //所有外键的Name都以加载
    @Override
    public Module findModuleWithForeignName(String moduleCode) {
        Module result = findMoudle(moduleCode);
        result.setHideAttribute(result.getCode());
        return result;
    }

    /**
     * 获取全部模块树
     *
     * @param pageDTO
     * @return
     */
    @Override
    public PageResultDTO findModuleTree(PageDTO pageDTO) {
        Map<String, Module> moduleMap = ModuleManager.refreshAndLoadModules();
        if (moduleMap == null || moduleMap.size() == 0) {
            throw new DDDException("模块加载失败");
        }
        List<Module> list = new ArrayList<>();
        Collection<Module> modules = moduleMap.values();
        for (Module module : modules) {
            String parentCode = module.getParentCode();
            if (StringUtils.isBlank(parentCode)) {
                list.add(module);
            }
            setModuleChildren(modules, module);
        }
        Integer currentPage = pageDTO == null ? 1 : pageDTO.getCurrentPage();
        Integer pageSize = pageDTO == null ? 10 : pageDTO.getPageSize();
        int formIndex = (currentPage - 1) * pageSize;
        int toIndex = formIndex + pageSize;
        toIndex = toIndex > list.size() ? list.size() : toIndex;
        Module resultModule = new Module();
        resultModule.getChildren().addAll(list);
        sort(resultModule);
        List<Module> resultlist = resultModule.getChildren().subList(formIndex, toIndex);
        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setDatas(resultlist);
        pageResultDTO.setTotalCount((long) list.size());
        return pageResultDTO;
    }

    /**
     * 获取全部模块树
     *
     * @return
     */
    public List<Module> findModuleTree() {
        Map<String, Module> moduleMap = ModuleManager.refreshAndLoadModules();
        if (moduleMap == null || moduleMap.size() == 0) {
            throw new DDDException("模块加载失败");
        }
        List<Module> list = new ArrayList<>();
        Collection<Module> modules = moduleMap.values();
        for (Module module : modules) {
            String parentCode = module.getParentCode();
            if (StringUtils.isBlank(parentCode)) {
                list.add(module);
            }
            setModuleChildren(modules, module);
        }
        Module resultModule = new Module();
        resultModule.getChildren().addAll(list);
        sort(resultModule);
        return resultModule.getChildren();
    }

    /**
     * 设置子模块
     *
     * @param modules
     * @param module
     */
    private void setModuleChildren(Collection<Module> modules, Module module) {
        String moduleCode = module.getCode();
        for (Module item : modules) {
            String parentCode = item.getParentCode();
            if (moduleCode.equals(parentCode)) {
                module.getChildren().add(item);
            }
        }
    }

    /**
     * 保存模块
     *
     * @param module
     * @return
     */
    @Override
    public void saveModule(Module module) {
        if (module == null) {
            throw new DDDException("模板为null，请检查参数是否正确");
        }
        String code = module.getCode();
        if (StringUtils.isBlank(code)) {
            throw new DDDException("模块编码为null,请重新输入");
        }
        if (ModuleManager.findModuleByCode(code) != null) {
            throw new DDDException("模块编码必须唯一，当前编码已存在");
        }
        if (module.getDisplayIndex() == null) {
            module.setDisplayIndex(1L);
        }
        ModuleManager.addModule(code, module);
    }

    /**
     * 获取全部模块权限树
     *
     * @return
     */
    public List<Module> findModuleAndPermissionTree() {
        Map<String, Module> moduleMap = ModuleManager.refreshAndLoadModules();
        if (moduleMap == null || moduleMap.size() == 0) {
            throw new DDDException("模块加载失败");
        }
        List<Module> list = new ArrayList<>();
        Collection<Module> modules = moduleMap.values();
        for (Module module : modules) {
            String parentCode = module.getParentCode();
            if (StringUtils.isBlank(parentCode)) {
                list.add(module);
            }
            setModuleChildren(modules, module);
        }
        Module resultModule = new Module();
        resultModule.getChildren().addAll(list);
        sort(resultModule);
        return resultModule.getChildren();
    }

    /**
     * 更新模块
     *
     * @param module
     * @return
     */
    @Override
    public Module updateModule(Module module) {
        if (module == null) {
            throw new DDDException("模块为null，请检查参数是否正确");
        }
        String code = module.getCode();
        String lastCode = module.getLastCode();
        if (StringUtils.isBlank(code)) {
            throw new DDDException("模块编码为null,请重新输入");
        }
        if (!code.equals(lastCode)) {
            if (ModuleManager.findModuleByCode(code) != null) {
                throw new DDDException("模块编码必须唯一，当前编码已存在");
            }
        }
        Map<String, Module> moduleMap = ModuleManager.refreshAndLoadModules();
        //删除之前的模块
        if (moduleMap.remove(lastCode) == null) {
            throw new DDDException("该模块不存在，可能已经被删除或更改");
        }
        //遍历每个模块,找到当前要修改的子模块进行更改
        for (Module childModule : moduleMap.values()) {
            if (code.equals(childModule.getParentCode())) {
                childModule.setParentName(module.getName());
                childModule.setParentCode(code);
            }
        }
        Module moduleResult = moduleMap.put(code, module);
        //保存文件
        ModuleManager.saveModules();
        return moduleResult;
    }

    /**
     * 删除模块
     *
     * @param moduleCode
     */
    @Override
    public boolean deleteModule(String moduleCode) {
        //删除模块
        boolean deleteModule = ModuleManager.removeModule(moduleCode);
        //删除该模块分配的权限
        boolean deletePermission = roleAndPermissionService.deleteRoleAndPermissionByModuleCode(moduleCode);
        return deleteModule && deletePermission;
    }

    /**
     * @param operatorId 操作员Id
     * @param modelType  模块类型
     * @return 根据moduleCode找到所有模块, 并生成树
     * @Author April_Xiang
     */
    @Override
    public ModuleTreeNode constructNewTree(Long operatorId, String modelType) {
        //默认为电脑模块
        modelType = StringUtils.isBlank(modelType) ? "电脑模块" : modelType;
        //找到该操作员对应的所有ModuleCode
        List<String> moduleCodes = this.operatorDao.findAllModuleCodesByOperatorId(operatorId);
        if (moduleCodes == null || moduleCodes.size() == 0) {
            return null;
        }
        Map<String, ModuleTreeNode> treeNodeMap = new HashMap<>();
        Set<ModuleTreeNode> topTreeNode = new HashSet<>();
        for (String moduleCode : moduleCodes) {
            Module module = ModuleManager.findModuleByCode(moduleCode);
            if (module == null) {
                throw new DDDException("找不到编码为" + moduleCode + "的模块");
            }
            if (treeNodeMap.get(moduleCode) != null || !modelType.equals(module.getModuleType()) || STOP_USE.equals(module.getIsInUse())) {
                continue;
            }
            setPrentModuleTreeNode(treeNodeMap, topTreeNode, module, null);
        }
        ModuleTreeNode rootModuleTreeNode = new ModuleTreeNode();
        rootModuleTreeNode.setId("root");
        rootModuleTreeNode.setName("root");
        rootModuleTreeNode.setText("根节点");
        rootModuleTreeNode.getNodes().addAll(topTreeNode);
        sort(rootModuleTreeNode);
        return rootModuleTreeNode;
    }

    /**
     * 设置当前模块节点的父节点
     *
     * @param nodeMap
     * @param module
     */
    private void setPrentModuleTreeNode(Map<String, ModuleTreeNode> nodeMap, Set<ModuleTreeNode> topTreeNode, Module module, Module child) {
        //将模块转换为节点
        ModuleTreeNode node = convertModule2TreeNode(module);
        if (child != null) {
            ModuleTreeNode childNode = nodeMap.get(child.getCode());
            node.addNode(childNode);
        }
        String moduleCode = module.getCode();
        if (nodeMap.get(moduleCode) != null) {
            return;
        }
        nodeMap.put(moduleCode, node);
        //设置扩展子模块
        String extendsMouleServiceName = module.getExtendsMouleServiceName();
        if (!StringUtils.isBlank(extendsMouleServiceName)) {
            List<Module> extendMoules = loadExtendMoule(extendsMouleServiceName);
            if (extendMoules != null && extendMoules.size() != 0) {
                //继续扩展子模块
                setModuleTreeNodeChilds(node, extendMoules);
            }
        }
        String prentCode = module.getParentCode();
        //判断是否有父模块
        if (StringUtils.isBlank(prentCode)) {
            topTreeNode.add(node);
            return;
        }
        //判断父节点是否已存在
        ModuleTreeNode parentTreeNode = nodeMap.get(prentCode);
        if (parentTreeNode == null) {  //如果不存在就新建父节点
            Module prentModule = ModuleManager.findModuleByCode(prentCode);
            //继续处理父节点
            setPrentModuleTreeNode(nodeMap, topTreeNode, prentModule, module);
        } else { //如果存在就将此节点关联到父节点
            parentTreeNode.getNodes().add(node);
        }
    }

    /**
     * 设置扩展子模块
     *
     * @param moduleTreeNode
     * @param extendMoules
     */
    private void setModuleTreeNodeChilds(ModuleTreeNode moduleTreeNode, List<Module> extendMoules) {
        if (extendMoules == null) {
            return;
        }
        for (Module extendMoule : extendMoules) {
            if (extendMoule == null) {
                continue;
            }
            //设置子节点
            ModuleTreeNode treeNode = convertModule2TreeNode(extendMoule);
            moduleTreeNode.addNode(treeNode);
            //判断是否还有扩展模块
            String extendsMouleServiceName = extendMoule.getExtendsMouleServiceName();
            if (!StringUtils.isBlank(extendsMouleServiceName)) {
                List<Module> extendMouleChilds = loadExtendMoule(extendsMouleServiceName);
                if (extendMouleChilds != null && extendMouleChilds.size() != 0) {
                    //设置扩展子模块
                    setModuleTreeNodeChilds(treeNode, extendMouleChilds);
                }
            }

        }
    }

    @Override
    public ModuleTreeNode findAllTreeNode(String modelType) {
        //默认为电脑模块
        modelType = StringUtils.isBlank(modelType) ? "电脑模块" : modelType;
        List<Module> allModules = findAllModules();
        Map<String, ModuleTreeNode> treeNodeMap = new HashMap<>();
        Set<ModuleTreeNode> topTreeNode = new HashSet<>();
        for (Module module : allModules) {
            if (treeNodeMap.get(module.getCode()) != null || !modelType.equals(module.getModuleType()) || STOP_USE.equals(module.getIsInUse())) {
                continue;
            }
            setPrentModuleTreeNode(treeNodeMap, topTreeNode, module, null);
        }
        ModuleTreeNode rootModuleTreeNode = new ModuleTreeNode();
        rootModuleTreeNode.setId("root");
        rootModuleTreeNode.setName("root");
        rootModuleTreeNode.setText("根节点");
        rootModuleTreeNode.getNodes().addAll(topTreeNode);
        sort(rootModuleTreeNode);
        return rootModuleTreeNode;
    }

    @Override
    public Module findModuleForDisplay(String moduleCode) {
        return ModuleManager.findModule(moduleCode);
    }

    /**
     * 根据serviceName加载扩展模块
     *
     * @param serviceName
     * @return
     */
    @Override
    public List<Module> loadExtendMoule(String serviceName) {
        if (StringUtils.isBlank(serviceName)) {
            return null;
        }
        //首字母转小写
        serviceName = (new StringBuilder()).append(Character.toLowerCase(serviceName.charAt(0))).append(serviceName.substring(1)).toString();
        //获取扩展的自定义service
        ExtendsMouleService extendsMouleService = extendsMouleServiceMap.get(serviceName);
        if (extendsMouleService == null) {
            String error = "没有找到对应的service：" + serviceName;
            logger.error(error);
            throw new DDDException(error);
        }
        //获取扩展模块
        List<Module> modules = extendsMouleService.loadExtendMoule();
        return modules;
    }

    private Module findMoudle(String code) {
        Module module = ModuleManager.findModule(code);
        if (module == null) {
            String error = "没找到模块：" + code;
            logger.error(error);
            throw new DDDException(error);
        }
        return module;
    }

    private ModuleTreeNode convertModule2TreeNode(Module module) {

        ModuleTreeNode treeNode = new ModuleTreeNode();
        treeNode.setText(module.getName());
        treeNode.setId(module.getCode());
        treeNode.setRouteData(module.getRoute());
        treeNode.setLocation(module.getUrl());
        treeNode.setRouteParamsObj(module.getRouteParamsObj());
        treeNode.setDisplayIndex(module.getDisplayIndex());
        treeNode.setIsExpanded(false);
        treeNode.setIsSelected(false);

        String iconClass = module.getIconClass();
        if (iconClass != null && (!("".equals(iconClass)))) {
            treeNode.setIcon(iconClass);
        }
        return treeNode;
    }

    /**
     * 对模块排序
     *
     * @param module
     */
    private void sort(Module module) {
        List<Module> children = module.getChildren();
        if (children.size() == 0) {
            return;
        }
        children.sort(Comparator.comparing(Module::getDisplayIndex));
        for (Module child : children) {
            sort(child);
        }
    }

    private void sort(ModuleTreeNode node) {
        if (node.getNodes().size() == 0) {
            return;
        }
        node.getNodes().sort(Comparator.comparing(ModuleTreeNode::getDisplayIndex));
        for (int i = 0; i < node.getNodes().size(); i++) {
            sort(node.getNodes().get(i));
        }
    }
}
