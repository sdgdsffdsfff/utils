/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.utils.tdd;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author dengqb
 * @date 2013年11月21日
 */
public class MoneyCalculatorTest {
/*
 * TODO:
 *  1. 可以获取总数
 *  2. 按照客户需要，自动根据汇率转换币种进行计算
 */
    @Test
    public void testTimes_RMB2US(){
        Calculator calc = new Calculator();
        Double total = calc.times(5D,2D);
        Double USTotal = calc.exchange(total,"RMB","US");
        assertEquals(10.00,USTotal.doubleValue(),0);
    }
    
    /**
     * 按照汇率转换职责交给货币对象
     */
    @Test
    public void testTimes_RMB2US_1(){
        USDollar dollar = new USDollar(5d);
        RMB rmb = (RMB) dollar.times(2d, new RMB());
        assertEquals(60,rmb.getAmount(),0);
    }
}
