package com.wtgroup.demo.hbase.controller;

import com.wtgroup.demo.hbase.entity.CustomerEvent;
import com.wtgroup.demo.hbase.utils.KgStringUtils;
import com.wtgroup.demo.hbase.utils.MD5Utils;
import org.apache.hadoop.hbase.client.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wtgroup.ohm.core.HBaseSupport;

import java.util.ArrayList;
import java.util.List;

/**测试ohm
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/27 16:16
 */
@Controller
public class OhmController {
    private final Logger log = LoggerFactory.getLogger(HbaseController.class);


    @RequestMapping("/ohm/{certno}")
    @ResponseBody
    public String getFieldsVal(@PathVariable String certno) {
        String prefix = null;

        String md5Certno = MD5Utils.md5(certno);
        log.debug("身份证号: {}, MD5: {}", certno, md5Certno);

        String md5Prefix = md5Certno.substring(md5Certno.length() - 5);
        log.debug("身份证号MD5后5位: {}", md5Prefix);


        prefix = md5Prefix+"-" + certno + "-";

        log.debug("row key前缀: {}", prefix);

        ArrayList<String> faColPairs = new ArrayList<>();
        faColPairs.add("cf,event");
        faColPairs.add("cf,event_type");
        faColPairs.add("cf,content");
        faColPairs.add("cf,create_time");

        String stopRow = KgStringUtils.nextString(prefix);
        log.debug("stop row: " + stopRow);

        List<CustomerEvent> res = new HBaseSupport<CustomerEvent>() {
        }.getByRowKeyPrefix(prefix, prefix, stopRow);

//        List<Result> res = HBaseUtilSingleton.getRowsByPrefix("lh_test2", prefix, faColPairs,prefix,stopRow);

        //log.debug("查询结果: " + res.toString());
        log.debug("查询结果: {}",res.size());

        System.out.println(res.get(0));

        return res.toString();
    }
}
