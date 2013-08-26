package com.utils.AOPDynamicConfigurator.cglibaop;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamBean;
import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Map;
import net.sf.cglib.proxy.*;


public class Spring3InterceptorWithFilter extends IInterceptorWithFilter
{

    public Spring3InterceptorWithFilter()
    {
    }

    public Object createProxy()
    {
        try
        {
            Class spring3PlaceHolderProcess = Class.forName("org.springframework.beans.factory.config.PropertyPlaceholderConfigurer");
            if(null != spring3PlaceHolderProcess)
            {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(spring3PlaceHolderProcess);
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
        return !"resolvePlaceholder".equalsIgnoreCase(method.getName()) ? 1 : 0;
    }

    public Object intercept(Object obj, Method method, Object args[], MethodProxy proxy)
        throws Throwable
    {
        String value = (String)proxy.invokeSuper(obj, args);
        resolvePlaceholderFromConfigParamMap(value);
        return null;
    }

    protected String resolvePlaceholderFromConfigParamMap(String placeholder)
    {
        Map configParamMap = ConfigParamMap.getConfigParamMap();
        if(placeholder.startsWith("{") && placeholder.endsWith("}"))
        {
            String paramMapKey = placeholder.substring(1, placeholder.length() - 1);
            ConfigParamBean cpb = (ConfigParamBean)configParamMap.get(paramMapKey);
            if(cpb != null)
                placeholder = cpb.getValue();
            else
                System.out.println("ERROR: no property's value found in ConfigParamMap, being sure this value exist in tabel t_config_param!");
        }
        return placeholder;
    }

    private static Spring3InterceptorWithFilter spring3InterceptorWithFilter;
    private static final String SPRING_PLACEHOLDER_PROCESS_CLASS = "org.springframework.beans.factory.config.PropertyPlaceholderConfigurer";
}
