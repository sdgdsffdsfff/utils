// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConfigParamReaderFactory.java

package com.utils.AOPDynamicConfigurator.envparam;


// Referenced classes of package com.utils.AOPDynamicConfigurator.envparam:
//            ConfigParamXMLReader, ConfigParamDBReader, ReaderEnum, IConfigParamReader

public class ConfigParamReaderFactory
{

    public ConfigParamReaderFactory()
    {
    }

    public static IConfigParamReader createConfigParamReader(String configType)
    {
        if(configType.equalsIgnoreCase(ReaderEnum.XML.name()))
            return new ConfigParamXMLReader();
        if(configType.equalsIgnoreCase(ReaderEnum.JDBC.name()))
            return new ConfigParamDBReader();
        if(configType.equalsIgnoreCase(ReaderEnum.JNDI.name()))
            return new ConfigParamDBReader();
        else
            return null;
    }
}
