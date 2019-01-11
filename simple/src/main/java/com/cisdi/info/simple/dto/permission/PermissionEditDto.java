package com.cisdi.info.simple.dto.permission;


import com.cisdi.info.simple.entity.permission.Permission;

public class PermissionEditDto {

    private Permission permission;

    private String lastCode;

    public String getLastCode() {
        return lastCode;
    }

    public void setLastCode(String lastCode) {
        this.lastCode = lastCode;
    }

    public Permission getPermission() {
        return this.permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

}
