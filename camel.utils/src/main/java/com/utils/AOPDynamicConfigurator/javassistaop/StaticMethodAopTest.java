/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.utils.AOPDynamicConfigurator.javassistaop;

/**
 * 测试静态方法aop拦截
 * @author dengqb
 * @date 2013年11月6日
 */
public class StaticMethodAopTest extends BytecodeProcessor {
    
    public static String getName(){
        System.out.println("camel.deng");
        return "camel.deng";
    }
}
