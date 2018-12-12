package com.cisdi.info.simple.dto.word;

import com.deepoove.poi.data.style.Style;
import org.apache.commons.lang3.tuple.Pair;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;

import java.util.List;

/**
 * @author:chengbg
 * @date:2018/11/28
 */
public class WordList {
    /**
     * 无序（sk）
     *
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_BLANK = Pair.of(STNumberFormat.NONE, "");
    /**
     * 1. 2. 3.
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_DECIMAL = Pair.of(STNumberFormat.DECIMAL, "%1.");
    /**
     * 1) 2) 3)
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_DECIMAL_PARENTHESES = Pair.of(STNumberFormat.DECIMAL,
            "%1)");
    /**
     * ● ● ●
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_BULLET = Pair.of(STNumberFormat.BULLET, "●");
    /**
     * a. b. c.
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_LOWER_LETTER = Pair.of(STNumberFormat.LOWER_LETTER,
            "%1.");
    /**
     * i ⅱ ⅲ
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_LOWER_ROMAN = Pair.of(STNumberFormat.LOWER_ROMAN,
            "%1.");
    /**
     * A. B. C.
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_UPPER_LETTER = Pair.of(STNumberFormat.UPPER_LETTER,
            "%1.");
    /**
     * Ⅰ Ⅱ Ⅲ
     */
    public static final Pair<STNumberFormat.Enum, String> FMT_UPPER_ROMAN = Pair.of(STNumberFormat.UPPER_ROMAN,
            "%1.");

    /**
     * 列表数据
     */
    private List<String> list;

    /**
     * 样式，默认为1. 2. 3.
     */
    private Pair<STNumberFormat.Enum, String> style = FMT_DECIMAL;

    /**
     * 字体样式，默认为1. 2. 3.（sk）
     */
    private Style fmtStyle = new Style("宋体", 12);

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Pair<STNumberFormat.Enum, String> getStyle() {
        return style;
    }

    public void setStyle(Pair<STNumberFormat.Enum, String> style) {
        this.style = style;
    }

    public Style getFmtStyle() {
        return fmtStyle;
    }

    public void setFmtStyle(Style fmtStyle) {
        this.fmtStyle = fmtStyle;
    }
}
