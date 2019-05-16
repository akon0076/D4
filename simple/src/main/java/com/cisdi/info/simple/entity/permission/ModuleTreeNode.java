package com.cisdi.info.simple.entity.permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：DDD3 类名称：Node 类描述： 创建人：AnotherTen 创建时间：2015年12月24日 下午4:27:34 修改人：胡均
 * 修改时间：2015年12月24日 下午4:27:34 修改备注：
 *
 * @version 1.0 Copyright (c) 2015 DDD
 */
public class ModuleTreeNode {
    // id text name img nodes
    private String id;
    private String text;
    private String name;
    private String img;
    private String icon;
    private String routeData;
    private String location;
    private String group;
    private String routeParamsObj;
    private Integer level;
    private boolean isExpanded;
    private boolean isSelected;
    private Long displayIndex;
    private List<ModuleTreeNode> nodes = new ArrayList<ModuleTreeNode>();


    public String getRouteParamsObj() {
        return routeParamsObj;
    }

    public void setRouteParamsObj(String routeParamsObj) {
        this.routeParamsObj = routeParamsObj;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRouteData() {
        return routeData;
    }

    public void setRouteData(String routeData) {
        this.routeData = routeData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<ModuleTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<ModuleTreeNode> nodes) {
        this.nodes = nodes;
    }

    public void addNode(ModuleTreeNode node) {
        this.nodes.add(node);
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean getIsExpanded() {
        return this.isExpanded;
    }

    public void setIsExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Long getDisplayIndex() {
        return displayIndex == null ? 0 : displayIndex;
    }

    public void setDisplayIndex(Long displayIndex) {
        this.displayIndex = displayIndex;
    }
}
