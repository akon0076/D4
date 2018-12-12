package com.cisdi.info.simple.dto.word;

import java.util.List;

/**
 * @author:chengbg
 * @date:2018/11/28
 */
public class WordTable {
    public static final String CENTER = "center";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    /**
     * 表格起始行，默认在表格最后一行添加
     */
    private int tableStartNumber = -1;

    /**
     * 表格的数据起始列，默认为第一列
     */
    private int dataStartNumber = 1;


    /**
     * 表格数据
     */
    private List<List<String>> data;

    /**
     * 对齐方式，默认居中
     */
    private String style = CENTER;

    public int getTableStartNumber() {
        return tableStartNumber;
    }

    public void setTableStartNumber(int tableStartNumber) {
        this.tableStartNumber = tableStartNumber;
    }

    public int getDataStartNumber() {
        return dataStartNumber;
    }

    public void setDataStartNumber(int dataStartNumber) {
        this.dataStartNumber = dataStartNumber;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
