package com.wtgroup.blog.test;

import com.wtgroup.blog.Application;
import com.wtgroup.blog.entity.TParam;
import com.wtgroup.blog.mapper.TParamMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-26-9:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback(false)
public class Test01 {

   @Resource
   private TParamMapper tParamMapper;
   /**
    * 测试mybatis
    */
   @Test
   public void fun01(){
      TParam tParam = new TParam();
      tParam.setParamName("签名");
      tParam.setCreateTime(System.currentTimeMillis());
      tParam.setCreateUser(101L);
      tParam.setParamValue("我是一个小尾巴~~");
      tParam.setUpdateUser(1102L);
      tParam.setUpdateTime(System.currentTimeMillis());
      int row = tParamMapper.insert(tParam);
      Assert.assertEquals(1,row);

   }



}
