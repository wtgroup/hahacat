package com.wtgroup.demo.demospringboot;

import org.junit.Test;

/**
 * @author Nisus Liu
 * @version 0.0.0
 * @email liuhejun108@163.com
 * @date 2018/5/19 23:59
 */
public class TestSymbol {


    @Test
    public void fun() {
        System.out.println("520");
        int i = 1, j, k;
        System.out.println("     *    *");
        i++;
        System.out.println("    ***   ***");
        i++;
        System.out.println("   ***** *****");
        i++;
        for (i = 4; i <= 6; i++) {
            for (k = 1; k <= 6-i; k++) {
                System.out.print(" ");
            }
            for (j = 1; j <= 2 * i + 5; j++) {
                System.out.print("*");
            }
            System.out.print("\n");
        }
        for (i = 7; i <= 14; i++) {
            for (k = 1; k <= i - 6; k++) {
                System.out.print(" ");
            }
            for (j = 1; j < 29 - 2 * i; j++) {
                System.out.print("*");
            }
            System.out.print("\n");
        }
    }
}
