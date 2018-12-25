package com.cisdi.info.simple.entity.crossTable;

/**
 * @author gjt
 * @version 1.0
 * @className
 * @date 2018/12/6 10:40
 * @description
 */
public class CrossFieldDef {

    public CrossFieldDef(String name,String label)
    {
        this.name = name;
        this.label = label;
        this.setCrossKey();
    }

    public CrossFieldDef()
    {

    }
    /**
     * 表格名称
     */
    private String name;
    /**
     * 表格标签（渲染成表格所展示出的表头的名称）
     */
    private String label;
    /**
     * 类型（三种类型）
     */
    private String type;

    private String dataType;

    private String width = "100";

    private String color;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * 设置成交叉表主键
     */
    public void setCrossKey() {
//        this.type = TypeEnum.CROSS_KEY.getValue();
        setType(TypeEnum.CROSS_KEY.getValue());
    }

    /**
     * 设置成交叉表中相同类的字段
     */
    public void setRawField() {
//        this.type = TypeEnum.RAW_FIELD.getValue();
        setType(TypeEnum.RAW_FIELD.getValue());
    }

    /**
     * 设置成交叉表项目字段
     */
    public void setCrossField() {
//        this.type = TypeEnum.CROSS_FIELD.getValue();
        setType(TypeEnum.CROSS_FIELD.getValue());
    }
}
