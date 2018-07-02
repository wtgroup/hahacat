package com.wtgroup.demo.demospringboot.controller;

import com.wtgroup.demo.demospringboot.bean.Girl;
import com.wtgroup.demo.demospringboot.bean.vo.ResponseVo;
import com.wtgroup.demo.demospringboot.exception.RequestParamException;
import com.wtgroup.demo.demospringboot.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**统一异常处理测试
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-23:39
 */
@RestController
public class ExceptionHandleController {

    @Autowired
    private GirlService girlService;

    @GetMapping("/getGirl")
    public ResponseVo getGirl(@Valid Girl girl, BindingResult br) throws Exception {   //@NotBlank 没有作用 / 必须放在包装类的字段上才有效
        if (br.hasErrors()) {
            //System.exit(1);
            ResponseVo vo = ResponseVo.error(-99, "参数异常");

            //throw new RequestParamException(br.getFieldError().getDefaultMessage());
            //返回所有的校验结果信息
            List<ObjectError> allErrors = br.getAllErrors();
            for (ObjectError error : allErrors) {
                vo.addMsg(error.getDefaultMessage());
            }
            throw new RequestParamException(vo);
        }

        System.out.println(girl);
        Girl g = girlService.getAge(girl);

        return ResponseVo.success(g);
    }
}
