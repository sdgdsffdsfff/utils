// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IInterceptorWithFilter.java

package com.utils.AOPDynamicConfigurator.cglibaop;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.MethodInterceptor;

public abstract class IInterceptorWithFilter
    implements MethodInterceptor, CallbackFilter
{

    public IInterceptorWithFilter()
    {
    }

    public Object createProxy()
    {
        return null;
    }
}
