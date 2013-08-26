// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnvConfigAOPFactory.java

package com.utils.AOPDynamicConfigurator.cglibaop;


// Referenced classes of package com.utils.AOPDynamicConfigurator.cglibaop:
//            Log4jInterceptorWithFilter

public class EnvConfigAOPFactory
{

    public EnvConfigAOPFactory()
    {
    }

    public static void initAOPProxy()
    {
        Log4jInterceptorWithFilter liwf = new Log4jInterceptorWithFilter();
        liwf.createProxy();
    }
}
