// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Log4jInterceptorWithFilter.java

package com.utils.AOPDynamicConfigurator.cglibaop;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;
import net.sf.cglib.proxy.*;

// Referenced classes of package com.utils.AOPDynamicConfigurator.cglibaop:
//            IInterceptorWithFilter

public class Log4jInterceptorWithFilter extends IInterceptorWithFilter
{

    public Log4jInterceptorWithFilter()
    {
    }

    public Object createProxy()
    {
        try
        {
            Class log4jPlaceHolderProcess = Class.forName("org.apache.log4j.PropertyConfigurator");
            if(null != log4jPlaceHolderProcess)
            {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(log4jPlaceHolderProcess);
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
        if("doConfigure".equalsIgnoreCase(method.getName()))
        {
            Class paramTypes[] = method.getParameterTypes();
            if(paramTypes[0].equals(java.util.Properties.class))
                return 0;
        }
        return 1;
    }

    public Object intercept(Object obj, Method method, Object args[], MethodProxy proxy)
        throws Throwable
    {
        setLoggerRootFromConfigParamMap();
        proxy.invokeSuper(obj, args);
        return null;
    }

    public static void setLoggerRootFromConfigParamMap()
    {
        String val = ConfigParamMap.getValue("logger.home");
        if(null != val && val.trim().length() > 0)
        {
            System.setProperty("logger.home", val);
        } else
        {
            URL url = Log4jInterceptorWithFilter.class.getClassLoader().getResource(null);
            System.setProperty("logger.home", url.getPath());
        }
    }

    private static final String LOGGER_HOME_KEY = "logger.home";
    private static Log4jInterceptorWithFilter log4jInterceptorWithFilter;
    private static final String LOG4J_PLACEHOLDER_PROCESS_CLASS = "org.apache.log4j.PropertyConfigurator";
}
