package com.wtgroup.demo.hbase.controller;

import com.wtgroup.demo.hbase.utils.HBaseUtilSingleton;
import com.wtgroup.demo.hbase.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nisus Liu
 * @version 0.0.0
 * @email liuhejun108@163.com
 * @date 2018/5/17 19:00
 */
@Controller
public class HbaseController {
    private final Logger log = LoggerFactory.getLogger(HbaseController.class);

    @RequestMapping("/getFields/{certno}")
    @ResponseBody
    public String getFieldsVal(@PathVariable String certno) {
        System.out.println("certno: " + certno);

        String fieldsVal = HBaseUtilSingleton.getFieldsVal("lh_customer", certno, "cf", "content,create_time,event,event_type");

        System.out.println(fieldsVal);


        return fieldsVal;
    }


    @RequestMapping("/getByPrefix/{certno}")
    @ResponseBody
    public String getRowsByPrefix(@PathVariable String certno) {

        String prefix = null;

        String md5Certno = MD5Utils.md5(certno);
        log.debug("身份证号: {}, MD5: {}", certno, md5Certno);

        String md5Prefix = md5Certno.substring(md5Certno.length() - 5);
        log.debug("身份证号MD5后5位: {}", md5Prefix);


        md5Prefix += "-" + certno + "-";

        log.debug("row key前缀: {}", md5Prefix);

        ArrayList<String> faColPairs = new ArrayList<>();
        faColPairs.add("cf,event");
        faColPairs.add("cf,event_type");
        faColPairs.add("cf,content");
        faColPairs.add("cf,create_time");


        List<String> res = HBaseUtilSingleton.getRowsByPrefix("lh_test2", md5Prefix, faColPairs);

        log.debug("查询结果: " + res.toString());

        return res.toString();
    }


    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        log.info("info level");
        log.debug("debug level");
        log.warn("warn level");
        log.error("error level");

        return "success";
    }


}
