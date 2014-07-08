package com.utils.AOPDynamicConfigurator.javassitaop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.utils.AOPDynamicConfigurator.javassistaop.StaticMethodAopProcessor;
import com.utils.AOPDynamicConfigurator.javassistaop.StaticMethodAopTest;

public class StaticMethodAopTestTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getName() {
        StaticMethodAopTest.getName();
        StaticMethodAopProcessor smap = new StaticMethodAopProcessor();
        smap.weaveProcessor();
        String name = StaticMethodAopTest.getName();
        
        //assertEquals("came.deng", name);
        System.out.println("----"+name);
    }

}
