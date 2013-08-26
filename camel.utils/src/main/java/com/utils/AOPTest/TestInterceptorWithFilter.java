// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestInterceptorWithFilter.java

package com.utils.AOPTest;

import com.utils.AOPDynamicConfigurator.cglibaop.IInterceptorWithFilter;
import java.io.PrintStream;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.*;

public class TestInterceptorWithFilter extends IInterceptorWithFilter
{

    public TestInterceptorWithFilter()
    {
    }

    public Object createProxy()
    {
        try
        {
            Class AOPTestProcess = Class.forName("camel.utils.AOPTest.PersonServiceImpl");
            if(null != AOPTestProcess)
            {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(AOPTestProcess);
                enhancer.setCallbacks(new Callback[] {
                    this, NoOp.INSTANCE
                });
                enhancer.setCallbackFilter(this);
                Object obj = enhancer.create();
                return obj;
            }
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int accept(Method method)
    {
        if("save".equalsIgnoreCase(method.getName()))
        {
            Class paramTypes[] = method.getParameterTypes();
            if(paramTypes[0].equals(java.lang.String.class))
                return 0;
        }
        return 1;
    }

    public Object intercept(Object obj, Method method, Object args[], MethodProxy proxy)
        throws Throwable
    {
        preMethod();
        proxy.invokeSuper(obj, args);
        return null;
    }

    public void preMethod()
    {
        System.out.println("i am ready to save");
    }

    private static final String LOGGER_HOME_KEY = "logger.home";
    private static final String LOG4J_PLACEHOLDER_PROCESS_CLASS = "org.apache.log4j.PropertyConfigurator";
}
