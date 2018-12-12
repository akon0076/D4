package com.cisdi.info.simple.dto.word;

import com.deepoove.poi.data.style.Style;

/**
 * @author:chengbg
 * @date:2018/11/28
 */
public class WordText {
    /**
     * 内容
     */
    private String text;

    /**
     * 样式
     */
    private Style style;

    public WordText() {
        this.text = "";
        Style style = new Style();
        style.setBold(false);
        style.setColor("000000");
        style.setFontFamily("宋体");
        style.setFontSize(12);
        style.setItalic(false);
        style.setStrike(false);
        style.setUnderLine(false);
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
