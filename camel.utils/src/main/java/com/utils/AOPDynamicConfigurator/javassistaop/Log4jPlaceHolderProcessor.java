package com.utils.AOPDynamicConfigurator.javassistaop;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import javassist.*;

/**
 * <pre>
 * DESC:处理log4j的占位符${...},使用环境参数配置值替代占位符
 * @author camel.deng
 * @2013-2-17
 * </pre>
 */

public class Log4jPlaceHolderProcessor extends BytecodeProcessor
{

    public Log4jPlaceHolderProcessor()
    {
    }

    public Object weaveProcessor()
    {
        super.weaveProcessor();
        pool.importPackage("com.utils.AOPDynamicConfigurator.javassistaop.Log4jPlaceHolderProcessor");
        try
        {
        	//Class clazz = pool.getClass().getClassLoader().loadClass("org.apache.log4j.helpers.OptionConverter");
            CtClass cc = pool.get("org.apache.log4j.helpers.OptionConverter");
            CtMethod cm = cc.getDeclaredMethod("getSystemProperty");
            cm.insertBefore("{Log4jPlaceHolderProcessor.preSetSystemProperty($1,$2);}");
            //cc.writeFile();
            cc.toClass();
        }
        catch(NotFoundException e)
        {
            e.printStackTrace();
        }
        catch(CannotCompileException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 在log4j的OptionConverter.getSystemProperty方法开始处理前，先调用本方法。
     * @param key
     * @param def
     */
    public static void preSetSystemProperty(String key, String def)
    {
    	/*
    	 * log4j的配置文件中的动态参数默认放在system.property中。
    	 * 在这里预先将配置的环境参数放入system.prperty内
    	 */
        String value = System.getProperty(key, def);
        if(null == value || value.length() == 0)
        {
        	//判读是否有自定义的环境参数
            value = ConfigParamMap.getValue(key);
            if(value != null)
                System.setProperty(key, value);
        }
    }
}
