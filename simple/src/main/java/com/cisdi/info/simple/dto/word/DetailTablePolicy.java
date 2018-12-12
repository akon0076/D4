package com.cisdi.info.simple.dto.word;

import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;

import java.util.List;

/**
 * @author:chengbg
 * @date:2018/11/26
 */
public class DetailTablePolicy extends DynamicTableRenderPolicy {

    @Override
    public void render(XWPFTable table, Object data) {
        WordTable wordTable = (WordTable) data;
        int tableStartNumber = wordTable.getTableStartNumber();
        int dataStartNumber = wordTable.getDataStartNumber();
        XWPFTableRow row = null;
        if (tableStartNumber >= 0) {
            //删除标签行
            table.removeRow(tableStartNumber - 1);
            row = table.getRow(tableStartNumber - 2);
        } else {
            row = table.getRow(table.getNumberOfRows() - 1);
        }

        //循环设置表格数据
        List<List<String>> tableData = wordTable.getData();
        String style = wordTable.getStyle();
        for (int i = 0; i < tableData.size(); i++) {
            XWPFTableRow tableRow = null;
            if (tableStartNumber < 0) {
                table.insertNewTableRow(table.getNumberOfRows());
            } else {
                tableRow = table.insertNewTableRow(tableStartNumber - 1);
            }
            copyTableRow(tableRow, row);
            List<String> list = tableData.get(i);
            for (int j = dataStartNumber - 1, k = 0; j < list.size(); j++, k++) {
                XWPFTableCell cell = tableRow.getCell(j);
                cell.setText(list.get(k));
                serTableCellStyle(cell, style);
            }
        }

    }


    /**
     * 设置对齐方式
     *
     * @param cell
     * @param style
     */
    private void serTableCellStyle(XWPFTableCell cell, String style) {
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        if (WordTable.LEFT.equals(style)) {
            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.LEFT);
        } else if (WordTable.RIGHT.equals(style)) {
            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.RIGHT);
        } else {
            cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        }
    }

    /**
     * 复制row
     *
     * @param target
     * @param source
     */
    public void copyTableRow(XWPFTableRow target, XWPFTableRow source) {
        // 复制样式
        if (source.getCtRow() != null) {
            target.getCtRow().setTrPr(source.getCtRow().getTrPr());
        }
        // 复制单元格
        for (int i = 0; i < source.getTableCells().size(); i++) {
            XWPFTableCell cell1 = target.getCell(i);
            XWPFTableCell cell2 = source.getCell(i);
            if (cell1 == null) {
                cell1 = target.addNewTableCell();
            }
            copyTableCell(cell1, cell2);
        }
    }

    /**
     * 复制cell
     *
     * @param target
     * @param source
     */
    public void copyTableCell(XWPFTableCell target, XWPFTableCell source) {
        // 列属性
        if (source.getCTTc() != null) {
            target.getCTTc().setTcPr(source.getCTTc().getTcPr());
        }

    }

    /**
     * 复制内容样式
     *
     * @param target
     * @param source
     */
    public void copyParagraph(XWPFParagraph target, XWPFParagraph source) {
        // 设置段落样式
        target.getCTP().setPPr(source.getCTP().getPPr());
        // 添加Run标签
        for (int pos = 0; pos < target.getRuns().size(); pos++) {
            target.removeRun(pos);
        }
        for (XWPFRun s : source.getRuns()) {
            XWPFRun targetrun = target.createRun();
            CopyRun(targetrun, s);
        }
    }

    /**
     * 复制run
     *
     * @param target
     * @param source
     */
    public void CopyRun(XWPFRun target, XWPFRun source) {
        target.getCTR().setRPr(source.getCTR().getRPr());
    }


}
