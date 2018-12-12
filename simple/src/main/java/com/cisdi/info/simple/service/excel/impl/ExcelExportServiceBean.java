package com.cisdi.info.simple.service.excel.impl;

import com.cisdi.info.simple.service.excel.ExcelExportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author:chengbg
 * @date:2018/10/22
 */
@Service
public class ExcelExportServiceBean implements ExcelExportService {

    /**
     * excel导出
     *
     * @param list   需要导出的数据
     * @param header 表格头
     * @param clazz  具体的实体对象class
     * @param <T>
     * @throws Exception
     */
    @Override
    public <T> void excelExport(List<T> list, Map<String, String> header, Class<T> clazz) throws Exception {
        //创建excel文档对象
        Workbook workbook = new HSSFWorkbook();
        //创建excel表单
        Sheet sheet = workbook.createSheet();
        //写入excel表格头
        writeExcelHeader(sheet, header);
        Object[] keys = header.keySet().toArray();
        for (int rowNumber = 0; rowNumber < list.size(); rowNumber++) {
            Object permission = list.get(rowNumber);
            //新建一行
            Row row = sheet.createRow(rowNumber + 1);
            for (int colNumber = 0; colNumber < header.size(); colNumber++) {
                //新建一列
                Cell cell = row.createCell(colNumber);
                //获取表格头
                String headerText = keys[colNumber].toString();
                //获取表格头对应实体字段
                String value = header.get(headerText);
                //反射获取对应实体字段内容
                Method method = clazz.getMethod("get" + value.substring(0, 1).toUpperCase() + value.substring(1));
                Object propVaulue = method.invoke(permission);
                //获取当前内容具体的类型
                String type = clazz.getDeclaredField(header.get(headerText)).getType().getSimpleName();
                //根据类型写入excel
                writeCellValueByType(type, cell, propVaulue);
            }
        }
        //将excel写入以流的方式返回response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=excel.xls");
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 根据数据类型写入Excel列内容
     *
     * @param type  类型
     * @param cell  单元格
     * @param value 值
     */
    private void writeCellValueByType(String type, Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (type) {
            case "int":
                cell.setCellValue(Integer.parseInt(value.toString().split("\\.")[0]));
                break;
            case "String":
                cell.setCellValue(value.toString());
                break;
            case "Integer":
                cell.setCellValue(Integer.parseInt(value.toString().split("\\.")[0]));
                break;
            case "Boolean":
                cell.setCellValue(Boolean.valueOf(value.toString()));
                break;
            case "double":
                cell.setCellValue(Integer.parseInt(value.toString().split("\\.")[0]));
                break;
            case "Date":
                cell.setCellValue(sdf.format(value));
                break;
            default:
                cell.setCellValue(value.toString());
                break;
        }
    }

    /**
     * 写入excel表格头
     *
     * @param sheet
     * @param header
     */
    private void writeExcelHeader(Sheet sheet, Map<String, String> header) {
        Object[] keys = header.keySet().toArray();
        Row row = sheet.createRow(0);
        int len = header.size();
        for (int i = 0; i < len; i++) {
            Cell cell = row.createCell(i);
            String hearText = keys[i].toString();
            cell.setCellValue(hearText);
        }
    }
}
