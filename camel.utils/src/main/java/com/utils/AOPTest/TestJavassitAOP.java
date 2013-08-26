// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TestJavassitAOP.java

package com.utils.AOPTest;

import java.io.PrintStream;
import javassist.*;

public class TestJavassitAOP
{

    public TestJavassitAOP()
    {
    }

    public void modifySaveMethod()
    {
        ClassPool pool = ClassPool.getDefault();
        pool.importPackage("camel.utils.AOPTest.TestJavassitAOP");
        try
        {
            CtClass ctClass = pool.get("camel.utils.AOPTest.PersonServiceImpl");
            ClassLoader cl = ctClass.getClass().getClassLoader();
            CtMethod cm = createMethod(ctClass);
            ctClass.addMethod(cm);
            CtMethod method1 = ctClass.getDeclaredMethod("getPersonName");
            method1.insertAfter("{$_ = TestJavassitAOP.CallForAOPBefore($_);}");
            ctClass.writeFile();
            ctClass.toClass();
        }
        catch(NotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch(CannotCompileException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public CtMethod createMethod(CtClass ctClass)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("public void CallForAOPBefore(){").append("System.out.println(\"insert befor save method by create method\");").append("}");
        try
        {
            CtMethod cm = CtNewMethod.make(sb.toString(), ctClass);
            return cm;
        }
        catch(CannotCompileException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String CallForAOPBefore(String local)
    {
        System.out.println((new StringBuilder()).append("insert befor save method by class method. and local = ").append(local).toString());
        return "AAA";
    }
}
