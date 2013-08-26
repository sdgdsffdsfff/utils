// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigParamXMLReader.java

package com.utils.AOPDynamicConfigurator.envparam;

import java.util.Map;

// Referenced classes of package com.utils.AOPDynamicConfigurator.envparam:
//            IConfigParamReader, EnvConfigBean

public class ConfigParamXMLReader
    implements IConfigParamReader
{

    public ConfigParamXMLReader()
    {
    }

    public Map<String,ConfigParamBean> getAllConfigParam(EnvConfigBean envConfig)
    {
        return envConfig.getConfigParam();
    }
}
