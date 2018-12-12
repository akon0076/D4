package com.cisdi.info.simple.service.word;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author:chengbg
 * @date:2018/10/28
 */
public interface WordTemplateService {

    public void initWord(int rows, int cols);

    public void initWord(String filename, int rows, int cols);

    public void setCellText(int rowNomber, int cellNumber, String text);

    public void mergeCellVertically(int col, int fromRow, int toRow);

    public void mergeCellHorizontally(int row, int fromCol, int toCol);

    public void write() throws IOException;

    public void write(OutputStream outputStream) throws IOException;

    public String template(Map<String, Object> map, String sourceFile, String fileName) throws IOException;

}
