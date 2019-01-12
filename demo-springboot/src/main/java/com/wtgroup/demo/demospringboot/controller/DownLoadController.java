package com.wtgroup.demo.demospringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/11/14 20:02
 */
@Controller
public class DownLoadController {

    @RequestMapping(value={"/download"},method={RequestMethod.POST})
    public void download(String fileName,HttpServletResponse response,
                         @RequestHeader String referer) throws IOException {    //referer:防止盗链
        System.out.println(referer);
        System.out.println(fileName);
        File file = new File("D:/download.txt");
        if(file.exists()){
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=download.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] by = new byte[fileInputStream.available()];
            fileInputStream.read(by);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(by);
            fileInputStream.close();
            outputStream.close();
        }
    }

}
