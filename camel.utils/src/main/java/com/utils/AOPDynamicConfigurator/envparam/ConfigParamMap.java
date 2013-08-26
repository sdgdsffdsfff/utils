// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigParamMap.java

package com.utils.AOPDynamicConfigurator.envparam;

import java.util.Map;

// Referenced classes of package com.utils.AOPDynamicConfigurator.envparam:
//            ConfigParamBean

public class ConfigParamMap
{
    private static Map<String,ConfigParamBean > configParamMap;

    public static Map<String,ConfigParamBean> getConfigParamMap()
    {
        return configParamMap;
    }

    public static void setConfigParamMap(Map<String,ConfigParamBean> configParamMap)
    {
    	ConfigParamMap.configParamMap = configParamMap;
    }

    public static String getValue(String key)
    {
        String value = null;
        ConfigParamBean cpb = (ConfigParamBean)getConfigParamMap().get(key);
        if(cpb != null)
            value = cpb.getValue();
        return value;
    }


}
