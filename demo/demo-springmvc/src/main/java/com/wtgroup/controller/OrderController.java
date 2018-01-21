package com.wtgroup.controller;

import com.wtgroup.bean.OrdersCustom;
import com.wtgroup.bean.QueryVo;
import com.wtgroup.exception.exception.MyException;
import com.wtgroup.service.OrdersService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.wtgroup.bean.Orders;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-16-20:34
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrdersService ordersService;


    /**
     * 批量修改, 获取其pojoList
     */
    @RequestMapping("/updateInBatch.action")
    public void updateInBatch(QueryVo vo) {
        ordersService.updateInBatch(vo);
    }

    /**
     * 批量删除订单. 接受id数组.
     */
    @RequestMapping("/deleteInBatch.action")
//    public String deleteInBatch(@RequestParam("ids") Integer[] ordersIds, ModelMap modelMap){
    public String deleteInBatch(QueryVo vo, ModelMap modelMap) {

//        for (Integer id : ordersIds) {
//            System.out.println(id);
//        }
        modelMap.addAttribute("info", "批量删除成功(虚拟)!");
        return "info";
    }


    @RequestMapping("/findOrders.action")    //指定请求的url
    public ModelAndView findOrders() {
        //暂时没有条件,暂传入null=>查出所有了
        List<OrdersCustom> list = ordersService.queryOrdersByVo(null);
        ModelAndView mv = new ModelAndView();
        mv.addObject("orders", list);
        //springmvc.xml里面已经配置了前缀和后缀
        mv.setViewName("orders/orders");
        return mv;
    }

    /**
     * 修改订单
     */
    @RequestMapping("/editOrders.action")
    public String editOrders(Integer id, Model model) {
        //获取id, 查询订单信息, 回显
        //!这里前台继续用custom pojo响应数据, 封装的事情交由service层完成!
//        OrdersCustom ordersCustom = ordersService.queryOrdersById(id);
        OrdersCustom ordersCustom = ordersService.queryOrdersCustomById(id);
        //将数据添加至model
        model.addAttribute("ordersCustom", ordersCustom);
        //页面跳转
        return "orders/edit_orders";
    }


    /**
     * 更新订单
     * 增加参数, 绑定文件
     */
    @RequestMapping("/updateOrders.action")
    public String updateOrders(OrdersCustom ordersCustom, Model model, MultipartFile imageFile) throws IOException, MyException {
        //处理文件信息, 文件名设置pojo, 保存至数据库, 文件本身保存至磁盘
        if (imageFile != null && imageFile.getSize()>0) {
            //文件重命名, 防止覆盖就文件
            //策略: 原名+当前时间毫秒值
            String nameSuffix = String.valueOf(System.currentTimeMillis());
            //
            System.out.println(imageFile.getName());
            String originalFilename = imageFile.getOriginalFilename();
            System.out.println(originalFilename);
            //
            String fileName4save = FilenameUtils.getBaseName(originalFilename) + nameSuffix + "." + FilenameUtils.getExtension(originalFilename);

            //保存文件
            imageFile.transferTo(new File("D:\\DevelopKit\\upload" + fileName4save));

            //设置pojo image属性值
            ordersCustom.setImage(fileName4save);
        }

        //测试自定义的异常处理器   --OK
//        if (true) {
//            throw new MyException("我的自定义异常有效!");
//        }


        try {
            ordersService.update(ordersCustom);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新订单失败!");
        }
        model.addAttribute("info", "保存成功!");
        return "info";
    }


    /**
     * 制造假数据回传页面显示
     *
     * @return
     */
    public ModelAndView findOrders_bak() {
        //获取数据
        Orders o1 = new Orders();
        o1.setId(3);
        o1.setUserId(27);
        o1.setCreatetime(new Date());
        o1.setNumber("146578");
        Orders o2 = new Orders();
        o2.setId(3);
        o2.setUserId(27);
        o2.setCreatetime(new Date());
        o2.setNumber("146578");
        List<Orders> list = new ArrayList<Orders>();
        list.add(o1);
        list.add(o2);

        //返回MV
        ModelAndView mv = new ModelAndView();
        //添加到ModelMap, 是一个LinkedHashMap
        mv.addObject("s", "hello");
        mv.addObject("orders", list);

        //设置跳转页面
        mv.setViewName("/WEB-INF/jsp/orders/orders.jsp");

        return mv;
    }


}
