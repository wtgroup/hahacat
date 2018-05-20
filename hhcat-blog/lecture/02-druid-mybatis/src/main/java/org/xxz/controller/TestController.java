package org.xxz.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xxz.domain.User;
import org.xxz.mapper.UserMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class TestController {
    
    @Resource
    private DataSource dataSource;
    
    @Resource
    private UserMapper userMapper;
    
    @GetMapping(value = "testDruid")
    public String testDruid() {
        System.out.println(dataSource);
        return "test druid";
    }
    
    @GetMapping(value = "find")
    public User find(Long id) {
        return userMapper.findUser(id);
    }
    
    @GetMapping(value = "save")
    public User save(String name) {
        User user = new User(name);
        userMapper.saveUser(user);
        return user;
    }
    
    @GetMapping(value = "update")
    public User update(Long id, String name) {
        User user = new User(id, name);
        userMapper.updateUser(user);
        return user;
    }
    
    @GetMapping(value = "delete")
    public int delete(Long id) {
        return userMapper.deleteUser(id);
    }
    
    @GetMapping(value = "findAll")
    public PageInfo<User> findAll(@RequestParam(required=false, defaultValue="1")int pageNum, 
            @RequestParam(required=false, defaultValue="1")int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.findAll();
        return new PageInfo<>(list);
    }

}
