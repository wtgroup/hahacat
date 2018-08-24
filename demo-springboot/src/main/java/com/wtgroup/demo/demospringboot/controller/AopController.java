package com.wtgroup.demo.demospringboot.controller;

import com.wtgroup.demo.demospringboot.bean.Girl;
import com.wtgroup.demo.demospringboot.bean.vo.ResponseVo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 测试aop
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-21:32
 */
@RestController
public class AopController {

    @PostMapping(value = {"/aop"})
    public ResponseVo aop(@RequestParam Map<String,Object> param,ResponseVo responseVo){
        System.out.println("## 进入controller中aop()");
        //System.out.println("制造了异常"+(1/0));
        System.out.println("注入的ResponseVo.getMsg()==: "+responseVo.getMsg());

        System.out.println(param);
        responseVo.setData(param);

        return responseVo.addMsg("aop returning");
    }


    @RequestMapping(value = {"/aop2"})
    public ResponseVo aop2(Girl girl, ResponseVo responseVo){
        System.out.println("## 进入controller中aop2()");
        responseVo.setData(girl);

        return responseVo.addMsg("aop returning");
    }
}
