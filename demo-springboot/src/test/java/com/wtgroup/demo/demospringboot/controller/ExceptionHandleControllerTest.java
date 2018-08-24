package com.wtgroup.demo.demospringboot.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionHandleControllerTest {
    @Test
    public void getGirl() throws Exception {
    }

    @Test
    public void fun1() throws Exception {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new Exception("捕获在抛出: ", e);
        }
    }

}