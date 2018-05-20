package com.wtgroup.pdf;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * 代码来源: http://blog.csdn.net/sishenkankan/article/details/53107195
 * 这里有在此基础上改进后的: http://llade.iteye.com/blog/2397480
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-03-22-0:27
 */
public class TestPdf {

    public static void main(String[] args) throws IOException, DocumentException {
//        PdfReplacer textReplacer = new PdfReplacer("C:/Users/Nisus/Desktop/test.pdf");
        PdfReplacer textReplacer = new PdfReplacer("C:/Users/Nisus/Desktop/修改/中信 - 副本.pdf");
//        textReplacer.replaceText("管理集团", "地产");
        textReplacer.replaceText("大连万达商业管理集团股份有限公司", "大连万达商业地产股份有限公司");
//        textReplacer.replaceText("本科", "社会大学");
//        textReplacer.replaceText("0755-29493863 ", "15112345678");
        textReplacer.toPdf("C:/Users/Nisus/Desktop/test_out.pdf");
    }


}
