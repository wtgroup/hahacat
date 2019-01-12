package com.wtgroup.demo.demospringboot.controller;

import com.wtgroup.demo.demospringboot.bean.vo.ResponseVo;
import com.wtgroup.demo.demospringboot.bean.vo.Status;
import com.wtgroup.demo.demospringboot.constant.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-21-23:44
 */
@RestController
@CrossOrigin
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);


    @RequestMapping(value = "/test/null", method = RequestMethod.GET)
    public String testNull(String name) {
        System.out.println(name);
        return "Ok...";
    }

    @PostMapping(value = "/test/null2")
    public String testNull2(@RequestBody Map<String, Object> params) {
        System.out.println(params);
        return "Ok...";
    }

    @RequestMapping(value = "/postman", method = RequestMethod.GET)
    public ResponseEntity<ResponseVo<String>> postMan(String name) {
        System.out.println(name);
        ResponseVo<String> resp = new ResponseVo<>();
        resp.setStatus(Status.SUCCESS);
        resp.setData("OK 啦~");
        return new ResponseEntity<ResponseVo<String>>(resp, StatusCode.ERROR.getHttpStatus());
    }


    @PostMapping("/test")
    public String testCtrl(HttpServletRequest request, HttpServletResponse response) {
        log.info("testCtrl is running ....");


        HttpSession session = request.getSession();
        System.out.println(session.getId());

        session.setAttribute("test", "hhahahhah");



        String s = response.encodeURL("http://localhost:8080/test?re=true");
        System.out.println(s);
        return "<a href=" + s + ">超链接, 点击看看</a>";
    }
    @GetMapping("/get_attr")
    public String getSessionAttr(HttpServletRequest request, HttpServletResponse response) {
        log.info("getSessionAttr is running ....");


        HttpSession session = request.getSession();
        System.out.println(session.getId());

        Object test = session.getAttribute("test");
        if (test==null) {
            System.out.println(":: NULL");
        }else{
            System.out.println(test);
        }



        return String.valueOf(test);
    }






    @RequestMapping(value = {"/rest/{name}.you/{age}"})
    public String restCtrl(@PathVariable String name, @PathVariable int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }


}
