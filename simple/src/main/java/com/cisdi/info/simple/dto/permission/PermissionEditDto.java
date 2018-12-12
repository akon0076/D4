package com.cisdi.info.simple.dto.permission;


import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class PermissionEditDto{

private Permission permission;




public  Permission getPermission()
{
    return this.permission;
}
public  void setPermission(Permission permission)
{
    this.permission = permission;
}

}
