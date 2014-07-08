/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.utils.AOPDynamicConfigurator.javassistaop;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * 测试静态方法aop拦截
 * @author dengqb
 * @date 2013年11月6日
 */
public class StaticMethodAopProcessor extends BytecodeProcessor {
    public Object weaveProcessor()
    {
        super.weaveProcessor();
        /*
         * 在生成的class文件中增加import com.utils.AOPDynamicConfigurator.javassistaop.Spring3PlaceHolderProcessor
         */
        pool.importPackage("com.utils.AOPDynamicConfigurator.javassistaop.StaticMethodAopProcessor");
        try
        {
            CtClass cc = pool.get("com.utils.AOPDynamicConfigurator.javassistaop.StaticMethodAopTest");
//            String params[] = {
//                "java.lang.String", "java.util.Properties", "int"
//            };
//            CtClass paramTypes[] = pool.get(params);
            /*
             * 获取指定参数的方法，由于resolvePlaceholder方法存在同名重载，所有需要指定参数类型
             */
            CtMethod cm = cc.getDeclaredMethod("getName");
            /*
             * 在尾部插入代码。$_表示原代码返回值。
             * 这段代码会改变原 代码：return propVal为
             *  String s = propVal;
             *  s = Spring3PlaceHolderProcessor.resolvePlaceholderFromConfigParamMap(s);
             *  return s
             */
            cm.insertAfter("{$_ = StaticMethodAopProcessor.insertIntoStaticMethod($_);}");
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
    public static String insertIntoStaticMethod(String original)
    {
        
        //如果自定义环境变量没有找到，则输入错误日志
        System.out.println("here print by aop method = dengqibiao");
        return "dengqibiao";
    }
}
