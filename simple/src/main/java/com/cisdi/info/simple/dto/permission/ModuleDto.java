package com.cisdi.info.simple.dto.permission;

import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chengbg
 * @date: 2019/5/27
 **/
public class ModuleDto {
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private Long displayIndex;

    private String parentCode;

    private String parentName;

    private String moduleType;

    private String isInUse;

    private List<Module> children = new ArrayList<>();

    private List<Permission> permissions = new ArrayList<Permission>();
}
