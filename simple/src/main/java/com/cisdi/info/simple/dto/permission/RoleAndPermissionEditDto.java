package com.cisdi.info.simple.dto.permission;


import com.cisdi.info.simple.entity.permission.RoleAndPermission;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
public class RoleAndPermissionEditDto{

private RoleAndPermission roleAndPermission;




public  RoleAndPermission getRoleAndPermission()
{
    return this.roleAndPermission;
}
public  void setRoleAndPermission(RoleAndPermission roleAndPermission)
{
    this.roleAndPermission = roleAndPermission;
}

}
