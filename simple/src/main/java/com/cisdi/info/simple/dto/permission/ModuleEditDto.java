package com.cisdi.info.simple.dto.permission;


import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.permission.Module;
public class ModuleEditDto{

private Module module;

//码表是：模块类型
private List<CodeTable> moduleTypeCodeTables;
//码表是：逻辑
private List<CodeTable> isInUseCodeTables;

//外键实体是：Module
private List<Module> parentModules;


public  Module getModule()
{
    return this.module;
}
public  void setModule(Module module)
{
    this.module = module;
}

public List<CodeTable> getModuleTypeCodeTables()
{
        return this.moduleTypeCodeTables;
}
public void setModuleTypeCodeTables(List<CodeTable> moduleTypeCodeTables)
{
    this.moduleTypeCodeTables = moduleTypeCodeTables;
}

public List<CodeTable> getIsInUseCodeTables()
{
        return this.isInUseCodeTables;
}
public void setIsInUseCodeTables(List<CodeTable> isInUseCodeTables)
{
    this.isInUseCodeTables = isInUseCodeTables;
}

public List<Module> getParentModules()
{
    return this.parentModules;
}
public void setParentModules(List<Module> parentModules)
{
    this.parentModules = parentModules;
}
}
