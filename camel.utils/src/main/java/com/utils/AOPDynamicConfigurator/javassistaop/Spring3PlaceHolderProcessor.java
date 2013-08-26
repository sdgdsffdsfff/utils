package com.utils.AOPDynamicConfigurator.javassistaop;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import java.io.IOException;
import java.io.PrintStream;
import javassist.*;

/**
 * <pre>
 * DESC: Spring3占位符AOP处理类
 * @author camel.deng
 * @2013-2-2
 * </pre>
 */
public class Spring3PlaceHolderProcessor extends BytecodeProcessor
{

    public Spring3PlaceHolderProcessor()
    {
    }

    public Object weaveProcessor()
    {
        super.weaveProcessor();
        /*
         * 在生成的class文件中增加import com.utils.AOPDynamicConfigurator.javassistaop.Spring3PlaceHolderProcessor
         */
        pool.importPackage("com.utils.AOPDynamicConfigurator.javassistaop.Spring3PlaceHolderProcessor");
        try
        {
            CtClass cc = pool.get("org.springframework.beans.factory.config.PropertyPlaceholderConfigurer");
            String params[] = {
                "java.lang.String", "java.util.Properties", "int"
            };
            CtClass paramTypes[] = pool.get(params);
            /*
             * 获取指定参数的方法，由于resolvePlaceholder方法存在同名重载，所有需要指定参数类型
             */
            CtMethod cm = cc.getDeclaredMethod("resolvePlaceholder", paramTypes);
            /*
             * 在尾部插入代码。$_表示原代码返回值。
             * 这段代码会改变原 代码：return propVal为
             *  String s = propVal;
        	 *	s = Spring3PlaceHolderProcessor.resolvePlaceholderFromConfigParamMap(s);
        	 *	return s
             */
            cm.insertAfter("{$_ = Spring3PlaceHolderProcessor.resolvePlaceholderFromConfigParamMap($_);}");
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
     * spring3动态参数占位符处理方法。
     * 注意：spring3自定义占位符采用{...}表示，前面不带有$符合，避免与spring占位符冲突
     * @param placeholder
     * @return
     */
    public static String resolvePlaceholderFromConfigParamMap(String placeholder)
    {
    	placeholder = placeholder.trim();
        if(placeholder.startsWith("{") && placeholder.endsWith("}"))
        {
            String paramMapKey = placeholder.substring(1, placeholder.length() - 1);
            placeholder = ConfigParamMap.getValue(paramMapKey);
            if(placeholder != null){
                return placeholder;
            }
            //如果自定义环境变量没有找到，则输入错误日志
            System.out.println((new StringBuilder()).append("ERROR: Spring3PlaceHolderProcessor-- no property's value found in ConfigParamMap for ").append(paramMapKey).append(", being sure this value exist in tabel t_configparam!").toString());
        }
        return placeholder;
    }
}
