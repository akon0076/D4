package com.cisdi.info.simple.service.excel.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.service.excel.ExcelImportService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author:chengbg
 * @date:2018/10/31
 */
@Service
public class ExcelImportServiceBean implements ExcelImportService {
    /**
     * 解析excel文件，返回表格数据
     * 一个map为一行数据，key：对应表格头，value：对应的值
     * 返回map集合对应整个excel表格数据
     *
     * @param file excel文件
     * @return List<Map>
     * @throws Exception
     */
    public List<Map<String, Object>> excelImport(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new DDDException("文件不能为null");
        }
        Workbook workbook = null;
        //根据文件流创建excel
        if (file.getOriginalFilename().endsWith(".xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else if (file.getOriginalFilename().endsWith(".xlsx")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else {
            throw new DDDException("文件格式错误");
        }
        //获取excel表格
        Sheet sheet = workbook.getSheetAt(0);
        //获取第一行（表格头）
        Row row = sheet.getRow(0);
        //表格头的数量
        int numberOfCells = row.getPhysicalNumberOfCells();
        //用于存储表格头
        List<String> list = new ArrayList<>();
        //用于存储所有excel数据
        List<Map<String, Object>> excelList = new ArrayList<>();
        //遍历获取表格头
        for (int i = 0; i < numberOfCells; i++) {
            Cell cell = row.getCell(i);
            String value = cell.getStringCellValue();
            list.add(value);
        }
        //所有行数
        int numberOfRows = sheet.getPhysicalNumberOfRows();
        //遍历处理所有数据
        for (int i = 1; i < numberOfRows; i++) {
            Row sheetRow = sheet.getRow(i);
            int cells = sheetRow.getPhysicalNumberOfCells();
            Map<String, Object> map = new HashMap<>();
            for (int j = 0; j < cells; j++) {
                Cell cell = sheetRow.getCell(j);
                //获取当前值的类型
                CellType cellTypeEnum = cell.getCellTypeEnum();
                String hearder = list.get(j);
                //根据类型保存进map
                switch (cellTypeEnum) {
                    case STRING:
                        String stringCellValue = cell.getStringCellValue();
                        map.put(hearder, stringCellValue);
                        break;
                    case BOOLEAN:
                        Boolean booleanCellValue = cell.getBooleanCellValue();
                        map.put(hearder, booleanCellValue);
                        break;
                    case NUMERIC:
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            String dateFormatValue = DateFormatUtils.format(date, "yyyy-MM-dd");
                            map.put(hearder, dateFormatValue);
                        } else {
                            Double numericCellValue = cell.getNumericCellValue();
                            DecimalFormat df = new DecimalFormat("0");
                            String numericFormatValue = df.format(numericCellValue);
                            map.put(hearder, numericFormatValue);
                        }
                        break;
                    case FORMULA:
                        String cellFormula = cell.getCellFormula();
                        map.put(hearder, cellFormula);
                        break;
                    case BLANK:
                        map.put(hearder, "");
                        break;
                    case _NONE:
                        map.put(hearder, "");
                        break;
                }
            }
            excelList.add(map);
        }
        return excelList;
    }
}
