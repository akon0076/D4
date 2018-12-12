package com.cisdi.info.simple.service.wordTest.impl;

import com.cisdi.info.simple.dto.word.WordList;
import com.cisdi.info.simple.dto.word.WordTable;
import com.cisdi.info.simple.service.word.WordTemplateService;
import com.cisdi.info.simple.service.wordTest.WordExportTestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:chengbg
 * @date:2018/10/22
 */
@Service
public class WordExportServiceBean implements WordExportTestService {
    private static Logger logger = LogManager.getLogger();

    @Autowired
    private WordTemplateService word;

    @Override
    public String template() throws IOException {
        Map<String, Object> map = new HashMap<>();
        //列表数据用list
        List<String> list = new ArrayList<>();
        list.add("列表替换测试1");
        list.add("列表替换测试2");
        list.add("列表替换测试3");
        list.add("列表替换测试4");

        WordList wordList = new WordList();
        wordList.setList(list);

        //循环的表格数据
        List<List<String>> table = new ArrayList<>();
        List<String> tableData = new ArrayList<>();
        tableData.add("20");
        tableData.add("18");
        tableData.add("23");
        tableData.add("23");
        tableData.add("23");
        table.add(tableData);
        table.add(tableData);
        table.add(tableData);
        table.add(tableData);

        WordTable wordTable = new WordTable();
        wordTable.setTableStartNumber(7);
        wordTable.setDataStartNumber(1);
        wordTable.setData(table);

        map.put("content", "小明-这是替换的内容");//文本类型数据
        map.put("list", wordList);//列表类型数据
        map.put("table", wordTable);//循环表格类型数据

        //需要传入数据，模板文件位置，生成的文件名
        String fileName = word.template(map, "D:\\测试文档.docx", "test.docx");
        return fileName;
    }

    @Override
    public void wordExport() throws IOException {
        //初始化一个10行5列的表格
        word.initWord(10, 5);
        //设置表格具体内容
        createSimpleTable();
        //将word写入返回流中
        word.write();
    }

    /**
     * 设置表格具体内容
     */
    private void createSimpleTable() {
        //合并第一行的0列到1列
        word.mergeCellHorizontally(0, 0, 1);
        //根据行号和列号设置单元格内容
        word.setCellText(0, 0, "种类");
        word.setCellText(0, 2, "总分");
        word.setCellText(0, 3, "实得");
        word.setCellText(0, 4, "备注");
        for (int i = 1; i < 8; i++) {
            word.setCellText(i, 0, "种类" + i);
            word.setCellText(i, 1, "种类细则" + i);
            word.setCellText(i, 2, "" + i);
            word.setCellText(i, 3, "" + i);
            word.setCellText(i, 4, "三度空间阿萨德妇女节开发四季的风");
        }
        //合并第8行的0列到3列
        word.mergeCellHorizontally(8, 0, 3);
        word.mergeCellHorizontally(8, 3, 4);
        word.mergeCellHorizontally(9, 1, 4);

        word.setCellText(8, 0, "合计");
        word.setCellText(9, 0, "总结");
        word.setCellText(8, 3, "50");
        word.setCellText(9, 1, "总结");
    }
}
