package com.cisdi.info.simple.service.word.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dto.word.DetailTablePolicy;
import com.cisdi.info.simple.dto.word.WordList;
import com.cisdi.info.simple.dto.word.WordTable;
import com.cisdi.info.simple.dto.word.WordText;
import com.cisdi.info.simple.service.word.WordTemplateService;
import com.cisdi.info.simple.config.Config;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.TextRenderData;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.*;

/**
 * @author:chengbg
 * @date:2018/10/28
 */
@Service
public class WordTemplateServiceBean implements WordTemplateService {
    private static final String FONTFAMILY = "微软雅黑";

    private XWPFTable table;
    private XWPFDocument document;
    private String filename;


    public XWPFTable getTable() {
        return table;
    }

    public XWPFDocument getDocument() {
        return document;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 初始化word表格
     *
     * @param rows 表格的行数
     * @param cols 表格的列数
     * @throws IOException
     */
    public void initWord(int rows, int cols) {
        this.filename = "word";
        initTable(rows, cols);
    }

    /**
     * 初始化word表格
     *
     * @param rows 表格的行数
     * @param cols 表格的列数
     * @throws IOException
     */
    public void initWord(String filename, int rows, int cols) {
        this.filename = filename;
        initTable(rows, cols);
    }

    /**
     * 初始化word表格
     */
    private void initTable(int rows, int cols) {
        this.document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontFamily(FONTFAMILY);
        run.setFontSize(20);
        run.setColor("333333");
        run.setBold(true);
        XWPFTable table = document.createTable(rows, cols);
        this.table = table;
        String bgColor = "FFFFFF";
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setW(new BigInteger("9600"));
        tblWidth.setType(STTblWidth.DXA);
    }


    /**
     * 设置单元格内容
     *
     * @param rowNomber  要设置的行数
     * @param cellNumber 要设置的列数
     * @param text       内容
     */
    public void setCellText(int rowNomber, int cellNumber, String text) {
        if (table == null) {
            throw new DDDException("word生成失败");
        }
        XWPFTableRow row = row = table.getRow(rowNomber);
        row.setHeight(100);
        XWPFTableCell cell = cell = row.getCell(cellNumber);
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(ctp, cell);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setColor("000000");
        run.setFontSize(10);
        run.setText(text);
        CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(FONTFAMILY);
        fonts.setEastAsia(FONTFAMILY);
        fonts.setHAnsi(FONTFAMILY);
        cell.setParagraph(paragraph);
    }

    /**
     * 将word写入response
     *
     * @throws IOException
     */
    public void write() throws IOException {
        if (document == null) {
            throw new DDDException("word生成失败");
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/msword;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".docx");
        document.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 将word写入response
     *
     * @throws IOException
     */
    public void write(OutputStream outputStream) throws IOException {
        if (document == null) {
            throw new DDDException("word生成失败");
        }
        document.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 设置表格间的空行
     *
     * @param document
     * @param run
     */
    public void setEmptyRow(XWPFDocument document, XWPFRun run) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
        run = paragraph.createRun();
    }


    /**
     * 列合并
     * 需要指定合并哪一列，合并起始行到终止行
     *
     * @param col     需要合并哪一列
     * @param fromRow 起始行
     * @param toRow   终止行
     */
    public void mergeCellVertically(int col, int fromRow, int toRow) {
        if (table == null) {
            throw new DDDException("word生成失败");
        }
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            CTVMerge vmerge = CTVMerge.Factory.newInstance();
            if (rowIndex == fromRow) {
                vmerge.setVal(STMerge.RESTART);
            } else {
                vmerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr != null) {
                tcPr.setVMerge(vmerge);
            } else {
                tcPr = CTTcPr.Factory.newInstance();
                tcPr.setVMerge(vmerge);
                cell.getCTTc().setTcPr(tcPr);
            }
        }
    }


    /**
     * 行合并
     * 需要指定合并哪一行，合并起始列到终止列
     *
     * @param row     需要合并哪一行
     * @param fromCol 起始列
     * @param toCol   终止列
     */
    public void mergeCellHorizontally(int row, int fromCol, int toCol) {
        if (table == null) {
            throw new DDDException("word生成失败");
        }
        for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
            CTHMerge hmerge = CTHMerge.Factory.newInstance();
            if (colIndex == fromCol) {
                hmerge.setVal(STMerge.RESTART);
            } else {
                hmerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(row).getCell(colIndex);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr != null) {
                tcPr.setHMerge(hmerge);
            } else {
                tcPr = CTTcPr.Factory.newInstance();
                tcPr.setHMerge(hmerge);
                cell.getCTTc().setTcPr(tcPr);
            }
        }
    }

    /**
     * word模板替换
     *
     * @param map        需要传入的数据
     * @param sourceFile 模板文件地址
     * @param fileName   输出文件名
     * @throws IOException
     */
    public String template(Map<String, Object> map, String sourceFile, String fileName) throws IOException {
        if (sourceFile == null) {
            throw new DDDException("模板文件不能为null");
        }
        if (map == null) {
            throw new DDDException("模板数据不能为null");
        }
        Map<String, Object> data = new HashMap<>();
        Set<String> keys = map.keySet();
        Object[] objects = keys.toArray();
        Configure config = null;
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof String) {
                String key = (String) objects[i];
                Object object = map.get(key);
                if (object instanceof String) {
                    String text = (String) object;
                    TextRenderData textRenderData = new TextRenderData(text);
                    data.put(key, textRenderData);
                }
                if (object instanceof WordText) {
                    WordText wordText = (WordText) object;
                    String text = wordText.getText();
                    TextRenderData textRenderData = new TextRenderData(text);
                    textRenderData.setStyle(wordText.getStyle());
                    data.put(key, textRenderData);
                }
                if (object instanceof WordList) {
                    WordList wordList = (WordList) object;
                    List<String> list = wordList.getList();
                    List<TextRenderData> renderDataList = new ArrayList<>();
                    for (int j = 0; j < list.size(); j++) {
                        String text = (String) list.get(j);
                        TextRenderData textRenderData = new TextRenderData(text);
                        renderDataList.add(textRenderData);
                    }
                    NumbericRenderData numbericRenderData = new NumbericRenderData(renderDataList);
                    numbericRenderData.setNumFmt(wordList.getStyle());
                    numbericRenderData.setFmtStyle(wordList.getFmtStyle());
                    data.put(key, numbericRenderData);
                }
                if (object instanceof WordTable) {
                    config = Configure.newBuilder().customPolicy(key, new DetailTablePolicy()).build();
                    data.put(key, object);
                }
            }
        }
        XWPFTemplate template = null;
        if (config == null) {
            template = XWPFTemplate.compile(sourceFile).render(data);
        } else {
            template = XWPFTemplate.compile(sourceFile, config).render(data);
        }
        FileOutputStream out = new FileOutputStream(Config.staticFilePath + fileName);
        template.write(out);
        out.flush();
        out.close();
        template.close();
        return fileName;
    }

}
