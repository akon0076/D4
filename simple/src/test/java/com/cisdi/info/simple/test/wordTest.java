package com.cisdi.info.simple.test;

import com.deepoove.poi.XWPFTemplate;

import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * @author:chengbg
 * @date:2018/11/26
 */
public class wordTest {
    public static void main(String[] args) throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("D:\\template.docx").render(new HashMap<String, Object>(){{
            put("title", "Pil-tl 模板引擎");
        }});
        FileOutputStream out = new FileOutputStream("out_template.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
