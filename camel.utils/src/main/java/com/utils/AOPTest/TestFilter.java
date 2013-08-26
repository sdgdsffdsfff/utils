// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestFilter.java

package com.utils.AOPTest;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.CallbackFilter;

public class TestFilter
    implements CallbackFilter
{

    public TestFilter()
    {
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
}
